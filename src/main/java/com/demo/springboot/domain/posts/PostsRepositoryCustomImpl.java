package com.demo.springboot.domain.posts;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.demo.springboot.domain.posts.QPosts.posts;

@RequiredArgsConstructor
public class PostsRepositoryCustomImpl implements PostsRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Posts> findDynamicQuery(String title, String content, String author){
        return queryFactory
                .selectFrom(posts)
                .where(eqTitle(title),
                        eqContent(content),
                        eqAuthor(author))
                .fetch();
    }

    private BooleanExpression eqTitle(String title) {
        if (StringUtils.isEmpty(title)) {
            return null;
        }
        return posts.title.eq(title);
    }

    private BooleanExpression eqContent(String content) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        return posts.content.eq(content);
    }

    private BooleanExpression eqAuthor(String author) {
        if (StringUtils.isEmpty(author)) {
            return null;
        }
        return posts.author.eq(author);
    }
}
