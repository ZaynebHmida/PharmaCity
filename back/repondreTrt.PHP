<?php
include 'config.php';
$idCl=$_GET['id'];
$reponse= $_POST['reponse'];
$sujet="REP:".$_POST['sujet'];
echo $sujet;
$sql="INSERT INTO `pharmacity`.`message` (`date`, `sujet`, `message`, `idCl`, `er`, `lu`) VALUES (now(), '$sujet', '$reponse', '$idCl', 'ac', '0');";
$res=$con->query($sql);
if($res==TRUE){

	header('location:liste_messages.php');
}
