package com.mongo.sys.dao;


import com.mongo.common.base.dao.MongodbBaseDao;
import com.mongo.sys.entity.Dict;
import com.mongo.sys.entity.QueryDict;
import org.springframework.stereotype.Component;

@Component
public class DictDao extends MongodbBaseDao<Dict,QueryDict> {

}
