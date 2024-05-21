package com.telusko.joblisting.Repository;

import com.mongodb.client.MongoClient;
import com.telusko.joblisting.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.AggregateIterable;

@Component
public class SearchRepositoryImpl implements  SearchRepository{
    @Autowired
    MongoClient mongoClient;
    @Autowired
    MongoConverter converter;
    public List<Post> findByText(String text){

        final List<Post> posts = new ArrayList<>();

        MongoDatabase database = mongoClient.getDatabase("telusko");
        MongoCollection<Document> collection = database.getCollection("jobpost");
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
                        new Document("text",
                                new Document("query", text)
                                        .append("path", Arrays.asList("techs", "desc", "profile")))),
                new Document("$sort",
                        new Document("exp", 1L)),
                new Document("$limit", 1L)));

        result.forEach(doc -> posts.add(converter.read(Post.class,doc)));

        return  posts;
    }
}
