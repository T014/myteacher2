package com.example.tyanai.myteacher2.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

public class CustomDialogFragment extends DialogFragment {

    String dateStr="";

    // ダイアログが生成された時に呼ばれるメソッド ※必須
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 今日の日付のカレンダーインスタンスを取得
        final Calendar calendar = Calendar.getInstance();

        // ダイアログ生成  DatePickerDialogのBuilderクラスを指定してインスタンス化します
        DatePickerDialog dateBuilder = new DatePickerDialog(
                getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // 選択された年・月・日を整形 ※月は0-1なので+1している

                        if (month+1<10 && dayOfMonth<10){
                            dateStr = year + "/" + ("0"+(month + 1)) + "/" + ("0"+dayOfMonth);
                        }else if (month+1<10 && dayOfMonth>9){
                            dateStr = year + "/" + ("0"+(month + 1)) + "/" + (dayOfMonth);
                        }else if(month+1>9 && dayOfMonth<10){
                            dateStr = year + "/" + (month + 1) + "/" + ("0"+dayOfMonth);
                        }else if(month+1>9 && dayOfMonth>9){
                            dateStr = year + "/" + (month + 1) + "/" + (dayOfMonth);
                        }

                        MakePostFragment.dateTextView.setText(dateStr);
                    }
                },
                calendar.get(Calendar.YEAR), // 初期選択年
                calendar.get(Calendar.MONTH), // 初期選択月
                calendar.get(Calendar.DAY_OF_MONTH) // 初期選択日
        );

        // dateBulderを返す
        return dateBuilder;
    }
}
