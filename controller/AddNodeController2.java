package com.zehua.controller;

import com.zehua.model2.NodeBeanDBA;
import com.zehua.model2.RelationshipBeanDBA;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddNodeController2")
public class AddNodeController2 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String result = request.getParameter("result");
        String rowsLengthStr = request.getParameter("rowsLength");
        String colsLengthStr = request.getParameter("colsLength");
        int rowsLength = 0;
        int colsLength = 0;

        if(rowsLengthStr!=null){
            rowsLength = Integer.parseInt(rowsLengthStr);
        }
        if(colsLengthStr!=null){
            colsLength = Integer.parseInt(colsLengthStr);
        }

        //本来在输入的时候是可以输入空格的（或者什么都不输入！这个可以在那个jsp页面加一个判断，填入%作为标记，表示特殊情况）
        // 目前只考虑全输入的情况！

        //按空格分割
        String[] resultStrs = result.split(" ");
        for(int i = 0;i<resultStrs.length;i++){
            System.out.println(resultStrs[i]);
        }
        //健
        String[] attributesName = new String[colsLength];
        //值
        String[][] attributesValue = new String[rowsLength-1][colsLength];
        //第一列为属性名
        int i = 0;

        for(;i<colsLength;i++){
            attributesName[i] = resultStrs[i];
        }
        //其他的列为属性值，是一个二维数组
        for(int x = 0;x<rowsLength-1;x++){

            for(int y = 0;y<colsLength;y++){
                System.out.println(x*colsLength+y+i);
                attributesValue[x][y] = resultStrs[x*colsLength+y+i];
            }
        }
        String flag = request.getSession().getAttribute("flag").toString();
        if(flag.equals("addNode")){
            String nodeType = request.getSession().getAttribute("nodeType").toString();

            String[] nodeAttributesName = attributesName;
            String[][] nodeAttributesValue = attributesValue;
            NodeBeanDBA nodeBeanDBA = new NodeBeanDBA();
            nodeBeanDBA.addNode(nodeType,nodeAttributesName,nodeAttributesValue);
            request.getRequestDispatcher("successful.jsp").forward(request,response);
        }
        else if(flag.equals("addRel")){
            String fromNodeType = request.getSession().getAttribute("fromNodeType").toString();
            String relType = request.getSession().getAttribute("relType").toString();
            String toNodeType = request.getSession().getAttribute("toNodeType").toString();

            String[] someParametersName = attributesName;
            String[][] someParametersValue = attributesValue;
            RelationshipBeanDBA relationshipBeanDBA = new RelationshipBeanDBA();
            relationshipBeanDBA.addRelationship(fromNodeType,relType,toNodeType,someParametersName,someParametersValue);

            request.getRequestDispatcher("successful.jsp").forward(request,response);
        }
    }
}
