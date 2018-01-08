package heyhou.com.baseproject.base;

import android.content.Context;
import android.os.Bundle;

import heyhou.com.baseproject.utils.ViewTools;


/**
 * @author ydc
 *         基类Activity
 *         Created by 1 on 2016/4/27.
 */
public class BaseActivity extends BaseAppCompatActivity {
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewTools.setWindowStatusBarColor(this);
//        BaseMainApp.getInstance().addActivity(this);
        this.mContext = this;
    }

}
