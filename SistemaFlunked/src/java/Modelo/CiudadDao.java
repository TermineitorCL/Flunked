/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;


import Data.Ciudad;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 *
 * @author Matia
 */
@Service
public class CiudadDao {
    
    @PersistenceContext
    private EntityManager em;
    
    @Transactional (rollbackFor = {ServicioException.class})
    //@Transactional
    
    public void create (Ciudad dto) throws ServicioException{
        em.persist(dto);
        //em.merge(dto); actualizar 
        //em.remove(dto); Borrar
        //em.find(Ciudad.class,"1"); buscar por id       
    }
    
    public List<Ciudad> readAll() throws ServicioException{
        String sql="Select a from Ciudad a";
        
        Query q=em.createQuery(sql);
        return q.getResultList();
        
    }
    
}
    

