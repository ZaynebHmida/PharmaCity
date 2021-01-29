<?php
			 include 'config.php';
			 
			 
			 
			 

			if($_SERVER['REQUEST_METHOD']=='POST'){
			
						$symptome=$_POST['sym'];
						$id_med=$_POST['med'];
				
				
						$sql="insert into diagnostic(id_sym) values('$symptome');";
						$res=$con->query($sql);
						if($res==TRUE){
							$idD=$con->insert_id;
							foreach($_POST['med'] as $valeur){
								$sql="insert into diag_Medicament(idD,idM) values('$idD','$valeur');";
								$res=$con->query($sql);
								 header("Refresh:0; url=liste_diagnostics.php");
							}
						}
					}
				?>