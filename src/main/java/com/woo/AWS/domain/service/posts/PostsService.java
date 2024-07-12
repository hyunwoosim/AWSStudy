package com.woo.AWS.domain.service.posts;

import com.woo.AWS.domain.posts.Posts;
import com.woo.AWS.domain.posts.PostsRepository;
import com.woo.AWS.web.dto.PostsListResponseDto;
import com.woo.AWS.web.dto.PostsResponseDto;
import com.woo.AWS.web.dto.PostsSaveRequestDto;
import com.woo.AWS.web.dto.PostsUpdateRequestDto;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
            .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findALlDesc() {
        return postsRepository.findALLDesc().stream().map(PostsListResponseDto::new).collect(
            Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new
            IllegalArgumentException("해당 게시글이 없습니다. id=" +id));
            postsRepository.delete(posts);
    }
}
