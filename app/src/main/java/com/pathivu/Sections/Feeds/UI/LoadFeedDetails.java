package com.pathivu.Sections.Feeds.UI;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pathivu.R;
import com.pathivu.Utils.ConnectionDetector;
import com.pathivu.Utils.Logger;


/**
 * Created by vijayarajsekar on 18/1/16.
 */
public class LoadFeedDetails extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private String mFeedUrl;
    private String mFeedTitle;

    private Bundle mBundle;

    private WebView mWebView;
    private WebSettings mWebSettings;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadfeeds);

        mBundle = getIntent().getExtras();

        if (mBundle != null) {
            mFeedTitle = mBundle.getString("TITLE");
            mFeedUrl = mBundle.getString("LINK");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        toolbar.setTitle(mFeedTitle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(mFeedTitle);

        mWebView = (WebView) findViewById(R.id.webView);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setScrollbarFadingEnabled(false);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);

        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);

                if (new ConnectionDetector(LoadFeedDetails.this).isConnectingToInternet()) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    mWebView.loadUrl(mFeedUrl);
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Logger.NoInternet(LoadFeedDetails.this);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mWebView.loadUrl(mFeedUrl);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                break;

            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}