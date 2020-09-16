package me.jun.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jun.guestbook.domain.Account;
import me.jun.guestbook.domain.Post;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class PostCreateDto {

    private final String title;
    private final String content;
    private final Account account;

    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .account(this.account)
                .build();
    }
}