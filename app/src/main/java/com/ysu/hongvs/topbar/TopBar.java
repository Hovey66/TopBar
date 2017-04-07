package com.ysu.hongvs.topbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import android.net.sip.SipAudioCall;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by hongvS on 2017/4/6.
 */

public class TopBar extends RelativeLayout {
    private Button leftButton;
    private Button rightButton;// 自定义的两个button和一个Textview
    private TextView tvTitel;

    private String title;
    private int titleColor;//  字体的color不能是drawable
    private float titleSize;// title 属性

    private String rightText;
    private Drawable rightBackground;
    private int rigthTitleColor;  //右边菜单属性

    private String leftText;
    private Drawable leftBackground;
    private int leftTitleColor;// 左菜单属性

    LayoutParams leftParams;
    LayoutParams rightParams;
    LayoutParams titleParams;

    TopBarListener mListener;

    public interface TopBarListener {
        void onClickLeftButton();

        void onClickRightButton();
    }

    public void setOnclickTopBar(TopBarListener listener) {
        this.mListener = listener;
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 1.  R.styleable,返回一个数组 把自己定义的attrs 与attrs参数绑定
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Topbar);
        //System.out.println(ta+"ta");
        // 2. 获取ta里的对应值
        title = ta.getString(R.styleable.Topbar_title);
        titleColor = ta.getColor(R.styleable.Topbar_titleTextColor, 0);
        titleSize = ta.getDimension(R.styleable.Topbar_titleTextSize, 0);

        rightText = ta.getString(R.styleable.Topbar_right);
        rightBackground = ta.getDrawable(R.styleable.Topbar_rightBackground);
        rigthTitleColor = ta.getColor(R.styleable.Topbar_rightTextColor, 30);

        leftText = ta.getString(R.styleable.Topbar_left);
        leftBackground = ta.getDrawable(R.styleable.Topbar_leftBackground);
        leftTitleColor = ta.getColor(R.styleable.Topbar_leftTextColor, 30);
        //3.把属性里的值赋值给button和textview
        leftButton = new Button(context);// 实例化 ，记得context,context 就是指使用这个viewgroup的 activity?  好像是！不然怎么知道是哪里的button需要改变，还可能有空指针错误，先这么说服自己吧
        rightButton = new Button(context);
        tvTitel = new Button(context);

        leftButton.setText(leftText);
        leftButton.setBackground(leftBackground);
        leftButton.setTextColor(leftTitleColor);


        rightButton.setText(rightText);

        rightButton.setBackground(rightBackground);
        rightButton.setTextColor(rigthTitleColor);


        tvTitel.setText(title);
        tvTitel.setTextColor(titleColor);
        tvTitel.setTextSize(titleSize);


        //5. 为什么三之后就是5  4呢？  4在下面 ，这一步我们来设置params 这里用的是RelativeLayout 原因是 这个容器继承的是 RelativeLayout 所以布局param 用的是RelativeLayout可以使用的布局，终于明白了

        leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);//注意这个键值对 TRUE
        titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RelativeLayout.CENTER_HORIZONTAL, TRUE);

        //4.将button和textview添加到本viewgroup中 也就是TopBar这个类，但是添加值钱需要给button和textview设置相应的参数，所以之前还的设置params
        addView(leftButton, leftParams);
        addView(rightButton, rightParams);
        addView(tvTitel, titleParams);
        System.out.print("rightText" + rightText + "leftText" + leftText);
        //6. 回收ta,原因：回收资源  避免冲突，个人认为避免冲突时关键
        ta.recycle();

        //7.设置自己的背景。。。。可以对自己再修饰这里先注释掉了
        //setBackgroundColor(0x000000);
        //8. 设置接口
        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickLeftButton();
            }
        });
        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickRightButton();
            }
        });
// 定义view 部分完结
    }

}
