<?php
include 'config.php';
                        $id=$_GET['id'];

        if($_SERVER['REQUEST_METHOD']=='POST'){
        $login=$_POST['login'];
        $pw=$_POST['pw'];
        

        $sql="UPDATE `pharmacity`.`pharmacien` SET `login` = '$login', `pw` = '$pw' WHERE `pharmacien`.`idP` ='$id';
 ";
        $res=$con->query($sql);
        if($res==TRUE){
          header("location:liste_administrateurs.php");
        }
        

        }
        ?>