package com.library.controoler;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/test")
    public Map<String, String> testEndpoint() {
        Map<String, String> response = new HashMap<>();
        response.put("email", "gohar.elsaqqa@gmail.com");
        response.put("emailLink", "mailto:gohar.elsaqqa@gmail.com");
        response.put("phone", "01060415152");
        response.put("whatsappLink", "https://wa.me/201060415152");
        return response;
    }
}