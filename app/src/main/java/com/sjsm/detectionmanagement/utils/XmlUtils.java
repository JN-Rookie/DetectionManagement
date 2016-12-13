package com.sjsm.detectionmanagement.utils;

import android.util.Xml;


import com.sjsm.detectionmanagement.model.ResObject;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;

public class XmlUtils {
	public static ResObject parseResponseXML(String xmlStr) throws Exception {
		ResObject resObject = new ResObject();
		XmlPullParser pullParser = Xml.newPullParser();
		ByteArrayInputStream tInputStringStream = null;
		if (xmlStr != null && !xmlStr.trim().equals("")) {
			xmlStr = xmlStr.replaceAll(" ", "").replaceAll("\n", "");
			tInputStringStream = new ByteArrayInputStream(xmlStr.getBytes());
		}
		pullParser.setInput(tInputStringStream, "UTF-8");
		int eventType = pullParser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:
				if ("success".equals(pullParser.getName())) {
					resObject.success = pullParser.nextText();
				} else if ("message".equals(pullParser.getName())) {
					resObject.message = pullParser.nextText();
				} else if ("state".equals(pullParser.getName())) {
					resObject.state = pullParser.nextText();
				} else if ("data".equals(pullParser.getName())) {
					resObject.data = pullParser.nextText();
				}
				break;
			case XmlPullParser.END_TAG:
				break;
			}
			eventType = pullParser.next();
		}
		return resObject;
	}
}
