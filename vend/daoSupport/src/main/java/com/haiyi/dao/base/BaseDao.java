package com.haiyi.dao.base;
import java.util.List;
import java.util.Map;

import com.haiyi.query.base.BaseQuery;
/**
 * 
  * @ClassName: BaseDao
  * @Company: 麦子科技
  * @Description: 泛型Dao接口
  * @author 技术部-谢维乐
  * @date 2016年4月2日 下午5:19:52
  *
  * @param <T>
 */
public interface BaseDao<T,E extends BaseQuery> {
	int check(Map<String,Object> param);
    
	int count(Map<String,Object> param);
	
    T findById(String id);
    
    List<T> findByIds(String[] ids);
    
    List<T> findBySelective(E e);
    
    int add(T t);

    int update(T t);

	int deleteById(String id);

	int deleteByIds(String[] ids);
}