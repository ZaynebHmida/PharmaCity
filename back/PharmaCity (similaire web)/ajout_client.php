<?php
       include 'config.php';

                if(isset($_POST['ajout']))
                {

                $login=$_POST['login'];
                $pw=$_POST['pw'];
                $hashpw=SHA1($pw);
				  $pw=$_POST['pw']; 
                  $hashpw2=SHA2($pw);				  
				    $CIN=$_POST['CIN'];
					  $tel=$_POST['tel'];
					    $ad=$_POST['ad'];
						  $mail=$_POST['mail'];
						    $nomCl=$_POST['nomCl'];
							  $prenomCl=$_POST['prenomCl'];
        

                             $sql="insert into client(login,pw,CIN,tel,ad,mail,nomCl,prenomCl) values('$login','$hashpw','CIN','tel','ad','mail','nomCl','prenomCl');";
                             $res=$con->query($sql);
                            if($res==TRUE){
                                header("Refresh:0; url=index.php.php");}
							else
								header("Refresh:0; url=sign_up.php.php");

                

                }
                ?>