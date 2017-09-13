package com.gw.common.util;

import com.gw.common.exception.ParamIsNull;

public class ParamUtil {
	public void nullcheck(Object... params) throws ParamIsNull{
		for (Object object : params) {
			if (object == null) {
				throw new ParamIsNull();
			}
		}
	}
}
