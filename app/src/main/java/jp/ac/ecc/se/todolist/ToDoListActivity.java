package jp.ac.ecc.se.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ToDoListActivity extends AppCompatActivity {

    @SuppressLint("MutatingSharedPrefs")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        //コンテキスト宣言
        Context context = getApplicationContext();
        //Preference宣言
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        //inPutからのintent取得
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        //画面上のパーツ生成
        ListView ListView = findViewById(R.id.ListView);
        Button newListButton = findViewById(R.id.newListButton);
        ArrayList<String> dataList;
        //葉入れるの
        Set<String> sArray = pref.getStringSet("ToDoTitle", null );
        if ( sArray != null ) dataList = new ArrayList<String>( sArray );
        else dataList = new ArrayList<>();
        //配列セット用のアダプタ宣言
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,dataList);
        ListView.setAdapter(adapter);

        if(title!=null){
            //Titleを取得していたらリストに追加
            dataList.add(title);
            //リストを保存する
            sArray = new HashSet<String>(dataList);
            pref.edit().putStringSet("ToDoTitle", null ).apply();
            pref.edit().putStringSet("ToDoTitle", sArray ).apply();
            sArray.clear();
        }

        //リストを表示
        ListView.setAdapter(adapter);


        //新規作成ボタンが押されたとき
        newListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //新規作成画面へ遷移
                Intent intentInp = new Intent(getApplicationContext(),InPutToDoActivity.class);
                startActivity(intentInp);
            }
        });


    }
}