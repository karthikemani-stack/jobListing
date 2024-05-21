package com.telusko.joblisting.Repository;

import com.telusko.joblisting.model.Post;

import java.util.List;

public interface SearchRepository {

    public List<Post> findByText(String text);
}
