package my.rental.mainP.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.inject.Inject;

import my.rental.mainP.OrderBy;
import my.rental.mainP.dao.RentalDao;
import my.rental.mainP.domain.Cennik;
import my.rental.mainP.domain.Film;
import my.rental.mainP.domain.Gatunek;
import my.rental.mainP.domain.Klient;
import my.rental.mainP.domain.Wypozyczenie;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.WildcardQuery;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.FlushMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.SearchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Component("searchService")
@Transactional(propagation=Propagation.REQUIRED)
public class SearchServiceHib implements SearchService {
	
	@Resource(name="databaseFacade")
	RentalDao rentalDao;
	
	
	//public RentalDao getRentalDao() {
	//	return rentalDao;
	//}

 
	public void setRentalDao(RentalDao rentalDao) {
		System.out.println("->SearchServiceHib--setting rentalDao to  : " + rentalDao);
		this.rentalDao = rentalDao;
	}


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
	
	
	 
	public List<String> displayAllByMatchingTitleOrderedBy(String words, OrderBy orderBy) {
		org.apache.lucene.search.Query luceneQuery = buildLuceneQuery(words, Film.class);
		Session s = rentalDao.getCurrentSession();
		FullTextSession ftSession = org.hibernate.search.Search.getFullTextSession(s);
	
		FullTextQuery query = ftSession.createFullTextQuery(luceneQuery, Film.class);
		Sort sort = null;
		
		switch (orderBy) {
		case ROKPROD:
		{
			//sort by ean
			SortField sortField = new SortField("rokProdukcji", SortField.INT);  //build a SortField
			sort = new Sort(sortField);           //wrap it in a sort
			break;
		}
		case TYTUL_THEN_ROKPROD:
		{
			//sort by title and for equals titles by ean
			SortField[] sortFields = new SortField[2];    //multiple sort fields are possible
			sortFields[0] = new SortField("tytylFilmu", SortField.STRING);
			sortFields[1] = new SortField("rokProdukcji", SortField.INT);
			sort = new Sort(sortFields);
			break;
		}
		case TYTUL_THEN_SCORE:
		{
			//sort by title and for equals titles by ean
			SortField[] sortFields = new SortField[2];
			sortFields[0] = new SortField("tytylFilmu", SortField.STRING);
			
			//use the special SortField
			sortFields[1] = SortField.FIELD_SCORE;  //sort by score after title
			sort = new Sort(sortFields);
			break;
		}
		default:
			assert sort == null: "Unknown OrderBy." + orderBy;
		}
		
		query.setSort( sort );    //assign Sort to the query
		
		@SuppressWarnings("unchecked")
		List<Film> items = query.list();
		
		List<String> results = new ArrayList<String>();
		for (Film item : items) {
			StringBuilder itemInString = new StringBuilder("Item ")
				.append(item.getTytulFilmu())
				.append(" (").append(item.getRokProdukcji() ).append(")");
			results.add( itemInString.toString() );
		}
		System.out.println("sorted : " + results);
		return results;
	}
	
	
	
	public List<String> displayItemAndDistributorByMatchingTitle(String words) {
		org.apache.lucene.search.Query luceneQuery = buildLuceneQuery(words, Film.class);
		Session s = rentalDao.getCurrentSession();
		FullTextSession ftSession = org.hibernate.search.Search.getFullTextSession(s);
		
		FullTextQuery query = ftSession.createFullTextQuery(luceneQuery, Film.class);
		
		Criteria fetchingStrategy = ftSession.createCriteria(Film.class)  //create criteria on targeted entit
											.setFetchMode("rodzajFilmu", FetchMode.JOIN); //set fetching profil
		query.setCriteriaQuery(fetchingStrategy);
		
		@SuppressWarnings("unchecked")
		List<Film> items = query.list();
		
		List<String> results = new ArrayList<String>();
		for (Film item : items) {
			StringBuilder itemInString = new StringBuilder("Item ")
				.append("(").append(item.getTytulFilmu()).append(")")
				.append(" ").append(item.getRokProdukcji())
				.append(" - ").append(item.getRodzajFilmu());  //use pre loaded association
			results.add( itemInString.toString() );
		}
		System.out.println("eager fetching : " + results);
		return results;
	}
	
	
	public Explanation explainFirstMatchingItem(String words) {
		Session s = rentalDao.getCurrentSession();
		FullTextSession ftSession = org.hibernate.search.Search.getFullTextSession(s);
		org.apache.lucene.search.Query luceneQuery = buildLuceneQuery(words, Film.class);
		
		FullTextQuery query = ftSession.createFullTextQuery(luceneQuery, Film.class);

		@SuppressWarnings("unchecked")
		Object[] result = (Object[]) query
								.setProjection(
									FullTextQuery.DOCUMENT_ID, //retrieve the docuemnt id
									FullTextQuery.THIS)
								.setMaxResults(1)
								.uniqueResult();
		
		return query.explain( (Integer) result[0] ); //explain a given document
	}
/*	
	String[] descs = new String[]{"he hits the road as a traveling salesman", "he's not a computer salesman",
			"a traveling salesman touting the wave of the future", "transforms into an aggressive, high-risk salesman",
			"a once-successful salesman"};

		
		public void testTermQuery() throws Exception {
			Session s = rentalDao.getCurrentSession();
			FullTextSession session = org.hibernate.search.Search.getFullTextSession(s);

			try {
				buildIndex( session);

				String userInput = "salesman";

				
				Term term = new Term( "description", userInput );
				TermQuery query = new TermQuery( term );

				System.out.println( query.toString() );

				org.hibernate.search.FullTextQuery hibQuery = session.createFullTextQuery( query, Dvd.class );
				List<Dvd> results = hibQuery.list();

				assert results.size() == 5 : "incorrect hit count";
				assert results.get( 0 ).getDescription().equals( "he's not a computer salesman" );

				for (Dvd dvd : results) {
					System.out.println( dvd.getDescription() );
				}

				for (Object element : session.createQuery( "from " + Dvd.class.getName() ).list()) session.delete( element );
				//tx.commit();
			}
			finally {
				session.close();
			}
		}

		private void buildIndex( FullTextSession session) {
			for (int x = 0; x < descs.length; x++) {
				Dvd dvd = new Dvd();
				dvd.setDescription( descs[x] );
				dvd.setId( x );
				session.save( dvd );
			}
			tx.commit();
			session.clear();
		}
	
	
	
	*/
	
	private static final String[] SPECIALS =
			new String[]{
			"+", "-", "&&", "||", "!", "(", ")", "{", "}",
			"[", "]", "^", "\"", "~", "*", "?", ":", "\\"
			};
			protected String escapeSpecials(String clientQuery)
			{
				String regexOr = "";
					for (String special : SPECIALS)
					{
						regexOr += (special
								.equals(SPECIALS[0]) ? "" : "|") + "\\"
								+ special.substring(0, 1);
					}
					clientQuery = clientQuery
							.replaceAll("(?<!\\\\)(" + regexOr + ")",
									"\\\\$1");
					return clientQuery.trim();
			}	
			
			
			public List<Film> testMultiFieldQueryParser(String tytul, String rokProdukcji) throws Exception {
				Session s = rentalDao.getCurrentSession();
				FullTextSession session = org.hibernate.search.Search.getFullTextSession(s);

				//try {
					//buildIndex( session, tx );

					String query0 = tytul;
					String query1 = rokProdukcji;
					String field0 = "tytulFilmu";
					String field1 = "gatunki.gatunekFilmu";

					String[] fields = new String[]{field0, field1};
					String[] queries = new String[]{query0, query1};
					

					Query query = MultiFieldQueryParser.parse(version, queries, fields, new StopAnalyzer(version) );
					System.out.println( query.toString() );

					org.hibernate.search.FullTextQuery hibQuery = session.createFullTextQuery( query, Film.class );
					List<Film> results = hibQuery.list();

					//assert results.size() == 9 : "incorrect hit count";
					//assert results.get( 0 ).getTitle().equals( "Films of Faith Collection" );

					for (Film dvd : results) {
						System.out.println( dvd.getTytulFilmu() );
					}

					//for (Object element : session.createQuery( "from " + Dvd.class.getName() ).list()) session.delete( element );
					//tx.commit();
					return results;
				//}
				//finally {
				//	session.close();
				//}
			}
			
			
			public void testPhraseQuery(String userInput) throws Exception {
				System.out.println("teste phrase query");
				Session s = rentalDao.getCurrentSession();
				FullTextSession session = org.hibernate.search.Search.getFullTextSession(s);

			//	try {
				//( session, tx );

				//	String userInput = "star trek";
					StringTokenizer st = new StringTokenizer( userInput, " " );

					//tx = session.beginTransaction();
					PhraseQuery query = new PhraseQuery();
					query.setSlop(4);//now words in userInput could be in different order
					while (st.hasMoreTokens()) {
						query.add( new Term( "tytulFilmu", st.nextToken() ) );
					}
					System.out.println( query.toString() );

					org.hibernate.search.FullTextQuery hibQuery = session.createFullTextQuery( query, Film.class );
					List<Film> results = hibQuery.list();

				//	assert results.size() == 3 : "incorrect hit count";
				//	assert results.get( 0 ).getDescription().equals( "Star Trek The Next Generation" );

					System.out.println("results : ");
					for (Film f : results) {
						System.out.println( f.getTytulFilmu());
					}

					//for (Object element : session.createQuery( "from " + Dvd.class.getName() ).list()) session.delete( element );
				//	tx.commit();
			//	}
				//finally {
				//	session.close();
			//	}
			}
			
			public void testWildcardQuery(String userInput) throws Exception {
				System.out.println("teste phrase query");
				Session s = rentalDao.getCurrentSession();
				FullTextSession session = org.hibernate.search.Search.getFullTextSession(s);

			
					WildcardQuery query = new WildcardQuery( new Term( "tytulFilmu", userInput ) );
					System.out.println( query.toString() );

					org.hibernate.search.FullTextQuery hibQuery = 
							session.createFullTextQuery( query, Film.class );
					List<Film> results = hibQuery.list();

					//assert results.size() == 8 : "incorrect hit count";
					for (Film dvd : results) {
						
						System.out.println( dvd.getTytulFilmu() );
					}
				
			
			}
			
			public void testPrefixQuery(String userInput) throws Exception {
				System.out.println("test prefix query");
				System.out.println(rentalDao == null ? "rentalDao is null" : rentalDao) ;
				Session s = rentalDao.getCurrentSession();
				FullTextSession session = org.hibernate.search.Search.getFullTextSession(s);

				//String userInput = "sea";

			
				PrefixQuery query = new PrefixQuery( new Term( "tytulFilmu", userInput ) );
				System.out.println( query.toString() );

				org.hibernate.search.FullTextQuery hibQuery = 
						session.createFullTextQuery( query, Film.class );
				List<Film> results = hibQuery.list();

				for (Film dvd : results) {
					
					System.out.println( dvd.getTytulFilmu() );
				}

			}



}
