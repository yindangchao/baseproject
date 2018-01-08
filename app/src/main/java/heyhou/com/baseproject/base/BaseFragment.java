package heyhou.com.baseproject.base;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;

import heyhou.com.baseproject.R;
import heyhou.com.baseproject.utils.DebugTool;
import heyhou.com.baseproject.utils.ToastTool;


/**
 * Created by lky on 2016/10/14.
 */

public class BaseFragment extends Fragment {

    protected final String TAG = getClass().getSimpleName();

    protected Context mContext;
    protected PermissionTask permissionTask;
    protected OnPermissionDenyListener mOnPermissionDenyListener;
    private static final int FRAGMENT_PERMISSION_CODE = 2000;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DebugTool.debug(TAG, "onCreate");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        DebugTool.debug(TAG, "onAttach");
        this.mContext = context;
    }

    public interface PermissionTask {
        void operate();
    }

    protected void applyPermission(String permission, PermissionTask task) {
        applyPermission(permission,task,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DebugTool.debug(TAG, "onViewCreated");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        DebugTool.debug(TAG, "onHiddenChanged :" + hidden);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DebugTool.debug(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        DebugTool.debug(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        DebugTool.debug(TAG, "onResume");
    }

    @Override
    public void onPause() {
        DebugTool.debug(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        DebugTool.debug(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        DebugTool.debug(TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        DebugTool.debug(TAG, "onDetach");
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        DebugTool.debug(TAG, "onDestroy");
        super.onDestroy();
    }

    protected void applyPermission(String permission, PermissionTask task, OnPermissionDenyListener onPermissionDenyListener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if(task != null){
                task.operate();
            }
//            setPermissionTask(task);
//            requestPermission(permission);
            return;
        }
        this.mOnPermissionDenyListener = onPermissionDenyListener;
        setPermissionTask(task);
        permission(permission);
    }

    protected void setPermissionTask(PermissionTask task) {
        this.permissionTask = task;
    }

    protected void permission(String permission) {
        if (!(ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_GRANTED)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                //已经禁止提示了
                ToastTool.showShort(BaseApplication.m_appContext, String.format(getString(R.string.permission_prohibit_tip), getRefuseMsg(permission)));
                if (mOnPermissionDenyListener != null) {
                    mOnPermissionDenyListener.onPermissionDeny();
                }
                return;
            }
            requestPermission(permission);
        } else {
            if (permissionTask != null)
                permissionTask.operate();
        }
    }

    private void requestPermission(String permission) {
        requestPermissions(new String[]{permission}, FRAGMENT_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case FRAGMENT_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //用户同意授权
                    if (permissionTask != null)
                        permissionTask.operate();
                } else {
                    //用户拒绝授权
                    if (mOnPermissionDenyListener != null) {
                        mOnPermissionDenyListener.onPermissionDeny();
                    }
                    if (permissions == null || permissions.length == 0) {
                        ToastTool.showShort(BaseApplication.m_appContext, String.format(getString(R.string.permission_prohibit_tip), getString(R.string.permission_default)));
                    } else {
                        ToastTool.showShort(BaseApplication.m_appContext, String.format(getString(R.string.permission_prohibit_tip), getRefuseMsg(permissions[0])));
                    }
                }
                break;
        }
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

    public interface OnPermissionDenyListener {
        void onPermissionDeny();
    }
}
