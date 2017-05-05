package com.deskit.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.deskit.R;
import com.deskit.persistance.DatabaseHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;

public class SplashActivity extends Activity implements OnSuccessListener<FileDownloadTask.TaskSnapshot>, OnFailureListener {

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        if (DatabaseHelper.getInstance(getApplicationContext()).createDataBase(this, this)) {
            openMainActivity();
        }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Log.d(TAG, "Database download failed!");

        closeApp();
    }

    @Override
    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
        Log.d(TAG, "Download database: onSuccess");

        openMainActivity();
    }

    private void openMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void closeApp() {
        finish();
    }
}
