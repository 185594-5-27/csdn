package com.mongo.common.base.controller;


import com.mongo.common.base.constant.SystemStaticConst;
import com.mongo.common.base.entity.QueryBase;
import com.mongo.common.base.service.MongodbBaseService;
import com.mongo.common.util.common.CommonUtil;
import com.mongo.common.util.json.JsonHelper;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class MongodbBaseController<T,Q extends QueryBase> {

    protected abstract MongodbBaseService<T,Q> getService();

    /**添加页面路径*/
    public final static String ADDPAGE = "/add";
    /**修改页面路径*/
    public final static String UPDATEPAGE = "/update";

    /**
     * Controller基路径
     * */
    protected String basePath;

    /**抽象方法，获取页面基路径
     * @throws Exception */
    protected String getPageBaseRoot() throws Exception{
        if(basePath==null){
            basePath = this.getClass().getName();
            Pattern p=Pattern.compile(".[a-z|A-z]+.controller.[a-z|A-z]+Controller");
            Matcher m=p.matcher(basePath);
            if(m.find()){
                basePath = m.group();
                basePath = basePath.substring(1, basePath.length()-10);
                basePath = basePath.replace(".", "/");
                basePath = basePath.replace("/controller/", "/");
                basePath = CommonUtil.toFirstCharLowerCase(basePath);
                basePath = basePath.substring(0,basePath.lastIndexOf("/")+1)+CommonUtil.toFirstCharLowerCase(basePath.substring(basePath.lastIndexOf("/")+1));
            }
            else{
                throw new Exception("获取页面基路径失败");
            }
        }
        return basePath;
    }

    /**
     * 功能描述：直接跳转到更新数据的页面
     * @param id
     * @return
     */
    @RequestMapping(value = "/updatePage",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public String updatePage(String id,Model model) throws Exception{
        T entity = getService().get(id);
        model.addAttribute("entity",entity);
        return getPageBaseRoot()+UPDATEPAGE;
    }

    /** 跳转到添加对象页面
     * @throws Exception */
    @RequestMapping(value="/addPage")
    public String addPage() throws Exception{
        return getPageBaseRoot()+ADDPAGE;
    }

    /**
     * 功能描述：保存数据字典数据
     * @param entity
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> save(T entity) throws Exception{
        entity = getService().save(entity);
        Map<String,Object> result = new HashMap<String, Object>();
        if(entity!=null){
            result.put(SystemStaticConst.RESULT,SystemStaticConst.SUCCESS);
            result.put(SystemStaticConst.MSG,"增加数据成功！");
            result.put("entity",entity);
        }else{
            result.put(SystemStaticConst.RESULT,SystemStaticConst.FAIL);
            result.put(SystemStaticConst.MSG,"增加数据失败！");
        }
        return result;
    }


    /**
     * 功能描述：更新数据字典数据
     * @param entity
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> update(String id,T entity)  throws Exception{
        Map<String,Object> result = new HashMap<String, Object>();
        try{
            getService().updateById(id,entity);
            result.put(SystemStaticConst.RESULT,SystemStaticConst.SUCCESS);
            result.put(SystemStaticConst.MSG,"更新数据成功！");
            result.put("entity",entity);
        }catch(Exception e){
            result.put(SystemStaticConst.RESULT,SystemStaticConst.FAIL);
            result.put(SystemStaticConst.MSG,"更新数据失败！");
        }
        return result;
    }

    /**
     * 功能描述：实现根据ID来删除数据
     * @param entity
     * @return
     */
    @RequestMapping(value = "/remove",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> remove(T entity) throws Exception{
        Map<String,Object> result = new HashMap<String, Object>();
        getService().deleteById(entity);
        result.put(SystemStaticConst.RESULT,SystemStaticConst.SUCCESS);
        result.put(SystemStaticConst.MSG,"删除数据成功！");
        return result;
    }

    /**
     * 功能描述：实现批量删除数据字典的记录
     * @param json
     * @return
     */
    @RequestMapping(value = "/removeBath",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> removeBath(String json) throws Exception{
        Map<String,Object> result = new HashMap<String, Object>();
        getService().removeBath((List<T>) JsonHelper.toList(json,(Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]));
        result.put(SystemStaticConst.RESULT,SystemStaticConst.SUCCESS);
        result.put(SystemStaticConst.MSG,"删除数据成功！");
        return result;
    }

    /**
     * 功能描述：判断当前的字典元素是否已经存在
     * @param entity
     * @return
     */
    @RequestMapping(value = "/isExist",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> isExist(T entity){
        Map<String,Object> result = new HashMap<String, Object>();
        if(getService().findByCondition(entity).size()>0){
            result.put("valid",false);
        }else{
            result.put("valid",true);
        }
        return result;
    }



}
