package src.main.java.com.nutritiapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    @Autowired
    private MealService mealService;

    public int getTotalCaloriesConsumed() {
        return mealService.getAllMeals().stream()
                .mapToInt(Meal::getCalories)
                .sum();
    }

    public long getNumberOfMeatFreeMeals() {
        return mealService.getAllMeals().stream()
                .filter(meal -> !meal.isContainsMeat())
                .count();
    }

    public double getEstimatedCO2Savings() {
        // Beispielwerte für die Berechnung
        double averageMealCO2 = 5.0; // kg CO2 pro durchschnittliche Mahlzeit
        double meatFreeMealCO2 = 2.0; // kg CO2 pro fleischfreie Mahlzeit
        long meatFreeMeals = getNumberOfMeatFreeMeals();

        return meatFreeMeals * (averageMealCO2 - meatFreeMealCO2);
    }
}