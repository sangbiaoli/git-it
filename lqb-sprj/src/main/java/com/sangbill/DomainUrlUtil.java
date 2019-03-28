package com.sangbill;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import lombok.Data;

@Service
@Data
@Configuration
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "api")
public class DomainUrlUtil {
    private String url;
}