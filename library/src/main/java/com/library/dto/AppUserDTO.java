package com.library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDTO {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String password;
    private Set<Long> roleIds;
}
