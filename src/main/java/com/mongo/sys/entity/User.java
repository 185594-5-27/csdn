package com.mongo.sys.entity;


import com.mongo.common.base.entity.QueryField;
import com.mongo.sys.dao.UserRoleDao;
import net.sf.json.JSONObject;
import org.bson.types.ObjectId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/*
* 类描述：用户实体类
* @auther linzf
* @create 2018/3/30 0030 
*/
public class User implements UserDetails {

    public static void main(String [] args){
        User user = new User();
        List<UserRole> roles = new ArrayList<UserRole>();
        UserRole userRole = new UserRole();
        userRole.setId("5ac0e051c053f4297804f42c");
        userRole.setName("ROLE_ADMIN");
        userRole.setRoleName("系统管理员");
        roles.add(userRole);
        user.setLogin("shyll");
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRoles(roles);
        user.setId(ObjectId.get().toString());
        user.setState("1");
        System.out.println(JSONObject.fromObject(user).toString());

    }


    private ObjectId id;
    // 增加QueryField注解在buildBaseQuery构建Query查询条件的时候会自动将其加入到Query查询条件中
    @QueryField
    private String login;
    private String password;
    private String userName;
    private String address;
    private String job;
    private Date birthDate;
    private String city;
    private String district;
    private String province;
    private String streetAddress;
    private String state;
    private String type;
    private Date lastLoginDate;
    // 用户角色信息
    private List<UserRole> roles;
    // 角色信息集合
    private String roleArray;

    // 组织架构数据集合
    private OrgGroup orgGroup;

    public OrgGroup getOrgGroup() {
        return orgGroup;
    }

    public void setOrgGroup(OrgGroup orgGroup) {
        this.orgGroup = orgGroup;
    }

    public String getRoleArray() {
        return roleArray;
    }

    public void setRoleArray(String roleArray) {
        this.roleArray = roleArray;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        if(this.getRoles()!=null){
            List<UserRole> roles=this.getRoles();
            for(UserRole role:roles){
                if(role.getName()!=null){
                    auths.add(new SimpleGrantedAuthority(role.getName()));
                }
            }
        }
        return auths;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getId() {
        return id.toString();
    }

    public void setId(String id) {
        this.id = new ObjectId(id);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    /**
     * 功能描述：组装角色数据集合
     * @param roleArray
     * @param userRoleDao
     */
    public void packagingRoles(String roleArray,UserRoleDao userRoleDao){
        List<UserRole> roles = new ArrayList<UserRole>();
        if(roleArray!=null){
            UserRole userRole = null;
            for(String roleId:roleArray.split(",")){
                if(!roleId.isEmpty()){
                    userRole = new UserRole();
                    roles.add(userRoleDao.get(roleId));
                }
            }
        }
        this.setRoles(roles);
    }

}
