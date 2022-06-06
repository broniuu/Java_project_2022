package com.example.java_project_2022.comparators;

import com.example.java_project_2022.model.User;

import java.util.Comparator;

/**
 * Klasa służy do porównywania ze sobą obiektów klasy User
 */

public class UserComparator implements Comparator<User> {

    @Override
    public int compare(User user1, User user2) {
        String login1 = user1.getLogin();
        String login2 = user2.getLogin();
        return login1.compareTo(login2);
    }
}
