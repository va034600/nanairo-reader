package com.gmail.va034600.nreader.business.rss.exception;

@SuppressWarnings("serial")
public class RssParsingException extends Exception {
	public RssParsingException(String message) {
		super(message);
	}

	public RssParsingException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
