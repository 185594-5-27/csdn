package com.mongo.sys.service;


import com.mongo.common.base.dao.MongodbBaseDao;
import com.mongo.common.base.service.MongodbBaseService;
import com.mongo.sys.dao.DictDao;
import com.mongo.sys.entity.Dict;
import com.mongo.sys.entity.QueryDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DictService extends MongodbBaseService<Dict,QueryDict> {

    @Autowired
    private DictDao dictDao;

    @Override
    protected MongodbBaseDao<Dict, QueryDict> getDao() {
        return dictDao;
    }


}
