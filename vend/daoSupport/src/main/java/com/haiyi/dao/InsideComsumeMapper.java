package com.haiyi.dao;

import com.haiyi.dao.base.BaseDao;
import com.haiyi.domain.InsideComsume;
import com.haiyi.domain.InsideComsume;
import com.haiyi.domain.vo.OrderMonthStasticVO;
import com.haiyi.query.InsideComsumeQuery;
import com.maizi.exception.KPException;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InsideComsumeMapper extends BaseDao<InsideComsume,InsideComsumeQuery> {

	//List<InsideComsume> findByOrderId(@Param("orderId") String orderId);

    int updateStatus(@Param("id") Integer id, @Param("payStatus") Byte payStatus);







}