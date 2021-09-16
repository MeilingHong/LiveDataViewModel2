package com.meiling.livedata.app.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.meiling.component.utils.datahandle.UnitExchangeUtil;
import com.meiling.component.utils.log.Mlog;
import com.meiling.component.utils.network.NetworkUtil;
import com.meiling.component.utils.text_show.SpanTextUtil;
import com.meiling.livedata.R;
import com.meiling.livedata.app.dialog.loading.LoadingDialog;
import com.meiling.livedata.app.popup.LoadingPopupWindow;
import com.meiling.livedata.base.activity.ActivityConfig;
import com.meiling.livedata.base.activity.BaseActivity;
import com.meiling.livedata.base.dialog.callback.IDismissCallback;
import com.meiling.livedata.base.dialog.callback.IShowCallback;
import com.meiling.livedata.databinding.ActivityDatabindLivedataBinding;
import com.meiling.livedata.lifecycle.FullLifecycleObserver;
import com.meiling.livedata.lifecycle.Lifecycle2Adapter;
import com.meiling.livedata.viewmodel.TitleViewModel;
import com.meiling.livedata.viewmodel.data.DataEntity;
import com.meiling.livedata.viewmodel.data.DataViewModel;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

/**
 * @Author marisareimu
 * @time 2021-05-19 10:52
 */
public class LiveDataActivity extends BaseActivity<ActivityDatabindLivedataBinding> {

    private TitleViewModel mTitle;
    private DataViewModel mData;
    private DataViewModel mData1;
    private DataViewModel mData2;
    private Random mRandom;

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
        return R.layout.activity_databind_livedata;
    }

    @Override
    protected void afterDestroy() {
        removeHandlerMessages(mHandler);
    }

    @Override
    protected void initViewAfterOnCreate() {
        mRandom = new Random();
//        // todo 创建一个ViewModel对象，并进行关联
        mTitle = getDefaultViewModelProviderFactory().create(TitleViewModel.class);
//        mTitle = ViewModelProviders.of(this).get(TitleViewModel.class);//
        mTitle.getmTitle().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String titleString) {
                layoutBinding.click.setText(titleString);// 当值发生变化时，进行更新显示
            }
        });

        /*
         **********************************************************************************************************
         */

        mData = getDefaultViewModelProviderFactory().create(DataViewModel.class);
        mData.getData().observe(this, new Observer<DataEntity>() {
            @Override
            public void onChanged(DataEntity dataEntity) {
                layoutBinding.click1.setText(dataEntity.getName());// 当值发生变化时，进行更新显示
            }
        });

        mData1 = getDefaultViewModelProviderFactory().create(DataViewModel.class);
        mData1.getData().observe(this, new Observer<DataEntity>() {
            @Override
            public void onChanged(DataEntity dataEntity) {
                layoutBinding.click2.setText(dataEntity.getName());// 当值发生变化时，进行更新显示
            }
        });

        mData2 = getDefaultViewModelProviderFactory().create(DataViewModel.class);
        mData2.getData().observe(this, new Observer<DataEntity>() {
            @Override
            public void onChanged(DataEntity dataEntity) {
                layoutBinding.click3.setText(dataEntity.getName());// 当值发生变化时，进行更新显示
            }
        });
        mData2.getData().setValue(new DataEntity("mData2 初始设置"));// 由于没有设置MutableLiveData的值，所以无法进行修改

        /*
         **********************************************************************************************************
         */
        layoutBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layoutBinding.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo 直接设置【基本类型】
                mTitle.getmTitle().setValue("默认的标题" + mRandom.nextDouble());
            }
        });


        layoutBinding.click1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo 设置值【对象类型】
                if (mData != null && mData.getData() != null && mData.getData().getValue() != null) {
                    mData.getData().getValue().setName("mData 后续设置：" + mRandom.nextDouble());
                    Mlog.e("mData 后续设置：" + mData.getData().getValue().toString());
                } else if (mData != null && mData.getData() != null) {
                    mData.getData().setValue(new DataEntity("mData 第一次设置：" + mRandom.nextDouble()));
                    Mlog.e("mData 第一次设置：" + mData.getData().getValue().toString());
                } else {
                    Toast.makeText(LiveDataActivity.this, "mData 对象为空", Toast.LENGTH_SHORT).show();
                    Mlog.e("mData 对象为空");
                }
            }
        });

        layoutBinding.click2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo 设置对象【对象类型】
//                mTitle.getmTitle().setValue("默认的标题" + mRandom.nextDouble());
                if (mData1 != null && mData1.getData() != null && mData1.getData().getValue() != null) {
                    mData1.getData().getValue().setName("mData1 后续设置：" + mRandom.nextDouble());
                    mData1.getData().setValue(mData1.getData().getValue());
                    Mlog.e("mData1 后续设置：" + mData1.getData().getValue().toString());
                } else if (mData1 != null && mData1.getData() != null) {
                    mData1.getData().setValue(new DataEntity("mData1 第一次设置：" + mRandom.nextDouble()));
                    Mlog.e("mData1 第一次设置：" + mData1.getData().getValue().toString());
                } else {
                    Toast.makeText(LiveDataActivity.this, "mData1 对象为空", Toast.LENGTH_SHORT).show();
                    Mlog.e("mData1 对象为空");
                }
            }
        });

        layoutBinding.click3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo 设置对象【对象类型】
//                mTitle.getmTitle().setValue("默认的标题" + mRandom.nextDouble());
                if (mData2 != null && mData2.getData() != null && mData2.getData().getValue() != null) {
                    mData2.getData().getValue().setName("mData2 后续设置：" + mRandom.nextDouble());
                    mData2.getData().setValue(mData2.getData().getValue());
                    Mlog.e("mData2 后续设置：" + mData2.getData().getValue().toString());
                } else if (mData2 != null && mData2.getData() != null) {
                    mData2.getData().setValue(new DataEntity("mData2 第一次设置：" + mRandom.nextDouble()));
                    Mlog.e("mData2 第一次设置：" + mData2.getData().toString());
                } else {
                    Toast.makeText(LiveDataActivity.this, "mData2 对象为空", Toast.LENGTH_SHORT).show();
                    Mlog.e("mData2 对象为空");
                }
            }
        });

        // 当返回为空时，表示没有SIM卡，或者SIM卡未联网
        String name = NetworkUtil.getOperatorName(getApplicationContext());
        layoutBinding.click.setText("--->" + name + "<---");// 中国移动返回的CMCC


        getLifecycle().addObserver(new Lifecycle2Adapter(new FullLifecycleObserver() {
            @Override
            public void onCreate(LifecycleOwner owner) {
                Mlog.w("Lifecycle  onCreate  ---  " + (owner != null ? owner.getClass().getName() : getBaseClassName()));
            }

            @Override
            public void onStart(LifecycleOwner owner) {
                Mlog.w("Lifecycle  onStart  ---  " + (owner != null ? owner.getClass().getName() : getBaseClassName()));
            }

            @Override
            public void onResume(LifecycleOwner owner) {
                Mlog.w("Lifecycle  onResume  ---  " + (owner != null ? owner.getClass().getName() : getBaseClassName()));
            }

            @Override
            public void onPause(LifecycleOwner owner) {
                Mlog.w("Lifecycle  onPause  ---  " + (owner != null ? owner.getClass().getName() : getBaseClassName()));
            }

            @Override
            public void onStop(LifecycleOwner owner) {
                Mlog.w("Lifecycle  onStop  ---  " + (owner != null ? owner.getClass().getName() : getBaseClassName()));
            }

            @Override
            public void onDestroy(LifecycleOwner owner) {
                Mlog.w("Lifecycle  onDestroy  ---  " + (owner != null ? owner.getClass().getName() : getBaseClassName()));
            }

            @Override
            public void onAny(LifecycleOwner owner) {
//                Mlog.w("Lifecycle  onAny" + (owner != null ? owner.getClass().getName() : getBaseClassName()));
            }
        }, this));
    }

    @Override
    protected void lazyloadCallback() {

    }

    /*
     **************************************************************************************
     */
    private void showSpanTextCall() {
        /**
         * todo 当用于显示Span的View，在初始文字大小设置上有问题时，可能会出现：
         *  1、应该显示...时，显示不出来，且显示文字出现被截断的情况
         *  2、显示...时，显示不够完全，中间可能会被截断导致显示只有一半的.
         */
        /**
         * todo 实践上较好的实现方式为以下这种
         *  先初始文字大小为可能显示的最大值，然后调用Span工具，配置相关参数
         *  优势：能够适应任意的显示上的通用要求
         *  劣势：需要传递的参数过多
         */
        layoutBinding.click.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 21);// 先默认处理成最大的显示文字大小，然后再进行显示
        SpanTextUtil.setSpanText(getApplicationContext(), layoutBinding.click, "$3000000000000000000.52",
                new int[]{R.color.color_black_33, R.color.colorPrimaryDark, R.color.color_black_33},
                new boolean[]{false, false, false},
                new boolean[]{false, false, false},
                new float[]{UnitExchangeUtil.dip2px(getApplicationContext(), 12),
                        UnitExchangeUtil.dip2px(getApplicationContext(), 21),
                        UnitExchangeUtil.dip2px(getApplicationContext(), 12)},
                new int[]{0, 1, 20}, null);
    }

    /*
     **************************************************************************************
     */
    private LoadingDialog loadingDialog;

    private void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog();
            loadingDialog.setDialogConfig(getApplicationContext(), new IShowCallback() {
                @Override
                public void afterDialogShow() {
                    Mlog.d("afterDialogShow---");
                }
            }, new IDismissCallback() {
                @Override
                public void afterDialogDismiss() {
                    Mlog.d("afterDialogDismiss---");
                    if (loadingDialog != null) {
                        loadingDialog = null;
                    }
                }
            });
            loadingDialog.show(getSupportFragmentManager(), "loading", 2000);
        }
    }

    private LoadingPopupWindow loadingPopupWindow;

    private void showLoadingPopupWindow() {
        if (loadingPopupWindow == null) {
            loadingPopupWindow = new LoadingPopupWindow(getApplicationContext());// 直接在构造方法中设置dismiss回调似乎有问题
            loadingPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {// 但显示设置就不会有这个问题了
                @Override
                public void onDismiss() {
                    Mlog.d("onDismiss【popupWindow】---");
                    if (loadingPopupWindow != null) {
                        loadingPopupWindow = null;
                    }
                }
            });
            loadingPopupWindow.showAsDropDown(layoutBinding.click);

            /*
             *
             */
//            int[] positionInScreen = new int[2];
//            // todo  使用 findViewById 可以拿到所在的屏幕位置，但直接使用 databind.view 的方式拿不到
//            TextView textView = findViewById(R.id.click);
//            textView.getLocationOnScreen(positionInScreen);// 获取在屏幕中的位置【全屏时，无偏差】
//            //            layoutBinding.click.getLocationInWindow(positionInScreen);
//            Mlog.w(positionInScreen[0] + "---" + positionInScreen[1] + "---" + textView.getMeasuredWidth() + "---" + textView.getMeasuredHeight());
//            loadingPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.LEFT | Gravity.TOP,
//                    positionInScreen[0], positionInScreen[1] + textView.getMeasuredHeight());
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