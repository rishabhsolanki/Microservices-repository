package com.lcwd.user.service.controllers;

import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.services.UserServices;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServices userServices;
    //create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1 = userServices.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }
    //single user get
    @GetMapping("/{userId}")
    //@CircuitBreaker(name="ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
    //@Retry(name="ratingHotelService",fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name="userRateLimiter",fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        User user = userServices.getUser(userId);
        return ResponseEntity.ok(user);
    }

    //creating fallBack method  for circuitBreaker
    public ResponseEntity<User> ratingHotelFallback(String userid,Exception ex){
        System.out.println("Fallback is executed "+ex.getMessage());
        User user = User.builder()
                .email("dummy@gmail")
                .userId("12212")
                .about("Dummy Id di hai ")
                .name("Dummy singh")
                .build();
        return new ResponseEntity<>(user,HttpStatus.OK);

    }
    @GetMapping
    public  ResponseEntity<List<User>> getAllUser(){
        List<User> allUser = userServices.getAllUser();
        return ResponseEntity.ok(allUser);
    }

}
