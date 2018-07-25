package com.zehua.model;

import com.zehua.MysqlJDBC;

import java.util.ArrayList;

public class BookBeanDBA {

    String bookId = null;
    String bookName = null;
    String classId = null;
    public void initializeInfo(BookBean bookBean){
        bookId = bookBean.getBookId();
        bookName = bookBean.getBookName();
        classId = bookBean.getClassId();
    }
    //增
    public boolean addItem(BookBean bookBean){
        initializeInfo(bookBean);

        String sql = "insert into book values(?,?,?)";
        String[] parameters = {bookId,bookName,classId};

        boolean isSuccess = MysqlJDBC.executeUpdate(sql,parameters);

        return isSuccess;
    }
    //删
    public boolean deleteItem(BookBean bookBean){
        initializeInfo(bookBean);

        String sql = "delete from book where bookId = ? and bookName = ? and classId = ?";
        String[] parameters = {bookId,bookName,classId};

        boolean isSuccess = MysqlJDBC.executeUpdate(sql,parameters);

        return isSuccess;
    }
    //改
    public boolean modifyItem(BookBean bookBean){
        initializeInfo(bookBean);

        String sql = "update book set bookId = ? , bookName = ? where classId = ?";
        String[] parameters = {bookId,bookName,classId};

        boolean isSuccess = MysqlJDBC.executeUpdate(sql,parameters);

        return isSuccess;
    }
    //查，目前先只写按classId查询
    public ArrayList<BookBean> findItem(String classId){

        ArrayList<BookBean> resultBookBeans = new ArrayList<BookBean>();

        String sql = "select*from book where classId = ?";
        String[] parameters = {classId};

        ArrayList<Object>[] resultArray = MysqlJDBC.query(sql,parameters);
        int length = resultArray.length;
        for(int i = 0;i<length;i++){
            ArrayList<Object> arrayList = resultArray[i];

            String thisBookId = arrayList.get(0).toString();
            String thisBookName = arrayList.get(1).toString();
            String thisClassId = arrayList.get(2).toString();

            BookBean thisBookBean = new BookBean(thisBookId,thisBookName,thisClassId);
            resultBookBeans.add(thisBookBean);

        }
        return resultBookBeans;

    }

}
