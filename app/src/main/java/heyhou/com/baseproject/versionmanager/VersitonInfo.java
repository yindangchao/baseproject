package heyhou.com.baseproject.versionmanager;


import heyhou.com.baseproject.bean.AutoType;
import heyhou.com.baseproject.databases.BaseModel;

/**
 * Created by 1 on 2016/7/5.
 */
public class VersitonInfo extends BaseModel implements AutoType {
    private int isDisableUpgrade;//（0：允许升级；1：禁止升级）
    private int version;
    private String versionName;
    private String info;
    private String url;
    private int force;

    public int getIsDisableUpgrade() {
        return isDisableUpgrade;
    }

    public void setIsDisableUpgrade(int isDisableUpgrade) {
        this.isDisableUpgrade = isDisableUpgrade;
    }

    public int getForce() {
        return force;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
