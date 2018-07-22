package com.zehua.neo4jJava;

import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Node;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GetNodes {

    static Driver driver = null;

    public static void getDriver(){
        String uri = "Bolt://localhost:7687";
        String user = "";//写你自己的neo4j的用户名
        String password = "";//写你自己的neo4j的密码
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user,password));
    }

    public static void close(){
        if(driver!=null){
            driver.close();
        }
    }

    //node
    public static void getNodesInfo(String cypher){
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

                    Iterator nodeTypes = node.labels().iterator();
                    String nodeType = nodeTypes.next().toString();
                    System.out.println("节点类型："+nodeType);
                    System.out.println("节点属性如下：");
                    while(keys.hasNext()){
                        String attrKey = (String)keys.next();
                        String attrValue = node.get(attrKey).asString();
                        System.out.println(attrKey+"-------"+attrValue);
                    }
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                }
            }
        }
        close();
    }

    public static void main(String... args){

        String cypher = "match (n:数据库章节{名称:'关系数据库'}) return n";
        getNodesInfo(cypher);

    }

}
