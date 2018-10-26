package com.example.tyanai.myteacher2.Models;

public class BusinessData {
    private String mBought;
    private String mDate;
    private String mReceiveDate;
    private String mSold;
    private String mPayDay;
    private String mRequestKey;
    private String mUserName;
    private String mUserIcon;
    private String mEvaluation;
    private String mJudgment;
    private String mPostKey;
    private String mContentImageBitmapString;
    private String mKind;
    private String mKindDetail;
    private String mBuyName;
    private String mBuyIconBitmapString;
    private String mPermittedDate;
    private String mRefactorKey;


    public String getBought(){
        return mBought;
    }
    public String getDate(){
        return mDate;
    }
    public String getReceiveDate(){
        return mReceiveDate;
    }
    public String getSold(){
        return mSold;
    }
    public String getPayDay(){
        return mPayDay;
    }
    public String getRequestKey(){
        return mRequestKey;
    }
    public String getUserName(){
        return mUserName;
    }

    public String getUserIcon() {
        return mUserIcon;
    }

    public String getEvaluation() {
        return mEvaluation;
    }

    public String getJudgment() {
        return mJudgment;
    }
    public String getPostKey(){
        return mPostKey;
    }

    public String getContentImageBitmapString() {
        return mContentImageBitmapString;
    }

    public String getKind() {
         return mKind;
    }
    public String getKindDetail(){
        return mKindDetail;
    }
    public String getBuyName(){
        return mBuyName;
    }
    public String getBuyIconBitmapString(){
        return mBuyIconBitmapString;
    }
    public String getPermittedDate(){
        return mPermittedDate;
    }

    public String getRefactorKey() {
        return mRefactorKey;
    }

    public BusinessData(String bought, String date, String receiveDate, String sold, String payDay, String requestKey, String userName,
                        String userIcon, String evaluation, String judgment, String postKey, String contentImageBitmapString,
                        String kind, String kindDetail, String buyName, String buyIconBitmapString, String permittedDate, String refactorKey){
        mBought = bought;
        mDate = date;
        mReceiveDate = receiveDate;
        mSold = sold;
        mPayDay = payDay;
        mRequestKey = requestKey;
        mUserName = userName;
        mUserIcon = userIcon;
        mEvaluation = evaluation;
        mJudgment = judgment;
        mPostKey = postKey;
        mContentImageBitmapString = contentImageBitmapString;
        mKind = kind;
        mKindDetail = kindDetail;
        mBuyName = buyName;
        mBuyIconBitmapString = buyIconBitmapString;
        mPermittedDate = permittedDate;
        mRequestKey = requestKey;
    }
}
