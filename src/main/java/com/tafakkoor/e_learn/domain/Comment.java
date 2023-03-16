package com.tafakkoor.e_learn.domain;

import com.tafakkoor.e_learn.enums.CommentType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Comment extends Auditable{
    @Column(nullable = false)
    private String comment;
    @JoinColumn(nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Content content;
    @JoinColumn(nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AuthUser user;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CommentType commentType;
    @JoinColumn(nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Comment parentComment;

}