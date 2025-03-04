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
 * Home object for domain model class Joboffer.
 * @see .Joboffer
 * @author Hibernate Tools
 */
@Repository
public class JobOfferDao {

    private static final Logger logger = Logger.getLogger(JobOfferDao.class.getName());

    @PersistenceContext private EntityManager entityManager;
    
    public void persist(JobOffer transientInstance) {
        logger.log(Level.INFO, "persisting Joboffer instance");
        try {
            entityManager.persist(transientInstance);
            logger.log(Level.INFO, "persist successful");
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "persist failed", re);
            throw re;
        }
    }
    
    public void remove(JobOffer persistentInstance) {
        logger.log(Level.INFO, "removing Joboffer instance");
        try {
            entityManager.remove(persistentInstance);
            logger.log(Level.INFO, "remove successful");
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "remove failed", re);
            throw re;
        }
    }
    
    public JobOffer merge(JobOffer detachedInstance) {
        logger.log(Level.INFO, "merging Joboffer instance");
        try {
            JobOffer result = entityManager.merge(detachedInstance);
            logger.log(Level.INFO, "merge successful");
            return result;
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "merge failed", re);
            throw re;
        }
    }
    
    public JobOffer findById( int id) {
        logger.log(Level.INFO, "getting Joboffer instance with id: " + id);
        try {
            JobOffer instance = entityManager.find(JobOffer.class, id);
            logger.log(Level.INFO, "get successful");
            return instance;
        }
        catch (RuntimeException re) {
            logger.log(Level.SEVERE, "get failed", re);
            throw re;
        }
    }
}