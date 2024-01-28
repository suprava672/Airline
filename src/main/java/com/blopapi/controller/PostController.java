package com.blopapi.controller;

import com.blopapi.payload.PostDto;
import com.blopapi.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {

        this.postService = postService;
    }


    //http://localhost:8080/api/posts
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid  @RequestBody PostDto postDto, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

        }
        PostDto savedDto = postService.createPost(postDto);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }
    //http://localhost:8080/api/posts/1

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id) {//status=200
        PostDto dto = postService.getById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }
    //http://localhost:8080/api/posts?pageNO=0&pageSize=2&sortBy=id
    @GetMapping

    List<PostDto> getAllPosts(

            @RequestParam(value = "pageNO",defaultValue = "0",required = false) int pageNO,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue ="asc",required = false ) String sortDir

    ) {
        List<PostDto> postDtos = postService.getAllPosts(pageNO,pageSize,sortBy,sortDir);
        return postDtos;

    }

    //http://localhost:8080/api/posts?id=1
    @PreAuthorize("hasRole('ADMIN')")

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id) {//Status code=200
        postService.deletePost(id);
        return new ResponseEntity<>("post is deleted!!", HttpStatus.OK);


    }

    //http://localhost:8080/api/posts/1
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") long id, @RequestBody PostDto postDto) {//Status code=200
        PostDto dto = postService.updatePost(id, postDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
}

