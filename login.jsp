<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>Home</title>
    <!-- Animate.css -->
    <link rel="stylesheet" href="css/animate.css">
    <!-- Bootstrap  -->
    <link rel="stylesheet" href="css/bootstrap.css">
    <!-- Theme style  -->
    <link rel="stylesheet" href="css/style.css">

    <!--js-->
    <!-- Modernizr JS -->
    <script src="js/modernizr-2.6.2.min.js"></script>
    <!-- FOR IE9 below -->
    <!--[if lt IE 9]>
    <script src="js/respond.min.js"></script>
    <![endif]-->
    <!-- jQuery -->
    <script src="js/jquery-3.3.1.min.js"></script>
    <!-- Waypoints -->
    <script src="js/jquery.waypoints.min.js"></script>
    <!-- Carousel -->
    <script src="js/owl.carousel.min.js"></script>
    <!-- Main -->
    <script src="js/main.js"></script>

</head>
<body>

<div class="gtco-loader"></div>

<div id="page">


    <div class="page-inner">


        <header id="gtco-header" class="gtco-cover" role="banner" style="background-image: url(images/img_4.jpg)">
            <div class="overlay"></div>
            <div class="gtco-container">
                <div class="row">
                    <div class="col-md-12 col-md-offset-0 text-left">
                        <div class="row row-mt-15em">
                            <div class="col-md-7 mt-text animate-box" data-animate-effect="fadeInUp">
                                <span class="intro-text-small">Welcome to Splash</span>
                                <h1>Build website using this template.</h1>
                            </div>
                            <div class="col-md-4 col-md-push-1 animate-box" data-animate-effect="fadeInRight">
                                <div class="form-wrap">
                                    <div class="tab">
                                        <ul class="tab-menu">
                                            <li class="gtco-second"><a href="#" data-tab="signup">注册</a></li>
                                            <li class="active gtco-first"><a href="#" data-tab="login">登录</a></li>
                                        </ul>
                                        <div class="tab-content">
                                            <div class="tab-content-inner" data-content="signup">
                                                <form action="#">
                                                    <div class="row form-group">
                                                        <div class="col-md-12">
                                                            <label for="username">用户名</label>
                                                            <input type="text" class="form-control" >
                                                        </div>
                                                    </div>
                                                    <div class="row form-group">
                                                        <div class="col-md-12">
                                                            <label for="password">密&nbsp;&nbsp;码</label>
                                                            <input type="password" class="form-control" >
                                                        </div>
                                                    </div>
                                                    <div class="row form-group">
                                                        <div class="col-md-12">
                                                            <label for="password2">重复密码</label>
                                                            <input type="password" class="form-control" id="password2">
                                                        </div>
                                                    </div>

                                                    <div class="row form-group">
                                                        <div class="col-md-12">
                                                            <input type="submit" class="btn btn-primary" value="注册">
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>

                                            <div class="tab-content-inner active" data-content="login">
                                                <form action="UserInfoServlet" method="post">
                                                    <div class="row form-group">
                                                        <div class="col-md-12">
                                                            <label for="username">用户名</label>
                                                            <input type="text" class="form-control username" id="username" name="username"><br>
                                                            <text class="error1" style="color: red"></text>
                                                        </div>
                                                    </div>
                                                    <div class="row form-group">
                                                        <div class="col-md-12">
                                                            <label for="password">密&nbsp;&nbsp;码</label>
                                                            <input type="password" class="form-control password" id="password" name="password"><br>
                                                            <text class="error2" style="color: red"></text>
                                                        </div>
                                                    </div>

                                                    <div class="row form-group">
                                                        <div class="col-md-12">
                                                            <input type="button" class="btn btn-primary submit" value="登录">
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </header>
    </div>

</div>

<div class="gototop js-top">
    <a href="#" class="js-gotop"><i class="icon-arrow-up"></i></a>
</div>

<script type="text/javascript">

    //jquery的class选择器，当用户点击“登录”按钮时触发
    $(".submit").click(function(){
        //得到用户输入的用户名和密码
        var username = $(".username").val();
        var password = $(".password").val();
        //异步通信，利用ajax传数据到后台（LoginServlet）
        $.ajax({
            url:"LoginServlet?username="+username+"&password="+password
        }).done(function(data){//当处理完成后，返回一个json数据，可以看LoginServlet文件源码，以便知道返回的json数据格式
            var json = data;
            //解析json数据
            var obj = JSON.parse(json,function(name,value){//键值对的方式
                if(name=="userNameError"){
                    //用户名错误
                    $(".error1").text(value)
                }
                else if(name=="userPasswordError"){
                    //密码错误
                    $(".error2").text(value);
                }
                else {
                    //没有错误，就可以正常登陆
                    if(value=="true"){
                        $("form").submit();
                    }
                }
                return value;
            });
        })
    })
</script>

</body>
</html>
