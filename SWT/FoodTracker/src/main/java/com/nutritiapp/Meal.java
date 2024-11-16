package src.main.java.com.nutritiapp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private int calories;
    private int carbohydrates;
    private int fat;
    private int protein;
    private boolean containsMeat;
    private boolean vegetarian;
    private boolean vegan;
    private String imageUrl;
    private int rating;

    // Getters and Setters
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public boolean isContainsMeat() {
        return containsMeat;
    }

    public void setContainsMeat(boolean containsMeat) {
        this.containsMeat = containsMeat;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public double calculateCO2Emissions() {
        if (isContainsMeat()) {
            return 10;
        } else if (isVegan()) {
            return 6.0;
        } else if (isVegetarian()) {
            return 8.0;
        } else {
            return 0.0; // Für den Fall, dass keine dieser Kategorien zutrifft
        }
    }

    // Constructors
    public Meal() {}

    public Meal(String title, int calories, int carbohydrates, int fat, int protein,
                boolean containsMeat, boolean vegetarian, boolean vegan, String imageUrl, int rating) {
        this.title = title;
        this.calories = calories;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.protein = protein;
        this.containsMeat = containsMeat;
        this.vegetarian = vegetarian;
        this.vegan = vegan;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }
}