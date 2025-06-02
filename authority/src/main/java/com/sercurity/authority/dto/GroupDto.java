package com.sercurity.authority.dto;

import com.sercurity.authority.model.Group;
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
public class GroupDto {
    private Long id;
    private String name;
    private Set<String> roles;

    public static GroupDto fromEntity(Group g) {
        GroupDto dto = new GroupDto();
        dto.id = g.getId();
        dto.name = g.getName();
        dto.roles = g.getRoles() != null
                ? g.getRoles().stream().map(r -> r.getName()).collect(Collectors.toSet())
                : Set.of();
        return dto;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
