package com.rong.admin.controller;

import com.jfinal.core.Controller;
import com.rong.system.service.IndexService;
import com.rong.system.service.IndexServiceImpl;

public class IndexController extends Controller {
	IndexService service = new IndexServiceImpl();
	
	public void index() {
		render("/views/login.jsp");
	}

}
