<%@ page import="com.zehua.admin.AdminInfo" %><%--
  Created by IntelliJ IDEA.
  User: 29306
  Date: 2018/6/6
  Time: 19:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Title</title>

    <link rel="stylesheet" href="layui/css/layui.css">

    <!--import d3 version 5-->
    <script type="text/javascript" src="js/d3.min.js"></script>
    <!--import jquery3.3.1-->
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>

    <script type="text/javascript" src="layui/layui.js"></script>
</head>
<body>

<%
    String username = AdminInfo.getUsername();
    String id = AdminInfo.getId();
    String gradName = AdminInfo.getGradeName();
    String facultyName = AdminInfo.getFacultyName();
    String majorName = AdminInfo.getMajorName();

    //得到课程集合
    String[] classTypeArray = AdminInfo.getClassTypeArray();

%>

<table width="100%" height="100%">
    <tr align="center">
        <td width="25%"></td>
        <td width="50%" style="background-color: burlywood;vertical-align: middle">
            <!--显示-->
            <h1>你的基本信息页面</h1>
            <hr>
            <br>
            <br>
            <br>
            姓名：<%=username %><br>
            编号：<%=id %><br>
            年级：<%=gradName %><br>
            学院：<%=facultyName %><br>
            专业：<%=majorName %><br>
            <br>
            <br>
            <br>
            <hr>
            <br>
            <br>
            <table>
                <tbody>
                <%
                    for(int i = 0;i<classTypeArray.length;i++){
                %>
                <tr>
                    <td width="50%"><%=classTypeArray[i] %></td>
                    <!--button的type属性很重要-->
                    <td width="50%">
                        <form class="layui-form">
                            <div class="layui-form-item">
                                <div class="layui-inline">
                                    <div class="layui-input-inline">
                                        <select>
                                            <%
                                                String[] classType_subArray = AdminInfo.getClassType_subArray(classTypeArray[i]);
                                                for(int j = 0;j<classType_subArray.length;j++){
                                            %>
                                            <option value="<%=classType_subArray[j] %>"><%=classType_subArray[j] %></option>
                                            <%
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </td>
                </tr>
                <%
                    }
                %>
                <tr align="center">
                    <td colspan="3">
                        <button class="layui-btn layui-btn-lg addClass">添加课程</button>
                    </td>
                </tbody>
            </table>

        </td>
        <td width="25%"></td>
    </tr>
</table>

<script type="text/javascript">
    $(".addClass").click(function () {
        window.location.href = "addClassUserInfo.jsp";
    })

</script>

<script>
    layui.use(['form', 'layedit', 'laydate'], function(){
        var form = layui.form
            ,layer = layui.layer
    });
</script>

</body>
</html>
