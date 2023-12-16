package com.example.fun;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public class FileSave {
    public static String saveFile(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty())
            return "";
        File target = new File("img");

        String originalFilename = file.getOriginalFilename();
        String fileName = originalFilename + LocalDateTime.now();

        File file1 = new File(target.getPath() + "/" + fileName);
        Files.write(Paths.get(file1.toURI()), file.getBytes(), StandardOpenOption.CREATE_NEW);
        return "src/main/main/resources/static/img/" + fileName;
    }
}
