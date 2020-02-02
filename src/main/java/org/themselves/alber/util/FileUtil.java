package org.themselves.alber.util;

import org.springframework.web.multipart.MultipartFile;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.StatusCode;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

public class FileUtil {

    public static Path directoryExistAndCreate(String url) {

        File directory = new File(url);

        try{
            if (!directory.exists()) {
                directory.mkdir();
            }
        } catch (Exception e){
            throw new CustomException(StatusCode.FILE_CREATE_ERROR);
        }

        return directory.toPath();
    }

    public static Path directoryDeleteAndCreate(String url) {

        File directory = new File(url);
        directory.delete();

        try{
            directory.mkdir();

        } catch (Exception e){
            throw new CustomException(StatusCode.FILE_CREATE_ERROR);
        }

        return directory.toPath();
    }

    public static Boolean fileEqImage(MultipartFile file) {
        int pos =  file.getResource().getFilename().lastIndexOf( "." );
        String ext = file.getResource().getFilename().substring( pos + 1 );

        if(ext.toLowerCase().equals("png")
                || ext.toLowerCase().equals("bmp")
                || ext.toLowerCase().equals("jpg")
                || ext.toLowerCase().equals("gif"))
            return Boolean.TRUE;
        else
            throw new CustomException(StatusCode.FILE_NOT_IMAGE_ERROR);
    }

    public static String ChangeFileName(String originFileName) {
        String ext = originFileName.substring(originFileName.lastIndexOf('.')); // 확장자 String saveFileName = getUuid() + ext;
        return getUuid() + ext;
    }

    //uuid생성
    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


}
