package com.youtube.ecommerce.service;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileService {
        public String saveFile(MultipartFile file, String parentPath) throws Exception {
            if(file.isEmpty()){
                throw new Exception("Không tìm thấy fỉle");
            }
            try {
                String fileName = UUID.randomUUID() + "___" + file.getOriginalFilename();
                File directory = new File(parentPath);
                if(!directory.exists()){
                    directory.mkdirs();
                }
                File dest = new File(directory.getAbsolutePath() + File.separator + fileName);
                FileCopyUtils.copy(file.getBytes(), dest);
                return fileName;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


}
