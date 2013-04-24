package my.rental.mainP.services;

import org.apache.lucene.analysis.Analyzer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.SearchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import my.rental.mainP.dao.RentalDao;
import my.rental.mainP.domain.Cennik;
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
		
		@SuppressWarnings("unchecked")
		List<Klient> klienci = session.createCriteria(Klient.class).list();
		
		for (Klient k : klienci) {
		    ftSession.index(k);  //manually index an item instance
		}
		
		@SuppressWarnings("unchecked")
		List<Cennik> cennik = session.createCriteria(Cennik.class).list();
		
		for (Cennik k : cennik) {
		    ftSession.index(k);  //manually index an item instance
		}
		
		
		
		@SuppressWarnings("unchecked")
		List<Wypozyczenie> wypozyczenia = session.createCriteria(Wypozyczenie.class).list();
		
		for (Wypozyczenie k : wypozyczenia) {
		    ftSession.index(k);  //manually index an item instance
		}
		
		@SuppressWarnings("unchecked")
		List<Gatunek> gatunki = session.createCriteria(Gatunek.class).list();
		
		for (Gatunek k : gatunki) {
		    ftSession.index(k);  //manually index an item instance
		}
		
		//ftSession.getTransaction().commit(); //index are written at commit time
	}
	
	//@Inject
	//FullTextSession ftsI;
	
	private final org.apache.lucene.util.Version version = 
			org.apache.lucene.util.Version.LUCENE_36;
	
	public List<Film> search() {
		//Building the Lucene query
		String searchQuery = "tytulFilmu:arg~ OR rokProdukcji:2000 OR gatunki.gatunekFilmu:akcja";  //query string
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
	
	
	@SuppressWarnings("unchecked")
	public String checkStemmingIndex() {
		Session session = rentalDao.getCurrentSession();
		FullTextSession ftSession = org.hibernate.search.Search.getFullTextSession(session);
		try {
			//build the Lucene query
			final Analyzer entityScopedAnalyzer = ftSession.getSearchFactory().getAnalyzer(Film.class);
			QueryParser parser = new QueryParser(version,"title", entityScopedAnalyzer );  //use Item analyzer
			//search on the exact field
			Query query = parser.parse("tytulFilmu:ryans");  //build Lucene query
			
			//the query is not altered
			////if ( ! "tytulFilmu:ryans".equals( query.toString() ) ) {
			//	return "searching the exact field should not alter the query";
			//}
			
			//return matching results
			org.hibernate.search.FullTextQuery hibQuery = 
				ftSession.createFullTextQuery(query, Film.class);  //return matching results 
			@SuppressWarnings("unchecked") 
			List<Film> results = hibQuery.list();
			System.out.println("after searching not stammed : " + results);
			
			//we find a single matching result
			int exactResultSize = results.size();
			if ( exactResultSize != 1 ) {
				System.out.println( "exact match should only return 1 result" );
			}
			
			//search on the stemmed field
			query = parser.parse("tytulFilmu_stemmer:ryans");   //search same word on the stemmed field
			
			//the query search the stem version of each word
			if ( ! "tytulFilmu_stemmer:ryans".equals( query.toString() ) ) {  //search the stem version of each word
				System.out.println("searching the stemmer field should search the stem" );
			}
			
			//return matching results
			hibQuery = ftSession.createFullTextQuery(query);
			results =  hibQuery.list();
			
			System.out.println("after stemmed search : " + results);
			//we should find more matches than the exact query
			if ( results.size() <= exactResultSize ) {   //more matching results are found 
				return "stemming should return more matches";
			}
			return null; //no error
		}
		catch (ParseException e) {
			throw new SearchException(e);
		}
	}
	
	
	public List<Klient> searchMultipleFieldsInKlient() {
		// Building the Lucene query
		String searchQuery = "admin";
		
		String[] productFields = {"imieKlienta", "login"};   // targeted fields
		
		Map<String,Float> boostPerField = new HashMap<String,Float>(2); // boost factors
		boostPerField.put( "imieKlienta", (float) 4); //assigning different weight
		boostPerField.put( "login", (float) 1); 
		
		QueryParser parser = new MultiFieldQueryParser(   // build query parser
				org.apache.lucene.util.Version.LUCENE_36,
			productFields, 
			new StandardAnalyzer(org.apache.lucene.util.Version.LUCENE_36), 
			boostPerField 
		); 
		org.apache.lucene.search.Query luceneQuery; 
		try { 
			luceneQuery = parser.parse(searchQuery); 
		} 
		catch (ParseException e) { 
			throw new RuntimeException("Unable to parse query: " + searchQuery, e);
		}
		
		Session session = rentalDao.getCurrentSession();
		FullTextSession ftSession = org.hibernate.search.Search.getFullTextSession(session);
		FullTextQuery query = ftSession.createFullTextQuery(luceneQuery, Klient.class);  //return matching Items
		
		query.setFirstResult(0).setMaxResults(20);  //Use pagination
		
		@SuppressWarnings("unchecked")
		final List<Klient> results = query.list();  //execute the query
		System.out.println("find multifield query resutl : " + results);
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
	public void addFilm(Film f) {
		
		rentalDao.saveFilm(f);
		
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


	private static int BATCH_SIZE = 10;
	@Override
	public void efficientIndexing() {
		Session s = rentalDao.getCurrentSession();
		FullTextSession session = org.hibernate.search.Search.getFullTextSession(s);
		
		session.setFlushMode(FlushMode.MANUAL);
		session.setCacheMode(CacheMode.IGNORE);
	//	tx = session.beginTransaction();
		//read the data from the database
		//Scrollable results will avoid loading too many objects in memory
		ScrollableResults results = session.createCriteria( Film.class )
		.scroll( ScrollMode.FORWARD_ONLY );
		
		int index = 0;
		while( results.next() ) {
			index++;
			session.index( results.get(0) );
			if (index % BATCH_SIZE == 0) {
				session.flushToIndexes();
				session.clear();
			}
		}
		//tx.commit();
		
	}
	
	public List<String> displayMediumResultsByMatchingTitle(String words, int n) {
		Session s = rentalDao.getCurrentSession();
		FullTextSession session = org.hibernate.search.Search.getFullTextSession(s);
		
		org.apache.lucene.search.Query luceneQuery = buildLuceneQuery(words, Film.class);
	
		FullTextQuery query = session.createFullTextQuery(luceneQuery, Film.class);
		
		query.setFetchSize(n);   //define fetch size
		ScrollableResults items = query.scroll();  //retrieve a ScrollableResults
		
		List<String> results = new ArrayList<String>();
		try {
			items.beforeFirst();    //go to the first position
			//get the jump to the position before the medium element
			int mediumIndexJump = query.getResultSize() / 2;
		
			items.scroll(mediumIndexJump);  //jump to a specific position
			
			int index = 0;
			while(index < n) {
				if ( items.next() ) {                //load the next element
					Film item = (Film) items.get()[0];  //read the object
					if ( item != null ) {               
						StringBuilder itemInString = new StringBuilder("Item ")
							.append("(").append(item.getTytulFilmu()).append(")")
							.append(" ").append(item.getRokProdukcji());
						results.add(itemInString.toString());
						index++;
					}
					else {
						//mismatch between the index and the database: ignore null entries
					}
				}
				else {
					break;
				}
			}
		}
		finally {
			items.close();   //close resources
		}
		System.out.println("result is : " + results);
		return results;
	}
	
	private org.apache.lucene.search.Query buildLuceneQuery(String words, Class<?> searchedEntity) {
		Analyzer analyzer;
		if (searchedEntity == null) {    //get the most appropriate analyzer
			analyzer = new StandardAnalyzer(version);
		}
		else {
			Session s = rentalDao.getCurrentSession();
			FullTextSession session = org.hibernate.search.Search.getFullTextSession(s);
			analyzer =session.getSearchFactory().getAnalyzer(searchedEntity);
		}
		
		QueryParser parser = new QueryParser(version, "tytulFilmu", analyzer );
		org.apache.lucene.search.Query luceneQuery = null;
		try {
			luceneQuery = parser.parse(words);
		}
		catch (org.apache.lucene.queryParser.ParseException e) {
			throw new IllegalArgumentException("Unable to parse search entry  into a Lucene query", e);
		}
		return luceneQuery;
	}
	
	
	public String displayIMFeelingLuckyByMatchingTitle(String words) {
		Session s = rentalDao.getCurrentSession();
		FullTextSession ftSession = org.hibernate.search.Search.getFullTextSession(s);
		org.apache.lucene.search.Query luceneQuery = buildLuceneQuery(words, Film.class);
		
		FullTextQuery query = ftSession.createFullTextQuery(luceneQuery, Film.class);
		
		Film item =  (Film) query
				.setFirstResult(0).setMaxResults(1)  //use pagination to return one result
				.uniqueResult();  //return one element
		
		StringBuilder itemInString = new StringBuilder("Film ");
		if (item == null) {
			itemInString.append("not found");
		}
		else {
			itemInString.append("(").append(item.getTytulFilmu()).append(")")
			.append(" ").append(item.getRokProdukcji());
		}
		System.out.println("feelingLucky : " + itemInString.toString());
		return itemInString.toString();
	}

	public List<String> displayAllByMatchingTitleWithPagination
	(String words, int pageNumber, int window) {
		Session s = rentalDao.getCurrentSession();
		FullTextSession ftSession = org.hibernate.search.Search.getFullTextSession(s);
		
		org.apache.lucene.search.Query luceneQuery = buildLuceneQuery(words, Film.class);
		
		FullTextQuery query = ftSession.createFullTextQuery(luceneQuery, Film.class);
		
		int resultSize = query.getResultSize();
		@SuppressWarnings("unchecked")
		List<Film> items = query
				.setFirstResult( (pageNumber - 1) * window )  //set first result from the page
				.setMaxResults( window )          //set number of results
				.list();
		
		System.out.println("nr of retived results : " + resultSize);
				
		List<String> results = new ArrayList<String>();
		for (Film item : items) {
			StringBuilder itemInString = new StringBuilder("Item ")
				.append("(").append(item.getTytulFilmu()).append(")")
				.append(" ").append(item.getRokProdukcji());
			results.add( itemInString.toString() );
		}
		return results;
	}
	
	
	public List<String> displayProjectionByMatchingKlient(String words) {
		Session s = rentalDao.getCurrentSession();
		FullTextSession ftSession = org.hibernate.search.Search.getFullTextSession(s);
		
		
		org.apache.lucene.search.Query luceneQuery = buildLuceneQuery(words, Klient.class);
	
		FullTextQuery query = ftSession.createFullTextQuery(luceneQuery, Klient.class);
		
		@SuppressWarnings("unchecked")
		List<Object[]> results = query
				.setProjection("login", "imieKlienta") //set the projected properties
				.list();
		
		System.out.println("result count : " + results);
		
		List<String> endResults = new ArrayList<String>(results.size());
		for (Object[] line : results) {
			endResults.add( line[0].toString() + " "  + line[1].toString());
		}
		return endResults;
	}
	
	
	public List<String> displayProjectionAndMetadataByMatchingTitle(String words) {
		Session s = rentalDao.getCurrentSession();
		FullTextSession ftSession = org.hibernate.search.Search.getFullTextSession(s);
		org.apache.lucene.search.Query luceneQuery = buildLuceneQuery(words, Film.class);
	
		FullTextQuery query = ftSession.createFullTextQuery(luceneQuery, Film.class);
		
		@SuppressWarnings("unchecked")
		List<Object[]> results = query
				.setProjection(
						"tytulFilmu", 
						 
						FullTextQuery.SCORE)  //project the document score
				.list();
		
		List<String> endResults = new ArrayList<String>(results.size());
		for (Object[] line : results) {
				Float score = (Float) line[1];
			String itemView = 
					(String) line[0] +    
					score.toString()
					;  //retrieve the document score
			endResults.add( itemView );
		}
		return endResults;
	}
	
	

}
