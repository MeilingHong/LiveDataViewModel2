package com.meiling.livedata;

/**
 * @Author marisareimu
 * @time 2021-05-19 10:52
 */
public interface Desc {
    /**
     * todo
     *  微信分享、微信支付、支付宝支付、微博分享
     *  三方登录（QQ、微信、微博）
     *  拍照（XCamera、直接调用系统拍照【这个】）
     *  录音
     */

    /**
     * todo
     *  网络状态监测
     *  1、使用Service/RxJava的方式，来在子线程中获取网络状态(这里其实需要区分一点：到APP服务器的网络 与 手机本身的网络)
     *     实际需要的应该是到APP服务器的网络/当有多个服务器需要进行交互时，需要检测多个到服务器间的网络状态
     */


    /**
     * todo
     *  1、基类使用Google的databind库来实现，减少注解导致的过多的声明【不过，布局文件上会需要额外写些绑定相关的东西】
     *  2、LiveData相关联的部分（让数据能够自动释放）
     *  3、databind得到的View似乎没办法获取在这个View在页面上的位置(暂不确定是由于什么问题引起)
     */
}
