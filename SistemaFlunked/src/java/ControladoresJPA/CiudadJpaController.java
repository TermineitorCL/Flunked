/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladoresJPA;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.RollbackFailureException;
import Data.Ciudad;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Data.Intermediario;
import java.util.ArrayList;
import java.util.Collection;
import Data.Orden;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Matia
 */
public class CiudadJpaController implements Serializable {

    public CiudadJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ciudad ciudad) throws RollbackFailureException, Exception {
        if (ciudad.getIntermediarioCollection() == null) {
            ciudad.setIntermediarioCollection(new ArrayList<Intermediario>());
        }
        if (ciudad.getOrdenCollection() == null) {
            ciudad.setOrdenCollection(new ArrayList<Orden>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Intermediario> attachedIntermediarioCollection = new ArrayList<Intermediario>();
            for (Intermediario intermediarioCollectionIntermediarioToAttach : ciudad.getIntermediarioCollection()) {
                intermediarioCollectionIntermediarioToAttach = em.getReference(intermediarioCollectionIntermediarioToAttach.getClass(), intermediarioCollectionIntermediarioToAttach.getId());
                attachedIntermediarioCollection.add(intermediarioCollectionIntermediarioToAttach);
            }
            ciudad.setIntermediarioCollection(attachedIntermediarioCollection);
            Collection<Orden> attachedOrdenCollection = new ArrayList<Orden>();
            for (Orden ordenCollectionOrdenToAttach : ciudad.getOrdenCollection()) {
                ordenCollectionOrdenToAttach = em.getReference(ordenCollectionOrdenToAttach.getClass(), ordenCollectionOrdenToAttach.getId());
                attachedOrdenCollection.add(ordenCollectionOrdenToAttach);
            }
            ciudad.setOrdenCollection(attachedOrdenCollection);
            em.persist(ciudad);
            for (Intermediario intermediarioCollectionIntermediario : ciudad.getIntermediarioCollection()) {
                Ciudad oldCiudadIdOfIntermediarioCollectionIntermediario = intermediarioCollectionIntermediario.getCiudadId();
                intermediarioCollectionIntermediario.setCiudadId(ciudad);
                intermediarioCollectionIntermediario = em.merge(intermediarioCollectionIntermediario);
                if (oldCiudadIdOfIntermediarioCollectionIntermediario != null) {
                    oldCiudadIdOfIntermediarioCollectionIntermediario.getIntermediarioCollection().remove(intermediarioCollectionIntermediario);
                    oldCiudadIdOfIntermediarioCollectionIntermediario = em.merge(oldCiudadIdOfIntermediarioCollectionIntermediario);
                }
            }
            for (Orden ordenCollectionOrden : ciudad.getOrdenCollection()) {
                Ciudad oldCiudadIdOfOrdenCollectionOrden = ordenCollectionOrden.getCiudadId();
                ordenCollectionOrden.setCiudadId(ciudad);
                ordenCollectionOrden = em.merge(ordenCollectionOrden);
                if (oldCiudadIdOfOrdenCollectionOrden != null) {
                    oldCiudadIdOfOrdenCollectionOrden.getOrdenCollection().remove(ordenCollectionOrden);
                    oldCiudadIdOfOrdenCollectionOrden = em.merge(oldCiudadIdOfOrdenCollectionOrden);
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

    public void edit(Ciudad ciudad) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Ciudad persistentCiudad = em.find(Ciudad.class, ciudad.getId());
            Collection<Intermediario> intermediarioCollectionOld = persistentCiudad.getIntermediarioCollection();
            Collection<Intermediario> intermediarioCollectionNew = ciudad.getIntermediarioCollection();
            Collection<Orden> ordenCollectionOld = persistentCiudad.getOrdenCollection();
            Collection<Orden> ordenCollectionNew = ciudad.getOrdenCollection();
            List<String> illegalOrphanMessages = null;
            for (Intermediario intermediarioCollectionOldIntermediario : intermediarioCollectionOld) {
                if (!intermediarioCollectionNew.contains(intermediarioCollectionOldIntermediario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Intermediario " + intermediarioCollectionOldIntermediario + " since its ciudadId field is not nullable.");
                }
            }
            for (Orden ordenCollectionOldOrden : ordenCollectionOld) {
                if (!ordenCollectionNew.contains(ordenCollectionOldOrden)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orden " + ordenCollectionOldOrden + " since its ciudadId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Intermediario> attachedIntermediarioCollectionNew = new ArrayList<Intermediario>();
            for (Intermediario intermediarioCollectionNewIntermediarioToAttach : intermediarioCollectionNew) {
                intermediarioCollectionNewIntermediarioToAttach = em.getReference(intermediarioCollectionNewIntermediarioToAttach.getClass(), intermediarioCollectionNewIntermediarioToAttach.getId());
                attachedIntermediarioCollectionNew.add(intermediarioCollectionNewIntermediarioToAttach);
            }
            intermediarioCollectionNew = attachedIntermediarioCollectionNew;
            ciudad.setIntermediarioCollection(intermediarioCollectionNew);
            Collection<Orden> attachedOrdenCollectionNew = new ArrayList<Orden>();
            for (Orden ordenCollectionNewOrdenToAttach : ordenCollectionNew) {
                ordenCollectionNewOrdenToAttach = em.getReference(ordenCollectionNewOrdenToAttach.getClass(), ordenCollectionNewOrdenToAttach.getId());
                attachedOrdenCollectionNew.add(ordenCollectionNewOrdenToAttach);
            }
            ordenCollectionNew = attachedOrdenCollectionNew;
            ciudad.setOrdenCollection(ordenCollectionNew);
            ciudad = em.merge(ciudad);
            for (Intermediario intermediarioCollectionNewIntermediario : intermediarioCollectionNew) {
                if (!intermediarioCollectionOld.contains(intermediarioCollectionNewIntermediario)) {
                    Ciudad oldCiudadIdOfIntermediarioCollectionNewIntermediario = intermediarioCollectionNewIntermediario.getCiudadId();
                    intermediarioCollectionNewIntermediario.setCiudadId(ciudad);
                    intermediarioCollectionNewIntermediario = em.merge(intermediarioCollectionNewIntermediario);
                    if (oldCiudadIdOfIntermediarioCollectionNewIntermediario != null && !oldCiudadIdOfIntermediarioCollectionNewIntermediario.equals(ciudad)) {
                        oldCiudadIdOfIntermediarioCollectionNewIntermediario.getIntermediarioCollection().remove(intermediarioCollectionNewIntermediario);
                        oldCiudadIdOfIntermediarioCollectionNewIntermediario = em.merge(oldCiudadIdOfIntermediarioCollectionNewIntermediario);
                    }
                }
            }
            for (Orden ordenCollectionNewOrden : ordenCollectionNew) {
                if (!ordenCollectionOld.contains(ordenCollectionNewOrden)) {
                    Ciudad oldCiudadIdOfOrdenCollectionNewOrden = ordenCollectionNewOrden.getCiudadId();
                    ordenCollectionNewOrden.setCiudadId(ciudad);
                    ordenCollectionNewOrden = em.merge(ordenCollectionNewOrden);
                    if (oldCiudadIdOfOrdenCollectionNewOrden != null && !oldCiudadIdOfOrdenCollectionNewOrden.equals(ciudad)) {
                        oldCiudadIdOfOrdenCollectionNewOrden.getOrdenCollection().remove(ordenCollectionNewOrden);
                        oldCiudadIdOfOrdenCollectionNewOrden = em.merge(oldCiudadIdOfOrdenCollectionNewOrden);
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
                Integer id = ciudad.getId();
                if (findCiudad(id) == null) {
                    throw new NonexistentEntityException("The ciudad with id " + id + " no longer exists.");
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
            Ciudad ciudad;
            try {
                ciudad = em.getReference(Ciudad.class, id);
                ciudad.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ciudad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Intermediario> intermediarioCollectionOrphanCheck = ciudad.getIntermediarioCollection();
            for (Intermediario intermediarioCollectionOrphanCheckIntermediario : intermediarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ciudad (" + ciudad + ") cannot be destroyed since the Intermediario " + intermediarioCollectionOrphanCheckIntermediario + " in its intermediarioCollection field has a non-nullable ciudadId field.");
            }
            Collection<Orden> ordenCollectionOrphanCheck = ciudad.getOrdenCollection();
            for (Orden ordenCollectionOrphanCheckOrden : ordenCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ciudad (" + ciudad + ") cannot be destroyed since the Orden " + ordenCollectionOrphanCheckOrden + " in its ordenCollection field has a non-nullable ciudadId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(ciudad);
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

    public List<Ciudad> findCiudadEntities() {
        return findCiudadEntities(true, -1, -1);
    }

    public List<Ciudad> findCiudadEntities(int maxResults, int firstResult) {
        return findCiudadEntities(false, maxResults, firstResult);
    }

    private List<Ciudad> findCiudadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ciudad.class));
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

    public Ciudad findCiudad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ciudad.class, id);
        } finally {
            em.close();
        }
    }

        public Ciudad findCiudadAll(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ciudad.class, id);
        } finally {
            em.close();
        }
    }
        
    public int getCiudadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ciudad> rt = cq.from(Ciudad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }   
    
}
