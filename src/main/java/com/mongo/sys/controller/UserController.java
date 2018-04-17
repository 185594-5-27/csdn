package com.mongo.sys.controller;


import com.mongo.common.base.constant.SystemStaticConst;
import com.mongo.common.base.controller.MongodbBaseController;
import com.mongo.common.base.entity.Pagination;
import com.mongo.common.base.service.MongodbBaseService;
import com.mongo.common.util.user.UserInfo;
import com.mongo.sys.entity.QueryUser;
import com.mongo.sys.entity.Tree;
import com.mongo.sys.entity.User;
import com.mongo.sys.entity.UserRole;
import com.mongo.sys.service.TreeService;
import com.mongo.sys.service.UserRoleService;
import com.mongo.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController extends MongodbBaseController<User,QueryUser> {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private TreeService treeService;

    @Override
    protected MongodbBaseService<User,QueryUser> getService() {
        return userService;
    }


    /**
     * 功能描述：更新用户状态为禁用/启用
     * @param entity
     * @return
     */
    @RequestMapping(value="/userControl")
    @ResponseBody
    public Map<String,Object> userControl(User entity) throws Exception{
        Map<String,Object> result = new HashMap<String, Object>();
        userService.userControl(entity);
        result.put(SystemStaticConst.RESULT,SystemStaticConst.SUCCESS);
        result.put(SystemStaticConst.MSG,"更新用户状态成功！");
        result.put("entity",entity);
        return result;
    }

    /**
     * 功能描述：加载所有的权限数据
     * @return
     */
    @RequestMapping(value = "/loadRoles",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> loadRoles(){
        Map<String,Object> result = new HashMap<String, Object>();
        List<UserRole> userRoleList = userRoleService.find(new Query());
        result.put(SystemStaticConst.RESULT,SystemStaticConst.SUCCESS);
        result.put("list",userRoleList);
        return result;
    }

    /**
     * 功能描述：加载首页菜单节点的数据
     * @return
     */
    @RequestMapping(value="/mainTree",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> mainTree(){
        Map<String,Object> result = new HashMap<String, Object>();
        List<Tree> trees = UserInfo.loadUserTree(userRoleService,treeService);
        result.put("data",trees);
        result.put(SystemStaticConst.RESULT, SystemStaticConst.SUCCESS);
        return result;
    }


    /**
     * 功能描述：获取用户的分页的数据
     * @param entity
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> list(QueryUser entity){
        Map<String,Object> result = new HashMap<String, Object>();
        Pagination page = userService.findByPage(entity);
        result.put("totalCount",page.getTotalNumber());
        result.put("result",page.getItems());
        return result;
    }
}
