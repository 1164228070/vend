package com.haiyi.dao;
import com.haiyi.dao.base.BaseDao;
import com.haiyi.domain.Dict;
import com.haiyi.query.DictQuery;

import java.util.List;

public interface DictMapper extends BaseDao<Dict,DictQuery> {
    List<Dict> selectDict();
}