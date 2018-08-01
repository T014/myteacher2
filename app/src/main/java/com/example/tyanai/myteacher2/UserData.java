package com.example.tyanai.myteacher2;

public class UserData {

    private String mName;
    private String mUserId;
    private String mComment;
    private String mFollows;
    private String mFollowers;
    private String mPosts;
    private String mFavorites;
    private String mSex;
    private String mAge;
    private String mEvaluations;
    private String mTaught;
    private String mPeriod;
    private String mGroups;
    private String mIconBitmapString;
    private String mHeaderBitmapString;



    public String getName(){
        return mName;
    }
    public String getUid(){
        return mUserId;
    }
    public String getComment(){
        return mComment;
    }
    public String getFollows(){
        return mFollows;
    }
    public String getFollowers(){
        return mFollowers;
    }
    public String getPosts(){
        return mPosts;
    }
    public String getFavorites(){
        return mFavorites;
    }

    public String getSex() {
        return mSex;
    }

    public String getAge() {
        return mAge;
    }

    public String getEvaluations(){
        return mEvaluations;
    }
    public String getTaught(){
        return mTaught;
    }
    public String getPeriod(){
        return mPeriod;
    }
    public String getGroups(){
        return mGroups;
    }
    public String getIconBitmapString(){
        return mIconBitmapString;
    }
    public String getHeaderBitmapString(){
        return mHeaderBitmapString;
    }

    public UserData(String name, String userId, String comment, String follows, String followers
            , String posts, String favorites,String sex,String age, String evaluations, String taught
            , String period, String group, String iconBitmapString, String headerBitmapString) {
        mName = name;
        mUserId = userId;
        mComment = comment;
        mFollows = follows;
        mFollowers = followers;
        mPosts = posts;
        mFavorites = favorites;
        mSex = sex;
        mAge = age;
        mEvaluations = evaluations;
        mTaught = taught;
        mPeriod = period;
        mGroups = group;
        mIconBitmapString = iconBitmapString;
        mHeaderBitmapString = headerBitmapString;


    }
}
