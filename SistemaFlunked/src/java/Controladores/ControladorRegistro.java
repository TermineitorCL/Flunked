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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;





/**
 *
 * @author Matias

@Controller
public class ControladorRegistro {
 
    @Autowired
    Cliente cliente;
 
    @RequestMapping(value = "/displayForm", method = RequestMethod.GET)
    public ModelAndView displayNewAccountForm(ModelMap model) {
        return new ModelAndView("registro_cliente", "new-cliente-form", new Cliente());
    }
 
    @RequestMapping(value = "/registro_cliente", method = RequestMethod.POST)
    public ModelAndView submit(@ModelAttribute("new-account-form") Cliente cliente, ModelMap model) {
        accountService.saveAccount(account);
        List<Account> accounts = accountService.getAccounts();
 
        ModelAndView modelAndView = new ModelAndView("accounts");
        modelAndView.addObject("accounts", accounts);
        return modelAndView;
    }
 
    @RequestMapping(value = "/accountDetails/{accountId}", method = RequestMethod.GET)
    public ModelAndView accountDetail(@PathVariable("accountId") String id) {
        Account account = accountService.getAccount(id);
        ModelAndView modelAndView = new ModelAndView("accountDetails"); //i.e accountDetail.jsp
                                                                    
        modelAndView.addObject("account", account);
       return modelAndView;
    }
}

 */