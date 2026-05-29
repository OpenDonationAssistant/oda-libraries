package io.github.opendonationassistant.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import io.micronaut.rabbitmq.connect.ChannelPool;

public class RabbitListener {
    
    private final ChannelPool channelPool;
    
    public RabbitListener(ChannelPool channelPool) {
        this.channelPool = channelPool;
        setupListener();
    }
    
    private void setupListener() {
        try {
            Channel channel = channelPool.getChannel();
            String queue = "my-queue";
            
            // Declare the queue
            channel.queueDeclare(queue, true, false, false, null);
            
            // Set up the consumer
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Received: " + message);
                
                // Acknowledge the message
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            };
            
            channel.basicConsume(queue, false, deliverCallback, consumerTag -> {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to setup RabbitMQ listener", e);
        }
    }
}
