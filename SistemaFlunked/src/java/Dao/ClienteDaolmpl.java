/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Data.Cliente;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Matia
 

@Service
public class ClienteDaolmpl {
    
    @Autowired
    private ClienteDao clienteDao;
    
    @Override
    
    public List<Cliente> getCliente() {
        return clienteDao.getCliente();
    }
 
    @Override
    public boolean saveAccount(Account account) {
        return accountDao.saveAccount(account);
    }
 
    @Override
    public Account getAccount(String id) {
        return accountDao.getAccount(id);
    }  
}
*/