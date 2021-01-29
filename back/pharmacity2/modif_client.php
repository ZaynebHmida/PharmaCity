<?php
include 'config.ph';
        $idcl=$_GET['id'];
?>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
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
   
            
           <p><h2><p><font color="fuchsia">modification d'un Client</font></p> </h2></p>
          </div> 
        </div>
        <div class="row">
          <div class="col-md-12">
          
         
       
              
					
              <div class="card-body">
              <?php echo' <form action="modif_client.php?id='.$idcl.'" method="POST">';?>
                 
				  <br>   
				  <?php
				  								$con=new mysqli("localhost","root","","PharmaCity");
												$id=$_GET['id'];
$sql="select * from client where idCl='$id'";
$clients=$con->query($sql);
if($clients->num_rows>0){
while($client=$clients->fetch_assoc()){
$nom=$client['nomCl'];}}
				  ?>

				  
				  <label class="control-label">nomCl &nbsp &nbsp &nbsp</label>
               <?php  echo'   <input class="form-control" name="nomCl" type="text" placeholder="Enter nomCl" value="'.$nom.'"  required>';?>
                  </div> 
				    <br>
				  <label class="control-label">prenomCl&nbsp</label>
                                  <?php  echo'   <input class="form-control" name="nomCl" type="text" placeholder="Enter prenomCl" value="'.$nom.'"  required>';?>
                  </div> 
				    <br> 
				  <div class="form-group">
                    <label class="control-label">login&nbsp&nbsp&nbsp&nbsp&nbsp &nbsp &nbsp</label>
                    <input class="form-control" name="login" type="text" placeholder="Enter login" required>
                  </div> 
				    <br> 
				  <div class="form-group">
                    <label class="control-label">password &nbsp</label>
                    <input class="form-control" name="password" type="text" placeholder="Enter password" required>
                  </div> 
				    <br>  
				  <div class="form-group">
                    <label class="control-label">date_naiss</label>
                    <input class="form-control" name="date_naiss" type="text" placeholder="Enter date_naiss" required>
                  </div> 
				    <br>  
				  <div class="form-group">
                    <label class="control-label">CIN&nbsp&nbsp&nbsp&nbsp &nbsp&nbsp&nbsp&nbsp&nbsp</label>
                    <input class="form-control" name="CIN" type="text" placeholder="Enter CIN" required>
                  </div> 
				    <br>  
				   <div class="form-group">
                    <label class="control-label">num_tel&nbsp&nbsp&nbsp&nbsp</label>
                    <input class="form-control" name="num_tel" type="text" placeholder="Enter num_tel" required>
                  </div>
				    <br>  
				   <div class="form-group">
                    <label class="control-label">adresse&nbsp&nbsp&nbsp&nbsp&nbsp</label>
                    <input class="form-control" name="adresse" type="text" placeholder="Enter adresse" required>
                  </div>
				    <br>
				   <div class="form-group">
                    <label class="control-label">email&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</label>
                    <input class="form-control" name="email" type="text" placeholder="Enter email" required>
                  </div>
				  
				  <br>  
                    <input type="submit" class="btn btn-primary icon-btn" value="Valider"/>
                
                  
                </form>
				<?php
				if($_SERVER['REQUEST_METHOD']=='POST'){
				$idCl=$_POST['idCl'];
				$nomCl=$_POST['nomCl'];
				$prenomCl=$_POST['prenomCl'];
				$login=$_POST['login'];
        echo  $login;
				$pw=$_POST['pw'];
				$dn=$_POST['dn'];
				$CIN=$_POST['CIN'];
				$tel=$_POST['tel'];
				$ad=$_POST['ad'];
				$mail=$_POST['mail'];

				$sql="update client SET login = '$login', pw = '$pw', CIN = '$CIN', tel = '$tel', ad = '$ad', mail = '$mail', nomCl = '$nomCl', prenomCl = '$prenomCl' WHERE idCl = '$id';" ;
				$res=$con->query($sql);
				if($res==TRUE){
					header("location:liste_clients.php");
				}
				

				}
				?>
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