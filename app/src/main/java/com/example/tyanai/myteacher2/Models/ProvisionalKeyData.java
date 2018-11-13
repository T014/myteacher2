package com.example.tyanai.myteacher2.Models;

public class ProvisionalKeyData {
    private String mCaseNum;
    private String mPostKey;
    private String mTime;
    private String mUid;
    private String mIconBitmapString;
    private String mContentBitmapString;
    private String mName;
    private String mContent;
    private String mCount;
    private String mPostUid;

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
    public String getIconBitmapString() {
        return mIconBitmapString;
    }
    public String getContentBitmapString(){
        return mContentBitmapString;
    }
    public String getName() {
        return mName;
    }
    public String getContent() {
        return mContent;
    }
    public String getCount() {
        return mCount;
    }
    public String getPostUid() {
        return mPostUid;
    }

    public ProvisionalKeyData(String caseNum, String postKey, String time, String uid, String iconBitmapString
            , String contentBitmapString, String name, String content, String count, String postUid){
        mCaseNum = caseNum;
        mPostKey = postKey;
        mTime = time;
        mUid = uid;
        mIconBitmapString = iconBitmapString;
        mContentBitmapString = contentBitmapString;
        mName = name;
        mContent = content;
        mCount = count;
        mPostUid = postUid;
    }
}