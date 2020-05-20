package com.haiyi.service.base;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.haiyi.query.base.BaseQuery;
import com.maizi.exception.KPException;
/**
  * 
  * @ClassName: BaseService
  * @Company: 麦子科技
  * @Description: 泛型Service接口
  * @author 技术部-谢维乐
  * @date 2016年4月2日 下午5:21:38
  *
  * @param <T>
 */
public interface BaseService<T,E extends BaseQuery> {
    
    int check(Map<String,Object> param);
    
    int count(Map<String,Object> param);
    
    //根据id查询分页
    T findById(String id);
    
    //查询所有
    List<T> findAll();
    
    //多添加查询
    PageInfo<T> findBySelective(E e);
    
    //根据Id数组查询所有
    List<T> findByIds(String... ids);
    
    //添加
    int add(T t) throws KPException;

    //更新
    int updateById(T t) throws KPException;
    
    /**
     * 根据Id更新
     * @param ignoreProperties    忽略属性
     * @return
     */
    int updateById(T t, String... ignoreProperties);
    
    /**
     * 根据Id更新
     * @param t
     * @param onlyProperties    仅这些属性
     * @return
     */
    int updateByIdOnly(T t, String... onlyProperties);
    
    //删除
    int deleteById(String id) throws KPException;

    //批量删除
	int deleteByIds(String ... id);
}