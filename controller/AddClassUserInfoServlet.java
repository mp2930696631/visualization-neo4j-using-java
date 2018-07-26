package com.zehua.controller;

import com.zehua.admin.AdminInfo;
import com.zehua.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "AddClassUserInfoServlet")
public class AddClassUserInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        response.setContentType("text/html; charset=utf-8");
        String[] userClasses = AdminInfo.getClassTypeArray();
        String[] userClassesId = AdminInfo.getClassIdArray();
        //将userClassesId转化成arrayList
        ArrayList<String> al = new ArrayList<>();

        for(int i = 0;i<userClassesId.length;i++){
            al.add(userClassesId[i]);
        }

        String id  = AdminInfo.getId();
        //获得了选中的class的id
        //通过classId查库
        //得到className
        String[] checkedClassIds = request.getParameterValues("className");
        ClassBeanDBA classBeanDBA = new ClassBeanDBA();
        UserClassBeanDBA userClassBeanDBA = new UserClassBeanDBA();

        for(int i = 0;i<checkedClassIds.length;i++){
            String classId = checkedClassIds[i];

            //得到className
            ArrayList<ClassBean> classBeans = classBeanDBA.findItem(classId);
            //只有一个
            String className = classBeans.get(0).getClassName();
            //先检查是否已经存在！通过id
            int j = 0;
            int size = al.size();
            for(;j<size;j++){
                if (classId.equals(al.get(j))){
                    //说明已经存在，并把这个id中al中移除
                    al.remove(classId);
                    break;
                }
            }
            //说明存在
            if(j<size) continue;

            //往userClass中添加记录
            UserClassBean userClassBean = new UserClassBean(id,classId);
            userClassBeanDBA.addItem(userClassBean);

            AdminInfo.addClassTypeSetItem(className);
            AdminInfo.addClassIdSetItem(classId);

            //然后去书表中利用课程号，查找到书集
            BookBeanDBA bookBeanDBA = new BookBeanDBA();
            ArrayList<BookBean> bookBeans = bookBeanDBA.findItem(classId);

            for(int y = 0;y<bookBeans.size();y++){
                BookBean bookBean = bookBeans.get(y);
                //得到书名
                String bookName = bookBean.getBookName();
                //然后可以添加AdminInfo的subSetItem域
                //这些是默认的，课程下面默认有：
                //章节，知识点
                //不过可以自己添加！
                String chapter = bookName+"章节";
                String knowledge = bookName+"知识点";
                String[] classType_subSetItem = {chapter,knowledge};

                AdminInfo.addClassType_subMap(className,classType_subSetItem);
            }
        }
        //循环完了之后。看看是否al为空，如果不为空，则说明用户删了一些课程
        //将用户删的课程从用户信息中移除
        for(int i = 0;i<al.size();i++){
            String thisClassId = al.get(i);
            //AdminInfo移除
            AdminInfo.deleteClassIdSetItem(thisClassId);
            String thisClassName = classBeanDBA.findItem(thisClassId).get(0).getClassName();
            AdminInfo.deleteClassTypeSetItem(thisClassName);
            //数据库中移除
            UserClassBean userClassBean = new UserClassBean(id,thisClassId);
            userClassBeanDBA.deleteItem(userClassBean);
        }
        //为AdminInfo添加选中的课程

        request.getRequestDispatcher("successful.jsp").forward(request,response);
    }
}
