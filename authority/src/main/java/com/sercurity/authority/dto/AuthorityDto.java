package com.sercurity.authority.dto;

import com.sercurity.authority.model.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityDto {
    private Long id;
    private String name;

    public static AuthorityDto fromEntity(Authority a) {
        AuthorityDto dto = new AuthorityDto();
        dto.id = a.getId();
        dto.name = a.getName();
        return dto;
    }
    // getters, setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
