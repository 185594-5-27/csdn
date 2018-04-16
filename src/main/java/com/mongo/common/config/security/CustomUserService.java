package com.mongo.common.config.security;



import com.mongo.sys.dao.UserDao;
import com.mongo.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/4 0004.
 */
public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.findByLogin(s);
        if(user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 用户登陆以后更新用户的最迟登陆时间
        Query query = new Query(Criteria.where("id").is(user.getId()));
        Update update= new Update().set("lastLoginDate", new Date());
        userDao.update(query,update);
        // 自定义错误的文章说明的地址：http://blog.csdn.net/z69183787/article/details/21190639?locationNum=1&fps=1
        if(user.getState()==null||user.getState().equalsIgnoreCase("0")){
            throw new LockedException("用户账号被冻结，无法登陆请联系管理员！");
        }
        return user;
    }
}
