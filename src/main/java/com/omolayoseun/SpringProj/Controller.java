package com.omolayoseun.SpringProj;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zwobble.mammoth.DocumentConverter;
import org.zwobble.mammoth.Result;

import java.io.File;
import java.io.IOException;

@RestController
public class Controller {


    @GetMapping("/index")
    public String hello(){
        return "this test actually works";
    }


    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file){
        String fileDir = "C:\\Users\\HP 4530s i5\\IdeaProjects\\SpringProj\\data\\" + file.getOriginalFilename();
        String htmlText;
        try {
            file.transferTo(new File(fileDir));
            htmlText = convertToHtml(fileDir);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().toString();
        }

        return htmlText;
    }

    private String convertToHtml(String fileDir) throws IOException {
        File file = new File(fileDir);

        DocumentConverter converter = new DocumentConverter();
        Result<String> result = converter.convertToHtml(file);

        fileDir =  result.getValue();

        if(file.exists())
            file.delete();

        return fileDir;
    }
}
