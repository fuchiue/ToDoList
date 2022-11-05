package jp.ac.ecc.se.todolist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {
    Uri imageUri;
    Path path = null;
    //改行コードを取得
    static final String BR = System.getProperty("line.separator");

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        //ToDoListからタイトルを取得
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        //Preferencesのinstance生成
        Preferences pre = new Preferences(getApplicationContext());
        String memoText = null;
        ArrayList<String> memoList;
        memoList = pre.getPreMemo(title);
        //正しくリストが生成されているかの確認
        if (memoList.size() == 2) {
            memoText = memoList.get(0);
            imageUri = Uri.parse(memoList.get(1));
        }

        //タイトルを表示
        setTitle(title);
        //画面上のパーツ生成
        TextView memoView = findViewById(R.id.memoView);
        FloatingActionButton imgViewButton2 = findViewById(R.id.imgViewButton2);
        Button backButton = findViewById(R.id.backButton2);
        Button variantButton = findViewById(R.id.variantButton);
        Button deleteButton = findViewById(R.id.deleteButton);
        //メモ表示
        memoView.setText(memoText.replace("<KAIbrKAI>",BR));

        //フロートボタンが押されたとき
        imgViewButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //パスに画像がなければnullにする。
                Preferences pre2 = new Preferences(getApplicationContext());
                ArrayList<String> List = pre2.getPreMemo(title);

                if (List.get(1).equals("URI")) {
                    imageUri = null;
                }
                if (imageUri != null) {
                    //画像表示画面へ遷移
                    Intent intentImg = new Intent(getApplicationContext(), ImgViewActivity.class);
                    intentImg.putExtra("title", title);
                    intentImg.putExtra("image", imageUri);
                    startActivity(intentImg);
                }else{
                    Toast.makeText(getApplicationContext(),"画像がありません",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //戻るボタンが押されたら
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //編集ボタンが押されたら
        variantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentIN = new Intent(getApplicationContext(),InPutToDoActivity.class);
                intentIN.putExtra("title",title);
                startActivity(intentIN);
            }
        });

        //削除ボタンが押されたら
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pre.deletePreMemo(title);
                pre.deletePreTitle(title);
                Intent intentToDo = new Intent(getApplicationContext(),ToDoListActivity.class);
                startActivity(intentToDo);
            }
        });
    }
}