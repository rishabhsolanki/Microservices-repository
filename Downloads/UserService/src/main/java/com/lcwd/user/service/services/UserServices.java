package com.lcwd.user.service.services;

import com.lcwd.user.service.entities.User;

import java.util.List;

public interface UserServices {
    //create a user
    User saveUser(User user);
    //get all user
    List<User> getAllUser();

    User getUser(String userId);
}
