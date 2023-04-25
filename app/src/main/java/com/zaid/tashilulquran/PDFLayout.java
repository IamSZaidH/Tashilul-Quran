package com.zaid.tashilulquran;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.File;

public class PDFLayout extends AppCompatActivity {
    private Byte totalPages;
    private Byte currPageNumberForBookmark;
    int currentPageNumber;
    private TextView currPage, totalPage,para_title;
    private ImageView addBookmark;
    private String name = null;
    private String name_dir;
    private String para_title_text;

    static {
        System.loadLibrary("keys");
    }

    private native String assetManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdflayout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        // Hide the status bar and the navigation bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        // Set the activity to use the full screen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Hide the navigation bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        para_title=findViewById(R.id.heading_title);
//        backbuton
        ImageView backBtn = findViewById(R.id.backbtn);
        backBtn.setOnClickListener(view -> onBackPressed());
        addBookmark = findViewById(R.id.addBookmark);
        currPage = findViewById(R.id.currPage);
        totalPage = findViewById(R.id.totalPage);
        Intent i = getIntent();
        if (i.hasExtra("name")) {
            name = i.getStringExtra("name");
            para_title_text=i.getStringExtra("para_title");
            totalPages = Common.getPages(name);
            totalPage.setText("/" + totalPages);
            currPageNumberForBookmark = totalPages;
            name_dir = Common.getDownDir()+"/" + name + ".pdf";
        } else {
            name = i.getStringExtra("Bname");
            para_title_text ="Para "+ name.substring(1);
            name_dir = Common.getDownDir()+"/" + name + ".pdf";
            totalPages = Common.getPages(name);
//            Log.d("jb", "totalPages= "+totalPages);
            totalPage.setText("/" + totalPages);
            currPageNumberForBookmark = (byte) i.getIntExtra("page_no", totalPages);
        }
        para_title.setText(para_title_text);
        PDFView pdfView = findViewById(R.id.pdfviewer);
        pdfView.useBestQuality(false);

        File file;
        Log.d("jarvis", "file "+name_dir);
        file = new File(name_dir);
        pdfView.fromFile(file).enableAnnotationRendering(false).defaultPage(currPageNumberForBookmark).password(assetManager()).swipeHorizontal(true).spacing(5).pageSnap(true).autoSpacing(true).pageFitPolicy(FitPolicy.WIDTH).fitEachPage(true).pageFling(true).onLoad(nbPages -> {
            currentPageNumber = pdfView.getCurrentPage();


            currPage.setText(String.valueOf(totalPages - currentPageNumber));
        }).onPageChange((page, pageCount) -> {
            currPageNumberForBookmark = (byte) page;
            currentPageNumber = totalPages - page;
            currPage.setText(String.valueOf(currentPageNumber));
            pdfView.setKeepScreenOn(true);
            Common.setResume(PDFLayout.this, name, currPageNumberForBookmark);
        }).
                load();


        addBookmark.setOnClickListener(view -> {
            // Create an AlertDialog.Builder object

            AlertDialog.Builder builder = new AlertDialog.Builder(PDFLayout.this);
            builder.setTitle("Add Bookmark");

            // Set up the input field
            final EditText input = new EditText(PDFLayout.this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            // Set up the OK button
            builder.setPositiveButton("OK", (dialog, which) -> {
                String bookmarkTitle = input.getText().toString();
                if (bookmarkTitle.isEmpty()) {
                    Toast.makeText(PDFLayout.this, "Bookmark Not Saved", Toast.LENGTH_SHORT).show();
                } else {
                    saveBookmark(bookmarkTitle, currPageNumberForBookmark);
                    MyDatabaseHandler db = new MyDatabaseHandler(PDFLayout.this);
                    db.addBookmark(currPageNumberForBookmark, name, bookmarkTitle);
                }
            });

            // Set up the Cancel button
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            // Show the dialog box
            builder.show();
        });
    }

    private void saveBookmark(String title, int pageNumber) {
        // TODO: Implement bookmark saving logic here
        Toast.makeText(PDFLayout.this, "BookmarkModel saved\n " + title + " \npage no. " + currentPageNumber, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Common.setResume(PDFLayout.this, name, currPageNumberForBookmark);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Common.setResume(PDFLayout.this, name, currPageNumberForBookmark);

    }
}
