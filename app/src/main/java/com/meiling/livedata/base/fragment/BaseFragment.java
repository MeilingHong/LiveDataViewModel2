package com.meiling.livedata.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meiling.component.utils.log.Mlog;
import com.meiling.component.utils.toast.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

/**
 * @Author marisareimu
 * @time 2021-05-19 11:13
 * <p>
 * 【androidx.fragment.app.Fragment 已经实现了这个 LifecycleOwner 接口】
 */
public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment/* implements LifecycleOwner */ {
    protected T layoutBinding = null;

    /**
     * 如果有必要，在这个方法里变更宿主Activity的相关配置信息
     */
    public abstract void changeActivityConfig();

    /**
     * todo 设置布局资源文件ID
     */
    protected abstract int getLayoutId();

    /**
     * todo 一些需要手动释放的资源可以在这里进行释放
     */
    protected abstract void afterDestroy();

    /**
     * todo 有必要在onCreate方法中进行实现的
     */
    protected abstract void initViewAfterOnCreate();

    /**
     * todo 当全部生命流程执行完后回调该方法，用来保证需要延迟初始化或者调用的东西可以在这里进行调用
     */
    protected abstract void lazyloadCallback();

    // 构建View
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        if (container != null) {// 理论上，避免重复添加引起问题，但似乎只要Fragment对应Tag不相同，似乎也没事
//            container.removeAllViews();
//        }
        changeActivityConfig();// 创建Fragment前变更对应的配置【其实基本不需要变更什么，除非对于不同Fragment，状态栏颜色不同，需要变更状态栏文字的颜色样式】
        layoutBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        initViewAfterOnCreate();
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                Mlog.d(getClass().getName() + "---" + Thread.currentThread().getName() + "--- onCreateView(Fragment)");
                lazyloadCallback();
                return false;
            }
        });
        return layoutBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getName());
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        afterDestroy();
        unbindRelease();
    }

    private void unbindRelease() {
        if (layoutBinding != null) layoutBinding.unbind();// todo 当页面销毁时，对databinding对象进行解绑操作
        layoutBinding = null;
    }

    /*
     *********************************************************************************************************
     * EventBus注册较为标准的方式，避免重复注册导致的异常
     * --- Subscriber class already registered to event class
     */
//    public void registerEventBus() {
//        if (!EventBus.getDefault().isRegistered(this)) {// 避免相同对象重复注册
//            EventBus.getDefault().register(this);
//        }
//    }
//
//    public void unregisterEventBus() {
//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//        }
//    }
    /*
     **********************************************************************************
     * 设置全屏和状态栏颜色
     */
    protected Intent newIntent(Class<?> cls) {
        return new Intent(getContext(), cls);
    }

    protected Bundle newBundle() {
        /*
        Bundle 类针对使用 parcel 进行编组和解组进行了高度优化。 在传递参数时，建议使用Bundle进行参数封装
        https://developer.android.google.cn/guide/components/activities/parcelables-and-bundles#java
        通过 intent 发送数据时，应小心地将数据大小限制为几 KB。发送过多数据会导致系统抛出 TransactionTooLargeException 异常。

        原因：Binder 事务缓冲区的大小固定有限，目前为 1MB，由进程中正在处理的所有事务共享。
        由于此限制是进程级别而不是 Activity 级别的限制，因此这些事务包括应用中的所有 binder 事务，
        例如 onSaveInstanceState，startActivity 以及与系统的任何互动。
         */
        return new Bundle();
    }

    protected void toActivity(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            activityResultWithData(requestCode, resultCode, data);
            return;
        }
        activityResultWithoutData(requestCode, resultCode);
    }

    protected void activityResultWithData(int requestCode, int resultCode, Intent data) {
        Mlog.d("---(activityResultWithData【Fragment】)" + getClass().getName() + "---" + requestCode + "----" + resultCode);
    }

    protected void activityResultWithoutData(int requestCode, int resultCode) {
        Mlog.d("---(activityResultWithoutData【Fragment】)" + getClass().getName() + "---" + requestCode + "----" + resultCode);
    }

    /*
     *********************************************************************************************************
     * 提供一个手动清除消息的方法
     */

    /**
     * todo 移除Handler消息队列中的全部信息
     */
    public void removeHandlerMessages(Handler handler) {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
    /*
     **********************************************************************************
     */
    public void toastShortCenter(String message) {
        ToastUtils.toastSystemStyle(Objects.requireNonNull(getActivity()), message, Gravity.CENTER, true);
    }
    /*
     **********************************************************************************
     * 等待对话框【加载】
     */
}
