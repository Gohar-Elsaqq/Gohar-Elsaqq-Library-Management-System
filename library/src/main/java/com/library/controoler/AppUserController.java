package com.library.controoler;

import com.library.dto.AppUserDTO;
import com.library.service.AppUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AppUserController {
    @Autowired
    private  AppUserServices appUserService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppUserDTO createUser(@RequestBody AppUserDTO appUserDTO) {

        return appUserService.save(appUserDTO);
    }

    @GetMapping("/{id}")
    public AppUserDTO getUserById(@PathVariable Long id) {
        return appUserService.findById(id);
    }

    @GetMapping
    public List<AppUserDTO> getAllUsers() {
        return appUserService.findAll();
    }
}
