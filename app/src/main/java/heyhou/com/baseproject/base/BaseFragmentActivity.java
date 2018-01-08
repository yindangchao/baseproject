package heyhou.com.baseproject.base;

import android.content.Context;
import android.os.Bundle;

import heyhou.com.baseproject.utils.ViewTools;


/**
 * Created by 1 on 2016/5/4.
 */
public class BaseFragmentActivity extends BaseAppCompatActivity {
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewTools.setWindowStatusBarColor(this);
//        BaseMainApp.getInstance().addActivity(this);
        this.mContext = this;
    }

}
