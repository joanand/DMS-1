package in.jdsoft.dms.dao;
// Generated Apr 11, 2016 12:53:14 PM by Hibernate Tools 4.3.1.Final

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.jdsoft.dms.model.Document;
import in.jdsoft.dms.model.Users;

/**
 * Home object for domain model class Users.
 * @see in.jdsoft.dms.dao.Users
 * @author Hibernate Tools
 */


@Repository
public class UsersDAO {

	private static final Log log = LogFactory.getLog(UsersDAO.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	
@Transactional
	public void persist(Users transientInstance) {
		log.debug("persisting Users instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

@Transactional
public void update(Users instance) {
	log.debug("updating instance");
	try {
		sessionFactory.getCurrentSession().update(instance);
		log.debug("update successful");
	} catch (RuntimeException re) {
		log.error("update failed", re);
		throw re;
	}
}

	public void attachDirty(Users instance) {
		log.debug("attaching dirty Users instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Users instance) {
		log.debug("attaching clean Users instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Transactional
	public void delete(Users persistentInstance) {
		log.debug("deleting Users instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Users merge(Users detachedInstance) {
		log.debug("merging Users instance");
		try {
			Users result = (Users) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Users findById(java.lang.Integer id) {
		log.debug("getting Users instance with id: " + id);
		try {
			Users instance = (Users) sessionFactory.getCurrentSession().get("in.jdsoft.dms.model.Users", id);
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

	public List findByExample(Users instance) {
		log.debug("finding Users instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("in.jdsoft.dms.model.Users")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	

	@Transactional
	public Users getUserByName(String UserName) {
		log.debug("getting Users instance with name: " + UserName);
		try {
			Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Users.class);
			criteria.add(Restrictions.eq("userName", UserName));
			Users user=(Users)criteria.uniqueResult();
			if (user == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return user;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@Transactional
	public Users getUserById(Integer UserId) {
		log.debug("getting Users instance with name: " + UserId);
		try {
			Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Users.class);
			criteria.add(Restrictions.eq("userId", UserId));
			Users user=(Users)criteria.uniqueResult();
			if (user == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return user;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@Transactional
	public List<Users> getUsersList() {
		log.debug("getting User list");
		try {
		
			List<Users>  Users=(List<Users>) sessionFactory.getCurrentSession().createCriteria(Users.class).list();
		
			return Users;
		} catch (RuntimeException re) {
			log.error("Users list", re);
			throw re;
		}
	}
	
	@Transactional
	public  List<Users>  getUsers() {
		log.debug("getting User");
		try {
		
			List<Users>  user=(List<Users>) sessionFactory.getCurrentSession().createCriteria(Users.class).list();
			
		
			if (user == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return user;
			
		} catch (RuntimeException re) {
			log.error("Users list", re);
			throw re;
		}
	}
}
