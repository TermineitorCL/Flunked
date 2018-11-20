/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Data.Cliente;
import Modelo.ClienteJpaController;
import Modelo.exceptions.NonexistentEntityException;
import Modelo.exceptions.RollbackFailureException;
import static javax.swing.text.StyleConstants.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private ClienteJpaController ClienteJPA;
    
    @RequestMapping(value = "/Cliente",method=RequestMethod.POST)
    
    public ModelAndView guardarClientes (@ModelAttribute("Cliente") Cliente Cliente) throws NonexistentEntityException, RollbackFailureException, Exception{
        
        try
        {
            if(ClienteJPA.findCliente(Cliente.getId())!= null);
            ClienteJPA.create(Cliente);
        }
        catch(EmptyResultDataAccessException e)
        {
            ClienteJPA.edit(Cliente);
        }
        return new ModelAndView("redirect:/Cliente");
    }
}

