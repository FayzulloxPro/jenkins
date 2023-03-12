package com.tafakkoor.e_learn.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "vocabulary")
@AllArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
public class Vocabulary extends Auditable {
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Content story;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AuthUser authUser;
    @Column(nullable = false)
    private String word;
    @Column(nullable = false)
    private String translation;
    private String definition;
}
