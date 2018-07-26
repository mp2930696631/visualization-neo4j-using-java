<%@ page import="com.zehua.admin.AdminInfo" %>
<%@ page import="com.zehua.model.ClassBeanDBA" %>
<%@ page import="com.zehua.model.ClassBean" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: 29306
  Date: 2018/6/6
  Time: 20:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>我的</title>
    <link rel="stylesheet" href="layui/css/layui.css">
    <link rel="stylesheet" href="css/myStyle.css">

    <!--import d3 version 5-->
    <script type="text/javascript" src="js/d3.min.js"></script>
    <!--import jquery3.3.1-->
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
</head>
<body>

<%
    String[] allClasses;
    String[] allClassesId;
    String[] userClasses = AdminInfo.getClassTypeArray();
    //先得到该年级，专业下的所有课程
    //通过gradeId，和majorId
    String gradeId = AdminInfo.getGradeId();
    String majorId = AdminInfo.getMajorId();

    ClassBeanDBA classBeanDBA  = new ClassBeanDBA();
    ArrayList<ClassBean> classBeans= classBeanDBA.findItem(gradeId,majorId);
    allClasses = new String[classBeans.size()];
    allClassesId = new String[classBeans.size()];
    for(int i = 0;i<classBeans.size();i++){
        ClassBean classBean = classBeans.get(i);
        String className = classBean.getClassName();
        String classId = classBean.getClassId();
        allClasses[i] = className;
        allClassesId[i]= classId;
    }
%>

<table width="100%" height="100%">
    <tr align="center">
        <td width="25%"></td>
        <td width="50%" style="background-color: burlywood;vertical-align: middle">
            <form class="layui-form" action="AddClassUserInfoServlet">
                        <h1>请选择你需要添加的课程</h1>
                        <hr>
                        <br>
                        <br>
                        <br>
                        <%
                            for(int i = 0;i<allClasses.length;i++){
                                String thisClassName = allClasses[i];
                                String thisClassId = allClassesId[i];
                                //标记是否已经在用户课程表中了！！
                                boolean isInclude = false;
                                for(int x = 0;x<userClasses.length;x++){
                                    if(thisClassName.equals(userClasses[x])){
                                        isInclude = true;
                                        break;
                                    }
                                }
                                if(isInclude){
                        %>
                        <input type="checkbox" name="className" title="<%=thisClassName %>" value="<%=thisClassId %>" checked="true"><br><br>
                        <%
                        }else{
                        %>
                        <input type="checkbox" name="className" title="<%=thisClassName %>" value="<%=thisClassId %>"><br><br>
                        <%
                                }
                            }
                        %>

                <br>
                <br>
                <br>
                <hr>
                <input type="submit" class="layui-btn layui-btn-lg" value="确定">
            </form>
        </td>
        <td width="25%"></td>
    </tr>
</table>


<script src="layui/layui.js"></script>
<script>
    layui.use(['form', 'layedit', 'laydate'], function(){
        var form = layui.form
            ,layer = layui.layer
    });
</script>
</body>
</html>
