package com.mongo.sys.dao;


import com.mongo.common.base.dao.MongodbBaseDao;
import com.mongo.sys.entity.QueryTree;
import com.mongo.sys.entity.Tree;
import org.springframework.stereotype.Component;

@Component
public class TreeDao extends MongodbBaseDao<Tree,QueryTree> {

}
