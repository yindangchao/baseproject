package heyhou.com.baseproject.glidetolls;

/**
 * Created by lky on 2017/3/10.
 */

public class GlideConfigInfo {

    private int mRoundSize;             //圆角大小
    private GlideConfigType mConfigType;//类型
    private boolean isCenterCrop;       //是否居中裁剪，默认true

    private int defaultImgRes = -1;

    private boolean useSelfScaleType;

    public GlideConfigInfo(GlideConfigType configType){
        this(configType,10);
    }

    public GlideConfigInfo(GlideConfigType configType,int roundSize){
        this(configType,roundSize,true);
    }

    public GlideConfigInfo(GlideConfigType configType,int roundSize,boolean isCenterCrop){
        this.mConfigType = configType;
        this.mRoundSize = roundSize;
        this.isCenterCrop = isCenterCrop;
    }

    public int getRoundSize() {
        return mRoundSize;
    }

    public GlideConfigType getConfigType() {
        return mConfigType;
    }

    public boolean isCenterCrop() {
        return isCenterCrop;
    }

    public void setDefaultImgRes(int res) {
        defaultImgRes = res;
    }

    public int getDefaultImgRes() {
        return defaultImgRes;
    }

    public void setUseSelfScaleType(boolean useSelfScaleType) {
        this.useSelfScaleType = useSelfScaleType;
    }

    public boolean isUseSelfScaleType() {
        return useSelfScaleType;
    }
}
