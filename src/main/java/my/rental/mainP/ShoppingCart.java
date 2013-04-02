package my.rental.mainP;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import my.rental.mainP.domain.Film;
import my.rental.mainP.domain.Plyta;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("session")
public class ShoppingCart {
	
	
	private Set<Film> zamowioneFilmy = new HashSet<Film>();
	private Set<Plyta> zamowionePlyty = new HashSet<Plyta>();
	
	public Set<Plyta> getZamowionePlyty() {
		return zamowionePlyty;
	}

	public void setZamowionePlyty(Set<Plyta> zamowionePlyty) {
		System.out.println("set zamowione plyty : " + zamowionePlyty);
		this.zamowionePlyty = zamowionePlyty;
	}

	public List<Film> getZamowioneFilmy() {
		return new ArrayList(zamowioneFilmy);
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
		
			if(f.getTytulFilmu().equals(film.getTytulFilmu())){
				System.out.println("znalazlem !");
				zamowioneFilmy.remove(f);
				break;
			}
		}
		
		boolean result = zamowioneFilmy.remove(film);
		System.out.println("deleting succes ? " + result);
		
	}

	public void deleteAllFilmFromShoppingCart() {
		zamowioneFilmy = new HashSet<Film>();
		
	}

	public void cleanShoppingCart() {
		zamowioneFilmy.clear();
		zamowionePlyty.clear();
		
	}
 
}
