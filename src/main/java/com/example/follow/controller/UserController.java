package com.example.follow.controller;

import com.example.follow.model.User;
import com.example.follow.model.response.NormalResponse;
import com.example.follow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/2
 **/
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("测试成功");
    }

    @PostMapping("/login")
    public ResponseEntity<NormalResponse> login(@RequestBody User user) {
        User findUser = userService.login(user);
        return ResponseEntity.ok(new NormalResponse(findUser));
    }

    @PostMapping("/create")
    public ResponseEntity<NormalResponse> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(new NormalResponse(createdUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}

