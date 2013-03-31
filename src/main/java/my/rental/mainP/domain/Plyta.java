package my.rental.mainP.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;



@Entity
public class Plyta {
	
	@Id
	private long idPlyty;
	
	public long getIdPlyty() {
		return idPlyty;
	}

	public void setIdPlyty(long idPlyty) {
		this.idPlyty = idPlyty;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	@ManyToOne
	@JoinColumn(name = "idFilmu")
	Film film;
	

	@OneToMany(mappedBy = "plyta", cascade=CascadeType.ALL, orphanRemoval=true,
			  fetch = FetchType.LAZY)
	//  @LazyCollection(LazyCollectionOption.FALSE)
	  List<Wypozyczenie> wypozyczenia;
	
	
	
	public List<Wypozyczenie> getWypozyczenia() {
		return wypozyczenia;
	}

	public void setWypozyczenia(List<Wypozyczenie> wypozyczenia) {
		this.wypozyczenia = wypozyczenia;
	}

	@Override
	public String toString() {
	 
	  return " film : " + film + " na plycie : " + idPlyty; 
	}
	
	

}
