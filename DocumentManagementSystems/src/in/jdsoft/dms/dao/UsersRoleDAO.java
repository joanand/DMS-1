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

import in.jdsoft.dms.model.Document;
import in.jdsoft.dms.model.UsersRole;

/**
 * Home object for domain model class UsersRole.
 * @see in.jdsoft.dms.dao.UsersRole
 * @author Hibernate Tools
 */


@Repository
public class UsersRoleDAO {

	private static final Log log = LogFactory.getLog(UsersRoleDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	
	@Transactional
	public void persist(UsersRole transientInstance) {
		log.debug("persisting UsersRole instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(UsersRole instance) {
		log.debug("attaching dirty UsersRole instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UsersRole instance) {
		log.debug("attaching clean UsersRole instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(UsersRole persistentInstance) {
		log.debug("deleting UsersRole instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UsersRole merge(UsersRole detachedInstance) {
		log.debug("merging UsersRole instance");
		try {
			UsersRole result = (UsersRole) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public UsersRole findById(java.lang.Integer id) {
		log.debug("getting UsersRole instance with id: " + id);
		try {
			UsersRole instance = (UsersRole) sessionFactory.getCurrentSession().get("in.jdsoft.dms.model.UsersRole", id);
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

	@Transactional
	public List<UsersRole> getUsersRoleList() {
		log.debug("getting users role list");
		try {
			List<UsersRole> results = (List<UsersRole>) sessionFactory.getCurrentSession().createCriteria(UsersRole.class).list();
		
			log.debug("document list, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("document list", re);
			throw re;
		}
	}

	
	
	
	public List findByExample(UsersRole instance) {
		log.debug("finding UsersRole instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("in.jdsoft.dms.model.UsersRole")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
