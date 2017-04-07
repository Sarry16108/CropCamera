package com.example.testing.cropcamera;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.testing.cropcamera.databinding.ActivityMainBinding;
import com.example.testing.cropcamera.view.NewCameraActvity;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.jpeg";
        mBinding.takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewCameraActvity.class);
                intent.putExtra("path", path);
                intent.putExtra("width", 3);
                intent.putExtra("height", 1);
                MainActivity.this.startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (RESULT_OK != resultCode) {
            return;
        }

        if (1 == requestCode) {
            Toast.makeText(this, "save path:" + path, Toast.LENGTH_SHORT).show();
        }
    }
}
