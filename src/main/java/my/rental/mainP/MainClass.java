package my.rental.mainP;



import my.rental.mainP.dao.HibernateRentalDaoImp;
import my.rental.mainP.domain.Aktor;
import my.rental.mainP.domain.Aktor_film;
import my.rental.mainP.domain.Film;
import my.rental.mainP.services.RentalService;
import my.rental.mainP.services.RentalServiceDeclImpl;

import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("/database-context.xml");
		ctx.refresh();
		
		HibernateRentalDaoImp daoImp = ctx.getBean("databaseFacade", HibernateRentalDaoImp.class);
		
		
		Film film = daoImp.getFilmById(1);
		System.out.println( film);
		
		Aktor aktor = daoImp.getAktorById(1);
		System.out.println(aktor);
		
		aktor.getFilmy().add(new Aktor_film());
		
		System.out.println(daoImp.getAllFilmsForAktor(aktor));
		
		System.out.println(daoImp.getAllAktorsForFilm(film));
		
		String rola = daoImp.getRodzajRoliForSpecyficAktorAndFilm(aktor.getIdAktora(), film.getIdFilmu());
		System.out.println("rola : " + rola );
		
		System.out.println(daoImp.getGatunkiForFilm(film.getIdFilmu()) );
		
		
		System.out.println(daoImp.getFilmyForGatunek("dramat"));
		
		System.out.println(daoImp.getCennikForFilm(film.getIdFilmu()));
		
		System.out.println(daoImp.getFilmyDlaDanejCeny(10.00));

		System.out.println(daoImp.getAllPlytyForFilm(1));
		
		System.out.println(daoImp.getKlientById(1));
		
		System.out.println(daoImp.getAllWypozyczeniaForKlient(1));
		
		System.out.println(daoImp.getDoplataForWypozyczenie(1));
		
		RentalService rs = ctx.getBean("rentalService", RentalService .class);
		
		rs.getAllKlients();
		
		System.out.println(rs.getAllFilmy(2));
		
		
	}

}
