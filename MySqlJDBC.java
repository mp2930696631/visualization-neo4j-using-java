package com.zehua;

import java.sql.*;
import java.util.ArrayList;

public class MysqlJDBC {
    static Connection conn = null;
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;

    //得到connection
    public static Connection getConn(){
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/dbcd?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        String user = "scott";
        String password = "tiger";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    //关闭连接
    public static void close(Connection conn,PreparedStatement preparedStatement,ResultSet resultSet){
        if (conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //查询，返回查询集
    public static ArrayList[] query(String sql, String[] parameters){
        ArrayList<Object>[] resultArray = null;
        getConn();
        try {
            preparedStatement = conn.prepareStatement(sql);
            if(parameters!=null){
                for(int i = 0;i<parameters.length;i++){
                    preparedStatement.setString(i+1,parameters[i]);
                }
            }
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData meta = resultSet.getMetaData();
            //得到结果集中的列数目
            int colsCount = meta.getColumnCount();

            //这个arraylist用来临时保存结果，因为我在这里还没法得到记录的数目
            ArrayList<ArrayList> arrays = new ArrayList<>();

            int arrayLength = 0;
            int rowCount = 0;//为了得到记录的行数
            while(resultSet.next()){//每次移动一条数据
                rowCount++;
                ArrayList<Object> array = new ArrayList<Object>();
                for(int index = 1;index<=colsCount;index++){
                    //对于每一条记录，分别得到每列的数据
                    String str = resultSet.getString(index);
                    array.add(str);
                }
                arrays.add(array);
            }
            //当过了循环体后，就可以的到记录的数目了；
            resultArray = new ArrayList[rowCount];
            for(int i = 0;i<arrays.size();i++){
                resultArray[i] = arrays.get(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close(conn,preparedStatement,resultSet);
        return resultArray;
    }
    //更新数据，包括更新，插入，删除
    public static boolean executeUpdate(String sql,String[] parameters){
        boolean isSuccess = false;
        getConn();
        try {
            preparedStatement  = conn.prepareStatement(sql);
            for(int i = 0;i<parameters.length;i++){
                preparedStatement.setString(i+1,parameters[i]);
            }
            preparedStatement.executeUpdate();
            isSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
}
