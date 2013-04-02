package my.rental.mainP.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import my.rental.mainP.dao.RentalDao;
import my.rental.mainP.domain.Film;
import my.rental.mainP.domain.Gatunek;
import my.rental.mainP.domain.Klient;
import my.rental.mainP.domain.Plyta;
import my.rental.mainP.domain.Wypozyczenie;


@Component("rentalService")
@Transactional(propagation=Propagation.REQUIRED)
public class RentalServiceDeclImpl implements RentalService {

	RentalDao rentalDao;
	
	@Resource(name="databaseFacade")
	public void setRentalDao(RentalDao rentalDao) {
		System.out.println("injected rantal dao : " + rentalDao);
		this.rentalDao = rentalDao;
	}


	@Override
	public List<Klient> getAllKlients() {
		System.out.println("getAllKlients");
		return rentalDao.getAllKlients();
	}


	@Override
	public List<Film> getAllFilmy(int filmyPerPage) {
		System.out.println("getAllFilmy");
		List<Film> filmy = rentalDao.getAllFilmy();
		System.out.println("find : " + filmy);
		return filmy;
		
	}


	@Override
	public List<Gatunek> getAllGatunki() {
		return rentalDao.getAllGatunki();
	}


	@Override
	public List<Film> getALlFilmyForGatunek(String nazwaGatunku) {
		 return rentalDao.getAllFilmyForGatunek(nazwaGatunku);
	}


	@Override
	public Film findFilmById(long idFilmu) {
		return rentalDao.getFilmById(idFilmu);
		
	}


	@Override
	public List<Plyta> getDostepnePlytyForFilmy(List<Film> zamowioneFilmy) {
		List<Plyta> plyty =  rentalDao.getAllPlyty();
		System.out.println("getDostepnePlytyForFilm");
		Set<Plyta> doWypozyczenia = new HashSet<Plyta>();
		
		for(Plyta p : plyty){
			for(Film f : zamowioneFilmy){
				if(p.getFilm().equals(f)){
				  System.out.println("znalazlem film na cd");
				     List<Wypozyczenie> wypozyczenia = rentalDao.getAllWypozyczeniaForPlyta(p.getIdPlyty());
				     if(wypozyczenia.isEmpty()){
				    	doWypozyczenia.add(p); 
				     }else{
				     System.out.println("wypozyczniea dla plyty" + wypozyczenia);
				     	if(spradzCzyPlytaZostalaOddana(wypozyczenia)){
				     			doWypozyczenia.add(p);
				     	}
				     }
				     		
				    }
				}
				   
			}
			
			
		
		
		return new ArrayList(doWypozyczenia);
	}
	
	private boolean spradzCzyPlytaZostalaOddana(List<Wypozyczenie> wypozyczenia){
		boolean result=true;
		
		for(Wypozyczenie wyp : wypozyczenia){
     		if(wyp.getDataZwrotu()==null){
     			System.out.println("plyta nie jest dostepna");
     			return false;
     			
     		}
		}
		return result;
	}


	@Override
	public void addWypozyczenie(Wypozyczenie wypozyczenie) {
		System.out.println("rentalService.addWypozyczenie : " + wypozyczenie);
		rentalDao.saveWypozyczenie(wypozyczenie);
		
	}


	@Override
	public Klient findKlientByName(String name) {
		
		return rentalDao.getKlientByName(name);
	}


	@Override
	public void saveKlient(Klient klient) {
		rentalDao.addKlient(klient);
		
	}


	@Override
	public List<Wypozyczenie> getAllWypozyczeniaBezZwrotu() {
		return rentalDao.getAllWypozyczeniaBezZwrotu();
		
	}


	@Override
	public Wypozyczenie getWypozyczenieById(long idWypozyczenia) {
		return rentalDao.getWypozyczenieById(idWypozyczenia);
	}


	@Override
	public void setDataZwrotuForWypozyczenie(Wypozyczenie wypozyczenie) {
		rentalDao.setDataZwrotuForWypozyczenie(wypozyczenie);
		
	}

}
