package com.example.carrental.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching(order = 0)
public class CacheConfig {
}