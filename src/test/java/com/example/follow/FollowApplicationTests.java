package com.example.follow;

import com.example.follow.model.user.User;
import com.example.follow.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FollowApplicationTests {
    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    void register() {
        User user = new User();
        user.setPhone("大傻22");
        user.setPassword("哈哈哈");
        User user1 = userService.createUser(user);
        System.out.println(user1.toString());
    }


}
