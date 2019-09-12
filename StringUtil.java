package com.nnbp.application.util;

public class StringUtil {
	
	public static String fillZeroLeft(String orgString,String seqString ,int figure) {
		if (orgString == null) {
			orgString = "";
		}

		StringBuffer _buffer = new StringBuffer(orgString);
		for(int i = orgString.length(); i < figure; i++) {
			_buffer.insert(orgString.length(), "0");
		}
		_buffer.replace(_buffer.length() - seqString.length() , _buffer.length() , seqString);
		if (figure < _buffer.length()) {
			return _buffer.toString().substring(_buffer.length()-figure);
		} else {
			return _buffer.toString();
		}
	}
}
