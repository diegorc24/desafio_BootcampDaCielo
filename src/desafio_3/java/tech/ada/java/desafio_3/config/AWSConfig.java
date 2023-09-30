package tech.ada.java.desafio_3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class AWSConfig {

    @Bean
    public SqsClient sqsClient() {
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(
                "AKIAVQIX5X65FRZU2OWH",
                "42Q2gb5n+o5jx6iUvhWSfjTY+xUa/P3Wvn0JMQqR"
        );

        return SqsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .build();
    }
}
