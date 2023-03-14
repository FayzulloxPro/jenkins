package com.tafakkoor.e_learn.domain;

import jakarta.persistence.*;
import lombok.*;

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
