package my.rental.mainP.dao;



import java.util.List;

import my.rental.mainP.domain.Aktor;
import my.rental.mainP.domain.Cennik;
import my.rental.mainP.domain.Doplata;
import my.rental.mainP.domain.Film;
import my.rental.mainP.domain.Gatunek;
import my.rental.mainP.domain.Klient;
import my.rental.mainP.domain.Plyta;
import my.rental.mainP.domain.Wypozyczenie;




public interface RentalDao {
	
	Film getFilmById(long id);
	
	List<Film> getAllFilmsForAktor(Aktor aktor);
	
	Aktor getAktorById(long id);
	
	List<Aktor> getAllAktorsForFilm(Film film);
	
	String getRodzajRoliForSpecyficAktorAndFilm(long aktorId, long filmId);
	
	List<Gatunek> getGatunkiForFilm(long filmId);
	List<Film> getFilmyForGatunek(String gatunek);
	
	Cennik getCennikForFilm(long filmId);
	
	List<Film >getFilmyDlaDanejCeny(double cena);
	
	List<Plyta> getAllPlytyForFilm(long filmId);
	
	List<Wypozyczenie> getAllWypozyczeniaForKlient(long klientId);
	
	Klient getKlientById(long id);
	
	Doplata getDoplataForWypozyczenie(long wypozyczenieId);
		
	Wypozyczenie getWypozyczenieById(long id);
	
	List<Klient> getAllKlients();
	
	List<Film> getAllFilmy();
	
	List<Gatunek >getAllGatunki();

	List<Film> getAllFilmyForGatunek(String nazwaGatunku);


}
