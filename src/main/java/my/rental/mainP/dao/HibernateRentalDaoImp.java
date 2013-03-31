package my.rental.mainP.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import my.rental.mainP.domain.Aktor;
import my.rental.mainP.domain.Aktor_film;
import my.rental.mainP.domain.Aktor_filmId;
import my.rental.mainP.domain.Cennik;
import my.rental.mainP.domain.Film;
import my.rental.mainP.domain.Gatunek;



@Component
public class HibernateRentalDaoImp implements rentalDao {
	
	
	@Autowired
	  private HibernateTemplate template;

	@Override
	public Film getFilmById(long id) {
		Film film = template.get(Film.class, id);
		System.out.println("getFilmByid found : "+ film);
		return film;
	}

	@Override
	public List<Film> getAllFilmsForAktor(Aktor aktor) {
	
		
		//System.out.println(template.find("SELECT Aktor_film.film FROM Aktor_film af WHERE af.idAktora=?",1));
		
		List<Film> filmy = new ArrayList(aktor.getFilmy());
		
		System.out.println("all films for aktor : " +filmy );
		return filmy;
		
	}
	
	
	
	

	@Override
	public Aktor getAktorById(long id) {
		Aktor aktor = template.get(Aktor.class, id);
		System.out.println("getAktorByid found : "+ aktor);
		return aktor;
	}

	@Override
	public List<Aktor> getAllAktorsForFilm(Film film) {
		  List<Aktor> aktorzy = new ArrayList(film.getAktorzy());
		  System.out.println("getAktorzy for FIlm : " + aktorzy);
		  return aktorzy;
		
	}

	@Override
	public String getRodzajRoliForSpecyficAktorAndFilm(long aktorId,
			long filmId) {
		
		Aktor_filmId id=new Aktor_filmId();
		
		id.setAktor_id(aktorId);
		id.setFilm_id(filmId);
		
		Aktor_film af = template.get(Aktor_film.class, id);
		System.out.println(af.getRola());
		return af.getRola();
	}

	@Override
	public List<Gatunek> getGatunkiForFilm(long filmId) {
		Film film = getFilmById(filmId);
		List<Gatunek> gatunki= film.getGatunki();
		System.out.println(gatunki);
		return gatunki;
	}

	@Override
	public List<Film> getFilmyForGatunek(String gatunekId) {
		Gatunek gatunek = template.get(Gatunek.class, gatunekId);
		return gatunek.getFilmy();
		
	}

	@Override
	public Cennik getCennikForFilm(long filmId) {
		Film film = getFilmById(filmId);
		return film.getRodzajFilmu();
	}

	@Override
	public List<Film> getFilmyDlaDanejCeny(double cena) {
		String query = "FROM Film f WHERE f.rodzajFilmu.oplataZa1Dzien=?";
		List<Film> films = template.find(query,cena);
		return films;
		
		
	}

}
