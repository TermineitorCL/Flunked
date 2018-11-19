/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Data.Cliente;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 *
 * @author Matias
 */
@Controller
public class ControladorRegistro {
    
    @Autowired
    
    ArrayList<Cliente> Clientes;
    
    @RequestMapping(value = "/registro_cliente.htm")
    
    public String home() {
    
        return "registro_cliente";
    }
    @RequestMapping (value = "registro", method = RequestMethod.POST)
    
    public ModelAndView registar (HttpSession session, HttpServletRequest request){
        
        ModelAndView model = new ModelAndView("registro_cliente");
        
        if (request.getParameter("boton").equals("Registrarse")) {
            
            request.getParameter("nombre");
            request.getParameter("apellido");
            request.getParameter("rut");
            request.getParameter("email");
            request.getParameter("telefono");
            
        if (session.getAttribute("listado") == null) {
            
            Clientes = new ArrayList();
        }else{
            Clientes = (ArrayList<Cliente>) session.getAttribute("listado");
            
        }
        }
    
        model.addObject("listadoClientes", Clientes);
        
        return model;
    }
    
    
}
