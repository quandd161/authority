package com.sercurity.authority.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "role_authority_rel")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleAuthority {
    @Id
    @GeneratedValue
    private Long id;

    private Long roleId;
    private Long authorityId;
}
