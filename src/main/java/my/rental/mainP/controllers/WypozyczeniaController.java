package my.rental.mainP.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;


import my.rental.mainP.domain.Film;
import my.rental.mainP.domain.Wypozyczenie;
import my.rental.mainP.services.RentalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/wypozyczenia")
public class WypozyczeniaController {
	
	 private RentalService rentalService;

	  @Inject
	  public WypozyczeniaController(RentalService rentalService) {
		  System.out.println("injecting rentalService : " + rentalService);
	    this.rentalService = rentalService;
	  }
	  
	  
	  @RequestMapping(value="/oddaj/{idWypozyczenia}", 
	          method=RequestMethod.GET)
	  	public String wypozyczFilm(@PathVariable("idWypozyczenia") long idWypozyczenia,Model model) {
		  
		  
		  model.addAttribute("gatunki", rentalService.getAllGatunki());
		  Wypozyczenie wyp = rentalService.getWypozyczenieById(idWypozyczenia);
		  Date today = new Date(); 
		  long todayM=today.getTime();
		  
		  long wypozyczenie = wyp.getDataWypozyczenia().getTime();
	
		  long czasOdWypozyczenia = todayM - wypozyczenie;
		  
		  wyp.setDataZwrotu(today);
		  rentalService.setDataZwrotuForWypozyczenie(wyp);
		  
		  int days = (int) ((czasOdWypozyczenia / (1000*60*60*24)));
		  System.out.println("klient trzymal film : " + days);
		  
		  double oplata = wyp.getPlyta().getFilm().getRodzajFilmu().getOplataZa1Dzien();
		  System.out.println("rodzaj filmu : " + oplata);
		  
		  Double kosztZaFilm = oplata * days;
		  List<Film> filmy = new LinkedList<Film>();
		  filmy.add(wyp.getPlyta().getFilm() );
		  
		  model.addAttribute("filmy", filmy);
		  model.addAttribute("koszt",kosztZaFilm);
		  
		  return "wypozyczenie/naliczonaKwota";
	  }
		  

}


 