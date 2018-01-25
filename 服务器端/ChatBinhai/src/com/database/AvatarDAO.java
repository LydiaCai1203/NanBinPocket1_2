package com.database;

import java.util.List;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 	* A data access object (DAO) providing persistence and search support for Avatar entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.database.Avatar
  * @author MyEclipse Persistence Tools 
 */
public class AvatarDAO extends BaseHibernateDAO  {
	     private static final Logger log = LoggerFactory.getLogger(AvatarDAO.class);
		//property constants
	public static final String IMG_NAME = "imgName";



    
    public void save(Avatar transientInstance) {
        log.debug("saving Avatar instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Avatar persistentInstance) {
        log.debug("deleting Avatar instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Avatar findById( java.lang.Integer id) {
        log.debug("getting Avatar instance with id: " + id);
        try {
            Avatar instance = (Avatar) getSession()
                    .get("com.database.Avatar", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Avatar instance) {
        log.debug("finding Avatar instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.database.Avatar")
                    .add(Example.create(instance))
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding Avatar instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Avatar as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByImgName(Object imgName
	) {
		return findByProperty(IMG_NAME, imgName
		);
	}
	

	public List findAll() {
		log.debug("finding all Avatar instances");
		try {
			String queryString = "from Avatar";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Avatar merge(Avatar detachedInstance) {
        log.debug("merging Avatar instance");
        try {
            Avatar result = (Avatar) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Avatar instance) {
        log.debug("attaching dirty Avatar instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Avatar instance) {
        log.debug("attaching clean Avatar instance");
        try {
                      	getSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}