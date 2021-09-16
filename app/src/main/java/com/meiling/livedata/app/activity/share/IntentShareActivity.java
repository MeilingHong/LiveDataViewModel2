package com.meiling.livedata.app.activity.share;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.meiling.livedata.R;
import com.meiling.livedata.base.activity.ActivityConfig;
import com.meiling.livedata.base.activity.BaseActivity;
import com.meiling.livedata.databinding.ActivityDatabindIntentShareBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Author marisareimu
 * @time 2021-05-19 10:52
 */
public class IntentShareActivity extends BaseActivity<ActivityDatabindIntentShareBinding> {

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void setConfiguration() {
        activityConfig = new ActivityConfig.Builder()
                .setFullscreen(true)
                .setShowStatusBar(true)
                .setWhiteStatusFont(false)
                .setBlockBackKey(false)// 屏蔽返回键
                .setDoubleBackExit(true)
                .setKeepScreenOn(false)
                .setNavigatorBarColor(getResources().getColor(R.color.color_white))
                .setPortraitMode(true)
                .setCheckSignalStrength(true)
                .build();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_databind_intent_share;
    }

    @Override
    protected void afterDestroy() {
        removeHandlerMessages(mHandler);
    }

    @Override
    protected void initViewAfterOnCreate() {
        layoutBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layoutBinding.clickAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 分享全部可处理的APP
                intentShareAll();

            }
        });
        layoutBinding.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 文本
                intentShareCallText();

            }
        });
        layoutBinding.click1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 图片
                startSystemImagePick();
            }
        });
        layoutBinding.click2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 链接
                intentShareCallUrl();

            }
        });
    }

    @Override
    protected void lazyloadCallback() {

    }

    /*
     **************************************************************************************
     */
    private void intentShareAll() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "这是一段分享的文字");
        /**
         * 1、分享本地文件
         */
        startActivity(Intent.createChooser(intent, "分享到"));
    }

    /*
     **************************************************************************************
        <activity android:configChanges="keyboardHidden|orientation|screenSize" android:icon="@drawable/icon" android:name="com.tencent.mm.ui.tools.ShareImgUI">
            <intent-filter android:label="@string/h6">
                <action android:name="android.intent.action.SEND"/>
                <category android:name="android.intent.category.DEFAULT"/>
                <data android:mimeType="image/*"/>
                <data android:mimeType="video/*"/>
                <data android:mimeType="text/*"/>
                <data android:mimeType="application/*"/>
            </intent-filter>
            <intent-filter android:label="@string/h6">
                <action android:name="android.intent.action.SEND_MULTIPLE"/>
                <category android:name="android.intent.category.DEFAULT"/>
                <data android:mimeType="image/*"/>
            </intent-filter>
        </activity>
        <activity android:configChanges="keyboardHidden|orientation|screenSize" android:icon="@drawable/adf" android:name="com.tencent.mm.ui.tools.AddFavoriteUI">
            <intent-filter android:label="@string/d5">
                <action android:name="android.intent.action.SEND"/>
                <category android:name="android.intent.category.DEFAULT"/>
                <data android:mimeType="image/*"/>
                <data android:mimeType="video/*"/>
                <data android:mimeType="text/*"/>
                <data android:mimeType="application/*"/>
                <data android:mimeType="audio/*"/>
            </intent-filter>
            <intent-filter android:label="@string/d5">
                <action android:name="android.intent.action.SEND_MULTIPLE"/>
                <category android:name="android.intent.category.DEFAULT"/>
                <data android:mimeType="image/*"/>
            </intent-filter>
        </activity>
        <activity android:configChanges="keyboardHidden|orientation|screenSize" android:icon="@drawable/a8q" android:name="com.tencent.mm.ui.tools.ShareToTimeLineUI">
            <intent-filter android:label="@string/h5">
                <action android:name="android.intent.action.SEND"/>
                <category android:name="android.intent.category.DEFAULT"/>
                <data android:mimeType="image/*"/>
            </intent-filter>
            <intent-filter android:label="@string/h5">
                <action android:name="android.intent.action.SEND_MULTIPLE"/>
                <category android:name="android.intent.category.DEFAULT"/>
                <data android:mimeType="image/*"/>
            </intent-filter>
        </activity>
     */
    private void intentShareCallText() {
        Intent intent = new Intent();
//        ComponentName comp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.tools.ShareToTimeLineUI");// 这个指定分享的位置有问题
//        intent.setComponent(comp);
        intent.setPackage("com.tencent.mm");
        intent.setAction(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "这是一段分享的文字");
        /**
         * 1、分享本地文件
         */
        startActivity(intent);
    }

    private void intentShareCallUrl() {
        Intent intent = new Intent();
//        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
//        intent.setComponent(comp);
        intent.setPackage("com.tencent.mm");
        intent.setAction(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "https://www.baidu.com");
        /**
         * 1、分享本地文件
         */
        startActivity(intent);
    }

    private void intentShareCallImage(Uri uri) {
//        String path = Environment.getExternalStorageDirectory().getPath() + File.separator + "crop_file.jpg";
//        String path = getResourcesUri(R.drawable.loading_icon);// 测试发现，启动微信分享后，分享到朋友圈不成功
        Intent intent = new Intent();
        intent.setPackage("com.tencent.mm");// todo 最好还是不指定页面，而是仅指定APP【包名】来处理，由APP可以处理该数据的地方进行处理
        intent.setAction(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);// 为了避免无法访问到指定的Uri，最好进行授权
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);//uri为你要分享的图片的uri
        startActivity(intent);
    }

//    private String getResourcesUri(@DrawableRes int id) {
//        Resources resources = getResources();
//        String uriPath = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
//                resources.getResourcePackageName(id) + "/" +
//                resources.getResourceTypeName(id) + "/" +
//                resources.getResourceEntryName(id);
////        Toast.makeText(this, "Uri:" + uriPath, Toast.LENGTH_SHORT).show();
//        return uriPath;
//    }

    private void startSystemImagePick() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            intentShareCallImage(selectedImage);
        }
    }

    /*
     **************************************************************************************
     * 页面跳转的返回结果
     */

    @Override
    protected void activityResultWithData(int requestCode, int resultCode, Intent data) {
        super.activityResultWithData(requestCode, resultCode, data);
    }

    @Override
    protected void activityResultWithoutData(int requestCode, int resultCode) {
        super.activityResultWithoutData(requestCode, resultCode);
    }

    /*
     **************************************************************************************
     */

    @Override
    protected void showDoubleBackExitHint() {
        super.showDoubleBackExitHint();
        Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
    }
}