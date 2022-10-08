package com.ibrahim.opensound.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ibrahim.opensound.services.SoundService;

@Controller
@CrossOrigin("*")
public class SoundControllers {

    @Autowired
    private SoundService soundService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("sounds", soundService.getAllSounds());
        return "home";
    }

    @GetMapping("/upload")
    public String uploadPage() {
        return "upload";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePage(@PathVariable("id") String id, Model model) {
        model.addAttribute("sound", soundService.getSoundById(Long.parseLong(id)));
        return "update";
    }

    // Delete a song
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSound(@PathVariable("id") String id) {

        Boolean soundDeleted = soundService.deleteSound(Long.parseLong(id));

        if (!soundDeleted) {
            return new ResponseEntity<>("Sound was not able to be deleted!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Sound has been successfully been deleted!", HttpStatus.OK);
    }

    // Upload a song
    @PostMapping("/upload")
    public ResponseEntity<String> uploadSound(@RequestParam("image") MultipartFile image,
            @RequestParam("audio") MultipartFile audio, @RequestParam("name") String name,
            @RequestParam("description") String desc) {

        Boolean fileUploaded = soundService.saveSound(image, audio, name, desc);

        if (!fileUploaded) {
            return new ResponseEntity<>("Contents were not able to be uploaded!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Contents Sucessfully uploaded!", HttpStatus.OK);
    }

    // Update/Put a song
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSound(@PathVariable("id") String id, @RequestParam("image") MultipartFile image,
            @RequestParam("audio") MultipartFile audio, @RequestParam("name") String name,
            @RequestParam("description") String desc) {

        Boolean soundUpdated = soundService.updateSound(Long.parseLong(id), image, audio, name, desc);

        if (!soundUpdated) {
            return new ResponseEntity<>("Contents were not able to be updated!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Contents Sucessfully updated!", HttpStatus.OK);
    }

}
