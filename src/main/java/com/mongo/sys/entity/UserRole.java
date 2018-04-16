package com.mongo.sys.entity;


import com.mongo.common.base.entity.QueryField;
import org.bson.types.ObjectId;


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

}
