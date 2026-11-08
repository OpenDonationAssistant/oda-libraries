# ADR 0001 — RFC 7807 error responses for unhandled and upstream failures

- **Status:** accepted
- **Date:** 2026-11-08
- **Issue:** oda-libraries — correct HTTP error responses for unhandled exceptions
- **Owner:** oda-libraries

## Context

`oda-commons` built the `Problem` for an unhandled `RuntimeException` without
`withStatus(...)`. Micronaut's `ThrowableProblemHandler` falls back to
`400 Bad Request` when a problem has no status, so every unhandled server-side
failure in every ODA service was reported to clients as a client error, with
`"status":null` in the body. The same class hand-built the `error` MDC value with
string formatting, which produced invalid JSON for messages containing quotes,
threw on empty stack traces, and cleared unrelated MDC keys.

The incident that surfaced this was an upstream `401 Unauthorized` from
`kick-auth` reaching the browser as `400 Bad Request`, logged as
`ERROR ... Server Error <id>`.

This ADR records the platform-wide decisions. The code change that accompanies
it is tracked by the issue's definition of done.

## Decisions

### 1. Unhandled exceptions are `500 Internal Server Error`

`GlobalExceptionHandler` always sets `Status.INTERNAL_SERVER_ERROR` (through the
shared `ApiProblem` builder), `type = /problems/internal_error`,
`code = internal_error`, a safe `detail` that contains the request id, and
`parameters.id` with the id that is logged server-side.

`500` is the correct semantic for an unexpected server fault. The previous
`400` was a library fallback, not a decision. Changing the title from
`Server Error` to `Internal Server Error` is part of this change; alert rules
that filter on the log **message** (`Server Error <id>`) are unaffected.

### 2. `ApiProblem` is the only problem builder in `commons`

`io.github.opendonationassistant.commons.problem.ApiProblem` owns the conventions:

| Field | Value |
| --- | --- |
| `type` / `code` | `/problems/<code>`, code in `snake_case` (`internal_error`, `upstream_unauthorized`) |
| `status` | always explicitly set |
| `detail` | human sentence, no secrets, ends with the request id |
| `parameters.id` | the id logged on the server |

Services must use it instead of building `Problem` by hand. A status-less
`Problem` must never reach `ThrowableProblemHandler`.

### 3. Upstream failure mapping: `401` for rejected authorization, `500` otherwise

When a reusable upstream-failure handler is shipped, the platform mapping is the
one already implemented in `oda-recipient-service` (its handler tests cover the
status/`type`/`code` mapping):

- upstream `401`/`403` → **`401 Unauthorized`**, `code = upstream_unauthorized`,
  logged at `WARN`. The provider rejected the authorization (for OAuth linking:
  the code is expired, already used, or issued for another client). The user has
  to re-link. This is an expected user flow, not a server fault, and it should
  not page anyone.
- any other upstream failure → **`500 Internal Server Error`**,
  `code = upstream_failure`, logged at `ERROR`, with the upstream status in the
  detail and in the log context.

**`502 Bad Gateway` for both is rejected.** It collapses "the user must re-link"
and "the provider is broken" into one code, forcing every client to parse the
detail or branch per service to decide whether to prompt a re-link. ODA clients
already act on `401`; keeping that contract avoids a second migration.

### 4. The upstream handler is **not** shipped in this release

`UpstreamCallExceptionHandler` stays in `oda-recipient-service` for now.
Shipping it in `commons` in the same release is blocked by:

1. **Dependency surface.** It needs `HttpClientResponseException` from
   `micronaut-http-client`, which `commons` does not depend on today. Adding it
   pulls the client stack into every service that depends on `oda-commons`.
2. **Duplicate handlers.** Services (starting with `oda-recipient-service`)
   still have local `HttpClientResponseException` handlers. A default-on bean in
   `commons` would register a second handler for the same exception type until
   each service deletes its local one.
3. **Migration order.** The issue's rollout keeps the recipient-service local
   handler until the shared one exists and is configurable.

When it is shipped, it will be gated by
`@Requires(property = "oda.problems.upstream", defaultValue = "true")` so a
service with its own mapping can switch it off without forking the library.
The consumer sequence will be: bump `oda.version` → delete local
`HttpClientResponseException` handlers → optionally set
`oda.problems.upstream=false`.

## Consequences

- Every service answering from a new `oda.version` returns `500` instead of
  `400` for genuinely unhandled exceptions, with `"status":500` in the body.
- Clients and generated npm clients must be audited for retry/re-auth logic that
  keys on `400`. This is a deliberate behavioural change.
- Alert rules that count `HTTP 400` responses change; rules on the
  `Server Error <id>` message and on `ERROR` level keep working.
- `rabbit-conf` is released together with `commons` so consumers never resolve
  a mixed pair; `RabbitExceptionHandler` needs no code change.
- The upstream handler follow-up requires its own PR and a consumer audit before
  the local handlers can be deleted.

## Follow-ups

1. Publish `0.12.<CI run number>` from `master` and read the exact version from
   the workflow run.
2. Bump `${oda.version}` in the consumers, starting with
   `oda-recipient-service`; delete its local `UnexpectedExceptionHandler` and
   its local `ApiProblem`, import the `commons` one, and keep
   `UpstreamCallExceptionHandler` until decision 4 is revisited.
3. Record the consumer audit outcome (which frontends/npm clients key on `400`)
   before the rollout is considered complete.
4. Revisit decision 4 when the consumer audit and local-handler cleanup are
   done; then ship the upstream handler with the decision-3 mapping.
