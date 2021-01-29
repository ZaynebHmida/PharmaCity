	<?php
	include 'config.php';
	        $id=$_GET['id'];

			

				$sql="delete from diagnostic  WHERE idD = '$id';" ;
				$res=$con->query($sql);
				if($res==TRUE){
					header("location:liste_diagnostics.php");
				}
				

				
				?>