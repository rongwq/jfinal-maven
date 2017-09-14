package com.rong.common.util;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;

/**
 * Created with IntelliJ IDEA. Author: StevenChow Date: 13-5-18
 */
public class ModelKit {
	@SuppressWarnings("rawtypes")
	private Model dao;
	private String cacheNameForOneModel;

	@SuppressWarnings("rawtypes")
	public ModelKit(Model dao, String cacheNameForOneModel) {
		this.dao = dao;
		this.cacheNameForOneModel = cacheNameForOneModel;
	}

	@SuppressWarnings("rawtypes")
	public <M> Page<M> loadModelPageNoCache(Page<M> page) {
		List<M> modelList = page.getList();
		for (int i = 0; i < modelList.size(); i++) {
			Model model = (Model) modelList.get(i);
			M topic = loadModelNoCache(model.getInt("id"));
			modelList.set(i, topic);
		}
		return page;
	}

	@SuppressWarnings("unchecked")
	public <M> M loadModelNoCache(int id) {
		final int ID = id;
		return ((M) dao.findById(ID));

	}

	@SuppressWarnings("rawtypes")
	public <M> Page<M> loadModelPage(Page<M> page) {
		List<M> modelList = page.getList();
		for (int i = 0; i < modelList.size(); i++) {
			Model model = (Model) modelList.get(i);
			M topic = loadModel(model.getInt("id"));
			modelList.set(i, topic);
		}
		return page;
	}

	@SuppressWarnings("rawtypes")
	public <M> M loadModel(int id) {
		final int ID = id;
		final Model DAO = dao;
		return CacheKit.get(cacheNameForOneModel, ID, new IDataLoader() {
			@Override
			public Object load() {
				return DAO.findById(ID);
			}
		});
	}

}
