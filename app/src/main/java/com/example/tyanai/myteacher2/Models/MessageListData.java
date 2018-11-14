package com.example.tyanai.myteacher2.Models;

public class MessageListData {
    private String mUid;
    private String mUserName;
    private String mIconBitmapString;
    private String mTime;
    private String mContent;
    private String mBitmapString;
    private String mKey;
    private String mMyUid;
    private long mLag;
    private String mRemoveKey;

    public String getUid(){
        return mUid;
    }
    public String getUserName(){
        return mUserName;
    }
    public String getIconBitmapString(){
        return mIconBitmapString;
    }
    public String getTime() {
        return mTime;
    }
    public String getContent() {
        return mContent;
    }
    public String getBitmapString(){
        return mBitmapString;
    }
    public String getKey() {
        return mKey;
    }
    public String getMyUid() {
        return mMyUid;
    }
    public long getLag() {
        return mLag;
    }
    public String getRemoveKey(){
        return mRemoveKey;
    }

    public MessageListData(String uid, String userName, String iconBitmapString, String time, String content, String bitmapString, String key, String myUid,long lag,String removeKey){
        mUid = uid;
        mUserName = userName;
        mIconBitmapString = iconBitmapString;
        mTime = time;
        mContent = content;
        mBitmapString = bitmapString;
        mKey = key;
        mMyUid = myUid;
        mLag =lag;
        mRemoveKey = removeKey;
    }
}