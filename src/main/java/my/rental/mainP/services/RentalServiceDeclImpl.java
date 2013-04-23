package my.rental.mainP.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import my.rental.mainP.dao.RentalDao;
import my.rental.mainP.domain.Film;
import my.rental.mainP.domain.Gatunek;
import my.rental.mainP.domain.Klient;
import my.rental.mainP.domain.Plyta;
import my.rental.mainP.domain.Wypozyczenie;


@Component("rentalService")
@Transactional(propagation=Propagation.REQUIRED)
public class RentalServiceDeclImpl implements RentalService {

	RentalDao rentalDao;
	
	
	 
   /*private SessionFactory sessionFactory;
	 
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	//@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		System.out.println("autowired sessionFactory : " + sessionFactory);
		this.sessionFactory = sessionFactory;
	}*/
	
	@Transactional
	public void indexWithHibernate() {
		ApplicationContext ac;
		
		
		System.out.println("indexing with hibernate");
		//System.out.println(sessionFactory==null? "sesF is null" : sessionFactory);
		
		Session session = rentalDao.getSessionFactory().getCurrentSession();
		System.out.println(session==null? "session is null" : session);
		//wrap a Session object
		
		FullTextSession ftSession = org.hibernate.search.Search.getFullTextSession(session);
		//ftSession.getTransaction().begin();
		
		@SuppressWarnings("unchecked")
		List<Film> items = session.createCriteria(Film.class).list();
		
		for (Film item : items) {
		    ftSession.index(item);  //manually index an item instance
		}
		
		//ftSession.getTransaction().commit(); //index are written at commit time
	}
	
	
	public List<Film> search() {
		//Building the Lucene query
		String searchQuery = "tytulFilmu:Batman OR rokProdukcji:2000";  //query string
		QueryParser parser = new QueryParser(org.apache.lucene.util.Version.LUCENE_36,
				"title",  //default field 
				new StandardAnalyzer(org.apache.lucene.util.Version.LUCENE_36) //analyzer used
		);

		Query luceneQuery;
		try {
		    luceneQuery = parser.parse(searchQuery);  //build Lucene query
		}
		catch (ParseException e) {
		    throw new RuntimeException("Unable to parse query: " + searchQuery, e);
		}
		
		Session session = rentalDao.getSessionFactory().getCurrentSession();
		FullTextSession ftSession = org.hibernate.search.Search.getFullTextSession(session);
		
		FullTextQuery query =  ftSession.createFullTextQuery(luceneQuery, Film.class);
		
		@SuppressWarnings("unchecked")
		final List<Film> results = query.list();  //execute the query
		System.out.println("results :" + results );
		return results;
	}
	
	
	
	//Session session = sessionFactory.getCurrentSession();
	
	@Resource(name="databaseFacade")
	public void setRentalDao(RentalDao rentalDao) {
		System.out.println("injected rantal dao : " + rentalDao);
		this.rentalDao = rentalDao;
	}


	@Override
	public List<Klient> getAllKlients() {
		System.out.println("getAllKlients");
		return rentalDao.getAllKlients();
	}


	@Override
	public List<Film> getAllFilmy(int filmyPerPage) {
		System.out.println("getAllFilmy");
		List<Film> filmy = rentalDao.getAllFilmy();
		System.out.println("find : " + filmy);
		return filmy;
		
	}


	@Override
	public List<Gatunek> getAllGatunki() {
		return rentalDao.getAllGatunki();
	}


	@Override
	public List<Film> getALlFilmyForGatunek(String nazwaGatunku) {
		 return rentalDao.getAllFilmyForGatunek(nazwaGatunku);
	}


	@Override
	public Film findFilmById(long idFilmu) {
		return rentalDao.getFilmById(idFilmu);
		
	}


	@Override
	public List<Plyta> getDostepnePlytyForFilmy(List<Film> zamowioneFilmy) {
		List<Plyta> plyty =  rentalDao.getAllPlyty();
		System.out.println("getDostepnePlytyForFilm");
		Set<Plyta> doWypozyczenia = new HashSet<Plyta>();
		
		for(Plyta p : plyty){
			for(Film f : zamowioneFilmy){
				if(p.getFilm().equals(f)){
				  System.out.println("znalazlem film na cd");
				     List<Wypozyczenie> wypozyczenia = rentalDao.getAllWypozyczeniaForPlyta(p.getIdPlyty());
				     if(wypozyczenia.isEmpty()){
				    	doWypozyczenia.add(p); 
				     }else{
				     System.out.println("wypozyczniea dla plyty" + wypozyczenia);
				     	if(spradzCzyPlytaZostalaOddana(wypozyczenia)){
				     			doWypozyczenia.add(p);
				     	}
				     }
				     		
				    }
				}
				   
			}
			
			
		
		
		return new ArrayList(doWypozyczenia);
	}
	
	private boolean spradzCzyPlytaZostalaOddana(List<Wypozyczenie> wypozyczenia){
		boolean result=true;
		
		for(Wypozyczenie wyp : wypozyczenia){
     		if(wyp.getDataZwrotu()==null){
     			System.out.println("plyta nie jest dostepna");
     			return false;
     			
     		}
		}
		return result;
	}


	@Override
	public void addWypozyczenie(Wypozyczenie wypozyczenie) {
		System.out.println("rentalService.addWypozyczenie : " + wypozyczenie);
		rentalDao.saveWypozyczenie(wypozyczenie);
		
	}


	@Override
	public Klient findKlientByName(String name) {
		
		return rentalDao.getKlientByName(name);
	}


	@Override
	public void saveKlient(Klient klient) {
		rentalDao.addKlient(klient);
		
	}


	@Override
	public List<Wypozyczenie> getAllWypozyczeniaBezZwrotu() {
		return rentalDao.getAllWypozyczeniaBezZwrotu();
		
	}


	@Override
	public Wypozyczenie getWypozyczenieById(long idWypozyczenia) {
		return rentalDao.getWypozyczenieById(idWypozyczenia);
	}


	@Override
	public void setDataZwrotuForWypozyczenie(Wypozyczenie wypozyczenie) {
		rentalDao.setDataZwrotuForWypozyczenie(wypozyczenie);
		
	}


	@Override
	public List<String> getALlNazwyFilmyForGatunek(String nazwaGatunku) {
		return rentalDao.getAllNazwyFilmForGatunek(nazwaGatunku);
	}

}
