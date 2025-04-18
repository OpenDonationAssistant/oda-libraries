package io.github.opendonationassistant.commons;

import io.micronaut.serde.ObjectMapper;

public class ToString {

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
}
