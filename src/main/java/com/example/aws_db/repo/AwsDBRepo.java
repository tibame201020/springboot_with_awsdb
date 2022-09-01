package com.example.aws_db.repo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.example.aws_db.domain.AWSDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class AwsDBRepo {
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public AWSDb save(AWSDb awsDb) {
        dynamoDBMapper.save(awsDb);
        return awsDb;
    }

    public AWSDb getById(String id) {
        return dynamoDBMapper.load(AWSDb.class, id);
    }

    public void delete(String id) {
        AWSDb awsDb = dynamoDBMapper.load(AWSDb.class, id);
        dynamoDBMapper.delete(awsDb);

    }

    public String update(String id, AWSDb awsDb) {
        dynamoDBMapper.save(awsDb,
                new DynamoDBSaveExpression()
                        .withExpectedEntry("side_project_db",
                                new ExpectedAttributeValue(
                                        new AttributeValue().withS(id)
                                )));
        return id;
    }

    public PaginatedScanList<AWSDb> findAll() {
        return dynamoDBMapper.scan(AWSDb.class, new DynamoDBScanExpression());
    }

    public PaginatedScanList<AWSDb> query(Map<String, AttributeValue> param, String query) {
        DynamoDBScanExpression expression =
                new DynamoDBScanExpression()
                        .withFilterExpression(query)
                        .withExpressionAttributeValues(param);


        return dynamoDBMapper.scan(AWSDb.class, expression);
    }
}
