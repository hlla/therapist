package com.zty.therapist.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zty.therapist.R;

/**
 * Created by zty on 2016/11/24.
 */

public class MyStripMenuView extends RelativeLayout {
    private ImageView imgSign;
    private TextView tvName;
    private TextView tvAddition;
    private View topLine;
    private View bottomLine;
    private String name;
    private String addition;
    private boolean isHasTop;
    private boolean isHasBottom;
    private int lineMargin;

    public MyStripMenuView(Context context) {
        this(context, null);
    }

    public MyStripMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 将自定义组合控件的布局渲染成View
        View view = View.inflate(context, R.layout.view_strip, this);
        imgSign = (ImageView) view.findViewById(R.id.imgStrip);
        tvName = (TextView) view.findViewById(R.id.textStrip);
        tvAddition = (TextView) view.findViewById(R.id.textStripAddition);
        // 加载自定义的属性并设置
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyStripMenuView);
        name = a.getString(R.styleable.MyStripMenuView_textName);
        tvName.setText(name);
        tvName.setTextColor(a.getColor(R.styleable.MyStripMenuView_textNameColor, ContextCompat.getColor(context, R.color.black)));
        Drawable drawable = a.getDrawable(R.styleable.MyStripMenuView_imgSrc);
        imgSign.setImageDrawable(drawable);
        addition = a.getString(R.styleable.MyStripMenuView_textAddition);
        tvAddition.setText(addition);
        tvAddition.setTextColor(a.getColor(R.styleable.MyStripMenuView_textAdditionColor, ContextCompat.getColor(context, R.color.black)));

        topLine = (View) view.findViewById(R.id.topLine);
        isHasTop = a.getBoolean(R.styleable.MyStripMenuView_isHasTopLine, true);
        bottomLine = (View) view.findViewById(R.id.bottomLine);
        isHasBottom = a.getBoolean(R.styleable.MyStripMenuView_isHasBottomLine, true);

        lineMargin = a.getInteger(R.styleable.MyStripMenuView_lineMargin, 0);

        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
        lp.setMargins(lineMargin, 0, lineMargin, 0);
        topLine.setLayoutParams(lp);
        bottomLine.setLayoutParams(lp);

        if (isHasTop) {
            topLine.setVisibility(View.VISIBLE);
        } else {
            topLine.setVisibility(View.INVISIBLE);
        }
        if (isHasBottom) {
            bottomLine.setVisibility(View.VISIBLE);
        } else {
            bottomLine.setVisibility(View.INVISIBLE);
        }

    }

    // 设置附加的文字内容
    public void setAdditionText(String addition) {
        tvAddition.setText(addition);
    }
}
