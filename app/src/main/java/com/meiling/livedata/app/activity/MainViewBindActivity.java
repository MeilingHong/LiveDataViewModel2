package com.meiling.livedata.app.activity;

import android.os.Bundle;

import com.meiling.livedata.databinding.ActivityMainBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 使用ViewBind的方式进行视图绑定
 *
 * @Author marisareimu
 * @time 2021-05-19 10:52
 */
public class MainViewBindActivity extends AppCompatActivity {
    /**
     * todo
     * 除正常的调用外，需要关注的点：
     * 1、状态栏文字颜色与背景色关系（含滑动时，颜色的变更）
     * 2、页面是否处于全屏状态【全屏/不全屏】
     * 3、网络检测
     * 3.1、处于无网络状态应该如何显示
     * (各页面统一处理？
     * 1> 使用基类的Activity;【优点：使用方便；缺点：继承会使得很多没用到的东西被继承，使得子类代码膨胀】
     * 2> 使用统一的基布局，ViewSub占位，当检测到网络问题，则填充，否则不进行填充;
     * 填充部分交互处理问题(各页面单独处理【不建议，重复工作太多，仅调用的东西不同】)，还是定义成工具类，进行统一调用
     * )
     * 4、换肤
     * 换肤一般考虑在基类中进行处理
     * 5、网络调用（RxJava3  更新版本，似乎RxJava2已经不再更新）
     * 6、Jetpack Compose （类Flutter页面构建方式） UI构建方式
     * 7、硬件调用---------------------------------------------------
     * Bluetooth
     * Camera
     * WiFi Scan
     * Screen Record
     * Voice Record
     * 8、接口请求的Loading View
     * 8.1、使用Dialog方式实现【缺陷：会存在一个灰色遮罩的问题】---
     * Java---progressDialog.getWindow().setDimAmount(0f);//核心代码 解决了无法去除遮罩问题 --- https://blog.csdn.net/u012080791/article/details/64907146
     * kotlin---window.setDimAmount(0f)
     * 暂未确认解决方式是否可行
     * 8.2、类似无网络的错误页面(ViewSub方式)，在点击时处理额外的交互
     * 9、3D模型
     * 9.1、OpenGL
     * 9.2、OpenCV
     * 10、音视频处理
     * 10.1、FFmpeg
     */

    private ActivityMainBinding layoutBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(layoutBind.getRoot());

        /**
         * todo
         *  待验证：
         *   ConstraintLayout相比RelativeLayout在实现上进行了缓存设计，执行效率上会更高、
         *  ---
         *  使用系统的Adapter实现适配性会更好
         *  现实问题：部分数据(列表中，仅部分数据发生了变动)刷新的问题
         *  ---
         *  数据库使用Room替代数据库
         *  ---
         *  SP是否有替代的使用方式？
         */
    }
}