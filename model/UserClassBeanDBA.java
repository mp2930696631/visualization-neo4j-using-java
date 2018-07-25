package com.zehua.model;

import com.zehua.MysqlJDBC;

import java.util.ArrayList;

public class UserClassBeanDBA {

    private String id;
    private String classId;

    public void initializeInfo(UserClassBean userClassBean){
        id = userClassBean.getId();
        classId = userClassBean.getClassId();
    }

    public boolean addItem(UserClassBean userClassBean){
        initializeInfo(userClassBean);

        String sql = "insert into userClass values (?,?)";
        String[] parameters = {id,classId};

        boolean isSuccess = MysqlJDBC.executeUpdate(sql,parameters);

        return isSuccess;

    }

    //删
    public boolean deleteItem(UserClassBean userClassBean){
        initializeInfo(userClassBean);

        String sql = "delete from userClass where id = ? and classId = ?";
        String[] parameters = {id,classId};

        boolean isSuccess = MysqlJDBC.executeUpdate(sql,parameters);

        return isSuccess;
    }

    public ArrayList<UserClassBean> findItem(String id){

        ArrayList<UserClassBean> resulUserClassBeans = new ArrayList<UserClassBean>();

        String sql = "select*from userClass where id = ?";
        String[] parameters = {id};

        ArrayList<Object>[] resultArray = MysqlJDBC.query(sql,parameters);
        int length = resultArray.length;
        for(int i = 0;i<length;i++){
            ArrayList<Object> arrayList = resultArray[i];

            String thisId = arrayList.get(0).toString();
            String thisClassId = arrayList.get(1).toString();

            UserClassBean thisUserClassBean = new UserClassBean(thisId,thisClassId);
            resulUserClassBeans.add(thisUserClassBean);

        }
        return resulUserClassBeans;

    }

}
