package my.rental.mainP.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import my.rental.mainP.ShoppingCart;
import my.rental.mainP.Utils;
import my.rental.mainP.domain.Film;
import my.rental.mainP.domain.Klient;
import my.rental.mainP.domain.Plyta;
import my.rental.mainP.domain.Wypozyczenie;
import my.rental.mainP.services.RentalService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
  
  
  @RequestMapping(value="/usunWszystkie", 
          method=RequestMethod.GET)
  	public String usunFilm(Model model) {
	  
	  cart.deleteAllFilmFromShoppingCart();
	  model.addAttribute("filmy",rentalService.getAllFilmy(20));
	  model.addAttribute("gatunki", rentalService.getAllGatunki());
	  
	  	return "home";
  }
  
  
  @RequestMapping(value="/zakonczTransakcje", 
  method=RequestMethod.GET)
  		public String sprawdzDostepnoscPlyt(Model model) {

	  List<Plyta> plyty = rentalService.getDostepnePlytyForFilmy(cart.getZamowioneFilmy());
	  System.out.println("controller get plyty : " + plyty);
	  cart.setZamowionePlyty(new HashSet(plyty));
	  
	  List<Film> filmyDostepne = getDostepne(plyty);
	  System.out.println("dostepne filmy :" + filmyDostepne);
	  List<Film> filmyNIedostepne = getNiedostepne(cart.getZamowioneFilmy(),filmyDostepne);
	  System.out.println("niedostepne filmy :" + filmyNIedostepne);
	 
	  model.addAttribute("dostepne",filmyDostepne);
	  model.addAttribute("niedostepne",filmyNIedostepne);
	  model.addAttribute("gatunki", rentalService.getAllGatunki());

	  	return "klient/zamowieniePodsumowanie";
}
  
  private List<Film> getDostepne(List<Plyta> plyty){
	  
	  List<Film> dostepne = new LinkedList<Film>();
	  	for(Plyta p : plyty){
	  		for(Film f:cart.getZamowioneFilmy())
	  			if(f.equals(p.getFilm()))
	  				dostepne.add(f);
	  				
	  	}
	  	return dostepne;
	  	
  }
  
  private List<Film>  getNiedostepne(List<Film> wanted, List<Film> get){
	  
	  Collection<Film> similar = new HashSet<Film>( wanted );
	  Collection<Film> different = new HashSet<Film>();
	  
	  different.addAll( wanted );
      different.addAll( get );

      similar.retainAll( get );
      different.removeAll( similar );
      return new ArrayList(different);
	  
	  
  }
  
  
  @RequestMapping(value="/zakonczenieTransakcji", 
          method=RequestMethod.GET)
  	public String zakonczTransakcje(Model model) {
	  
	   zapiszZamowienieWBazieDanych();
	  
	  	return "klient/dziekujemyZaZamowienie";
  }


  @Async
  	private void zapiszZamowienieWBazieDanych() {
	  
	  
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); //get logged in username
		
		System.out.println("user logged in : " + name);
		Klient klient = rentalService.findKlientByName(name);
	  	
	  for(Plyta p : cart.getZamowionePlyty() ){
		  Wypozyczenie wypozyczenie = new Wypozyczenie();
		  wypozyczenie.setPlyta(p);
		  wypozyczenie.setKlient(klient);
		  wypozyczenie.setDataWypozyczenia(new Date());
		  
		  rentalService.addWypozyczenie(wypozyczenie);
  		}
	  
	  cart.cleanShoppingCart();
  
  	
  }
 
  
  }