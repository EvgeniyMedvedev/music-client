package ru.luxoft.client.configuration;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import ru.luxoft.client.model.Some;
import ru.luxoft.client.model.*;


import javax.jms.ConnectionFactory;
import java.util.Collections;
import java.util.Map;

/**
 * JMSConfiguration.
 *
 * @author Evgeniy_Medvedev
 */
@Configuration
@EnableJms
public class JMSConfiguration {

    @Bean
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        // You could still override some of Boot's default if necessary.
        return factory;
    }

    @Bean // Serialize message content to json using TextMessage
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("content-type");
        converter.setTypeIdMappings(Map.of("song", Song.class, "singer", Singer.class,
                "album", Album.class, "genre",Genre.class));

        return converter;
    }
}