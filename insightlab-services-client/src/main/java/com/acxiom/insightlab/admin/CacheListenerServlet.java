package com.acxiom.insightlab.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeoutException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.acxiom.insightlab.api.memcache.util.CacheConfig;

/**
 * Servlet implementation class CacheListenerServlet
 */
public class CacheListenerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1223576879520426691L;

	/**
	 * Slf4j logger.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CacheListenerServlet.class);

	/**
	 * CacheConfig object stores cache configurations from property files.
	 */
	@Autowired
	private CacheConfig cacheConfig;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CacheListenerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init()
	 */
	public void init() throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				getServletContext());
	}

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		MemcachedClient memcachedClient = cacheConfig.getMemcachedClient();
		if (memcachedClient != null) {
			try {
				if (request.getParameter("flush_all") != null) {
					memcachedClient.flushAll();
					response.getWriter()
							.println(
									"<h3>Success. Command 'flush_all' has been execuded.</h3>");
				} else if (request.getParameter("stats") != null) {

					Map<InetSocketAddress, Map<String, String>> stats = memcachedClient
							.getStats();
					PrintWriter responseWriter = response.getWriter();
					responseWriter
							.print("<html><head><title>Cache List</title></head><body>");
					for (Entry<InetSocketAddress, Map<String, String>> stat : stats
							.entrySet()) {
						responseWriter.println(MessageFormat.format(
								"<h2>{0}</h2>", stat.getKey().toString()));
						responseWriter.println("<ol>");
						for (Entry<String, String> statValue : stat.getValue()
								.entrySet()) {
							String statValueString = MessageFormat.format(
									"<li>{0}</li>", statValue.toString());
							responseWriter.println(statValueString);
						}
						responseWriter.println("</ol>");
					}
					responseWriter.print("</body></html>");
				}
			} catch (Exception e) {
				response.getWriter().println(e.getMessage());
			}
		} else {
			response.getWriter().print("Memcached is off.");
		}
	}

}
