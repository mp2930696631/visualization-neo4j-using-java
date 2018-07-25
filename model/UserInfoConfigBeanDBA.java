package com.zehua.model;

import com.zehua.MysqlJDBC;

import java.util.ArrayList;

public class UserInfoConfigBeanDBA {

    private String id;
    private String gradeId;
    private String facultyId;
    private String majorId;

    public void initializeInfo(UserInfoConfigBean userInfoConfigBean){
        id = userInfoConfigBean.getId();
        gradeId = userInfoConfigBean.getGradeId();
        facultyId = userInfoConfigBean.getFacultyId();
        majorId = userInfoConfigBean.getMajorId();
    }

    public ArrayList<UserInfoConfigBean> findItem(String id){

        ArrayList<UserInfoConfigBean> resultUserInfoConfigBeans = new ArrayList<UserInfoConfigBean>();

        String sql = "select*from userInfoConfig where id = ?";
        String[] parameters = {id};

        ArrayList<Object>[] resultArray = MysqlJDBC.query(sql,parameters);
        int length = resultArray.length;
        for(int i = 0;i<length;i++){
            ArrayList<Object> arrayList = resultArray[i];

            String thisId = arrayList.get(0).toString();
            String thisGradeId = arrayList.get(1).toString();
            String thisFacultyId = arrayList.get(2).toString();
            String thisMajorId = arrayList.get(3).toString();

            UserInfoConfigBean thisUserInfoConfigBean =
                    new UserInfoConfigBean(thisId,thisGradeId,thisFacultyId,thisMajorId);
            resultUserInfoConfigBeans.add(thisUserInfoConfigBean);

        }
        return resultUserInfoConfigBeans;

    }

}
