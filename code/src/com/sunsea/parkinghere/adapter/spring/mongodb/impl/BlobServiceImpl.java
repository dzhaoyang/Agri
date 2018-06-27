package com.sunsea.parkinghere.adapter.spring.mongodb.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.sunsea.parkinghere.adapter.spring.mongodb.BlobService;
import com.sunsea.parkinghere.framework.utils.IOUtils;

@Service
public class BlobServiceImpl implements BlobService, InitializingBean {
    
    private static final Log logger = LogFactory.getLog(BlobServiceImpl.class);
    
    public static final String BLOB_COLLECTION_NAME = "Attachments";
    
    @Autowired
    private MongoDbFactory mongoDbFactory;
    
    private GridFS gridFS;
    
    @Override
    public GridFSFile save(InputStream inputStream,
                           String fileName,
                           String contentType,
                           Map<String, Object> metaData) {
        GridFSInputFile gfsInput = gridFS.createFile(inputStream);
        gfsInput.setFilename(fileName);
        gfsInput.setContentType(contentType);
        DBObject dbObject = new BasicDBObject(metaData);
        gfsInput.setMetaData(dbObject);
        gfsInput.save();
        return gfsInput;
    }
    
    @Override
    public GridFSFile[] saveAndGenerateThumbnail(InputStream inputStream,
                                                 String fileName,
                                                 String contentType,
                                                 Map<String, Object> metaData) {
        InputStream originalInputStream = saveOriginalImage(inputStream);
        GridFSFile gfsInput = this.save(originalInputStream,
                                        fileName,
                                        contentType,
                                        metaData);
        // generate thumbnail
        GridFSInputFile thumbnailGfsInput = null;
//<<<<<<< local
    	InputStream originalInputSteam = null;
    	try {
    		originalInputSteam = new FileInputStream(getOriginalFile());
    		File thumbnailFile  = getThumbnailFile();
			Thumbnails.of(originalInputSteam).size(400, 600).toFile(thumbnailFile);
			InputStream  thumbnailInputStream = new FileInputStream(thumbnailFile);
			thumbnailGfsInput = gridFS.createFile(thumbnailInputStream);
			thumbnailGfsInput.setFilename(fileName.substring(0, fileName.lastIndexOf(".")) + "_s.jpg");
			thumbnailGfsInput.setContentType(contentType);
			DBObject dbObject = new BasicDBObject(metaData);
			thumbnailGfsInput.setMetaData(dbObject);
			thumbnailGfsInput.save();
			
		} catch (IOException ioe) {
			logger.error("fileName:" + fileName + ", generate tumbnail error:", ioe);
		}finally{
			IOUtils.close(originalInputSteam);
		}
        return new GridFSFile[]{gfsInput, thumbnailGfsInput};
//=======
//        InputStream originalInputSteam = null;
//        try {
//            originalInputSteam = new FileInputStream(getOriginalFile());
//            File thumbnailFile = getThumbnailFile();
//            Thumbnails.of(originalInputSteam)
//                      .size(200, 300)
//                      .toFile(thumbnailFile);
//            InputStream thumbnailInputStream = new FileInputStream(thumbnailFile);
//            thumbnailGfsInput = gridFS.createFile(thumbnailInputStream);
//            thumbnailGfsInput.setFilename(fileName.substring(0,
//                                                             fileName.lastIndexOf(".")) + "_s.jpg");
//            thumbnailGfsInput.setContentType(contentType);
//            DBObject dbObject = new BasicDBObject(metaData);
//            thumbnailGfsInput.setMetaData(dbObject);
//            thumbnailGfsInput.save();
//            
//        }
//        catch (IOException ioe) {
//            logger.error("fileName:" + fileName + ", generate tumbnail error:",
//                         ioe);
//        }
//        finally {
//            IOUtils.close(originalInputSteam);
//        }
//        return new GridFSFile[] { gfsInput, thumbnailGfsInput };
//>>>>>>> other
    }
    
    public GridFSFile saveOnlyThumbnail(InputStream inputStream,
							            String fileName,
							            String contentType,
							            Map<String, Object> metaData){
    	GridFSInputFile thumbnailGfsInput = null;
    	InputStream originalInputSteam = null;
    	try {
    		originalInputSteam = new FileInputStream(getOriginalFile());
    		File thumbnailFile  = getThumbnailFile();
			Thumbnails.of(originalInputSteam).size(150, 300).toFile(thumbnailFile);
			InputStream  thumbnailInputStream = new FileInputStream(thumbnailFile);
			thumbnailGfsInput = gridFS.createFile(thumbnailInputStream);
			thumbnailGfsInput.setFilename(fileName.substring(0, fileName.lastIndexOf(".")) + ".jpg");
			thumbnailGfsInput.setContentType(contentType);
			DBObject dbObject = new BasicDBObject(metaData);
			thumbnailGfsInput.setMetaData(dbObject);
			thumbnailGfsInput.save();
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}finally{
			IOUtils.close(originalInputSteam);
		}
    	return thumbnailGfsInput;
    }
    
    private File getThumbnailFile() {
        String thumbnailFilePath = BlobServiceImpl.class.getResource("/")
                                                        .getFile();
        if (File.separator.equals("/")) {
            thumbnailFilePath = thumbnailFilePath + "/thumbnail/thumbnail.jpg";
        }
        else {
            thumbnailFilePath = thumbnailFilePath + "\\thumbnail\\thumbnail.jpg";
        }
        File thumbnailFile = new File(thumbnailFilePath);
        if (!thumbnailFile.getParentFile().exists()) {
            thumbnailFile.getParentFile().mkdirs();
        }
        return thumbnailFile;
    }
    
    private File getOriginalFile() {
        String originalPath = BlobServiceImpl.class.getResource("/").getFile();
        if (File.separator.equals("/")) {
            originalPath = originalPath + "/thumbnail/original.jpg";
        }
        else {
            originalPath = originalPath + "\\thumbnail\\original.jpg";
        }
        File originalFile = new File(originalPath);
        if (!originalFile.getParentFile().exists()) {
            originalFile.getParentFile().mkdirs();
        }
        return originalFile;
    }
    
    private InputStream saveOriginalImage(InputStream inputStream) {
        // inputStream cache;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        File originalFile = getOriginalFile();
        OutputStream os = null;
        try {
            os = new FileOutputStream(originalFile);
            byte[] b = new byte[4096];
            int len = 0;
            while ((len = inputStream.read(b)) != -1) {
                os.write(b, 0, len);
                baos.write(b, 0, len);
            }
            os.flush();
            baos.flush();
        }
        catch (Exception e) {
            logger.error("Save original image error:", e);
        }
        finally {
            IOUtils.close(os);
        }
        if (baos.size() > 0) {
            return new ByteArrayInputStream(baos.toByteArray());
        }
        // not read, so return original input stream.
        return inputStream;
    }
    
    public GridFSDBFile findByFileName(String fileName) {
        GridFSDBFile gfsFile = gridFS.findOne(fileName);
        return gfsFile;
    }
    
    public GridFSDBFile findById(String id) {
        GridFSDBFile gfsFile = gridFS.find(new ObjectId(id));
        return gfsFile;
    }
    
    public void removeByFileName(String fileName) {
        gridFS.remove(fileName);
    }
    
    public void removeById(String id) {
        gridFS.remove(new ObjectId(id));
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        DB db = mongoDbFactory.getDb();
        this.gridFS = new GridFS(db, BLOB_COLLECTION_NAME);
    }
    
}
