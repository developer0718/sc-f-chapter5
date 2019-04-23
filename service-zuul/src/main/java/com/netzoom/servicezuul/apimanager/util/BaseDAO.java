package com.netzoom.servicezuul.apimanager.util;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by TanzJ on 2018/12/12.
 * 通用DAO类
 * @author tanzj
 */
@Component
public class BaseDAO extends SqlSessionDaoSupport {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    private SqlSessionFactory sqlSessionFactory;

    public BaseDAO() {
    }

    /**
     * 根据Key删除记录
     *
     * @param key  删除的key
     * @return int　result
     * @throws Exception
     */
    public int delete(Object key) throws Exception {
        if (key == null) {
            return -1;
        } else if (!(key instanceof String)) {
            this.log.info("【MyBatisDao】错误信息 : 参数不匹配");
            return -1;
        } else {
            boolean var2 = false;

            try {
                int result = this.getSqlSession().delete((String)key);
                return result;
            } catch (DataAccessException e) {
                throw new Exception(e.getMessage(), e);
            }
        }
    }

    /**
     * 根据key和对象删除记录
     *
     * @param key  删除的key
     * @param obj  删除的对象
     * @return int result
     * @throws Exception
     */
    public int delete(Object key, Object obj) throws Exception {
        if (obj == null) {
            return this.delete(key);
        } else if (!(key instanceof String)) {
            this.log.info("【MyBatisDao】错误信息 : 参数不匹配");
            return -1;
        } else {
            boolean var3 = false;

            try {
                int result = this.getSqlSession().delete((String)key, obj);
                return result;
            } catch (DataAccessException var5) {
                throw new Exception(var5.getMessage(), var5);
            }
        }
    }

    /**
     * 插入方法
     *
     * @param key String key
     * @return int result
     * @throws Exception
     */
    public int insert(Object key) throws Exception {
        if (key == null) {
            return -1;
        } else if (!(key instanceof String)) {
            this.log.info("【MyBatisDao】错误信息 : 参数不匹配");
            return -1;
        } else {
            boolean var2 = false;

            try {
                int result = this.getSqlSession().insert((String)key);
                return result;
            } catch (DataAccessException var4) {
                throw new Exception(var4.getMessage(), var4);
            }
        }
    }

    /**
     * 根据key和对象插入记录
     *
     * @param key String key
     * @param obj 存储的对象
     * @return int result
     * @throws Exception
     */
    public int insert(Object key, Object obj) throws Exception {
        if (obj == null) {
            return this.insert(key);
        } else if (!(key instanceof String)) {
            this.log.info("【MyBatisDao】错误信息 : 参数不匹配");
            return -1;
        } else {
            boolean var3 = false;

            try {
                int result = this.getSqlSession().insert((String)key, obj);
                return result;
            } catch (DataAccessException e) {
                throw new Exception(e.getMessage(), e);
            }
        }
    }

    /**
     * 根据key查询记录，以List的形式返回
     *
     * @param key 查询的key
     * @param <T> 泛型对象
     * @return List
     * @throws Exception
     */
    public <T> List<T> queryForList(Object key) throws Exception {
        if (key == null) {
            return null;
        } else if (!(key instanceof String)) {
            this.log.info("【MyBatisDao】错误信息 : 参数不匹配");
            return null;
        } else {
            List result = null;

            try {
                result = this.getSqlSession().selectList((String)key);
                return result;
            } catch (DataAccessException e) {
                throw new Exception(e.getMessage(), e);
            }
        }
    }

    /**
     * 以key和对象的形式查询记录并以List的形式返回
     *
     * @param key 查询的key
     * @param obj
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> List<T> queryForList(Object key, Object obj) throws Exception {
        if (obj == null) {
            return this.queryForList(key);
        } else if (!(key instanceof String)) {
            this.log.info("【MyBatisDao】错误信息 : 参数不匹配");
            return null;
        } else {
            List result = null;

            try {
                result = this.getSqlSession().selectList((String)key, obj);
                return result;
            } catch (DataAccessException var5) {
                throw new Exception(var5.getMessage(), var5);
            }
        }
    }

    /**
     * 查询一条记录
     *
     * @param key 查询的key
     * @return Object
     * @throws Exception
     */
    private Object selectOne(Object key) throws Exception {
        if (!(key instanceof String)) {
            this.log.info("【MyBatisDao】错误信息 : 参数不匹配");
            return null;
        } else {
            Object result = null;

            try {
                result = this.getSqlSession().selectOne((String)key);
                return result;
            } catch (DataAccessException var4) {
                throw new Exception(var4.getMessage(), var4);
            }
        }
    }

    /**
     * 根据key和对象查询一条记录
     *
     * @param key 查询的key
     * @param obj
     * @return Object
     * @throws Exception
     */
    private Object selectOne(Object key, Object obj) throws Exception {
        if (!(key instanceof String)) {
            this.log.info("【MyBatisDao】错误信息 : 参数不匹配");
            return null;
        } else {
            Object result = null;

            try {
                result = this.getSqlSession().selectOne((String)key, obj);
                return result;
            } catch (DataAccessException var5) {
                throw new Exception(var5.getMessage(), var5);
            }
        }
    }

    /**
     * 通过key和object查询记录并以对象的形式返回
     *
     * @param key 查询的key
     * @param obj  查询的object
     * @param  <T>  泛型
     * @return <T>  泛型
     * @throws Exception
     */
    public <T> T queryForObject(Object key, Object obj) throws Exception {
        Object result = this.selectOne(key, obj);
        return result == null ? null : (T) result;
    }

    /**
     * 以key的形式查询一条记录，并以泛型对象的形式返回
     *
     * @param key 查询的key
     * @param <T> 泛型
     * @return <T> 泛型
     * @throws Exception
     */
    public <T> T queryForObject(Object key) throws Exception {
        Object result = this.selectOne(key);
        return result == null ? null : (T) result;
    }

    /**
     * 根据key的形式更新一条记录
     *
     * @param key 更新的key
     * @return Integer -1错误 0成功
     * @throws Exception
     */
    public Integer update(Object key) throws Exception {
        if (!(key instanceof String)) {
            this.log.info("【MyBatisDao】错误信息 : 参数不匹配");
            return -1;
        } else {
            Integer result = 0;

            try {
                result = this.getSqlSession().update((String)key);
                return result;
            } catch (DataAccessException var4) {
                throw new Exception(var4.getMessage(), var4);
            }
        }
    }

    /**
     * 以key和对象的形式更新记录
     *
     * @param key  更新的key
     * @param obj 更新的对象
     * @return Integer -1错误 0成功
     * @throws Exception
     */
    public Integer update(Object key, Object obj) throws Exception {
        if (!(key instanceof String)) {
            this.log.info("【MyBatisDao】错误信息 : 参数不匹配");
            return null;
        } else {
            Integer result = 0;

            try {
                result = this.getSqlSession().update((String)key, obj);
                return result;
            } catch (DataAccessException e) {
                throw new Exception(e.getMessage(), e);
            }
        }
    }

    /**
     * 查询记录并以map的形式返回
     *
     * @param key 查询的key
     * @param parameterObject
     * @param keyProperty
     * @return Map
     * @throws Exception
     */
    public Map queryForMap(Object key, Object parameterObject, Object keyProperty) throws Exception {
        return this.getSqlSession().selectMap((String)key, parameterObject, (String)keyProperty);
    }

//    public SqlSessionFactory getSqlSessionFactory() {
//        return this.sqlSessionFactory;
//    }

    @Resource
	@Override
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        super.setSqlSessionFactory(sqlSessionFactory);
    }
}
