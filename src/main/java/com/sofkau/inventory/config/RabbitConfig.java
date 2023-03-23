package com.sofkau.inventory.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitConfig {

    public static final String ARMOR_QUEUE = "armor.queue";
    public static final String EXCHANGE = "armor-exchange-events";
    public static final String ROUTING_KEY = "events.armor.routing.key";

    public static final String GENERAL_QUEUE = "general.queue";
    public static final String ROUTING_KEY_GENERAL = "events.#";

    @Bean
    public Queue eventsQueue() {
        return new Queue(ARMOR_QUEUE);
    }

    @Bean
    public Queue generalQueue() {
        return new Queue(GENERAL_QUEUE);
    }

    @Bean
    public TopicExchange eventsExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding eventsBinding(){
        return BindingBuilder.bind(this.eventsQueue()).to(this.eventsExchange()).with(ROUTING_KEY);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(this.generalQueue()).to(this.eventsExchange()).with(ROUTING_KEY_GENERAL);
    }
}
