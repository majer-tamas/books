package com.example.books.controller;

import com.example.books.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/cloudinary")
public class CloudinaryController {

    private CloudinaryService cloudinaryService;

    @Autowired
    public CloudinaryController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map> upload(@RequestParam MultipartFile multipartFile) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
        if (bufferedImage == null) {
            return new ResponseEntity(new String("Not valid"), HttpStatus.BAD_REQUEST);
        }
        Map result = cloudinaryService.upload(multipartFile);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map> delete(@PathVariable String id) throws IOException {
        Map result = cloudinaryService.delete(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/getAll/{nextCursor}")
    public ResponseEntity<?> getAllImageUrls(@PathVariable String nextCursor) throws Exception {
        return ResponseEntity.ok(cloudinaryService.getAllImageUrls(nextCursor));
    }

}
