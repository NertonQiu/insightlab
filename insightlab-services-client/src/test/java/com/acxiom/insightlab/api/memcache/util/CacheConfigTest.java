package com.acxiom.insightlab.api.memcache.util;

import net.rubyeye.xmemcached.MemcachedClient;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static com.acxiom.insightlab.test.Fakes.CACHE_LIFETIME_1;
import static com.acxiom.insightlab.test.Fakes.CACHE_LIFETIME_2;
import static com.acxiom.insightlab.test.Fakes.PORT;
import static com.acxiom.insightlab.test.Fakes.HOST;

public class CacheConfigTest {

	private CacheConfig tested;


	@Before
	public void setUp() throws Exception {
		tested = new CacheConfig();
	}

	@Test
	public void testGetMemcachedClient() {
		MemcachedClient memcachedClient = tested.getMemcachedClient();
		Assert.assertNull(memcachedClient);

		tested.setCacheEnabled(true);
		tested.setCacheHost("localhost");
		tested.setCachePort(PORT);

		memcachedClient = tested.getMemcachedClient();
		Assert.assertNotNull(memcachedClient);
	}

	@Test
	public void testIsCacheEnabled() {
		boolean expected = true;
		tested.setCacheEnabled(expected);
		Assert.assertEquals(expected, tested.isCacheEnabled());
	}

	@Test
	public void testGetCacheLive() {
		tested.setCacheLive(CACHE_LIFETIME_1);
		Assert.assertEquals(CACHE_LIFETIME_1, tested.getCacheLive());
	}

	@Test
	public void testIsCacheAll() {
		boolean expected = true;
		tested.setCacheAll(expected);
		Assert.assertEquals(expected, tested.isCacheAll());
	}

	@Test
	public void testSetCacheAll() {
		boolean expected = true;
		tested.setCacheAll(expected);
		Assert.assertEquals(expected, tested.isCacheAll());
		boolean expected2 = false;
		tested.setCacheAll(expected2);
		Assert.assertEquals(expected2, tested.isCacheAll());
	}

	@Test
	public void testSetCacheLive() {
		tested.setCacheLive(CACHE_LIFETIME_1);
		Assert.assertEquals(CACHE_LIFETIME_1, tested.getCacheLive());
		tested.setCacheLive(CACHE_LIFETIME_2);
		Assert.assertEquals(CACHE_LIFETIME_2, tested.getCacheLive());
	}

	@Test
	public void testGetCachePort() {
		tested.setCachePort(PORT);
		Assert.assertEquals(PORT, tested.getCachePort());
	}

	@Test
	public void testGetCacheHost() {
		tested.setCacheHost(HOST);
		Assert.assertEquals(HOST, tested.getCacheHost());
	}

}
