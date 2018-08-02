package com.example.tyanai.myteacher2;

public class PostData {


    private String mUserId;
    private String mName;
    private String mTime;
    private String mKey;
    private String mDate;
    private String mImageBitmapString;
    private String mContents;
    private String mCost;
    private String mHowLong;
    private String mGood;
    private String mShare;
    private String mBought;
    private String mEvaluation;
    private String mCancel;
    private String mMethod;
    private String mPostArea;
    private String mPostType;
    private String mLevel;
    private String mCareer;
    private String mPlace;
    private String mSex;
    private String mAge;
    private String mTaught;
    private String mUserEvaluation;



    public String getUserId(){
        return mUserId;
    }
    public String getName(){
        return mName;
    }
    public String getTime(){
        return mTime;
    }
    public String getKey(){
        return mKey;
    }
    public String getDate(){
        return mDate;
    }
    public String getImageBitmapString(){
        return mImageBitmapString;
    }
    public String getContents(){
        return mContents;
    }
    public String getCost(){
        return mCost;
    }
    public String getHowLong(){
        return mHowLong;
    }
    public String getGood(){
        return mGood;
    }
    public String getShare(){
        return mShare;
    }
    public String getBought(){
        return mBought;
    }
    public String getEvaluation(){
        return mEvaluation;
    }
    public String getCancel(){
        return mCancel;
    }
    public String getMethod(){
        return mMethod;
    }
    public String getPostArea(){
        return mPostArea;
    }
    public String getPostType(){
        return mPostType;
    }
    public String getLevel(){
        return mLevel;
    }
    public String getCareer(){
        return mCareer;
    }
    public String getPlace() {
        return mPlace;
    }
    public String getSex(){
        return mSex;
    }
    public String getAge(){
        return  mAge;
    }
    public String getTaught(){
        return  mTaught;
    }
    public String getUserEvaluation(){
        return  mUserEvaluation;
    }


    public PostData(String userId, String name, String time, String key, String date
            , String imageBitmapString, String contents, String cost, String howLong, String good
            , String share, String bought, String evaluation, String cancel, String method
            , String postArea, String postType, String level, String career,String place
            , String sex, String age, String taught, String userEvaluation){
        mUserId = userId;
        mName = name;
        mTime = time;
        mKey = key;
        mDate = date;
        mImageBitmapString = imageBitmapString;
        mContents = contents;
        mCost = cost;
        mHowLong = howLong;
        mGood = good;
        mShare = share;
        mBought = bought;
        mEvaluation = evaluation;
        mCancel = cancel;
        mMethod = method;
        mPostArea = postArea;
        mPostType = postType;
        mLevel = level;
        mCareer = career;
        mPlace = place;
        mSex = sex;
        mAge = age;
        mTaught = taught;
        mUserEvaluation = userEvaluation;
    }
}

