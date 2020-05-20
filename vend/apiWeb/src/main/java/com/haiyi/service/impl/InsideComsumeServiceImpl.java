package com.haiyi.service.impl;

import com.github.pagehelper.PageInfo;
import com.haiyi.dao.InsideComsumeMapper;
import com.haiyi.domain.InsideComsume;
import com.haiyi.query.InsideComsumeQuery;
import com.haiyi.service.InsideComsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsideComsumeServiceImpl  implements InsideComsumeService {

    @Autowired
    InsideComsumeMapper insideComsumeMapper;



    @Override
    public PageInfo<InsideComsume> findBySelective(InsideComsumeQuery e) {
        return new PageInfo<InsideComsume>(this.insideComsumeMapper.findBySelective(e));
    }

    @Override
    public int update(InsideComsume insideComsume) {
        return this.insideComsumeMapper.update(insideComsume);
    }

    @Override
    public int add(InsideComsume insideComsume) {
        return this.insideComsumeMapper.add(insideComsume);
    }
}
