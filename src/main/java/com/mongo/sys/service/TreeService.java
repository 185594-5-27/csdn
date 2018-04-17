package com.mongo.sys.service;


import com.mongo.common.base.dao.MongodbBaseDao;
import com.mongo.common.base.service.MongodbBaseService;
import com.mongo.sys.dao.TreeDao;
import com.mongo.sys.entity.QueryTree;
import com.mongo.sys.entity.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TreeService extends MongodbBaseService<Tree,QueryTree> {

    @Autowired
    private TreeDao treeDao;

    @Override
    protected MongodbBaseDao<Tree, QueryTree> getDao() {
        return treeDao;
    }
}
