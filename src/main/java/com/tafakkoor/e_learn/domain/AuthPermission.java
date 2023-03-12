package com.tafakkoor.e_learn.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AuthPermission extends Auditable{
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String code;
}
