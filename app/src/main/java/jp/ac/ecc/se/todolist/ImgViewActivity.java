package jp.ac.ecc.se.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ImgViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_view);
        //画像データ取得
        Intent intent = getIntent();
        Uri image = intent.getParcelableExtra("image");
        //画像表示パーツ生成
        ImageView viewImg = findViewById(R.id.viewImg);
        //画像表示
        viewImg.setImageURI(image);

        //ボタン表示
        Button backImg = findViewById(R.id.backImg);
        Button deleteImg = findViewById(R.id.deleteImg);
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

            }
        });
    }
}