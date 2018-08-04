<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %><%--
  Created by IntelliJ IDEA.
  User: 29306
  Date: 2018/5/26
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="css/myStyle.css">
    <link rel="stylesheet" href="layui/css/layui.css">

    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>

</head>
<body>

    <%

        //注意，java变量在js中不可以通过《%= %》这种方式来获取
        //可以通过：把java变量的值赋值给页面中<input type="hidden"/>的value，通过js去取；

        String welcome = request.getParameter("welcome");

        String flag = session.getAttribute("flag").toString();

        int length = 0;
        String[] attrKeys = null;
        if(flag.equals("addNode")){
            Map<String,String> node_AttrKey_AttrValue_Map =
                    (Map<String, String>) session.getAttribute("node_AttrKey_AttrValue_Map");
            //得到所有的属性
            System.out.println(node_AttrKey_AttrValue_Map);
            Object[] attrKeysObj = node_AttrKey_AttrValue_Map.keySet().toArray();
            length = attrKeysObj.length;
            attrKeys = new String[length];

            for(int i = 0;i<length;i++){
                attrKeys[i] = attrKeysObj[i].toString();
            }

        }
        if(flag.equals("addRel")){
            Map<String,String> relation_AttrKey_AttrValue_Map =
                    (Map<String, String>) session.getAttribute("relation_AttrKey_AttrValue_Map");
            //得到所有的属性
            Object[] attrKeysObj = relation_AttrKey_AttrValue_Map.keySet().toArray();
            length = attrKeysObj.length-1;
            attrKeys = new String[length];

            int index = 0;
            for(int i = 0;i<attrKeysObj.length;i++){
                if(attrKeysObj[i].toString().equals("name")) continue;
                attrKeys[index++] = attrKeysObj[i].toString();
            }
        }
    %>

    <!--通过一个hidden的input来保存nodeType的值-->
    <!--其实也可以直接用session传-->
    <input type="hidden" value="" class="nodeTypeInput">

    <script>
        function onSubmit(){
            var table = document.getElementById("table");
            //得到行数
            var rowsLength  = table.rows.length;
            //得到列数
            var colsLength = table.rows[0].cells.length;

            var result = "";

            //格式化输入的数据，即用空格分开
            var textarea = document.getElementsByTagName("textarea");
            var length = textarea.length;
            for(var i = 0;i<length;i++){
                var str = textarea[i].value;
                str += " ";
                result += str;
            }
            window.location.href = "AddNodeController2?result="+result+"&rowsLength="+rowsLength+"&colsLength="+colsLength;
        }
    </script>

<script type="text/javascript">
    $(document).ready(function(){
        <!--增加列-->
        $(".colAdd").click(function(){
            var str = "<td class='col'><textarea ></textarea></td>"
            $(".tr").append(str);
        });
        <!--减少列-->
        $(".colMinus").click(function(){
            var table = document.getElementById("table");
            var rowsLength  = table.rows.length;
            var colsLength = table.rows[0].cells.length;
            for(var i = 0;i<rowsLength;i++){
                var index = -1-(rowsLength-1-i)*colsLength;
                //移除最后一列元素，，
                // 因为这里是选种全部的col，而不是每一个tr下的col
                $(".col").eq(index).remove();
            }
        });
        <!--增加行-->
        $(".rowAdd").click(function(){
            var table = document.getElementById("table");
            var colsLength = table.rows[0].cells.length;

            var subStr1 = "<tr class=\"tr\">"
            var subStr2 = "";
            for(var i = 1;i<=colsLength;i++){
                var subStr2_str = "<td class='col'><textarea></textarea></td>";
                subStr2 += subStr2_str;
            }
            var subStr3 = "</tr>"
            var str = subStr1+subStr2+subStr3;
            $(".table").append(str);
        });
        <!--减少行-->
        $(".rowMinus").click(function(){
            <!--jquery语法，移除匹配元素中的最后一个元素-->
            $(".tr").eq(-1).remove();
        });
    });
</script>

<table width="100%" height="100%">
    <tr align="center">
        <td width="25%"></td>
        <td width="50%" style="background-color: burlywood;vertical-align: middle">
            <h1 align="center"><%=welcome %></h1>
            <hr>
            <br>
            <br>
            <br>
            <br>
            <table border="0" width="100%" align="center">
                <tr align="center">
                    <td width="25%"><button class="layui-btn layui-btn-sm colAdd" type="button">列+1</button></td>
                    <td width="25%"><button class="layui-btn layui-btn-sm colMinus" type="button">列-1</button></td>
                    <td width="25%"><button class="layui-btn layui-btn-sm rowAdd" type="button">行+1</button></td>
                    <td width="25%"><button class="layui-btn layui-btn-sm rowMinus" type="button">行-1</button></td>
                </tr>
            </table>
            <br>
            <table width="80%" border="1"  align="center" class="table" id="table">
                <tbody>
                <tr class="tr" bgcolor="#ffc0cb" align="center">
                    <%
                        if(flag.equals("addNode")){
                            for(int i = 0;i<length;i++){
                    %>
                    <td class="col" ><textarea readonly style="text-align: center;background-color: pink" ><%=attrKeys[i] %></textarea></td>
                    <%
                        }
                    }
                    else if(flag.equals("addRel")){
                    %>
                    <td class="col" ><textarea readonly style="text-align: center;background-color: pink" >名称</textarea></td>
                    <td class="col" ><textarea readonly style="text-align: center;background-color: pink" >name</textarea></td>
                    <td class="col" ><textarea readonly style="text-align: center;background-color: pink" >名称</textarea></td>
                    <%
                        for(int i = 0;i<length;i++){
                    %>
                    <td class="col" ><textarea readonly style="text-align: center;background-color: pink" ><%=attrKeys[i] %></textarea></td>
                    <%
                            }
                        }
                    %>
                </tr>
                <tr class="tr" align="center">
                    <%
                        if(flag.equals("addNode")){
                            for(int i = 0;i<length;i++){
                    %>
                    <td class="col" ><textarea ></textarea></td>
                    <%
                            }
                        }
                    %>
                    <%
                        if(flag.equals("addRel")){
                            for(int i = 0;i<length+3;i++){
                    %>
                    <td class="col" ><textarea ></textarea></td>
                    <%
                            }
                        }
                    %>
                </tr>
                </tbody>
            </table>
            <br>
            <table width="50%" border="0"  align="center" class="">
                <tbody>
                <tr align="center">
                    <td width="50%"><button class="layui-btn layui-btn-sm " type="button" >重置</button></td>
                    <td width="50%"><button class="layui-btn layui-btn-sm " type="button" onclick="onSubmit()">提交</button></td>
                </tr>
                </tbody>
            </table>
            <br>
            <br>
            <br>
            <br>
            <hr>
            <a href="index.jsp">返回主界面</a>
        </td>
        <td width="25%"></td>
    </tr>
</table>

</body>
</html>
