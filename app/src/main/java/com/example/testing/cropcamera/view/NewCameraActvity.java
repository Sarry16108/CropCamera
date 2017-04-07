package com.example.testing.cropcamera.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

import com.example.testing.cropcamera.R;


/**
 * Created by Administrator on 2017/4/5.
 * 支持拍照剪切。
 */

public class NewCameraActvity extends Activity {

    private CameraDrawView mCameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_new_camera);
        mCameraView = (CameraDrawView) findViewById(R.id.cameraDrawView);


        mCameraView = (CameraDrawView) findViewById(R.id.cameraDrawView);

        String path = getIntent().getStringExtra("path");
        int width = getIntent().getIntExtra("width", 0);
        int height = getIntent().getIntExtra("height", 0);


        mCameraView.setPath(path);
        if (0 < width && 0 < height) {
            mCameraView.setVisibleSize(width, height);
        }
    }

}
