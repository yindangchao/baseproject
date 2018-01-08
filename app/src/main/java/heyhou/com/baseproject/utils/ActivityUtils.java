package heyhou.com.baseproject.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import heyhou.com.baseproject.base.BaseMainApp;


/**
 * Created by lky on 2017/3/15.
 */

public class ActivityUtils {

    /**
     * 登录
     *
     * @param context
     */
    public static boolean checkLoginActivity(Context context) {
        if (BaseMainApp.getInstance().isLogin) {
            return true;
        }
//        context.startActivity(new Intent(context, LoginActivity.class));
        return false;

    }


}
