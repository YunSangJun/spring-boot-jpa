package com.demo.springboot.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class CommonApiController {

    private Environment env;

    @GetMapping("/profile")
    public String[] getProfile() {
        log.debug("Active profile: " + String.join(", ", env.getActiveProfiles()));
        return env.getActiveProfiles();
    }
}
