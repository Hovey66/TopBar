# 自定义一个Toolbar


## 基本步骤
- **自定义属性**
- **自定义view**
- **使用view**

----------------


## 自定义属性
- 在values目录下新建atts.xml 文件
- atts.xml 内容如下：其中topbar分三部分：title left right 实现这三部分的自定义

```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="Topbar">
        <attr name="title" format="string"></attr>
        <attr name="titleTextSize" format="dimension"></attr>
        <attr name="titleTextColor" format="color"></attr>

        <attr name="left" format="string"></attr>
        <attr name="leftTextColor" format="color"></attr>
        <attr name="leftBackground" format="color|reference"></attr>

        <attr name="right" format="string"></attr>
        <attr name="rightTextColor" format="color"></attr>
        <attr name="rightBackground" format="color|reference"></attr>

    </declare-styleable>
</resources>
```
## 自定义view
- 新建一个类 Topbar,并实现其构造函数

``` java 
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
        // 2. 获取ta里的对应值并赋值给本地变量
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
```
- 其中分为以下几个步骤

1. 取出属性并赋值本地变量中，其中 **AttributeSet attrs** 存放的就是配置文件配置的各种属性，我们需要**TypedArray**类型的ta取出attrs中的值，并将其分别存放到变量中（ 记得要将数据类型相对应）string-String color-int/drawable dimension-float 具体根据代码来看
  2. 将赋值后的本地变量作为参数给button/textview 对应的方法
  3. 这时候，两个button和一个textview已经准备好了，下一步就是把它们放到我们自定义的这个groupview中，即TopBar（它继承了RelativeLayout）,然而，要放入的话我们还需要准备好Layoutparams 每个子组件都需要这个param,就像你要在xml布局中直接放置button一样需要配置它的layout_width一样，所以这步需要配置params
  4. 这下将button和textview add到该viewGroup中
  5. 对本group可以进行一些操作,比如设置个背景色
  6. 静态的布局此时已经都准备好，下面要对两个按钮的点击事件做处理，怎么处理呢，给外界提供个内部接口 TopBarOnClickListener  外部要实现这个接口就需要一个方法来与外界沟通，这个方法就是SetOnTopBarCickListener();
  7. 将接口的两个方法分别写到左右BUTTON 的点击事件里
  8. 大功告成，回调这个东西是好东西，得学会活学活用 不懂的话可以看一下设计模式中的观察者模式


## 使用自定义view
- 根布局中加入命名空间
> AS中xmlns:  xmlns:custom="http://schemas.android.com/apk/res-auto"
> EC中xmlns:xmlns:custom="http://schemas.android.com/apk/res/包名/类名"
- 具体代码 activity_main.xml

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:custom="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context="com.ysu.hongvs.topbar.MainActivity">

    <com.ysu.hongvs.topbar.TopBar
        android:id="@+id/topbar"
        android:layout_width="match_parent"
        android:layout_height="50dp"

        custom:left="Back"
        custom:leftTextColor="#00ff00"
        custom:leftBackground="#0000ff"

        custom:right="right"
        custom:rightBackground="#005500"
        custom:rightTextColor="#ffffff"

        custom:title="TopBAr"
        custom:titleTextColor="#ff0000"
        custom:titleTextSize="10sp"
        ></com.ysu.hongvs.topbar.TopBar>

</LinearLayout>
```
- 在 Activity中调用MainActivity.class：

```
 public class MainActivity extends AppCompatActivity {
    TopBar mBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBar= (TopBar) findViewById(R.id.topbar);
        mBar.setOnclickTopBar(new TopBar.TopBarListener() {
            @Override
            public void onClickLeftButton() {
                Toast.makeText(getApplicationContext(),"left" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onClickRightButton() {
                Toast.makeText(getApplicationContext(),"right" , Toast.LENGTH_LONG).show();
            }
        });

    }
}
```

## 自己的学习笔记 ，有问题欢迎交流
 
