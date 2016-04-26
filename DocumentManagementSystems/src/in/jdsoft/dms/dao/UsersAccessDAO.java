package in.jdsoft.dms.dao;
// Generated Apr 11, 2016 12:53:14 PM by Hibernate Tools 4.3.1.Final

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.jdsoft.dms.model.UsersAccess;

/**
 * Home object for domain model class UsersAccess.
 * @see in.jdsoft.dms.dao.UsersAccess
 * @author Hibernate Tools
 */

@Repository
public class UsersAccessDAO {

	private static final Log log = LogFactory.getLog(UsersAccessDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public void persist(UsersAccess transientInstance) {
		log.debug("persisting UsersAccess instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(UsersAccess instance) {
		log.debug("attaching dirty UsersAccess instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UsersAccess instance) {
		log.debug("attaching clean UsersAccess instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(UsersAccess persistentInstance) {
		log.debug("deleting UsersAccess instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UsersAccess merge(UsersAccess detachedInstance) {
		log.debug("merging UsersAccess instance");
		try {
			UsersAccess result = (UsersAccess) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public UsersAccess findById(java.lang.Integer id) {
		log.debug("getting UsersAccess instance with id: " + id);
		try {
			UsersAccess instance = (UsersAccess) sessionFactory.getCurrentSession().get("in.jdsoft.dms.model.UsersAccess",
					id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(UsersAccess instance) {
		log.debug("finding UsersAccess instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("in.jdsoft.dms.model.UsersAccess")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
