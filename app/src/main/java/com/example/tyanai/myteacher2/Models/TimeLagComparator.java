package com.example.tyanai.myteacher2.Models;


import java.util.Comparator;

public class TimeLagComparator implements Comparator<MessageListData> {
    @Override
    public int compare(MessageListData m1,MessageListData m2){
        return m1.getLag() < m2.getLag() ? -1 : 1;
    }
}
