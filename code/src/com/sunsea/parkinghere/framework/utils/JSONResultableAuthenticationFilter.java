package com.sunsea.parkinghere.framework.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.SaveContextOnUpdateOrErrorResponseWrapper;
import org.springframework.web.filter.OncePerRequestFilter;

public class JSONResultableAuthenticationFilter extends OncePerRequestFilter {
	private class HttpServletResponseProxy implements HttpServletResponse {
		private String location;
		private HttpServletResponse wrapped;

		public HttpServletResponseProxy(HttpServletResponse wrapped) {
			this.wrapped = wrapped;
		}

		@Override
		public String getCharacterEncoding() {
			return wrapped.getCharacterEncoding();
		}

		@Override
		public String getContentType() {
			return wrapped.getContentType();
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			return wrapped.getOutputStream();
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			return wrapped.getWriter();
		}

		@Override
		public void setCharacterEncoding(String charset) {
			wrapped.setCharacterEncoding(charset);
		}

		@Override
		public void setContentLength(int len) {
			wrapped.setContentLength(len);
		}

		@Override
		public void setContentType(String type) {
			wrapped.setContentType(type);
		}

		@Override
		public void setBufferSize(int size) {
			wrapped.setBufferSize(size);
		}

		@Override
		public int getBufferSize() {
			return wrapped.getBufferSize();
		}

		@Override
		public void flushBuffer() throws IOException {
			wrapped.flushBuffer();
		}

		@Override
		public void resetBuffer() {
			wrapped.resetBuffer();
		}

		@Override
		public boolean isCommitted() {
			return wrapped.isCommitted();
		}

		@Override
		public void reset() {
			wrapped.reset();
		}

		@Override
		public void setLocale(Locale loc) {
			wrapped.setLocale(loc);
		}

		@Override
		public Locale getLocale() {
			return wrapped.getLocale();
		}

		@Override
		public void addCookie(Cookie cookie) {
			wrapped.addCookie(cookie);
		}

		@Override
		public boolean containsHeader(String name) {
			return wrapped.containsHeader(name);
		}

		@Override
		public String encodeURL(String url) {
			return wrapped.encodeURL(url);
		}

		@Override
		public String encodeRedirectURL(String url) {
			return wrapped.encodeRedirectURL(url);
		}

		@Override
		public String encodeUrl(String url) {
			return wrapped.encodeUrl(url);
		}

		@Override
		public String encodeRedirectUrl(String url) {
			return wrapped.encodeRedirectUrl(url);
		}

		@Override
		public void sendError(int sc, String msg) throws IOException {
			wrapped.sendError(sc, msg);
		}

		@Override
		public void sendError(int sc) throws IOException {
			wrapped.sendError(sc);
		}

		@Override
		public void sendRedirect(String location) throws IOException {
			this.location = location;
		}

		@Override
		public void setDateHeader(String name, long date) {
			wrapped.setDateHeader(name, date);
		}

		@Override
		public void addDateHeader(String name, long date) {
			wrapped.addDateHeader(name, date);
		}

		@Override
		public void setHeader(String name, String value) {
			wrapped.setHeader(name, value);
		}

		@Override
		public void addHeader(String name, String value) {
			wrapped.addHeader(name, value);
		}

		@Override
		public void setIntHeader(String name, int value) {
			wrapped.setIntHeader(name, value);
		}

		@Override
		public void addIntHeader(String name, int value) {
			wrapped.addIntHeader(name, value);
		}

		@Override
		public void setStatus(int sc) {
			wrapped.setStatus(sc);
		}

		@Override
		public void setStatus(int sc, String sm) {
			wrapped.setStatus(sc, sm);
		}
	}

	private class HttpServletResponseWrapper extends SaveContextOnUpdateOrErrorResponseWrapper {

		public HttpServletResponseWrapper(HttpServletResponseProxy response, boolean disableUrlRewriting) {
			super(response, disableUrlRewriting);
		}

		@Override
		protected void saveContext(SecurityContext context) {
			// SaveContextOnUpdateOrErrorResponseWrapper
			// saveContextOnUpdateOrErrorResponseWrapper =
			// (SaveContextOnUpdateOrErrorResponseWrapper)((HttpServletResponseProxy)this.getResponse()).wrapped;
			// saveContextOnUpdateOrErrorResponseWrapper.saveContext(context);
		}
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getRequestURI().startsWith("/api/v2")) {
			HttpServletResponseProxy response2 = new HttpServletResponseProxy(response);
			filterChain.doFilter(request, new HttpServletResponseWrapper(response2, false));
			if (response2.location != null) {
				if (response2.location.indexOf("/login") > 0) {
					response.setContentType("application/json");
					response.setStatus(200);
					response.getWriter().print("{\"code\":403}");
					return;
				} else {
					response.sendRedirect(response2.location);
				}
			}
			return;
		}
		filterChain.doFilter(request, response);
	}

}
