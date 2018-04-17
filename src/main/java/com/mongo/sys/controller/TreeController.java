package com.mongo.sys.controller;


import com.mongo.common.base.constant.SystemStaticConst;
import com.mongo.common.base.controller.MongodbBaseController;
import com.mongo.common.base.service.MongodbBaseService;
import com.mongo.sys.entity.QueryTree;
import com.mongo.sys.entity.Tree;
import com.mongo.sys.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tree")
public class TreeController  extends MongodbBaseController<Tree,QueryTree> {

    @Autowired
    private TreeService treeService;

    @Override
    protected MongodbBaseService<Tree, QueryTree> getService() {
        return treeService;
    }

    /**
     * 功能描述：跳转到修改菜单节点的页面
     * @param entity
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/updateTreePage")
    public String updateTreePage(Tree entity,Model model) throws Exception{
        entity = treeService.get(entity.getId());
        Tree pTree = null;
        if(entity.getParentId().equals("5ac0c4a0c053f417ac310e3f")){
            pTree = new Tree();
            pTree.setId("5ac0c4a0c053f417ac310e3f");
            pTree.setName("顶层节点");
        }else{
            pTree = treeService.get(entity.getParentId());
        }
        entity.setTree(pTree);
        model.addAttribute("entity",entity);
        return getPageBaseRoot()+UPDATEPAGE;
    }

    /**
     * 功能描述：跳转到增加菜单节点的页面
     * @param entity
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/addTreePage")
    public String addPage(Tree entity,Model model) throws Exception{
        if(entity.getId().equals("5ac0c4a0c053f417ac310e3f")){
            entity = new Tree();
            entity.setId("5ac0c4a0c053f417ac310e3f");
            entity.setName("顶层节点");
        }else{
            entity = treeService.get(entity.getId());
        }
        model.addAttribute("entity",entity);
        return getPageBaseRoot()+ADDPAGE;
    }

    /**
     * 功能描述：直接加载整个菜单树的数据(且必须要有管理员权限才可以加载该菜单树的数据)
     * @return
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/loadUserTree",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> loadUserTree(){
        Map<String,Object> result = new HashMap<String, Object>();
        List<Tree> treeList = treeService.find(new Query());
        result.put(SystemStaticConst.RESULT, SystemStaticConst.SUCCESS);
        result.put(SystemStaticConst.MSG,"加载菜单数据成功！");
        result.put("data",treeList);
        return result;
    }
}
