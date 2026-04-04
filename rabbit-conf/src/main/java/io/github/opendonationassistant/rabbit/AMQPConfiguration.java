package io.github.opendonationassistant.rabbit;

import com.rabbitmq.client.Channel;
import io.github.opendonationassistant.commons.logging.ODALogger;
import io.micronaut.rabbitmq.connect.ChannelInitializer;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AMQPConfiguration extends ChannelInitializer {

  private ODALogger log = new ODALogger(this);

  private final List<Exchange> exchanges;

  public AMQPConfiguration(List<Exchange> exchanges) {
    this.exchanges = exchanges;
    super();
  }

  @Override
  public void initialize(Channel channel, String name) throws IOException {
    log.debug(
      "Initializing Exchanges",
      Map.of("exchangeCount", exchanges.size())
    );
    exchanges.forEach(exchange -> exchange.connect(channel));
  }
}
