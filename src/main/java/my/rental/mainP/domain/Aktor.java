package my.rental.mainP.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Aktor implements Serializable {
	
	@Id
	//@GeneratedValue(strategy = IDENTITY)
	private Long idAktora;
	private String imieAktora;
	private String nazwiskoAktora;
	
	@OneToMany(mappedBy="film")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<Aktor_film> filmy;//=new HashSet<Aktor_film>();
	
	public Set<Aktor_film> getFilmy() {
		return filmy;
	}
	public void setFilmy(Set<Aktor_film> filmy) {
		this.filmy = filmy;
	}
	
	
	
//	@ManyToMany(mappedBy="aktorzy")
//	@LazyCollection(LazyCollectionOption.FALSE)
//	private List<Film> filmy;
//	
//	public List<Film> getFilmy() {
//		return filmy;
//	}
//	public void setFilmy(List<Film> filmy) {
//		this.filmy = filmy;
//	}
	
	
	public Long getIdAktora() {
		return idAktora;
	}
	public void setIdAktora(Long idAktora) {
		this.idAktora = idAktora;
	}
	public String getImieAktora() {
		return imieAktora;
	}
	public void setImieAktora(String imieAktora) {
		this.imieAktora = imieAktora;
	}
	public String getNazwiskoAktora() {
		return nazwiskoAktora;
	}
	public void setNazwiskoAktora(String nazwiskoAktora) {
		this.nazwiskoAktora = nazwiskoAktora;
	}

	
	@Override
	public String toString() {
	
	   return imieAktora +  " " + nazwiskoAktora + idAktora;
	}
	

}
