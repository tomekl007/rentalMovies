package my.rental.mainP.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Wypozyczenie {
	
	@ManyToOne
	@JoinColumn(name = "idKlienta")
	Klient klient;
	
	@Id
	@GeneratedValue
    @Column(name="idWypozyczenia")
	private long idWypozyczenia;
	
	//Plyta plyta
	private Date dataWypozyczenia;
	private Date dataZwrotu;
	
	
	@ManyToOne
	@JoinColumn(name = "idPlyty")
	private Plyta plyta;
	
	@OneToOne(mappedBy="wypozyczenie", cascade=CascadeType.ALL)
	Doplata doplata;
	
	public Doplata getDoplata() {
		return doplata;
	}
	public void setDoplata(Doplata doplata) {
		this.doplata = doplata;
	}
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
