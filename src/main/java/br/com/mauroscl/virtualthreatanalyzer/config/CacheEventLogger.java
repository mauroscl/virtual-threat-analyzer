package br.com.mauroscl.virtualthreatanalyzer.config;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CacheEventLogger implements CacheEventListener<Object, Object> {

  private static final Logger logger = LoggerFactory.getLogger(CacheEventLogger.class.getName());

  @Override
  public void onEvent(final CacheEvent<?, ?> cacheEvent) {
    logger.info("Cache event {} for item with key {}. Old value = {}, New value = {}",
        cacheEvent.getType(), cacheEvent.getKey(), cacheEvent.getOldValue(),
        cacheEvent.getNewValue());
  }
}
