package my.rental.mainP.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Wypozyczenie {
	
	@ManyToOne
	@JoinColumn(name = "idKlienta")
	Klient klient;
	
	@Id
	private long idWypozyczenia ;
	
	//Plyta plyta
	private Date dataWypozyczenia;
	private Date dataZwrotu;
	
	
	@ManyToOne
	@JoinColumn(name = "idPlyty")
	private Plyta plyta;
	
	public Plyta getPlyta() {
		return plyta;
	}
	public void setPlyta(Plyta plyta) {
		this.plyta = plyta;
	}
	public Klient getKlient() {
		return klient;
	}
	public void setKlient(Klient klient) {
		this.klient = klient;
	}
	public long getIdWypozyczenia() {
		return idWypozyczenia;
	}
	public void setIdWypozyczenia(long idWypozyczenia) {
		this.idWypozyczenia = idWypozyczenia;
	}
	public Date getDataWypozyczenia() {
		return dataWypozyczenia;
	}
	public void setDataWypozyczenia(Date dataWypozyczenia) {
		this.dataWypozyczenia = dataWypozyczenia;
	}
	public Date getDataZwrotu() {
		return dataZwrotu;
	}
	public void setDataZwrotu(Date dataZwrotu) {
		this.dataZwrotu = dataZwrotu;
	}
	
	
	
	@Override
	public String toString() {
	   return " wypozyczenie nr: " + idWypozyczenia + " film ->" + plyta.getFilm().getTytulFilmu();
	}

}
