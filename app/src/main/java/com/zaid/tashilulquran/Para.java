package com.zaid.tashilulquran;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Para extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 123;
    private Button button;

    static {
        System.loadLibrary("keys");
    }

    private native String getUrl();

    public final static int[] btnArr = new int[]{
            R.id.p1,
            R.id.p2,
            R.id.p3,
            R.id.p4,
            R.id.p5,
            R.id.p6,
            R.id.p7,
            R.id.p8,
            R.id.p9,
            R.id.p10,
            R.id.p11,
            R.id.p12,
            R.id.p13,
            R.id.p14,
            R.id.p15,
            R.id.p16,
            R.id.p17,
            R.id.p18,
            R.id.p19,
            R.id.p20,
            R.id.p21,
            R.id.p22,
            R.id.p23,
            R.id.p24,
            R.id.p25,
            R.id.p26,
            R.id.p27,
            R.id.p28,
            R.id.p29,
            R.id.p30,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_para);
//        Code here
        Common.init();
        PRDownloader.initialize(Para.this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

    }


    private void createFolderIfNotExists() {
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Tashilul Quran");

        if (!folder.exists()) {
            folder.mkdir();
        }

        File subFolder = new File(folder, "Para");

        if (!subFolder.exists()) {
            subFolder.mkdir();
        }
    }

    public void onClick(View v) {
        for (int btn : btnArr) {
            if (btn == v.getId()) {
                button = findViewById(btn);
                break;
            }
        }
        requestPermission();


    }


    private void openPDF(Button button) {
        createFolderIfNotExists();
        File pdfFile = new File(Common.getDownDir(), button.getContentDescription() +".pdf");
        if (pdfFile.exists()) {
            Toast.makeText(this, "Opening " + button.getText(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Para.this, PDFLayout.class);
            i.putExtra("name", button.getContentDescription());
            i.putExtra("para_title", button.getText());
            startActivity(i);
        } else {
            if (isNetworkConnected(Para.this)){

            downloadPDF();
            }
            else{
                Toast.makeText(Para.this, "No internet connection.\nIt needs only one time for download the para", Toast.LENGTH_SHORT).show();
            }
        }


    }
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    private void downloadPDF() {
        ProgressDialog progressDialog=new ProgressDialog(Para.this);
        progressDialog.setMessage("Downloading "+button.getText());
        progressDialog.setCancelable(false);
        progressDialog.show();
        Log.d("durl", "url-> "+getUrl() + button.getContentDescription());
        PRDownloader.download(getUrl() + button.getContentDescription()+".pdf", Common.getDownDir(), button.getContentDescription() +".pdf")
                .build()
                .setOnStartOrResumeListener(() -> {

                })
                .setOnPauseListener(() -> {

                })
                .setOnCancelListener(() -> {

                })
                .setOnProgressListener(progress -> {
                long percentage=progress.currentBytes*100/progress.totalBytes;
                progressDialog.setMessage("Downloading "+button.getText()+" : "+percentage+"%");
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        progressDialog.dismiss();

                        Toast.makeText(Para.this, "Downloading Completed", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Error error) {
                        progressDialog.dismiss();
                        Toast.makeText(Para.this, "Not Download", Toast.LENGTH_SHORT).show();
                    }

                });
    }


    private void requestPermission() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        List<String> permissionsToRequest = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }

        if (!permissionsToRequest.isEmpty()) {
            String[] permissionsArray = new String[permissionsToRequest.size()];
            permissionsArray = permissionsToRequest.toArray(permissionsArray);
            ActivityCompat.requestPermissions(this, permissionsArray, PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted, do your work here
            openPDF(this.button);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean allPermissionsGranted = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (allPermissionsGranted) {
                // Permission granted, do your work here
                openPDF(this.button);

            } else {
                // Permission denied or canceled
                boolean shouldShowRationale = false;
                for (String permission : permissions) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        shouldShowRationale = shouldShowRequestPermissionRationale(permission);
                    }
                    if (shouldShowRationale) {
                        // User declined the permission but didn't select "Don't ask again"
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Permission Required");
                        builder.setMessage("This app needs permission to store the Para.");
                        builder.setPositiveButton("Grant", (dialog, which) -> {
                            dialog.dismiss();
                            requestPermission();
                        });
                        builder.setNegativeButton("Cancel", null);
                        builder.create().show();
                        break;
                    }
                }

                if (!shouldShowRationale) {
                    // User declined the permission and selected "Don't ask again"
                    // You may want to disable some functionality that depends on this permission
                    Toast.makeText(this, "Permission denied. Please grant permission in app settings.\nor \nReinstall app", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}
