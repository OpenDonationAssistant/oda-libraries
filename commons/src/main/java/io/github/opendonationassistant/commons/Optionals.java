package io.github.opendonationassistant.commons;

import java.util.Optional;
import java.util.function.BiFunction;

public final class Optionals {

  private Optionals() {}

  public static <A, B> For2<A, B> For(Optional<A> first, Optional<B> second) {
    return new For2<>(first, second);
  }

  public static final class For2<A, B> {

    private final Optional<A> first;
    private final Optional<B> second;

    private For2(Optional<A> first, Optional<B> second) {
      this.first = first;
      this.second = second;
    }

    public <R> Optional<R> yield(
      BiFunction<? super A, ? super B, ? extends R> fn
    ) {
      return first.flatMap(x -> second.map(y -> fn.apply(x, y)));
    }
  }

  public static <A, B, C> For3<A, B, C> For(
    Optional<A> first,
    Optional<B> second,
    Optional<C> third
  ) {
    return new For3<>(first, second, third);
  }

  public static final class For3<A, B, C> {

    private final Optional<A> first;
    private final Optional<B> second;
    private final Optional<C> third;

    private For3(Optional<A> first, Optional<B> second, Optional<C> third) {
      this.first = first;
      this.second = second;
      this.third = third;
    }

    public <R> Optional<R> yield(
      Function3<? super A, ? super B, ? super C, ? extends R> fn
    ) {
      return first.flatMap(a ->
        second.flatMap(b -> third.map(c -> fn.apply(a, b, c)))
      );
    }
  }

  @FunctionalInterface
  public interface Function3<A, B, C, R> {
    R apply(A a, B b, C c);
  }
}
