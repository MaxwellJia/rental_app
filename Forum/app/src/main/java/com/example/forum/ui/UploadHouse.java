package com.example.forum.ui;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UploadHouse implements Serializable {
    private static final Random random = new Random(System.nanoTime());
    private transient Context mContext;
    public UploadHouse(Context context) {
        this.mContext = context;
    }
    public List<String> getProvinces() {
        List<String> provinces = new ArrayList<>();
        try {
            // Get the resource ID for the XML file
            InputStream is = mContext.getAssets().open("addressBook.xml");
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(is, null);
            int eventType = parser.getEventType();
            boolean isInsideTargetProvince = false;
            //find all province
            while ((eventType != XmlPullParser.END_DOCUMENT)) {
                if (eventType == XmlPullParser.START_TAG && "province".equals(parser.getName())) {
                    provinces.add(parser.getAttributeValue(null, "name"));
                }
                eventType = parser.next();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        provinces.add(0,"select your city");
        return provinces;
    }
    public List<String> getSuburbs(String targetProvince) {
        List<String> suburbs = new ArrayList<>();
        try {
            // Get the resource ID for the XML file
            InputStream is = mContext.getAssets().open("addressBook.xml");
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(is, null);
            int eventType = parser.getEventType();
            boolean isInsideTargetProvince = false;
            //find all province
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if ("province".equals(parser.getName()) && targetProvince.equals(parser.getAttributeValue(null, "name"))) {
                        isInsideTargetProvince = true;
                    } else if (isInsideTargetProvince && "suburb".equals(parser.getName())) {
                        suburbs.add(parser.getAttributeValue(null, "name"));
                    }
                } else if (eventType == XmlPullParser.END_TAG && "province".equals(parser.getName())) {
                    isInsideTargetProvince = false;
                }
                eventType = parser.next();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        suburbs.add(0,"select your suburb");
        return suburbs;
    }

    public List<String> getStreetsForSelectedSuburb(String selectedProvince, String selectedSuburb) {
        List<String> streets = new ArrayList<>();
        try {
            XmlPullParser parser = Xml.newPullParser();
            InputStream is = mContext.getAssets().open("addressBook.xml");
            parser.setInput(is, null);
            int eventType = parser.getEventType();
            boolean insideTargetProvince = false;
            boolean insideTargetSuburb = false;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String tagName = parser.getName();
                    if ("province".equals(tagName) && selectedProvince.equals(parser.getAttributeValue(null, "name"))) {
                        insideTargetProvince = true;
                    } else if (insideTargetProvince && "suburb".equals(tagName) && selectedSuburb.equals(parser.getAttributeValue(null, "name"))) {
                        insideTargetSuburb = true;
                    } else if (insideTargetSuburb && "street".equals(tagName)) {
                        streets.add(parser.nextText());
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if ("province".equals(parser.getName())) {
                        insideTargetProvince = false;
                    } else if ("suburb".equals(parser.getName())) {
                        insideTargetSuburb = false;
                    }
                }
                eventType = parser.next();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        streets.add(0,"select your street");
        return streets;
    }
}
