package com.sercurity.authority.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "access_control_group")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupRole {
    @Id
    @GeneratedValue
    private Long id;

    private Long groupId;
    private Long roleId;
}
