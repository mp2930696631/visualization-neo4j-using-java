package com.zehua.model;

import com.zehua.MysqlJDBC;

import java.util.ArrayList;

public class GradeBeanDBA {

    String gradeId = null;
    String gradeName = null;

    public void initializeInfo(GradeBean gradeBean){
        gradeId = gradeBean.getGradeId();
        gradeName = gradeBean.getGradeName();
    }

    public boolean addItem(GradeBean gradeBean){
        initializeInfo(gradeBean);

        String sql = "insert into grade values(?,?)";
        String[] parameters = {gradeId,gradeName};

        boolean isSuccess = MysqlJDBC.executeUpdate(sql,parameters);

        return isSuccess;

    }

    public boolean deleteItem(GradeBean gradeBean){
        initializeInfo(gradeBean);

        String sql = "delete from grade where gradeId = ? and gradeName = ?";
        String[] parameters = {gradeId,gradeName};

        boolean isSuccess = MysqlJDBC.executeUpdate(sql,parameters);

        return isSuccess;

    }

    public boolean modifyItem(GradeBean gradeBean){
        initializeInfo(gradeBean);

        String sql = "update grade set gradeName = ? where gradeId = ?";
        String[] parameters = {gradeName,gradeId};

        boolean isSuccess = MysqlJDBC.executeUpdate(sql,parameters);

        return isSuccess;

    }

    public ArrayList<GradeBean> findItem(String gradeId){

        ArrayList<GradeBean> resultGradeBeans = new ArrayList<GradeBean>();

        String sql = "select*from grade where gradeId = ?";
        String[] parameters = {gradeId};

        ArrayList<Object>[] resultArray = MysqlJDBC.query(sql,parameters);
        int length = resultArray.length;
        for(int i = 0;i<length;i++){
            ArrayList<Object> arrayList = resultArray[i];

            String thisGradeId = arrayList.get(0).toString();
            String thisGradeName = arrayList.get(1).toString();

            GradeBean thisGradeBean = new GradeBean(thisGradeId,thisGradeName);
            resultGradeBeans.add(thisGradeBean);

        }
        return resultGradeBeans;

    }

}
