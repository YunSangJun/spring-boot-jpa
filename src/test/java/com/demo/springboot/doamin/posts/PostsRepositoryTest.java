package com.demo.springboot.doamin.posts;

import com.demo.springboot.domain.posts.Posts;
import com.demo.springboot.domain.posts.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void savePost() {
        String title = "test";
        String content = "This is a test.";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("yunsangjun")
                .build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void saveBaseTimeEntity() {
        String title = "test";
        String content = "This is a test.";

        LocalDateTime now = LocalDateTime.of(2020,1,1,0,0,0);
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("yunsangjun")
                .build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);
        System.out.println(">>> Created date: " + posts.getCreatedDate());
        System.out.println(">>> Modified date: " + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
