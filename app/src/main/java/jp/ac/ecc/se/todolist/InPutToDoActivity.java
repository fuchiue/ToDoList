package jp.ac.ecc.se.todolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InPutToDoActivity extends AppCompatActivity {
    //カメラ起動のリクエストコード宣言
    final int CAMERA_RESULT = 100;
    //画像変数に画像情報を格納するURI変数を宣言する.
    Uri imageUri;
    //改行コードを取得
    static final String BR = System.getProperty("line.separator");

    //写真撮影
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_RESULT && resultCode == RESULT_OK){
            Toast.makeText(getApplicationContext(),"撮影しました。",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_put_to_do);
        //intent取得
        Intent intent = getIntent();
        String toTitle = intent.getStringExtra("title");

        //Preferencesのinstance生成
        Preferences pre = new Preferences(getApplicationContext());

        //画面上のパーツ生成
        EditText getTitle = findViewById(R.id.getTitle);
        EditText getMemo = findViewById(R.id.getMemo);
        FloatingActionButton imgViewButton = findViewById(R.id.imgViewButton);
        Button cameraButton = findViewById(R.id.cameraButton);
        Button backButton = findViewById(R.id.backButton);
        Button finishButton = findViewById(R.id.finishButton);
        Button clearButton = findViewById(R.id.clearButton);

        //編集ボタンから来た場合
        if(toTitle!=null){
            ArrayList<String> memoList = pre.getPreMemo(toTitle);
            getMemo.setText(memoList.get(0).replace("<KAIbrKAI>",BR));
            getTitle.setText(toTitle);
            imageUri = Uri.parse(memoList.get(1));
        }

        // 戻るボタンが押されたら
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //クリアボタンが押されたら
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTitle.setText(null);
                getMemo.setText(null);
            }
        });

        //cameraButtonが押されたときカメラを起動させる
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fileName = "Traning"+timestamp+"_.jpg";
                //パラメータ設定
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE,fileName);
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

                //画像情報のURIを生成する
                imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);

                startActivityForResult(intent,CAMERA_RESULT);
            }
        });

        //フロートボタンが押されたとき
        imgViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUri != null) {
                    if (!imageUri.toString().equals("URI")) {
                        //画像表示画面へ遷移
                        Intent intentImg = new Intent(getApplicationContext(), ImgViewActivity.class);
                        intentImg.putExtra("image", imageUri);
                        startActivity(intentImg);
                    }else{
                        Toast.makeText(getApplicationContext(),"画像がありません",Toast.LENGTH_SHORT).show();
                    }
                }else{
                Toast.makeText(getApplicationContext(),"画像がありません",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //完了ボタンが押されたとき
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = getTitle.getText().toString();

                if(!title.equals("")) {
                    //---もし同じタイトルがあったときの分岐
                    ArrayList<String> titleList = pre.getPreTitle();
                    if(!titleList.contains(title) || title.equals(toTitle)) {
                        //編集ボタンから遷移の場合元データを削除
                        if (toTitle != null) {
                            pre.deletePreTitle(toTitle);
                            pre.deletePreMemo(toTitle);
                        }
                        //タイトルをtitleListに保存
                        pre.setPreTitle(title);
                        //改行コードを置換
                        String memo = getMemo.getText().toString().replace(BR, "<KAIbrKAI>");
                        String img = (imageUri == null) ? "URI" : imageUri.toString();
                        //メモ情報と写真情報をMemoListに保存
                        pre.setPreMemo(title, memo, img);
                        //トーストを表示
                        Toast.makeText(getApplicationContext(), "保存しました", Toast.LENGTH_SHORT).show();
                        //ToDoListに遷移
                        Intent intent = new Intent(getApplicationContext(), ToDoListActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "同じタイトルが既に存在します", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    //トーストを表示
                    Toast.makeText(getApplicationContext(), "titleを入力してください", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}