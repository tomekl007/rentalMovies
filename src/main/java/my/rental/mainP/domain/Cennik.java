package my.rental.mainP.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
public class Cennik {
	
	@Id
	private String rodzajFilmu;
	
	private double oplataZa1Dzien;
	
	@OneToMany(mappedBy="rodzajFilmu")
	@LazyCollection(LazyCollectionOption.FALSE)
	List<Film>filmy;
	
	public String getRodzajFilmu() {
		return rodzajFilmu;
	}

	public void setRodzajFilmu(String rodzajFilmu) {
		this.rodzajFilmu = rodzajFilmu;
	}

	public double getOplataZa1Dzien() {
		return oplataZa1Dzien;
	}

	public void setOplataZa1Dzien(double oplataZa1Dzien) {
		this.oplataZa1Dzien = oplataZa1Dzien;
	}

	public List<Film> getFilmy() {
		return filmy;
	}

	public void setFilmy(List<Film> filmy) {
		this.filmy = filmy;
	}

	@Override
	public String toString() {
	   return rodzajFilmu + " -->" + oplataZa1Dzien;
	
	}

}
