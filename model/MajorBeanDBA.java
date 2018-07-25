package com.zehua.model;

import com.zehua.MysqlJDBC;

import java.util.ArrayList;

public class MajorBeanDBA {

    String majorId = null;
    String majorName = null;
    String facultyId = null;

    public void initializeInfo(MajorBean majorBean){
        majorId = majorBean.getMajorId();
        majorName = majorBean.getMajorName();
        facultyId = majorBean.getFacultyId();
    }

    public ArrayList<MajorBean> findItem(String majorId){

        ArrayList<MajorBean> resultMajorBeans = new ArrayList<MajorBean>();

        String sql = "select*from major where majorId = ?";
        String[] parameters = {majorId};

        ArrayList<Object>[] resultArray = MysqlJDBC.query(sql,parameters);
        int length = resultArray.length;
        for(int i = 0;i<length;i++){
            ArrayList<Object> arrayList = resultArray[i];

            String thisMajorId = arrayList.get(0).toString();
            String thisMajorName = arrayList.get(1).toString();
            String thisFacultyId = arrayList.get(2).toString();


            MajorBean thisMajorBean = new MajorBean(thisMajorId,thisMajorName,thisFacultyId);
            resultMajorBeans.add(thisMajorBean);

        }
        return resultMajorBeans;

    }

}
