package com.example.prussia.tools;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * author：Prussia
 * date：2018/9/5
 * describe：加载图片框架
 * 注意这里上下文context最好用界面的的上下文，如果用aplication的上下文容易导致内存oom
 */

public class ImgaLoad {

    //Glide加载普通图片
    public static void load(Context context, String url, ImageView imageView, boolean isCircle, int error) {
        if (context != null && url != null && imageView != null) {
            try {
                DrawableTypeRequest<String> load = Glide.with(context).load(url);
                //展示图形是否是圆形
                if (isCircle) {
                    load.bitmapTransform(new CropCircleTransformation(context));
                }
                //加载失败是否展示错误图
                if (error != 0) {
                    load.error(error);
                }
                //将图片加载到控件上
                load.into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Glide加载倒圆角的图片
    public static void loadRound(Context context, String url, ImageView imageView, int radius, int margin, int error) {
        if (context != null && url != null && imageView != null) {
            try {
                DrawableTypeRequest<String> load = Glide.with(context).load(url);
                //展示图形是否是圆形
                load.bitmapTransform(new RoundedCornersTransformation(context, radius, margin, RoundedCornersTransformation.CornerType.ALL));
                //加载失败是否展示错误图
                if (error != 0) {
                    load.error(error);
                }
                //将图片加载到控件上
                load.into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
