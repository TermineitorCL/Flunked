<%-- 
    Document   : registro
    Created on : 10-11-2018, 20:03:35
    Author     : Matia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Registro</title>

        <!-- Bootstrap -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link rel="stylesheet" href="css/animate.css">
        <link href="css/prettyPhoto.css" rel="stylesheet">
        <link href="css/style.css" rel="stylesheet" />		
        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
    <body>
        <header>		
            <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
                <div class="navigation">
                    <div class="container">					
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse.collapse">
                                <span class="sr-only">Toggle navigation</span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                                <span class="icon-bar"></span>
                            </button>
                            <div class="navbar-brand">
                                <a href="index.htm"><h1><span>Flun</span>ked</h1></a>
                            </div>
                        </div>

                        <div class="navbar-collapse collapse">							
                            <div class="menu">
                                <ul class="nav nav-tabs" role="tablist">
                                    <li role="presentation"><a href="index.htm" >Inicio</a></li>
                                    <li role="presentation"><a href="about.html">Nuestro Servicio</a></li>
                                    <li role="presentation"><a href="registro_general.jsp" class="active">Registro</a></li>								
                                    <li role="presentation"><a href="portfolio.html">Login</a></li>
                                    <li role="presentation"><a href="contact.html">Contacto</a></li>						
                                </ul>
                            </div>
                        </div>						
                    </div>
                </div>	
            </nav>		
        </header>

        <div id="breadcrumb">
            <div class="container">	
                <div class="breadcrumb">							
                    <li><a href="index.htm">Inicio</a></li>
                    <li>Registro</li>			
                </div>		
            </div>	
        </div>
        <section id="contact-page">
            <div class="trans"> 
                <div class="container">
                    <div class="center">   
                        <br/>
                        <h2>Registro</h2>
                        <p>Seleccione tipo de usuario que desea registar.</p>
                    </div> 
                    <div class="row contact-wrap"> 
                        <div class="form-group">
                            <div class="col-12 col-sm-8 col-md-6 ">
                                <a href="registro_cliente.jsp" class="btn btn-primary btn-lg">Registro de Cliente</a>
                            </div>
                            <div class="col-12 col-sm-8 col-md-6">
                                <a href="registro_intermediario.jsp" class="btn btn-primary btn-lg">Registro de Intermediario</a>
                            </div>
                        </div>                   

                    </div>
                </div>
            </div><!--/.container-->
        </div>
    </section><!--/#contact-page-->

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="js/jquery-2.1.1.min.js"></script>
    <script src="js/jquery.js"></script>	
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.prettyPhoto.js"></script>
    <script src="js/jquery.isotope.min.js"></script>  
    <script src="js/wow.min.js"></script>
    <script src="js/functions.js"></script>
</body>
</html>
