package com.ibrahim.opensound.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonConfiguration {

    @Bean
    public AmazonS3 amazonS3() {

        final String accessKey = AmazonDetails.ACCESS_KEY.getValue();
        final String privateKey = AmazonDetails.PRIVATE_KEY.getValue();

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, privateKey);
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(AmazonDetails.REGION.getValue())
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}
