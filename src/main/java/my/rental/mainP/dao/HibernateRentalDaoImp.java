package my.rental.mainP.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.sql.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import my.rental.mainP.domain.Aktor;
import my.rental.mainP.domain.Aktor_film;
import my.rental.mainP.domain.Aktor_filmId;
import my.rental.mainP.domain.Cennik;
import my.rental.mainP.domain.Doplata;
import my.rental.mainP.domain.Film;
import my.rental.mainP.domain.Gatunek;
import my.rental.mainP.domain.Klient;
import my.rental.mainP.domain.Plyta;
import my.rental.mainP.domain.Wypozyczenie;



@Component
@Transactional
public class HibernateRentalDaoImp implements RentalDao {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	  private HibernateTemplate template;
	
	
	public Session getCurrentSession(){
		SessionFactory sf = template.getSessionFactory();
		System.out.println("RentalDao : getting currentSessionFactory : " + sf);
		return sf.getCurrentSession();
		
	}
	
	
	
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

	@Override
	public List<Plyta> getAllPlytyForFilm(long filmId) {
		Film film = getFilmById(filmId);
		return film.getPlyty();
		
	}

	@Override
	public List<Wypozyczenie> getAllWypozyczeniaForKlient(long klientId) {
		Klient klient = getKlientById(klientId);
		return klient.getWypozyczenia();
		
	}

	@Override
	public Klient getKlientById(long id) {
		Klient klient = template.get(Klient.class, id);
		System.out.println("getKLientbyId found : "+ klient);
		return klient;
	}
	
	@Override
	public Wypozyczenie getWypozyczenieById(long id) {
		Wypozyczenie wypozyczenie = template.get(Wypozyczenie.class, id);
		System.out.println("getWypozycznieebyId found : "+ wypozyczenie);
		return wypozyczenie;
	}

	@Override
	public Doplata getDoplataForWypozyczenie(long wypozyczenieId) {
		Wypozyczenie wypozyczenie = getWypozyczenieById(wypozyczenieId);
		return wypozyczenie.getDoplata();

	}

	@Override
	public List<Klient> getAllKlients() {
		//String query="SELECT Klient FROM Klient";
		List<Klient> k = template.loadAll(Klient.class);
		System.out.println("find : "+  k);
		return k ; 
		
	}

	@Override
	public List<Film> getAllFilmy() {
		System.out.println("dao.getAllFilmy");
		List<Film> filmy = template.loadAll(Film.class);
		System.out.println("find : "+  filmy);
		return filmy;
	}

	@Override
	public List<Gatunek> getAllGatunki() {
		List<Gatunek> gatunki = template.loadAll(Gatunek.class);
		System.out.println("find all : " + gatunki);
		return gatunki;
	}

	@Override
	public List<Film> getAllFilmyForGatunek(String nazwaGatunku) {
		
		//String query = "FROM Film f, IN (f.gatunki) AS g WHERE g.gatunekFilmu=?";
		String query="FROM Film f inner join fetch f.gatunki as gat WHERE gat.gatunekFilmu=? ";
		//List<Film> filmy = template.find(query);
		List<Film> filmy = template.find(query,nazwaGatunku);
		System.out.println("find filmy for gatunek : "+ filmy);
		return filmy;

	}

	@Override
	public List<Plyta> getAllPlyty() {
		return template.loadAll(Plyta.class);
		
	}

	@Override
	public List<Wypozyczenie> getAllWypozyczeniaForPlyta(long idPlyty) {
		
		//String query="FROM Plyta p inner join fetch p.wypozyczenia WHERE p.film.idFilmu=? ";
		String query="FROM Wypozyczenie w inner join fetch w.plyta as plyta WHERE " +
				"plyta.idPlyty=? ";
		
		List<Wypozyczenie> wyp = template.find(query,idPlyty);
		System.out.println("find wypozyczniea for plyta : "+ wyp);
		return wyp;
		
	}

	@Override
	public void saveWypozyczenie(Wypozyczenie wypozyczenie) {
		template.saveOrUpdate(wypozyczenie);
		
	}

	@Override
	public Klient getKlientByName(String name) {
		String query="FROM Klient k WHERE " +
				"k.login=? ";
		
		List<Klient> k = template.find(query,name);
		System.out.println("find klient  : "+ k);
		return k.get(0);
	}

	@Override
	public void addKlient(Klient klient) {
		template.saveOrUpdate(klient);
		
	}

	@Override
	public List<Wypozyczenie> getAllWypozyczeniaBezZwrotu() {
		String query = "FROM Wypozyczenie w where w.dataZwrotu is null";
		List<Wypozyczenie> wyp = template.find(query);
		System.out.println("find wyp bez daty zwrotu :" + wyp);
		return wyp;
		
		
	}

	@Override
	public void setDataZwrotuForWypozyczenie(Wypozyczenie wypozyczenie) {
		template.saveOrUpdate(wypozyczenie);
		
	}

	@Override
	public List<String> getAllNazwyFilmForGatunek(String nazwaGatunku) {
		List<Film> filyForGatunel = this.getAllFilmyForGatunek(nazwaGatunku);
		List<String> tytuly = new LinkedList<String>();
		for(Film f : filyForGatunel){
			tytuly.add(f.getTytulFilmu());
		}
		
		System.out.println("find tytuly : " + tytuly);
		return tytuly;
	}



	@Override
	public SessionFactory getSessionFactory() {
		return template.getSessionFactory();
	}



	@Override
	public void saveFilm(Film f) {
		System.out.println("saving film : " + f);
		logger.info("saving film : " + f);
		template.saveOrUpdate(f);
		
	}



	@Override
	public List<Wypozyczenie> getAllWypozyczenias() {
		return template.loadAll(Wypozyczenie.class);
		
	}

}
