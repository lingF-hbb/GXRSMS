<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <c:set var="request" value="${pageContext.request.contextPath}"/>
    <title></title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${request}/static/plugins/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${request}/static/plugins/layuiadmin/style/admin.css" media="all">
</head>
<body>

<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-form layui-card-header layuiadmin-card-header-auto">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label"></label>
                    <div class="layui-input-inline">
                        <input type="text" name="stuNum" id="searchInfo" placeholder="Search for~" autocomplete="off" class="layui-input">
                    </div>
                    <button class="layui-btn layadmin-btn-list" id="query" data-type="query">查询</button>
                    <button class="layui-btn layuiadmin-btn-comm" data-type="batchdel" style="background-color: #FFB800"
                            id="query-all-info">查询所有信息
                    </button>
                </div>

            </div>
        </div>
        <div class="layui-card-body">
            <div style="padding-bottom: 10px;">
                <button class="layui-btn layuiadmin-btn-list" data-type="batchdel">删除</button>
                <button class="layui-btn layuiadmin-btn-list" data-type="add">添加</button>
            </div>
            <table id="depInfoQuery" lay-filter="LAY-app-content-comm"></table>
            <script type="text/html" id="table-content-list1">
                <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i
                        class="layui-icon layui-icon-edit"></i>编辑</a>
                <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i
                        class="layui-icon layui-icon-delete"></i>删除</a>
            </script>
        </div>
    </div>
</div>

<script src="${request}/static/plugins/layuiadmin/layui/layui.js" charset="UTF-8"></script>
<script>
    layui.config({
        base: '${request}/static/plugins/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'contlist', 'table', 'laypage'], function () {
        var $ = layui.$
            , admin = layui.admin
            , form = layui.form
            , table = layui.table
            , laypage = layui.laypage;

        //方法级渲染
        table.render({
            elem: '#depInfoQuery'
            , url: '${request}/department/queryAllDep.html' //向后端默认传page和limit
            , cols: [[
                {type: 'checkbox', fixed: 'left'}
                , {field: 'id', title: 'ID', sort: true}
                , {field: 'deptNo', title: '部门编号', sort: true}
                , {field: 'deptName', title: '部门名称', sort: true}
                , {title: '操作', minWidth: 150, align: 'center', fixed: 'right', toolbar: '#table-content-list1'}
            ]]
            , page: true
            , limit: 10
            , limits: [5, 10, 15, 20]
            , id: 'depInfoTable'
            , request: {
                pageName: 'pageNum',
                limitName: 'pageSize'  //如不配置，默认为page=1&limit=10
            }
            , done: function (res, curr, count) {
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                // console.log(res);
                //
                // 得到当前页码
                // console.log(curr);
                //
                // 得到数据总量
                // console.log(count);
            }

        });


        $("#query-all-info").click(function () {
            table.reload('depInfoTable', {
                url: '${request}/department/queryAllDep.html'
                , request: {
                    pageName: 'pageNum',
                    limitName: 'pageSize'  //如不配置，默认为page=1&limit=10
                }
                , where: { //设定异步数据接口的额外参数，任意设
                }
                , page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        });

        //监听搜索
        $("#query").click(function () {
            var depInfo = $("#searchInfo").val();
            if(depInfo==""||depInfo==null){
                layer.msg('请输入查询内容(~)', {
                    offset: '15px'
                    , icon: 2
                    , time: 1000
                });
            }else {
                //执行重载
                table.reload('depInfoTable', {
                    url: '${request}/department/queryDepInfo.html' //向后端默认传page和limit
                    , where: { //设定异步数据接口的额外参数，任意设
                        Msg: depInfo
                    }
                    , request: {
                        pageName: 'pageNum',
                        limitName: 'pageSize'  //如不配置，默认为page=1&limit=10
                    }
                    , page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
            }
        });


        var $ = layui.$, active = {
            batchdel: function () {
                var checkStatus = table.checkStatus('depInfoTable')
                    , checkData = checkStatus.data; //得到选中的数据
                if (checkData.length === 0) {
                    return layer.msg('请选择数据');
                }

                // console.log(JSON.stringify(checkData));
                // console.log(checkStatus);
                // console.log(checkData);
                layer.confirm('确定要删除' + checkData.length + '条数据吗？', function (index) {
                    //执行 Ajax 后重载
                    $.ajax({
                        type: 'post',
                        data: {deps: JSON.stringify(checkData)},
                        url: "${request}/department/deleteManyEmps.html",
                        success: function (data) {
                            if (data.data == "deleteSuccess") {
                                layer.msg('已删除');
                                table.reload('depInfoTable', {
                                    url: '${request}/department/queryAllDep.html' //向后端默认传page和limit); //重载表格
                                    , request: {
                                        pageName: 'pageNum',
                                        limitName: 'pageSize'  //如不配置，默认为page=1&limit=10
                                    }
                                    , page: {
                                        curr: 1 //重新从第 1 页开始
                                    }
                                });
                            } else {
                                layer.msg('未知错误');
                            }
                        }

                    });

                });
            },
            add: function () {
                layer.open({
                    type: 2
                    , title: '添加部门'
                    , content: '${request}/department/toEditDep.html'
                    , maxmin: true
                    , area: ['600px', '500px']
                    , btn: ['确定', '取消']
                    , yes: function (index, layero) {
                        //点击确认触发 iframe 内容中的按钮提交
                        var iframeWindow = window['layui-layer-iframe' + index]
                            , submit = layero.find('iframe').contents().find("#layuiadmin-app-form-submit");

                        iframeWindow.layui.form.on('submit(layuiadmin-app-form-submit)', function (data) {
                            var field = data.field; //获取提交的字段
                            // var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                            var json = {
                                deptNo: field.deptNo,
                                deptName: field.deptName
                            };
                            console.log(json);
                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            $.ajax({
                                data: json,
                                type: 'post',
                                url: "${request}/department/insertDep.html",
                                success: function (res) {
                                    if (res.data == "insertSuccess") {
                                        layer.msg('添加成功', {
                                            icon: 1
                                            , time: 1000
                                        });

                                        layer.close(index); //再执行关闭

                                    } else {
                                        return layer.msg('未知错误');
                                    }
                                }
                            });

                        });
                        submit.trigger('click');
                        table.reload('depInfoTable', {
                            url: '${request}/department/queryAllDep.html'
                            , request: {
                                pageName: 'pageNum',
                                limitName: 'pageSize'  //如不配置，默认为page=1&limit=10
                            }
                            , where: { //设定异步数据接口的额外参数，任意设
                            }
                            , page: {
                                curr: 1 //重新从第 1 页开始
                            }
                        });

                    }
                });
            }
        };

        $('.layui-btn.layuiadmin-btn-list').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });


        //监听工具条
        table.on('tool(LAY-app-content-comm)', function (obj) {
            var data = obj.data;

            if (obj.event === 'del') {
                layer.confirm('确定删除此部门吗？', function (index) {
                    //提交删除ajax
                    $.ajax({
                        data: {id:data.id},
                        type: 'post',
                        url: "${request}/department/deleteOneDep.html",
                        success: function (data) {
                            if (data.data == "deleteSuccess") {
                                layer.msg('删除成功', {
                                    icon: 1
                                    , time: 1000
                                });

                                obj.del();

                                layer.close(index); //关闭弹层
                            } else {
                                return layer.msg('未知错误');
                            }
                        }
                    });
                });
            } else if (obj.event === 'edit') {
                layer.open({
                    type: 2
                    ,
                    title: '编辑部门'
                    ,
                    content: '${request}/department/toEditDep.html?depname=' + data.depname + '&depleader=' + data.depleader + '&depid=' + data.depid
                    ,
                    maxmin: true
                    ,
                    area: ['820px', '730px']
                    ,
                    btn: ['确定', '取消']
                    ,
                    yes: function (index, layero) {
                        var iframeWindow = window['layui-layer-iframe' + index]
                            , submit = layero.find('iframe').contents().find("#layuiadmin-app-form-edit");

                        //监听提交
                        iframeWindow.layui.form.on('submit(layuiadmin-app-form-edit)', function (data) {
                            var field = data.field; //获取提交的字段
                            console.log(field);
                            var json = {
                                id : field.id
                                , deptName: field.deptName
                                , deptNo: field.deptNo
                            };

                            $.ajax({
                                data: json,
                                type: 'post',
                                url: "${request}/department/updateDep.html",
                                success: function (data) {
                                    if (data.data == "updateSuccess") {
                                        layer.msg('修改成功', {
                                            icon: 1
                                            , time: 1000
                                        });

                                        obj.update(json); //数据更新


                                        form.render();

                                        layer.close(index); //关闭弹层
                                    } else {
                                        return layer.msg('未知错误');
                                    }
                                }
                            });

                        });

                        submit.trigger('click');
                    }
                    ,
                    success: function (layero, index) {
                        console.log(data);
                        //给iframe元素赋值
                        var othis = layero.find('iframe').contents().find("#layuiadmin-app-form-list").click();
                        othis.find('input[name="id"]').val(data.id);
                        othis.find('input[name="deptName"]').val(data.deptName);
                        othis.find('input[name="deptNo"]').val(data.deptNo);

                    }
                });
            }
        });

    });
</script>
</body>
</html>

