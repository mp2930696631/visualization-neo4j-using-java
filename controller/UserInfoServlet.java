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

@WebServlet(name = "UserInfoServlet")
public class UserInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //这句话可以避免jsp页面传值到servlet页面时出现乱码
        request.setCharacterEncoding("utf-8");

        //这句话可以避免从servlet到jsp页面出现乱码
        response.setContentType("text/html; charset=utf-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserBeanDBA userBeanDBA = new UserBeanDBA();
        //数据库验证
        ArrayList<UserBean> user = userBeanDBA.findItemWithNameAndPas(username,password);

        //在这里只会返回一个
        UserBean userBean = user.get(0);
        String id = userBean.getId();
        //这里AdminInfo保存数据
        AdminInfo.setUsername(username);
        AdminInfo.setId(id);

        UserInfoConfigBeanDBA userInfoConfigBeanDBA = new UserInfoConfigBeanDBA();
        ArrayList<UserInfoConfigBean> userInfoConfigBeans = userInfoConfigBeanDBA.findItem(id);

        //只会有一个
        //保存该用户的一些基本信息，也就是保存在AdminInfo中
        for(int i = 0;i<userInfoConfigBeans.size();i++){
            UserInfoConfigBean userInfoConfigBean = userInfoConfigBeans.get(i);

            String gradeId = userInfoConfigBean.getGradeId();
            String facultyId = userInfoConfigBean.getFacultyId();
            String majorId = userInfoConfigBean.getMajorId();

            AdminInfo.setGradeId(gradeId);
            AdminInfo.setFacultyId(facultyId);
            AdminInfo.setMajorId(majorId);

            //1、查找课程
            UserClassBeanDBA userClassBeanDBA = new UserClassBeanDBA();
            ArrayList<UserClassBean> userClassBeans = userClassBeanDBA.findItem(id);

            //这里会有多个，因为一个用户会与多门课发生联系
            for(int x = 0;x<userClassBeans.size();x++){
                UserClassBean userClassBean = userClassBeans.get(x);
                //得到课程编号，然后再去课程中查找！
                String classId = userClassBean.getClassId();

                ClassBeanDBA classBeanDBA = new ClassBeanDBA();
                //得到课程名称！
                ArrayList<ClassBean> classBeans = classBeanDBA.findItem(classId);
                //只会有一个
                ClassBean classBean = classBeans.get(0);
                String className = classBean.getClassName();
                //得到课程名称后，将它加入到AdminInfo中的ClassType域中
                AdminInfo.addClassTypeSetItem(className);
                //将课程编号加入到AdminInfo中
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
            //2、查找年级
            GradeBeanDBA gradeBeanDBA = new GradeBeanDBA();
            ArrayList<GradeBean> gradeBeans = gradeBeanDBA.findItem(gradeId);
            //只会有一个
            String gradeName = gradeBeans.get(0).getGradeName();
            //AdminInfo中的gradeName域
            AdminInfo.setGradeName(gradeName);
            //3、查找学院
            FacultyBeanDBA facultyBeanDBA = new FacultyBeanDBA();
            ArrayList<FacultyBean> facultyBeans = facultyBeanDBA.findItem(facultyId);
            //只会有一个
            String facultyName = facultyBeans.get(0).getFacultyName();
            //AdminInfo中的facultyName域
            AdminInfo.setFacultyName(facultyName);
            //4、查找专业
            MajorBeanDBA majorBeanDBA = new MajorBeanDBA();
            ArrayList<MajorBean> majorBeans = majorBeanDBA.findItem(majorId);
            //只有一个
            String majorName = majorBeans.get(0).getMajorName();
            //AdminInfo中的majorName域
            AdminInfo.setMajorName(majorName);
        }
        //至此，配置信息全部完成
        //可以跳转了！！
        request.getRequestDispatcher("index.jsp").forward(request,response);

    }
}
