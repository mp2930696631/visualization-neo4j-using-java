package com.zehua.model;

import com.zehua.MysqlJDBC;

import java.util.ArrayList;

public class UserBeanDBA {

    private String username;
    private String password;
    private String id;

    public void initializeInfo(UserBean userBean){
        username = userBean.getUsername();
        password = userBean.getPassword();
        id = userBean.getId();
    }

    //按用户名，密码查找
    public ArrayList<UserBean> findItemWithNameAndPas(String username, String password){

        ArrayList<UserBean> resultUserBeans = new ArrayList<UserBean>();

        String sql = "select*from user where username = ? and password = ?";
        String[] parameters = {username,password};

        ArrayList<Object>[] resultArray = MysqlJDBC.query(sql,parameters);
        int length = resultArray.length;
        for(int i = 0;i<length;i++){
            ArrayList<Object> arrayList = resultArray[i];

            String thisUsername = arrayList.get(0).toString();
            String thisPassword = arrayList.get(1).toString();
            String thisId = arrayList.get(2).toString();

            UserBean thisUserBean = new UserBean(thisUsername,thisPassword,thisId);
            resultUserBeans.add(thisUserBean);

        }
        return resultUserBeans;

    }

    //按用户名查找
    public ArrayList<UserBean> findItemWithName(String username){

        ArrayList<UserBean> resultUserBeans = new ArrayList<UserBean>();

        String sql = "select*from user where username = ?";
        String[] parameters = {username};

        ArrayList<Object>[] resultArray = MysqlJDBC.query(sql,parameters);
        int length = resultArray.length;
        for(int i = 0;i<length;i++){
            ArrayList<Object> arrayList = resultArray[i];

            String thisUsername = arrayList.get(0).toString();
            String thisPassword = arrayList.get(1).toString();
            String thisId = arrayList.get(2).toString();

            UserBean thisUserBean = new UserBean(thisUsername,thisPassword,thisId);
            resultUserBeans.add(thisUserBean);

        }
        return resultUserBeans;

    }

}
