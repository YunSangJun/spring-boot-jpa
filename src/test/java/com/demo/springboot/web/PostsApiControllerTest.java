package com.demo.springboot.web;

import com.demo.springboot.domain.posts.Posts;
import com.demo.springboot.domain.posts.PostsRepository;
import com.demo.springboot.web.dto.PostsSaveRequestDto;
import com.demo.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void savePost() throws Exception {
        String title = "test";
        String content = "This is a test.";
        String author = "ysj188cm@gmail.com";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> postsList = postsRepository.findAll();
        Posts posts = postsList.get(0);
        System.out.println(">>> Created date: " + posts.getCreatedDate());
        System.out.println(">>> Modified date: " + posts.getModifiedDate());
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void updatePost() throws Exception {
        String title = "test";
        String content = "This is a test.";
        String author = "ysj188cm@gmail.com";

        Posts savedPosts = postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        Long updateId = savedPosts.getId();
        String updateTitle = "Updated title";
        String updateContent = "This is an updated content.";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(updateTitle)
                .content(updateContent)
                .build();

        String url = "http://localhost:" + port +  "/api/v1/posts/" + updateId;
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> postsList = postsRepository.findAll();
        Posts posts = postsList.get(0);
        System.out.println(">>> Created date: " + posts.getCreatedDate());
        System.out.println(">>> Modified date: " + posts.getModifiedDate());
        assertThat(posts.getTitle()).isEqualTo(updateTitle);
        assertThat(posts.getContent()).isEqualTo(updateContent);

    }

    @Test
    public void getPostById() throws Exception {
        String title = "test";
        String content = "This is a test.";
        String author = "ysj188cm@gmail.com";

        Posts savedPosts = postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        Long postId = savedPosts.getId();

        String url = "http://localhost:" + port +  "/api/v1/posts/" + postId;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Posts> postsList = postsRepository.findAll();
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
        assertThat(posts.getAuthor()).isEqualTo(author);

    }

}
