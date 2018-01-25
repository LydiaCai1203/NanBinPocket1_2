package com.database;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Chatlog entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.database.Chatlog
 * @author MyEclipse Persistence Tools
 */
public class ChatlogDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(ChatlogDAO.class);
	// property constants
	public static final String SENDER_ID = "senderId";
	public static final String RECEIVER_ID = "receiverId";
	public static final String CONTENT = "content";

	public void save(Chatlog transientInstance) {
		log.debug("saving Chatlog instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Chatlog persistentInstance) {
		log.debug("deleting Chatlog instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Chatlog findById(java.lang.Integer id) {
		log.debug("getting Chatlog instance with id: " + id);
		try {
			Chatlog instance = (Chatlog) getSession().get(
					"com.database.Chatlog", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Chatlog instance) {
		log.debug("finding Chatlog instance by example");
		try {
			List results = getSession().createCriteria("com.database.Chatlog")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Chatlog instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Chatlog as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findBySenderId(Object senderId) {
		return findByProperty(SENDER_ID, senderId);
	}

	public List findByReceiverId(Object receiverId) {
		return findByProperty(RECEIVER_ID, receiverId);
	}

	public List findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List findAll() {
		log.debug("finding all Chatlog instances");
		try {
			String queryString = "from Chatlog";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Chatlog merge(Chatlog detachedInstance) {
		log.debug("merging Chatlog instance");
		try {
			Chatlog result = (Chatlog) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Chatlog instance) {
		log.debug("attaching dirty Chatlog instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Chatlog instance) {
		log.debug("attaching clean Chatlog instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}