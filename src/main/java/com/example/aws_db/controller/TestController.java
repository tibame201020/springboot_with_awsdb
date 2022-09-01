package com.example.aws_db.controller;

import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.example.aws_db.domain.AWSDb;
import com.example.aws_db.repo.AwsDBRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private AwsDBRepo awsDBRepo;

    @RequestMapping("/create")
    public String testInsert() {
        AWSDb awsDb = new AWSDb();
        awsDb.setId("1233333333333333333333333");
        awsDb.setJsonData("testAutoDetectId");
        awsDBRepo.save(awsDb);

        return awsDb.getId();
    }

    @RequestMapping("/test")
    public AWSDb test() {
        AWSDb awsDb = awsDBRepo.getById("f6202d5d-6976-440b-be64-438dfe962df3");
        awsDb.setJsonData("updateByHere");
        awsDBRepo.update("f6202d5d-6976-440b-be64-438dfe962df3", awsDb);

        return awsDb;
    }

    @RequestMapping("/getAll")
    public PaginatedScanList<AWSDb> getAll() {
        PaginatedScanList<AWSDb> awsDbs = awsDBRepo.findAll();
        return awsDbs;
    }
}
