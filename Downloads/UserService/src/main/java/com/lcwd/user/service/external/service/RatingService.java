package com.lcwd.user.service.external.service;

import com.lcwd.user.service.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

    //get


    //post
    @PostMapping("/ratings")
    public Rating createRating(Rating values);


    //put
    @PutMapping("/ratings/{ratingId}")
    public Rating updateRating(@PathVariable("ratingId") String ratingId ,Rating rating);

    //delete
    @DeleteMapping("/ratings/{ratingId}")
    public void deleteRating(@PathVariable("ratingId") String ratingId);
}
