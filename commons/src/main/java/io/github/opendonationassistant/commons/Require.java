package io.github.opendonationassistant.commons;

import java.util.Objects;

public class Require {

  public static void nonNull(Object... objs){
    for (Object obj : objs) {
      Objects.requireNonNull(obj);
    }
  }

}
