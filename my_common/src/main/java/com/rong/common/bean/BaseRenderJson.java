package com.rong.common.bean;

import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.rong.common.util.StringUtils;

public class BaseRenderJson {
	public static final BaseRenderJson baseRenderObj = new BaseRenderJson();
	/**
	 * 更新
	 * 
	 * @param ai
	 * @param flag
	 */
	public static void returnUpdateObj(Controller ai, boolean flag) {
		BaseTemplate obj = new BaseTemplate();
		if (flag) {
			obj.setResultCode("1");
			obj.setResultDes("更新成功！");
			ai.renderJson(obj);
		} else {
			obj.setResultCode("0");
			obj.setResultDes("更新失败！");
			ai.renderJson(obj);
		}
	}
	
	/**
	 * 新增
	 * 
	 * @param ai
	 * @param flag
	 */
	public static void returnAddObj(Controller ai, boolean flag) {
		BaseTemplate obj = new BaseTemplate();
		if (flag) {
			obj.setResultCode("1");
			obj.setResultDes("新增成功！");
			ai.renderJson(obj);
		} else {
			obj.setResultCode("0");
			obj.setResultDes("新增失败！");
			ai.renderJson(obj);
		}
	}

	/**
	 * 发送
	 * 
	 * @param ai
	 * @param flag
	 */
	public static void returnSendObj(Controller ai, boolean flag) {
		BaseTemplate obj = new BaseTemplate();
		if (flag) {
			obj.setResultCode("1");
			obj.setResultDes("发送成功！");
			ai.renderJson(obj);
		} else {
			obj.setResultCode("0");
			obj.setResultDes("发送失败！");
			ai.renderJson(obj);
		}
	}
	
	/**
	 * 结果返回字符串
	 * 
	 * @param ai
	 * @param flag
	 * @param tags
	 */
	public static void returnJsonS(Controller ai, int flag, String tags) {
		String json = "{\"resultCode\":\"" + flag + "\",\"resultDes\":\"" + tags + "\"}";
		ai.renderJson(json);
	}

	/**
	 * 删除
	 * 
	 * @param ai
	 * @param flag
	 */
	public static void returnDelObj(Controller ai, boolean flag) {
		BaseTemplate obj = new BaseTemplate();
		if (flag) {
			obj.setResultCode("1");
			obj.setResultDes("删除成功！");
			ai.renderJson(obj);
		} else {
			obj.setResultCode("0");
			obj.setResultDes("删除失败！");
			ai.renderJson(obj);
		}
	}

	/**
	 * 删除
	 * 
	 * @param ai
	 * @param flag
	 * @param tag
	 *            成功与失败的提示语
	 */
	public static void returnDelObj(Controller ai, boolean flag, String[] tag) {
		BaseTemplate obj = new BaseTemplate();
		if (flag) {
			obj.setResultCode("1");
			obj.setResultDes(tag[0]);
			ai.renderJson(obj);
		} else {
			obj.setResultCode("0");
			obj.setResultDes(tag[1]);
			ai.renderJson(obj);
		}
	}


	/**
	 * 参数错误
	 * @param ai
	 * @param para
	 */
	public static void returnParaErrorObj(Controller ai,String para) {
		BaseTemplate tmp = new BaseTemplate();
		tmp.setResultCode(MyErrorCodeConfig.ERROR_BAD_REQUEST);
		tmp.setResultDes(para+"参数缺失或出错！");
		ai.renderJson(tmp);
	}
	

	/**
	 * 操作
	 * 
	 * @param ai
	 * @param flag
	 */
	public static void returnOpareObj(Controller ai, boolean flag) {
		BaseTemplate obj = new BaseTemplate();
		if (flag) {
			obj.setResultCode("1");
			obj.setResultDes("操作成功！");
			ai.renderJson(obj);
		} else {
			obj.setResultCode("0");
			obj.setResultDes("操作失败！");
			ai.renderJson(obj);
		}
	}
	
	public static void returnBaseTemplateObj(Controller ai,String code,String tag) {
		BaseTemplate tmp = new BaseTemplate();
		tmp.setResultCode(code);
		tmp.setResultDes(tag);
		ai.renderJson(tmp);
	}
	
	public void returnUserIdErrorObj(Controller ai) {
		BaseTemplate obj = new BaseTemplate();
		obj.setResultCode(MyErrorCodeConfig.ERROR_TOKEN_MISS);
		obj.setResultDes("非法用户！");
		ai.renderJson(obj);
	}
	
	/**
	 * token验证出错返回
	 * 
	 * @param ai
	 * @param tag
	 */
	public void returnTokenErrorObj(Controller ai, int tag) {
		BaseTemplate obj = new BaseTemplate();
		if (tag == 1) {
			obj.setResultCode(MyErrorCodeConfig.ERROR_TOKEN_MISS);
			obj.setResultDes("token为空！");
			ai.renderJson(obj);
		} else if (tag == 2) {
			obj.setResultCode(MyErrorCodeConfig.ERROR_TOKEN_MISS);
			obj.setResultDes("token错误！");
			ai.renderJson(obj);
		} else if (tag == 3) {
			obj.setResultCode(MyErrorCodeConfig.ERROR_TOKEN_EXPIRE);
			obj.setResultDes("token过期，请重新登录！");
			ai.renderJson(obj);
		}
	}
	
	/**
	 * 保存
	 * 
	 * @param ai
	 * @param id
	 * @param rongToken TODO
	 */
	public void returnRegObj(Controller ai, Object entity, int id, String tag,
			String token) {
		ResultObjectWithToken obj = new ResultObjectWithToken();
		if (id > 0) {
			obj.setResultCode("1");
			obj.setResultDes("注册成功!");
			obj.setResultData(token,entity);
			ai.renderJson(obj);
		} else if (id == -1) {
			obj.setResultCode("ACCOUNT_EXISTS");
			obj.setResultDes("手机号已被注册！");
			ai.renderJson(obj);
		} else if (id == 0) {
			obj.setResultCode("0");
			obj.setResultDes("注册失败！");
			if (!StringUtils.isNullOrEmpty(tag)) {
				obj.setResultCode(tag);
				if(tag.equals("MOBILE_ERROR_001")){
					obj.setResultDes("验证码复用或不存在");
				}else if(tag.equals("MOBILE_ERROR_002")){
					obj.setResultDes("验证码超时");
				}else if(tag.equals("CODE_ERROR")){
					obj.setResultDes("验证码不正确");
				}else if(tag.equals("REGISTER_ERROR")){
					obj.setResultDes("用户名注册失败");
				}else if(tag.equals("ACCOUNT_EXISTS")){
					obj.setResultDes("手机号已被注册");
				}else{
					obj.setResultDes("注册失败！");
				}
				ai.renderJson(obj);
			}
		}
	}
	
	/**
	 * 登录返回
	 * 
	 * @param ai
	 * @param rongToken TODO
	 * @param id
	 */
	public void returnLoginObj(Controller ai, Object entity, String[] tags,
			String token) {
		ResultObjectWithToken obj = new ResultObjectWithToken();
		if (entity != null) {
			obj.setResultCode("1");
			obj.setResultDes(tags[0]);
			obj.setResultData(token,entity);
			ai.renderJson(obj);
		} else {
			obj.setResultCode(tags[0]);
			obj.setResultDes(tags[1]);
			ai.renderJson(obj);
		}
	}
	
	/**
	 * 登出返回
	 * 
	 * @param ai
	 * @param id
	 */
	public void returnLogoutObj(Controller ai, int tag) {
		BaseTemplate obj = new BaseTemplate();
		if (tag > 0) {
			obj.setResultCode("1");
			obj.setResultDes("登出成功");
			ai.renderJson(obj);
		} else {
			obj.setResultCode("0");
			obj.setResultDes("登出失败");
			ai.renderJson(obj);
		}
	}

	/**
	 * 自动登录返回
	 * 
	 * @param ai
	 * @param id
	 */
	public void returnAutoLoginObj(Controller ai, Object entity) {
		BaseTemplate obj = new BaseTemplate();
		if (entity != null) {
			obj.setResultCode("1");
			obj.setResultDes("登录成功!");
			obj.setResultData(entity);
			ai.renderJson(obj);
		} else {
			obj.setResultCode("0");
			obj.setResultDes("登录失败!");
			ai.renderJson(obj);
		}
	}
	
	/**
	 * 查询返回对象
	 * 
	 * @param ai
	 * @param id
	 */
	public void returnObj(Controller ai, Object entity, String[] tags) {
		BaseTemplate obj = new BaseTemplate();
		if (entity != null) {
			obj.setResultCode("1");
			obj.setResultData(entity);
			obj.setResultDes(tags[0]);
			ai.renderJson(obj);
		} else {
			obj.setResultCode("0");
			obj.setResultDes(tags[1]);
			ai.renderJson(obj);
		}
	}
	
	public static void returnViewObjectTmplate(Controller ai,String success ,Object obj){
		BaseTemplate tmp = new BaseTemplate();
		tmp.setResultCode(success);
		if(success.equals(MyErrorCodeConfig.REQUEST_FAIL)){
			tmp.setResultDes("查询失败");
		}else if(success.equals(MyErrorCodeConfig.REQUEST_SUCCESS)){
			if(null==obj){
				tmp.setResultDes("查询成功,但是没有找到要查询数据");
			}else{
				tmp.setResultDes("查询成功");
			}
			tmp.setResultData(obj);
		}
		ai.renderJson(tmp);
	}
	
	public static void returnObjectTmplate(Controller ai,String code  ,Object obj,String tag){
		BaseTemplate tmp = new BaseTemplate();
		tmp.setResultCode(code);
		tmp.setResultDes(tag);
		tmp.setResultData(obj);
		ai.renderJson(tmp);
	}
	
	/**
	 * 查询返回集合数据
	 * 
	 * @param ai
	 * @param list
	 */
	public void returnQueryMap(Controller ai, Map<?, ?> map) {
		BaseTemplate tmp = new BaseTemplate();
		if (map != null) {
			tmp.setResultCode("1");
			tmp.setResultDes("获取查询数据成功");
			tmp.setResultData(map);
			ai.renderJson(tmp);
		} else {
			tmp.setResultCode("0");
			tmp.setResultDes("获取资源失败！");
			ai.renderJson(tmp);
		}
	}
	
	/**
	 * 查询返回集合数据
	 * 
	 * @param ai
	 * @param list
	 */
	public void returnQueryList(Controller ai, List<?> list) {
		BaseTemplate tmp = new BaseTemplate();
		if (list != null) {
			tmp.setResultCode("1");
			tmp.setResultDes("获取查询数据成功");
			tmp.setResultData(list);
			ai.renderJson(tmp);
		} else {
			tmp.setResultCode("0");
			tmp.setResultDes("获取资源失败！");
			ai.renderJson(tmp);
		}
	}
	/**
	 * 
	 * @param ai
	 * @param flag
	 * @param tag
	 * 保存成功与失败的提示语
	 */
	public static void returnSaveObj(Controller ai, int id, String[] tag) {
		BaseTemplate obj = new BaseTemplate();
		if (id>0) {
			obj.setResultCode("1");
			obj.setResultDes(tag[0]);
			obj.setResultData(id);
			ai.renderJson(obj);
		} else {
			obj.setResultCode("0");
			obj.setResultDes(tag[1]);
			ai.renderJson(obj);
		}
	}
	/**
	 * 点赞时提示
	 */
	public static void returnSaveObjParise(Controller ai, int id, String[] tag) {
		BaseTemplate obj = new BaseTemplate();
		if (id > 0) {
			obj.setResultCode("1");
			obj.setResultDes(tag[0]);
			ai.renderJson(obj);
		} else if (id == 0) {
			obj.setResultCode("0");
			obj.setResultDes(tag[1]);
			ai.renderJson(obj);
		} else if (id == -1) {
			obj.setResultCode("-1");
			obj.setResultDes(tag[2]);
			ai.renderJson(obj);
		} else if (id == -2) {
			obj.setResultCode("-2");
			obj.setResultDes(tag[3]);
			ai.renderJson(obj);
		}
	}
}
