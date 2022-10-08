package com.ibrahim.opensound.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "sounds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String soundName;
    private String soundDescription;
    private String soundAudioName;
    private String soundImageName;
    private String soundAudioURL;
    private String soundImageURL;
}
