package com.ibrahim.opensound.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ibrahim.opensound.models.Sound;
import com.ibrahim.opensound.repository.H2Database;
import com.ibrahim.opensound.services.filestorage.FileStorage;

import java.util.List;

@Service
public class SoundService {

    @Autowired
    FileStorage fileStorage;

    @Autowired
    H2Database database;

    public Boolean saveSound(MultipartFile image, MultipartFile audio, String name, String description) {
        // Save Sound to FileStorage
        Map<String, String> savedFilesInfo = fileStorage.saveFiles(image, audio);
        if (savedFilesInfo == null) {
            return false;
        }

        // Retrieve File Info
        String imageFileName = savedFilesInfo.get("imageFileName");
        String imageURL = savedFilesInfo.get("imageURL");
        String audioFileName = savedFilesInfo.get("audioFileName");
        String audioURL = savedFilesInfo.get("audioURL");

        // Build Sound Entity
        Sound newSound = Sound.builder()
                .soundName(name)
                .soundDescription(description)
                .soundImageName(imageFileName)
                .soundImageURL(imageURL)
                .soundAudioName(audioFileName)
                .soundAudioURL(audioURL)
                .build();

        // Save to DB
        database.save(newSound);
        return true;
    }

    public Boolean updateSound(Long key, MultipartFile image, MultipartFile audio, String name, String description) {

        // Save Sound to FileStorage
        Map<String, String> savedFilesInfo = fileStorage.saveFiles(image, audio);
        if (savedFilesInfo == null) {
            return false;
        }

        // Retrieve File Info
        String imageFileName = savedFilesInfo.get("imageFileName");
        String imageURL = savedFilesInfo.get("imageURL");
        String audioFileName = savedFilesInfo.get("audioFileName");
        String audioURL = savedFilesInfo.get("audioURL");

        // Get out-dated Sound entity
        Sound updatedSound = database.findById(key).get();
        updatedSound.setSoundName(name);
        updatedSound.setSoundDescription(description);
        updatedSound.setSoundImageName(imageFileName);
        updatedSound.setSoundImageURL(imageURL);
        updatedSound.setSoundAudioName(audioFileName);
        updatedSound.setSoundAudioURL(audioURL);

        // Save to DB
        database.save(updatedSound);
        return true;
    }

    public Boolean deleteSound(Long key) {
        // Get Sound to be deleted
        Sound toBeDeletedSound = getSoundById(key);
        String imageKey = toBeDeletedSound.getSoundImageName();
        String audioKey = toBeDeletedSound.getSoundAudioName();
        // Delete Sound Resources from file-storage
        List<String> filesToBeDeleted = new ArrayList<>();
        filesToBeDeleted.add(imageKey);
        filesToBeDeleted.add(audioKey);
        if (!fileStorage.deleteFiles(filesToBeDeleted)) {
            return false;
        }
        // Delete Sound from DB
        database.deleteById(key);
        return true;
    }

    public Sound getSoundById(Long key) {
        return database.findById(key).get();
    }

    public Iterable<Sound> getAllSounds() {
        List<Sound> list = (List<Sound>) database.findAll();
        Collections.reverse(list);
        return list;
    }
}
