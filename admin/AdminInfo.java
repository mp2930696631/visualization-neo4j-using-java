package com.zehua.admin;

import org.bouncycastle.math.ec.custom.sec.SecT113R1Point;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//目前只支持查询课程级别一下的关系
//怎样使的在用户登录后，建立一个全局唯一的AdminInfo!!!!!!!!!!!!!
public class AdminInfo {

    private static String username = "";//用户名
    private static String id = "";//用户id
    private static String gradeId = "";//年级id
    private static String gradeName = "";//年级
    private static String facultyId = "";//学院id
    private static String facultyName = "";//学院
    private static String majorId = "";//专业id
    private static String majorName = "";//专业
    private static Set<String> classIdSet = new HashSet();//所选课的id
    private static Set<String> classTypeSet = new HashSet();//所选课的名称
    private static Map<String,Set<String>> classType_subMap = new HashMap();

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        AdminInfo.username = username;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        AdminInfo.id = id;
    }

    public static String getGradeId() {
        return gradeId;
    }

    public static void setGradeId(String gradeId) {
        AdminInfo.gradeId = gradeId;
    }

    public static String getGradeName() {
        return gradeName;
    }

    public static void setGradeName(String gradeName) {
        AdminInfo.gradeName = gradeName;
    }

    public static String getFacultyId() {
        return facultyId;
    }

    public static void setFacultyId(String facultyId) {
        AdminInfo.facultyId = facultyId;
    }

    public static String getFacultyName() {
        return facultyName;
    }

    public static void setFacultyName(String facultyName) {
        AdminInfo.facultyName = facultyName;
    }

    public static String getMajorId() {
        return majorId;
    }

    public static void setMajorId(String majorId) {
        AdminInfo.majorId = majorId;
    }

    public static String getMajorName() {
        return majorName;
    }

    public static void setMajorName(String majorName) {
        AdminInfo.majorName = majorName;
    }

    //添加课程Id
    public static void addClassIdSetItem(String... items){
        int length = items.length;

        for(int i = 0;i<length;i++){
            classIdSet.add(items[i]);
        }
    }
    //移除课程id
    public static void deleteClassIdSetItem(String... items){
        int length = items.length;

        for(int i = 0;i<length;i++){
            classIdSet.remove(items[i]);
        }
    }
    //返回课程id
    public static String[] getClassIdArray(){
        int length = classIdSet.size();
        Object[] classIdArrayObj = classIdSet.toArray();
        String[] classIdArray = new String[length];

        for(int i = 0;i<length;i++){
            classIdArray[i] = classIdArrayObj[i].toString();
        }

        return classIdArray;
    }
    //添加课程级别的节点
    public static void addClassTypeSetItem(String... items){
        int length = items.length;

        for(int i = 0;i<length;i++){
            classTypeSet.add(items[i]);
        }
    }
    //移除课程
    public static void deleteClassTypeSetItem(String... items){
        int length = items.length;

        for(int i = 0;i<length;i++){
            classTypeSet.remove(items[i]);
        }
    }
    //得到课程级别的节点类型的集合
    public static String[] getClassTypeArray(){
        int length = classTypeSet.size();
        Object[] classTypeArrayObj = classTypeSet.toArray();
        String[] classTypeArray = new String[length];

        for(int i = 0;i<length;i++){
            classTypeArray[i] = classTypeArrayObj[i].toString();
        }

        return classTypeArray;
    }
    //添加某课程下面的节点类型，这里默认为“章节”和“知识点”,不太明白没关系
    //后面会有所了解
    public static void addClassType_subMap(String classTypeSetItem,String... items){
        Set<String> classType_subSet = classType_subMap.get(classTypeSetItem);

        if(classType_subSet==null){
            classType_subSet = new HashSet();
        }
        int length = items.length;

        for(int i = 0;i<length;i++){
            classType_subSet.add(items[i]);
        }
        classType_subMap.put(classTypeSetItem,classType_subSet);
    }
    //得到某课程下面的节点类型集合，这里默认为“章节”和“知识点”,不太明白没关系
    public static String[] getClassType_subArray(String classTypeSetItem){
        Set classType_subSet = classType_subMap.get(classTypeSetItem);
        int length = classType_subSet.size();
        Object[] classType_subArrayObj = classType_subSet.toArray();
        String[] classType_subArray = new String[length];

        for(int i = 0;i<length;i++){
            classType_subArray[i] = classType_subArrayObj[i].toString();
        }
        return classType_subArray;
    }
    //得到全部课程下面的节点类型集合
    public static Map getClassType_subMap(){

        return classType_subMap;
    }
}
