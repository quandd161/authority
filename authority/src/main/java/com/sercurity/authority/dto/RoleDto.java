package com.sercurity.authority.dto;

import com.sercurity.authority.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private Long id;
    private String name;
    private Set<String> authorities;

    public static RoleDto fromEntity(Role role) {
        RoleDto dto = new RoleDto();
        dto.id = role.getId();
        dto.name = role.getName();
//        dto.authorities = role.getAuthorities() != null
//                ? role.getAuthorities().stream().map(a -> a.getName()).collect(Collectors.toSet())
//                : Set.of();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }
}
