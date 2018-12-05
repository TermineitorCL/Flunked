/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Matias
 */

@Controller
public class ViewController {
    @RequestMapping(value = "/registro_general.htm", method = RequestMethod.GET)
    public String viewRegistro (Model model){
        return "registro_general";
    }
    
    @RequestMapping(value = "/login_general.htm", method = RequestMethod.GET)
        public String viewLogin(Model model) {
        return "login_general";
    }
        
    @RequestMapping(value = "/registro_cliente.htm", method = RequestMethod.GET)
    public String viewRegistroCliente (Model model){
        return "registro_cliente";
    }    
    @RequestMapping(value = "/registro_intermediario.htm", method = RequestMethod.GET)
    public String viewRegistroIntermediario (Model model){
        return "registro_intermediario";
    } 
    @RequestMapping(value = "/login_cliente.htm", method = RequestMethod.GET)
        public String viewLoginCliente(Model model) {
        return "login_cliente";
    }
    @RequestMapping(value = "/login_intermediario.htm", method = RequestMethod.GET)
        public String viewLoginIntermediario(Model model) {
        return "login_intermediario";
    } 
}
