	<?php
	include 'config.php';
	        $id=$_GET['id'];

			

				$sql="delete from pharmacien  WHERE idP = '$id';" ;
				
				$res=$con->query($sql);
				if($res==TRUE){
					header("location:liste_administrateurs.php");
				
				}
				

				
				?>