/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Data.Cliente;

/**
 *
 * @author Matia
 */
public abstract class ClienteDao {
	
    
    abstract boolean saveCliebte(Cliente cliente);
        
    abstract Cliente getCliente(String id);

}
