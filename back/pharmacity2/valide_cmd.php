		<?php

    $con=new mysqli("localhost","root","","PharmaCity");
    $id=$_GET['id'];
$sql="update commande set etat=1 where idCm='$id=' ";
$commandes=$con->query($sql);
if($commandes==TRUE){
$sql1="SELECT * FROM `lignecmd` where idC='$id'";
$res1=$con->query($sql1);
while($med=$res1->fetch_assoc()){
  $idmed=$med['idmed'];
  $qt=$med['qt'];
  $sql2="select * from medicament where id_med='$idmed'";
$res=$con->query($sql2);
while($medicament=$res->fetch_assoc()){$stock=$medicament['qt'];}
$nvqt=$stock-$qt;
  $sql3="update medicament set qt='$nvqt' where id_med='$idmed'";
$con->query($sql3);

}
header('location:liste_commandes.php');

}