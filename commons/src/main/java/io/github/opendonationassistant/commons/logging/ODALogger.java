package io.github.opendonationassistant.commons.logging;

import io.github.opendonationassistant.commons.ToString;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class ODALogger {

  final org.slf4j.Logger log;

  public ODALogger(Object context) {
    this.log = LoggerFactory.getLogger(context.getClass());
  }

  public ODALogger(Class<?> className) {
    this.log = LoggerFactory.getLogger(className);
  }

  public void info(String message, Map<?, ?> context) {
    MDC.put("context", ToString.asJson(context));
    this.log.info(message);
    MDC.clear();
  }

  public void debug(String message, Map<?, ?> context) {
    MDC.put("context", ToString.asJson(context));
    this.log.debug(message);
    MDC.clear();
  }
}
