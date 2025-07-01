package com.gourav.YummiGoBackend.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.name}")
    private String cloudName;
    @Value("${cloudinary.access.key}")
    private String accessKey;
    @Value("${cloudinary.secret.key}")
    private String secretKey;

    @Bean
    public Cloudinary cloudinary(){
        Map config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", accessKey);
        config.put("api_secret", secretKey);
        config.put("secure",true);

        return new Cloudinary(config);

    }
}
