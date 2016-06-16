package com.infzm.slidingmenu.demo.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.infzm.slidingmenu.demo.MainActivity;
import com.infzm.slidingmenu.demo.R;
import com.infzm.slidingmenu.demo.WebViewActivity;
import com.infzm.slidingmenu.demo.adapter.NewsApdapter;
import com.infzm.slidingmenu.demo.bean.News;
import com.infzm.slidingmenu.demo.constants.Constants;
import com.infzm.slidingmenu.demo.utils.HttpUtils;

/**
 * @author wuwenjie
 * @description 今日
 */
public class AppleFragment extends Fragment {

    private LayoutInflater        factory;
    private LinearLayout          contentLayout;
    private View                  contentView;
    private PullToRefreshListView pullToRefreshListView = null;
    private NewsApdapter          newsApdapter          = null;
    private int                   pageNum               = 1;

    private List<News>            appleList             = new ArrayList<News>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        factory = LayoutInflater.from(getActivity());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.frag_apple, null);

        contentLayout = (LinearLayout) contentView.findViewById(R.id.ll_apple_container);

        initListView();
        return contentView;
    }

    private void initListView() {
        if (pullToRefreshListView == null) {
            pullToRefreshListView = (PullToRefreshListView) factory.inflate(R.layout.item_pulltorefresh_listview, null);
            /** 设置ListView的滚动条不可见 */
            pullToRefreshListView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                                                                   LinearLayout.LayoutParams.MATCH_PARENT));
            pullToRefreshListView.setBackgroundColor(getResources().getColor(R.color.white));
            contentLayout.addView(pullToRefreshListView);
        }

        pullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                appleList.clear();
                pageNum = 0;
                new GetData().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNum++;
                new GetData().execute();
            }
        });
        pullToRefreshListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent((MainActivity) getActivity(), WebViewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri uri = Uri.parse(appleList.get(position - 1).url);
                intent.setData(uri);
                ((MainActivity) getActivity()).startActivity(intent);
            }
        });

        initData(appleList);
    }

    private void initData(List<News> data) {
        if (data == null || data.size() == 0) {
            new GetData().execute();
            return;
        }

        if (newsApdapter == null) {
            newsApdapter = new NewsApdapter(getActivity(), appleList);
            pullToRefreshListView.setAdapter(newsApdapter);
        }

        appleList.addAll(data);
        newsApdapter.notifyDataSetChanged();
        pullToRefreshListView.onRefreshComplete();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class GetData extends AsyncTask<String, Integer, List<News>> {

        private List<News> tempList = new ArrayList<News>();
        private String     result   = null;

        @Override
        protected synchronized List<News> doInBackground(String... params) {
            result = HttpUtils.getHttpRequest(Constants.URL_APPLE + pageNum);// http://apis.baidu.com/txapi/world/world?num=10&page=1
            try {
                if (TextUtils.isEmpty(result)) return null;
                JSONObject obj = new JSONObject(result);
                int code = obj.getInt(Constants.CODE);
                if (code != 200) return null;
                JSONArray jsonArr = obj.getJSONArray(Constants.NEWSLIST);
                if (jsonArr == null || jsonArr.length() == 0) return null;
                tempList.clear();
                int length = jsonArr.length();
                for (int i = 0; i < length; i++) {
                    JSONObject newsObj = jsonArr.getJSONObject(i);
                    News news = new News();
                    news.picUrl = newsObj.getString(Constants.PICURL);
                    news.title = newsObj.getString(Constants.TITLE);
                    news.description = newsObj.getString(Constants.DESCRIPTION);
                    news.url = newsObj.getString(Constants.URL);
                    tempList.add(news);
                }
            } catch (JSONException e) {
                Log.e("GetData", e.getMessage());
            }
            return tempList;
        }

        @Override
        protected void onPostExecute(List<News> result) {
            super.onPostExecute(result);

            if (result == null || result.size() == 0) {
                return;
            }
            if (pageNum == 1) {
                appleList.clear();
            }

            appleList.addAll(result);
            if (newsApdapter == null) {
                newsApdapter = new NewsApdapter(getActivity(), appleList);
                pullToRefreshListView.setAdapter(newsApdapter);
            } else {
                newsApdapter.notifyDataSetChanged();
            }
            pullToRefreshListView.onRefreshComplete();
        }
    }
}
