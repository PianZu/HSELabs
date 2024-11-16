package src.main.java.com.nutritiapp;

public class Statistics {
    private MealService mealService;
    private RatingService ratingService;

    public Statistics(MealService mealService, RatingService ratingService) {
        this.mealService = mealService;
        this.ratingService = ratingService;
    }

    public int getTotalCalories() {
        return mealService.getAllMeals().stream().mapToInt(Meal::getCalories).sum();
    }

    public double getAverageMealRating() {
        return ratingService.getAverageRating();
    }

    public long getMeatFreeMealCount() {
        return mealService.getAllMeals().stream().filter(meal -> !meal.isContainsMeat()).count();
    }

    public double getEstimatedCO2Savings() {
        return getMeatFreeMealCount() * 2.5; // Example: 2.5 kg CO2 saved per meat-free meal
    }
}
