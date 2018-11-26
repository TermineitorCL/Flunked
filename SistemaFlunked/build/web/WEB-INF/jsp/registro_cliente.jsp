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
								<li role="presentation"><a href="registro_general.htm" class="active">Registro</a></li>								
								<li role="presentation"><a href="login_general.htm">Login</a></li>						
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
            <div class="container">
                <div class="center">   
                    <br/>
                    <h2>Registro</h2>
                    <p>Ingrese sus datos de registro de cliente.</p>
                </div> 
                <div class="row contact-wrap"> 
                    <div class="status alert alert-success" style="display: none"></div>
                    <form:form method="POST" action="registro_cliente" modelAttribute="new-cliente-form">
                        <div class="col-sm-5 col-sm-offset-1">
                            <div class="form-group">
                                <p>Nombre</p>
                                <input type="text" name="nombre" class="form-control" required="required">
                            </div>
                            <div class="form-group">
                                <p>Apellido</p>
                                <input type="text" name="apellido" class="form-control" required="required">
                            </div>
                            <div class="form-group">
                                <p>RUT</p>
                                <input type="text" name="rut" class="form-control" required="required">
                            </div>
                            <div class="form-group">
                                <p>Correo</p>
                                <input type="email" name="email" class="form-control" required="required">
                            </div>
                            <div class="form-group">
                                <p>Telefono</p>
                                <input type="text" class="form-control" required="required" name="telefono">
                            </div>
                            <div class="form-group">
                                <button type="submit" name="guardar" class="btn btn-primary btn-lg" required="required">Registrarse</button>
                            </div>
                        </div>                       
                  </form:form>
                </div><!--/.row-->
            </div><!--/.container-->
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
