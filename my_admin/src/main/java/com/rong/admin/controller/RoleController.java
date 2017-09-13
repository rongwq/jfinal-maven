package com.rong.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.RealmSecurityManager;

import com.gw.common.validator.CommonValidatorUtils;
import com.jfinal.aop.Duang;
import com.jfinal.json.Json;
import com.rong.common.bean.BaseRenderJson;
import com.rong.persist.model.AdminResource;
import com.rong.persist.model.AdminRole;
import com.rong.system.service.ResourceService;
import com.rong.system.service.ResourceServiceImpl;
import com.rong.system.service.RoleService;
import com.rong.system.service.RoleServiceImpl;

/****
 * @Project_Name:	gxt_admin
 * @Copyright:		Copyright © 2012-2016 g-emall Technology Co.,Ltd
 * @Version:		1.1.0
 * @File_Name:		RoleController.java
 * @CreateDate:		2016年6月7日 上午10:08:12
 * @Designer:		rongwq
 * @Desc:			
 * @ModifyHistory:	
 ****/

public class RoleController extends BaseController{
	private final Logger logger = Logger.getLogger(this.getClass());
	RoleService roleService = Duang.duang(RoleServiceImpl.class);//明显开启事务
	ResourceService resourceService = new ResourceServiceImpl();
	
	public void list(){
		setAttr("list", roleService.getList());
		render("/views/system/role/list.jsp");
	}
	
	public void toEdit() {
		Integer id = getParaToInt("id");
		if(id!=null){
			AdminRole role = roleService.getById(id);
			setAttr("role", role);
		}
		render("/views/system/role/edit.jsp");
	}
	
	public void edit() {
		String rname = getPara("rname");
		String remark = getPara("remark");
		Integer id = getParaToInt("id");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("rname", rname);
		if(CommonValidatorUtils.requiredValidate(paramMap, this)){
			return;
		}
		if(id==null){
			if (roleService.getByName(rname)!=null) {
				BaseRenderJson.returnJsonS(this,0,"角色已经存在");
				return;
			}
			AdminRole model = new AdminRole();
			model.setRname(rname);
			model.setRemark(remark);
			model.setCreateTime(new Date());
			if (model.save()) {
				BaseRenderJson.returnAddObj(this, true);
				logger.info("[操作日志]添加角色"+rname+"成功");
			} else {
				BaseRenderJson.returnAddObj(this, false);
				logger.info("[操作日志]添加角色"+rname+"失败");
			}
		}else{
			AdminRole model = roleService.getById(id);
			model.setRemark(remark);
			if (roleService.update(id, null, remark)) {
				BaseRenderJson.returnUpdateObj(this, true);
				logger.info("[操作日志]修改角色"+rname+"成功");
			} else {
				BaseRenderJson.returnUpdateObj(this, false);
				logger.info("[操作日志]修改角色"+rname+"成功");
			} 
		}
	}

	public void delete() {
		Integer id = getParaToInt("id");
		String rname = roleService.getById(id).getRname();
		if(roleService.isExistUserByRname(rname)){
			BaseRenderJson.returnJsonS(this,0,"温馨提示：该角色下有多个用户，无法删除！");
			return;
		}
		if(roleService.delete(id)){
			BaseRenderJson.returnDelObj(this, true);
			logger.info("[操作日志]删除角色"+rname+"成功");
		}else{
			BaseRenderJson.returnDelObj(this, false);
			logger.info("[操作日志]删除角色"+rname+"失败");
		}
	}
	
	public void toPermissions() {
		Integer id = getParaToInt("id");
		if(id!=null){
			AdminRole role = roleService.getById(id);
			setAttr("role", role);
			List<AdminResource> resourceList = resourceService.getAll();
			List<AdminResource> roleResourceLIst = roleService.getRolePermissions(id);
			List<Map<String, Object>> list = new ArrayList<>();
			for (AdminResource adminResource : resourceList) {
				int resId = adminResource.getId();
				Map<String, Object> map = new HashMap<>();
				map.put("id", adminResource.getId());
				map.put("name", adminResource.getName());
				Integer pid = adminResource.getPid();
				if(pid==null){
					map.put("pId", 0);
				}else{
					map.put("pId", pid);
				}
				map.put("open", true);
				for (AdminResource roleResource : roleResourceLIst) {
					if(resId==roleResource.getId()){
						map.put("checked", true);
						break;
					}
				}
				list.add(map);
			}
			setAttr("mytree", Json.getJson().toJson(list));
			render("/views/system/role/rolePermissions.jsp");
		}
	}
	
	public void savePermissions() {
		Integer roleId = getParaToInt("roleId");
		String ids = getPara("resources");
		String [] arr = ids.split(",");
		List<Integer> list = new ArrayList<>();
		for (int i=0;i<arr.length;i++) {
			list.add(Integer.parseInt(arr[i]));
		}
		if (roleService.saveRolePermissions(roleId, list)) {
			clearCache();
			BaseRenderJson.returnUpdateObj(this, true);
			logger.info("[操作日志]权限保存成功");
		} else {
			BaseRenderJson.returnUpdateObj(this, false);
			logger.info("[操作日志]权限保存失败");
		}
	}

	//清理shiro缓存
	private void clearCache() {
		RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
		MemoryConstrainedCacheManager cache = (MemoryConstrainedCacheManager)securityManager.getCacheManager();
		cache.getCache("dbRealm.authorizationCache").clear();
	}
}

