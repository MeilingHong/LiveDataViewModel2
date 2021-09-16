package com.meiling.component.utils.activity_stack;

import android.app.Activity;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Activity管理工具类
 *
 * @Author marisareimu
 * @time 2021-05-26 16:31
 */
public class ActivityStackUtil {

    public static ActivityStackUtil getInstance() {
        return Holder.instances;
    }

    private static class Holder {
        private static ActivityStackUtil instances = new ActivityStackUtil();
    }

    // 人为管理Activity的栈
    private volatile String lastActivityName;// 存储上一次加入数组中的Activity对象名称【带路径，避免重名引起的问题】
    private ArrayList<Activity> activityStack = new ArrayList<>();

    private ActivityStackUtil() {
        // 构造函数私有化
        stackInit();
    }

    private void stackInit() {
        if (activityStack == null) {
            activityStack = new ArrayList<>();
        }
    }

    /**
     * 将Activity放入数组中进行管理
     * 目的：
     * 1、避免多次重复实例化相同Activity【在Activity栈顶层】
     */
    public synchronized boolean pushIntoStack(Activity activity) {
        if (activity == null) {// 避免添加无效的Activity对象
            return false;
        }
        stackInit();// 避免出现调用时空指针问题【数组空指针】
        if (!activityStack.isEmpty()) {
            if (activityStack.contains(activity)) {// 如果数组中包含该对象【相同对象】
                // 【contains表示包含了相同对象，但Intent创建的应该是不同的对象，所以，这个管理还不够完善】
                return pushAction(activity);
            } else if (isContainSameActivity(activity)) {// 不包含相同Activity对象，但有相同类型的实例【】
                return pushAction(activity);
            } else {
                return pushAction(activity);
            }
        } else {
            // 从未添加过
            activityStack.add(activity);
            lastActivityName = activity.getClass().getName();// 正常赋值
            return true;
        }
    }

    private boolean pushAction(Activity activity) {
        if (activity == null) {// 避免添加无效的Activity对象
            return false;
        }
        int size = activityStack.size();
        if (!TextUtils.isEmpty(lastActivityName) && lastActivityName.equals(activity.getClass().getName())) {
            // 上次记录的Activity名称赋值过，并且与当前的，名称相同时，忽略该次的添加，并关闭该添加的Activity
            finishActivity(activity);// 关闭该Activity
            return false;
        } else if (size > 0 && activityStack.get(size - 1) != null &&// 如果不判断Size>0，则可能出现下标越界异常
                activityStack.get(size - 1).getClass().getName().equals(activity.getClass().getName())) {
            // 上一次添加了相同类型的对象，但名称赋值被跳过，仍然需要忽略
            finishActivity(activity);// 关闭该Activity
            lastActivityName = activity.getClass().getName();// 避免，添加过，但赋值出现了问题；
            return false;
        } else {
            // 相同Activity被添加过
            activityStack.add(activity);
            lastActivityName = activity.getClass().getName();// 正常赋值
            return true;
        }
    }

    private void finishActivity(Activity activity) {
        if (activity != null) activity.finish();
    }

    private boolean isContainSameActivity(Activity activity) {
        if (activity == null) {
            return false;
        }
        if (activityStack == null || activityStack.isEmpty()) {
            return false;
        } else {
            String tempClassName = activity.getClass().getName();
            for (Activity tempActivity : activityStack) {
                if (tempActivity != null && tempClassName.equals(tempActivity.getClass().getName())) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 在队列中，移除指定的对象
     */
    public synchronized void pullOutOfStack(Activity activity) {
        if (activity == null) {
            return;
        }
        if (activityStack == null || activityStack.isEmpty()) {
            return;
        }
        if (!activityStack.contains(activity)) {
            if (!TextUtils.isEmpty(lastActivityName) && lastActivityName.equals(activity.getClass().getName())) {
                // 避免由于重复进出一个Activity引起第二次进入关闭该Activity导致无法进入页面问题
                lastActivityName = "";
            }
            return;
        }
        if (!TextUtils.isEmpty(lastActivityName) && lastActivityName.equals(activity.getClass().getName())) {
            // 避免由于重复进出一个Activity引起第二次进入关闭该Activity导致无法进入页面问题
            lastActivityName = "";
        }
        activityStack.remove(activity);// 由于remove实现中已经进行了循环的比较，所以，不需要在外层额外做一个包含的判断了
    }

    /**
     * 将该指定的Activity加入数组，并保证仅有有该【但这个方法并不太好】
     */
    @Deprecated
    public synchronized boolean pushOnlyOneIntoStack(Activity activity) {
        if (activity == null) {// 避免添加无效的Activity对象
            return false;
        }
        stackInit();// 避免出现调用时空指针问题【数组空指针】
        if (!activityStack.isEmpty()) {
            // 循环，清除已有的全部数据
            int size = activityStack.size();
            for (int i = size - 1; i >= 0; i--) {
                if (activityStack.get(i) != null) {
                    finishActivity(activityStack.get(i));
                    activityStack.remove(i);
                }
            }
            // 避免影响该添加，需要重置上一次的值；
            lastActivityName = null;
            return pushAction(activity);
        } else {
            // 从未添加过
            activityStack.add(activity);
            lastActivityName = activity.getClass().getName();// 正常赋值
            return true;
        }
    }

    /*
     * todo 保证顶部一定是该Activity
     *  【由于进出栈虽然是根据页面是否可见，但管理的操作，并不是按照这个步骤来的，
     *  所以：会存在，页面虽然关闭了，在列表中该关闭页面的对象并没哟从列表中移除，仍然处于列表尾部，反而上一个页面处于列表倒数第二位置；
     *  如果仅以列表尾部是否是当前显示的页面来判断，会造成返回页面操作后，调用该方法，使得全部的页面被关闭】
     */
    public synchronized boolean pushOnlyOneTopIntoStack(Activity activity) {
        if (activity == null) {// 避免添加无效的Activity对象
            return false;
        }
        stackInit();// 避免出现调用时空指针问题【数组空指针】
        if (!activityStack.isEmpty()) {
            // 避免影响该添加，需要重置上一次的值；
            int size = activityStack.size();
            // 如果不判断Size>0，则可能出现下标越界异常
            for (int i = size - 1; i >= 0; i--) {
                if (activityStack.get(i) != null && !activityStack.get(i).equals(activity)) {// 不包含当前这个Activity，则关闭，并删除
                    activityStack.get(i).finish();
                    activityStack.remove(i);
                } else if (activityStack.get(i) == null) {
                    activityStack.remove(i);
                }
            }
            return true;
        } else {
            // 从未添加过
            activityStack.add(activity);
            lastActivityName = activity.getClass().getName();// 正常赋值
            return true;
        }
    }
}
