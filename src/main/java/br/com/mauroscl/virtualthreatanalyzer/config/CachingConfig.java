package br.com.mauroscl.virtualthreatanalyzer.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableCaching
public class CachingConfig {

//  @Bean
//  public CacheManager cacheManager() {
//    return new EhCacheCacheManager(ehCacheCacheManager().getObject());
//  }
//
//  @Bean
//  public EhCacheManagerFactoryBean ehCacheCacheManager() {
//    EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
//    cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
//    cmfb.setShared(true);
//    return cmfb;
//  }
}
