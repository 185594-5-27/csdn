package com.mongo.sys.dao;


import com.mongo.common.base.dao.MongodbBaseDao;
import com.mongo.common.base.entity.Pagination;
import com.mongo.sys.entity.QueryUser;
import com.mongo.sys.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/*
* 类描述：实现用户管理的dao
* @auther linzf
* @create 2018/3/30 0030 
*/
@Component
public class UserDao extends MongodbBaseDao<User,QueryUser> {



    /**
     * 功能描述：根据账号来获取用户信息
     * @param login
     * @return
     */
    public User findByLogin(String login){
        Query query=new Query(Criteria.where("login").is(login));
        User user =  mongoTemplate.findOne(query , User.class);
        return user;
    }

    /**
     * 功能描述：更新用户状态为可用或者不可用
     * @param user
     * @return
     */
    public void userControl(User user){
        Query query=new Query(Criteria.where("id").is(user.getId()));
        Update update= new Update().set("state", user.getState());
        mongoTemplate.updateFirst(query,update,User.class);
    }

    @Override
    public Pagination<User> findByPage(QueryUser queryUser) {
        Query query = buildBaseQuery(queryUser);
        if(queryUser.getGroupId()!=null&&!queryUser.getGroupId().equals("")){
            query.addCriteria(Criteria.where("orgGroup.id").is(new ObjectId(queryUser.getGroupId())));
        }
        //获取总条数
        long totalCount = this.mongoTemplate.count(query, this.getEntityClass());
        //总页数
        int totalPage = (int) (totalCount/queryUser.getLimit());
        int skip = (queryUser.getPage()-1)*queryUser.getLimit();
        Pagination<User> page = new Pagination(queryUser.getPage(), totalPage, (int)totalCount);
        query.skip(skip);// skip相当于从那条记录开始
        query.limit(queryUser.getLimit());// 从skip开始,取多少条记录
        List<User> data = this.find(query);
        page.build(data);//获取数据
        return page;
    }
}
