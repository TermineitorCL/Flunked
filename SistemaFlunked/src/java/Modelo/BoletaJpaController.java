/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Data.Boleta;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Data.Orden;
import Modelo.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Matia
 */
public class BoletaJpaController implements Serializable {

    public BoletaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Boleta boleta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orden ordenId = boleta.getOrdenId();
            if (ordenId != null) {
                ordenId = em.getReference(ordenId.getClass(), ordenId.getId());
                boleta.setOrdenId(ordenId);
            }
            em.persist(boleta);
            if (ordenId != null) {
                ordenId.getBoletaCollection().add(boleta);
                ordenId = em.merge(ordenId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Boleta boleta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Boleta persistentBoleta = em.find(Boleta.class, boleta.getId());
            Orden ordenIdOld = persistentBoleta.getOrdenId();
            Orden ordenIdNew = boleta.getOrdenId();
            if (ordenIdNew != null) {
                ordenIdNew = em.getReference(ordenIdNew.getClass(), ordenIdNew.getId());
                boleta.setOrdenId(ordenIdNew);
            }
            boleta = em.merge(boleta);
            if (ordenIdOld != null && !ordenIdOld.equals(ordenIdNew)) {
                ordenIdOld.getBoletaCollection().remove(boleta);
                ordenIdOld = em.merge(ordenIdOld);
            }
            if (ordenIdNew != null && !ordenIdNew.equals(ordenIdOld)) {
                ordenIdNew.getBoletaCollection().add(boleta);
                ordenIdNew = em.merge(ordenIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = boleta.getId();
                if (findBoleta(id) == null) {
                    throw new NonexistentEntityException("The boleta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Boleta boleta;
            try {
                boleta = em.getReference(Boleta.class, id);
                boleta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The boleta with id " + id + " no longer exists.", enfe);
            }
            Orden ordenId = boleta.getOrdenId();
            if (ordenId != null) {
                ordenId.getBoletaCollection().remove(boleta);
                ordenId = em.merge(ordenId);
            }
            em.remove(boleta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Boleta> findBoletaEntities() {
        return findBoletaEntities(true, -1, -1);
    }

    public List<Boleta> findBoletaEntities(int maxResults, int firstResult) {
        return findBoletaEntities(false, maxResults, firstResult);
    }

    private List<Boleta> findBoletaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Boleta.class));
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

    public Boleta findBoleta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Boleta.class, id);
        } finally {
            em.close();
        }
    }

    public int getBoletaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Boleta> rt = cq.from(Boleta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
