package my.rental.mainP.services;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import my.rental.mainP.dao.RentalDao;
import my.rental.mainP.domain.Film;
import my.rental.mainP.domain.Klient;


@Component("rentalService")
//@Transactional(propagation=Propagation.REQUIRED)
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

}
