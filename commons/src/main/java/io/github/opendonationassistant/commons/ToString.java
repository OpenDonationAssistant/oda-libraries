package io.github.opendonationassistant.commons;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.micronaut.serde.ObjectMapper;
import java.util.Map;

public class ToString {

  private static final ODALogger log = new ODALogger(ToString.class);

  public static String asJson(Object target) {
    var mapper = ObjectMapper.getDefault();
    var value =
      """
      { "error": "can't be serialized as json" }
      """;
    try {
      value = mapper.writeValueAsString(target);
    } catch (Exception e) {}
    return value;
  }

  public static byte[] asBytes(Object target) {
    try {
      return ObjectMapper.getDefault().writeValueAsBytes(target);
    } catch (Exception e) {
      log.error("Serialization error", Map.of("error", e.getMessage()));
      throw new RuntimeException(e);
    }
  }
}
