package my.rental.mainP.services;

import java.util.Date;
import java.util.List;

import my.rental.mainP.domain.Film;
import my.rental.mainP.domain.Gatunek;
import my.rental.mainP.domain.Klient;
import my.rental.mainP.domain.Plyta;
import my.rental.mainP.domain.Wypozyczenie;

public interface RentalService {
	
	List<Klient> getAllKlients();
	
	List<Film> getAllFilmy(int filmyPerPage);
	
	List<Gatunek> getAllGatunki();

	List<Film> getALlFilmyForGatunek(String nazwaGatunku);

	Film findFilmById(long idFilmu);

	List<Plyta> getDostepnePlytyForFilmy(List<Film> zamowioneFilmy);
	
	void addWypozyczenie(Wypozyczenie wypozyczenie);

	Klient findKlientByName(String name);

	void saveKlient(Klient klient);

	List<Wypozyczenie> getAllWypozyczeniaBezZwrotu();

	Wypozyczenie getWypozyczenieById(long idWypozyczenia);

	void setDataZwrotuForWypozyczenie(Wypozyczenie wypozyczenie);

	List<String> getALlNazwyFilmyForGatunek(String nazwaGatunku);
	void indexWithHibernate();
	List<Film> search();
	
	List<Klient>searchMultipleFieldsInKlient();
	String checkStemmingIndex();
	void efficientIndexing();
	void addFilm(Film f);
	List<String> displayMediumResultsByMatchingTitle(String words, int n);
	
	String displayIMFeelingLuckyByMatchingTitle(String words);
	List<String> displayAllByMatchingTitleWithPagination
	(String words, int pageNumber, int window);
	List<String> displayProjectionByMatchingKlient(String words);
	List<String> displayProjectionAndMetadataByMatchingTitle(String words) ;

}
