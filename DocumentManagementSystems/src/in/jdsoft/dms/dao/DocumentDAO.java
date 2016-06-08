package in.jdsoft.dms.dao;
// Generated Apr 11, 2016 12:53:14 PM by Hibernate Tools 4.3.1.Final

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.jdsoft.dms.model.Document;

/**
 * Home object for domain model class Document.
 * @see in.jdsoft.dms.dao.Document
 * @author Hibernate Tools
 */
@Repository
public class DocumentDAO {

	private static final Log log = LogFactory.getLog(DocumentDAO.class);

	@Autowired
	private SessionFactory sessionFactory;
	


@Transactional
	public void persist(Document transientInstance) {
		log.debug("persisting Document instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	

@Transactional
	public void delete(Document persistentInstance) {
		log.debug("deleting Document instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

@Transactional
public void update(Document instance) {
	log.debug("updating instance");
	try {
		sessionFactory.getCurrentSession().update(instance);
		log.debug("update successful");
	} catch (RuntimeException re) {
		log.error("update failed", re);
		throw re;
	}
}
	public Document findById(java.lang.Integer id) {
		log.debug("getting Document instance with id: " + id);
		try {
			Document instance = (Document) sessionFactory.getCurrentSession().get("in.jdsoft.dms.model.Document", id);
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
	public Document getDocumentByUuid(String DocumentUuid) {
		log.debug("getting Document instance with Uuid: " + DocumentUuid);
		try {
			Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Document.class);
			criteria.add(Restrictions.eq("documentUuid", DocumentUuid));
			Document document=(Document)criteria.uniqueResult();
		
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

@Transactional
public List<Document>  getDocumentByPath(String Path) {
	log.debug("getting Document instance with path: " + Path);
	try {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Document.class);
		criteria.add(Restrictions.eq("path",Path));
		List<Document>  document=(List<Document>) criteria.list();
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
	
@Transactional
public List<Document> getDocumentList() {
	log.debug("getting document list");
	try {
		List<Document> results = (List<Document>) sessionFactory.getCurrentSession().createCriteria(Document.class).list();
	
		log.debug("document list, result size: " + results.size());
		return results;
	} catch (RuntimeException re) {
		log.error("document list", re);
		throw re;
	}
}



@Transactional
public List<Document>  getDocumentListByUser(String Path) {
	log.debug("getting Document instance with path: " + Path);
	try {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Document.class);
		MatchMode matchMode=MatchMode.ANYWHERE;
		criteria.add(Restrictions.like("path", Path, matchMode));
		List<Document>  document=(List<Document>) criteria.list();
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
	


@Transactional
public List<Document>  getDocumentsByUser(String UserId) {
	log.debug("getting Document instance with userid: " + UserId);
	try {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Document.class);
		MatchMode matchMode=MatchMode.ANYWHERE;
		criteria.add(Restrictions.like("documentAccess", UserId, matchMode));
		List<Document>  document=(List<Document>) criteria.list();
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

@Transactional
public List<Document>  Search(String Keyword) {
	log.debug("getting Document instance with keyword: " + Keyword );
	try {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Document.class);
		MatchMode matchMode=MatchMode.ANYWHERE;
		criteria.add(Restrictions.like("documentUuid", Keyword, matchMode));		
		List<Document>  document=(List<Document>) criteria.list();
		List<Document>  document1=SearchByName(Keyword);	
		document.addAll(document1);		
		if (document == null) 
		{
			log.debug("get successful, no instance found");
		} else 
		{
			log.debug("get successful, instance found");
		}
		return document;
	} catch (RuntimeException re) {
		log.error("get failed", re);
		throw re;
	}
}
@Transactional
public List<Document>  SearchByName(String Keyword) {
	log.debug("getting Document instance with keyword: " + Keyword );
	try {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Document.class);
		MatchMode matchMode=MatchMode.ANYWHERE;
		
		criteria.add(Restrictions.like("documentName", Keyword, matchMode));
	
		List<Document>  document=(List<Document>) criteria.list();

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
@Transactional
public List<Document>  SearchByPath(String Path) {
	log.debug("getting Document instance with keyword: " +Path );
	try {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Document.class);
		MatchMode matchMode=MatchMode.ANYWHERE;
		criteria.add(Restrictions.like("path", Path, matchMode));
		List<Document>  document=(List<Document>) criteria.list();
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

}
