	<?php
	include 'config.php';
	        $id=$_GET['id'];

			

				$sql="delete from medicament  WHERE id_med = '$id';" ;
				$res=$con->query($sql);
				if($res==TRUE){
					header("location:liste_medicaments.php");
				}
				

				
				?>