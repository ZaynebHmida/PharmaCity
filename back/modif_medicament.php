<?php
include 'config.php';
$id=$_GET['id'];
?>
<!doctype html>
<html class="no-js" lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title> ModularAdmin - Free Dashboard Theme | HTML Version </title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="apple-touch-icon" href="apple-touch-icon.png">
        <!-- Place favicon.ico in the root directory -->
        <link rel="stylesheet" href="css/vendor.css">
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
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    </head>

    <body>
        <div class="main-wrapper">
            <div class="app" id="app">
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
               
			  </header>
<?php
include 'menu.php'

?>             

   <div class="sidebar-overlay" id="sidebar-overlay"></div>
                <article class="content responsive-tables-page">
                   
                    <section class="section">
                        <div class="row">
                            <div class="col-md-12">
                                 
                               <div class="card card-block sameheight-item">
                                    <div class="title-block">
 <p><h2><p><font color="fuchsia">modification d'un Médicament</font></p> </h2></p> 
                                    </div>
                <div class="row">
          <div class="col-md-12">
          
         
       
              
					
              <div class="card-body">
              <?php echo' <form action="modif_medicamentTrt.php?id='.$id.'" method="POST" enctype="multipart/form-data">';?>
                 
				  <br>   
				  <?php
$sql1="select * from medicament where id_med='$id'";
$medicaments=$con->query($sql1);
while($medicament=$medicaments->fetch_assoc()){
$nom=$medicament['nom_med'];
$nom1=$medicament['ref'];
$nom2=$medicament['lib'];
$nom3=$medicament['forme'];
$nom4=$medicament['prix'];
$type=$medicament['type'];
$ord=$medicament['nedd_ord'];
$image=$medicament['image'];

}
				  ?>

				  
				  <label class="control-label">Nom de Médicament &nbsp &nbsp &nbsp</label>
               <?php  echo'   <input class="form-control" name="nom_med" type="text" placeholder="Enter nom_med" value="'.$nom.'"  required>';?>
                  
				    <br>
				  <label class="control-label">Référence&nbsp</label>
                    <?php  echo'   <input class="form-control" name="ref" type="text" placeholder="Enter ref" value="'.$nom1.'"  required>';?>
                
				    <br> 
				  <div class="form-group">
                    <label class="control-label">Libelle&nbsp&nbsp&nbsp&nbsp&nbsp &nbsp &nbsp</label>
                  <?php  echo'   <input class="form-control" name="lib" type="text" placeholder="Enter lib" value="'.$nom2.'"  required>';?>
                  </div> 
				    <br> 
				  <div class="form-group">
                    <label class="control-label">forme &nbsp</label>
                   <?php  echo'   <input class="form-control" name="forme" type="text" placeholder="Enter forme" value="'.$nom3.'"  required>';?>
                  </div> 
				    <br>  
				  <div class="form-group">
                    <label class="control-label">prix</label>
                    <?php  echo'   <input class="form-control" name="prix" type="text" placeholder="Enter prix" value="'.$nom4.'"  required>';?>
                  </div> 
				    <br>  
<?php

if($type==0){
                                echo'<div class="form-group"> <label class="control-label">type</label></br>
                                                       <INPUT type= "radio" name="type" value=1/> Régulier
                                                        <INPUT type= "radio" name="type" value=0 checked/> Irrégulier

                                         </div>';}
                                         else{
 echo'<div class="form-group"> <label class="control-label">type</label></br>
                                                       <INPUT type= "radio" name="type" value=1 checked/> Régulier
                                                        <INPUT type= "radio" name="type" value=0 /> Irrégulier

                                         </div>';


                                         }
                                         
                                         if($ord==0){

                                        echo' <div class="form-group"> <label class="control-label">Avec ordonnace?</label></br>
                                                       <INPUT type= "radio" name="ord" value=1/> Oui
                                                        <INPUT type= "radio" name="ord" value=0 checked/> Non

                                         </div>';}
                                         else{

echo' <div class="form-group"> <label class="control-label">Avec ordonnace?</label></br>
                                                       <INPUT type= "radio" name="ord" value=1 checked/> Oui
                                                        <INPUT type= "radio" name="ord" value=0 /> Non

                                         </div>';

                                         }
                                         ?>
				  	<div class="col-sm-10">
                                    <div class="images-container">
                                        
                                       
                                        
                                        
  <input type="file" name="image" accept="image/*" value="<?php echo $image ?>">
  

                                    </div>
                                </div>
				    
                    <input type="submit" class="btn btn-primary icon-btn" value="Valider"/>
                
                  
                </form>
				
              </div>
        </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  
    <!--//header-->    
     
    <!--page_container-->
    <div class="page_container">

    <!-- Javascripts-->
    <script src="js/jquery-2.1.4.min.js"></script>
    <script src="js/essential-plugins.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/plugins/pace.min.js"></script>
    <script src="js/main.js"></script>
  </body>
</html>






