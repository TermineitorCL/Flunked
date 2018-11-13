/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladoresJPA;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Data.Ciudad;
import Data.Cliente;
import Data.Intermediario;
import Data.SolicitudCompra;
import Data.Boleta;
import Data.Orden;
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
public class OrdenJpaController implements Serializable {

    public OrdenJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orden orden) throws RollbackFailureException, Exception {
        if (orden.getBoletaCollection() == null) {
            orden.setBoletaCollection(new ArrayList<Boleta>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Ciudad ciudadId = orden.getCiudadId();
            if (ciudadId != null) {
                ciudadId = em.getReference(ciudadId.getClass(), ciudadId.getId());
                orden.setCiudadId(ciudadId);
            }
            Cliente clienteId = orden.getClienteId();
            if (clienteId != null) {
                clienteId = em.getReference(clienteId.getClass(), clienteId.getId());
                orden.setClienteId(clienteId);
            }
            Intermediario intermediarioId = orden.getIntermediarioId();
            if (intermediarioId != null) {
                intermediarioId = em.getReference(intermediarioId.getClass(), intermediarioId.getId());
                orden.setIntermediarioId(intermediarioId);
            }
            SolicitudCompra solicitudCompraId = orden.getSolicitudCompraId();
            if (solicitudCompraId != null) {
                solicitudCompraId = em.getReference(solicitudCompraId.getClass(), solicitudCompraId.getId());
                orden.setSolicitudCompraId(solicitudCompraId);
            }
            Collection<Boleta> attachedBoletaCollection = new ArrayList<Boleta>();
            for (Boleta boletaCollectionBoletaToAttach : orden.getBoletaCollection()) {
                boletaCollectionBoletaToAttach = em.getReference(boletaCollectionBoletaToAttach.getClass(), boletaCollectionBoletaToAttach.getId());
                attachedBoletaCollection.add(boletaCollectionBoletaToAttach);
            }
            orden.setBoletaCollection(attachedBoletaCollection);
            em.persist(orden);
            if (ciudadId != null) {
                ciudadId.getOrdenCollection().add(orden);
                ciudadId = em.merge(ciudadId);
            }
            if (clienteId != null) {
                clienteId.getOrdenCollection().add(orden);
                clienteId = em.merge(clienteId);
            }
            if (intermediarioId != null) {
                intermediarioId.getOrdenCollection().add(orden);
                intermediarioId = em.merge(intermediarioId);
            }
            if (solicitudCompraId != null) {
                solicitudCompraId.getOrdenCollection().add(orden);
                solicitudCompraId = em.merge(solicitudCompraId);
            }
            for (Boleta boletaCollectionBoleta : orden.getBoletaCollection()) {
                Orden oldOrdenIdOfBoletaCollectionBoleta = boletaCollectionBoleta.getOrdenId();
                boletaCollectionBoleta.setOrdenId(orden);
                boletaCollectionBoleta = em.merge(boletaCollectionBoleta);
                if (oldOrdenIdOfBoletaCollectionBoleta != null) {
                    oldOrdenIdOfBoletaCollectionBoleta.getBoletaCollection().remove(boletaCollectionBoleta);
                    oldOrdenIdOfBoletaCollectionBoleta = em.merge(oldOrdenIdOfBoletaCollectionBoleta);
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

    public void edit(Orden orden) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Orden persistentOrden = em.find(Orden.class, orden.getId());
            Ciudad ciudadIdOld = persistentOrden.getCiudadId();
            Ciudad ciudadIdNew = orden.getCiudadId();
            Cliente clienteIdOld = persistentOrden.getClienteId();
            Cliente clienteIdNew = orden.getClienteId();
            Intermediario intermediarioIdOld = persistentOrden.getIntermediarioId();
            Intermediario intermediarioIdNew = orden.getIntermediarioId();
            SolicitudCompra solicitudCompraIdOld = persistentOrden.getSolicitudCompraId();
            SolicitudCompra solicitudCompraIdNew = orden.getSolicitudCompraId();
            Collection<Boleta> boletaCollectionOld = persistentOrden.getBoletaCollection();
            Collection<Boleta> boletaCollectionNew = orden.getBoletaCollection();
            List<String> illegalOrphanMessages = null;
            for (Boleta boletaCollectionOldBoleta : boletaCollectionOld) {
                if (!boletaCollectionNew.contains(boletaCollectionOldBoleta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Boleta " + boletaCollectionOldBoleta + " since its ordenId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (ciudadIdNew != null) {
                ciudadIdNew = em.getReference(ciudadIdNew.getClass(), ciudadIdNew.getId());
                orden.setCiudadId(ciudadIdNew);
            }
            if (clienteIdNew != null) {
                clienteIdNew = em.getReference(clienteIdNew.getClass(), clienteIdNew.getId());
                orden.setClienteId(clienteIdNew);
            }
            if (intermediarioIdNew != null) {
                intermediarioIdNew = em.getReference(intermediarioIdNew.getClass(), intermediarioIdNew.getId());
                orden.setIntermediarioId(intermediarioIdNew);
            }
            if (solicitudCompraIdNew != null) {
                solicitudCompraIdNew = em.getReference(solicitudCompraIdNew.getClass(), solicitudCompraIdNew.getId());
                orden.setSolicitudCompraId(solicitudCompraIdNew);
            }
            Collection<Boleta> attachedBoletaCollectionNew = new ArrayList<Boleta>();
            for (Boleta boletaCollectionNewBoletaToAttach : boletaCollectionNew) {
                boletaCollectionNewBoletaToAttach = em.getReference(boletaCollectionNewBoletaToAttach.getClass(), boletaCollectionNewBoletaToAttach.getId());
                attachedBoletaCollectionNew.add(boletaCollectionNewBoletaToAttach);
            }
            boletaCollectionNew = attachedBoletaCollectionNew;
            orden.setBoletaCollection(boletaCollectionNew);
            orden = em.merge(orden);
            if (ciudadIdOld != null && !ciudadIdOld.equals(ciudadIdNew)) {
                ciudadIdOld.getOrdenCollection().remove(orden);
                ciudadIdOld = em.merge(ciudadIdOld);
            }
            if (ciudadIdNew != null && !ciudadIdNew.equals(ciudadIdOld)) {
                ciudadIdNew.getOrdenCollection().add(orden);
                ciudadIdNew = em.merge(ciudadIdNew);
            }
            if (clienteIdOld != null && !clienteIdOld.equals(clienteIdNew)) {
                clienteIdOld.getOrdenCollection().remove(orden);
                clienteIdOld = em.merge(clienteIdOld);
            }
            if (clienteIdNew != null && !clienteIdNew.equals(clienteIdOld)) {
                clienteIdNew.getOrdenCollection().add(orden);
                clienteIdNew = em.merge(clienteIdNew);
            }
            if (intermediarioIdOld != null && !intermediarioIdOld.equals(intermediarioIdNew)) {
                intermediarioIdOld.getOrdenCollection().remove(orden);
                intermediarioIdOld = em.merge(intermediarioIdOld);
            }
            if (intermediarioIdNew != null && !intermediarioIdNew.equals(intermediarioIdOld)) {
                intermediarioIdNew.getOrdenCollection().add(orden);
                intermediarioIdNew = em.merge(intermediarioIdNew);
            }
            if (solicitudCompraIdOld != null && !solicitudCompraIdOld.equals(solicitudCompraIdNew)) {
                solicitudCompraIdOld.getOrdenCollection().remove(orden);
                solicitudCompraIdOld = em.merge(solicitudCompraIdOld);
            }
            if (solicitudCompraIdNew != null && !solicitudCompraIdNew.equals(solicitudCompraIdOld)) {
                solicitudCompraIdNew.getOrdenCollection().add(orden);
                solicitudCompraIdNew = em.merge(solicitudCompraIdNew);
            }
            for (Boleta boletaCollectionNewBoleta : boletaCollectionNew) {
                if (!boletaCollectionOld.contains(boletaCollectionNewBoleta)) {
                    Orden oldOrdenIdOfBoletaCollectionNewBoleta = boletaCollectionNewBoleta.getOrdenId();
                    boletaCollectionNewBoleta.setOrdenId(orden);
                    boletaCollectionNewBoleta = em.merge(boletaCollectionNewBoleta);
                    if (oldOrdenIdOfBoletaCollectionNewBoleta != null && !oldOrdenIdOfBoletaCollectionNewBoleta.equals(orden)) {
                        oldOrdenIdOfBoletaCollectionNewBoleta.getBoletaCollection().remove(boletaCollectionNewBoleta);
                        oldOrdenIdOfBoletaCollectionNewBoleta = em.merge(oldOrdenIdOfBoletaCollectionNewBoleta);
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
                Integer id = orden.getId();
                if (findOrden(id) == null) {
                    throw new NonexistentEntityException("The orden with id " + id + " no longer exists.");
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
            Orden orden;
            try {
                orden = em.getReference(Orden.class, id);
                orden.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orden with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Boleta> boletaCollectionOrphanCheck = orden.getBoletaCollection();
            for (Boleta boletaCollectionOrphanCheckBoleta : boletaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Orden (" + orden + ") cannot be destroyed since the Boleta " + boletaCollectionOrphanCheckBoleta + " in its boletaCollection field has a non-nullable ordenId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Ciudad ciudadId = orden.getCiudadId();
            if (ciudadId != null) {
                ciudadId.getOrdenCollection().remove(orden);
                ciudadId = em.merge(ciudadId);
            }
            Cliente clienteId = orden.getClienteId();
            if (clienteId != null) {
                clienteId.getOrdenCollection().remove(orden);
                clienteId = em.merge(clienteId);
            }
            Intermediario intermediarioId = orden.getIntermediarioId();
            if (intermediarioId != null) {
                intermediarioId.getOrdenCollection().remove(orden);
                intermediarioId = em.merge(intermediarioId);
            }
            SolicitudCompra solicitudCompraId = orden.getSolicitudCompraId();
            if (solicitudCompraId != null) {
                solicitudCompraId.getOrdenCollection().remove(orden);
                solicitudCompraId = em.merge(solicitudCompraId);
            }
            em.remove(orden);
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

    public List<Orden> findOrdenEntities() {
        return findOrdenEntities(true, -1, -1);
    }

    public List<Orden> findOrdenEntities(int maxResults, int firstResult) {
        return findOrdenEntities(false, maxResults, firstResult);
    }

    private List<Orden> findOrdenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orden.class));
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

    public Orden findOrden(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orden.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orden> rt = cq.from(Orden.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
