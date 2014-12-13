package com.acxiom.insightlab.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.memcache.util.CacheConfig;
import com.acxiom.insightlab.api.utils.UrlUtil;
import com.acxiom.insightlab.service.SecurityUtils;
import com.acxiom.insightlab.service.ServletUtils;

/**
 * Abstract filter that uses memcached client to get/store data in memory cache.
 * Patterns used: template method - for setting different response types.
 * 
 * @author dmytro.plekhotkin
 * 
 */
public abstract class AbstractMemcachingFilter implements Filter {

	private static final String NOCACHE = "nocache";

	private static final String REFERER = "referer";

	private static final String UTF_8 = "UTF-8";

	private static final String KEY_PREPARATION_ERROR = "Error within MD5 key preparation";
	
	private static final String PUT_TO_CACHE_ERROR = "Error within put to cache for key: ";

	/**
	 * Slf4j logger.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractMemcachingFilter.class);
	
	/**
	 * CacheConfig object stores cache configurations from property files.
	 */
	@Autowired
	private CacheConfig cacheConfig;

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(final FilterConfig fConfig) throws ServletException {
		LOGGER.debug("Memcached filter insitializing...");
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		MemcachedClient memcachedClient = cacheConfig.getMemcachedClient();
		if (memcachedClient != null && memcachedClient.isShutdown()) {
			try {
				memcachedClient.shutdown();
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
			}
		}
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(final ServletRequest request,
			final ServletResponse response, final FilterChain filterChain)
			throws IOException, ServletException {
		if (cacheConfig.isCacheEnabled()
				&& isCachingAllowedForRequest((HttpServletRequest) request)) {
			doCaching(request, response, filterChain);
		} else {
			filterChain.doFilter(request, response);
		}
	}

	/**
	 * Storing and retrieving data from cache.
	 * 
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws IOException
	 * @throws ServletException
	 */
	private void doCaching(final ServletRequest request,
			final ServletResponse response, final FilterChain filterChain)
			throws IOException, ServletException {
		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final HttpServletResponse httpResponse = (HttpServletResponse) response;

		final String key = prepareCacheKey(httpRequest);

		try {
			MemcachedClient memcachedClient = cacheConfig.getMemcachedClient();
			String cacheValue = memcachedClient.get(key);
			if (cacheValue == null) {

				LOGGER.info("Cache miss for key " + key);

				final CachingResponseWrapper responseWrap = new CachingResponseWrapper(
						httpResponse);
				filterChain.doFilter(httpRequest, responseWrap);

				final CachingResponseWriter responseWriter = (CachingResponseWriter) responseWrap
						.getWriter();

				cacheValue = responseWriter.getCollectedResponse();

				LOGGER.info(cacheValue);
				cache(key, cacheValue, cacheConfig.getCacheLive());

			} else {
				LOGGER.info("Cache hit for key " + key);
				setResponseHeaders(httpResponse);
				httpResponse.getWriter().println(cacheValue);
			}
		} catch (Exception ex) {
			LOGGER.info("Cache functionality skipped due to exception", ex);
			// attempting call to actual target
			filterChain.doFilter(request, response);
		}
	}

	private String prepareCacheKey(final HttpServletRequest httpRequest) {
		final String path = httpRequest.getPathInfo();

		String key = "";

		try {
			final JSONObject params = ServletUtils.getParams(httpRequest);
			final String[] names =JSONObject.getNames(params);
			if (names != null) {
				final JSONObject opts = new JSONObject(params,
						names);
				LOGGER.info("params in key preparing: " + opts.toString());

				if (opts.has(NOCACHE)) {
					opts.remove(NOCACHE);
				}
				if (opts.has(REFERER)) {
					opts.remove(REFERER);
				}
			}
			

			final String userName = SecurityUtils.getUserName();
			params.put("username", userName);

			key = path + UrlUtil.jsonParamsToQueryString(params);

		} catch (JSONException e) {
			LOGGER.error(KEY_PREPARATION_ERROR, e);
		} catch (BaseDataApiException e) {
			LOGGER.error(KEY_PREPARATION_ERROR, e);
		}

		key = getEncodedKey(key, "MD5", UTF_8);

		return key;
	}

	private String getEncodedKey(String key, final String digestAlgorithm, final String encoding) {
		String encodedKey = null;
		try {
			final MessageDigest md = MessageDigest.getInstance(digestAlgorithm);
			encodedKey = new String(md.digest(key.getBytes(encoding)), encoding);
			encodedKey = new String(Base64.encodeBase64(encodedKey.getBytes(encoding)));
		} catch (NoSuchAlgorithmException e) {
			encodedKey  = null;
			LOGGER.error(KEY_PREPARATION_ERROR, e);
		} catch (UnsupportedEncodingException e) {
			encodedKey  = null;
			LOGGER.error(KEY_PREPARATION_ERROR, e);
		}
		return encodedKey;
	}

	private boolean validContent(final String content) {
		return content != null
				&& content.toLowerCase(Locale.ENGLISH).indexOf("error") < 0;
	}

	private void cache(final String key, final String content,
			final int cacheLive) {

		if (validContent(content)) {
			try {
				final MemcachedClient memcachedClient = cacheConfig
						.getMemcachedClient();
				memcachedClient.set(key, cacheLive, content);
			} catch (Exception e) {
				LOGGER.error(PUT_TO_CACHE_ERROR + key, e);
			}
			LOGGER.debug("Stored in memcached [" + key + "]: " + content);

		} else {
			LOGGER.debug("Will not cache error or empty content for key=" + key
					+ " content: " + content);
		}
	}

	/**
	 * Sets response headers (e.g.
	 * httpResponse.setContentType("application/json")).
	 * 
	 * @param httpResponse
	 *            HttpServletResponse
	 */
	protected abstract void setResponseHeaders(
			final HttpServletResponse httpResponse);

	/**
	 * Returns true if requested path has to be cached.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return boolean value that says if caching allowed for the request
	 */
	protected abstract boolean isCachingAllowedForRequest(
			HttpServletRequest request);

	public CacheConfig getCacheConfig() {
		return cacheConfig;
	}

	public void setCacheConfig(final CacheConfig cacheConfig) {
		this.cacheConfig = cacheConfig;
	}
}
