package com.zehua.model2;

import com.zehua.Neo4jRestAPI;
import com.zehua.neo4jJava.ToJson;
import systemInfo.SystemInfo;

import java.util.ArrayList;
import java.util.Map;

public class RelationshipBeanDBA {

    //标准格式
    //match (n1:XXX1{yyy1:"xxx1"}),(n2:XXX2{yyy2:"xxx2"})
    //merge (n1)-[:RRR{sss:"rrr"}]-(n2);
    public void addRelationship(String fromNodeType,String relType,String toNodeType,String[] someParametersName,String[][] someParapetersValue){
        for(int index = 0;index<someParapetersValue.length;index++){
            String cypher = "";
            StringBuffer stringBuffer = new StringBuffer("");
            stringBuffer.append("match (n1:");
            stringBuffer.append(fromNodeType);
            stringBuffer.append("{");
            stringBuffer.append(someParametersName[0]);
            stringBuffer.append(":");
            stringBuffer.append("\"");
            stringBuffer.append(someParapetersValue[index][0]);
            stringBuffer.append("\"");
            stringBuffer.append("})");
            stringBuffer.append(",");
            stringBuffer.append("(n2:");
            stringBuffer.append(toNodeType);
            stringBuffer.append("{");
            stringBuffer.append(someParametersName[2]);
            stringBuffer.append(":");
            stringBuffer.append("\"");
            stringBuffer.append(someParapetersValue[index][2]);
            stringBuffer.append("\"");
            stringBuffer.append("}) ");
            stringBuffer.append("merge (n1)-[:");//开始添加关系
            stringBuffer.append(relType);
            stringBuffer.append("{");//开始关系属性的添加
            stringBuffer.append(someParametersName[1]);
            stringBuffer.append(":");
            stringBuffer.append("\"");
            stringBuffer.append(someParapetersValue[index][1]);
            stringBuffer.append("\"");
            //因为添加关系时，关系必须有一个属性，
            //所以，下面的循环是添加关系其他的属性（如果有的话）
            for(int i = 3;i<someParametersName.length;i++){
                stringBuffer.append(",");
                stringBuffer.append(someParametersName[i]);
                stringBuffer.append(":");
                stringBuffer.append("\"");
                stringBuffer.append(someParapetersValue[index][i]);
                stringBuffer.append("\"");
            }
            stringBuffer.append("}]-(n2)");
            //得到cypher语句
            cypher = stringBuffer.toString();
            System.out.println(cypher);
            //执行
            Neo4jRestAPI.executeCypher(cypher);
        }
    }
    //这里是针对（sdu.zehuape@qq.com）所写的一个方法,这样的话，形参一个就可以了
    public Map<String,String> getRelAttrs(String relType){
        Map<String,String> relation_AttrKey_AttrValue_Map = null;

        String cypher = "";
        StringBuffer stringBuffer= new StringBuffer();
        stringBuffer.append("match ()-");
        stringBuffer.append("[r:");
        stringBuffer.append(relType);
        stringBuffer.append("{");
        stringBuffer.append("name:");
        stringBuffer.append("\"");
        stringBuffer.append("sdu.zehuape@qq.com");
        stringBuffer.append("\"");
        stringBuffer.append("}");
        stringBuffer.append("]");
        stringBuffer.append("-() ");
        stringBuffer.append("return r");

        cypher = stringBuffer.toString();

        System.out.println(cypher);
        //执行
        relation_AttrKey_AttrValue_Map = Neo4jRestAPI.executeFindRelAttrCypher(cypher);
        return relation_AttrKey_AttrValue_Map;
    }
    //写入json文件,查找
    public String lookInto(String nodeType, String nodeName){
        String cypher = "";

        if(nodeName==null||nodeName==""){
            StringBuffer stringBuffer1 = new StringBuffer("");
            stringBuffer1.append("match p=(:");
            stringBuffer1.append(nodeType);
            stringBuffer1.append(")");
            stringBuffer1.append("-[*..1]-() return p");

            cypher = stringBuffer1.toString();
        }else{
            StringBuffer stringBuffer2 = new StringBuffer("");
            stringBuffer2.append("match p=(:");
            stringBuffer2.append(nodeType);
            stringBuffer2.append("{");
            stringBuffer2.append("名称:");
            stringBuffer2.append("\"");
            stringBuffer2.append(nodeName);
            stringBuffer2.append("\"");
            stringBuffer2.append("}");
            stringBuffer2.append(")");
            stringBuffer2.append("-[*..1]-() return p");

            cypher = stringBuffer2.toString();

            //先清空，再将cypher语句加入到SystemInfo中

        }
        SystemInfo.clearAll();
        SystemInfo.addCypher(cypher);
//System.out.println(cypher);


        StringBuffer relationBuffer = Neo4jRestAPI.executeFindRelationCypher(cypher);
        StringBuffer relationNodesBuffer = Neo4jRestAPI.executeFindRelationNodesCypher(cypher);
        ToJson toJson = new ToJson(relationNodesBuffer,relationBuffer);
        //toJson.writeJson();
//System.out.println(toJson.getJson());

        return toJson.getJson();
    }
    //针对的是多条查询语句
    public String lookIntos(ArrayList<String> cypherArrayList){
        String cypher = "";

        StringBuffer relationBuffer = Neo4jRestAPI.executeFindRelationCyphers(cypherArrayList);
        StringBuffer relationNodesBuffer = Neo4jRestAPI.executeFindRelationNodesCyphers(cypherArrayList);
        ToJson toJson = new ToJson(relationNodesBuffer,relationBuffer);

        //toJson.writeJson();

        return toJson.getJson();
    }

}
