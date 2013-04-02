package my.rental.mainP.controllers;

import javax.inject.Inject;

import my.rental.mainP.services.RentalService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/gatunki")
public class GatunkiController {

	
	 private RentalService rentalService;

	  @Inject
	  public GatunkiController(RentalService rentalService) {
		  System.out.println("injecting rentalService : " + rentalService);
	    this.rentalService = rentalService;
	  }
	
	
	 @RequestMapping(value="/{nazwaGatunku}", method=RequestMethod.GET)
	  public String getAllFilmyForGatunek(@PathVariable("nazwaGatunku") String nazwaGatunku,
	          Model model) {
		 
		 System.out.println("dostalem: " + nazwaGatunku);
		 //spitterService.getALlFilmyForGatunek
		
	    model.addAttribute("filmy", rentalService.getALlFilmyForGatunek(nazwaGatunku));
	    model.addAttribute("gatunek",nazwaGatunku);
	    model.addAttribute("gatunki", rentalService.getAllGatunki());
	   // return "spittles/view";
	    return "gatunek/film";
	 }

}