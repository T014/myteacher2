package com.example.tyanai.myteacher2.Models;

public class ProvisionalMessageData {
    private String mCaseNum;
    private String mConfirmKey;
    private String mDate;
    private String mDetail;
    private String mKey;
    private String mMessage;
    private String mMoney;
    private String mReceiveUid;
    private String mSendUid;
    private String mTime;
    private String mTypePay;
    private String mBooleans;

    public String getCaseNum() {
        return mCaseNum;
    }
    public String getConfirmKey() {
        return mConfirmKey;
    }
    public String getDate() {
        return mDate;
    }
    public String getDetail() {
        return mDetail;
    }
    public String getKey() {
        return mKey;
    }
    public String getMessage() {
        return mMessage;
    }
    public String getMoney() {
        return mMoney;
    }
    public String getReceiveUid() {
        return mReceiveUid;
    }
    public String getSendUid() {
        return mSendUid;
    }
    public String getTime() {
        return mTime;
    }
    public String getTypePay() {
        return mTypePay;
    }
    public String getBooleans() {
        return mBooleans;
    }

    public ProvisionalMessageData(String caseNum,String confirmKey,String date,String detail
            ,String key,String message,String money,String receiveUid,String sendUid,String time
            ,String typePay,String booleans){

        mCaseNum = caseNum;
        mConfirmKey = confirmKey;
        mDate = date;
        mDetail = detail;
        mKey = key;
        mMessage = message;
        mMoney = money;
        mReceiveUid = receiveUid;
        mSendUid = sendUid;
        mTime = time;
        mTypePay = typePay;
        mBooleans = booleans;
    }
}
