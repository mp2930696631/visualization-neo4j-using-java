package com.zehua.model;

import com.zehua.MysqlJDBC;

import java.util.ArrayList;

public class FacultyBeanDBA {

    String facultyId = null;
    String facultyName = null;

    public void initializeInfo(FacultyBean facultyBean){
        facultyId = facultyBean.getFacultyId();
        facultyName = facultyBean.getFacultyName();
    }

    public boolean addItem(FacultyBean facultyBean){
        initializeInfo(facultyBean);

        String sql = "insert into faculty values(?,?)";
        String[] parameters = {facultyId,facultyName};

        boolean isSuccess = MysqlJDBC.executeUpdate(sql,parameters);

        return isSuccess;

    }

    public boolean deleteItem(FacultyBean facultyBean){
        initializeInfo(facultyBean);

        String sql = "delete from faculty where facultyId = ? and facultyName = ?";
        String[] parameters = {facultyId,facultyName};

        boolean isSuccess = MysqlJDBC.executeUpdate(sql,parameters);

        return isSuccess;

    }

    public boolean modifyItem(FacultyBean facultyBean){
        initializeInfo(facultyBean);

        String sql = "update faculty set facultyName = ? where facultyId = ?";
        String[] parameters = {facultyName,facultyId};

        boolean isSuccess = MysqlJDBC.executeUpdate(sql,parameters);

        return isSuccess;

    }

    public ArrayList<FacultyBean> findItem(String facultyId){

        ArrayList<FacultyBean> resultFacultyBeans = new ArrayList<FacultyBean>();

        String sql = "select*from faculty where facultyId = ?";
        String[] parameters = {facultyId};

        ArrayList<Object>[] resultArray = MysqlJDBC.query(sql,parameters);
        int length = resultArray.length;
        for(int i = 0;i<length;i++){
            ArrayList<Object> arrayList = resultArray[i];

            String thisFacultyId = arrayList.get(0).toString();
            String thisFacultyName = arrayList.get(1).toString();

            FacultyBean thisFacultyBean = new FacultyBean(thisFacultyId,thisFacultyName);
            resultFacultyBeans.add(thisFacultyBean);

        }
        return resultFacultyBeans;

    }

}
