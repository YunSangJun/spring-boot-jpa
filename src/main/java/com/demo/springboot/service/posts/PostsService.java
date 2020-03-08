package com.demo.springboot.service.posts;

import com.demo.springboot.domain.posts.Posts;
import com.demo.springboot.domain.posts.PostsRepository;
import com.demo.springboot.web.dto.PostsResponseDto;
import com.demo.springboot.web.dto.PostsSaveRequestDto;
import com.demo.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("There is no post whose ID is '" + id + "'."));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("There is no post whose ID is '" + id + "'."));
        return new PostsResponseDto(entity);
    }

    public List<PostsResponseDto> findDynamicQuery(String title, String content, String author) {
        List<PostsResponseDto> postsResponseDtoList = new ArrayList<PostsResponseDto>();
        List<Posts> postsList = postsRepository.findDynamicQuery(title, content, author);
        postsList.forEach(posts -> {
            postsResponseDtoList.add(new PostsResponseDto(posts));
        });

        return postsResponseDtoList;
    }

}
