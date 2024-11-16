package src.main.java.com.nutritiapp;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.dnd.EffectAllowed;
import com.vaadin.flow.component.dnd.DropEffect;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Route("")
@Component
public class MainView extends VerticalLayout {
    private final MealService mealService;
    private final StatisticsService statisticsService;
    private final List<Meal> todayMeals = new ArrayList<>();
    private final Grid<Meal> todayGrid = new Grid<>(Meal.class);
    private final Grid<Meal> mealGrid = new Grid<>(Meal.class);
    private final TextField totalCaloriesField = new TextField("Total Calories");
    private final TextField totalMeatFreeMealsField = new TextField("Total Meat-free Meals");
    private final TextField averageCO2EmissionsField = new TextField("Average CO2 Emissions");

    @Autowired
    public MainView(MealService mealService, StatisticsService statisticsService) {
        this.mealService = mealService;
        this.statisticsService = statisticsService;

        H1 heading = new H1("NutritionTracker");
        heading.getStyle().set("text-align", "center");
        heading.setWidthFull();

        // Add the heading to the layout
        add(heading);

        // Create a layout for the tables
        HorizontalLayout tablesLayout = new HorizontalLayout();
        tablesLayout.setWidthFull();

        // Create a layout for today's meals
        VerticalLayout todayLayout = new VerticalLayout();
        H2 todayHeading = new H2("Daily Tracker");
        todayLayout.add(todayHeading);

        // Create the grid to display meals for today
        todayGrid.setColumns("title", "calories", "carbohydrates", "fat", "protein", "containsMeat", "vegetarian", "vegan", "imageUrl", "rating");
        todayGrid.setItems(todayMeals);
        todayGrid.setWidth("100%");

        // Add the grid to the layout
        todayLayout.add(todayGrid);

        // Add total calories, total meat-free meals, and average CO2 emissions fields
        totalCaloriesField.setReadOnly(true);
        totalMeatFreeMealsField.setReadOnly(true);
        averageCO2EmissionsField.setReadOnly(true);
        todayLayout.add(totalCaloriesField, totalMeatFreeMealsField, averageCO2EmissionsField);

        // Create a layout for all meals
        VerticalLayout allMealsLayout = new VerticalLayout();
        H2 allMealsHeading = new H2("All Meals");
        allMealsLayout.add(allMealsHeading);
        allMealsLayout.setWidth("50%");

        // Create the grid to display all meals
        mealGrid.setColumns("title", "calories", "carbohydrates", "fat", "protein", "containsMeat", "vegetarian", "vegan", "imageUrl", "rating");
        mealGrid.setItems(mealService.getAllMeals());
        mealGrid.setWidth("100%"); // Ensure the grid takes full width of its container

        // Add the grid to the layout
        allMealsLayout.add(mealGrid);

        // Create the Add New Meal button and add it below the meal grid
        Button addMealButton = new Button("Add New Meal");
        addMealButton.addClickListener(e -> openAddMealDialog());
        allMealsLayout.add(addMealButton);

        // Align items in the allMealsLayout to the start (left)
        allMealsLayout.setAlignItems(Alignment.STRETCH);

        // Enable drag and drop from mealGrid to todayGrid
        mealGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        DragSource<Grid<Meal>> dragSource = DragSource.create(mealGrid);
        dragSource.setEffectAllowed(EffectAllowed.MOVE);
        dragSource.addDragStartListener(event -> {
            if (event.getComponent().getSelectedItems().isEmpty()) {
                event.getComponent().getElement().setAttribute("draggable", "false");
            } else {
                event.getComponent().getElement().setAttribute("draggable", "true");
            }
        });

        DropTarget<Grid<Meal>> dropTarget = DropTarget.create(todayGrid);
        dropTarget.setDropEffect(DropEffect.MOVE);
        dropTarget.addDropListener(event -> {
            event.getDragSourceComponent().ifPresent(source -> {
                if (source instanceof Grid) {
                    Grid<Meal> sourceGrid = (Grid<Meal>) source;
                    sourceGrid.getSelectedItems().forEach(item -> {
                        todayMeals.add(item);
                        todayGrid.getDataProvider().refreshAll();
                        updateStatistics();
                    });
                }
            });
        });

        // Add the grids to the tables layout
        tablesLayout.add(todayLayout, allMealsLayout);
        tablesLayout.setFlexGrow(3, todayLayout); // Adjust flex grow for todayLayout
        tablesLayout.setFlexGrow(1, allMealsLayout); // Adjust flex grow for allMealsLayout

        // Add the tables layout to the main layout
        add(tablesLayout);

        // Center the main layout's content
        setAlignItems(Alignment.CENTER);
    }

    private void updateStatistics() {
        int totalCalories = todayMeals.stream().mapToInt(Meal::getCalories).sum();
        long totalMeatFreeMeals = todayMeals.stream().filter(meal -> !meal.isContainsMeat()).count();
        double averageCO2Emissions = todayMeals.stream().mapToDouble(Meal::calculateCO2Emissions).average().orElse(0.0);

        totalCaloriesField.setValue(String.valueOf(totalCalories));
        totalMeatFreeMealsField.setValue(String.valueOf(totalMeatFreeMeals));
        averageCO2EmissionsField.setValue(String.format("%.2f", averageCO2Emissions));
    }

    private void openAddMealDialog() {
        Dialog addMealDialog = new Dialog();
        addMealDialog.setWidth("400px");

        VerticalLayout centerLayout = new VerticalLayout();
        centerLayout.setAlignItems(Alignment.CENTER);

        FormLayout mealForm = new FormLayout();

        TextField titleField = new TextField("Title");
        TextField caloriesField = new TextField("Calories");
        TextField carbsField = new TextField("Carbohydrates");
        TextField fatField = new TextField("Fat");
        TextField proteinField = new TextField("Protein");
        TextField imageUrlField = new TextField("Image URL");

        Checkbox containsMeatCheckbox = new Checkbox("Contains meat");
        Checkbox vegetarianCheckbox = new Checkbox("Vegetarian");
        Checkbox veganCheckbox = new Checkbox("Vegan");

        // Number field to input rating
        TextField ratingField = new TextField("Rating");

        Button saveButton = new Button("Save Meal");
        saveButton.addClickListener(event -> {
            try {
                Meal meal = new Meal(
                        titleField.getValue(),
                        Integer.parseInt(caloriesField.getValue()),
                        Integer.parseInt(carbsField.getValue()),
                        Integer.parseInt(fatField.getValue()),
                        Integer.parseInt(proteinField.getValue()),
                        containsMeatCheckbox.getValue(),
                        vegetarianCheckbox.getValue(),
                        veganCheckbox.getValue(),
                        imageUrlField.getValue(),
                        Integer.parseInt(ratingField.getValue()));
                mealService.addMeal(meal);

                // Update the meal grid with the new meal
                mealGrid.setItems(mealService.getAllMeals());

                // Close the dialog after saving
                addMealDialog.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        mealForm.add(titleField, caloriesField, carbsField, fatField, proteinField, imageUrlField, ratingField);

        centerLayout.add(mealForm);

        // Create a vertical layout for the checkboxes and add them to the center layout
        VerticalLayout checkboxLayout = new VerticalLayout(containsMeatCheckbox, vegetarianCheckbox, veganCheckbox);
        centerLayout.add(checkboxLayout);

        // Add the save button to the center layout
        centerLayout.add(saveButton);

        // Add the center layout to the dialog
        addMealDialog.add(centerLayout);
        addMealDialog.open();
    }
}
