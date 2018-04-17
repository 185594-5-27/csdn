package com.mongo.sys.service;


import com.mongo.common.base.dao.MongodbBaseDao;
import com.mongo.common.base.service.MongodbBaseService;
import com.mongo.sys.dao.UserRoleDao;
import com.mongo.sys.entity.QueryUserRole;
import com.mongo.sys.entity.User;
import com.mongo.sys.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService extends MongodbBaseService<UserRole,QueryUserRole> {

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    protected MongodbBaseDao<UserRole, QueryUserRole> getDao() {
        return userRoleDao;
    }

    @Override
    public UserRole save(UserRole entity) {
        entity.packagingTrees(entity.getTreeArray());
        return super.save(entity);
    }

    @Override
    public void updateById(String id, UserRole entity) {
        entity.packagingTrees(entity.getTreeArray());
        super.updateById(id, entity);
    }



    /**
     * 功能描述：根据用户来获取用户相应的角色以及他的菜单数据
     * @param user
     * @return
     */
    public List<UserRole> getUserRoleByRoleId(User user){
        return userRoleDao.getUserRoleByRoleId(user.getRoles());
    }
}
