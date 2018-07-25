package com.zehua.model2;

import com.zehua.Neo4jRestAPI;

import java.util.Map;

public class NodeBeanDBA {

    public void addNode(String nodeType,String[] nodeAttributesName,String[][] nodeAttributesValue){
        for(int index = 0;index<nodeAttributesValue.length;index++){
            String cypher = "";
            StringBuffer stringBuffer = new StringBuffer("");
            stringBuffer.append("merge (:");
            stringBuffer.append(nodeType);//节点类型
            stringBuffer.append("{");//开始添加节点属性
            int i = 0;
            for(i = 0;i<nodeAttributesName.length-1;i++){
                stringBuffer.append(nodeAttributesName[i]);
                stringBuffer.append(":");
                stringBuffer.append("\"");
                stringBuffer.append(nodeAttributesValue[index][i]);
                stringBuffer.append("\",");
            }
            //当添加最后一个属性时需要做特殊处理
            stringBuffer.append(nodeAttributesName[i]);
            stringBuffer.append(":");
            stringBuffer.append("\"");
            stringBuffer.append(nodeAttributesValue[index][i]);
            stringBuffer.append("\"");
            stringBuffer.append("})");
            //一条cypher语句拼接完成
            cypher = stringBuffer.toString();
            //执行
            Neo4jRestAPI.executeCypher(cypher);
        }
    }

    //这里是针对（sdu.zehuape@qq.com）所写的一个方法,这样的话，形参一个就可以了
    //标准格式match (n:XXX{xxx:"yyy"}) return n;
    public Map<String,String> getNodeAttrs(String nodeType){
        Map<String,String> node_AttrKey_AttrValue_Map = null;

        String cypher = "";
        StringBuffer stringBuffer= new StringBuffer();
        stringBuffer.append("match (n:");
        stringBuffer.append(nodeType);
        stringBuffer.append("{");
        stringBuffer.append("名称");
        stringBuffer.append(":");
        stringBuffer.append("\"");
        stringBuffer.append("sdu.zehuape@qq.com");
        stringBuffer.append("\"");
        stringBuffer.append("}) ");
        stringBuffer.append("return n");

        cypher = stringBuffer.toString();
        //执行
        node_AttrKey_AttrValue_Map = Neo4jRestAPI.executeFindNodeCypher(cypher);
        return node_AttrKey_AttrValue_Map;
    }

    /*
    public void deleteNode(String nodeType,String[] nodeAttributesName,String[] nodeAttributesValue){
        String cypher = "";
        StringBuffer stringBuffer = new StringBuffer("");
        stringBuffer.append("match (n:");
        stringBuffer.append(nodeType);//节点类型
        stringBuffer.append("{");//开始添加节点属性
        int i = 0;
        for(i = 0;i<nodeAttributesName.length-1;i++){
            stringBuffer.append(nodeAttributesName[i]);
            stringBuffer.append(":");
            stringBuffer.append("\"");
            stringBuffer.append(nodeAttributesValue[i]);
            stringBuffer.append("\",");
        }
        //当添加最后一个属性时需要做特殊处理
        stringBuffer.append(nodeAttributesName[i]);
        stringBuffer.append(":");
        stringBuffer.append("\"");
        stringBuffer.append(nodeAttributesValue[i]);
        stringBuffer.append("\"");
        stringBuffer.append("})");
        stringBuffer.append("delete n");
        //一条cypher语句拼接完成
        cypher = stringBuffer.toString();
        //执行
        Neo4jRestAPI.executeCypher(cypher);
    }*/

}
