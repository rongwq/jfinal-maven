package com.rong.common.bean;

public class RedisKeyConst {
	public static final String USER_CACHE = "userMap";
	public static final String USERTOKEN_CACHE = "userTokenMap";
	public static final String SYSCONFMAP = "sysConfMap";
	public static final String SYSCONFMAP_APP = "sysConfMap_app";
	public static final String WALLET_MAP = "walletMap";// 用户钱包
	public static final String COUPON_MAP = "couponMap";// 用户代金券
	public static final String SMALLCHANGE_MAP = "smallChangeMap";// 用户零钱
	public static final String SOLD_VOLUME = "sold_volume";

	/**
	 * 一折购库存量(规格ID为键，规格库存量为值) --- 在一折购和全额购扣减库存是用到，开奖执行完中奖订单商品库存扣减后需要更新此缓存的值。<br>
	 * Map 形式缓存，规格ID为键，库存量为值
	 */
	public static final String STOCK_4_ONE = "stock4one";

	/**
	 * 开奖时生效的商品库存修改 -- 商家管理后台可以修改商品库存，但不是即时生效，要等到开奖执行完中奖订单商品库存扣减后再让操作生效。 <br>
	 * Map 形式缓存，规格ID为键，库存量为值
	 */
	public static final String KAIJIANG_EFFECT_PRODUCT_STOCK = "KAIJIANG_EFFECT_PRODUCT_STOCK";

	/**
	 * 开奖时生效的商品状态修改（删除和下架） --
	 * 商家管理后台可以下架和删除商品，但不是即时生效，要等到开奖执行完中奖订单商品库存扣减后再让操作生效。<br>
	 * Map 形式缓存，商品ID为键，商品状态为值
	 */
	public static final String KAIJIANG_EFFECT_PRODUCT_STATUS = "KAIJIANG_EFFECT_PRODUCT_STATUS";

	/**
	 * 商品上架延迟生效
	 */
	public static final String PRODUCT_ON = "PRODUCT_ON";

	public static final String ORDER_PAY_CALLBACK_LOCK = "ORDER_PAY_CALLBACK_LOCK:";
}
