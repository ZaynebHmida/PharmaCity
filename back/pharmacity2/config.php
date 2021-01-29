<?php
session_start();
if(isset($_SESSION['admin'])==FALSE){
	
	header("location:index.php");
}
$con=new mysqli("localhost","root","","pharmacity");

?>
