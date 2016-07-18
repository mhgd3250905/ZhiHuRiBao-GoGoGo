package skkk.gogogo.com.dakaizhihu.View;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * Created by admin on 2016/7/18.
 */
/*
* 
* 描    述：限制速度的我自己的NestScrollView
* 作    者：ksheng
* 时    间：
*/
public class MyNestScrollView extends NestedScrollView {
    public MyNestScrollView(Context context) {
        super(context);
    }

    public MyNestScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNestScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY/2);
    }
}
