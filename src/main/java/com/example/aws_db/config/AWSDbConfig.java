package com.example.aws_db.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.s3.model.Region;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSDbConfig {

    private static final Region AWS_REGION = Region.AP_Mumbai;
    // ap-south-1
    private static final String REGION = AWS_REGION.toAWSRegion().getName();
    private static final String END_POINT_PREFIX = "dynamodb";
    // dynamodb.ap-south-1.amazonaws.com
    private static final String SERVICE_END_POINT = AWS_REGION.toAWSRegion().getServiceEndpoint(END_POINT_PREFIX);

    private static final String PUB_KEY = "ur public access key";
    private static final String PRI_KEY = "ur private access key";

    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(buildAWSDB());
    }

    private AmazonDynamoDB buildAWSDB() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                SERVICE_END_POINT,
                                REGION
                        )
                )
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(
                                        PUB_KEY,
                                        PRI_KEY
                                )
                        )
                )
                .build();
    }

}
