package systemInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//1、这个类包含用户某一系列操作查询的所有语句（即用户双击了节点）
//而当用户重新输入数据时，该类将重新保存用户的查询产生的查询语句！
//2、这个类还包含一个颜色数组color[5]
// （数组大小为5 已经足够！因为可能展现给用户的节点类别不超过5）
//展现的不同的节点类用不同的颜色
//3、包含一个节点-颜色的对应关系
public class SystemInfo {

    //用于保存每次用户点击“查询”后的所有查询语句，用于实现“双击”节点后弹出下一层
    public static ArrayList<String> cypherArrayList = new ArrayList<>();
    //默认为5种颜色
    public static final String[] COLOR = {"blue","yellow","green","pink","purple"};
    //将一种颜色和一种类型的节点对应起来
    public static Map nodeType_Color = new HashMap();

    //添加cypher语句
    public static void addCypher(String... cyphers){
        int length = cyphers.length;

        for(int i = 0;i<length;i++){
            String cypher = cyphers[i];
            cypherArrayList.add(cypher);
        }
    }
    //返回保存的cypher语句
    public static ArrayList<String> getCypherArrayList(){
        return cypherArrayList;
    }
    //清空所有
    public static void clearAll(){
        cypherArrayList.clear();
        nodeType_Color.clear();
    }
    //返回颜色数组
    public static String[] getColor(){
        return COLOR;
    }
    //添加节点和颜色的映射
    public static void addNodeType_Color(String nodeType,String color){
        nodeType_Color.put(nodeType,color);
    }
    //返回节点和颜色的映射
    public static Map<String,String> getNodeType_Color(){
        return nodeType_Color;
    }
    //返回已经分配颜色的节点集合
    public static Set<String> getNodeTypeKeys(){
        return nodeType_Color.keySet();
    }

}
