package jp.ac.ecc.se.todolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
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
import java.util.Date;

public class InPutToDoActivity extends AppCompatActivity {
    //カメラ起動のリクエストコード宣言
    final int CAMERA_RESULT = 100;
    //画像変数に画像情報を格納するURI変数を宣言する.
    Uri imageUri;

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
        //画面上のパーツ生成
        EditText getTitle = findViewById(R.id.getTitle);
        EditText getMemo = findViewById(R.id.getMemo);
        FloatingActionButton imgViewButton = findViewById(R.id.imgViewButton);
        Button cameraButton = findViewById(R.id.cameraButton);
        Button backButton = findViewById(R.id.backButton);
        Button finishButton = findViewById(R.id.finishButton);
        Button clearButton = findViewById(R.id.clearButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
                //画像表示画面へ遷移
                Intent intentImg = new Intent(getApplicationContext(),ImgViewActivity.class);
                intentImg.putExtra("image",imageUri);
                startActivity(intentImg);
            }
        });

    }
}