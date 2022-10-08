package com.ibrahim.opensound.services.filestorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;

import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.MultiObjectDeleteException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.ibrahim.opensound.configuration.AmazonDetails;

@Service
public class S3FileStorage implements FileStorage {

    @Autowired
    private AmazonS3 amazonS3;

    @Override
    public Map<String, String> saveFiles(MultipartFile image, MultipartFile audio) {
        String audiofileName = new Date().toString() + "_" + audio.getOriginalFilename();
        String imagefileName = new Date().toString() + "_" + image.getOriginalFilename();

        try {
            ObjectMetadata audioMetadata = new ObjectMetadata();
            audioMetadata.setContentLength(audio.getSize());
            ObjectMetadata imageMetadata = new ObjectMetadata();
            imageMetadata.setContentLength(image.getSize());

            amazonS3.putObject(AmazonDetails.BUCKET_NAME.getValue(), audiofileName, audio.getInputStream(),
                    audioMetadata);
            amazonS3.putObject(AmazonDetails.BUCKET_NAME.getValue(), imagefileName, image.getInputStream(),
                    imageMetadata);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        String urlToAudio = amazonS3.getUrl(AmazonDetails.BUCKET_NAME.getValue(), audiofileName).toString();
        String urlToImage = amazonS3.getUrl(AmazonDetails.BUCKET_NAME.getValue(), imagefileName).toString();

        Map<String, String> fileInfoMap = new HashMap<>();
        fileInfoMap.put("imageFileName", imagefileName);
        fileInfoMap.put("imageURL", urlToImage);
        fileInfoMap.put("audioFileName", audiofileName);
        fileInfoMap.put("audioURL", urlToAudio);

        return fileInfoMap;
    }

    @Override
    public Boolean deleteFiles(List<String> files) {

        List<KeyVersion> keysToBeDeleted = new ArrayList<>();
        keysToBeDeleted.add(new KeyVersion(files.get(0)));
        keysToBeDeleted.add(new KeyVersion(files.get(1)));
        DeleteObjectsRequest deleteRequest = new DeleteObjectsRequest(AmazonDetails.BUCKET_NAME.getValue());
        deleteRequest.withKeys(keysToBeDeleted);

        try {
            amazonS3.deleteObjects(deleteRequest);
            return true;

        } catch (MultiObjectDeleteException e) {
            e.printStackTrace();
            return false;
        }
    }

}
