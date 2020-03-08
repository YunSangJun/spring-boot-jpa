package com.demo.springboot.domain.posts;

import java.util.List;

public interface PostsRepositoryCustom {
    List<Posts> findDynamicQuery(String title, String content, String author);
}
