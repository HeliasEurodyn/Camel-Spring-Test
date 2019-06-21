package com.flexi.controller;

import com.flexi.model.User;
import com.flexi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class UserController {


    @Autowired
    private UserRepository userRepository;


    @PostMapping("/user/registration")
    public ResponseEntity<Void> createUser(
            @RequestBody User user){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String EncodedPassword =encoder.encode(user.getPassword());
        user.setPassword(EncodedPassword);
        //User createdUser = userService.save(user);

        User createdUser = userRepository.save(user);

        //Location
        //Get current resource url
        ///{id}
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdUser.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

}
