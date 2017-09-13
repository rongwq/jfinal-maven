package com.rong.admin.controller;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.gw.common.exception.CommonException;
import com.gw.common.validator.CommonValidatorUtils;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.bean.BaseRenderJson;
import com.rong.persist.model.Admin;
import com.rong.system.service.AdminService;
import com.rong.system.service.AdminServiceImpl;
import com.rong.system.service.RoleService;
import com.rong.system.service.RoleServiceImpl;

public class AdminController extends BaseController{
	private final Log logger = Log.getLog(this.getClass());
	private AdminService adminService = new AdminServiceImpl();
	private RoleService roleService = new RoleServiceImpl();
	
	public void captcha() {
		renderCaptcha();
	}
	
	@SuppressWarnings("deprecation")
	public void login() {
	    String username = getPara("userName");
	    String password = getPara("password");
	    //接收文本框值，toUpperCase()忽略验证码大小写
	    /* 验证码
	    String inputRandomCode = getPara("inputRandomCode");
	    boolean loginSuccess = CaptchaRender.validate(this, inputRandomCode.toUpperCase());
	    if(!loginSuccess){//验证码错误
	    	this.redirect("/views/login.jsp?isError=1&username="+username+"&msg="+URLEncoder.encode("验证码错误"));
	    	return;
	    }
	    */
	    
	    try {
	    	Admin u = adminService.getByUserName(username);
	    	if(u==null){
	    		throw new CommonException("loginError", "username not exist!");
	    	}
	    	password = genPassword(password, u.getStr("salt"));
	        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
	        Subject subject = SecurityUtils.getSubject();
	        // 进行用用户名和密码验证,如果验证不过会throw exception
	        subject.login(token);
	        //save admin session
	        setSessionAttr(Admin.SESSION_KEY, u);
	        //保存数据到登录日志表
	        saveLog(getUser().getUserName()+"登录成功");
	        // 调转到admin主页面
	        this.redirect("/views/index.jsp");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        this.redirect("/views/login.jsp?isError=1&username="+username+"&msg="+URLEncoder.encode("用户名或者密码错误"));
	    }
	}
	
	private String genPassword(String password, String salt) {
		return getMD5((password + salt).getBytes());
	}

	private String getMD5(byte[] src) {
		StringBuffer sb = new StringBuffer();
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(src);
			for (byte b : md.digest())
				sb.append(Integer.toString(b >>> 4 & 0xF, 16)).append(Integer.toString(b & 0xF, 16));
		} catch (NoSuchAlgorithmException e) {
		}
		return sb.toString();
	}
	
	public void logout() {
		 Subject subject = SecurityUtils.getSubject();
		 //保存数据到登录日志表
		 saveLog(getUser().getUserName()+"退出登录");
		 setSessionAttr(Admin.SESSION_KEY, null);
	     subject.logout();
	     this.redirect("/views/login.jsp");
	}
	
	public void toEdit() {
		Integer id = getParaToInt("id");
		if(id!=null){
			Admin user = Admin.dao.findById(id);
			setAttr("user", user);
		}
		keepPara();
		setAttr("roles", roleService.getRolesList());
		render("/views/system/user/edit.jsp");
	}

	public void edit() {
		String userName = getPara("userName");
		String password = getPara("password");
		String role = getPara("role");
		Integer id = getParaToInt("id");
		String email = getPara("email");
		String mobile = getPara("mobile");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userName", userName);
		paramMap.put("password", password);
		paramMap.put("role", role);
		paramMap.put("email", email);
		paramMap.put("mobile", mobile);
		if(CommonValidatorUtils.requiredValidate(paramMap, this)){
			return;
		}
		if (adminService.getByUserName(userName)!=null && id==null) {
			BaseRenderJson.returnJsonS(this,0,"账号已经存在");
			return;
		}
		if(id==null){
			Admin model = new Admin();
			model.setUserName(userName);
			model.setCreateTime(new Date());
			model.setRole(role);
			String salt = genSalt();
			model.setPassword(genPassword(password, salt));
			model.setSalt(salt);
			model.setEmail(email);
			model.setMobile(mobile);
			if (model.save()) {
				BaseRenderJson.returnAddObj(this, true);
				logger.info("[操作日志]添加新用户"+userName+"成功");
			} else {
				BaseRenderJson.returnAddObj(this, false);
				logger.error("[操作日志]添加新用户"+userName+"失败");
			}
		}else{
			Admin admin = Admin.dao.findById(id);
			admin.setRole(role);
			admin.setEmail(email);
			admin.setMobile(mobile);
			if (admin.update()) {
				BaseRenderJson.returnUpdateObj(this, true);
				logger.info("[操作日志]修改用户"+admin.getUserName()+"成功");
			} else {
				BaseRenderJson.returnUpdateObj(this, false);
				logger.info("[操作日志]修改用户"+admin.getUserName()+"失败");
			}
		}
	}

	public void delete() {
		Integer id = getParaToInt("id");
		Admin u = Admin.dao.findById(id);
		if(u.delete()){
			BaseRenderJson.returnDelObj(this, true);
			logger.info("[操作日志]删除用户"+u.getUserName()+"成功,删除用户组中间表id:"+u.getId()+"成功");
		}else{
			BaseRenderJson.returnDelObj(this, false);
			logger.info("[操作日志]删除用户"+u.getUserName()+"失败,删除用户组中间表id:"+u.getId()+"失败");
		}
		
	}

	public void backPassword() {
		int uid = getParaToInt("id");
		Admin u = Admin.dao.findById(uid);
		u.set("password", genPassword("123456", u.getStr("salt")));
		if (u.update()) {
			BaseRenderJson.returnJsonS(this,1,"还原用户密码成功");
			logger.info("[操作日志]还原用户"+u.getStr("userName")+"密码成功");
		} else {
			BaseRenderJson.returnJsonS(this,0,"还原用户密码失敗");
			logger.info("[操作日志]还原用户"+u.getStr("userName")+"密码失败");
		}
	}

	public void updatePassword() {
		int id = getParaToInt("id");
		Admin au = Admin.dao.findById(id);
		String oldPassword = getPara("oldPassword");
		String password = getPara("password");
		String confirmPassword = getPara("confirmPassword");
		if (!password.equals(confirmPassword)) {
			BaseRenderJson.returnJsonS(this,0,"两次密码输入不一致");
			return;
		}
		if (oldPassword.equals(confirmPassword)) {
			BaseRenderJson.returnJsonS(this,0,"新密码与旧密码一样");
			return;
		}
		String oldEnpassword = genPassword(oldPassword, au.getStr("salt"));
		if (!oldEnpassword.equals(au.getStr("password"))) {
			BaseRenderJson.returnJsonS(this,0,"旧密码输入错误");
			return;
		}
		au.set("password", genPassword(password, au.getStr("salt")));
		if (au.update()) {
			BaseRenderJson.returnUpdateObj(this, true);
			logger.info("[操作日志]用户"+au.getUserName()+"更新密码成功");
		} else {
			BaseRenderJson.returnUpdateObj(this, false);
			logger.info("[操作日志]用户"+au.getUserName()+"更新密码失败");
		}
	}

	public void userList() {
		int page = getParaToInt("page", 1);
		Page<Admin> list = adminService.getList(page, pageSize);
		keepPara();
		setAttr("page", list);
		render("/views/system/user/list.jsp");
	}

	private String genSalt() {
		int x = (int) (Math.random() * 10000);
		String salt = String.valueOf(x);
		return salt;
	}
	public void userInfo(){
		Admin user = getSessionAttr("ADMIN_USER");
		BaseRenderJson.returnViewObjectTmplate(this, "1", user);
	}
	
	public void userInfoToEdit(){
		Admin user = getSessionAttr("ADMIN_USER");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user", user);
		map.put("roles", roleService.getRolesList());
		BaseRenderJson.returnViewObjectTmplate(this, "1", map);
	}
	
	public void userInfoEdit(){
		Admin user = getSessionAttr("ADMIN_USER");
		String role = getPara("role");
		String email = getPara("email");
		String mobile = getPara("mobile");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("role", role);
		paramMap.put("email", email);
		paramMap.put("mobile", mobile);
		if(CommonValidatorUtils.requiredValidate(paramMap, this)){
			return;
		}
		user.setRole(role);
		user.setEmail(email);
		user.setMobile(mobile);
		if (user.update()) {
			BaseRenderJson.returnUpdateObj(this, true);
			logger.info("[操作日志]修改用户"+user.getUserName()+"成功");
		} else {
			BaseRenderJson.returnUpdateObj(this, false);
			logger.info("[操作日志]修改用户"+user.getUserName()+"失败");
		}
	}
}
