package my.rental.mainP.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;


@Entity
@Embeddable
public class Doplata implements Serializable {
	
	
	
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
	
	@Field(index=Index.UN_TOKENIZED)
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
