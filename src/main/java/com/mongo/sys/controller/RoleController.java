package com.mongo.sys.controller;


import com.mongo.common.base.constant.SystemStaticConst;
import com.mongo.common.base.controller.MongodbBaseController;
import com.mongo.common.base.entity.Pagination;
import com.mongo.common.base.service.MongodbBaseService;
import com.mongo.sys.entity.QueryUserRole;
import com.mongo.sys.entity.Tree;
import com.mongo.sys.entity.UserRole;
import com.mongo.sys.service.TreeService;
import com.mongo.sys.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController extends MongodbBaseController<UserRole,QueryUserRole> {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private TreeService treeService;

    @Override
    protected MongodbBaseService<UserRole, QueryUserRole> getService() {
        return userRoleService;
    }

    /**
     * 功能描述：获取用户的分页的数据
     * @param entity
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> list(QueryUserRole entity){
        Map<String,Object> result = new HashMap<String, Object>();
        Pagination<UserRole> page = userRoleService.findByPage(entity);
        result.put("totalCount",page.getTotalNumber());
        result.put("result",page.getItems());
        return result;
    }

    /**
     * 功能描述：根据用户的权限去加载角色数据
     * @return
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/loadRoleTree",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> loadRoleTree(UserRole entity){
        entity = userRoleService.get(entity.getId()+"");
        List<Tree> treeList = treeService.find(new Query());
        if(entity!=null){
            for(Tree tree:entity.getTreeList()){
                treeList.stream().forEach(t->{
                    if(t.getId().equals(tree.getId())){
                        t.setChecked(true);
                    }
                });
            }
        }
        Map<String,Object> result = new HashMap<String, Object>();
        result.put(SystemStaticConst.RESULT, SystemStaticConst.SUCCESS);
        result.put("data",treeList);
        return result;
    }

}
