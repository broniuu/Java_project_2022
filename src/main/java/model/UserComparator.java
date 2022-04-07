package model;

import java.util.Comparator;

public class UserComparator implements Comparator<User> {

    // porównuje użytkowników pod względem ich loginów
    @Override
    public int compare(User user1, User user2) {
        String login1 = user1.getLogin();
        String login2 = user2.getLogin();
        int result = login1.compareTo(login2);
        return result;
    }
}
