package src.main.java.com.nutritiapp;

public class Rating {
    private Meal meal;
    private int rating; // 0-5 stars

    // Getters and Setters

    public Rating(Meal meal, int rating) {
        this.meal = meal;
        this.rating = rating;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
