package com.example.tyanai.myteacher2.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tyanai.myteacher2.Models.ProvisionalMessageData;
import com.example.tyanai.myteacher2.R;

import java.util.ArrayList;

class ProvisionalMessageListViewHolder{
    ImageView provisionalMessageIconImageView;
    TextView provisionalMessageNameTextView;
    TextView provisionalMessageDateTextView;
    TextView provisionalMessageMoneyTextView;
    TextView provisionalMessageTypePayTextView;
    TextView provisionalMessageDetailTextView;
    TextView provisionalMessageTextView;
    Button provisionalMessageOkButton;
    Button provisionalMessageNoButton;
    RelativeLayout provisionalMessageRelativeLayout;
}

public class ProvisionalMessageListAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private int layoutId;
    private ArrayList<ProvisionalMessageData> provisionalMessageArrayList = new ArrayList<ProvisionalMessageData>();

    public ProvisionalMessageListAdapter(Context context,int layoutId){
        super();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId = layoutId;
    }

    @Override
    public View getView(final int position, View convertView,final ViewGroup parent){
        String iconBitmapString = provisionalMessageArrayList.get(position).getIconBitmapString();
        String name = provisionalMessageArrayList.get(position).getUserName();
        String date = provisionalMessageArrayList.get(position).getDate();
        String money = provisionalMessageArrayList.get(position).getMoney();
        String typePay = provisionalMessageArrayList.get(position).getTypePay();
        String detail = provisionalMessageArrayList.get(position).getDetail();
        String message = provisionalMessageArrayList.get(position).getMessage();
        String sendUid = provisionalMessageArrayList.get(position).getSendUid();
        String watchUid = provisionalMessageArrayList.get(position).getWatchUid();
        String booleans = provisionalMessageArrayList.get(position).getBooleans();

        ProvisionalMessageListViewHolder provisionalMessageListViewHolder;
        if (convertView == null) {
            // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
            convertView = inflater.inflate(layoutId, parent, false);
            // ViewHolder を生成
            provisionalMessageListViewHolder = new ProvisionalMessageListViewHolder();
            provisionalMessageListViewHolder.provisionalMessageIconImageView = (ImageView)convertView.findViewById(R.id.provisionalMessageIconImageView);
            provisionalMessageListViewHolder.provisionalMessageNameTextView = (TextView)convertView.findViewById(R.id.provisionalMessageNameTextView);
            provisionalMessageListViewHolder.provisionalMessageDateTextView = (TextView)convertView.findViewById(R.id.provisionalMessageDateTextView);
            provisionalMessageListViewHolder.provisionalMessageMoneyTextView = (TextView)convertView.findViewById(R.id.provisionalMessageMoneyTextView);
            provisionalMessageListViewHolder.provisionalMessageTypePayTextView = (TextView)convertView.findViewById(R.id.provisionalMessageTypePayTextView);
            provisionalMessageListViewHolder.provisionalMessageDetailTextView = (TextView)convertView.findViewById(R.id.provisionalMessageDetailTextView);
            provisionalMessageListViewHolder.provisionalMessageTextView = (TextView)convertView.findViewById(R.id.provisionalMessageTextView);
            provisionalMessageListViewHolder.provisionalMessageOkButton = (Button) convertView.findViewById(R.id.provisionalMessageOkButton);
            provisionalMessageListViewHolder.provisionalMessageNoButton = (Button) convertView.findViewById(R.id.provisionalMessageNoButton);
            provisionalMessageListViewHolder.provisionalMessageRelativeLayout = (RelativeLayout)convertView.findViewById(R.id.provisionalMessageRelativeLayout);
            convertView.setTag(provisionalMessageListViewHolder);
        }
        else {
            provisionalMessageListViewHolder = (ProvisionalMessageListViewHolder) convertView.getTag();
        }
        if (message!=null){
            provisionalMessageListViewHolder.provisionalMessageTextView.setText(message);
        }
        if (name != null){
            provisionalMessageListViewHolder.provisionalMessageNameTextView.setText(name);
        }
        if (date != null){
            provisionalMessageListViewHolder.provisionalMessageDateTextView.setText(date);
        }
        if (money!=null){
            provisionalMessageListViewHolder.provisionalMessageMoneyTextView.setText(money);
        }
        if (typePay!=null){
            provisionalMessageListViewHolder.provisionalMessageTypePayTextView.setText(typePay);
        }
        if (detail!=null){
            provisionalMessageListViewHolder.provisionalMessageDetailTextView.setText(detail);
        }
        provisionalMessageListViewHolder.provisionalMessageOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView)parent).performItemClick(view,position,R.id.provisionalMessageOkButton);
            }
        });
        provisionalMessageListViewHolder.provisionalMessageNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView)parent).performItemClick(view,position,R.id.provisionalMessageNoButton);
            }
        });
        provisionalMessageListViewHolder.provisionalMessageOkButton.setVisibility(View.VISIBLE);
        if (0==position){
            provisionalMessageListViewHolder.provisionalMessageNoButton.setVisibility(View.VISIBLE);
            if (sendUid.equals(watchUid)){
                //自分で送ったやつ
                provisionalMessageListViewHolder.provisionalMessageRelativeLayout.setBackgroundColor(Color.rgb(127,127,255));
                provisionalMessageListViewHolder.provisionalMessageOkButton.setVisibility(View.GONE);
            }else {
                provisionalMessageListViewHolder.provisionalMessageRelativeLayout.setBackgroundColor(Color.rgb(255,255,255));
            }
        }else{
            provisionalMessageListViewHolder.provisionalMessageOkButton.setVisibility(View.GONE);
            provisionalMessageListViewHolder.provisionalMessageNoButton.setVisibility(View.GONE);
            if (sendUid.equals(watchUid)){
                //自分で送ったやつ
                provisionalMessageListViewHolder.provisionalMessageRelativeLayout.setBackgroundColor(Color.rgb(127,127,255));
            }else {
                provisionalMessageListViewHolder.provisionalMessageRelativeLayout.setBackgroundColor(Color.rgb(255,255,255));
            }
        }
        if (booleans.equals("ok")){
            provisionalMessageListViewHolder.provisionalMessageOkButton.setText("支払う");
        }else{
            provisionalMessageListViewHolder.provisionalMessageOkButton.setText("契約する");
        }
        byte[] iconImageBytes = Base64.decode(iconBitmapString,Base64.DEFAULT);
        if(iconImageBytes.length!=0){
            Bitmap iconImageBitmap = BitmapFactory.decodeByteArray(iconImageBytes,0, iconImageBytes.length).copy(Bitmap.Config.ARGB_8888,true);
            provisionalMessageListViewHolder.provisionalMessageIconImageView.setImageBitmap(iconImageBitmap);
        }
        return convertView;
    }

    @Override
    public int getCount() {
        // List<String> imgList の全要素数を返す
        return provisionalMessageArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setProvisionalMessageArrayList(ArrayList<ProvisionalMessageData> list){
        provisionalMessageArrayList = list;
    }
}
