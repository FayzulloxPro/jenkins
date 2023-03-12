package com.tafakkoor.e_learn.domain;

import com.tafakkoor.e_learn.enums.Progress;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class UserContent extends Auditable{
    @JoinColumn(name = "auth_user_id")
    @ManyToOne
    private AuthUser user;
    @JoinColumn(name = "content_id")
    @ManyToOne
    private Content content;
    @Column(nullable = false)
    @Builder.Default
    private Progress progress = Progress.IN_PROGRESS;
}
