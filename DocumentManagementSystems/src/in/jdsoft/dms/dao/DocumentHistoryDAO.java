package in.jdsoft.dms.dao;
// Generated Apr 11, 2016 12:53:14 PM by Hibernate Tools 4.3.1.Final

import java.util.List;


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

import in.jdsoft.dms.model.DocumentHistory;

/**
 * Home object for domain model class DocumentHistory.
 * @see in.jdsoft.dms.dao.DocumentHistory
 * @author Hibernate Tools
 */

@Repository
public class DocumentHistoryDAO {

	private static final Log log = LogFactory.getLog(DocumentHistoryDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public void persist(DocumentHistory transientInstance) {
		log.debug("persisting DocumentHistory instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(DocumentHistory instance) {
		log.debug("attaching dirty DocumentHistory instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	
	
	public void attachClean(DocumentHistory instance) {
		log.debug("attaching clean DocumentHistory instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Transactional
	public void delete(DocumentHistory persistentInstance) {
		log.debug("deleting DocumentHistory instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	@Transactional
	public DocumentHistory getDocumentHistoryByName(Integer DocumentId) {
		log.debug("getting documenthistory instance with name: " + DocumentId);
		try {
			Criteria criteria=sessionFactory.getCurrentSession().createCriteria(DocumentHistory.class);
			criteria.add(Restrictions.eq("documentId", DocumentId));
			DocumentHistory documenthistory=(DocumentHistory)criteria.uniqueResult();
			if (documenthistory == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return documenthistory;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@Transactional
	public void update(DocumentHistory instance) {
		log.debug("updating instance");
		try {
			sessionFactory.getCurrentSession().update(instance);
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}
	
	@Transactional
	public List<DocumentHistory>  getDocumentHistoryById(Integer integer) {
		log.debug("getting DocumentHistory instance with id: " + integer);
		try {
			Criteria criteria=sessionFactory.getCurrentSession().createCriteria(DocumentHistory.class);
			criteria.add(Restrictions.eq("documentId", integer));
			List<DocumentHistory>  document=(List<DocumentHistory>) criteria.list();
			if (document == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return document;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public DocumentHistory merge(DocumentHistory detachedInstance) {
		log.debug("merging DocumentHistory instance");
		try {
			DocumentHistory result = (DocumentHistory) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public DocumentHistory findById(java.lang.Integer id) {
		log.debug("getting DocumentHistory instance with id: " + id);
		try {
			DocumentHistory instance = (DocumentHistory) sessionFactory.getCurrentSession()
					.get("in.jdsoft.dms.model.DocumentHistory", id);
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

	public List findByExample(DocumentHistory instance) {
		log.debug("finding DocumentHistory instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("in.jdsoft.dms.model.DocumentHistory")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
