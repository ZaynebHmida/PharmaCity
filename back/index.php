<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	

    <!-- CSS-->
      <!-- CSS-->
    <link rel="stylesheet" type="text/css" href="css/main.css">

  
  </head>
  
  

 
 <!--header-->
 
    <div class="header">
    	<div class="wrap">
        	<div class="navbar navbar_ clearfix">
            	<div class="container">
                    <div class="row">
                        <div class="span4">
                        	<div class="logo"><a href="index.html"><img src="img/logo.jpg" alt="" /></a></div>       
                               							
                        </div>
                        <div class="span8">
                        	
                            <div class="clear"></div>
                                          
                        </div>
                    </div>                
                </div>
             </div>
        </div>    
    </div>
    <!--//header-->    
  
  <body style="background: url(img/index/1.jpg) no-repeat center center fixed; -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;">

    <section class="material-half-bg">
      <div class="cover"></div>
    </section>
    <section class="login-content">
      <div class="logo">
       
      </div>
      <div class="login-box">
	  <div style="padding:5px; width:300px; margin:auto; border:8px solid #67ab9f; background-color:#b3d8d2; -moz-border-radius:20px; -khtml-border-radius:20px; -webkit-border-radius:20px; border-radius:20px;">
        <form class="login-form" action="index.php" method="POST">
          <h1 class="login-head"><i class="fa fa-lg fa-fw fa-user"></i><h1><center><FONT size="10pt">Connexion</FONT></center></h1>
          <div class="form-group"><br><br>
            <label class="control-label"><center><FONT size="5pt">Nom d'utilisateur</FONT></center></label><br>
            <center><input class="form-control" type="text" placeholder="login" autofocus required name="login"></center><br>
          </div>
          <div class="form-group">
            <label class="control-label"><center><FONT size="5pt"> Mot de Passe</FONT></center></label><br>
            <center><input class="form-control" type="password" placeholder="Password" required name="password"></center><br>
          </div>
         
          <div class="form-group btn-container">
           <center> <button class="btn btn-primary btn-block" name="admin"><center>Se Connecter</center> </button></center>
          </div>
             
		  </div>
        </form>
        
      </div>
    </section>


	
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
$p=$_POST['password'];
$sql="select * from pharmacien where login='$l' and pw='$p'";
$res=$con->query($sql);
if($res->num_rows>0)
{
	
	$_SESSION['admin']=$l;
	header('location:menu_principal.php');
}

}

?>