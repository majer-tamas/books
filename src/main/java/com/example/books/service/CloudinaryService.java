package com.example.books.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class CloudinaryService {

    Cloudinary cloudinary;

    private List<String> allUrls = new ArrayList<>();

    private Map<String, String> valuesMap = new HashMap<>();

    public CloudinaryService() {
        valuesMap.put("cloud_name", "dexrz7o95");
        valuesMap.put("api_key", "729238632446188");
        valuesMap.put("api_secret", "dLhfAMLIgEoR2Ls1CO1AZ84xbmo");
        cloudinary = new Cloudinary(valuesMap);
    }

    public Map upload(MultipartFile multipartFile) throws IOException {
        File file = convert(multipartFile);
        Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        file.delete();
        return result;
    }

    public Map delete(String id) throws IOException {
        Map result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        return result;
    }

    public List<String> getAllImageUrls(String nextCursor) throws Exception {
        ApiResponse results;
        if (nextCursor.length() < 6) {
            allUrls.clear();
            results = cloudinary.api().resources(ObjectUtils.asMap("max_results", 100));
        } else {
            results = cloudinary.api().resources(ObjectUtils.asMap("next_cursor", nextCursor, "max_results", 100));
        }
        String[] lines = convertApiResponseToStringArray(results);
        for (String line : lines) {
            if (line.startsWith("secure_url=")) {
                allUrls.add(line.substring(11).replace(",", ""));
            }
        }
        if (lines[lines.length - 1].startsWith("next_cursor")) {
            nextCursor = lines[lines.length - 1].substring(12).replace("}", "");
            getAllImageUrls(nextCursor);
        }
        return allUrls;
    }

    private String[] convertApiResponseToStringArray(ApiResponse results) {
        List<Object> list = new ArrayList<>();
        list.add(results);
        String rawData = list.get(0).toString();
        return rawData.split(" ");

    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }

}
