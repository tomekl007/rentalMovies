package my.rental.mainP.controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import my.rental.mainP.ShoppingCart;
import my.rental.mainP.domain.Film;
import my.rental.mainP.domain.Klient;
import my.rental.mainP.services.RentalService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/klienci")
public class KlienciController {
	
	 @Autowired
	   private ShoppingCart cart;



  private RentalService rentalService;

  @Inject
  public KlienciController(RentalService rentalService) {
	  System.out.println("injecting rentalService : " + rentalService);
    this.rentalService = rentalService;
  }
  
  
  @RequestMapping(value="/konto", 
          method=RequestMethod.GET)
  	public String getKontoDetails(Model model) {
	//  Object principal =  
			 // SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	//if(principal instanceof Klient){
	//	Klient k = (Klient)principal;
	//	System.out.println("klient logged in : " + k.getImieKlienta());
	//}else{
	//	System.out.println("Klient not instance of principal");
	//}
	  
	  User u = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("user logged in : " + u.getPassword() + "\n " 
							+ u.getAuthorities());	  	
	  
	  
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	String name = auth.getName(); //get logged in username
	
	System.out.println("user logged in : " + name);
	  
	 
    	return "klient/kontoDetale";//need to add this view
  }
  
  
  @RequestMapping(value="/wypozycz/{idFilmu}", 
          method=RequestMethod.GET)
  	public String wypozyczFilm(@PathVariable("idFilmu") long idFilmu,Model model) {
	  
	  System.out.println("wypozycz film id : " + idFilmu);
	  //List<Film> filmy = new LinkedList<Film>();
	  Film film = rentalService.findFilmById(idFilmu);
	  cart.addFilmToShoppingCart(film);
	  //filmy.add(film);
	  
	  model.addAttribute("filmy",cart.getZamowioneFilmy());
	  model.addAttribute("gatunki", rentalService.getAllGatunki());
	  System.out.println(cart);
	  
	  
	  
	  	return "klient/zamowienie";
  }
  
  @RequestMapping(value="/usun/{idFilmu}", 
          method=RequestMethod.GET)
  	public String usunFilm(@PathVariable("idFilmu") long idFilmu,Model model) {
	  
	  System.out.println("usun film id : " + idFilmu);
	  model.addAttribute("gatunki", rentalService.getAllGatunki());
	  Film film = rentalService.findFilmById(idFilmu);
	  cart.deleteFilmFromShoppingCart(film);
	  model.addAttribute("filmy",cart.getZamowioneFilmy());
	  
	  	return "klient/zamowienie";
  }
  
  
  
  	

 
  
  }