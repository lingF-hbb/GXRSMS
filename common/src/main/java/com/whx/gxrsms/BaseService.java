package com.whx.gxrsms;

import com.whx.gxrsms.bean.Page;

import java.util.List;

/**
 * @author 赵帅
 * @company lihfinance.com
 * @date Create in 2020/2/23
 **/
public interface BaseService<T> {

    /**
     * 分页查询
     *
     * @param t
     * @param page
     * @return com.github.pagehelper.Page<T>
     **/
    Page<T> queryForPageList(T t, Page<T> page);

    /**
     * 更新数据
     * @param t
     */
    void updateNotNullable(T t);

    /**
     * 新增数据
     * @param t
     */
    void insert(T t);

    /**
     * 删除
     * @param id
     */
    void delete(Long id) throws Exception;

    /**
     * 根据条件删除
     * @param t
     */
    int delete(T t);

    /**
     * 按id批量删除
     * @param t
     * @param ids
     * @throws Exception
     */
    void delete(T t, List<Long> ids) throws Exception;

    /**
     * 查询所有数据
     * @param t
     * @param <T>
     * @return
     */
    List<T> queryForList(T t);

    /**
     * 查询一个对象
     * @param t
     * @param <T>
     * @return
     */
    T queryForOne(T t);

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    T queryById(Long id);

}
