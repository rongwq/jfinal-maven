package com.rong.common.util;

public class PageUtil {
	public static class PageB{
		public PageB(int start, int end, int page, int pageSize) {
			super();
			this.start = start;
			this.end = end;
			this.page = page;
			this.pageSize = pageSize;
		}

		public int start, end, page, pageSize;
	}
	public static PageB build(int pageNumber, int pageSize){
		return new PageB((pageNumber - 1) * pageSize, pageNumber * pageSize - 1, pageNumber, pageSize);
	}
}
