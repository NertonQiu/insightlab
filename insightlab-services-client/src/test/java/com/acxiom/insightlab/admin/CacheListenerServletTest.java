package com.acxiom.insightlab.admin;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeoutException;

import javax.servlet.ServletException;

import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.Counter;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.KeyIterator;
import net.rubyeye.xmemcached.KeyProvider;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientStateListener;
import net.rubyeye.xmemcached.auth.AuthInfo;
import net.rubyeye.xmemcached.buffer.BufferAllocator;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.impl.ReconnectRequest;
import net.rubyeye.xmemcached.networking.Connector;
import net.rubyeye.xmemcached.transcoders.Transcoder;
import net.rubyeye.xmemcached.utils.Protocol;

import org.aspectj.lang.annotation.Before;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.acxiom.insightlab.api.memcache.util.CacheConfig;

public class CacheListenerServletTest {

	private CacheListenerServlet servlet;

	private CacheConfig cacheConfig;
	private MemcachedClient memcachedClient;

	@Before
	public void setUp() throws Exception {

		servlet = new CacheListenerServlet();

		memcachedClient = new MemcachedClient() {

			@Override
			public boolean touch(String arg0, int arg1, long arg2)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touch(String arg0, int arg1)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public Map<String, String> stats(InetSocketAddress arg0, long arg1)
					throws MemcachedException, InterruptedException,
					TimeoutException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map<String, String> stats(InetSocketAddress arg0)
					throws MemcachedException, InterruptedException,
					TimeoutException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void shutdown() throws IOException {
				// TODO Auto-generated method stub

			}

			@Override
			public <T> void setWithNoReply(String arg0, int arg1, T arg2,
					Transcoder<T> arg3) throws InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub

			}

			@Override
			public void setWithNoReply(String arg0, int arg1, Object arg2)
					throws InterruptedException, MemcachedException {
				// TODO Auto-generated method stub

			}

			@Override
			public void setTranscoder(Transcoder arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setTimeoutExceptionThreshold(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setSanitizeKeys(boolean arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setPrimitiveAsString(boolean arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setOptimizeMergeBuffer(boolean arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setOptimizeGet(boolean arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setOpTimeout(long arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setName(String arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setMergeFactor(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setLoggingLevelVerbosityWithNoReply(
					InetSocketAddress arg0, int arg1)
					throws InterruptedException, MemcachedException {
				// TODO Auto-generated method stub

			}

			@Override
			public void setLoggingLevelVerbosity(InetSocketAddress arg0,
					int arg1) throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub

			}

			@Override
			public void setKeyProvider(KeyProvider arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setHealSessionInterval(long arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setFailureMode(boolean arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setEnableHeartBeat(boolean arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setEnableHealSession(boolean arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setConnectionPoolSize(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setConnectTimeout(long arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setBufferAllocator(BufferAllocator arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setAuthInfoMap(Map<InetSocketAddress, AuthInfo> arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public <T> boolean set(String arg0, int arg1, T arg2,
					Transcoder<T> arg3, long arg4) throws TimeoutException,
					InterruptedException, MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public <T> boolean set(String arg0, int arg1, T arg2,
					Transcoder<T> arg3) throws TimeoutException,
					InterruptedException, MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean set(String arg0, int arg1, Object arg2, long arg3)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean set(String arg0, int arg1, Object arg2)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public <T> void replaceWithNoReply(String arg0, int arg1, T arg2,
					Transcoder<T> arg3) throws InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub

			}

			@Override
			public void replaceWithNoReply(String arg0, int arg1, Object arg2)
					throws InterruptedException, MemcachedException {
				// TODO Auto-generated method stub

			}

			@Override
			public <T> boolean replace(String arg0, int arg1, T arg2,
					Transcoder<T> arg3, long arg4) throws TimeoutException,
					InterruptedException, MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public <T> boolean replace(String arg0, int arg1, T arg2,
					Transcoder<T> arg3) throws TimeoutException,
					InterruptedException, MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean replace(String arg0, int arg1, Object arg2, long arg3)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean replace(String arg0, int arg1, Object arg2)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void removeStateListener(MemcachedClientStateListener arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void removeServer(String arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void prependWithNoReply(String arg0, Object arg1)
					throws InterruptedException, MemcachedException {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean prepend(String arg0, Object arg1, long arg2)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean prepend(String arg0, Object arg1)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isShutdown() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isSanitizeKeys() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isFailureMode() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void incrWithNoReply(String arg0, long arg1)
					throws InterruptedException, MemcachedException {
				// TODO Auto-generated method stub

			}

			@Override
			public long incr(String arg0, long arg1, long arg2, long arg3,
					int arg4) throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long incr(String arg0, long arg1, long arg2, long arg3)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long incr(String arg0, long arg1, long arg2)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long incr(String arg0, long arg1) throws TimeoutException,
					InterruptedException, MemcachedException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public <T> Map<String, GetsResponse<T>> gets(
					Collection<String> arg0, long arg1, Transcoder<T> arg2)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> GetsResponse<T> gets(String arg0, long arg1,
					Transcoder<T> arg2) throws TimeoutException,
					InterruptedException, MemcachedException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> Map<String, GetsResponse<T>> gets(
					Collection<String> arg0, Transcoder<T> arg1)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> Map<String, GetsResponse<T>> gets(
					Collection<String> arg0, long arg1)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> GetsResponse<T> gets(String arg0, Transcoder arg1)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> GetsResponse<T> gets(String arg0, long arg1)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> Map<String, GetsResponse<T>> gets(Collection<String> arg0)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> GetsResponse<T> gets(String arg0)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map<InetSocketAddress, String> getVersions(long arg0)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map<InetSocketAddress, String> getVersions()
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Transcoder getTranscoder() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getTimeoutExceptionThreshold() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Map<InetSocketAddress, Map<String, String>> getStatsByItem(
					String arg0, long arg1) throws MemcachedException,
					InterruptedException, TimeoutException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map<InetSocketAddress, Map<String, String>> getStatsByItem(
					String arg0) throws MemcachedException,
					InterruptedException, TimeoutException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map<InetSocketAddress, Map<String, String>> getStats(
					long arg0) throws MemcachedException, InterruptedException,
					TimeoutException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map<InetSocketAddress, Map<String, String>> getStats()
					throws MemcachedException, InterruptedException,
					TimeoutException {
				Map<InetSocketAddress, Map<String, String>> fakeStats = new HashMap<InetSocketAddress, Map<String, String>>();
				Map<String, String> fakeStat = new HashMap<String, String>();
				fakeStat.put("fake key", "fake value");
				fakeStats.put(new InetSocketAddress(0), fakeStat);
				return fakeStats;
			}

			@Override
			public Collection<MemcachedClientStateListener> getStateListeners() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<String> getServersDescription() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Queue<ReconnectRequest> getReconnectRequestQueue() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Protocol getProtocol() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getOpTimeout() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public KeyIterator getKeyIterator(InetSocketAddress arg0)
					throws MemcachedException, InterruptedException,
					TimeoutException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getHealSessionInterval() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Counter getCounter(String arg0, long arg1) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Counter getCounter(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Connector getConnector() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getConnectTimeout() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Collection<InetSocketAddress> getAvaliableServers() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Collection<InetSocketAddress> getAvailableServers() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map<InetSocketAddress, AuthInfo> getAuthInfoMap() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> T getAndTouch(String arg0, int arg1, long arg2)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> T getAndTouch(String arg0, int arg1)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> Map<String, T> get(Collection<String> arg0, long arg1,
					Transcoder<T> arg2) throws TimeoutException,
					InterruptedException, MemcachedException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> T get(String arg0, long arg1, Transcoder<T> arg2)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> Map<String, T> get(Collection<String> arg0, long arg1)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> Map<String, T> get(Collection<String> arg0,
					Transcoder<T> arg1) throws TimeoutException,
					InterruptedException, MemcachedException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> T get(String arg0, Transcoder<T> arg1)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> T get(String arg0, long arg1) throws TimeoutException,
					InterruptedException, MemcachedException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> Map<String, T> get(Collection<String> arg0)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> T get(String arg0) throws TimeoutException,
					InterruptedException, MemcachedException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void flushAllWithNoReply(InetSocketAddress arg0, int arg1)
					throws MemcachedException, InterruptedException {
				// TODO Auto-generated method stub

			}

			@Override
			public void flushAllWithNoReply(int arg0)
					throws InterruptedException, MemcachedException {
				// TODO Auto-generated method stub

			}

			@Override
			public void flushAllWithNoReply(InetSocketAddress arg0)
					throws MemcachedException, InterruptedException {
				// TODO Auto-generated method stub

			}

			@Override
			public void flushAllWithNoReply() throws InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub

			}

			@Override
			public void flushAll(InetSocketAddress arg0, long arg1, int arg2)
					throws MemcachedException, InterruptedException,
					TimeoutException {
				// TODO Auto-generated method stub

			}

			@Override
			public void flushAll(int arg0, long arg1) throws TimeoutException,
					InterruptedException, MemcachedException {
				// TODO Auto-generated method stub

			}

			@Override
			public void flushAll(InetSocketAddress arg0, long arg1)
					throws MemcachedException, InterruptedException,
					TimeoutException {
				// TODO Auto-generated method stub

			}

			@Override
			public void flushAll(String arg0) throws TimeoutException,
					InterruptedException, MemcachedException {
				// TODO Auto-generated method stub

			}

			@Override
			public void flushAll(InetSocketAddress arg0)
					throws MemcachedException, InterruptedException,
					TimeoutException {
				// TODO Auto-generated method stub

			}

			@Override
			public void flushAll(long arg0) throws TimeoutException,
					InterruptedException, MemcachedException {
				// TODO Auto-generated method stub

			}

			@Override
			public void flushAll() throws TimeoutException,
					InterruptedException, MemcachedException {
				// TODO Auto-generated method stub

			}

			@Override
			public void deleteWithNoReply(String arg0, int arg1)
					throws InterruptedException, MemcachedException {
				// TODO Auto-generated method stub

			}

			@Override
			public void deleteWithNoReply(String arg0)
					throws InterruptedException, MemcachedException {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean delete(String arg0, long arg1)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean delete(String arg0, int arg1)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean delete(String arg0) throws TimeoutException,
					InterruptedException, MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void decrWithNoReply(String arg0, long arg1)
					throws InterruptedException, MemcachedException {
				// TODO Auto-generated method stub

			}

			@Override
			public long decr(String arg0, long arg1, long arg2, long arg3,
					int arg4) throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long decr(String arg0, long arg1, long arg2, long arg3)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long decr(String arg0, long arg1, long arg2)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long decr(String arg0, long arg1) throws TimeoutException,
					InterruptedException, MemcachedException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public <T> void casWithNoReply(String arg0, int arg1,
					GetsResponse<T> arg2, CASOperation<T> arg3)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub

			}

			@Override
			public <T> void casWithNoReply(String arg0, int arg1,
					CASOperation<T> arg2) throws TimeoutException,
					InterruptedException, MemcachedException {
				// TODO Auto-generated method stub

			}

			@Override
			public <T> void casWithNoReply(String arg0, GetsResponse<T> arg1,
					CASOperation<T> arg2) throws TimeoutException,
					InterruptedException, MemcachedException {
				// TODO Auto-generated method stub

			}

			@Override
			public <T> void casWithNoReply(String arg0, CASOperation<T> arg1)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub

			}

			@Override
			public <T> boolean cas(String arg0, int arg1, T arg2,
					Transcoder<T> arg3, long arg4, long arg5)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public <T> boolean cas(String arg0, int arg1, GetsResponse<T> arg2,
					CASOperation<T> arg3, Transcoder<T> arg4)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public <T> boolean cas(String arg0, int arg1, T arg2,
					Transcoder<T> arg3, long arg4) throws TimeoutException,
					InterruptedException, MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean cas(String arg0, int arg1, Object arg2, long arg3,
					long arg4) throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public <T> boolean cas(String arg0, int arg1, GetsResponse<T> arg2,
					CASOperation<T> arg3) throws TimeoutException,
					InterruptedException, MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public <T> boolean cas(String arg0, int arg1, CASOperation<T> arg2,
					Transcoder<T> arg3) throws TimeoutException,
					InterruptedException, MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean cas(String arg0, int arg1, Object arg2, long arg3)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public <T> boolean cas(String arg0, int arg1, CASOperation<T> arg2)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public <T> boolean cas(String arg0, GetsResponse<T> arg1,
					CASOperation<T> arg2) throws TimeoutException,
					InterruptedException, MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public <T> boolean cas(String arg0, CASOperation<T> arg1)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void appendWithNoReply(String arg0, Object arg1)
					throws InterruptedException, MemcachedException {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean append(String arg0, Object arg1, long arg2)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean append(String arg0, Object arg1)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public <T> void addWithNoReply(String arg0, int arg1, T arg2,
					Transcoder<T> arg3) throws InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub

			}

			@Override
			public void addWithNoReply(String arg0, int arg1, Object arg2)
					throws InterruptedException, MemcachedException {
				// TODO Auto-generated method stub

			}

			@Override
			public void addStateListener(MemcachedClientStateListener arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void addServer(String arg0, int arg1, int arg2)
					throws IOException {
				// TODO Auto-generated method stub

			}

			@Override
			public void addServer(InetSocketAddress arg0, int arg1)
					throws IOException {
				// TODO Auto-generated method stub

			}

			@Override
			public void addServer(String arg0, int arg1) throws IOException {
				// TODO Auto-generated method stub

			}

			@Override
			public void addServer(String arg0) throws IOException {
				// TODO Auto-generated method stub

			}

			@Override
			public void addServer(InetSocketAddress arg0) throws IOException {
				// TODO Auto-generated method stub

			}

			@Override
			public <T> boolean add(String arg0, int arg1, T arg2,
					Transcoder<T> arg3, long arg4) throws TimeoutException,
					InterruptedException, MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public <T> boolean add(String arg0, int arg1, T arg2,
					Transcoder<T> arg3) throws TimeoutException,
					InterruptedException, MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean add(String arg0, int arg1, Object arg2, long arg3)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean add(String arg0, int arg1, Object arg2)
					throws TimeoutException, InterruptedException,
					MemcachedException {
				// TODO Auto-generated method stub
				return false;
			}
		};

		cacheConfig = new CacheConfig();
		cacheConfig.setCacheEnabled(true);
		cacheConfig.setCacheHost("localhost");
		cacheConfig.setCachePort(1234);
		cacheConfig.getMemcachedClient();
		Whitebox.setInternalState(cacheConfig, memcachedClient);
		Whitebox.setInternalState(servlet, cacheConfig);

	}

	@Test
	public void testDestroy() {
		servlet.destroy();
	}

	@Test
	public void testCacheListenerServlet() throws ServletException {
		CacheListenerServlet cacheListenerServlet = new CacheListenerServlet();
	}

	@Test
	public void testDoGet() throws ServletException, IOException {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		mockRequest.addParameter("stats", "some value");
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		servlet.doGet(mockRequest, mockResponse);
		mockRequest.addParameter("flush_all", "some value");
		servlet.doGet(mockRequest, mockResponse);

	}

}
