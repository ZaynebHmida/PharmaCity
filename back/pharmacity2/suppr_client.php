	<?php
	include 'config.php';
	        $idcl=$_GET['id'];

			

				$sql="delete from client  WHERE idCl = '$idcl';" ;
				$res=$con->query($sql);
				if($res==TRUE){
					header("location:liste_clients.php");
				}
				

				
				?>