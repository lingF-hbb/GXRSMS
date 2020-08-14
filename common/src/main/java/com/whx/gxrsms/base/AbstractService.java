package com.whx.gxrsms.base;

import com.whx.gxrsms.bean.Page;
import com.github.pagehelper.PageHelper;
import com.whx.gxrsms.BaseService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


/**
 * @Company lihfinance.com
 * @Author ZhaoShuai
 * @Date Create in 2020/2/11
 **/
public abstract class AbstractService<T> implements BaseService<T> {

    /**
     * 设置数据源
     *
     * @return
     */
    protected abstract Mapper<T> getMapper();

    @Override
    public Page<T> queryForPageList(T t, Page<T> page) {
        com.github.pagehelper.Page<T> pageTmp = PageHelper.startPage(page.getPageNum(), page.getPageSize(), true);
        getMapper().select(t);
        return convertPage(pageTmp, page);
    }

    private Page<T> convertPage(com.github.pagehelper.Page<T> pageTmp,Page<T> page) {
        page.setPages(pageTmp.getPages());
        page.setRows(pageTmp.getResult());
        page.setTotal(pageTmp.getTotal());
        return page;
    }

    @Override
    public void updateNotNullable(T t) {
        int i = getMapper().updateByPrimaryKeySelective(t);
        Assert.isTrue(i > 0, "修改数据失败");
    }

    @Override
    public void insert(T t) {
        int i = getMapper().insertSelective(t);
        Assert.isTrue(i > 0, "新增数据失败");
    }

    @Override
    public void delete(Long id) throws Exception {
        int i = getMapper().deleteByPrimaryKey(id);
        if (i <= 0) {
            throw new Exception("删除失败");
        }
    }

    @Override
    public int delete(T t) {
        return getMapper().delete(t);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(T t, List<Long> ids) throws Exception {
        Example example = new Example(t.getClass());
        example.createCriteria().andIn("id", ids);
        int i = getMapper().deleteByExample(example);
        if (i <= 0) {
            throw new Exception("删除失败");
        }
    }

    @Override
    public List<T> queryForList(T t) {
        return getMapper().select(t);
    }

    @Override
    public T queryForOne(T t) {
        return getMapper().selectOne(t);
    }

    @Override
    public T queryById(Long id) {
        return getMapper().selectByPrimaryKey(id);
    }


}
