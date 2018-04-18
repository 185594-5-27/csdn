package com.mongo.sys.service;


import com.mongo.common.base.dao.MongodbBaseDao;
import com.mongo.common.base.service.MongodbBaseService;
import com.mongo.sys.dao.OrgGroupDao;
import com.mongo.sys.entity.OrgGroup;
import com.mongo.sys.entity.QueryOrgGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
* 类描述：
* @auther linzf
* @create 2018/4/3 0003 
*/
@Service
public class OrgGroupService extends MongodbBaseService<OrgGroup,QueryOrgGroup> {

    @Autowired
    private OrgGroupDao orgGroupDao;

    @Override
    protected MongodbBaseDao<OrgGroup, QueryOrgGroup> getDao() {
        return orgGroupDao;
    }
}
