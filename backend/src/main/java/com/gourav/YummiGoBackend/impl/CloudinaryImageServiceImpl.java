package com.gourav.YummiGoBackend.impl;

import com.cloudinary.Cloudinary;
import com.gourav.YummiGoBackend.service.CloudinaryImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryImageServiceImpl implements CloudinaryImageService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Map upload(MultipartFile file) {
        try {
            Map<String, Object> options = Map.of(
                    "folder", "YummiGo"
            );
            Map data = this.cloudinary.uploader().upload(file.getBytes(), options);
            return data;
        } catch (IOException e) {
            throw new RuntimeException("Image Upload fail !!", e);
        }
    }

    @Override
    public Map delete(String publicId) throws IOException {
        return cloudinary.uploader().destroy(publicId, new HashMap<>());
    }
}
