package com.zehua.controller;

import com.zehua.model.UserBean;
import com.zehua.model.UserBeanDBA;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //这句话可以避免jsp页面传值到servlet页面时出现乱码
        request.setCharacterEncoding("utf-8");

        //这句话可以避免从servlet到jsp页面出现乱码
        response.setContentType("text/html; charset=utf-8");

        PrintWriter out = response.getWriter();

        String userNameError = "";
        String userPasswordError = "";
        String isOk = "false";

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserBeanDBA userBeanDBA = new UserBeanDBA();
        //数据库验证
        ArrayList<UserBean> users = userBeanDBA.findItemWithName(username);
        if(users.size()!=0){
            ArrayList<UserBean> userBean = userBeanDBA.findItemWithNameAndPas(username,password);
            //这里只会有一个用户
            if(userBean.size()!=0){
                isOk = "true";
            }
            else{
                userPasswordError = "密码错误！";
            }
        }
        else{
            userNameError = "用户名不存在！";
        }
        StringBuffer stringBuffer = new StringBuffer("");
        stringBuffer.append("{");
        stringBuffer.append("\"");
        stringBuffer.append("userNameError");
        stringBuffer.append("\"");
        stringBuffer.append(":");
        stringBuffer.append("\"");
        stringBuffer.append(userNameError);
        stringBuffer.append("\"");
        stringBuffer.append(",");
        stringBuffer.append("\"");
        stringBuffer.append("userPasswordError");
        stringBuffer.append("\"");
        stringBuffer.append(":");
        stringBuffer.append("\"");
        stringBuffer.append(userPasswordError);
        stringBuffer.append("\"");
        stringBuffer.append(",");
        stringBuffer.append("\"");
        stringBuffer.append("isOk");
        stringBuffer.append("\"");
        stringBuffer.append(":");
        stringBuffer.append("\"");
        stringBuffer.append(isOk);
        stringBuffer.append("\"");
        stringBuffer.append("}");

        String json = stringBuffer.toString();
        out.write(json);
        //查询数据库，新建一个AdminInfo


    }
}
