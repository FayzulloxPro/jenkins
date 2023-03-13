package com.tafakkoor.e_learn.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image extends Auditable{
    @Column(nullable = false)
    private String generatedFileName;
    @Column(nullable = false)
    private String originalFileName;
    @Column(nullable = false)
    private String filePath;
    @Column(nullable = false)
    private String mimeType;
}
