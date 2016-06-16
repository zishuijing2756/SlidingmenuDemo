package com.infzm.slidingmenu.demo.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.infzm.slidingmenu.demo.R;
import com.infzm.slidingmenu.demo.bean.News;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class NewsApdapter extends BaseAdapter {

    private Context                  mContext;
    private List<News>               items;
    private LayoutInflater           factory;

    private DisplayImageOptions      options              = null;
    private ImageLoaderConfiguration config               = null;
    private ImageLoader              imageLoader          = null;
    private ImageLoadingListener     animateFirstListener = null;

    public NewsApdapter(Context mContext, List<News> items){
        this.mContext = mContext;
        factory = LayoutInflater.from(mContext);
        this.items = items;

        animateFirstListener = new AnimateFirstDisplayListener();
        options = new DisplayImageOptions.Builder()// 创建句柄
        .showImageOnLoading(R.drawable.loading)// 加载过程中显示的图片
        .showImageOnFail(R.drawable.img_default)// 加载失败
        .showImageForEmptyUri(R.drawable.img_default)// 空url时显示的图片
        .cacheInMemory(false)// 不缓存至内存（default）
        .cacheOnDisk(true)// 缓存至SD卡
        .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的编码方式
        .imageScaleType(ImageScaleType.IN_SAMPLE_INT)// 设置缩放类型
        .displayer(new RoundedBitmapDisplayer(20))// 设置圆角动画
        .build();

        config = new ImageLoaderConfiguration.Builder(mContext)// 创建句柄
        .memoryCache(new LRULimitedMemoryCache(2 * 1024 * 1024)) // 缓存策略
        .memoryCacheExtraOptions(180, 300) // default = device screen dimensions 内存缓存文件的最大长宽
        .threadPoolSize(5)// 线程池内加载的数量
        .build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = factory.inflate(R.layout.item_list, null);

            viewHolder.img_iv = (ImageView) convertView.findViewById(R.id.iv_img);
            viewHolder.title_tv = (TextView) convertView.findViewById(R.id.tv_title);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.initView(position);
        return convertView;
    }

    private class ViewHolder {

        private ImageView img_iv;
        private TextView  title_tv;

        private void initView(final int position) {

            News news = items.get(position);
            if (!TextUtils.isEmpty(news.title)) {
                title_tv.setText(news.title);
            }

            imageLoader.displayImage(news.picUrl, img_iv, options, animateFirstListener);
        }

    }

    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }

    }
}
