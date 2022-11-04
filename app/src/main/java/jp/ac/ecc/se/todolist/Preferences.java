package jp.ac.ecc.se.todolist;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Preferences extends AppCompatActivity {
    //Preference宣言
    SharedPreferences pref;
    ArrayList<String> titleList;
    ArrayList<String> memoList;


    //Listを読み込む
    Preferences(Context context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        //titleListを読み込む
        Set<String> tArray = pref.getStringSet("ToDoTitle", null);
        if (tArray != null) titleList = new ArrayList<String>(tArray);
        else titleList = new ArrayList<>();
    }

    /////titleListを扱う範囲始まり/////

    //titleを追加する
    public void setPreTitle(String title) {
        titleList.add(title);
        //リストを保存する
        HashSet<String> tArray = new HashSet<String>(titleList);
        pref.edit().putStringSet("ToDoTitle", null).apply();
        pref.edit().putStringSet("ToDoTitle", tArray).apply();
        tArray.clear();
    }

    //titleのリストを返す
    public ArrayList<String> getPreTitle() {
        return titleList;
    }

    //titleListからtitleを削除する
    public void deletePreTitle(String title) {
        titleList.remove(title);
        HashSet<String> tArray = new HashSet<String>(titleList);
        pref.edit().putStringSet("ToDoTitle", null).apply();
        pref.edit().putStringSet("ToDoTitle", tArray).apply();
        tArray.clear();
    }

    /////titleListを扱う範囲終わり/////

    /////MemoListを扱う範囲始まり/////

    //titleに適応したMemoListを作成
    public void setPreMemo(String title, String memo, String uri) {
        //MemoListを読み込む
        memoList = new ArrayList<>();
        memoList.add(memo);
        memoList.add(uri);
        HashSet<String> mArray = new HashSet<String>(memoList);
        pref.edit().putStringSet(title, null).apply();
        pref.edit().putStringSet(title, mArray).apply();
        mArray.clear();
    }
    public void deletePreMemoUri(String title){
        Set<String> mArray = pref.getStringSet(title, null);
        memoList = new ArrayList<String>(mArray);
        memoList.set(1,"URI");
        mArray = new HashSet<String>(memoList);
        pref.edit().putStringSet(title, mArray).apply();
        mArray.clear();
    }
    //titleに適応したMemoListを返す
    public ArrayList<String> getPreMemo(String title){
        Set<String> mArray = pref.getStringSet(title, null);
        if (mArray != null) memoList = new ArrayList<String>(mArray);
        else memoList = new ArrayList<>();
        return memoList;
    }
    //titleに適応したMemoListを削除
    public void deletePreMemo(String title) {
        pref.edit().putStringSet(title, null).apply();
    }

    /////memoListを扱う範囲終わり/////
}
