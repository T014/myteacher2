package com.example.tyanai.myteacher2;

public class NotificationFavData {
    private String mUid;
    private String mUserName;
    private String mIconBitmapString;
    private String mTime;
    private String mFavKey;
    private String mKind;
    private String mKindDetail;
    private String mSoldUid;
    private String mTradeKey;

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
    public String getFavPostKey() {
        return mFavKey;
    }
    public String getKind(){
        return mKind;
    }
    public String getKindDetail(){
        return  mKindDetail;
    }
    public String getSoldUid() {
        return mSoldUid;
    }
    public String getTradeKey() {
        return mTradeKey;
    }

    public NotificationFavData(String uid, String userName, String iconBitmapString, String time, String favKey, String kind, String kindDetail,String soldUid,String tradeKey){
        mUid = uid;
        mUserName = userName;
        mIconBitmapString = iconBitmapString;
        mTime = time;
        mFavKey = favKey;
        mKind = kind;
        mKindDetail = kindDetail;
        mSoldUid = soldUid;
        mTradeKey = tradeKey;
    }
}
