package com.rong.common.util;

import java.net.URL;

public class Gutil {
	public static URL resource(String name){
		return classLoader().getResource(name);
	}

	public static ClassLoader classLoader() {
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		return contextClassLoader;
	}
}
