package com.sercurity.authority.dto;

import com.sercurity.authority.model.User;
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
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    private Set<String> roles;
    private Set<String> groups;

    public static UserDto fromEntity(User user) {
        UserDto dto = new UserDto();
        dto.id = user.getId();
        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.enabled = user.isEnabled();
        dto.roles = user.getRoles() != null ? user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toSet()) : Set.of();
        dto.groups = user.getGroups() != null ? user.getGroups().stream().map(g -> g.getName()).collect(Collectors.toSet()) : Set.of();
        return dto;
    }


    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public Set<String> getGroups() {
        return groups;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public void setGroups(Set<String> groups) {
        this.groups = groups;
    }
}
