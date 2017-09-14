package com.rong.common.util;

import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.exception.CommonException;

/*****
 *@Project_Name:xk_common
 * @Copyright:	Copyright © 2012-2017 G-emall Technology Co.,Ltd
 * @Version:	1.0.0.1
 * @File_Name:	ExceptionUtil.java
 * @CreateDate:	2017年9月1日 下午3:17:49
 * @Designer:	zhenghongbin
 * @Desc:			
 * @ModifyHistory:

*****/
public class ExceptionUtil {

	public static CommonException create(String message) {
		return new CommonException(MyErrorCodeConfig.REQUEST_FAIL, message);
	}
}
