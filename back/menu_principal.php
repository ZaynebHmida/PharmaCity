<?php
include 'config.php';
                   //  $id=$_GET['id'];

?>
<!doctype html>
<html class="no-js" lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
       
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="apple-touch-icon" href="apple-touch-icon.png">
        <!-- Place favicon.ico in the root directory -->
        <link rel="stylesheet" href="css/vendor.css">
		 <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
   

    <!-- BOOTSTRAP STYLES-->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
    <!-- FONTAWESOME STYLES-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
       <!--CUSTOM BASIC STYLES-->
    <link href="assets/css/basic.css" rel="stylesheet" />
    <!--CUSTOM MAIN STYLES-->
    <link href="assets/css/custom.css" rel="stylesheet" />
    <!-- GOOGLE FONTS-->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
		
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800' rel='stylesheet' type='text/css'>

<link href="css/prettyPhoto.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" id="camera-css"  href="css/camera.css" type="text/css" media="all">
<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/theme.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/skins/tango/skin.css" />
<link href="css/bootstrap-responsive.css" rel="stylesheet">
        <!-- Theme initialization -->
        <script>
            var themeSettings = (localStorage.getItem('themeSettings')) ? JSON.parse(localStorage.getItem('themeSettings')) :
            {};
            var themeName = themeSettings.themeName || '';
            if (themeName)
            {
                document.write('<link rel="stylesheet" id="theme-style" href="css/app-' + themeName + '.css">');
            }
            else
            {
                document.write('<link rel="stylesheet" id="theme-style" href="css/app.css">');
            }
        </script>
    </head>

    <body
	style="background: url(img/index/1.jpg) no-repeat center center fixed; -webkit-background-size: cover;
     -moz-background-size: cover;
     -o-background-size: cover;
      background-size: cover;">
	  
	  
       
           
                <header class="header">
                    <div class="header-block header-block-collapse hidden-lg-up"> <button class="collapse-btn" id="sidebar-collapse-btn">
    			<i class="fa fa-bars"></i>
    		</button> </div>
                    <div class="header-block header-block-search hidden-sm-down">
                      
                    </div>
                    <?php
$sql="select count(id_msg) from message where er='ca' and lu=0";
$res=$con->query($sql);
$sql1="select * from message M, client C  where M.idCl=C.idCl and  er='ca' and lu=0";

$res1=$con->query($sql1);

$mess=$res->fetch_assoc();

                    ?>
                    <div class="header-block header-block-nav">
                        <ul class="nav-profile">
                            <li class="notifications new">
                                <a href="" data-toggle="dropdown"> <i class="fa fa-envelope-o"></i> <sup>
                      <span class="counter"><?php echo $mess['count(id_msg)'] ?></span>
                    </sup> </a>
                                <div class="dropdown-menu notifications-dropdown-menu">
                                    <ul class="notifications-container">
<?php                                       
while($mess1=$res1->fetch_assoc()){
                                  echo'      <li>
                                            <a href="" class="notification-item">
                                                <div class="img-col">
                                                    <div class="img"></div>
                                                </div>
                                                <div class="body-col">
                                                    <p> <span class="accent">'.$mess1['nomCl'].' '.$mess1['prenomCl'].'</span>'.$mess1['sujet'].'</span>. </p>
                                                </div>
                                            </a>
                                        </li>';}
                                 ?>
                                    </ul>
                                    <footer>
                                        <ul>
                                            <li> <a href="liste_messages.php">
                            tous
                          </a> </li>
                                        </ul>
                                    </footer>
                                </div>
                            </li>
                            <li class="profile dropdown">
                                <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
                                     <span class="name">
                     
                    </span> </a>
                                <div class="dropdown-menu profile-dropdown-menu" aria-labelledby="dropdownMenu1">
                                    
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item" href="index.php"> <i class="fa fa-power-off icon"></i> Logout </a>
                                </div>
                            </li>
                        </ul>
                    </div>
               
			  </header></br></br></br></br></br>
			  
			  
			  
			   <!-- /. NAV SIDE  -->
        <div id="page-wrapper">
            <div id="page-inner">
                <div class="row">
                    <div class="col-md-12">
                       

                    </div>
                </div>
               
                <div class="row">
                    <div class="col-md-8">
                        <div class="row">
                            <div class="col-md-12">

                                <div id="reviews" class="carousel slide" data-ride="carousel">

                                    <div class="carousel-inner">
                                        <div class="item active">

                                            <div class="col-md-10 col-md-offset-1">

                                                <h4><i class="fa fa-quote-left"></i> Dans cette tache,le super-admin a le droit d''ajouter un admin ou de consulter la liste des admins existants. <i class="fa fa-quote-right"></i></h4>
                                                <div class="user-img pull-right">
                                                    <img src="assets/img/1.gif" alt="" class="img-u image-responsive" />
                                                </div>
                                                <h5 class="pull-right"><strong class="c-black">Administrateur</strong></h5>
                                            </div>
                                        </div>
                                        <div class="item">
                                            <div class="col-md-10 col-md-offset-1">

                                                <h4><i class="fa fa-quote-left"></i> Dans cette tache,l'admin peut ajouter un médicament ou consulter la liste des médicaments existants. <i class="fa fa-quote-right"></i></h4>
                                                <div class="user-img pull-right">
                                                    <img src="assets/img/2.gif" alt="" class="img-u image-responsive" />
                                                </div>
                                                <h5 class="pull-right"><strong class="c-black">Médicament</strong></h5>
                                            </div>

                                        </div>
                                        <div class="item">
                                            <div class="col-md-10 col-md-offset-1">

                                                <h4><i class="fa fa-quote-left"></i> Cette tache permet à l'admin à ajouter un diagnostic approximatif qui sera affiché au client ou de consulter la liste des diagnostics existants. <i class="fa fa-quote-right"></i></h4>
                                                <div class="user-img pull-right">
                                                    <img src="assets/img/3.gif" alt="" class="img-u image-responsive" />
                                                </div>
                                                <h5 class="pull-right"><strong class="c-black">Diagnostic</strong></h5>
                                            </div>
                                        </div>
										 <div class="item">
                                            <div class="col-md-10 col-md-offset-1">

                                                <h4><i class="fa fa-quote-left"></i> Cette tache permet à l'admin à  consulter la liste des clients. <i class="fa fa-quote-right"></i></h4>
                                                <div class="user-img pull-right">
                                                    <img src="assets/img/4.gif" alt="" class="img-u image-responsive" />
                                                </div>
                                                <h5 class="pull-right"><strong class="c-black">Clients</strong></h5>
                                            </div>
                                        </div>
										 <div class="item">
                                            <div class="col-md-10 col-md-offset-1">

                                                <h4><i class="fa fa-quote-left"></i> Cette tache permet à l'admin à  consulter la liste des commandes effectuées par les clients. <i class="fa fa-quote-right"></i></h4>
                                                <div class="user-img pull-right">
                                                    <img src="assets/img/5.gif" alt="" class="img-u image-responsive" />
                                                </div>
                                                <h5 class="pull-right"><strong class="c-black">Commandes</strong></h5>
                                            </div>
                                        </div>
                                    </div>
                                    <!--INDICATORS-->
                                    
                                    <!--PREVIUS-NEXT BUTTONS-->

                                </div>

                            </div>

                        </div>
                        <!-- /. ROW  -->
                        <hr />

                        <div class="panel panel-default">

                            <div id="carousel-example" class="carousel slide" data-ride="carousel" style="border: 5px solid #000;">

                                <div class="carousel-inner">
                                    <div class="item active">

                                        <img src="assets/img/slideshow/a.jpg" alt="" />

                                    </div>
                                    <div class="item">
                                        <img src="assets/img/slideshow/b.jpg" alt="" />

                                    </div>
                                    <div class="item">
                                        <img src="assets/img/slideshow/c.jpg" alt="" />

                                    </div>
									 <div class="item">
                                        <img src="assets/img/slideshow/d.jpg" alt="" />

                                    </div>
									 <div class="item">
                                        <img src="assets/img/slideshow/e.jpg" alt="" />

                                    </div>
									 <div class="item">
                                        <img src="assets/img/slideshow/f.jpg" alt="" />

                                    </div>
									 <div class="item">
                                        <img src="assets/img/slideshow/g.jpg" alt="" />

                                    </div>
                                </div>
                                <!--INDICATORS-->
                               
                                <!--PREVIUS-NEXT BUTTONS-->
                                
                            </div>
                        </div>
                    </div>
                    <!-- /.REVIEWS &  SLIDESHOW  -->
                    <div class="col-md-4">

                        <div class="panel panel-default">
                            
                            <div class="panel-body" style="padding: 0px;">
                                <div class="chat-widget-main">


                                    <div class="chat-widget-left">
                                        L’application PharmaCity englobe plusieurs services .
                                        Elle présente une interface complètement intuitive, vous permettant de sélectionner vos médicaments et produits en seulement quelques clics.
                                        Elle se positionne en tant que pionnier dans la recherche et le développement de l’ordonnance numérique. Vous envoyez la photo de votre ordonnance puis vous recevrez immédiatement  une alerte sur votre téléphone de sa préparation et disponibilité, ensuite elle sera livrée à votre domicile. 
                                        Cette dernière offre à l’individu l’opportunité d'effectuer une commande de médicament en ligne ainsi que savoir les prix et d'autres informations sur n'importe quel médicament.
                                        Le client  peut recevoir  des alertes pour ses rappels de prises de médicaments pour qu'il n'oublie pas.
                                        Cette application permet au client  d'envoyer la photo d'ordonnance au pharmacien et d'effectuer une commande des médicaments existants dans cette ordonnance. 
                                        En outre, notre application offre l'opportunité de consulter des statistiques sur les médicaments les plus vendues et plus efficaces d'après l'avis d'autres utilisateurs. Enfin, et comme nouvelle initiative, on a proposé un nouveau service qui donne au client des diagnostics approximatifs en se basant sur ses propres symptômes.

                                    </div>
                                    <div class="chat-widget-name-left">
                                        <h4>PharmaCity</h4>
                                    </div>
                                    
                                </div>
                            </div>
                           
                        </div>


                    </div>
                    <!--/.Chat Panel End-->
                </div>
                <!-- /. ROW  -->


                <div class="row">

                   
                    
                </div>
               

               
                <!--/.ROW-->

            </div>
            <!-- /. PAGE INNER  -->
        </div>
        <!-- /. PAGE WRAPPER  -->
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
<?php
include 'menu.php'

?>             

  
                
              
                
     
        <!-- Reference block for JS -->
        <div class="ref" id="ref">
            <div class="color-primary"></div>
            <div class="chart">
                <div class="color-primary"></div>
                <div class="color-secondary"></div>
            </div>
        </div>
		
		
		
		
		
		
		
		
		
		
		
        <script>
            (function(i, s, o, g, r, a, m)
            {
                i['GoogleAnalyticsObject'] = r;
                i[r] = i[r] || function()
                {
                    (i[r].q = i[r].q || []).push(arguments)
                }, i[r].l = 1 * new Date();
                a = s.createElement(o),
                    m = s.getElementsByTagName(o)[0];
                a.async = 1;
                a.src = g;
                m.parentNode.insertBefore(a, m)
            })(window, document, 'script', 'https://www.google-analytics.com/analytics.js', 'ga');
            ga('create', 'UA-80463319-2', 'auto');
            ga('send', 'pageview');
        </script>
		
		
		
		
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script type="text/javascript" src="js/jquery.easing.1.3.js"></script>
    <script type="text/javascript" src="js/jquery.mobile.customized.min.js"></script>
    <script type="text/javascript" src="js/camera.js"></script>
    <script src="js/bootstrap.js"></script>
    <script src="js/superfish.js"></script>
    <script type="text/javascript" src="js/jquery.prettyPhoto.js"></script>
    <script type="text/javascript" src="js/jquery.tweet.js"></script>
    <script type="text/javascript" src="js/myscript.js"></script>
		
		
		
		
		
		
		
		
		
        <script src="js/vendor.js"></script>
        <script src="js/app.js"></script>
		
     

 <script type="text/javascript">
		$(document).ready(function(){	
			//Slider
			$('#camera_wrap_1').camera();
			
			//Featured works & latest posts
			$('#mycarousel, #mycarousel2, #newscarousel').jcarousel();													
		});		
	</script>

	
	
	
	
	
	
        <script>
            (function(i, s, o, g, r, a, m)
            {
                i['GoogleAnalyticsObject'] = r;
                i[r] = i[r] || function()
                {
                    (i[r].q = i[r].q || []).push(arguments)
                }, i[r].l = 1 * new Date();
                a = s.createElement(o),
                    m = s.getElementsByTagName(o)[0];
                a.async = 1;
                a.src = g;
                m.parentNode.insertBefore(a, m)
            })(window, document, 'script', 'https://www.google-analytics.com/analytics.js', 'ga');
            ga('create', 'UA-80463319-2', 'auto');
            ga('send', 'pageview');
        </script>
		
		
		
		
		
		
		
		
		 <script src="js/vendor.js"></script>
		 <script src="js/app.js"></script>
		
		 <script src="assets/js/bootstrap.js"></script>
		   <script src="assets/js/jquery.metisMenu.js"></script>
		     <script src="assets/js/custom.js"></script>




    </body>

</html>






