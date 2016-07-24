package skkk.gogogo.com.dakaizhihu.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import skkk.gogogo.com.dakaizhihu.R;

/**
 * Created by admin on 2016/7/24.
 */
/*
* 
* 描    述：自定义组件一个可以勾选状态的
* 作    者：ksheng
* 时    间：
*/
public class SettingItemView extends RelativeLayout {
    private final String NAMESPACE="http://schemas.android.com/apk/res-auto";
    private TextView tvTitle,tvDesc;
    private CheckBox cbStatus;
    private String mTitle,mDescOn,mDescOff;


    public SettingItemView(Context context) {
        super(context);
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //根据属性名称获取属性的值
        mTitle=attrs.getAttributeValue(NAMESPACE,"titleText");
        mDescOn=attrs.getAttributeValue(NAMESPACE,"desc_on");
        mDescOff=attrs.getAttributeValue(NAMESPACE,"desc_off");
        initView();


    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /*
    初始化布局
    */
    private void initView(){
        //将定义好的布局文件设置给当前的SettingItemView
        View.inflate(getContext(), R.layout.view_setting_item, this);
        tvTitle= (TextView) findViewById(R.id.tv_title);
        tvDesc= (TextView) findViewById(R.id.tv_desc);
        cbStatus= (CheckBox) findViewById(R.id.cb_status);
        setTitle(mTitle);//设置标题
    }

    public void setChecked(boolean checked){
        cbStatus.setChecked(checked);
        //根据选择的状态更新文本描述
        if(checked){
            setDesc(mDescOn);
        }else{
            setDesc(mDescOff);
        }
    }



    public void setTitle(String title){
        tvTitle.setText(title);
    }
    public void setDesc(String desc){
        tvDesc.setText(desc);
    }
    /*
    *返回勾选状态
    * */
    public boolean isChecked(){
        return cbStatus.isChecked();
    }



}
