package my.rental.mainP.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


@Entity
public class Doplata {
	
	
	
	 @Id
	 @Column(name="idWypozyczenia", unique=true, nullable=false)
	 @GeneratedValue(generator="gen")
	 @GenericGenerator(name="gen", strategy="foreign", 
	 parameters=@Parameter(name="property", value="wypozyczenie"))
	private long idWypozyczenia;
	
	public long getIdWypozyczenia() {
		return idWypozyczenia;
	}
	public void setIdWypozyczenia(long idWypozyczenia) {
		this.idWypozyczenia = idWypozyczenia;
	}
	@OneToOne
	@PrimaryKeyJoinColumn
	private Wypozyczenie wypozyczenie;
	
	//@Column(name="doplata")
	private double doplata;
	
	public Wypozyczenie getWypozyczenie() {
		return wypozyczenie;
	}
	public void setWypozyczenie(Wypozyczenie wypozyczenie) {
		this.wypozyczenie = wypozyczenie;
	}
	public double getDoplata() {
		return doplata;
	}
	public void setDoplata(double doplata) {
		this.doplata = doplata;
	}
	
	@Override
	public String toString() {
	    return "doplata w wys. " + doplata + " zl dla wypozycznia nr " + idWypozyczenia;
	}

}
