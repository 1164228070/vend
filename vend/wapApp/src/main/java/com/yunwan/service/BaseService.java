package com.yunwan.service;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.yunwan.bean.BaseBean;
import com.yunwan.query.BaseQuery;

@Service
public interface BaseService<T extends BaseBean,E extends BaseQuery> {
	PageInfo<T> query(E query);

}
