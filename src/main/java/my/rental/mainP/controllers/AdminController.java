package my.rental.mainP.controllers;

import java.util.List;

import javax.inject.Inject;


import my.rental.mainP.domain.Wypozyczenie;
import my.rental.mainP.services.RentalService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private RentalService rentalService;

	  @Inject
	  public AdminController(RentalService rentalService) {
		  System.out.println("injecting rentalService : " + rentalService);
	    this.rentalService = rentalService;
	  }
	
	
	  @RequestMapping(value = "/listaAllWyp", method = RequestMethod.GET)
	  String getWypozyczeniaBezZwrotu(Model model) {
		  List<Wypozyczenie> wypozyczenia = rentalService.getAllWypozyczeniaBezZwrotu();
		  System.out.println("zaraz dodam wyp : " + wypozyczenia);
		  model.addAttribute("wypozyczenia",wypozyczenia);
		  model.addAttribute("gatunki", rentalService.getAllGatunki());
		  
		  
		  	return "admin/listaWypozyczen";
	}

}
