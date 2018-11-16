<%-- 
    Document   : registro
    Created on : 10-11-2018, 20:03:35
    Author     : Matia
--%>

<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="ControladoresJPA.CiudadJpaController"%>
<%@page import="java.util.List"%>
<%@page import="Data.Ciudad"%>
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
                                    <li role="presentation"><a href="registro_general.htm" class="active">Registro</a></li>								
                                    <li role="presentation"><a href="login_general.htm">Login</a></li>
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
            <div class="container">
                <div class="center">   
                    <br/>
                    <h2>Registro</h2>
                    <p>Ingrese sus datos de registro de cliente.</p>
                </div> 
                <div class="row contact-wrap"> 
                    <div class="status alert alert-success" style="display: none"></div>
                    <form id="main-contact-form" class="contact-form" name="contact-form" method="post" action="registro_cliente_bd.jsp">
                        <div class="col-sm-5 col-sm-offset-1">
                            <div class="form-group">
                                <label>Nombre</label>
                                <input type="text" name="nombre" class="form-control" required="required">
                            </div>
                            <div class="form-group">
                                <label>Apellido</label>
                                <input type="text" name="apellido" class="form-control" required="required">
                            </div>
                            <div class="form-group">
                                <label>RUT</label>
                                <input type="text" name="rut" class="form-control" required="required">
                            </div>
                            <div class="form-group">
                                <label>Correo</label>
                                <input type="email" name="email" class="form-control" required="required">
                            </div>
                            <div class="form-group">
                                <label>Telefono</label>
                                <input type="text" name="telefono" class="form-control" required="required">
                            </div>
                            <div class="form-group">
                                <label>Disponibilidad Inmediata</label>
                                <select class="form-control">
                                    <option value="si">SI</option>
                                    <option value="no">NO</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Ciudad</label>                               
               
                            <div class="form-group">
                                <button type="submit" name="submit" class="btn btn-primary btn-lg" required="required">Registrarse</button>
                            </div>
                        </div>                       
                    </form> 
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
