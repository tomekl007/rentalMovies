package my.rental.mainP.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;



//@Table(name="aktor_film")
@Entity
@IdClass(Aktor_filmId.class)
public class Aktor_film implements Serializable {
	
	@Id
	private long idAktora;
	@Id
	private long idFilmu; 
	
	
	@ManyToOne(targetEntity = Aktor.class)
	@JoinColumn(name = "idAktora", updatable = false, insertable = false)
	private Aktor aktor;
    

	
	
    @ManyToOne(targetEntity = Film.class)
    @JoinColumn(name = "idFilmu", updatable = false, insertable = false)
    private Film film;

    @Column(name = "rola")
    private String rola;

	public Aktor getAktor() {
		return aktor;
	}

	public void setAktor(Aktor aktor) {
		this.aktor = aktor;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public String getRola() {
		return rola;
	}

	public void setRola(String rola) {
		this.rola = rola;
	}
	@Override
	public String toString() {
	     
		return aktor + " -> " + rola;
	}
	

}
