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

import in.jdsoft.dms.model.Folder;


/**
 * Home object for domain model class Folder.
 * @see in.jdsoft.dms.dao.Folder
 * @author Hibernate Tools
 */

@Repository
public class FolderDAO {

	private static final Log log = LogFactory.getLog(FolderDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	
@Transactional
	public void persist(Folder transientInstance) {
		log.debug("persisting Folder instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

@Transactional
public void update(Folder instance) {
	log.debug("updating instance");
	try {
		sessionFactory.getCurrentSession().update(instance);
		log.debug("update successful");
	} catch (RuntimeException re) {
		log.error("update failed", re);
		throw re;
	}
}


	public void attachDirty(Folder instance) {
		log.debug("attaching dirty Folder instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Folder instance) {
		log.debug("attaching clean Folder instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Transactional
	public void delete(Folder persistentInstance) {
		log.debug("deleting Folder instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Folder merge(Folder detachedInstance) {
		log.debug("merging Folder instance");
		try {
			Folder result = (Folder) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
	
@Transactional
	public Folder findById(Integer id) {
		log.debug("getting Folder instance with id: " + id);
		try {
			Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Folder.class);
			criteria.add(Restrictions.eq("folderId",id));
			Folder instance=(Folder)criteria.uniqueResult();
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
public Folder FolderId(Integer id) {
	log.debug("getting folder instance with folderId: " + id);
	try {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Folder.class);
		criteria.add(Restrictions.eq("parentFolderId",id));
		Folder folder=(Folder)criteria.uniqueResult();
		if (folder == null) {
			log.debug("get successful, no instance found");
		} else {
			log.debug("get successful, instance found");
		}
		return folder;
	} catch (RuntimeException re) {
		log.error("get failed", re);
		throw re;
	}
}



	public List findByExample(Folder instance) {
		log.debug("finding Folder instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("in.jdsoft.dms.model.Folder")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	
@Transactional
	public Folder getFolderDetailsByFolderName(String UserName,Integer ParentId) {
		log.debug("getting folder instance with foldername: " + UserName);
		try {
			Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Folder.class);
			criteria.add(Restrictions.eq("folderName", UserName));
			criteria.add(Restrictions.eq("parentFolderId", ParentId));
			Folder folder=(Folder)criteria.uniqueResult();
			if (folder == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return folder;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}


@Transactional
public Folder getFolderDetailsByUserName(String UserName) {
	log.debug("getting folder instance with foldername: " + UserName);
	try {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Folder.class);
		criteria.add(Restrictions.eq("folderName", UserName));
	
		Folder folder=(Folder)criteria.uniqueResult();
		if (folder == null) {
			log.debug("get successful, no instance found");
		} else {
			log.debug("get successful, instance found");
		}
		return folder;
	} catch (RuntimeException re) {
		log.error("get failed", re);
		throw re;
	}
}


@Transactional
public Folder getFolderCodeByUser(String FolderCode) {
	log.debug("getting folder instance with foldercode: " + FolderCode);
	try {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Folder.class);
		criteria.add(Restrictions.eq("folderCode", FolderCode));
		Folder folder=(Folder)criteria.uniqueResult();
		if (folder == null) {
			log.debug("get successful, no instance found");
		} else {
			log.debug("get successful, instance found");
		}
		return folder;
	} catch (RuntimeException re) {
		log.error("get failed", re);
		throw re;
	}
}


@Transactional
public List<Folder> getFolderCode(String FolderCode) {
	log.debug("getting folder instance with foldercode: " + FolderCode);
	try {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Folder.class);
		criteria.add(Restrictions.eq("folderCode", FolderCode));
		
		List<Folder> folder=(List<Folder>)criteria.list();
		if (folder == null) {
			log.debug("get successful, no instance found");
		} else {
			log.debug("get successful, instance found");
		}
		return folder;
	} catch (RuntimeException re) {
		log.error("get failed", re);
		throw re;
	}
}





@Transactional
public List<Folder> getFolderList() {
	log.debug("getting folder list");
	try {
		List<Folder> results = (List<Folder>) sessionFactory.getCurrentSession().createCriteria(Folder.class).list();
	
		log.debug("document list, result size: " + results.size());
		return results;
	} catch (RuntimeException re) {
		log.error("document list", re);
		throw re;
	}
}



@Transactional
public List<Folder> getFoldersListByUsers(String UserName) {
	log.debug("getting folder instance with name: " + UserName);
	try {
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Folder.class);
		criteria.add(Restrictions.eq("createdBy", UserName));
		List<Folder> folder=(List<Folder>)criteria.list();
		if (folder == null) {
			log.debug("get successful, no instance found");
		} else {
			log.debug("get successful, instance found");
		}
		return folder;
	} catch (RuntimeException re) {
		log.error("get failed", re);
		throw re;
	}
}




}
