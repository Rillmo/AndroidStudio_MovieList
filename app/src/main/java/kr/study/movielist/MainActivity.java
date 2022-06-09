package kr.study.movielist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

class DBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "movieList.db";
    private static final int DATABASE_VERSION = 2;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE movieList (_id INTEGER PRIMARY KEY AUTOINCREMENT,"+" title TEXT, director TEXT, actor TEXT);");
        db.execSQL("INSERT INTO movieList VALUES (1, '기생충','봉준호' ,'송강호, 이선균');");
        db.execSQL("INSERT INTO movieList VALUES (2, '설국열차','봉준호' ,'송강호');");
        db.execSQL("INSERT INTO movieList VALUES (3, '어벤져스','앤서니 루소, 조 루소' ,'크리스 에반스, 로버트 다우니 주니어');");
        db.execSQL("INSERT INTO movieList VALUES (4, '백두산','이해준' ,'하정우, 마동석');");
        db.execSQL("INSERT INTO movieList VALUES (5, '폼페이','폴 앤더슨' ,'킷 해링턴');");
        db.execSQL("INSERT INTO movieList VALUES (6, '신과함께','김용화' ,'하정우');");
        db.execSQL("INSERT INTO movieList VALUES (7, '쥬라기월드','콜린 트러보로' ,'크리스 프랫');");
        db.execSQL("INSERT INTO movieList VALUES (8, '검은 사제들','장재현' ,'강동원');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS movieList");
        onCreate(db);
    }
}

public class MainActivity extends AppCompatActivity {

    ListView listView;
    DBHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list01);
        helper = new DBHelper(this);
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM movieList", null);
        String [] from = {"title", "director"};
        int [] to = {android.R.id.text1, android.R.id.text2};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,cursor,from,to);
        listView = findViewById(R.id.list01);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                Object vo = (Object)adapterView.getAdapter().getItemId(i);  //리스트뷰의 포지션 내용을 가져옴.
                String index = vo.toString();
                intent.putExtra("index", index);
                startActivity(intent);
            }
        });


    }

}