package com.example.follow.repository;

import com.example.follow.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author LYF
 * @dec:
 * @date 2023/7/2
 **/
@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findUserByPhoneAndPassword(String phone, String password);

    User findUserByPhone(String phone);

}

