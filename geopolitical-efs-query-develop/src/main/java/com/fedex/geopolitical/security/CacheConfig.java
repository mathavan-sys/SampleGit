package com.fedex.geopolitical.security;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The Class CacheConfig.
 */
@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

	/* (non-Javadoc)
	 * @see org.springframework.cache.annotation.CachingConfigurerSupport#cacheManager()
	 */
	@Override
    @Bean
	public CacheManager cacheManager() {
    	final CaffeineCacheManager manager = new CaffeineCacheManager("AuthzDao","GroupDao","AuthN");
    	manager.setCacheSpecification("maximumSize=500,expireAfterWrite=6h");
        return manager;
	}

    /**
     * Token cache manager.
     *
     * @return the cache manager
     */
    @Bean
    public CacheManager tokenCacheManager() {
    	final CaffeineCacheManager manager = new CaffeineCacheManager("TokenCache");
    	manager.setCacheSpecification("initialCapacity=1,maximumSize=10,expireAfterWrite=45m");
        return manager;
    }
}
