package com.sercurity.authority.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_group_rel")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupUser {
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private Long groupId;
}
