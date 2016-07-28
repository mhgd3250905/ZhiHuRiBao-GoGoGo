package skkk.gogogo.com.dakaizhihu.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import skkk.gogogo.com.dakaizhihu.R;

/**
 * Created by admin on 2016/7/27.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：
*/
public class HorizortalProgressbarWithProgress extends ProgressBar {

    private static final int DEFAULT_TEXT_SIZE=10;//sp
    private static final int DEFAULT_TEXT_COLOR=0xFFFC00D1;
    private static final int DEFAULT_COLOR_UNREACH=0XFFD3D6DA;
    private static final int DEFAULT_HIGHT_UNREACH=2;//dp
    private static final int DEFAULT_COLOR_REACH=DEFAULT_TEXT_COLOR;
    private static final int DEFAULT_HEIGHT_REACH=2;//dp
    private static final int DEFAULT_TEXT_OFFSET=10;//dp


    private int mTextSize=sp2px(DEFAULT_TEXT_SIZE);
    private int mTextColor=DEFAULT_TEXT_COLOR;
    private int mUnReachColor=DEFAULT_COLOR_UNREACH;
    private int mUnReachHeight=dp2px(DEFAULT_HIGHT_UNREACH);
    private int mReachColor=DEFAULT_COLOR_REACH;
    private int mReachHeight=dp2px(DEFAULT_HEIGHT_REACH);
    private int mTextOffset=dp2px(DEFAULT_TEXT_OFFSET);


    private Paint mPaint=new Paint();

    private int mRealWidth;


    //构造方法1个参数
    public HorizortalProgressbarWithProgress(Context context) {
        this(context, null);

    }
    //构造方法2个参数
    public HorizortalProgressbarWithProgress(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        obtainStyleAttrs(attrs);
    }
    //构造方法3个参数
    public HorizortalProgressbarWithProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyleAttrs(attrs);
    }


    /*
    * @desc 获取自定义属性
    * @时间 2016/7/26 23:11
    */
    private void obtainStyleAttrs(AttributeSet attrs) {

        TypedArray ta=getContext().obtainStyledAttributes(attrs,

                R.styleable.HorizortalProgressbarWithProgress);

        mTextSize= (int) ta.getDimension(
                R.styleable.HorizortalProgressbarWithProgress_process_text_size,
                mTextSize);
        mTextColor=ta.getColor(
                R.styleable.HorizortalProgressbarWithProgress_process_text_color,
                mTextColor);
        mTextOffset=(int) ta.getDimension(
                R.styleable.HorizortalProgressbarWithProgress_process_text_offset,
                mTextOffset);
        mUnReachColor=ta.getColor(
                R.styleable.HorizortalProgressbarWithProgress_process_unreach_color,
                mUnReachColor);
        mUnReachHeight=(int) ta.getDimension(
                R.styleable.HorizortalProgressbarWithProgress_process_unreach_hight,
                mUnReachHeight);
        mReachColor=ta.getColor(
                R.styleable.HorizortalProgressbarWithProgress_process_reach_color,
                mReachColor);
        mReachHeight=(int) ta.getDimension(
                R.styleable.HorizortalProgressbarWithProgress_process_reach_height,
                mReachHeight);

        ta.recycle();

        //设置字体
        mPaint.setTextSize(mTextSize);
    }

    /*
    * @desc 控件的测量
    * @时间 2016/7/26 23:16
    */
    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        //用户一定要设置要么是具体数值要么就是fill
        //int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int widthVal = MeasureSpec.getSize(widthMeasureSpec);

        int height=measureHeight(heightMeasureSpec);

        setMeasuredDimension(widthVal,height);

        mRealWidth=getMeasuredWidth()-getPaddingLeft()-getPaddingRight();

    }

    //获取高度
    private int measureHeight(int heightMeasureSpec) {

        int result=0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);


        if(mode==MeasureSpec.EXACTLY){
            //如果是精确值
            result=size;
        }else{
            int textHeight= (int) (mPaint.descent()-mPaint.ascent());
            result=getPaddingTop()
                    +getPaddingBottom()
                    +Math.max(Math.max(mReachHeight,mUnReachHeight),
                    Math.abs(textHeight));

            if(mode==MeasureSpec.AT_MOST){
                result=Math.min(result,size);
            }
        }
        return result;
    }

    /*
    * @desc 绘制
    * @时间 2016/7/26 23:25
    */
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getPaddingLeft(),getHeight()/2);


        //draw reachBar
        boolean noNeedUnRech=false;


        String text=getProgress()+"%";

        int textWidth = (int) mPaint.measureText(text);

        float radio=getProgress()*1.0f/getMax();

        float progressX=radio*mRealWidth;

        if(progressX+textWidth>mRealWidth){

            progressX=mRealWidth-textWidth;
            noNeedUnRech=true;

        }

        float endX=progressX-mTextOffset/2;

        if(endX>0){
            mPaint.setColor(mReachColor);
            mPaint.setStrokeWidth(mReachHeight);
            canvas.drawLine(0,0,endX,0,mPaint);
        }

        //draw text
        mPaint.setColor(mTextColor);
        int y= (int) (-(mPaint.descent()+mPaint.ascent())/2);
        canvas.drawText(text,progressX,y,mPaint);


        //draw unReachBar
        if(!noNeedUnRech){
            float start=progressX+textWidth+mTextOffset/2;
            mPaint.setColor(mUnReachColor);
            mPaint.setStrokeWidth(mUnReachHeight);
            canvas.drawLine(start,0,mRealWidth,0,mPaint);
        }

        canvas.restore();
    }

    /*
            * @desc 完成dp到px的转换
            * @时间 2016/7/26 23:04
            */
    private int dp2px(int dbVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dbVal, getResources().getDisplayMetrics());
    }

    /*
    * @desc 完成sp到px的转换
    * @时间 2016/7/26 23:04
    */
    private int sp2px(int spVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal,getResources().getDisplayMetrics());
    }
}
