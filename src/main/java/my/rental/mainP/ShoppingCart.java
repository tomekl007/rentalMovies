package my.rental.mainP;

import java.util.LinkedList;
import java.util.List;

import my.rental.mainP.domain.Film;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("session")
public class ShoppingCart {
	
	
	private List<Film> zamowioneFilmy = new LinkedList<Film>();
	
	public List<Film> getZamowioneFilmy() {
		return zamowioneFilmy;
	}

	public void addFilmToShoppingCart(Film f){
		System.out.println("dodaje do shopping cart film: " + f);
		zamowioneFilmy.add(f);
	}
	
	@Override
	public String toString() {
	   return "shopping cart";
	
	}

	public void deleteFilmFromShoppingCart(Film film) {
		
		for(Film f : zamowioneFilmy){
			System.out.println("film : " + f);
		
			if(f.getTytulFilmu().equals(film.getTytulFilmu()))
				System.out.println("znalazlem !");
		}
		
		boolean result = zamowioneFilmy.remove(film);
		System.out.println("deleting succes ? " + result);
		
	}
 
}
