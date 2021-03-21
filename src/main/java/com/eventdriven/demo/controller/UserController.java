package com.eventdriven.demo.controller;

import com.eventdriven.demo.entity.UserMaster;
import com.eventdriven.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserRepository userRepository;


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserMaster userMaster){
        UserMaster user = userRepository.save(userMaster);
        if( user!=null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>("loggedIn", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/login/{username}")
    public ResponseEntity<?> login(@PathVariable String username){
        UserMaster userMaster = userRepository.findByUsername(username);
        if(userMaster!=null)
            return new ResponseEntity<>(userMaster, HttpStatus.OK);

        return new ResponseEntity<>("User Not found", HttpStatus.NOT_FOUND);
    }

}
