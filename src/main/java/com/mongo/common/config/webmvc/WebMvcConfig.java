package com.mongo.common.config.webmvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 类描述：springMVC的配置
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 功能描述：配置放行的静态文件的目录
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("classpath:/static/fonts/");
        registry.addResourceHandler("/font-awesome/**").addResourceLocations("classpath:/static/font-awesome/");
    }

    /**
     * 重写方法描述：实现在url中输入相应的地址的时候直接跳转到某个地址
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/error").setViewName("error");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/upload").setViewName("/sys/upload/upload");
        registry.addViewController("/dictList").setViewName("/sys/dict/dictList");
        registry.addViewController("/userRoleList").setViewName("/sys/role/roleList");
        registry.addViewController("/groupList").setViewName("/sys/orggroup/groupList");
        registry.addViewController("/userList").setViewName("/sys/user/userList");
        registry.addViewController("/treeList").setViewName("/sys/tree/treeList");
        // 跳转到点餐桌子主页
        registry.addViewController("/diningTable").setViewName("/back/orderHome/diningTable");
        // 菜品类型列表页
        registry.addViewController("/foodTypeList").setViewName("/back/foodType/foodTypeList");
        // 菜单列表页
        registry.addViewController("/dinerList").setViewName("/back/diner/dinerList");
        // 订单列表页
        registry.addViewController("/orderList").setViewName("/back/order/orderList");
        // 桌子列表页
        registry.addViewController("/diningTableList").setViewName("/back/diningTable/diningTableList");
        // 移动用户列表页
        registry.addViewController("/mobileUser").setViewName("/back/mobileUser/mobileUserList");
        // 积分明细列表页
        registry.addViewController("/scoreDetailList").setViewName("/back/scoreDetail/scoreDetailList");
    }

}
