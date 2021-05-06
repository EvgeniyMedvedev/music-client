package ru.luxoft.client.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "crud")
public class QueueProperty {
    
    private String create;
    private String createAnswer;
    private String read;
    private String readAnswer;
    private String update;
    private String updateAnswer;
    private String delete;
    private String deleteAnswer;
}
