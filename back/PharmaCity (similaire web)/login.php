<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Creative - Bootstrap 3 Responsive Admin Template">
    <meta name="author" content="GeeksLabs">
    <meta name="keyword" content="Creative, Dashboard, Admin, Template, Theme, Bootstrap, Responsive, Retina, Minimal">
    <link rel="shortcut icon" href="img/favicon.png">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	

    <!-- CSS-->
      <!-- CSS-->
    <link rel="stylesheet" type="text/css" href="css/main.css">

    <title>PharmaCity</title>

    <!-- Bootstrap CSS -->    
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- bootstrap theme -->
    <link href="css/bootstrap-theme.css" rel="stylesheet">
    <!--external css-->
    <!-- font icon -->
    <link href="css/elegant-icons-style.css" rel="stylesheet" />
    <link href="css/font-awesome.css" rel="stylesheet" />
    <!-- Custom styles -->
    <link href="css/style.css" rel="stylesheet">
    <link href="css/style-responsive.css" rel="stylesheet" />

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
</head>

  <body class="login-img3-body">

    <div class="container">

      <form class="login-form" action="login.php" method="POST">        
        <div class="login-wrap">
            <p class="login-img"><i class="icon_lock_alt"></i></p>
            <div class="form-group">
            <label class="control-label"><center><FONT size="5pt">Nom d'utilisateur</FONT></center></label><br>
            <center><input class="form-control" type="text" placeholder="login" autofocus required name="login"></center><br>
          </div>
           <div class="form-group">
            <label class="control-label"><center><FONT size="5pt"> Mot de Passe</FONT></center></label><br>
            <center><input class="form-control" type="password" placeholder="Password" required name="password"></center><br>
          </div>
              <button class="btn btn-primary btn-lg btn-block" type="submit">Login</button>
              <button class="btn btn-info btn-lg btn-block" type="submit"> <a href="sign_up.php">Signup</a></button>
        </div>
      </form>
   
    </div>


  </body>
  <script src="js/jquery-2.1.4.min.js"></script>
  <script src="js/essential-plugins.js"></script>
  <script src="js/bootstrap.min.js"></script>
  <script src="js/plugins/pace.min.js"></script>
  <script src="js/main.js"></script>
</html>
<?php



session_start();
$con=new mysqli("localhost","root","","pharmacity");
if($_SERVER['REQUEST_METHOD']=='POST'){
$l=$_POST['login'];
$p=$_POST['pw'];
$sql="select * from client where login='$l' and pw='$p'";
$res=$con->query($sql);
if($res->num_rows>0)
{
	
	$_SESSION['admin']=$l;
	header('location:login.php');
}

}

?>
