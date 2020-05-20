package com.yunwan.dao;

import java.util.List;

import com.yunwan.bean.BaseBean;
import com.yunwan.query.BaseQuery;

public interface BaseDao <T extends BaseBean,E extends BaseQuery>{
	List<T> query(E e);


}
