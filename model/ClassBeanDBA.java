package com.zehua.model;

import com.zehua.MysqlJDBC;

import java.util.ArrayList;

public class ClassBeanDBA {

    String classId = null;
    String className = null;
    String gradeId = null;
    String majorId = null;

    public void initializeInfo(ClassBean classBean){
        classId = classBean.getClassId();
        className = classBean.getClassName();
        gradeId = classBean.getGradeId();
        majorId = classBean.getMajorId();
    }

    //增
    public boolean addItem(ClassBean classBean){
        initializeInfo(classBean);

        String sql = "insert into class values(?,?,?,?)";
        String[] parameters = {classId,className,gradeId,majorId};

        boolean isSuccess = MysqlJDBC.executeUpdate(sql,parameters);

        return isSuccess;
    }
    //删
    public boolean deleteItem(ClassBean classBean){
        initializeInfo(classBean);

        String sql = "delete from class where classId = ? , className = ? , gradeId = ? , majorId = ?";
        String[] parameters = {classId,className,gradeId,majorId};

        boolean isSuccess = MysqlJDBC.executeUpdate(sql,parameters);

        return isSuccess;

    }
    //改
    public boolean modifyItem(ClassBean classBean){
        initializeInfo(classBean);

        String sql = "update class set classId = ? , className = ? where gradeId = ? and majorId = ?";
        String[] parameters = {classId,className,gradeId,majorId};

        boolean isSuccess = MysqlJDBC.executeUpdate(sql,parameters);

        return isSuccess;

    }
    //查，目前先只写按gradeId和majorId查询
    public ArrayList<ClassBean> findItem(String gradeId,String majorId){
        ArrayList<ClassBean> resultClassBeans = new ArrayList<ClassBean>();

        String sql = "select * from class where gradeId = ? and majorId = ?";
        String[] parameters = {gradeId,majorId};

        ArrayList<Object>[] resultArray = MysqlJDBC.query(sql,parameters);
        int length = resultArray.length;
        for(int i = 0;i<length;i++){
            ArrayList<Object> arrayList = resultArray[i];

            String thisClassId = arrayList.get(0).toString();
            String thisClassName = arrayList.get(1).toString();
            String thisGradeId = arrayList.get(2).toString();
            String thisMajorId = arrayList.get(3).toString();

            ClassBean thisClassBean = new ClassBean(thisClassId,thisClassName,thisGradeId,thisMajorId);
            resultClassBeans.add(thisClassBean);

        }
        return resultClassBeans;
    }

    //查！按classId查
    public ArrayList<ClassBean> findItem(String classId){
        ArrayList<ClassBean> resultClassBeans = new ArrayList<ClassBean>();

        String sql = "select * from class where classId = ?";
        String[] parameters = {classId};

        ArrayList<Object>[] resultArray = MysqlJDBC.query(sql,parameters);
        int length = resultArray.length;
        for(int i = 0;i<length;i++){
            ArrayList<Object> arrayList = resultArray[i];

            String thisClassId = arrayList.get(0).toString();
            String thisClassName = arrayList.get(1).toString();
            String thisGradeId = arrayList.get(2).toString();
            String thisMajorId = arrayList.get(3).toString();

            ClassBean thisClassBean = new ClassBean(thisClassId,thisClassName,thisGradeId,thisMajorId);
            resultClassBeans.add(thisClassBean);

        }
        return resultClassBeans;
    }

}
