<?php
include 'config.php';
$id=$_GET['id'];
				if($_SERVER['REQUEST_METHOD']=='POST'){

				$nom_med=$_POST['nom_med'];
				$ref=$_POST['ref'];
				$lib=$_POST['lib'];
				$forme=$_POST['forme'];
				$prix=$_POST['prix'];
				$image=$_FILES['image']['name'];
				$type=$_POST['type'];
				$ord=$_POST['ord'];
  $dossier="img/";

        move_uploaded_file($_FILES["image"]["tmp_name"], "$dossier".$image);
				$sql="UPDATE `pharmacity`.`medicament` SET `nom_med` = '  $nom_med', `ref` = '$ref', `lib` = '$lib', `forme` = '$forme', `prix` = '$prix', `image` = '$image', `type` = '$type',`need_ord` = '$ord' WHERE `medicament`.`id_med` = '$id';";
				$res=$con->query($sql);
				if($res==TRUE){
				header("location:liste_medicaments.php");
				}
				

				}
				?>