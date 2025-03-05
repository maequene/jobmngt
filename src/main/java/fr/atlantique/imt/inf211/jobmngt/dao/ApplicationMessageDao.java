package fr.atlantique.imt.inf211.jobmngt.dao;
// Generated Mar 3, 2025, 4:39:13 PM by Hibernate Tools 5.6.15.Final


import fr.atlantique.imt.inf211.jobmngt.entity.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Home object for domain model class Applicationmessage.
 * @see .Applicationmessage
 * @author Hibernate Tools
 */
@Repository
public class ApplicationMessageDao {

    private static final Logger logger = Logger.getLogger(ApplicationMessageDao.class.getName());

    @PersistenceContext private EntityManager entityManager;
    
    public void persist(ApplicationMessage transientInstance) {
        logger.log(Level.INFO, "persisting Applicationmessage instance");
        try {
            entityManager.persist(transientInstance);
            logger.log(Level.INFO, "persist successful");
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "persist failed", re);
            throw re;
        }
    }
    
    public void remove(ApplicationMessage persistentInstance) {
        logger.log(Level.INFO, "removing Applicationmessage instance");
        try {
            entityManager.remove(persistentInstance);
            logger.log(Level.INFO, "remove successful");
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "remove failed", re);
            throw re;
        }
    }
    
    public ApplicationMessage merge(ApplicationMessage detachedInstance) {
        logger.log(Level.INFO, "merging Applicationmessage instance");
        try {
            ApplicationMessage result = entityManager.merge(detachedInstance);
            logger.log(Level.INFO, "merge successful");
            return result;
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "merge failed", re);
            throw re;
        }
    }
    
    public ApplicationMessage findById( int id) {
        logger.log(Level.INFO, "getting Applicationmessage instance with id: {0}", id);
        try {
            ApplicationMessage instance = entityManager.find(ApplicationMessage.class, id);
            logger.log(Level.INFO, "get successful");
            return instance;
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "get failed", re);
            throw re;
        }
    }
}