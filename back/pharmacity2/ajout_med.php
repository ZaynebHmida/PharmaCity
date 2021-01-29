     <?php
       include 'config.php';

                if(isset($_POST['ajout']))
                {

                $nom_med=$_POST['nom_med'];
				$ref=$_POST['ref'];
				$lib_POST['lib'];
				$forme=$_POST['forme'];
				$prix=$_POST['prix'];
				$image=$_FILES['image']['name'];
			    $qt=$_POST['qt'];
		 		$type=$_POST['type'];
                                $ord=$_POST['ord'];

               $dossier="img/";

        move_uploaded_file($_FILES["image"]["tmp_name"], "$dossier".$image);


                             $sql="insert into medicament(nom_med,ref,lib,forme,prix,image,qt,type,need_ord) values('$nom_med','$ref','$lib','$forme','$prix','$image','$qt','$type','$ord');";
                             $res=$con->query($sql);
                            if($res==TRUE){
                                header("Refresh:0; url=liste_medicaments.php");}
							
                

                }
                ?>
