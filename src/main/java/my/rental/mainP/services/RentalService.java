package my.rental.mainP.services;

import java.util.List;

import my.rental.mainP.domain.Film;
import my.rental.mainP.domain.Klient;

public interface RentalService {
	
	List<Klient> getAllKlients();
	
	List<Film> getAllFilmy(int filmyPerPage);

}
