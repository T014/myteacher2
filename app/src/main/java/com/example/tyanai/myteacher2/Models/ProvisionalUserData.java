package com.example.tyanai.myteacher2.Models;

public class ProvisionalUserData {

    private String mName;
    private String mUserId;
    private String mIconBitmapString;
    private String mCaseNum;


    public String getName(){
        return mName;
    }
    public String getUid(){
        return mUserId;
    }
    public String getIconBitmapString(){
        return mIconBitmapString;
    }
    public String getCaseNum() {
        return mCaseNum;
    }

    public ProvisionalUserData(String name, String userId, String iconBitmapString,String caseNum){
        mName = name;
        mUserId = userId;
        mIconBitmapString = iconBitmapString;
        mCaseNum = caseNum;
    }
}
