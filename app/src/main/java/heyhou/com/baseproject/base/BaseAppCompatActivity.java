package heyhou.com.baseproject.base;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import heyhou.com.baseproject.R;
import heyhou.com.baseproject.utils.DebugTool;
import heyhou.com.baseproject.utils.ProtocolHelper;
import heyhou.com.baseproject.utils.ViewTools;

/**
 * Created by 1 on 2016/5/4.
 */
public class BaseAppCompatActivity extends AppCompatActivity {
    protected final String TAG = getClass().getSimpleName();
    protected PermissionTask permissionTask;
    protected OnPermissionDenyListener mOnPermissionDenyListener;
    protected OnCompleteListener onCompleteListener;
    public static final int ACTIVITY_PERMISSION_CODE = 1000;
    public static final int ACTIVITY_PERMISSIONS_CODE = 1500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewTools.setWindowStatusBarColor(this);
        DebugTool.debug(TAG, "onCreate");
    }


    @Override
    protected void onStart() {
        super.onStart();
        DebugTool.debug(TAG, "onStart");
    }

    protected void setHeadTitle(String title) {
        TextView tvTitle = (TextView) findViewById(R.id.title_content_text);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
        tvTitle.setOnClickListener(listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DebugTool.debug(TAG, "onResume");

        IntentFilter filter = new IntentFilter(Constant.ACTION_LOGIN_INFO_OUT_OF_DATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        DebugTool.debug(TAG, " onPause");
    }


    @Override
    protected void onStop() {
        DebugTool.debug(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        DebugTool.debug(TAG, "onDestroy");
        super.onDestroy();
    }


    protected void setHeadTitle(int resId) {
        TextView tvTitle = (TextView) findViewById(R.id.title_content_text);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(resId);
        tvTitle.setOnClickListener(listener);
    }

    protected void setBack() {
        ImageView imgBack = (ImageView) findViewById(R.id.title_left_back);
        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(listener);
    }

    protected void setRightText(String text) {
        TextView tvRightText = (TextView) findViewById(R.id.title_right_text);
        tvRightText.setVisibility(View.VISIBLE);
        tvRightText.setText(text);
        tvRightText.setOnClickListener(listener);
    }

    protected void setRightText(int resId) {
        TextView tvRightText = (TextView) findViewById(R.id.title_right_text);
        tvRightText.setVisibility(View.VISIBLE);
        tvRightText.setText(resId);
        tvRightText.setOnClickListener(listener);
    }

    protected void setRightIv(int resId) {
        ImageView ivRight = (ImageView) findViewById(R.id.title_right_edit);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(resId);
        ivRight.setOnClickListener(listener);
    }

    protected void setRightSecondIv(int resId) {
        ImageView ivRight = (ImageView) findViewById(R.id.title_second_right_home);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(resId);
        ivRight.setOnClickListener(listener);
    }

    protected void onHeadRightClick() {

    }

    protected void onHeadRightSecondClick() {

    }

    public final boolean isProtocol() {
        return ProtocolHelper.getInstance().isProtocol(getIntent());
    }

    protected final boolean isProtocol(Intent intent) {
        return ProtocolHelper.getInstance().isProtocol(intent);
    }

    public final int getProtocolId() {
        return ProtocolHelper.getInstance().obtainIdParam(getIntent());
    }

    protected final int getProtocolId(Intent intent) {
        return ProtocolHelper.getInstance().obtainIdParam(intent);
    }

    protected final String getProtocolStringParam(String key) {
        return ProtocolHelper.getInstance().getStringParam(getIntent(), key);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.title_left_back:
                    onBackPressed();
                    break;
                case R.id.title_right_text:
                    onHeadRightClick();
                    break;
                case R.id.title_right_edit:
                    onHeadRightClick();
                    break;
                case R.id.title_second_right_home:
                    onHeadRightSecondClick();
                    break;
            }
        }
    };

    protected void setPermissionTask(PermissionTask task) {
        this.permissionTask = task;
    }

    protected void applyPermission(String permission, PermissionTask task, OnPermissionDenyListener onPermissionDenyListener) {
        applyPermission(permission, task, onPermissionDenyListener, null);
    }

    protected void applyPermission(String permission,
                                   PermissionTask task,
                                   OnPermissionDenyListener onPermissionDenyListener
            , OnCompleteListener onCompleteListener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (task != null) {
                task.operate();
            }
            return;
        }
        this.mOnPermissionDenyListener = onPermissionDenyListener;
        this.onCompleteListener = onCompleteListener;
        setPermissionTask(task);
        permission(permission);
    }

    protected void applyPermissions(String[] permissions) {
        List<String> result = new ArrayList<>();
        if (permissions != null && permissions.length > 0) {
            for (String permission : permissions) {
                if (!(ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED)) {
                    result.add(permission);
                }
            }
            if (result.size() > 0)
                ActivityCompat.requestPermissions(this, result.toArray(new String[result.size()]), ACTIVITY_PERMISSIONS_CODE);
        }
    }


    protected void applyPermission(String permission, PermissionTask task) {
        applyPermission(permission, task, null);
    }


    protected void permission(String permission) {
        if (!(ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                //已经禁止提示了
                if (mOnPermissionDenyListener != null) {
                    mOnPermissionDenyListener.onPermissionDeny();
                }
                Toast.makeText(BaseApplication.m_appContext, String.format(getString(R.string.permission_prohibit_tip), getRefuseMsg(permission)), Toast.LENGTH_SHORT).show();
                onComplete();
                return;
            }
            requestPermission(permission);
        } else {
            if (permissionTask != null) {
                permissionTask.operate();
            }
            onComplete();
        }
    }

    private void onComplete() {
        if (onCompleteListener != null) {
            onCompleteListener.onPermissionComplete();
        }
    }

    private void requestPermission(String permission) {
        ActivityCompat.requestPermissions(this, new String[]{permission}, ACTIVITY_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ACTIVITY_PERMISSION_CODE:
                DebugTool.info("permission----------->>>");
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //用户同意授权
                    if (permissionTask != null) {
                        permissionTask.operate();
                    }
                    onComplete();
                } else {
                    //用户拒绝授权
                    if (mOnPermissionDenyListener != null) {
                        mOnPermissionDenyListener.onPermissionDeny();
                    }
                    if (permissions == null || permissions.length == 0) {
                        Toast.makeText(BaseApplication.m_appContext, String.format(getString(R.string.permission_prohibit_tip), getString(R.string.permission_default)), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BaseApplication.m_appContext, String.format(getString(R.string.permission_prohibit_tip), getRefuseMsg(permissions[0])), Toast.LENGTH_SHORT).show();
                    }

                    onComplete();
                }
                break;
        }
    }

    public interface PermissionTask {
        void operate();
    }

    public interface OnPermissionDenyListener {
        void onPermissionDeny();
    }

    public interface OnCompleteListener {
        void onPermissionComplete();
    }

    private String getRefuseMsg(String permission) {
        String permissionStr = getString(R.string.permission_default);
        if (permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionStr = getString(R.string.permission_location);
        } else if (permission.equals(Manifest.permission.CAMERA)) {
            permissionStr = getString(R.string.permission_camera);
        } else if (permission.equals(Manifest.permission.RECORD_AUDIO)) {
            permissionStr = getString(R.string.permission_record);
        } else if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            permissionStr = getString(R.string.permission_storage);
        } else if (permission.equals(Manifest.permission.CALL_PHONE)) {
            permissionStr = getString(R.string.permission_phone);
        }
        return permissionStr;
    }

}
