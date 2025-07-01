package com.gourav.YummiGoBackend.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryImageService {
    public Map upload(MultipartFile file) throws IOException;

    Map delete(String publicId) throws IOException;

}
