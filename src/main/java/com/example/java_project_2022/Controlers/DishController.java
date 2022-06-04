package com.example.java_project_2022.Controlers;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.example.java_project_2022.model.Dish;


/**
 * The class Dish controller odpowiada za wyświetlanie pojedynczej potrawy
 */
public class DishController {
    public Label dishName;
    public Label dishDescription;
    public ImageView dishImage;


    /**
     *
     * Ini dish
     *ustawia potrawe do wyswietlania
     * @param dish  potrawa ktora chcemy wyswietlic
     */
    public void iniDish(Dish dish){

        dishName.setText(dish.getName());
        dishDescription.setText(dish.getDescription());
        dishImage.setImage(new Image(dish.getImageUrl()));
    }


}
