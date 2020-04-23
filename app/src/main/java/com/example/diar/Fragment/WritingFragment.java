package com.example.diar.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public class WritingFragment extends Fragment {

    /*
    private String month;
    private String date;
    private String day;
    private String weather;
    private String word;
    private String title;
    private String content;

    private int version = 1;
    private SQLiteDatabase database;

    private Button writeBtn;
     */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        month = getView().findViewById(R.id.etMonth).toString();
        date = getView().findViewById(R.id.etDate).toString();
        day = getView().findViewById(R.id.etDay).toString();
        weather = getView().findViewById(R.id.etWeather).toString();
        word = getView().findViewById(R.id.etWord).toString();
        title = getView().findViewById(R.id.etTitle).toString();
        content = getView().findViewById(R.id.etContent).toString();

        writeBtn = (Button) getView().findViewById(R.id.writeBtn);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("GOT 1");
                helper.insertValues(database, month, date, day, weather, word, title, content);
                System.out.println("GOT 2");
            }
        });
         */
    }
}
