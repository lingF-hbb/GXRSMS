package com.whx.gxrsms.base;

import com.whx.gxrsms.bean.Page;
import com.whx.gxrsms.model.User;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author ZhaoShuai
 * @company lihfinance.com
 * @date Create in 2020/2/20
 **/
public class BaseController {

    private ThreadLocal<HttpServletRequest> request = new ThreadLocal<>();
    private ThreadLocal<HttpServletResponse> response = new ThreadLocal<>();
    private ThreadLocal<Model> model = new ThreadLocal<>();

    @ModelAttribute
    public void init(HttpServletRequest request, HttpServletResponse response,Model model) {
        this.request.set(request);
        this.response.set(response);
        this.model.set(model);
    }

    @InitBinder
    public void binder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(Date.class,new WebDateBinder());
    }

    protected void setAttr(String name, Object attr) {
        getModel().addAttribute(name, attr);
    }

    protected String getParameter(String name) {
        return getHttpServletRequest().getParameter(name);
    }

    protected String getParameter(String name, Object defaultValue) {
        String result = getHttpServletRequest().getParameter(name);
        if (Objects.isNull(result)|| StringUtils.isEmpty(result)) {
            result = String.valueOf(defaultValue);
        }
        return result;
    }

    protected <T> Page<T> getPage(Class<T> clazz) {
        Page<T> page = new Page<>();
        String pageNum = getParameter("pageNum", 1);
        String pageSize = getParameter("pageSize", 10);
        page.setPageNum(Integer.parseInt(pageNum));
        page.setPageSize(Integer.parseInt(pageSize));
        return page;
    }

    protected HttpServletRequest getHttpServletRequest() {
        return this.request.get();
    }

    protected HttpServletResponse getHttpServletResponse() {
        return this.response.get();
    }

    protected Model getModel() {
        return this.model.get();
    }

    protected User getUser() {
        return (User) getHttpServletRequest().getSession().getAttribute("user");
    }

}
