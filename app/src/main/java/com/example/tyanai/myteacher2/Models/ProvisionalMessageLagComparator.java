package com.example.tyanai.myteacher2.Models;

import java.util.Comparator;

public class ProvisionalMessageLagComparator implements Comparator<ProvisionalMessageData> {
    @Override
    public int compare(ProvisionalMessageData m1,ProvisionalMessageData m2){

        return m1.getLag() < m2.getLag() ? -1 : 1;
    }
}
