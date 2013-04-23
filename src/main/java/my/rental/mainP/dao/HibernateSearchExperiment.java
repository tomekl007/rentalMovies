package my.rental.mainP.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import my.rental.mainP.domain.Film;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.search.impl.FullTextSessionImpl;

@Component
@Transactional
public class HibernateSearchExperiment {
	
	//@PersistenceContext
	//EntityManager em;
	
	//FullTextEntityManager ft = Search.getFullTextEntityManager(em);
	
	/*@PostConstruct
	public void indexAllRecords(){
		ft.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<Film> filmy =  em.createQuery("Select f from Film f").getResultList();
		for(Film f : filmy)
			ft.index(f);
		ft.getTransaction().commit();
	}*/
	
	//public List<Film> search(){
	//	
	//}
	
	 
     private SessionFactory sessionFactory;
	 
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		System.out.println("autowired sessionFactory : " + sessionFactory);
		this.sessionFactory = sessionFactory;
	}

	public void indexWithHibernate() {
		System.out.println("indexing with hibernate");
		System.out.println(sessionFactory==null? "sesF is null" : sessionFactory);
		Session session = sessionFactory.getCurrentSession();
		System.out.println(session==null? "session is null" : session);
		//wrap a Session object
		FullTextSession ftSession =  
				org.hibernate.search.Search.getFullTextSession(session);
		ftSession.getTransaction().begin();
		
		@SuppressWarnings("unchecked")
		List<Film> items = session.createCriteria(Film.class).list();
		
		for (Film item : items) {
		    ftSession.index(item);  //manually index an item instance
		}
		
		ftSession.getTransaction().commit(); //index are written at commit time
	}

}
