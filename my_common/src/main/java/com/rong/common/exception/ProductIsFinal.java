package com.rong.common.exception;


/**
 * 商品已完结（卖完最后一期）
 * @author jerry
 *
 */
public class ProductIsFinal extends Exception {
	private static final long serialVersionUID = -5551057110107850478L;

	public ProductIsFinal() {
		super("该商品已卖完最后一期");
		// TODO Auto-generated constructor stub
	}

	public ProductIsFinal(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ProductIsFinal(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ProductIsFinal(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ProductIsFinal(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}


}
