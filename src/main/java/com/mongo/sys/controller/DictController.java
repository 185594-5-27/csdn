package com.mongo.sys.controller;


import com.mongo.common.base.constant.SystemStaticConst;
import com.mongo.common.base.controller.MongodbBaseController;
import com.mongo.common.base.entity.Pagination;
import com.mongo.common.base.service.MongodbBaseService;
import com.mongo.common.util.dict.DictCache;
import com.mongo.sys.entity.Dict;
import com.mongo.sys.entity.QueryDict;
import com.mongo.sys.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
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
@RequestMapping("/dict")
public class DictController extends MongodbBaseController<Dict,QueryDict> {

    @Autowired
    private DictService dictService;

    @Override
    protected MongodbBaseService<Dict, QueryDict> getService() {
        return dictService;
    }

    /**
     * 功能描述：获取数据字典的分页的数据
     * @param entity
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> list(QueryDict entity){
        Map<String,Object> result = new HashMap<String, Object>();
        Pagination<Dict> page = dictService.findByPage(entity);
        result.put("totalCount",page.getTotalNumber());
        result.put("result",page.getItems());
        return result;
    }

    /**
     * 功能描述：将字典数据初始化到前端js中
     * @return
     */
    @RequestMapping(value = "/loadDict",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> loadDict(){
        Map<String,Object> result = new HashMap<String, Object>();
        List<Dict> dictList = dictService.find(new Query(Criteria.where("isLoad").is("1")));
        result.put(SystemStaticConst.RESULT,SystemStaticConst.SUCCESS);
        result.put("data",dictList);
        return result;
    }

    /**
     * 功能描述：重新加载数据字典的数据到内存中
     * @return
     */
    @RequestMapping(value = "/reload",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> reload(){
        Map<String,Object> result = new HashMap<String, Object>();
        List<Dict> dictList = dictService.find(new Query());
        DictCache.reload(dictList);
        result.put(SystemStaticConst.RESULT,SystemStaticConst.SUCCESS);
        result.put(SystemStaticConst.MSG,"重新加载数据字典成功！");
        return result;
    }

}
