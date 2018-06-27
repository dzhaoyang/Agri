package com.sunsea.parkinghere.openapi;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.gridfs.GridFSFile;
import com.sunsea.parkinghere.adapter.spring.mongodb.BlobService;
import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.framework.utils.IOUtils;

public class NBizBaseFaceService {
	@Autowired
	protected BlobService blobService;

	
	protected User getCurrentPrincipal() {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return (User) obj;
	}

	protected boolean isHasAuthentication() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth != null && auth.isAuthenticated();
	}

	protected boolean isAnonymousUserAuthentication() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth != null && auth.getName().equals("anonymousUser");
	}

	protected boolean isCarOwnerUserAuthentication() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth != null && auth.getPrincipal() instanceof User;
	}

	public String doFileUpload(HttpServletRequest request, String paramName, UploadCallback callback) throws IOException {
		MultipartFile multipartFile = HttpRequestUtils.getMultipartFile(request, paramName);
		if (multipartFile == null || multipartFile.isEmpty()) {
			return null;
		}

		if (callback != null) {
			callback.execute(multipartFile);
		}

		String contentType = multipartFile.getContentType();
		String originalFileName = multipartFile.getOriginalFilename();
		String suffix = FilenameUtils.getExtension(originalFileName);
		if("image/*".equals(contentType) && StringUtils.isNotBlank(suffix)) {
			contentType = "image/" + suffix;
		}
		
		String fileName = UUID.randomUUID().toString();
		if (StringUtils.isNotEmpty(suffix)) {
			fileName += '.' + suffix;
		}

		Map<String, Object> metadata = new HashMap<String, Object>();
		metadata.put("originalFileName", originalFileName);
		metadata.put("uploadBy", getCurrentPrincipal().getUsername());
		InputStream is = multipartFile.getInputStream();
		try {
			GridFSFile result = blobService.save(is, fileName, contentType, metadata);
			return result.getId().toString();
		} finally {
			IOUtils.close(is);
		}
	}

	protected NBizSuccessResult toSuccessEmpty() {
		return new NBizSuccessResult(null);
	}

	protected NBizSuccessResult toSuccessResult(Object obj) {
		return new NBizSuccessResult(obj);
	}

	protected void assertSession(String fromSession, HttpSession session) {
		if (StringUtils.isNotBlank(fromSession)) {
			if (!fromSession.equals(session.getAttribute("token"))) {
				throw new NBizException(403, "非法的Session");
			}
		} else {
			throw new NBizException(300, "要求提供session的token");
		}
	}

	protected void assertTrue(boolean value, int code, String message) {
		if (!value)
			throw new NBizException(code, message);
	}

	protected void assertFalse(boolean value, int code, String message) {
		if (value)
			throw new NBizException(code, message);
	}

	protected void assertNull(Object obj, int code, String message) {
		if (obj != null)
			throw new NBizException(code, message);
	}

	protected void assertNotNull(Object obj, int code, String message) {
		if (obj == null)
			throw new NBizException(code, message);
	}

	protected void assertBlank(String str, int code, String message) {
		if (StringUtils.isNotBlank(str))
			throw new NBizException(code, message);
	}

	protected void assertNotBlank(String str, int code, String message) {
		if (StringUtils.isBlank(str))
			throw new NBizException(code, message);
	}

	@ExceptionHandler(NBizException.class)
	protected @ResponseBody NBizExceptionResult exception(NBizException ex) {
		return new NBizExceptionResult(ex.getCode(), ex.getBizMessage());
	}

	protected interface UploadCallback {

		public void execute(MultipartFile file);

	}
}
