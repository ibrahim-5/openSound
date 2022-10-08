package com.ibrahim.opensound.services.filestorage;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {
    public Map<String, String> saveFiles(MultipartFile image, MultipartFile audio);

    public Boolean deleteFiles(List<String> files);
}
