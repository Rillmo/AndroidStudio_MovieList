package kr.study.movielist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;


public class SubActivity extends AppCompatActivity {

    DBHelper helper;
    SQLiteDatabase db;

    ImageView movieImg;
    TextView title;
    TextView info01;
    TextView info02;
    Button back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub01);

        Intent intent2 = getIntent();
        String index = intent2.getStringExtra("index"); // intent로 전달된 index 불러오기

        helper = new DBHelper(this);
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM movieList", null);

        String[] movieData = new String[3];
        if (cursor.moveToFirst()) {    //항상 처음에서 시작
            do {
                if (cursor.getInt(0) == Integer.valueOf(index)) { // index가 일치하는 column 탐색
                    movieData[0] = cursor.getString(1);
                    movieData[1] = cursor.getString(2);
                    movieData[2] = cursor.getString(3);
                }
            } while (cursor.moveToNext());
        }

        // 영화사진 출력
        movieImg = (ImageView) findViewById(R.id.image);
        int resId = getResources().getIdentifier("movie"+index, "drawable", getPackageName());
        movieImg.setImageResource(resId);

        // 영화정보 출력
        title = (TextView) findViewById(R.id.title01);
        title.setText(movieData[0]);
        info01 = (TextView) findViewById(R.id.info01);
        info01.setText("감독 : " + movieData[1]);
        info02 = (TextView) findViewById(R.id.info02);
        info02.setText("출연 : " + movieData[2]);


        // 돌아가기 버튼
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent03 = new Intent(SubActivity.this, MainActivity.class);
                startActivity(intent03);

            }
        });

    }
}
