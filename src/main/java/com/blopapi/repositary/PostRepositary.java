package com.blopapi.repositary;

import com.blopapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepositary extends JpaRepository<Post,Long> {

}
