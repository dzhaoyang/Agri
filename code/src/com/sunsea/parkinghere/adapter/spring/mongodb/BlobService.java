package com.sunsea.parkinghere.adapter.spring.mongodb;

import java.io.InputStream;
import java.util.Map;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

public interface BlobService {
    
    /**
     * @param inputStream
     * @param fileName
     * @param contentType
     * @param metaData
     * @return
     */
    public GridFSFile save(InputStream inputStream,
                           String fileName,
                           String contentType,
                           Map<String, Object> metaData);
    
    /**
     * @param inputStream
     * @param fileName
     * @param contentType
     * @param metaData
     * @return
     */
    public GridFSFile[] saveAndGenerateThumbnail(InputStream inputStream,
                                                 String fileName,
                                                 String contentType,
                                                 Map<String, Object> metaData);
    /**
     * 只保存缩略图
     * @param inputStream
     * @param fileName
     * @param contentType
     * @param metaData
     * @return
     */
    public GridFSFile saveOnlyThumbnail(InputStream inputStream,
							            String fileName,
							            String contentType,
							            Map<String, Object> metaData);
    
    public GridFSDBFile findByFileName(String fileName);
    
    public GridFSDBFile findById(String id);
    
    public void removeByFileName(String fileName);
    
    public void removeById(String id);
}
