package com.zehua;

import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Path;
import org.neo4j.driver.v1.types.Relationship;
import systemInfo.SystemInfo;

import java.util.*;

public class Neo4jRestAPI {

    static Driver driver = null;

    public static void getDriver(){
        String uri = "Bolt://localhost:7687";
        String user = "neo4j";
        String password = "ccc561115";
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user,password));
    }
    public static void close(){
        if(driver!=null){
            driver.close();
        }
    }
    //add,delete,modify attribute,
    public static void executeCypher(String cypher){
        getDriver();
        try(Session session = driver.session()){

            try(Transaction transaction = session.beginTransaction()){
                transaction.run(cypher);
                transaction.success();
            }

        }
        close();
    }
    //node
    public static Map<String,String> executeFindNodeCypher(String cypher){
        getDriver();

        Map<String,String> node_AttrKey_AttrValue_Map = new HashMap<>();

        try(Session session = driver.session()){
            StatementResult result = session.run(cypher);

            while(result.hasNext()){
                Record record = result.next();
                List<Value> value = record.values();

                for(Value i:value){
                    Node node = i.asNode();
                    Iterator keys = node.keys().iterator();

                    while(keys.hasNext()){
                        String attrKey = (String)keys.next();
                        String attrValue = node.get(attrKey).asString();
                        node_AttrKey_AttrValue_Map.put(attrKey,attrValue);
                    }
                }
            }
        }
        close();
        return node_AttrKey_AttrValue_Map;
    }
    //relation
    public static Map<String,String> executeFindRelAttrCypher(String cypher){
        getDriver();

        Map<String,String> Rel_AttrKey_AttrValue_Map = new HashMap<>();

        try(Session session = driver.session()){
            StatementResult result = session.run(cypher);

            while(result.hasNext()){
                Record record = result.next();
                List<Value> value = record.values();

                for(Value i:value){
                    Map map = i.asMap();
                    Rel_AttrKey_AttrValue_Map = map;
                }
            }
        }
        close();
        return Rel_AttrKey_AttrValue_Map;
    }
    //返回关系的StringBuffer，为可视化做准备！json文件
    public static StringBuffer executeFindRelationCypher(String cypher){
        //关系的StringBuffer,json格式
        StringBuffer relationBuffer = new StringBuffer("");
        relationBuffer.append("\"links\":[");//return "links":[
        getDriver();

        try(Session session = driver.session()){
            //result包含了所有的path
            StatementResult result = session.run(cypher);

            while(result.hasNext()){
                Record record = result.next();
                List<Value> value = record.values();

                for(Value i:value){
                    Path path = i.asPath();
                    Iterator<Relationship> relationships = path.relationships().iterator();

                    while(relationships.hasNext()){
                        Relationship relationship = relationships.next();

                        long startNodeId = relationship.startNodeId();
                        long endNodeId = relationship.endNodeId();
                        String relType = relationship.type();

                        //得到关系属性的健
                        Iterator<String> relKeys = relationship.keys().iterator();

                        relationBuffer.append("{");
                        relationBuffer.append("\"source\":");
                        relationBuffer.append(startNodeId);
                        relationBuffer.append(",");
                        relationBuffer.append("\"target\":");
                        relationBuffer.append(endNodeId);
                        relationBuffer.append(",");
                        relationBuffer.append("\"type\":");
                        relationBuffer.append("\""+relType+"\"");

                        //这里处理关系属性
                        while(relKeys.hasNext()){
                            String relKey = relKeys.next();
                            String relValue = relationship.get(relKey).asObject().toString();

                            //去除制表符
                            relValue = relValue.replaceAll("\t","");
                            //去除换行符
                            relValue= relValue.replaceAll("\r","");
                            //去除回车符
                            relValue = relValue.replaceAll("\n","");

                            //将双引号换成单引号
                            relValue = relValue.replaceAll("\"","'");

                            relationBuffer.append(",");
                            relationBuffer.append("\""+relKey+"\"");
                            relationBuffer.append(":");
                            relationBuffer.append("\""+relValue+"\"");
                        }
                        if(!relationships.hasNext()&&!result.hasNext()){
                            relationBuffer.append("}");
                        }
                        else {
                            //如果是最后一个，只需要添加}即可
                            relationBuffer.append("},");
                        }

                    }
                }
            }
        }
        relationBuffer.append("]");
        close();
        return relationBuffer;
    }
    //返回关系中节点的StringBuffer，为可视化做准备！json文件,需要增加节点的类型
    public static StringBuffer executeFindRelationNodesCypher(String cypher) {
        //用一个set集合去除重复项
        Set nodeSet = new HashSet();
        StringBuffer relationNodesBuffer = new StringBuffer("");
        relationNodesBuffer.append("\"nodes\":[");
        getDriver();

        try(Session session = driver.session()){
            StatementResult result = session.run(cypher);

            while(result.hasNext()){
                Record record = result.next();
                List<Value> value = record.values();

                for(Value i:value){

                    Path path = i.asPath();
                    Iterator<Node> nodes = path.nodes().iterator();

                    while(nodes.hasNext()){
                        Node node = nodes.next();
                        //在增加节点以前，先判断是否在集合中
                        boolean isExist = nodeSet.contains(node.id());
                        if (isExist) continue;
                        Iterator<String> nodeKeys = node.keys().iterator();
                        relationNodesBuffer.append("{");

                        //节点属性
                        while(nodeKeys.hasNext()){
                            String nodeKey = nodeKeys.next();
                            relationNodesBuffer.append("\""+nodeKey+"\":");
                            //node.get(nodeKey).toString();
                            //System.out.println(node.get(nodeKey).asObject().toString());
                            String content = node.get(nodeKey).asObject().toString();

                            //去除制表符
                            content = content.replaceAll("\t","");
                            //去除换行符
                            content = content.replaceAll("\r","");
                            //去除回车符
                            content = content.replaceAll("\n","");

                            //将双引号换成单引号
                            content = content.replaceAll("\"","'");

                            relationNodesBuffer.append("\""+content+"\",");
                        }
                        relationNodesBuffer.append("\"id\":");
                        relationNodesBuffer.append(node.id());
                        //添加节点类型！不知道为什么取得节点类型用的是labels，可能一个节点可以属于多个类别
                        //但是我们这里只属于一个类别！
                        Iterator<String> nodeTypes = node.labels().iterator();
                        //得到节点类型了！
                        String nodeType = nodeTypes.next();

                        relationNodesBuffer.append(",");
                        relationNodesBuffer.append("\"type\":");
                        relationNodesBuffer.append("\""+nodeType+"\"");


                        //从SystemInfo中得到NodeTypeKeys，好为新的节点类型分配颜色
                        Set<String> nodeTypeKeys = SystemInfo.getNodeTypeKeys();

                        //如果是新的节点类型，则分配颜色，并保持到SystemInfo中
                        if(!nodeTypeKeys.contains(nodeType)){
                            int colorIndex = nodeTypeKeys.size();
                            //以防万一，所需颜色超过5，则随机一种颜色
                            if(colorIndex>=5){
                                colorIndex = (int)Math.random()*5;
                            }
                            //得到颜色集合
                            String[] colors = SystemInfo.getColor();
                            String color = colors[colorIndex];
                            //添加
                            SystemInfo.addNodeType_Color(nodeType,color);
                            //添加颜色属性
                            relationNodesBuffer.append(",");
                            relationNodesBuffer.append("\"color\":");
                            relationNodesBuffer.append("\""+color+"\"");
                        }
                        else{
                            //说明不是新的节点
                            //通过nodeType得到该节点的颜色
                            Map nodeType_Color = SystemInfo.getNodeType_Color();
                            String color = nodeType_Color.get(nodeType).toString();
                            //添加颜色属性
                            relationNodesBuffer.append(",");
                            relationNodesBuffer.append("\"color\":");
                            relationNodesBuffer.append("\""+color+"\"");
                        }

                        //将节点添加到set集合中
                        nodeSet.add(node.id());

                        if(!nodes.hasNext()&&!result.hasNext()){
                            relationNodesBuffer.append("}");
                        }
                        else{
                            relationNodesBuffer.append("},");
                        }
                    }
                }
            }
        }
        int bufferLength = relationNodesBuffer.length();
        char lastChar = relationNodesBuffer.charAt(bufferLength-1);
        if(lastChar==','){
            String str = relationNodesBuffer.substring(0,relationNodesBuffer.length()-1);
            relationNodesBuffer = relationNodesBuffer.replace(0,bufferLength,str);
        }
        relationNodesBuffer.append("]");
        close();
        return  relationNodesBuffer;
    }
    //执行多条cypher语句
    //处理关系
    public static StringBuffer executeFindRelationCyphers(ArrayList<String> cypherArrayList){
        StringBuffer relationBuffer = new StringBuffer("");
        relationBuffer.append("\"links\":[");//return "links":[
        getDriver();

        int index = 0;

        //新建一个Map,以startNodeId作为key，以endNodeId作为value，用来去重
        Map<Long,Long> start_end = new HashMap<>();

        //不知道可不可以批处理
        //这里我就没用批处理了
        try(Session session = driver.session()){

            for(int i = 0;i<cypherArrayList.size();i++){
                String cypher = cypherArrayList.get(i);
                StatementResult statementResult = session.run(cypher);

                while(statementResult.hasNext()){
                    Record record = statementResult.next();
                    List<Value> list = record.values();

                    for(Value item:list){
                        Path path = item.asPath();
                        Iterator<Relationship> rels = path.relationships().iterator();
                        while(rels.hasNext()){
                            index++;

                            Relationship relationship = rels.next();
                            long startNodeId = relationship.startNodeId();
                            long endNodeId = relationship.endNodeId();

                            //先判断是否重复,
                            boolean isExist = start_end.get(startNodeId)!=null&&start_end.get(startNodeId)==endNodeId;
                            //如果重复，跳过该关系
                            if(isExist){
                                continue;
                            }
                            //没用重复,将将该关系添加到map中
                            start_end.put(startNodeId,endNodeId);

                            String relType = relationship.type();

                            //一个新的关系到来
                            if(index==1){
                                relationBuffer.append("{");
                            }else{
                                relationBuffer.append(",{");
                            }
                            relationBuffer.append("\"source\":");
                            relationBuffer.append(startNodeId);
                            relationBuffer.append(",");
                            relationBuffer.append("\"target\":");
                            relationBuffer.append(endNodeId);
                            relationBuffer.append(",");
                            relationBuffer.append("\"type\":");
                            relationBuffer.append("\""+relType+"\"");

                            Iterator<String> relKeys = relationship.keys().iterator();

                            while(relKeys.hasNext()){
                                String relKey = relKeys.next();
                                String relKeyValue = relationship.get(relKey).asObject().toString();

                                //去除空格
                                //relKeyValue = relKeyValue.replaceAll("\\s","");
                                //去除制表符
                                relKeyValue = relKeyValue.replaceAll("\t","");
                                //去除换行符
                                relKeyValue = relKeyValue.replaceAll("\r","");
                                //去除回车符
                                relKeyValue = relKeyValue.replaceAll("\n","");

                                //将双引号换成单引号
                                relKeyValue = relKeyValue.replaceAll("\"","'");

                                relationBuffer.append(",");
                                relationBuffer.append("\""+relKey+"\":");
                                relationBuffer.append("\""+relKeyValue+"\"");
                            }
                            relationBuffer.append("}");
                            /*if(i==cypherArrayList.size()-1&&!rels.hasNext()){
                                relationBuffer.append("}");
                            }
                            else{
                                relationBuffer.append("},");
                            }*/
                        }
                    }
                }
            }
        }
        relationBuffer.append("]");
        close();
        return relationBuffer;
    }
    //接下来是处理节点！！，这个有点复杂了！因为需要添加颜色属性！
    //执行多条cypher语句
    public static StringBuffer executeFindRelationNodesCyphers(ArrayList<String> cypherArrayList){
        //用一个set集合去除重复项
        Set nodeSet = new HashSet();
        StringBuffer relationNodesBuffer = new StringBuffer("");
        relationNodesBuffer.append("\"nodes\":[");
        getDriver();

        int index = 0;

        try(Session session = driver.session()){

            for(int i = 0;i<cypherArrayList.size();i++){
                String cypher = cypherArrayList.get(i);
                StatementResult statementResult = session.run(cypher);

                while(statementResult.hasNext()){
                    Record record = statementResult.next();
                    List<Value> list = record.values();

                    for(Value item:list){
                        Path path = item.asPath();
                        Iterator<Node> nodes = path.nodes().iterator();

                        while(nodes.hasNext()){

                            index++;

                            Node node = nodes.next();
                            long nodeId = node.id();
                            boolean isExist = nodeSet.contains(nodeId);
                            if(isExist) continue;

                            if(index==1){
                                relationNodesBuffer.append("{");
                            }
                            else{
                                relationNodesBuffer.append(",{");
                            }

                            //没有重复，将nodeId添加到集合中
                            nodeSet.add(nodeId);

                            //得到node的类型
                            Iterator<String> nodeTypes = node.labels().iterator();
                            String nodeType = nodeTypes.next();
                            //从SystemInfo中得到NodeTypeKeys，好为新的节点类型分配颜色
                            Set<String> nodeTypeKeys = SystemInfo.getNodeTypeKeys();

                            //如果是新的节点类型，则分配颜色，并保持到SystemInfo中
                            if(!nodeTypeKeys.contains(nodeType)){
                                int colorIndex = nodeTypeKeys.size();
                                //以防万一，所需颜色超过5，则随机一种颜色
                                if(colorIndex>=5){
                                    colorIndex = (int)Math.random()*5;
                                }
                                //得到颜色集合
                                String[] colors = SystemInfo.getColor();
                                String color = colors[colorIndex];
                                //添加
                                SystemInfo.addNodeType_Color(nodeType,color);
                                //添加颜色属性
                                relationNodesBuffer.append("\"color\":");
                                relationNodesBuffer.append("\""+color+"\"");
                            }
                            else{
                                //说明不是新的节点
                                //通过nodeType得到该节点的颜色
                                Map nodeType_Color = SystemInfo.getNodeType_Color();
                                String color = nodeType_Color.get(nodeType).toString();
                                //添加颜色属性
                                relationNodesBuffer.append("\"color\":");
                                relationNodesBuffer.append("\""+color+"\"");
                            }

                            //添加id
                            relationNodesBuffer.append(",");
                            relationNodesBuffer.append("\"id\":");
                            relationNodesBuffer.append(nodeId);

                            //添加type
                            relationNodesBuffer.append(",");
                            relationNodesBuffer.append("\"type\":");
                            relationNodesBuffer.append("\""+nodeType+"\"");

                            //添加其他属性
                            Iterator<String> nodeKeys = node.keys().iterator();

                            while (nodeKeys.hasNext()){
                                String nodeKey = nodeKeys.next();
                                String nodeKeyValue = node.get(nodeKey).asObject().toString();

                                //去除空格
                                //nodeKeyValue = nodeKeyValue.replaceAll("\\s","");
                                //去除制表符
                                nodeKeyValue = nodeKeyValue.replaceAll("\t","");
                                //去除换行符
                                nodeKeyValue = nodeKeyValue.replaceAll("\r","");
                                //去除回车符
                                nodeKeyValue = nodeKeyValue.replaceAll("\n","");

                                //将双引号换成单引号
                                nodeKeyValue = nodeKeyValue.replaceAll("\"","'");

                                relationNodesBuffer.append(",");
                                relationNodesBuffer.append("\""+nodeKey+"\"");
                                relationNodesBuffer.append(":");
                                relationNodesBuffer.append("\""+nodeKeyValue+"\"");
                            }
                            //添加完一个节点后
                            relationNodesBuffer.append("}");
                        }

                    }
                }
            }
        }
        relationNodesBuffer.append("]");
        close();
        return relationNodesBuffer;
    }

}
