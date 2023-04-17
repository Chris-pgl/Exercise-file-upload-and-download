package com.example.fileuploadanddownload.controllers;

import com.example.fileuploadanddownload.services.FileStorageService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;


    @GetMapping("/download")
    public @ResponseBody byte[] download(@RequestParam("fileName") String fileName, HttpServletResponse response) throws IOException{
        System.out.println("downloading " + fileName);
        String extension = FilenameUtils.getExtension(fileName);
        switch (extension){
            case "gif":
                response.setContentType(MediaType.IMAGE_GIF_VALUE);
                break;
            case "jpg":
            case "jpeg":
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                break;
            case "png":
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                break;
        }
        response.setHeader("Content-Disposition","attachment; filename=\""+fileName+"\"");
        return fileStorageService.download(fileName);
    }


    @PostMapping("/upload")
    public String upload(@RequestParam("file")MultipartFile file)throws Exception{
        return  fileStorageService.upload(file);
    }

}
