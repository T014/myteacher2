package com.example.tyanai.myteacher2.func;

import com.example.tyanai.myteacher2.Models.MessageListData;

import java.util.Comparator;

public class TimeLagComparator implements Comparator<MessageListData> {
    @Override
    public int compare(MessageListData m1,MessageListData m2){
        return m1.getLag() < m2.getLag() ? -1 : 1;
    }
}
