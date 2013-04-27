package my.rental.mainP.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.sql.rowset.serial.SerialArray;

import my.rental.mainP.OrderBy;
import my.rental.mainP.dao.HibernateSearchExperiment;
import my.rental.mainP.domain.Film;
import my.rental.mainP.services.RentalService;
import my.rental.mainP.services.SearchService;

import org.apache.lucene.search.Explanation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import my.rental.mainP.OrderBy;



@Controller
@RequestMapping("/hs")
public class HibernateSearchConroller {

	
	//@Inject
	//HibernateSearchExperiment hse = new HibernateSearchExperiment();
	
	
	private RentalService rentalService;
	
	 @Inject
	  public HibernateSearchConroller(RentalService rentalService) {
		  System.out.println("injecting rentalService : " + rentalService);
	    this.rentalService = rentalService;
	  }
	 
	 private SearchService searchService;
	
	 @Inject
	public void setSearchService(SearchService searchService) {
		 System.out.println("->injecting search service : " + searchService);
		this.searchService = searchService;
	}

	@RequestMapping(value={"/"})
	public String getMenu(){
		return "hibernateSearch";
	}
	
	@RequestMapping(value={"/search"})
	public String search(){
		searchService.search();
		return "hibernateSearch";
	}
	
	
	@RequestMapping(value={"/index"})
	public String index(){
		//hse.indexWithHibernate();
		searchService.indexWithHibernate();
		return "hibernateSearch";
	}
	
	
	
	@RequestMapping(value={"/searchMultiple"})
	public String searchMultipleKlients(){
		//hse.indexWithHibernate();
		searchService.searchMultipleFieldsInKlient();
		return "hibernateSearch";
	}
	
	@RequestMapping(value={"/searchStemmed"})
	public String searchStemmedFilms(){
		//hse.indexWithHibernate();
		searchService.checkStemmingIndex();
		return "hibernateSearch";
	}
	
	
	
	@RequestMapping(value={"/eIndex"})
	public String addFilmToDataBase(){
		System.out.println("efficient indexing");
		searchService.efficientIndexing();
		return "hibernateSearch";
	}
	
	@RequestMapping(value={"/searchScroll"})
	public String searchScrollable(){
		System.out.println("efficient indexing");
		searchService.displayMediumResultsByMatchingTitle("Batma~", 5);
		return "hibernateSearch";
	}
	
	@RequestMapping(value={"/luckyShot"})
	public String luckySearch(){
		searchService.displayIMFeelingLuckyByMatchingTitle("Batma~");
		return "hibernateSearch";
	}
	
	@RequestMapping(value={"/pagination"})
	public String getWithPagination(){
		int pageNr = 1;
		int window = 1;//only for testing !
		for(;pageNr<3;pageNr++){
		List<String> res = searchService.displayAllByMatchingTitleWithPagination("Batma~", pageNr , window);
		 System.out.println("for page nr : " + pageNr + " result : " + res);
		
		}
		return "hibernateSearch";
	}
	

	@RequestMapping(value={"/projectionKlient"})
	public String displayProjectionForKlient(){
		List<String> res = searchService.displayProjectionByMatchingKlient("admin");
		
		System.out.println("result of projetion : " + res);
		return "hibernateSearch";
	}
	
	@RequestMapping(value={"/score"})
	public String displayScoreForFilm(){
		List<String> res = searchService.displayProjectionAndMetadataByMatchingTitle("batman") ;
		
		System.out.println("result of score : " + res);
		return "hibernateSearch";
	}
	

	@RequestMapping(value={"/sort"})
	public String displaySortedFilm(){
		List<String> res = searchService.displayAllByMatchingTitleOrderedBy("batman", OrderBy.TYTUL_THEN_SCORE);
		
		System.out.println("result of score : " + res);
		return "hibernateSearch";
	}
	
	@RequestMapping(value={"/eager"})
	public String fetchFilmAndGatunki(){
		List<String> res = searchService.displayItemAndDistributorByMatchingTitle("batman");
		
		System.out.println("result of eager fetch : " + res);
		return "hibernateSearch";
	}
	
	@RequestMapping(value={"/explain"})
	public String getFilmAndExplain(){
		Explanation ex = searchService.explainFirstMatchingItem("batman");
		
		System.out.println("explanation : " + ex.getDescription());
		return "hibernateSearch";
	}
	

	@RequestMapping(value={"/searchParam/{tytulFilmu}/{rokProdukcji}"})
	public String searchFilmByParam(@PathVariable String tytulFilmu, @PathVariable String rokProdukcji){
		System.out.println("get tytul : " + tytulFilmu);
		System.out.println("get rok prod : " + rokProdukcji);
		
		try {
		//	List<Film> res =  rentalService.testMultiFieldQueryParser(tytulFilmu, rokProdukcji);
		//batman rise or rise batman:
			//rentalService.testPhraseQuery(tytulFilmu);
			
			//a*
		//	rentalService.testWildcardQuery(tytulFilmu);
			//ar
		//	searchService.testPrefixQuery(tytulFilmu);
			searchService.testFuzzyQuery(tytulFilmu);
			searchService.testNumericRangeQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "hibernateSearch";
	}
	
	
}
