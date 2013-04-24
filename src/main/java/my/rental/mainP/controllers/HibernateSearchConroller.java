package my.rental.mainP.controllers;

import javax.inject.Inject;

import my.rental.mainP.dao.HibernateSearchExperiment;
import my.rental.mainP.domain.Film;
import my.rental.mainP.services.RentalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



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
	
	@RequestMapping(value={"/"})
	public String getMenu(){
		return "hibernateSearch";
	}
	
	@RequestMapping(value={"/search"})
	public String search(){
		rentalService.search();
		return "hibernateSearch";
	}
	
	
	@RequestMapping(value={"/index"})
	public String index(){
		//hse.indexWithHibernate();
		rentalService.indexWithHibernate();
		return "hibernateSearch";
	}
	
	
	
	@RequestMapping(value={"/searchMultiple"})
	public String searchMultipleKlients(){
		//hse.indexWithHibernate();
		rentalService.searchMultipleFieldsInKlient();
		return "hibernateSearch";
	}
	
	@RequestMapping(value={"/searchStemmed"})
	public String searchStemmedFilms(){
		//hse.indexWithHibernate();
		rentalService.checkStemmingIndex();
		return "hibernateSearch";
	}
	
	
	
	@RequestMapping(value={"/eIndex"})
	public String addFilmToDataBase(){
		System.out.println("efficient indexing");
		rentalService.efficientIndexing();
		return "hibernateSearch";
	}
	
	
	
}
