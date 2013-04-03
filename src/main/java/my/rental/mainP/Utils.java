package my.rental.mainP;

import java.util.LinkedList;
import java.util.List;

import my.rental.mainP.domain.Film;
import my.rental.mainP.domain.Plyta;

public class Utils {
	
	
	
	 public static List<Film> filtrFilmy(List<Film> allFilmy, String filtr) {
			
		  List<Film> wynik = new LinkedList<Film>();
		  for(Film f : allFilmy){
			  if(f.getRodzajFilmu().getRodzajFilmu().equals(filtr))
				  wynik.add(f);
		  }
		  return wynik;
		 // return allFilmy;
	}

	

}
