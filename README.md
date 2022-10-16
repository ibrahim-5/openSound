# openSound

Allows users of the service to upload their Sounds for the world to hear!

## Getting Started

Currently at the time of me writing this [16/10/2022] the web application is currently being hosted [here](http://opensoundspringboot-env.eba-8pc6wy45.eu-west-2.elasticbeanstalk.com)!

The web application can also be hosted locally, with some extra steps required.

### Hosting Locally

To host locally, you'll be required to provide valid Amazon S3 configurations details. These details should replace the current place-holders within *src/main/java/com/ibrahim/opensound/configuration/AmazonDetails.java*! An example of the class is shown below.

```java
package com.ibrahim.opensound.configuration;

public enum AmazonDetails {
    ACCESS_KEY("YOUR_ACCESS_KEY"),
    PRIVATE_KEY("YOUR_PRIVATE_KEY"),
    BUCKET_NAME("YOUR_BUCKET_NAME"),
    REGION("YOUR_REGION");

    private String value;

    AmazonDetails(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
``` 

Once these details are correctly entered, you can now start to host the web application locally on you machine!

### Running the application

To run the application you'll need to verify that you do indeed have a JDK version of at-least 11+. Once this is comfirmed all you need to do is execute one of the following executables present at the root of the project:

* Unix/Linux
```
./mvnw
```

* Windows (Un-tested)
```
Run the *mvnw.cmd* in the appropriate fashion.
```

## Releases

* Version 1.0
    * Initial Release.
    Allows users to upload, listen to, update and delete shared sounds. At this stage there is no concept of User verification, which means anyone can perform the affromentioned operations without clearance. This is something that I hope to introduce in future releases.
