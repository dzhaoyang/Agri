package com.sunsea.parkinghere.framework.spring.mvc;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class LogMappingExceptionResolver extends SimpleMappingExceptionResolver {
	private static final Log logger = LogFactory.getLog(LogMappingExceptionResolver.class);

	protected void logException(Exception ex, HttpServletRequest request) {
		try {
			String referer = request.getHeader("referer");
			if (StringUtils.isNotEmpty(referer)) {
				request.setAttribute("refererPage", referer);
			}
			super.logException(ex, request);
		} finally {
			logger.error(ex.getClass().getName() + ":" + ex.getMessage(), ex);
		}
	}

}
