package com.library.service;

import com.library.dto.AppUserDTO;
import com.library.entity.AppUser;
import com.library.mapper.AppUserMapper;
import com.library.repository.AppUserRepository;
import com.library.security.AppUserDatiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserServices implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppUserMapper appUserMapper;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
       Optional<AppUser> appUser=appUserRepository.findByUserName(userName);
       if(!appUser.isPresent()){
           throw new UsernameNotFoundException("this name does noe found "+userName);
       }
       return new AppUserDatiles(appUser.get());
    }
//    private static List<GrantedAuthority> getGrantedAuthorities(AppUser user){
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        if(!user.getRoles().isEmpty()){
//            user.getRoles().forEach(role -> {
//                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//            });
//        }
//        return grantedAuthorities;
//    }
    public AppUserDTO save(AppUserDTO appUserDTO) {
        AppUser appUser = appUserMapper.toEntity(appUserDTO);
        appUser.setPassword(passwordEncoder.encode(appUserDTO.getPassword()));
        appUser = appUserRepository.save(appUser);
        return appUserMapper.toDto(appUser);
    }

    public AppUserDTO findById(Long id) {
        Optional<AppUser> appUser = appUserRepository.findById(id);
        return appUser.map(appUserMapper::toDto).orElse(null);
    }

    public List<AppUserDTO> findAll() {
        List<AppUser> users = appUserRepository.findAll();
        return users.stream().map(appUserMapper::toDto).toList();
    }
}
