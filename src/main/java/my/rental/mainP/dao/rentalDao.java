package my.rental.mainP.dao;



import java.util.List;

import my.rental.mainP.domain.Aktor;
import my.rental.mainP.domain.Cennik;
import my.rental.mainP.domain.Film;
import my.rental.mainP.domain.Gatunek;




public interface rentalDao {
	
	Film getFilmById(long id);
	
	List<Film> getAllFilmsForAktor(Aktor aktor);
	
	Aktor getAktorById(long id);
	
	List<Aktor> getAllAktorsForFilm(Film film);
	
	String getRodzajRoliForSpecyficAktorAndFilm(long aktorId, long filmId);
	
	List<Gatunek> getGatunkiForFilm(long filmId);
	List<Film> getFilmyForGatunek(String gatunek);
	
	Cennik getCennikForFilm(long filmId);
	
	List<Film >getFilmyDlaDanejCeny(double cena);
		
	


}
