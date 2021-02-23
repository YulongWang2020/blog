package com.wyl.blog.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileUtils {

    private Environment environment;

    public FileUtils(Environment environment) {
        this.environment = environment;
    }

    public String upload(MultipartFile file, String fileName) throws FileNotFoundException {

//        String path = ClassUtils.getDefaultClassLoader().getResource("static").getPath() + File.separator+ "images" +File.separator +"upload";
        String path = environment.getRequiredProperty("upload.path");
        System.out.println(path + "image path---------------------------------");

        fileName = UUID.randomUUID().toString() +"." + FilenameUtils.getExtension(fileName);

        String realPath = "/" + path + File.separator + fileName;

        File dest = new File(realPath);
        System.out.println(path + fileName + dest.toString());
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }

        try {
            file.transferTo(dest);
            return fileName;
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getStaticPath() throws FileNotFoundException {
        String staticPath = ResourceUtils.getURL("static").getPath();
        return staticPath;
    }
}

