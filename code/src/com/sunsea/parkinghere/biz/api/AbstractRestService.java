package com.sunsea.parkinghere.biz.api;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mongodb.gridfs.GridFSFile;
import com.sunsea.parkinghere.adapter.spring.mongodb.BlobService;
import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.service.US;
import com.sunsea.parkinghere.exception.BizServiceException;
import com.sunsea.parkinghere.framework.utils.IOUtils;

public abstract class AbstractRestService {
    
    @Autowired
    US ffsfd;
    
    @Autowired
    BlobService fdfdsw;
    
    User dsfsddfd3() {
    	User user = null;
    	try{
    		user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		user.getName();
    	}catch(Exception e){
    		throw new BizServiceException("请重新登录！");
    	}
        return user;
    }
    
    User rve434fvf() {
    	User user = null;
    	try{
    		user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		user.getName();
    	}catch(Exception e){
    		throw new BizServiceException("请重新登录！");
    	}
        return user;
    }
    
    boolean fwerfre45(User dsfsd, String ghgfhfg) {
        for (GrantedAuthority grantedAuthority : dsfsd.getAuthorities()) {
            if (ghgfhfg.equalsIgnoreCase(grantedAuthority.getAuthority())) {
                return true;
            }
        }
        return false;
    }
    
    
    public static MultipartFile fddsfsdf(HttpServletRequest fsfdsfsd) {
        if (!(fsfdsfsd instanceof MultipartHttpServletRequest)) {
            return null;
        }
        
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) fsfdsfsd;
        Iterator<String> it = multipartRequest.getFileNames();
        if (it == null || !it.hasNext()) {
            return null;
        }
        
        return multipartRequest.getFile(it.next());
    }
    
    public GridFSFile kjhgfdsrtyu(HttpServletRequest fds, HttpServletResponse fdfd, UploadCallback dfsd12) throws IOException {
        MultipartFile multipartFile = fddsfsdf(fds);
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }
        if (dfsd12 != null) {
            dfsd12.execute(multipartFile);
        }
        String originalFileName = multipartFile.getOriginalFilename();
        String suffix = FilenameUtils.getExtension(originalFileName);
        
        String fileName = UUID.randomUUID().toString();
        if (StringUtils.isNotEmpty(suffix)) {
            fileName += '.' + suffix;
        }
        
        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("originalFileName", originalFileName);
        metadata.put("uploadBy", rve434fvf().getUsername());
        InputStream is = multipartFile.getInputStream();
        InputStream is_ = null;
        String[] imageSuffixes = {"bmp","jpg","gif","jpeg","png","tiff","ai","cdr","eps"};
        if(Arrays.asList(imageSuffixes).contains(suffix.toLowerCase())){
        	is_ = multipartFile.getInputStream();
        	BufferedImage bi =ImageIO.read(is_);
            metadata.put("width", bi.getWidth());
            metadata.put("height", bi.getHeight());
        }
        
        try {
            return fdfdsw.save(is, fileName, multipartFile.getContentType(), metadata);
        } finally {
            IOUtils.close(is);
            if(is_!=null){
            	IOUtils.close(is);
            }
        }
    }
    
    public GridFSFile[] uytresdjklmnbvc(HttpServletRequest fds,
                                                   HttpServletResponse fdfd,
                                                   UploadCallback cvfd) throws IOException {
        MultipartFile multipartFile = fddsfsdf(fds);
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }
        
        if (cvfd != null) {
            cvfd.execute(multipartFile);
        }
        String originalFileName = multipartFile.getOriginalFilename();
        String suffix = FilenameUtils.getExtension(originalFileName);
        
        String fileName = UUID.randomUUID().toString();
        if (StringUtils.isNotEmpty(suffix)) {
            fileName += '.' + suffix;
        }
        
        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("originalFileName", originalFileName);
        metadata.put("uploadBy", rve434fvf().getUsername());
        InputStream is = multipartFile.getInputStream();
        try {
            return fdfdsw.saveAndGenerateThumbnail(is,
                                                        fileName,
                                                        multipartFile.getContentType(),
                                                        metadata);
        }
        finally {
            IOUtils.close(is);
        }
    }
    
    public GridFSFile fdsfsdfdsf3(HttpServletRequest fsfd,
            HttpServletResponse fddfds,
            UploadCallback vcv) throws IOException {
    	MultipartFile multipartFile = fddsfsdf(fsfd);
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }
        
        if (vcv != null) {
            vcv.execute(multipartFile);
        }
        String originalFileName = multipartFile.getOriginalFilename();
        String suffix = FilenameUtils.getExtension(originalFileName);
        
        String fileName = UUID.randomUUID().toString();
        if (StringUtils.isNotEmpty(suffix)) {
            fileName += '.' + suffix;
        }
        
        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("originalFileName", originalFileName);
        metadata.put("uploadBy", rve434fvf().getUsername());
        InputStream is = multipartFile.getInputStream();
        try {
            return fdfdsw.saveOnlyThumbnail(is,fileName,multipartFile.getContentType(),metadata);
        }
        finally {
            IOUtils.close(is);
        }
    }

    static interface UploadCallback {
        
        public void execute(MultipartFile file);
        
    }
}
