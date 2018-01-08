package heyhou.com.baseproject.pull;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import heyhou.com.baseproject.R;
import heyhou.com.baseproject.base.ex.InjectView;
import heyhou.com.baseproject.base.ex.ViewAnotationUtil;


/**
 * Created by Neil.Yang on 2017/4/5.
 *
 *  列表页面没有数据时候默认显示的文本和icon
 *
 */

public class DefaultNoDataView extends LinearLayout {

    private static final int DEFAULT_VALUE = -1;

    @InjectView(id = R.id.iv_no_data_icon)
    private ImageView mIvNoDataImg;
    @InjectView(id = R.id.tv_no_data_text)
    private TextView mTvText;

    public DefaultNoDataView(Context context) {
        super(context);
        initView(context, null);
    }

    public DefaultNoDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        inflate(context, R.layout.default_no_data_view, this);
        ViewAnotationUtil.autoInjectAllField(this, this);

        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.DefaultNoDataView);

        //获取图片资源
        int imgRes = typeArray.getResourceId(R.styleable.DefaultNoDataView_defaultImage, DEFAULT_VALUE);
        if (imgRes != DEFAULT_VALUE) {
            mIvNoDataImg.setImageResource(imgRes);
        }
        //获取文本
        String text = typeArray.getString(R.styleable.DefaultNoDataView_defaultText);
        if (text != null) {
            mTvText.setText(text);
        }
        //获取文本的颜色
        int color = typeArray.getColor(R.styleable.DefaultNoDataView_defaultTextColor, DEFAULT_VALUE);
        if (color != DEFAULT_VALUE) {
            mTvText.setTextColor(color);
        }
        //获取文本的大小
        int textSize = typeArray.getDimensionPixelSize(R.styleable.DefaultNoDataView_defaultTextSize, DEFAULT_VALUE);
        if (textSize != DEFAULT_VALUE) {
            mTvText.setTextSize(textSize);
        }

        //设置图片的宽度和高度
        int imageWidth = typeArray.getDimensionPixelSize(R.styleable.DefaultNoDataView_defaultImageWidth, DEFAULT_VALUE);
        if (imageWidth != DEFAULT_VALUE) {
            LayoutParams params = (LayoutParams)mIvNoDataImg.getLayoutParams();
            params.width = imageWidth;
            mIvNoDataImg.setLayoutParams(params);
        }

        int imageHeight = typeArray.getDimensionPixelSize(R.styleable.DefaultNoDataView_defaultImageHeight, DEFAULT_VALUE);
        if (imageWidth != DEFAULT_VALUE) {
            LayoutParams params = (LayoutParams)mIvNoDataImg.getLayoutParams();
            params.height = imageHeight;
            mIvNoDataImg.setLayoutParams(params);
        }
        //设置图片和文字之间的距离
        int verticalSpace = typeArray.getDimensionPixelSize(R.styleable.DefaultNoDataView_verticalSpace, DEFAULT_VALUE);
        if (verticalSpace != DEFAULT_VALUE) {
            LayoutParams params = (LayoutParams)mIvNoDataImg.getLayoutParams();
            params.bottomMargin = verticalSpace;
            mIvNoDataImg.setLayoutParams(params);
        }
        typeArray.recycle();
    }


}
