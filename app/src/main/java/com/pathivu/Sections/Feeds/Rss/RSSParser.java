package com.pathivu.Sections.Feeds.Rss;


import com.pathivu.Sections.Feeds.Model.TNews;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vijayarajsekar on 18/1/16.
 */
public class RSSParser extends DefaultHandler {
    private List<TNews> news = new ArrayList<TNews>();
    private TNews currentNews;
    private boolean parsingTitle;
    private boolean parsingLink;
    private boolean parsingDescription;
    private boolean parsingDate;

    public List<TNews> getNews() {
        return news;
    }

    @Override
    public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes) throws SAXException {
        if (qName.equals("item")) {
            currentNews = new TNews();
        }
        if (qName.equals("title")) {
            parsingTitle = true;
        }
        if (qName.equals("description")) {
            parsingDescription = true;
        }
        if (qName.equals("link")) {
            parsingLink = true;
        }
        if (qName.equals("pubDate")) {
            parsingDate = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("item")) {
            //   Log.d("Parser", currentNews.getDescription());
            news.add(currentNews);
            currentNews = null;
        }
        if (qName.equals("title")) {
            parsingTitle = false;
        }
        if (qName.equals("description")) {
            parsingDescription = false;
        }
        if (qName.equals("link")) {
            parsingLink = false;
        }
        if (qName.equals("pubDate")) {
            parsingDate = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentNews == null) {
            return;
        }

        String newText = new String(ch, start, length);

        if (parsingTitle) {
            parsingTitle = false;
            currentNews.setTitle(newText);
        }
        if (parsingDescription) {
            parsingDescription = false;
            currentNews.setDescription(newText);
        }
        if (parsingLink) {
            parsingLink = false;
            currentNews.setLink(newText);
        }
        if (parsingDate) {
            parsingDate = false;
            currentNews.setDate(newText);
        }
    }
}
