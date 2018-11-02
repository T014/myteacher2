package com.example.tyanai.myteacher2.Models;

public class ProvisionalKeyData {
    private String mCaseNum;
    private String mPostKey;
    private String mTime;
    private String mUid;


    public String getCaseNum(){
        return  mCaseNum;
    }
    public String getPostKey() {
        return mPostKey;
    }
    public String getTime() {
        return mTime;
    }
    public String getUid() {
        return mUid;
    }

    public ProvisionalKeyData(String caseNum,String postKey,String time,String uid){
        mCaseNum = caseNum;
        mPostKey = postKey;
        mTime = time;
        mUid = uid;
    }


}
