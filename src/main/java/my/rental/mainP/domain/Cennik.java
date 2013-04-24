package my.rental.mainP.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import my.rental.mainP.hibSearchUtils.PaddedPriceBridge;
import my.rental.mainP.hibSearchUtils.PaddedRoundedPriceBridge;
import my.rental.mainP.hibSearchUtils.ParameterizedPaddedRoundedPriceBridge;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;


@Entity
@Indexed
public class Cennik implements Serializable {
	
	@Id
	@DocumentId
	private String rodzajFilmu;
	
	@Field
	//@FieldBridge(impl=PaddedPriceBridge.class)
	@FieldBridge(
			impl=ParameterizedPaddedRoundedPriceBridge.class,
			
			params= { @Parameter(name="pad", value="3"),
			@Parameter(name="round", value="5") }
			)
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
