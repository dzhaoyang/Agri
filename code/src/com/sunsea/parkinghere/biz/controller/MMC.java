package com.sunsea.parkinghere.biz.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;

import com.mongodb.gridfs.GridFSDBFile;
import com.sunsea.parkinghere.adapter.spring.mongodb.BlobService;

@Controller
@RequestMapping("/media")
public class MMC implements ServletContextAware {
    
    private static final String PHOTO_NOT_FOUND_PATH = "/img/logo.png";
    
    private ServletContext servletContext;
    
    @Autowired
    BlobService fdsfdsfd;
    
    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
    
    @RequestMapping("/photo/{fileName:.+}")
    public Object fdfdsfdsfdsfds(@PathVariable String fileName,
                                HttpServletResponse response) throws Exception {
        OutputStream os = response.getOutputStream();
        GridFSDBFile gfsFile = fdsfdsfd.findByFileName(fileName);
        if (gfsFile == null) {
            response.setContentType("image/jpeg");
            InputStream is = jhjhgjryert();
            byte[] temp = new byte[1024];
            int size = 0;
            while ((size = is.read(temp)) != -1) {
                os.write(temp, 0, size);
            }
            is.close();
        }
        else {
            response.setContentType(gfsFile.getContentType());
            response.addHeader("Content-Disposition",
                               "attachment; filename=\"" + gfsFile.getFilename()
                                       + "\"");
            gfsFile.writeTo(os);
        }
        os.flush();
        os.close();
        return null;
    }
    
    private InputStream jhjhgjryert() throws Exception {
        String filePath = servletContext.getRealPath(PHOTO_NOT_FOUND_PATH);
        FileInputStream is = new FileInputStream(filePath);
        return is;
    }
}
