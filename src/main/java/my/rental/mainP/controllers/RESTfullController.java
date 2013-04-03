package my.rental.mainP.controllers;



import java.util.List;

import javax.inject.Inject;

import my.rental.mainP.domain.Film;
import my.rental.mainP.services.RentalService;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
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


}
