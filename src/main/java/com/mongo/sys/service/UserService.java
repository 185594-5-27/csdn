package com.mongo.sys.service;


import com.mongo.common.base.dao.MongodbBaseDao;
import com.mongo.common.base.service.MongodbBaseService;
import com.mongo.common.util.user.UserInfo;
import com.mongo.sys.dao.UserDao;
import com.mongo.sys.dao.UserRoleDao;
import com.mongo.sys.entity.QueryUser;
import com.mongo.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * 功能描述:实现用户管理的service
 */
@Service
public class UserService extends MongodbBaseService<User,QueryUser> {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    protected MongodbBaseDao<User,QueryUser> getDao() {
        return userDao;
    }

    /**
     * 功能描述：更新用户状态为可用或者不可用
     * @param user
     * @return
     */
    public void userControl(User user){
        userDao.userControl(user);
    }


    @Override
    public void updateById(String id, User user) {
        user.packagingRoles(user.getRoleArray(),userRoleDao);
        super.updateById(id, user);
    }

    @Override
    public User save(User entity) {
        entity.setAddress(entity.getProvince()+entity.getCity()+entity.getDistrict()+entity.getStreetAddress());
        entity.setPassword(UserInfo.encode(entity.getPassword()));
        entity.setState("1");
        entity.packagingRoles(entity.getRoleArray(),userRoleDao);
        return super.save(entity);
    }

    /**
     * 功能描述：根据账号来获取用户信息
     * @param login
     * @return
     */
    public User findByLogin(String login){
        return userDao.findByLogin(login);
    }

}
