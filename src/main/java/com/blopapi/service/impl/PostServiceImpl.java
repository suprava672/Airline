package com.blopapi.service.impl;


import com.blopapi.entity.Post;
import com.blopapi.exceptions.ResourceNotFoundException;
import com.blopapi.payload.PostDto;
import com.blopapi.repositary.PostRepositary;
import com.blopapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepositary postRepo;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepositary postRepo,ModelMapper modelMapper) {

        this.postRepo = postRepo;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post=new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        mapToEntity(postDto);
        Post savedpost = postRepo.save(post);
        PostDto dto = mapToDto(savedpost);

        return dto;
    }

    @Override
    public PostDto getById(long id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        PostDto dto = mapToDto(post);
        return dto;
    }
      

    @Override
    public List<PostDto> getAllPosts(int pageNO, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNO, pageSize, sort);

        Page<Post> posts = postRepo.findAll(pageable);

        List<Post> content = posts.getContent();

        List<PostDto> postDtos = content.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public void deletePost(long id) {
        postRepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException(id)
        );
        postRepo.deleteById(id);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        Post updatedContent = mapToEntity(postDto);
        updatedContent.setId(post.getId());
        
        Post updatedPostInfo=postRepo.save(updatedContent);
        return mapToDto(updatedPostInfo);
    }

    PostDto mapToDto(Post post){

        PostDto dto=modelMapper.map(post,PostDto.class);
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
            return dto;
        
        
    }
    Post mapToEntity(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);
//        Post post=new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
        
        
    }
}
