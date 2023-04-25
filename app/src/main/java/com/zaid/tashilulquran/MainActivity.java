package com.zaid.tashilulquran;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    LinearLayout resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        LinearLayout pbtn = findViewById(R.id.pIndex);
        LinearLayout sbtn = findViewById(R.id.sIndex);
        LinearLayout donate = findViewById(R.id.Donate);
        LinearLayout bookmark = findViewById(R.id.BookMarks);
        ImageView shareBtn=findViewById(R.id.shareBtn);
         resume= findViewById(R.id.ResumePara);
        updateResumeVisibility(); // call the method to hide/show the view
        if (Common.empty){
            Common.init();
        }
//        para button listener
        pbtn.setOnClickListener(view -> {
            Intent obj = new Intent(MainActivity.this, Para.class);
            startActivity(obj);
        });
        sbtn.setOnClickListener(view -> {
            Intent obj = new Intent(MainActivity.this, Sura.class);
            startActivity(obj);
        });
        donate.setOnClickListener(view -> {
            Intent obj = new Intent(MainActivity.this, Donate.class);
            startActivity(obj);
        });
        bookmark.setOnClickListener(view -> {
            Intent obj = new Intent(MainActivity.this, Bookmark.class);
            startActivity(obj);
        });
        resume.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, PDFLayout.class);
            i.putExtra("Bname", Common.getResumeParaName(getApplicationContext()));
            i.putExtra("page_no", Common.getResumePageNo(getApplicationContext()));
            startActivity(i);
        });
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.mShareText("Discover the beauty and wisdom of the Quran with Tashilul Quran, a user-friendly Quran and Tafsir reader for Android devices.\nCheck out the app now and start exploring.\n https://play.google.com/store/apps/details?id=" + getPackageName() + " \n", MainActivity.this);

            }
        });
// initialize


    }
    private void updateResumeVisibility() {
        if (Common.getResumePageNo(MainActivity.this) == -1) {
            resume.setVisibility(View.GONE);
        } else {
            resume.setVisibility(View.VISIBLE);
        }
    }

    // call this method whenever the condition for hiding/showing the view might change


    @Override
    protected void onResume() {
        super.onResume();
        updateResumeVisibility();
    }
}