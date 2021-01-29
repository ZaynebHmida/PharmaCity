<?php
include 'config.php';

?>
<!doctype html>
<html class="no-js" lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title> ModularAdmin - Free Dashboard Theme | HTML Version </title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="apple-touch-icon" href="apple-touch-icon.png">
        <!-- Place favicon.ico in the root directory -->
        <link rel="stylesheet" href="css/vendor.css">
        <!-- Theme initialization -->
        <script>
            var themeSettings = (localStorage.getItem('themeSettings')) ? JSON.parse(localStorage.getItem('themeSettings')) :
            {};
            var themeName = themeSettings.themeName || '';
            if (themeName)
            {
                document.write('<link rel="stylesheet" id="theme-style" href="css/app-' + themeName + '.css">');
            }
            else
            {
                document.write('<link rel="stylesheet" id="theme-style" href="css/app.css">');
            }
        </script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    </head>

    <body>
        <div class="main-wrapper">
            <div class="app" id="app">
               <header class="header">
                    <div class="header-block header-block-collapse hidden-lg-up"> <button class="collapse-btn" id="sidebar-collapse-btn">
    			<i class="fa fa-bars"></i>
    		</button> </div>
                    <div class="header-block header-block-search hidden-sm-down">
                      
                    </div>
                    <?php
$sql="select count(id_msg) from message where er='ca' and lu=0";
$res=$con->query($sql);
$sql1="select * from message M, client C  where M.idCl=C.idCl and  er='ca' and lu=0";

$res1=$con->query($sql1);

$mess=$res->fetch_assoc();

                    ?>
                    <div class="header-block header-block-nav">
                        <ul class="nav-profile">
                            <li class="notifications new">
                                <a href="" data-toggle="dropdown"> <i class="fa fa-envelope-o"></i> <sup>
                      <span class="counter"><?php echo $mess['count(id_msg)'] ?></span>
                    </sup> </a>
                                <div class="dropdown-menu notifications-dropdown-menu">
                                    <ul class="notifications-container">
<?php                                       
while($mess1=$res1->fetch_assoc()){
                                  echo'      <li>
                                            <a href="" class="notification-item">
                                                <div class="img-col">
                                                    <div class="img"></div>
                                                </div>
                                                <div class="body-col">
                                                    <p> <span class="accent">'.$mess1['nomCl'].' '.$mess1['prenomCl'].'</span>'.$mess1['sujet'].'</span>. </p>
                                                </div>
                                            </a>
                                        </li>';}
                                 ?>
                                    </ul>
                                    <footer>
                                        <ul>
                                            <li> <a href="liste_messages.php">
                            tous
                          </a> </li>
                                        </ul>
                                    </footer>
                                </div>
                            </li>
                            <li class="profile dropdown">
                                <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
                                     <span class="name">
                     
                    </span> </a>
                                <div class="dropdown-menu profile-dropdown-menu" aria-labelledby="dropdownMenu1">
                                    
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item" href="index.php"> <i class="fa fa-power-off icon"></i> Logout </a>
                                </div>
                            </li>
                        </ul>
                    </div>
               
			  </header>
<?php
include 'menu.php'

?>             

   <div class="sidebar-overlay" id="sidebar-overlay"></div>
                <article class="content responsive-tables-page">
                   
                    <section class="section">
                        <div class="row">
                            <div class="col-md-12">
                                 
                               <div class="card card-block sameheight-item">
                                    <div class="title-block">
                                        <h3 class="title"> Ajouter un Diagnostic approximatif </h3> </br> </br></br>
                                    </div>
                                      <div class="row">
          <div class="col-md-12">
          
         
       
              
					
              <div class="card-body">
			  
                <form action="ajout_diag.php" method="POST">
                  <div class="form-group">
<label class="control-label"><font color="olive">Symptomes</font>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</label><br>
<select name="sym">
<?php
        $con=new mysqli("localhost","root","","PharmaCity");
      
        $sql="select * from symptome ;";
        $res=$con->query($sql);
		
while($sym=$res->fetch_assoc()){ 
echo'<option value="'.$sym['id_sym'].'">'.$sym['nom'].'</option>';
 }?>
 </select>

<br>
<label class="control-label"><font color="olive">Medicaments</font></label>		
<br>  


                                  
									
									
									
									
									
<?php
				$con=new mysqli("localhost","root","","PharmaCity");
			
				$sql="select * from medicament ;";
				$res=$con->query($sql);
while($med=$res->fetch_assoc()){
  echo' <div> <label>
        <input class="checkbox" type="checkbox" name="med[]" value="'.$med['id_med'].'">
            <span>'.$med['nom_med'].'</span>
            </label> </div>';
				}?>
				
				



 <input type="submit" class="btn btn-primary icon-btn" value="Sauvegarder"/>
                     </form>
				
              </div>
			  </div>
            </div>
          </div>
        </div>
                                 





                                </div>
                                            

      
                                        
                                        </section>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
            
                </article>
                
                <div class="modal fade" id="modal-media">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header"> <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                        <span class="sr-only">Close</span>
                    </button>
                                <h4 class="modal-title">Media Library</h4>
                            </div>
                            <div class="modal-body modal-tab-container">
                                <ul class="nav nav-tabs modal-tabs" role="tablist">
                                    <li class="nav-item"> <a class="nav-link" href="#gallery" data-toggle="tab" role="tab">Gallery</a> </li>
                                    <li class="nav-item"> <a class="nav-link active" href="#upload" data-toggle="tab" role="tab">Upload</a> </li>
                                </ul>
                                <div class="tab-content modal-tab-content">
                                    <div class="tab-pane fade" id="gallery" role="tabpanel">
                                        <div class="images-container">
                                            <div class="row"> </div>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade active in" id="upload" role="tabpanel">
                                        <div class="upload-container">
                                            <div id="dropzone">
                                                <form action="/" method="POST" enctype="multipart/form-data" class="dropzone needsclick dz-clickable" id="demo-upload">
                                                    <div class="dz-message-block">
                                                        <div class="dz-message needsclick"> Drop files here or click to upload. </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer"> <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button> <button type="button" class="btn btn-primary">Insert Selected</button> </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
                <!-- /.modal -->
                <div class="modal fade" id="confirm-modal">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header"> <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                                <h4 class="modal-title"><i class="fa fa-warning"></i> Alert</h4>
                            </div>
                            <div class="modal-body">
                                <p>Are you sure want to do this?</p>
                            </div>
                            <div class="modal-footer"> <button type="button" class="btn btn-primary" data-dismiss="modal">Yes</button> <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button> </div>
                        </div>
                        <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                </div>
                <!-- /.modal -->
            </div>
        </div>
        <!-- Reference block for JS -->
        <div class="ref" id="ref">
            <div class="color-primary"></div>
            <div class="chart">
                <div class="color-primary"></div>
                <div class="color-secondary"></div>
            </div>
        </div>
        <script>
            (function(i, s, o, g, r, a, m)
            {
                i['GoogleAnalyticsObject'] = r;
                i[r] = i[r] || function()
                {
                    (i[r].q = i[r].q || []).push(arguments)
                }, i[r].l = 1 * new Date();
                a = s.createElement(o),
                    m = s.getElementsByTagName(o)[0];
                a.async = 1;
                a.src = g;
                m.parentNode.insertBefore(a, m)
            })(window, document, 'script', 'https://www.google-analytics.com/analytics.js', 'ga');
            ga('create', 'UA-80463319-2', 'auto');
            ga('send', 'pageview');
        </script>
        <script src="js/vendor.js"></script>
        <script src="js/app.js"></script>
     






    </body>

</html>
