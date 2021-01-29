<?php
include 'config.php';
?>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- CSS-->
    <link rel="stylesheet" type="text/css" href="css/main.css">
	<link href="css/menus.css" rel="stylesheet" type="text/css" />

  
  </head>
  <Body>
  <!--header-->
    <div class="header">
    	<div class="wrap">
        	<div class="navbar navbar_ clearfix">
            	<div class="container">
                    <div class="row">
                        <div class="span4">
                        	<div class="logo"><a href="index.html"><img src="img/logo.png" alt="" /></a></div>                        
                        </div>
                        <?php
										include 'menu.php';
									?>
                </div>
             </div>
        </div>    
    </div>
	
    <!--//header-->    
     
    <!--page_container-->
    <div class="page_container">
	 <!--planning-->
        <div class="wrap planning">
            <div class="container">
                <div class="row">
                    
                       
                    </div>                           	
                </div>
            </div>
        </div>
        <!--//planning-->
		
  
  <!--header-->
    <div class="header">
    	<div class="wrap">
        	<div class="navbar navbar_ clearfix">
            	<div class="container">
                    <div class="row">
                        <div class="span4">
                        	                       
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
     </Body>
    <!-- CSS-->
    <link rel="stylesheet" type="text/css" href="css/main.css">
   
            
           <p><h2><p><font color="fuchsia">Liste des Client</font></p> </h2></p>
          </div> 
        </div>
        <div class="row">
          <div class="col-md-12">
          
         
       
              
					
  <body class="sidebar-mini fixed">
    <div class="wrapper">
      
              </li>
            </ul>
          </div>
        </nav>
      </header>
      <!-- Side-Nav-->
      
         
           
          </div> 
        </div>
        
              <table class="table">
                <thead>
				
				
				
				
				<table border=2> 
   <tr>
   
      <th>Date</th>
      <th>idCl</th>
      <th>nomCl</th>
	  <th>prenomCl</th>
	    <th>ad</th>
	  <th>Action</th>
   </tr>
   <?php
				$con=new mysqli("localhost","root","","PharmaCity");
$sql="select * from client";
$clients=$con->query($sql);
if($clients->num_rows>0){
while($client=$clients->fetch_assoc()){
                 echo'<tr class="info">
                    <td>2012-12-12</td>
                    <td>'.$client['idCl'].'</td>
                    <td>'.$client['nomCl'].'</td>
					<td>'.$client['prenomCl'].'</td>
					   <td>'.$client['ad'].'</td>
					
                    <td><a href="modif_client.php?id='.$client['idCl'].'"><button class="btn btn-warning icon-btn" type="button"><i class="fa fa-fw fa-lg fa-check-circle"></i>Modifier</button></a>
					 &nbsp;&nbsp;<a class="btn btn-danger icon-btn" href="suppr_client.php?id='.$client['idCl'].'"><i class="fa fa-fw fa-lg fa-times-circle"></i>Supprimer</a> <br><br>
					</td>
                  </tr>';
} }else {echo'<td colspan="6"> tableau vide</td>';}?>

</table>
                 
                </thead>
                <tbody>
				
                </tbody>
              </table>
			  </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Javascripts-->
    <script src="js/jquery-2.1.4.min.js"></script>
    <script src="js/essential-plugins.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/plugins/pace.min.js"></script>
    <script src="js/main.js"></script>
  </body>
</html>