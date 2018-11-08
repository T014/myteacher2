package com.example.tyanai.myteacher2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.tyanai.myteacher2.Models.Const;
import com.example.tyanai.myteacher2.R;
import com.example.tyanai.myteacher2.Screens.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ContractFragment extends Fragment {
    public static final String TAG = "ContractFragment";

    String caseNum;
    String postKey;
    String postUid;
    public static TextView cldTextView;
    RadioGroup costTypeRadioGroup;
    RadioButton typeTimeRadioButton;
    RadioButton typeFixedRadiobutton;
    EditText moneyEditText;
    EditText detailEditText;
    EditText messageEditText;
    Button backPostButton;
    Button cancelButton;
    Button sendButton;
    Button cldButton;
    DatabaseReference mDataBaseReference;
    private DatabaseReference requestRef;
    private DatabaseReference confirmRef;
    private DatabaseReference confirmKeyRef;
    FirebaseUser user;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_contract,container,false);

        cldTextView = (TextView)v.findViewById(R.id.cldTextView);
        costTypeRadioGroup = (RadioGroup)v.findViewById(R.id.costTypeRadioGroup);
        typeTimeRadioButton = (RadioButton)v.findViewById(R.id.typeTimeRadioButton);
        typeFixedRadiobutton = (RadioButton)v.findViewById(R.id.typeFixedRadiobutton);
        moneyEditText = (EditText)v.findViewById(R.id.moneyEditText);
        detailEditText = (EditText)v.findViewById(R.id.detailEditText);
        messageEditText = (EditText)v.findViewById(R.id.messageEditText);
        backPostButton = (Button)v.findViewById(R.id.backPostButton);
        cancelButton = (Button)v.findViewById(R.id.cancelButton);
        sendButton = (Button)v.findViewById(R.id.sendButton);
        cldButton = (Button)v.findViewById(R.id.cldButton);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.mToolbar.setTitle("契約内容");
        user = FirebaseAuth.getInstance().getCurrentUser();

        mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        requestRef = mDataBaseReference.child(Const.RequestPATH);
        confirmRef = mDataBaseReference.child(Const.ConfirmPATH);
        confirmKeyRef = mDataBaseReference.child(Const.ConfirmKeyPATH);


        Bundle caseNumBundle = getArguments();
        //roomKey
        caseNum = caseNumBundle.getString("caseNum");
        postKey = caseNumBundle.getString("key");
        postUid = caseNumBundle.getString("postUid");


        cldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ダイアログクラスをインスタンス化
                CustomDialogFragment dialog = new CustomDialogFragment();
                // 表示  getFagmentManager()は固定、sampleは識別タグ
                dialog.show(getFragmentManager(), "sample");
            }
        });


        backPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("key",postKey);
                bundle.putString("screenKey","timeLine");
                DetailsFragment fragmentDetails = new DetailsFragment();
                fragmentDetails.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,fragmentDetails,DetailsFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestRef.child(caseNum).removeValue();

                Bundle bundle = new Bundle();
                bundle.putString("key",postKey);
                bundle.putString("screenKey","timeLine");
                DetailsFragment fragmentDetails = new DetailsFragment();
                fragmentDetails.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,fragmentDetails,DetailsFragment.TAG);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String typePay="";

                int checkedId = costTypeRadioGroup.getCheckedRadioButtonId();
                if (-1 != checkedId) {
                    if (checkedId==typeTimeRadioButton.getId()){
                        typePay = typeTimeRadioButton.getText().toString();
                    }else if (checkedId==typeFixedRadiobutton.getId()){
                        typePay = typeFixedRadiobutton.getText().toString();
                    }

                    String money = moneyEditText.getText().toString();
                    String detail = detailEditText.getText().toString();
                    String message = messageEditText.getText().toString();
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
                    String time = sdf.format(cal.getTime());

                    String date = cldTextView.getText().toString();
                    if (money!=null){
                        if (date!=null){
                            if (detail!=null){
                                if (message!=null){
                                    if (time!=null){
                                        Map<String,Object> data = new HashMap<>();
                                        //postKey
                                        String confirmKey = confirmRef.push().getKey();

                                        data.put("typePay", typePay);
                                        data.put("money",money);
                                        data.put("key", confirmKey);
                                        data.put("date", date);
                                        data.put("time",time);
                                        data.put("detail", detail);
                                        data.put("message", message);
                                        data.put("sendUid", user.getUid());
                                        data.put("receiveUid",postUid);
                                        data.put("caseNum",caseNum);
                                        data.put("confirmKey",confirmKey);
                                        data.put("booleans","");
                                        data.put("postKey",postKey);
                                        data.put("postUid",postUid);

                                        Map<String,Object> childUpdates = new HashMap<>();
                                        childUpdates.put(confirmKey,data);
                                        confirmRef.child(caseNum).updateChildren(childUpdates);


                                        //管理画面

                                        Map<String,Object> datas = new HashMap<>();

                                        datas.put("time",time);
                                        datas.put("uid",postUid);
                                        datas.put("postKey",postKey);
                                        datas.put("caseNum",caseNum);

                                        confirmKeyRef.child(user.getUid()).child(caseNum).updateChildren(datas);


                                        Map<String,Object> mdatas = new HashMap<>();

                                        mdatas.put("time",time);
                                        mdatas.put("uid",user.getUid());
                                        mdatas.put("postKey",postKey);
                                        mdatas.put("caseNum",caseNum);

                                        confirmKeyRef.child(postUid).child(caseNum).updateChildren(mdatas);


//                                        Bundle bundle = new Bundle();
//                                        bundle.putString("key",postKey);
//                                        bundle.putString("screenKey","timeLine");
//                                        DetailsFragment fragmentDetails = new DetailsFragment();
//                                        fragmentDetails.setArguments(bundle);
//                                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                                        transaction.replace(R.id.container,fragmentDetails,DetailsFragment.TAG);
//                                        transaction.addToBackStack(null);
//                                        transaction.commit();

                                        Bundle cNumBundle = new Bundle();
                                        cNumBundle.putString("caseNum",caseNum);
                                        ProvisionalMessageFragment fragmentProvisionalMessage = new ProvisionalMessageFragment();
                                        fragmentProvisionalMessage.setArguments(cNumBundle);
                                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.container,fragmentProvisionalMessage,ProvisionalMessageFragment.TAG);
                                        transaction.addToBackStack(null);
                                        transaction.commit();




                                        //notification


                                    }else {
                                     //time
                                    }
                                }else {
                                    //message
                                }
                            }else {
                                //detail
                            }

                        }else{
                            //date
                        }
                    }else {
                        //money
                    }






                } else {

                    //radiobutton選択してくれ
                }











            }
        });
    }
}
