package com.example.aws_db.controller;

import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.example.aws_db.domain.AWSDb;
import com.example.aws_db.repo.AwsDBRepo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private AwsDBRepo awsDBRepo;

    @RequestMapping("/create")
    public String testInsert() {
        AWSDb awsDb = new AWSDb();
        awsDb.setId("12333");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("prop1", "testAutoDetectId");
        Gson gson = new Gson();
        awsDb.setJsonData(gson.toJson(jsonObject));
        awsDBRepo.save(awsDb);

        return awsDb.getId();
    }

    @RequestMapping("/test")
    public AWSDb test() {
        AWSDb awsDb = awsDBRepo.getById("f6202d5d-6976-440b-be64-438dfe962df3");
        JsonObject jsonObject = new JsonObject();
        Gson gson = new Gson();
        awsDb.setJsonData(gson.toJson(jsonObject));
        jsonObject.addProperty("prop1", "updateByHere");
        awsDBRepo.update("f6202d5d-6976-440b-be64-438dfe962df3", awsDb);

        return awsDb;
    }

    @RequestMapping("/getAll")
    public PaginatedScanList<AWSDb> getAll() {
        PaginatedScanList<AWSDb> awsDbs = awsDBRepo.findAll();
        return awsDbs;
    }

    @RequestMapping("/query")
    public PaginatedScanList<AWSDb> query() {
        String id = "1233333333333333333333333";
        String jsonData = "prop1";
        int n = 123333;

        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":v1", new AttributeValue(id));
        eav.put(":v2", new AttributeValue(jsonData));
        eav.put(":v3", new AttributeValue(String.valueOf(n)));

        String query = "side_project_db = :v1 or contains(jsonData, :v2) or side_project_db = :v3";

        return awsDBRepo.query(eav, query);
    }
}
