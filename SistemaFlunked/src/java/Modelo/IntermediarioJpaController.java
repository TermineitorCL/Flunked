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
import Data.Ciudad;
import Data.Intermediario;
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
public class IntermediarioJpaController implements Serializable {

    public IntermediarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Intermediario intermediario) {
        if (intermediario.getOrdenCollection() == null) {
            intermediario.setOrdenCollection(new ArrayList<Orden>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad ciudadId = intermediario.getCiudadId();
            if (ciudadId != null) {
                ciudadId = em.getReference(ciudadId.getClass(), ciudadId.getId());
                intermediario.setCiudadId(ciudadId);
            }
            Collection<Orden> attachedOrdenCollection = new ArrayList<Orden>();
            for (Orden ordenCollectionOrdenToAttach : intermediario.getOrdenCollection()) {
                ordenCollectionOrdenToAttach = em.getReference(ordenCollectionOrdenToAttach.getClass(), ordenCollectionOrdenToAttach.getId());
                attachedOrdenCollection.add(ordenCollectionOrdenToAttach);
            }
            intermediario.setOrdenCollection(attachedOrdenCollection);
            em.persist(intermediario);
            if (ciudadId != null) {
                ciudadId.getIntermediarioCollection().add(intermediario);
                ciudadId = em.merge(ciudadId);
            }
            for (Orden ordenCollectionOrden : intermediario.getOrdenCollection()) {
                Intermediario oldIntermediarioIdOfOrdenCollectionOrden = ordenCollectionOrden.getIntermediarioId();
                ordenCollectionOrden.setIntermediarioId(intermediario);
                ordenCollectionOrden = em.merge(ordenCollectionOrden);
                if (oldIntermediarioIdOfOrdenCollectionOrden != null) {
                    oldIntermediarioIdOfOrdenCollectionOrden.getOrdenCollection().remove(ordenCollectionOrden);
                    oldIntermediarioIdOfOrdenCollectionOrden = em.merge(oldIntermediarioIdOfOrdenCollectionOrden);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Intermediario intermediario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Intermediario persistentIntermediario = em.find(Intermediario.class, intermediario.getId());
            Ciudad ciudadIdOld = persistentIntermediario.getCiudadId();
            Ciudad ciudadIdNew = intermediario.getCiudadId();
            Collection<Orden> ordenCollectionOld = persistentIntermediario.getOrdenCollection();
            Collection<Orden> ordenCollectionNew = intermediario.getOrdenCollection();
            List<String> illegalOrphanMessages = null;
            for (Orden ordenCollectionOldOrden : ordenCollectionOld) {
                if (!ordenCollectionNew.contains(ordenCollectionOldOrden)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orden " + ordenCollectionOldOrden + " since its intermediarioId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (ciudadIdNew != null) {
                ciudadIdNew = em.getReference(ciudadIdNew.getClass(), ciudadIdNew.getId());
                intermediario.setCiudadId(ciudadIdNew);
            }
            Collection<Orden> attachedOrdenCollectionNew = new ArrayList<Orden>();
            for (Orden ordenCollectionNewOrdenToAttach : ordenCollectionNew) {
                ordenCollectionNewOrdenToAttach = em.getReference(ordenCollectionNewOrdenToAttach.getClass(), ordenCollectionNewOrdenToAttach.getId());
                attachedOrdenCollectionNew.add(ordenCollectionNewOrdenToAttach);
            }
            ordenCollectionNew = attachedOrdenCollectionNew;
            intermediario.setOrdenCollection(ordenCollectionNew);
            intermediario = em.merge(intermediario);
            if (ciudadIdOld != null && !ciudadIdOld.equals(ciudadIdNew)) {
                ciudadIdOld.getIntermediarioCollection().remove(intermediario);
                ciudadIdOld = em.merge(ciudadIdOld);
            }
            if (ciudadIdNew != null && !ciudadIdNew.equals(ciudadIdOld)) {
                ciudadIdNew.getIntermediarioCollection().add(intermediario);
                ciudadIdNew = em.merge(ciudadIdNew);
            }
            for (Orden ordenCollectionNewOrden : ordenCollectionNew) {
                if (!ordenCollectionOld.contains(ordenCollectionNewOrden)) {
                    Intermediario oldIntermediarioIdOfOrdenCollectionNewOrden = ordenCollectionNewOrden.getIntermediarioId();
                    ordenCollectionNewOrden.setIntermediarioId(intermediario);
                    ordenCollectionNewOrden = em.merge(ordenCollectionNewOrden);
                    if (oldIntermediarioIdOfOrdenCollectionNewOrden != null && !oldIntermediarioIdOfOrdenCollectionNewOrden.equals(intermediario)) {
                        oldIntermediarioIdOfOrdenCollectionNewOrden.getOrdenCollection().remove(ordenCollectionNewOrden);
                        oldIntermediarioIdOfOrdenCollectionNewOrden = em.merge(oldIntermediarioIdOfOrdenCollectionNewOrden);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = intermediario.getId();
                if (findIntermediario(id) == null) {
                    throw new NonexistentEntityException("The intermediario with id " + id + " no longer exists.");
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
            Intermediario intermediario;
            try {
                intermediario = em.getReference(Intermediario.class, id);
                intermediario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The intermediario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Orden> ordenCollectionOrphanCheck = intermediario.getOrdenCollection();
            for (Orden ordenCollectionOrphanCheckOrden : ordenCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Intermediario (" + intermediario + ") cannot be destroyed since the Orden " + ordenCollectionOrphanCheckOrden + " in its ordenCollection field has a non-nullable intermediarioId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Ciudad ciudadId = intermediario.getCiudadId();
            if (ciudadId != null) {
                ciudadId.getIntermediarioCollection().remove(intermediario);
                ciudadId = em.merge(ciudadId);
            }
            em.remove(intermediario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Intermediario> findIntermediarioEntities() {
        return findIntermediarioEntities(true, -1, -1);
    }

    public List<Intermediario> findIntermediarioEntities(int maxResults, int firstResult) {
        return findIntermediarioEntities(false, maxResults, firstResult);
    }

    private List<Intermediario> findIntermediarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Intermediario.class));
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

    public Intermediario findIntermediario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Intermediario.class, id);
        } finally {
            em.close();
        }
    }

    public int getIntermediarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Intermediario> rt = cq.from(Intermediario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
