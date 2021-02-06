package com.test.start;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "test.config")
public class TestConfigProperties {
    private int id;
    private String name;
}
