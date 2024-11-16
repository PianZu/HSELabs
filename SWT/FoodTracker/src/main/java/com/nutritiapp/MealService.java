package src.main.java.com.nutritiapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class MealService {
    private static final Logger logger = LoggerFactory.getLogger(MealService.class);

    @Autowired
    private MealRepository mealRepository;

    public void addMeal(Meal meal) {
        logger.info("Adding meal: " + meal);
        mealRepository.save(meal);
        logger.info("Meal added successfully.");
    }

    public List<Meal> getAllMeals() {
        return mealRepository.findAll();
    }

    public double calculateAverageRating() {
        List<Meal> meals = getAllMeals();
        if (meals.isEmpty()) {
            return 0.0;
        }

        double totalRating = 0.0;
        for (Meal meal : meals) {
            totalRating += meal.getRating();
        }
        return totalRating / meals.size();
    }
}