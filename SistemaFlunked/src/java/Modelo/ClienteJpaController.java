/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Data.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Data.Orden;
import Modelo.exceptions.IllegalOrphanException;
import Modelo.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Matia
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getOrdenCollection() == null) {
            cliente.setOrdenCollection(new ArrayList<Orden>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Orden> attachedOrdenCollection = new ArrayList<Orden>();
            for (Orden ordenCollectionOrdenToAttach : cliente.getOrdenCollection()) {
                ordenCollectionOrdenToAttach = em.getReference(ordenCollectionOrdenToAttach.getClass(), ordenCollectionOrdenToAttach.getId());
                attachedOrdenCollection.add(ordenCollectionOrdenToAttach);
            }
            cliente.setOrdenCollection(attachedOrdenCollection);
            em.persist(cliente);
            for (Orden ordenCollectionOrden : cliente.getOrdenCollection()) {
                Cliente oldClienteIdOfOrdenCollectionOrden = ordenCollectionOrden.getClienteId();
                ordenCollectionOrden.setClienteId(cliente);
                ordenCollectionOrden = em.merge(ordenCollectionOrden);
                if (oldClienteIdOfOrdenCollectionOrden != null) {
                    oldClienteIdOfOrdenCollectionOrden.getOrdenCollection().remove(ordenCollectionOrden);
                    oldClienteIdOfOrdenCollectionOrden = em.merge(oldClienteIdOfOrdenCollectionOrden);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getId());
            Collection<Orden> ordenCollectionOld = persistentCliente.getOrdenCollection();
            Collection<Orden> ordenCollectionNew = cliente.getOrdenCollection();
            List<String> illegalOrphanMessages = null;
            for (Orden ordenCollectionOldOrden : ordenCollectionOld) {
                if (!ordenCollectionNew.contains(ordenCollectionOldOrden)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orden " + ordenCollectionOldOrden + " since its clienteId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Orden> attachedOrdenCollectionNew = new ArrayList<Orden>();
            for (Orden ordenCollectionNewOrdenToAttach : ordenCollectionNew) {
                ordenCollectionNewOrdenToAttach = em.getReference(ordenCollectionNewOrdenToAttach.getClass(), ordenCollectionNewOrdenToAttach.getId());
                attachedOrdenCollectionNew.add(ordenCollectionNewOrdenToAttach);
            }
            ordenCollectionNew = attachedOrdenCollectionNew;
            cliente.setOrdenCollection(ordenCollectionNew);
            cliente = em.merge(cliente);
            for (Orden ordenCollectionNewOrden : ordenCollectionNew) {
                if (!ordenCollectionOld.contains(ordenCollectionNewOrden)) {
                    Cliente oldClienteIdOfOrdenCollectionNewOrden = ordenCollectionNewOrden.getClienteId();
                    ordenCollectionNewOrden.setClienteId(cliente);
                    ordenCollectionNewOrden = em.merge(ordenCollectionNewOrden);
                    if (oldClienteIdOfOrdenCollectionNewOrden != null && !oldClienteIdOfOrdenCollectionNewOrden.equals(cliente)) {
                        oldClienteIdOfOrdenCollectionNewOrden.getOrdenCollection().remove(ordenCollectionNewOrden);
                        oldClienteIdOfOrdenCollectionNewOrden = em.merge(oldClienteIdOfOrdenCollectionNewOrden);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Orden> ordenCollectionOrphanCheck = cliente.getOrdenCollection();
            for (Orden ordenCollectionOrphanCheckOrden : ordenCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Orden " + ordenCollectionOrphanCheckOrden + " in its ordenCollection field has a non-nullable clienteId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
