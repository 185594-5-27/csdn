package com.mongo.common.base.dao;


import com.mongo.common.base.entity.Pagination;
import com.mongo.common.base.entity.QueryBase;
import com.mongo.common.base.entity.QueryField;
import com.mongo.common.util.common.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/*
* 类描述：
* @auther linzf
* @create 2018/3/30 0030 
*/
public abstract class MongodbBaseDao<T,Q extends QueryBase>{

    @Autowired
    @Qualifier("mongoTemplate")
    protected MongoTemplate mongoTemplate;

    //保存一个对象到mongodb
    public T save(T bean) {
        mongoTemplate.save(bean);
        return bean;
    }

    // 根据id删除对象
    public void deleteById(T t) {
        mongoTemplate.remove(t);
    }

    // 根据对象的属性删除
    public void deleteByCondition(T t) {
        Query query = buildBaseQuery(t);
        mongoTemplate.remove(query, getEntityClass());
    }

    // 通过条件查询更新数据
    public void update(Query query, Update update) {
        mongoTemplate.updateMulti(query, update, this.getEntityClass());
    }

    // 根据id进行更新
    public void updateById(String id, T t) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update update = buildBaseUpdate(t);
        update(query, update);
    }

    // 通过条件查询实体(集合)
    public List<T> find(Query query) {
        return mongoTemplate.find(query, this.getEntityClass());
    }

    public List<T> findByCondition(T t) {
        Query query = buildBaseQuery(t);
        return mongoTemplate.find(query, getEntityClass());
    }

    // 通过一定的条件查询一个实体
    public T findOne(Query query) {
        return mongoTemplate.findOne(query, this.getEntityClass());
    }


    // 通过ID获取记录
    public T get(String id) {
        return mongoTemplate.findById(id, this.getEntityClass());
    }

    // 通过ID获取记录,并且指定了集合名(表的意思)
    public T get(String id, String collectionName) {
        return mongoTemplate.findById(id, this.getEntityClass(), collectionName);
    }

    /**
     * 通过条件查询,查询分页结果
     */
    public Pagination<T> findByPage(Q q) {
        Query query = buildBaseQuery(q);
        //获取总条数
        long totalCount = this.mongoTemplate.count(query, this.getEntityClass());
        //总页数
        int totalPage = (int) (totalCount/q.getLimit());
        int skip = (q.getPage()-1)*q.getLimit();
        Pagination<T> page = new Pagination(q.getPage(), totalPage, (int)totalCount);
        query.skip(skip);// skip相当于从那条记录开始
        query.limit(q.getLimit());// 从skip开始,取多少条记录
        List<T> data = this.find(query);
        page.build(data);//获取数据
        return page;
    }

    /**
     * 根据注解构建Query查询条件
     * @param q
     * @return
     */
    public Query buildBaseQuery(Q q){
        Query query = new Query();

        Field[] fields = q.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(q);
                if (value != null&&!value.equals("")) {
                    QueryField queryField = field.getAnnotation(QueryField.class);
                    if (queryField != null) {
                        if(value instanceof String){
                            query.addCriteria(queryField.type().buildCriteria(queryField, field, value));
                        }else{
                            query.addCriteria(queryField.type().buildCriteria(queryField, field, value));
                        }
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return query;
    }

    // 根据vo构建查询条件Query
    private Query buildBaseQuery(T t) {
        Query query = new Query();

        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(t);
                if (value != null) {
                    QueryField queryField = field.getAnnotation(QueryField.class);
                    if (queryField != null) {
                        query.addCriteria(queryField.type().buildCriteria(queryField, field, value));
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return query;
    }

    private Update buildBaseUpdate(T t) {
        Update update = new Update();

        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(t);
                if (value != null) {
                    update.set(field.getName(), value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return update;
    }

    // 获取需要操作的实体类class
    @SuppressWarnings("unchecked")
    protected Class<T> getEntityClass() {
        return ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    protected  String getCollection(){
        String [] collections = getEntityClass().toString().split("\\.");
        return CommonUtil.toFirstCharLowerCase(collections[collections.length-1]);
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }
}