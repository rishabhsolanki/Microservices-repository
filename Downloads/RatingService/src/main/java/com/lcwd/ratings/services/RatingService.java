package com.lcwd.ratings.services;

import com.lcwd.ratings.entities.Rating;

import java.util.List;

public interface  RatingService {
    //create
    Rating create(Rating rating);

    //get all ratings
    List<Rating> getRatings();

    //getall by userId
    List<Rating> getRatingByUserId(String userId);

    //get all by hotel
    List<Rating> getRatingByHotelId(String hotelId);




}
