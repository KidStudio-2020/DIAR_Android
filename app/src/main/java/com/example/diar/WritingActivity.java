package com.example.diar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diar.Database.DBHelper;
import com.example.diar.Database.LetterDB;

import java.util.ArrayList;

public class WritingActivity extends AppCompatActivity {

    private EditText month;
    private EditText date;
    private EditText day;
    private EditText weather;
    private EditText word;
    private EditText title_test;
    private EditText content_test;

    private DBHelper helper;
    private SQLiteDatabase database;

    private Button writeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        helper = new DBHelper(WritingActivity.this, "letter", null, 1);
        database = helper.getWritableDatabase();

        Button check = findViewById(R.id.check);
        month = findViewById(R.id.etMonth);
        date = findViewById(R.id.etDate);
        day = findViewById(R.id.etDay);
        weather = findViewById(R.id.etWeather);
        word = findViewById(R.id.etWord);
        title_test = findViewById(R.id.etTitle);
        content_test = findViewById(R.id.etContent);

        writeBtn = (Button) findViewById(R.id.writeBtn);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = title_test.toString();
                String create_date = month.getText().toString() + " 월 "+day.getText().toString() + "일" ;
                String content = content_test.getText().toString();
                Double lat = 0.0;
                Double lng = 0.0;
                String path = "test";
                if (title.equals("")) {
                    Toast.makeText(WritingActivity.this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    helper.insertLetter(lat, lng, create_date, content, path, title);
                    Intent intent = new Intent(WritingActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer resultAll = new StringBuffer();
                ArrayList<LetterDB> letterDBArrayList = helper.getAllLetter();
                for (int i = 0; i < letterDBArrayList.size(); i++) {
                    resultAll.append(letterDBArrayList.get(i).toString());
                }
                Toast.makeText(WritingActivity.this, resultAll.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
