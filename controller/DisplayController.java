package com.zehua.controller;

import com.zehua.model2.RelationshipBeanDBA;
import systemInfo.SystemInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "DisplayController")
public class DisplayController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //这句话可以解决从jsp页面接受到中文乱码问题
        request.setCharacterEncoding("utf-8");

        response.setContentType("text/html; charset=utf-8");

        PrintWriter out  = response.getWriter();

        String selectItem1 = request.getParameter("selectItem1");
        String selectItem2 = request.getParameter("selectItem2");
        String keyword = request.getParameter("keyword");
        String times = request.getParameter("times");//判断这次操作是用户点击查询按钮还是双击节点
        String isHide = request.getParameter("isHide");

        String nodeType = "";
        String nodeName = "";

        if(selectItem1!=null&&selectItem1!=""){
            if(selectItem2!=null&&selectItem2!=""){
                nodeType = selectItem2;
            }
            else nodeType= selectItem1;
        }
        nodeName  = keyword;

        String json = "";
        RelationshipBeanDBA relationshipBeanDBA = new RelationshipBeanDBA();
        //用户点击的是查询按钮！
        if(times.equals("first")||isHide.equals("yes")){
            //这里需要特别注意！！，因为更换了服务器tomcat->neo4j，所以会报500错误，
            //解决办法：将neo4j的驱动包，拷到jre中lib的ext目录下
            json = relationshipBeanDBA.lookInto(nodeType,nodeName);
        }
        //用户双击了节点！
        else{
            //执行新的查询方法（允许多条查询语句语句同时进行）
            //先将语句加入到SystemInfo中，然后在得到全部的查询语句
            String cypher = "";

            StringBuffer stringBuffer = new StringBuffer("");
            stringBuffer.append("match p=(:");
            stringBuffer.append(nodeType);
            stringBuffer.append("{");
            stringBuffer.append("名称:");
            stringBuffer.append("\"");
            stringBuffer.append(nodeName);
            stringBuffer.append("\"");
            stringBuffer.append("}");
            stringBuffer.append(")");
            stringBuffer.append("-[*..1]-() return p");

            cypher = stringBuffer.toString();

            SystemInfo.addCypher(cypher);

            //得到所有的查询语句
            ArrayList<String> cypherArrayList = SystemInfo.getCypherArrayList();

            /*for(int i = 0;i<cypherArrayList.size();i++){
                System.out.println(cypherArrayList.get(i));
            }*/

            json = relationshipBeanDBA.lookIntos(cypherArrayList);
        }
        out.write(json);
    }
}
