package src.main.java.com.nutritiapp;

import java.util.ArrayList;
import java.util.List;

public class RatingService {
    private List<Rating> ratings = new ArrayList<>();

    public void rateMeal(Meal meal, int rating) {
        ratings.add(new Rating(meal, rating));
    }

    public List<Rating> getAllRatings() {
        return ratings;
    }

    public double getAverageRating() {
        return ratings.stream().mapToInt(Rating::getRating).average().orElse(0.0);
    }
}
