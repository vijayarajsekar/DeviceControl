package com.pathivu.Sections.Feeds.Rss;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.pathivu.Sections.Feeds.Interfaces.Constants;
import com.pathivu.Sections.Feeds.Interfaces.OnItemClickListener;
import com.pathivu.Sections.Feeds.Model.TNews;
import com.pathivu.Sections.Feeds.UI.FeedsFragment;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * Created by vijayarajsekar on 18/1/16.
 */

public class UpdateRSSList extends AsyncTask<Void, Void, List<TNews>> implements Constants {

    private Context mContext;
    private RecyclerView mRssList;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public UpdateRSSList(Context ctx, RecyclerView feedslist, SwipeRefreshLayout swipeRefreshLayout) {
        mContext = ctx;
        mRssList = feedslist;
        mSwipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    protected List<TNews> doInBackground(Void... ignore) {

        List<TNews> mResultList = new ArrayList<TNews>();

        List<TNews> mRecentFeedsList;

        try {

            URL url = new URL(mFeedsURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream stream = connection.getInputStream();

            Log.d("Updater", "OK 1");

            Reader reader = new InputStreamReader(stream, "UTF-8");
            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");

            Log.d("Updater", "OK 2");

            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            RSSParser RSSParser = new RSSParser();
            saxParser.parse(is, RSSParser);

            Log.d("Updater", "OK 3");

            mRecentFeedsList = RSSParser.getNews();

            if (mRecentFeedsList != null) {
                for (int j = 0; j < mRecentFeedsList.size(); j++) {
                    mRecentFeedsList.get(j).setParent(mFeedsTitle);
                    mResultList.add(mRecentFeedsList.get(j));
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();

            // stopping swipe refresh
            stopSwipLoading();

        } catch (IOException e) {
            e.printStackTrace();

            // stopping swipe refresh
            stopSwipLoading();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();

            // stopping swipe refresh
            stopSwipLoading();

        } catch (SAXException e) {
            e.printStackTrace();

            // stopping swipe refresh
            stopSwipLoading();
        } catch (Exception ex) {
            ex.printStackTrace();

            // stopping swipe refresh
            stopSwipLoading();
        }

        return mResultList;
    }

    @Override
    protected void onPostExecute(List<TNews> res) {

        if (res == null || res.size() == 0) {

            Log.d("UpdateRSSList", "null pointer!!!!!!!!!!!!!!");

        } else {

            Log.d("UpdateRSSList", res.get(0).getDescription());

        }

        Collections.sort(res, new Comparator<TNews>() {
            public int compare(TNews o1, TNews o2) {
                if (o1.getDate2() == null) {
                    return 1;
                }
                if (o2.getDate2() == null) {
                    return -1;
                }
                return -o1.getDate2().compareTo(o2.getDate2());
            }
        });

        RSSAdapter mAdapter = new RSSAdapter(mContext, res);

        mAdapter.SetOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, TNews tNews) {
                new FeedsFragment().ClickedItem(mContext, view, position, tNews);
            }
        });

        mRssList.setAdapter(mAdapter);

        // stopping swipe refresh
        stopSwipLoading();
    }

    private void stopSwipLoading() {
        try {
            mSwipeRefreshLayout.setRefreshing(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
