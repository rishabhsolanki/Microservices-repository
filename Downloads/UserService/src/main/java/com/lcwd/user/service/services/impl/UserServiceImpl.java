package com.lcwd.user.service.services.impl;

import com.lcwd.user.service.entities.Hotel;
import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.external.service.HotelService;
import com.lcwd.user.service.repositories.UserRepository;
import com.lcwd.user.service.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserServices {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    //get single user
    @Override
    public User getUser(String userId) {
        //get user from database with the help of user repository
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server !! "+userId));
       //fetch rating of the above user  from RATING SERVICE
        // http://localhost:8083/ratings/users/0d9b3a58-a7f1-40f1-8ae6-c5309b12cf1e
     // ArrayList<Rating> ratingsOfUser = restTemplate.getForObject("http://localhost:8083/ratings/users/0d9b3a58-a7f1-40f1-8ae6-c5309b12cf1e", ArrayList.class);
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        logger.info("{}", ratingsOfUser);
        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
        List<Rating> ratingList = ratings.stream().map(rating -> {
            //api call to HOTEL SERVICE to get the hotel
            //http://localhost:8082/hotels/a51670e3-c98b-407e-ba07-c200a09d52bc
            System.out.println(rating.getHotelId());
           // ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/a51670e3-c98b-407e-ba07-c200a09d52bc", Hotel.class);
            //Hotel hotel = forEntity.getBody();
            // feign client code call for hotelService
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
         //   logger.info("response status code:{}",forEntity.getStatusCode());
            //set the hotel to rating
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

      user.setRatings(ratingList);
      return user;
    }
}
