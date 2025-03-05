package fr.atlantique.imt.inf211.jobmngt.dao;
// Generated Mar 3, 2025, 4:39:13 PM by Hibernate Tools 5.6.15.Final


import fr.atlantique.imt.inf211.jobmngt.entity.*;
 import org.springframework.transaction.annotation.Transactional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Home object for domain model class Offermessage.
 * @see .Offermessage
 * @author Hibernate Tools
 */
@Repository
public class OfferMessageDao {

    private static final Logger logger = Logger.getLogger(OfferMessageDao.class.getName());

    @PersistenceContext private EntityManager entityManager;
    
    @Transactional
    public void persist(OfferMessage transientInstance) {
        logger.log(Level.INFO, "persisting Offermessage instance");
        try {
            entityManager.persist(transientInstance);
            logger.log(Level.INFO, "persist successful");
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "persist failed", re);
            throw re;
        }
    }
    
    @Transactional
    public void remove(OfferMessage persistentInstance) {
        logger.log(Level.INFO, "removing Offermessage instance");
        try {
            entityManager.remove(persistentInstance);
            logger.log(Level.INFO, "remove successful");
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "remove failed", re);
            throw re;
        }
    }
    
    @Transactional
    public OfferMessage merge(OfferMessage detachedInstance) {
        logger.log(Level.INFO, "merging Offermessage instance");
        try {
            OfferMessage result = entityManager.merge(detachedInstance);
            logger.log(Level.INFO, "merge successful");
            return result;
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "merge failed", re);
            throw re;
        }
    }
    
    public OfferMessage findById( int id) {
        logger.log(Level.INFO, "getting Offermessage instance with id: " + id);
        try {
            OfferMessage instance = entityManager.find(OfferMessage.class, id);
            logger.log(Level.INFO, "get successful");
            return instance;
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "get failed", re);
            throw re;
        }
    }
}