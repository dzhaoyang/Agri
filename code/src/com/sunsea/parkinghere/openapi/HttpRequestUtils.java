package com.sunsea.parkinghere.openapi;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public final class HttpRequestUtils {
	/**
	 * 取上传的文件
	 * 
	 * @param request
	 * @param 参数名
	 * @return
	 */
	public static MultipartFile getMultipartFile(HttpServletRequest request, String paramName) {
		if (!(request instanceof MultipartHttpServletRequest)) {
			return null;
		}
		return ((MultipartHttpServletRequest) request).getFile(paramName);
	}
}
