package com.example.stock.price.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@Configuration
public class RedisConfig {
    // Read cluster nodes from properties
    @Value("${spring.redis.cluster.nodes}")
    private List<String> clusterNodes;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Create Cluster Configuration
        RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration();
        
        // Dynamically add cluster nodes
        clusterNodes.forEach(node -> {
            String[] parts = node.split(":");
            clusterConfig.addClusterNode(
                new RedisNode(parts[0], Integer.parseInt(parts[1]))
            );
        });
        
        // Set maximum redirects for error handling
        clusterConfig.setMaxRedirects(3);
        
        // Create connection factory
        return new LettuceConnectionFactory(clusterConfig);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        
        // Consistent serialization
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        
        template.setConnectionFactory(connectionFactory);
        template.afterPropertiesSet();
        
        return template;
    }
}