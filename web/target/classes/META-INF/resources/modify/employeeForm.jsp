<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <c:set var="request" value="${pageContext.request.contextPath}"/>
    <title>layuiAdmin 文章管理 iframe 框</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${request}/static/plugins/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${request}/static/plugins/layuiadmin/style/admin.css" media="all">

</head>
<body>

<div class="layui-form" lay-filter="layuiadmin-app-form-list" id="layuiadmin-app-form-list"
     style="padding: 20px 30px 0 0;">
    <div class="layui-form-item">
        <div class="layui-input-inline">
            <input type="hidden" name="id" id="empid" value="" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">员工编号</label>
        <div class="layui-input-inline">
            <input type="text" name="empNo" id="empNo" autocomplete="off" value="" class="layui-input" lay-verify="no">
        </div>
        <div class="layui-form-mid " style="color:red">*必填项</div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">姓名</label>
        <div class="layui-input-inline">
            <input type="text" name="name" id="empName" autocomplete="off" value="" class="layui-input" lay-verify="name">
        </div>
        <div class="layui-form-mid " style="color:red">*必填项</div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">性别</label>
        <div class="layui-col-md12 layui-input-inline">
            <input type="radio" name="gender" value="男" id="isMale" title="男" checked >
            <input type="radio" name="gender" value="女" id="isFemale" title="女"  >
        </div>
        <div class="layui-form-mid " style="color:red">*必填项</div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">入职日期</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" id="createTime" name="createTime" lay-verify="createTime">
        </div>
        <div class="layui-form-mid " style="color:red">*必填项</div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">邮箱</label>
        <div class="layui-input-inline">
            <input type="text" name="email" id="email" value="" autocomplete="off" class="layui-input">
        </div>
        <div class="layui-form-mid " style="color:red">*必填项</div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">联系方式</label>
        <div class="layui-input-inline">
            <input type="text" name="phone" id="phone" autocomplete="off" value="" class="layui-input">
        </div>
        <div class="layui-form-mid " style="color:red">*必填项</div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">家庭住址</label>
        <div class="layui-input-inline">
            <input type="text" name="address" id="address" autocomplete="off" value="" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">部门</label>
        <div class="layui-input-inline">
            <select name="deptNo" id="department" lay-verify="required">
                <option value="" >请选择部门</option>
            </select>
        </div>
        <div class="layui-form-mid " style="color:red">*必填项</div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">在职状态</label>
        <div class="layui-input-inline">
            <select name="workStatus" id="workStatus" lay-verify="required">
                <option value="在职" >在职</option>
                <option value="离职" >离职</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="layuiadmin-app-form-submit" id="layuiadmin-app-form-submit"
               value="确认添加">
        <input type="button" lay-submit lay-filter="layuiadmin-app-form-edit" id="layuiadmin-app-form-edit"
               value="确认编辑">
    </div>
</div>

<script src="${request}/static/plugins/layuiadmin/layui/layui.js"></script>
<script src="${request}/static/custom/js/myLayVerify.js"></script>
<script src="${request}/static/custom/js/myValidity.js"></script>
<script>
    layui.config({
        base: '${request}/static/plugins/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'form','laydate'], function () {
        var $ = layui.$
            , form = layui.form
            , laydate = layui.laydate;

        laydate.render({
            elem: '#createTime' //指定元素
            ,value: '2000-1-1'
            ,isInitValue: true //是否允许填充初始值，默认为 true'
            ,min: '1920-1-1'
            ,max: '2030-12-31'
        });


         $.ajax({
             type:"get",
             url:"${request}/department/getDepName.html",
             success:function (result) {
                 for(var i = 0; i<result.data.length; i++){
                     var json = result.data[i];
                     var str ="";
                     str += '<option value="' + json.deptNo + '">' + json.deptName + '</option>';
                     $("#department").append(str);
                 }
                 form.render('select');
             }
         });
        $("#department").find("option[value = '"+'${empInfo.deptNo}'+"']").attr("selected","selected");
        $("#createTime").val('${empInfo.createTime}');
        $("#empName").blur(function () {
            var empname = $("#empName").val();
            console.log(empname);
            $.ajax({
                type:"get",
                url:"${request}/employee/checkEmpName.html",
                data:{name:empname},
                success:function (res) {
                    if(res.data == "empnameExist"){
                       layer.msg("该员工已存在!");
                    }
                }
            })
        });

         $("#email").blur(function () {
             var email = $("#email").val();
             if(!mailValidity(email)){
                 layer.msg("请输入正确邮箱格式!");
             }
         });

         $("#phone").blur(function () {
             var phone = $("#phone").val();
             if(!phoneValidity(phone)){
                 layer.msg("请输入正确的手机格式");
             }
         })
    })
</script>
</body>
</html>
