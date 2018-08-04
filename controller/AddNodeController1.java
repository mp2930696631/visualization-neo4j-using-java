package com.zehua.controller;

import com.zehua.model2.NodeBeanDBA;
import com.zehua.model2.RelationshipBeanDBA;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "AddNodeController1")

//为了使节点或者关系的属性不用用户输入，我们规定在新建一个节点 类别，或者关系类别的时候
//都必须加入一个属性名为（名称），值为（sdu.zehuape@qq.com），且其他属性名的值都为空（不为空也可以，反正随便）;
//不管是节点类型还是关系类型，都这样处理
//以后！每次添加新的东西（关系或者节点），都事先查出对应的属性集合
//而 有新增属性的时候，值为（du.zehuape@qq.com）的必须同步更新
//而如果由新的节点类型被加入，这要求强制性注册（即，更新AdminInfo）！！
public class AddNodeController1 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String flag = request.getParameter("flag");
        HttpSession session = request.getSession();
        session.setAttribute("flag",flag);


        if(flag.equals("addNode")){
            NodeBeanDBA nodeBeanDBA = new NodeBeanDBA();

            String nodeType = request.getParameter("nodeType");
            //得到了节点类型，去查询该节点类型中，名称为（sdu.zehuape@qq.com）的节点
            //得到属性集合
            Map<String,String> node_AttrKey_AttrValue_Map =
                    nodeBeanDBA.getNodeAttrs(nodeType);
            //利用session传递对象
            session.setAttribute("node_AttrKey_AttrValue_Map",node_AttrKey_AttrValue_Map);

            session.setAttribute("nodeType",nodeType);
            String welcome = "欢迎来到添加 ("+nodeType+") 节点的界面";
            request.getRequestDispatcher("inputInfo.jsp?welcome="+welcome).forward(request,response);
        }
        else if(flag.equals("addRel")){
            RelationshipBeanDBA relationshipBeanDBA = new RelationshipBeanDBA();

            String fromNodeType = request.getParameter("fromNodeType");
            String relType = request.getParameter("relType");
            String toNodeType = request.getParameter("toNodeType");

            //得到了关系类型的名称，去查询name为（在sdu.zehuape@qq.com）的关系的所有属性
            Map<String,String> relation_AttrKey_AttrValue_Map =
                    relationshipBeanDBA.getRelAttrs(relType);
            //利用session传递对象
            session.setAttribute("relation_AttrKey_AttrValue_Map",relation_AttrKey_AttrValue_Map);

            session.setAttribute("fromNodeType",fromNodeType);
            session.setAttribute("relType",relType);
            session.setAttribute("toNodeType",toNodeType);

            String welcome = "欢迎来到添加 ("+fromNodeType+")"+"-["+relType+"]-"+"("+toNodeType+") 节点的界面";
            request.getRequestDispatcher("inputInfo.jsp?welcome="+welcome).forward(request,response);
        }

    }
}
