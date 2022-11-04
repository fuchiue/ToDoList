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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ToDoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        //Preferencesのinstance生成
        Preferences pre = new Preferences(getApplicationContext());

        //画面上のパーツ生成
        ListView ListView = findViewById(R.id.ListView);
        Button newListButton = findViewById(R.id.newListButton);
        ArrayList<String> dataList = pre.getPreTitle();
        //配列セット用のアダプタ宣言
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,dataList);
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

        //リストの任意のタイトルが押されたとき
        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //押された場所のタイトルを取得
                TextView tv = (TextView) view;
                //トースト表示
                Toast.makeText(getApplicationContext(),tv.getText(),Toast.LENGTH_SHORT).show();
                //ListViewページに遷移
                Intent intent = new Intent(getApplicationContext(),ListViewActivity.class);
                intent.putExtra("title",tv.getText());
                startActivity(intent);
            }
        });
    }
}