package com.blopapi.service;

import com.blopapi.payload.PostDto;

import java.util.List;

public interface PostService {

    public PostDto createPost(PostDto postDto);

    PostDto getById(long id);

    List<PostDto> getAllPosts(int pageNO, int pageSize, String sortBy, String sortDir);

    void deletePost(long id);

    PostDto updatePost(long id, PostDto postDto);
}
