       <?php
       include 'config.php';

                if(isset($_POST['ajout']))
                {

                $login=$_POST['login'];
                $pw=$_POST['pw'];
                $hashpw=SHA1($pw);
        

                             $sql="insert into pharmacien(login,pw,role) values('$login','$hashpw','aide');";
                             $res=$con->query($sql);
                            if($res==TRUE){
                                header("Refresh:0; url=liste_administrateurs.php");}
							else
								header("Refresh:0; url=ajout_administrateur.php");

                

                }
                ?>
