package com.mongo.common.util.user;




import com.mongo.common.util.node.NodeUtil;
import com.mongo.sys.entity.Tree;
import com.mongo.sys.entity.User;
import com.mongo.sys.entity.UserRole;
import com.mongo.sys.service.TreeService;
import com.mongo.sys.service.UserRoleService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @auher linzf
 * @since 2018-03-31
 */
public class UserInfo {

    /**
     * 功能描述：加载菜单节点的数据
     * @return
     */
    public static List<Tree> loadUserTree(UserRoleService userRoleService, TreeService treeService){
        Map<String,Tree> treeMap = new HashMap<String,Tree>();
        User user = getUser();
        List<UserRole> userRoleList = userRoleService.getUserRoleByRoleId(user);
        for(UserRole userRole:userRoleList){
            for(Tree tree:userRole.getTreeList()){
                treeMap.put(tree.getId(),treeService.get(tree.getId()));
            }
        }
        List<Tree> treeList = NodeUtil.getChildNodes(new ArrayList<Tree>(treeMap.values()),"5ac0c4a0c053f417ac310e3f");
        return treeList;
    }

    /**
     * 功能描述：实现对密码进行bcrypt加密
     * @param password
     * @return
     */
    public static String encode(String password){
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 功能描述：获取当前登陆的用户的信息
     * 注释：强转一个null对象不会产生报错
     * @return
     */
    public static User getUser(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        return (User)Optional.ofNullable(securityContextImpl.getAuthentication().getPrincipal()).orElse(null);
    }

    /**
     * 功能描述：获取当前登陆的用户的权限集合
     * @return
     */
    public static List<GrantedAuthority> getGrantedAuthority(){
        return (List<GrantedAuthority>)Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getAuthorities()).orElse(new ArrayList<>());
    }

    /**
     * 功能描述：判断当前的用户是否包含某一个权限
     * @param authority
     * 注释：
     *      allMatch：Stream 中全部元素符合传入的 predicate，返回 true
     *      anyMatch：Stream 中只要有一个元素符合传入的 predicate，返回 true
     *      noneMatch：Stream 中没有一个元素符合传入的 predicate，返回 true
     * @return
     */
    public static boolean hasAuthority(String authority){
        return getGrantedAuthority().stream().anyMatch(obj->obj.getAuthority().equalsIgnoreCase(authority));
    }

}
