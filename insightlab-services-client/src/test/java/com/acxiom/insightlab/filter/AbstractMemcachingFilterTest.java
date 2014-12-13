package com.acxiom.insightlab.filter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeoutException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.acxiom.insightlab.api.memcache.util.CacheConfig;
import com.acxiom.insightlab.service.SecurityUtils;

@SuppressWarnings("deprecation")
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class AbstractMemcachingFilterTest {

	private AbstractMemcachingFilter filter;
	private CacheConfig cacheConfig;
	private MemcachedClient memcachedClient;

	private class TestMemcachedClient implements MemcachedClient {

		@Override
		public boolean touch(String s, int i, long l) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touch(String s, int i) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Map<String, String> stats(InetSocketAddress inetsocketaddress,
				long l) throws MemcachedException, InterruptedException,
				TimeoutException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<String, String> stats(InetSocketAddress inetsocketaddress)
				throws MemcachedException, InterruptedException,
				TimeoutException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void shutdown() throws IOException {

		}

		@Override
		public <T> void setWithNoReply(String arg0, int arg1, T arg2,
				Transcoder<T> arg3) throws InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub

		}

		@Override
		public void setWithNoReply(String s, int i, Object obj)
				throws InterruptedException, MemcachedException {
			// TODO Auto-generated method stub

		}

		@Override
		public void setTranscoder(
				@SuppressWarnings("rawtypes") Transcoder transcoder) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setTimeoutExceptionThreshold(int i) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setSanitizeKeys(boolean flag) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setPrimitiveAsString(boolean flag) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setOptimizeMergeBuffer(boolean flag) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setOptimizeGet(boolean flag) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setOpTimeout(long l) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setName(String s) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setMergeFactor(int i) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setLoggingLevelVerbosityWithNoReply(
				InetSocketAddress inetsocketaddress, int i)
				throws InterruptedException, MemcachedException {
			// TODO Auto-generated method stub

		}

		@Override
		public void setLoggingLevelVerbosity(
				InetSocketAddress inetsocketaddress, int i)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub

		}

		@Override
		public void setKeyProvider(KeyProvider keyprovider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setHealSessionInterval(long l) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setFailureMode(boolean flag) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setEnableHeartBeat(boolean flag) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setEnableHealSession(boolean flag) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setConnectionPoolSize(int i) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setConnectTimeout(long l) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setBufferAllocator(BufferAllocator bufferallocator) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setAuthInfoMap(Map<InetSocketAddress, AuthInfo> map) {
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
		public <T> boolean set(String arg0, int arg1, T arg2, Transcoder<T> arg3)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean set(String s, int i, Object obj, long l)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean set(String s, int i, Object obj)
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
		public void replaceWithNoReply(String s, int i, Object obj)
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
		public boolean replace(String s, int i, Object obj, long l)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean replace(String s, int i, Object obj)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void removeStateListener(
				MemcachedClientStateListener memcachedclientstatelistener) {
			// TODO Auto-generated method stub

		}

		@Override
		public void removeServer(String s) {
			// TODO Auto-generated method stub

		}

		@Override
		public void prependWithNoReply(String s, Object obj)
				throws InterruptedException, MemcachedException {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean prepend(String s, Object obj, long l)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean prepend(String s, Object obj) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isShutdown() {
			// TODO Auto-generated method stub
			return true;
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
		public void incrWithNoReply(String s, long l)
				throws InterruptedException, MemcachedException {
			// TODO Auto-generated method stub

		}

		@Override
		public long incr(String s, long l, long l1, long l2, int i)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long incr(String s, long l, long l1, long l2)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long incr(String s, long l, long l1) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long incr(String s, long l) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public <T> Map<String, GetsResponse<T>> gets(
				Collection<String> collection, long l, Transcoder<T> transcoder)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> GetsResponse<T> gets(String s, long l,
				Transcoder<T> transcoder) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> Map<String, GetsResponse<T>> gets(
				Collection<String> collection, Transcoder<T> transcoder)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> Map<String, GetsResponse<T>> gets(
				Collection<String> collection, long l) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub
			return null;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public <T> GetsResponse<T> gets(String s, Transcoder transcoder)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> GetsResponse<T> gets(String s, long l)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> Map<String, GetsResponse<T>> gets(
				Collection<String> collection) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> GetsResponse<T> gets(String s) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<InetSocketAddress, String> getVersions(long l)
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

		@SuppressWarnings("rawtypes")
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
				String s, long l) throws MemcachedException,
				InterruptedException, TimeoutException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<InetSocketAddress, Map<String, String>> getStatsByItem(
				String s) throws MemcachedException, InterruptedException,
				TimeoutException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<InetSocketAddress, Map<String, String>> getStats(long l)
				throws MemcachedException, InterruptedException,
				TimeoutException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<InetSocketAddress, Map<String, String>> getStats()
				throws MemcachedException, InterruptedException,
				TimeoutException {
			// TODO Auto-generated method stub
			return null;
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
		public KeyIterator getKeyIterator(InetSocketAddress inetsocketaddress)
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
		public Counter getCounter(String s, long l) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Counter getCounter(String s) {
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
		public <T> T getAndTouch(String s, int i, long l)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T getAndTouch(String s, int i) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> Map<String, T> get(Collection<String> collection, long l,
				Transcoder<T> transcoder) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T get(String s, long l, Transcoder<T> transcoder)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> Map<String, T> get(Collection<String> collection, long l)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> Map<String, T> get(Collection<String> collection,
				Transcoder<T> transcoder) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T get(String s, Transcoder<T> transcoder)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T get(String s, long l) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> Map<String, T> get(Collection<String> collection)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T get(String s) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void flushAllWithNoReply(InetSocketAddress inetsocketaddress,
				int i) throws MemcachedException, InterruptedException {
			// TODO Auto-generated method stub

		}

		@Override
		public void flushAllWithNoReply(int i) throws InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub

		}

		@Override
		public void flushAllWithNoReply(InetSocketAddress inetsocketaddress)
				throws MemcachedException, InterruptedException {
			// TODO Auto-generated method stub

		}

		@Override
		public void flushAllWithNoReply() throws InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub

		}

		@Override
		public void flushAll(InetSocketAddress inetsocketaddress, long l, int i)
				throws MemcachedException, InterruptedException,
				TimeoutException {
			// TODO Auto-generated method stub

		}

		@Override
		public void flushAll(int i, long l) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub

		}

		@Override
		public void flushAll(InetSocketAddress inetsocketaddress, long l)
				throws MemcachedException, InterruptedException,
				TimeoutException {
			// TODO Auto-generated method stub

		}

		@Override
		public void flushAll(String s) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub

		}

		@Override
		public void flushAll(InetSocketAddress inetsocketaddress)
				throws MemcachedException, InterruptedException,
				TimeoutException {
			// TODO Auto-generated method stub

		}

		@Override
		public void flushAll(long l) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub

		}

		@Override
		public void flushAll() throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub

		}

		@Override
		public void deleteWithNoReply(String s, int i)
				throws InterruptedException, MemcachedException {
			// TODO Auto-generated method stub

		}

		@Override
		public void deleteWithNoReply(String s) throws InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean delete(String s, long l) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean delete(String s, int i) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean delete(String s) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void decrWithNoReply(String s, long l)
				throws InterruptedException, MemcachedException {
			// TODO Auto-generated method stub

		}

		@Override
		public long decr(String s, long l, long l1, long l2, int i)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long decr(String s, long l, long l1, long l2)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long decr(String s, long l, long l1) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long decr(String s, long l) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public <T> void casWithNoReply(String s, int i,
				GetsResponse<T> getsresponse, CASOperation<T> casoperation)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub

		}

		@Override
		public <T> void casWithNoReply(String s, int i,
				CASOperation<T> casoperation) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub

		}

		@Override
		public <T> void casWithNoReply(String s, GetsResponse<T> getsresponse,
				CASOperation<T> casoperation) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub

		}

		@Override
		public <T> void casWithNoReply(String s, CASOperation<T> casoperation)
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
		public <T> boolean cas(String s, int i, GetsResponse<T> getsresponse,
				CASOperation<T> casoperation, Transcoder<T> transcoder)
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
		public boolean cas(String s, int i, Object obj, long l, long l1)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public <T> boolean cas(String s, int i, GetsResponse<T> getsresponse,
				CASOperation<T> casoperation) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public <T> boolean cas(String s, int i, CASOperation<T> casoperation,
				Transcoder<T> transcoder) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean cas(String s, int i, Object obj, long l)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public <T> boolean cas(String s, int i, CASOperation<T> casoperation)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public <T> boolean cas(String s, GetsResponse<T> getsresponse,
				CASOperation<T> casoperation) throws TimeoutException,
				InterruptedException, MemcachedException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public <T> boolean cas(String s, CASOperation<T> casoperation)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void appendWithNoReply(String s, Object obj)
				throws InterruptedException, MemcachedException {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean append(String s, Object obj, long l)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean append(String s, Object obj) throws TimeoutException,
				InterruptedException, MemcachedException {
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
		public void addWithNoReply(String s, int i, Object obj)
				throws InterruptedException, MemcachedException {
			// TODO Auto-generated method stub

		}

		@Override
		public void addStateListener(
				MemcachedClientStateListener memcachedclientstatelistener) {
			// TODO Auto-generated method stub

		}

		@Override
		public void addServer(String s, int i, int j) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void addServer(InetSocketAddress inetsocketaddress, int i)
				throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void addServer(String s, int i) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void addServer(String s) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void addServer(InetSocketAddress inetsocketaddress)
				throws IOException {
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
		public <T> boolean add(String arg0, int arg1, T arg2, Transcoder<T> arg3)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean add(String s, int i, Object obj, long l)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean add(String s, int i, Object obj)
				throws TimeoutException, InterruptedException,
				MemcachedException {
			// TODO Auto-generated method stub
			return false;
		}
	}

	@Before
	public void setUp() {
		PowerMock.mockStatic(SecurityUtils.class);

		memcachedClient = new TestMemcachedClient();
		filter = new AbstractMemcachingFilter() {

			@Override
			protected void setResponseHeaders(
					final HttpServletResponse httpResponse) {
				// TODO Auto-generated method stub

			}

			@Override
			protected boolean isCachingAllowedForRequest(
					final HttpServletRequest request) {

				return true;
			}
		};
		cacheConfig = new CacheConfig();
		cacheConfig.setCacheEnabled(true);
		cacheConfig.setCacheHost("localhost");
		cacheConfig.setCachePort(1234);
		cacheConfig.getMemcachedClient();
		filter.setCacheConfig(cacheConfig);

		Whitebox.setInternalState(cacheConfig, memcachedClient);
	}

	@Test
	public void testInit() throws ServletException {
		filter.init(new MockFilterConfig());
		// TODO: add asserts
	}

	@Test
	public void testDestroy() {
		filter.destroy();

		MemcachedClient testMemcachedClient = new TestMemcachedClient() {
			@Override
			public void shutdown() throws IOException {
				throw new IOException(
						"test I/O exception during memcached shutdown...");

			}
		};
		Whitebox.setInternalState(cacheConfig, testMemcachedClient);
		filter.destroy();

	}

	@Test
	public void testGetConfig() {
		AbstractMemcachingFilter abstractFilter = new AbstractMemcachingFilter() {

			@Override
			protected void setResponseHeaders(
					final HttpServletResponse httpResponse) {
				// TODO Auto-generated method stub

			}

			@Override
			protected boolean isCachingAllowedForRequest(
					final HttpServletRequest request) {

				return true;
			}
		};
		CacheConfig config = new CacheConfig();
		config.setCacheEnabled(true);
		config.setCacheHost("localhost");
		config.setCachePort(1234);
		config.getMemcachedClient();
		abstractFilter.setCacheConfig(config);

		Assert.assertEquals(config, abstractFilter.getCacheConfig());

	}

	@Test
	public void testPrivateGetEncodedKey() throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			SecurityException, NoSuchMethodException {

		Method method = AbstractMemcachingFilter.class.getDeclaredMethod(
				"getEncodedKey", String.class, String.class, String.class);
		method.setAccessible(true);
		String nullKey = (String) method.invoke(filter, "key",
				"bad digest method", "UTF-8");
		Assert.assertNull(nullKey);
		nullKey = (String) method.invoke(filter, "key", "MD5", "bad encoding");
		Assert.assertNull(nullKey);

	}

	@Test
	public void testDoFilter() throws IOException, ServletException {
		final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

		mockHttpServletRequest.addParameter("nocache", "no cache");
		mockHttpServletRequest.addParameter("referer", "some referer");

		EasyMock.expect(SecurityUtils.getUserName()).andReturn("gpundi");
		EasyMock.expect(SecurityUtils.getUserName()).andReturn("gpundi");
		EasyMock.expect(SecurityUtils.getUserName()).andReturn("gpundi");
		EasyMock.expect(SecurityUtils.getUserName()).andReturn("gpundi");
		EasyMock.expect(SecurityUtils.getUserName()).andReturn("gpundi");

		PowerMock.replayAll();

		filter.doFilter(mockHttpServletRequest, mockHttpServletResponse,
				new MockFilterChain());

		memcachedClient = new TestMemcachedClient() {
			@SuppressWarnings("unchecked")
			@Override
			public <T> T get(String s) {
				return (T) "fake cache value";
			}
		};
		Whitebox.setInternalState(cacheConfig, memcachedClient);
		filter.doFilter(mockHttpServletRequest, mockHttpServletResponse,
				new MockFilterChain());

		memcachedClient = new TestMemcachedClient() {
			@Override
			public boolean set(String s, int i, Object obj)
					throws MemcachedException {
				throw new MemcachedException();

			}
		};
		Whitebox.setInternalState(cacheConfig, memcachedClient);
		filter.doFilter(mockHttpServletRequest, mockHttpServletResponse,
				new MockFilterChain());

		memcachedClient = new TestMemcachedClient() {
			@Override
			public <T> T get(String s) throws MemcachedException {
				throw new MemcachedException();
			}
		};
		Whitebox.setInternalState(cacheConfig, memcachedClient);
		filter.doFilter(mockHttpServletRequest, mockHttpServletResponse,
				new MockFilterChain());

		cacheConfig.setCacheEnabled(false);
		filter.doFilter(mockHttpServletRequest, mockHttpServletResponse,
				new MockFilterChain());
		// TODO: add asserts
	}

	@Test
	public void testSetResponseHeaders() {
		final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		filter.setResponseHeaders(mockHttpServletResponse);
		// TODO: add asserts
	}

	@Test
	public void testIsCachingAllowedForRequest() {
		final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
		filter.isCachingAllowedForRequest(mockHttpServletRequest);
		// TODO: add asserts
	}

}
