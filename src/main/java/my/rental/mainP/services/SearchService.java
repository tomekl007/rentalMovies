package my.rental.mainP.services;

import java.util.List;

import my.rental.mainP.OrderBy;
import my.rental.mainP.domain.Film;
import my.rental.mainP.domain.Klient;

import org.apache.lucene.search.Explanation;


//playing with hibernate search
public interface SearchService {
	
	//Hiberanate Search
		void indexWithHibernate();
		List<Film> search();
		
		List<Klient>searchMultipleFieldsInKlient();
		String checkStemmingIndex();
		void efficientIndexing();
	
		List<String> displayMediumResultsByMatchingTitle(String words, int n);
		
		String displayIMFeelingLuckyByMatchingTitle(String words);
		List<String> displayAllByMatchingTitleWithPagination
		(String words, int pageNumber, int window);
		List<String> displayProjectionByMatchingKlient(String words);
		List<String> displayProjectionAndMetadataByMatchingTitle(String words) ;
		
		List<String> displayAllByMatchingTitleOrderedBy(String words, OrderBy orderBy);
		 List<String> displayItemAndDistributorByMatchingTitle(String words);
		 Explanation explainFirstMatchingItem(String words);
		 List<Film> testMultiFieldQueryParser(String tytul, String rokProdukcji) throws Exception ;
		 void testPhraseQuery(String userInput) throws Exception;
		 void testWildcardQuery(String userInput) throws Exception;
		 void testPrefixQuery(String userInput) throws Exception ;

}
