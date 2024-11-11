package com.library.mapper;

import com.library.dto.AppUserDTO;
import com.library.entity.AppUser;
import com.library.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    @Mapping(source = "roles", target = "roleIds", qualifiedByName = "rolesToRoleIds")
    AppUserDTO toDto(AppUser appUser);

    @Mapping(source = "roleIds", target = "roles", qualifiedByName = "roleIdsToRoles")
    AppUser toEntity(AppUserDTO appUserDto);

    @Named("rolesToRoleIds")
    default Set<Long> rolesToRoleIds(Set<Role> roles) {
        return roles.stream().map(Role::getId).collect(Collectors.toSet());
    }

    @Named("roleIdsToRoles")
    default Set<Role> roleIdsToRoles(Set<Long> roleIds) {
        return roleIds.stream().map(id -> {
            Role role = new Role();
            role.setId(id);
            return role;
        }).collect(Collectors.toSet());
    }
}
