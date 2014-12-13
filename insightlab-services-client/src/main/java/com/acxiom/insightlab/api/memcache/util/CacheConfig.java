package com.acxiom.insightlab.api.memcache.util;

import java.io.IOException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CacheConfig {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CacheConfig.class);

	@Value("${app.cache.enabled:false}")
	private boolean cacheEnabled;

	@Value("${app.cache.host:localhost}")
	private String cacheHost;

	@Value("${app.cache.port:11211}")
	private int cachePort;

	@Value("${app.cache.live:86400}")
	private int cacheLive;

	@Value("${app.cache.all:false}")
	private boolean cacheAll;

	private MemcachedClient memcachedClient;

	public MemcachedClient getMemcachedClient() {
		if (memcachedClient == null && cacheEnabled) {
			try {
				memcachedClient = new XMemcachedClient(cacheHost, cachePort);
			} catch (IOException e) {
				LOGGER.error("Error within Memcached Client initialization", e);
			}
		}
		return memcachedClient;

	}

	public boolean isCacheEnabled() {
		return cacheEnabled;
	}
	
	public void setCacheEnabled(final boolean enabled) {
		this.cacheEnabled = enabled;
	}

	public int getCacheLive() {
		return cacheLive;
	}

	public String getCacheHost() {
		return cacheHost;
	}

	public void setCacheHost(final String cacheHost) {
		this.cacheHost = cacheHost;
	}

	public int getCachePort() {
		return cachePort;
	}

	public void setCachePort(final int cachePort) {
		this.cachePort = cachePort;
	}

	public boolean isCacheAll() {
		return cacheAll;
	}

	public void setCacheAll(final boolean cacheAll) {
		this.cacheAll = cacheAll;
	}

	public void setCacheLive(final int cacheLive) {
		this.cacheLive = cacheLive;
	}
}
