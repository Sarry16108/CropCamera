package com.example.testing.cropcamera.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.testing.cropcamera.R;


/**
 * Created by Administrator on 2017/4/6.
 */

public class CameraDrawView extends RelativeLayout implements View.OnClickListener {

    public static int  CAMERA_REQUEST = 100;


    private RelativeLayout  mRootLayout;
    private CameraView  mCameraView;
    private CameraLayerView mCameraLayerView;
    private CameraView.BitmapCallback   mBitmapCallback;

    private ImageView       mTakePic;
    private ImageView       mBack;
    private ImageView       mSelect;
    private Context         mContext;
    private Bitmap          mCapture;   //照片

    private int         mVisiblePadding;


    public CameraDrawView(Context context) {
        this(context, null);
    }

    public CameraDrawView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraDrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
        init(context, attrs);
    }

    private void initLayout(Context context) {
        mContext = context;
        mRootLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.layout_camera_darw_view, this, true);
        mCameraView = (CameraView) mRootLayout.findViewById(R.id.cameraView);
        mCameraLayerView = (CameraLayerView) mRootLayout.findViewById(R.id.cameraLayerView);
        mTakePic = (ImageView) mRootLayout.findViewById(R.id.takePic);
        mBack = (ImageView) mRootLayout.findViewById(R.id.back);
        mSelect = (ImageView) mRootLayout.findViewById(R.id.select);

        mRootLayout.setPadding(0, 0, 0, 0);
        mTakePic.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mSelect.setOnClickListener(this);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CameraDrawView);
        if (null != typedArray) {
            mVisiblePadding = typedArray.getDimensionPixelOffset(R.styleable.CameraDrawView_android_padding, 0);
            int lineWidth = typedArray.getDimensionPixelOffset(R.styleable.CameraDrawView_lineWidth, 0);
            if (0 < lineWidth) {
                mCameraLayerView.setLineWidth(lineWidth);
                mCameraLayerView.setLineColor(typedArray.getDimensionPixelOffset(R.styleable.CameraDrawView_lineColor, 0x7c000000));
            }
        }

        DisplayMetrics displayMetrics = context.getApplicationContext().getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        mCameraLayerView.setDimmedSize(width, height);
        mCameraView.setPreviewSize(width, height);

    }

    //设置显示区域
    public void setVisibleSize(int width, int height) {
        mCameraLayerView.setVisibility(View.VISIBLE);
        mCameraLayerView.setVisibleSize(width, height, mVisiblePadding);
        mCameraView.setImageRect(mCameraLayerView.getVisibleRect());
    }

    public void setPath(String path) {
        mCameraView.setPath(path);
    }

    public void setBitmapCallback(CameraView.BitmapCallback callback) {
        mBitmapCallback = callback;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.takePic:
                if (null != mBitmapCallback) {      //当前页面回调
                    mCameraView.takePicture(mBitmapCallback);
                } else {
                    mCameraView.takePicture(new CameraView.BitmapCallback() {   //页面传递
                        @Override
                        public void onCallback(Bitmap bitmap) {
                            mCapture = bitmap;
                        }
                    });
                }

                mTakePic.setVisibility(View.GONE);
                mSelect.setVisibility(View.VISIBLE);
                break;
            case R.id.back:
                if (mCameraView.isPreview()) {
                    ((Activity)mContext).finish();
                } else {
                    mTakePic.setVisibility(View.VISIBLE);
                    mSelect.setVisibility(View.GONE);
                    mCameraView.start();
                }
                break;
            case R.id.select:
                selectImage();
                break;
        }
    }

    private void selectImage() {
        Activity act = (Activity)mContext;
//        Intent intent = new Intent();
//        intent.putExtra("bitmap", mCapture);
//        act.setResult(Activity.RESULT_OK, intent);
        act.setResult(Activity.RESULT_OK);
        act.finish();
    }
}
