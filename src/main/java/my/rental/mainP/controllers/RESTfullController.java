package my.rental.mainP.controllers;



import java.util.List;

import javax.inject.Inject;

import my.rental.mainP.domain.Film;
import my.rental.mainP.domain.Gatunek;
import my.rental.mainP.domain.Klient;
import my.rental.mainP.domain.Wypozyczenie;
import my.rental.mainP.services.RentalService;



import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/restfull")
public class RESTfullController {
	
	private RentalService rentalService;

	  @Inject
	  public RESTfullController(RentalService rentalService) {
		  System.out.println("injecting rentalService : " + rentalService);
	    this.rentalService = rentalService;
	  }

	
	
	  @RequestMapping(value = "/allFilmy", method = RequestMethod.GET)//,
           //   headers = {"Accept=text/xml, application/json"})
	  	public @ResponseBody List<Film>
	  	getAllFilmy() {
		return rentalService.getAllFilmy(100);  
		
	  
	  }
	  
	  
	  @RequestMapping(value = "/allGatunki", method = RequestMethod.GET)//,
      //   headers = {"Accept=text/xml, application/json"})
 	public @ResponseBody List<Gatunek>
 	getAllGatunki() {
	return rentalService.getAllGatunki();  		
	  }
	  
	  @RequestMapping(value = "/klient/{username}", method = RequestMethod.GET, 
              headers = {"Accept=text/xml, application/json, text/html"})
	  public @ResponseBody 
		Klient getKlientByName(@PathVariable String username) {
		 
		  return rentalService.findKlientByName(username);
	  	}
	  
	  
	  @RequestMapping(value="/search", method={RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST},
	  produces = MediaType.APPLICATION_JSON_VALUE )
	  public @ResponseBody 
		List<Film>  getAllFilmyForGatunek(@RequestBody String nazwaGatunku
	          ) {
		 
		 System.out.println("dostalem w rest: " + nazwaGatunku);
		 String delimiter = "=";
		  /* given string will be split by the argument delimiter provided. */
		
		 String[] nazwa = nazwaGatunku.split(delimiter);
		 //spitterService.getALlFilmyForGatunek
		 System.out.println("po oczysczeniu : "+ nazwa[1]);
		
	    return rentalService.getALlFilmyForGatunek(nazwa[1]);
	   
	   // return "spittles/view";
	    
	 }
	  
	  @RequestMapping(value="/search/{nazwaGatunku}", method=RequestMethod.GET,
			  produces = MediaType.APPLICATION_JSON_VALUE )
			  public @ResponseBody 
				List<String>  getAllFilmyForGatunekParam(@PathVariable String nazwaGatunku
			          ) {
				 System.out.println("nazwaGat param :" + nazwaGatunku);
				
				// List<String> r = 
					return	 rentalService.getALlNazwyFilmyForGatunek(nazwaGatunku);
//						 StringBuilder buffer;
//				 String delimeter = ";";
//				 for(String s : r){
//					 buffer.append(s);
//					 buffer.append(delimeter);
//				 }
//				
//			    return buffer.toString();
			   
			   // return "spittles/view";
			    
			 }
	  
	  


}
