	<?php
	include 'config.php';
	        $id=$_GET['id'];

			

				$sql="delete from message  WHERE id_msg = '$id';" ;
				
				$res=$con->query($sql);
				if($res==TRUE){
					header("location:liste_messages.php");
				
				}
				

				
				?>