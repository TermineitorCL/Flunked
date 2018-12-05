/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Data.Orden;
import Data.SolicitudCompra;
import Modelo.exceptions.IllegalOrphanException;
import Modelo.exceptions.NonexistentEntityException;
import Modelo.exceptions.RollbackFailureException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Matia
 */
public class SolicitudCompraJpaController implements Serializable {

    public SolicitudCompraJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SolicitudCompra solicitudCompra) throws RollbackFailureException, Exception {
        if (solicitudCompra.getOrdenCollection() == null) {
            solicitudCompra.setOrdenCollection(new ArrayList<Orden>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Orden> attachedOrdenCollection = new ArrayList<Orden>();
            for (Orden ordenCollectionOrdenToAttach : solicitudCompra.getOrdenCollection()) {
                ordenCollectionOrdenToAttach = em.getReference(ordenCollectionOrdenToAttach.getClass(), ordenCollectionOrdenToAttach.getId());
                attachedOrdenCollection.add(ordenCollectionOrdenToAttach);
            }
            solicitudCompra.setOrdenCollection(attachedOrdenCollection);
            em.persist(solicitudCompra);
            for (Orden ordenCollectionOrden : solicitudCompra.getOrdenCollection()) {
                SolicitudCompra oldSolicitudCompraIdOfOrdenCollectionOrden = ordenCollectionOrden.getSolicitudCompraId();
                ordenCollectionOrden.setSolicitudCompraId(solicitudCompra);
                ordenCollectionOrden = em.merge(ordenCollectionOrden);
                if (oldSolicitudCompraIdOfOrdenCollectionOrden != null) {
                    oldSolicitudCompraIdOfOrdenCollectionOrden.getOrdenCollection().remove(ordenCollectionOrden);
                    oldSolicitudCompraIdOfOrdenCollectionOrden = em.merge(oldSolicitudCompraIdOfOrdenCollectionOrden);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SolicitudCompra solicitudCompra) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            SolicitudCompra persistentSolicitudCompra = em.find(SolicitudCompra.class, solicitudCompra.getId());
            Collection<Orden> ordenCollectionOld = persistentSolicitudCompra.getOrdenCollection();
            Collection<Orden> ordenCollectionNew = solicitudCompra.getOrdenCollection();
            List<String> illegalOrphanMessages = null;
            for (Orden ordenCollectionOldOrden : ordenCollectionOld) {
                if (!ordenCollectionNew.contains(ordenCollectionOldOrden)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orden " + ordenCollectionOldOrden + " since its solicitudCompraId field is not nullable.");
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
            solicitudCompra.setOrdenCollection(ordenCollectionNew);
            solicitudCompra = em.merge(solicitudCompra);
            for (Orden ordenCollectionNewOrden : ordenCollectionNew) {
                if (!ordenCollectionOld.contains(ordenCollectionNewOrden)) {
                    SolicitudCompra oldSolicitudCompraIdOfOrdenCollectionNewOrden = ordenCollectionNewOrden.getSolicitudCompraId();
                    ordenCollectionNewOrden.setSolicitudCompraId(solicitudCompra);
                    ordenCollectionNewOrden = em.merge(ordenCollectionNewOrden);
                    if (oldSolicitudCompraIdOfOrdenCollectionNewOrden != null && !oldSolicitudCompraIdOfOrdenCollectionNewOrden.equals(solicitudCompra)) {
                        oldSolicitudCompraIdOfOrdenCollectionNewOrden.getOrdenCollection().remove(ordenCollectionNewOrden);
                        oldSolicitudCompraIdOfOrdenCollectionNewOrden = em.merge(oldSolicitudCompraIdOfOrdenCollectionNewOrden);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = solicitudCompra.getId();
                if (findSolicitudCompra(id) == null) {
                    throw new NonexistentEntityException("The solicitudCompra with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            SolicitudCompra solicitudCompra;
            try {
                solicitudCompra = em.getReference(SolicitudCompra.class, id);
                solicitudCompra.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The solicitudCompra with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Orden> ordenCollectionOrphanCheck = solicitudCompra.getOrdenCollection();
            for (Orden ordenCollectionOrphanCheckOrden : ordenCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SolicitudCompra (" + solicitudCompra + ") cannot be destroyed since the Orden " + ordenCollectionOrphanCheckOrden + " in its ordenCollection field has a non-nullable solicitudCompraId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(solicitudCompra);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SolicitudCompra> findSolicitudCompraEntities() {
        return findSolicitudCompraEntities(true, -1, -1);
    }

    public List<SolicitudCompra> findSolicitudCompraEntities(int maxResults, int firstResult) {
        return findSolicitudCompraEntities(false, maxResults, firstResult);
    }

    private List<SolicitudCompra> findSolicitudCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SolicitudCompra.class));
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

    public SolicitudCompra findSolicitudCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SolicitudCompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getSolicitudCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SolicitudCompra> rt = cq.from(SolicitudCompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
