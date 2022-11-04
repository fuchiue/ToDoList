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
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImgViewActivity extends AppCompatActivity {

    //Uriのパスを取得する
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Path createPath(Uri image,Context context){
        Path path = null;
        //ファイルのパスを取得
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = context.getContentResolver().query(image,projection,null,null,null);
        if(cursor != null){
            String pathStr = null;
            if(cursor.moveToFirst()){
                pathStr = cursor.getString(0);
            }
            cursor.close();
            if(pathStr != null){
                File file = new File(pathStr);
                path = file.toPath();
            }
        }
        return path;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_view);
        //画像データ取得
        Intent intent = getIntent();
        Uri image = intent.getParcelableExtra("image");
        String title = intent.getStringExtra("title");
        //画像表示パーツ生成
        ImageView viewImg = findViewById(R.id.viewImg);
        //画像表示
        viewImg.setImageURI(image);

        //ボタンパーツ宣言
        Button backImg = findViewById(R.id.backImg);
        Button deleteImg = findViewById(R.id.deleteImg);
        //パス取得
        Path path=createPath(image,getApplicationContext());

        //データが存在しないときボタンを非表示
        if(!Files.exists(path)){
            deleteImg.setVisibility(View.INVISIBLE);
        }

        //戻るボタンが押されたとき
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //削除ボタンが押されたとき
        deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    //タイトルがあればUriを空文字にする
                    if(title!=null){
                        Preferences pre = new Preferences(getApplicationContext());
                        pre.deletePreMemoUri(title);
                        Toast.makeText(getApplicationContext(), "削除しました", Toast.LENGTH_SHORT).show();
                    }
                    Files.deleteIfExists(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }
}