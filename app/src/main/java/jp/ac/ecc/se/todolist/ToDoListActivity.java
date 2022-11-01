package jp.ac.ecc.se.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ToDoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        //画面上のパーツ生成
        ListView ListView = findViewById(R.id.ListView);
        Button newListButton = findViewById(R.id.newListButton);
        ArrayList<String> dataList = new ArrayList<>();
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

    }
}