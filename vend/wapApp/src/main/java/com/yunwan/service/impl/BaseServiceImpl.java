package com.yunwan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yunwan.bean.BaseBean;
import com.yunwan.dao.BaseDao;
import com.yunwan.query.BaseQuery;
import com.yunwan.service.BaseService;

@Service
public class BaseServiceImpl <T extends BaseBean,E extends BaseQuery> implements BaseService<T,E> {
	@Autowired
	BaseDao<T,E> baseDao;
	
	@Override
	public PageInfo<T> query(E query) {
		PageHelper.startPage(query.getPageNum(), query.getPageSize());
		Page<T> page = (Page<T>) baseDao.query(query);
		PageInfo<T> pageInfo = page.toPageInfo();
		return pageInfo;
	}

}
