package com.mongo.sys.entity;


import com.mongo.common.base.entity.QueryField;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;


/*
* 类描述：用户角色实体类
* @auther linzf
* @create 2018/3/30 0030 
*/
public class UserRole {

    private ObjectId id;
    // 增加QueryField注解在buildBaseQuery构建Query查询条件的时候会自动将其加入到Query查询条件中
    @QueryField
    private String name;

    private String roleName;

    private List<Tree> treeList;

    // 临时采访菜单数集合的数据
    private String treeArray;

    public List<Tree> getTreeList() {
        return treeList;
    }

    public void setTreeList(List<Tree> treeList) {
        this.treeList = treeList;
    }

    public String getTreeArray() {
        return treeArray;
    }

    public void setTreeArray(String treeArray) {
        this.treeArray = treeArray;
    }

    public String getId() {
        return id.toString();
    }

    public void setId(String id) {
        this.id = new ObjectId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void packagingTrees(String treeArray){
        Tree tree = null;
        List<Tree> trees = new ArrayList<>();
        for(String id:treeArray.split(",")){
            if(!id.isEmpty()){
                tree = new Tree(id);
                trees.add(tree);
            }
        }
        this.setTreeList(trees);
    }

}
