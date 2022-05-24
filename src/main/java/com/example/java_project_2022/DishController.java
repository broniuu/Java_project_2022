package com.example.java_project_2022;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Dish;

public class DishController {
    public Label dishName;
    public Label dishDescription;
    public ImageView dishImage;

    public void iniDish(Dish dish){
        dishName.setText(dish.getName());
        dishDescription.setText(dish.getDescription());
        dishImage.setImage(new Image(dish.getImageUrl()));
    }


}
