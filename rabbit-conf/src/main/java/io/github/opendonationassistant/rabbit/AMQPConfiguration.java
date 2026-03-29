package io.github.opendonationassistant.rabbit;

import com.rabbitmq.client.Channel;
import io.micronaut.rabbitmq.connect.ChannelInitializer;
import java.io.IOException;
import java.util.List;

public class AMQPConfiguration extends ChannelInitializer {

  private final List<Exchange> exchanges;

  public AMQPConfiguration(List<Exchange> exchanges) {
    super();
    this.exchanges = exchanges;
  }

  @Override
  public void initialize(Channel channel, String name) throws IOException {
    exchanges.forEach(exchange -> exchange.connect(channel));
  }
}
