package com.corp.myxof.utils;

import com.corp.myxof.config.InfoExtractionConfig;

public class Utils {
	public static boolean isURLLegal(String url){
		if(url == null) return false;
		
		return url.startsWith(InfoExtractionConfig.URL_START_FILTER) && !url.endsWith(InfoExtractionConfig.URL_END_FILTER);
	}
}
