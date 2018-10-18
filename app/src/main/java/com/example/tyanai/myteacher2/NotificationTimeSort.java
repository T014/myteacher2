package com.example.tyanai.myteacher2;

import java.util.Comparator;

public class NotificationTimeSort implements Comparator<NotificationFavData> {
    @Override
    public int compare(NotificationFavData m1,NotificationFavData m2){
        return m1.getLag() < m2.getLag() ? -1 : 1;
    }
}
