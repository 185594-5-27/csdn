package com.mongo.common.base.service;


import com.mongo.common.base.dao.MongodbBaseDao;
import com.mongo.common.base.entity.Pagination;
import com.mongo.common.base.entity.QueryBase;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public abstract class MongodbBaseService<T,Q extends QueryBase> {

    protected abstract MongodbBaseDao<T,Q> getDao();

    //保存一个对象到mongodb
    public T save(T bean) {
        getDao().save(bean);
        return bean;
    }

    // 根据id删除对象
    public void deleteById(T t) {
        getDao().deleteById(t);
    }

    /**
     * 功能描述：批量删除数据
     * @param entityList
     * @return
     */
    public boolean removeBath(List<T> entityList) throws Exception{
        for(T t:entityList){
            deleteById(t);
        }
        return true;
    }

    // 根据对象的属性删除
    public void deleteByCondition(T t) {
        getDao().deleteByCondition(t);
    }

    // 通过条件查询更新数据
    public void update(Query query, Update update){
        getDao().update(query,update);
    }

    // 根据id进行更新
    public void updateById(String id, T t){
        getDao().updateById(id,t);
    }

    // 通过条件查询实体(集合)
    public List<T> find(Query query) {
        return getDao().find(query);
    }

    public List<T> findByCondition(T t){
        return getDao().findByCondition(t);
    }

    public T findOne(Query query){
        return getDao().findOne(query);
    }

    // 通过ID获取记录
    public T get(String id){
        return getDao().get(id);
    }

    // 通过ID获取记录,并且指定了集合名(表的意思)
    public T get(String id, String collectionName){
        return getDao().get(id,collectionName);
    }


    /**
     * 通过条件查询,查询分页结果
     */
    public Pagination<T> findByPage(Q q){
        return getDao().findByPage(q);
    }

}
