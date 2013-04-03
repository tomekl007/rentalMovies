package my.rental.mainP.controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import my.rental.mainP.Utils;
import my.rental.mainP.domain.Film;
import my.rental.mainP.services.RentalService;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class HomeController {

  private RentalService rentalService;

  @Inject
  public HomeController(RentalService rentalService) {
	  System.out.println("injecting rentalService : " + rentalService);
    this.rentalService = rentalService;
  }
  
  @RequestMapping(value={"/","/home"}, method=RequestMethod.GET)
  public String showHomePage(Map<String, Object> model) {
	  System.out.println("getting all filmy : " + 
			  		rentalService.getAllFilmy(filmyPerPage));
	  
	  List<Film> nowosci = Utils.filtrFilmy(rentalService.getAllFilmy(filmyPerPage),"nowosc");
    model.put("filmy", 
    		nowosci);
    
    model.put("gatunki", rentalService.getAllGatunki());
    
    
    return "home";
  }
  
  
  @RequestMapping(value={"/polecane"}, method=RequestMethod.GET)
  public String showPolecaneFilmy(Map<String, Object> model) {
	  System.out.println("getting all filmy : " + 
			  		rentalService.getAllFilmy(filmyPerPage));
	  
	  List<Film> nowosci = Utils.filtrFilmy(rentalService.getAllFilmy(filmyPerPage),"hit");
    model.put("filmy", 
    		nowosci);
    
    model.put("gatunki", rentalService.getAllGatunki());
    
    
    return "polecaneFilmy";
  }
  
  
  
  @RequestMapping(value={"/kontakt"}, method=RequestMethod.GET)
  public String shoKontaktPage(Map<String, Object> model) {
	 
	String kontakt = "500 134 424 Krakow ";//it should not be hardcoded  
    
    model.put("gatunki", rentalService.getAllGatunki());
    model.put("kontakt",kontakt);
    
    
    return "kontaktPage";
  }
  
//  @RequestMapping(value={"/menu"}, method=RequestMethod.GET)
//  public String showAllGatunki(Map<String, Object> model) {
//	  
//	  System.out.println("in menu controller");
//	  
//    
//    return "home";
//  }


 


//<start id="spittlesPerPage"/> 
  public static final int DEFAULT_FILMY_PER_PAGE = 25;
  
  private int filmyPerPage = DEFAULT_FILMY_PER_PAGE ;
  
  public void setFilmyPerPage(int filmyPerPage) {
    this.filmyPerPage = filmyPerPage;
  }
  
  public int getFilmyPerPage() {
    return filmyPerPage;
  }
  //<end id="spittlesPerPage"/> 
}
