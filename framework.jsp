<%--
  Created by IntelliJ IDEA.
  User: 29306
  Date: 2018/5/26
  Time: 9:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <title>我的</title>
  <link rel="stylesheet" href="layui/css/layui.css">
  <link rel="stylesheet" href="css/myStyle.css">

  <!--import d3 version 5-->
  <script type="text/javascript" src="js/d3.min.js"></script>
  <!--import jquery3.3.1-->
  <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>

  <link rel="stylesheet" href="css/jquery-ui.min.css">
  <script src="js/jquery-ui.min.js"></script>
</head>
<body class="layui-layout-body">


<div class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo">xxxxxxx</div>
    <!-- 头部区域（可配合layui已有的水平导航） -->
    <ul class="layui-nav layui-layout-left">
      <li class="layui-nav-item">
        <!-- 输入查询内容-->
        <!--这里不可以为空-->
        <form class="myform" action="DisplayController" method="post" >
          <input type="text" name="keyword"  placeholder="数据库" autocomplete="off" class="layui-input input">
        </form>
      </li>
      <span>&nbsp;&nbsp;&nbsp;</span>
      <li class="layui-nav-item">
        <!--这里不可以为空-->
        <form class="selectForm1" action="DisplayController">
          课程：

          <select  lay-search="" id="level1">
            <option value="">直接选择或搜索选择</option>
          </select>

        </form>

      </li>
      <li class="layui-nav-item">
        <!--这里可以为空-->
        <!--先不级联-->
        <form class="selectForm2" action="DisplayController">
          -->
          <select name="selectItem2"  lay-search="" id="level2">
            <option value="">--请选择--</option>
          </select>
        </form>
      </li>
      <span>&nbsp;&nbsp;&nbsp;</span>
      <li class="layui-nav-item">
        <!--查询-->
        <button class="layui-btn layui-btn-radius checkButton" onclick="checkButton()">查询</button>
      </li>
      <span>&nbsp;&nbsp;</span>
      <li class="layui-nav-item">
        <!--清空-->
        <button class="layui-btn layui-btn-radius clearButton" onclick="clearButton()">清空</button>
      </li>

    </ul>
    <ul class="layui-nav layui-layout-right">

      <li class="layui-nav-item">
        是否隐藏后继节点：
        <input type="radio" name="radio" value="yes">是&nbsp;&nbsp;
        <input type="radio" name="radio" value="no">否
      </li>

      <li class="layui-nav-item">
        <a href="javascript:;">
          <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
          贤心
        </a>
        <dl class="layui-nav-child">
          <dd><a href="showUserInfoConfig.jsp">基本资料</a></dd>
          <dd><a href="">安全设置</a></dd>
        </dl>
      </li>
      <li class="layui-nav-item"><a href="">退了</a></li>
    </ul>
  </div>

  <div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
      <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
      <ul class="layui-nav layui-nav-tree"  lay-filter="test">
        <li class="layui-nav-item layui-nav-itemed">
          <a class="" href="javascript:;">添加</a>
          <dl class="layui-nav-child">
            <dd><a href="javascript:;" data-method="addNode" class="addNode"><span>&nbsp;&nbsp;</span>添加节点</a></dd>
            <dd><a href="javascript:;" data-method="addRel" class="addRel"><span>&nbsp;&nbsp;</span>添加关系</a></dd>
            <dd>
              <a href="javascript:;"><span>&nbsp;&nbsp;</span>上传csv文件</a>
              <dl class="layui-nav-child">
                <dd><a href="javascript:;"><span>&nbsp;&nbsp;&nbsp;&nbsp;</span>上传添加节点.csv</a></dd>
                <dd><a href="javascript:;"><span>&nbsp;&nbsp;&nbsp;&nbsp;</span>上传添加关系.csv</a></dd>
              </dl>
            </dd>
          </dl>
        </li>
      </ul>
    </div>
  </div>
  <div class="layui-body">

    <svg class=".svg" width="100%" height="100%"></svg>

  </div>

  <div class="layui-footer">

  </div>
</div>
<script src="layui/layui.js"></script>

<script>
    //JavaScript代码区域
    layui.use(['element','layer'], function(){
        var element = layui.element;
        var layer = layui.layer;

        var $ = layui.jquery;

        var active = {

            addNode: function(){
                //示范一个公告层
                layer.open({
                    type: 1
                    ,title: false //不显示标题栏
                    ,closeBtn: false
                    ,area: '300px;'
                    ,shade: 0.8
                    ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
                    ,btn: ['取消']
                    ,btnAlign: 'c'
                    ,moveType: 1 //拖拽模式，0或者1
                    ,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">' +
                    '<form action="AddNodeController1?flag=addNode" method="post">'+
                    '节点类型：<input type="text" name="nodeType">'+
                    '<input type="submit" value="提交" >'+
                    '</form>'+
                    '</div>'
                });
            },

            addRel: function(){
                //示范一个公告层
                layer.open({
                    type: 1
                    ,title: false //不显示标题栏
                    ,closeBtn: false
                    ,area: '300px;'
                    ,shade: 0.8
                    ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
                    ,btn: ['取消']
                    ,btnAlign: 'c'
                    ,moveType: 1 //拖拽模式，0或者1
                    ,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">' +
                    '<form action="AddNodeController1?flag=addRel" method="post">'+
                    '前驱节点类型：<input type="text" name="fromNodeType"> <br>'+
                    ' 关 系 类 型：<input type="text" name="relType"> <br>'+
                    '后继节点类型：<input type="text" name="toNodeType">'+
                    '<input type="submit" value="提交" >'+
                    '</form>'+
                    '</div>'
                });
            }
        };

        $('.addNode').on('click', function(){
            var othis = $(this), method = othis.data('method');
            active[method] ? active[method].call(this, othis) : '';
        });
        $('.addRel').on('click', function(){
            var othis = $(this), method = othis.data('method');
            active[method] ? active[method].call(this, othis) : '';
        });
    });

</script>

<script>
    layui.use(['form', 'layedit', 'laydate'], function(){
        var form = layui.form
            ,layer = layui.layer
    });
</script>

</body>
</html>

