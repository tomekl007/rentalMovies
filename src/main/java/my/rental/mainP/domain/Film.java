package my.rental.mainP.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
public class Film {
	@Id
	private Long idFilmu;
	private String tytulFilmu;
	
	@ManyToOne
	@JoinColumn(name = "rodzajFilmu")
	private Cennik rodzajFilmu;
	private Integer rokProdukcji;
	
	
	@OneToMany(mappedBy="aktor")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<Aktor_film> aktorzy;//=new HashSet<Aktor_film>();
	

	public Set<Aktor_film> getAktorzy() {
		return aktorzy;
	}
	public void setAktorzy(Set<Aktor_film> aktorzy) {
		this.aktorzy = aktorzy;
	}
	
	

	  @OneToMany(mappedBy = "film", cascade=CascadeType.ALL, orphanRemoval=true,
			  fetch = FetchType.EAGER)
	  @LazyCollection(LazyCollectionOption.FALSE)
	  List<Plyta> plyty;
	
	
	public List<Plyta> getPlyty() {
		return plyty;
	}
	public void setPlyty(List<Plyta> plyty) {
		this.plyty = plyty;
	}



	@ManyToMany
	@JoinTable(name = "film_gatunek",
			joinColumns = { @JoinColumn(name="idFilmu",nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "gatunekFilmu",
					nullable = false, updatable = false) } )
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Gatunek> gatunki;
	
	
	public List<Gatunek> getGatunki() {
		return gatunki;
	}
	public void setGatunki(List<Gatunek> gatunki) {
		this.gatunki = gatunki;
	}
	
	
	
	//@ManyToMany
	
	
	//	@JoinTable(name = "aktor_film", 
//	joinColumns = { 
//			@JoinColumn(name = "idFilmu", nullable = false, updatable = false) }, 
//	
//	inverseJoinColumns = { @JoinColumn(name = "idAktora",
//			nullable = false, updatable = false) }
//	
//			)
//			
//	@LazyCollection(LazyCollectionOption.FALSE)
//	private List<Aktor> aktorzy;
//	
////	
//	public List<Aktor> getAktorzy() {
//		return aktorzy;
//	}
//	public void setAktorzy(List<Aktor> aktorzy) {
//		this.aktorzy = aktorzy;
//	}
	public Long getIdFilmu() {
		return idFilmu;
	}
	public void setIdFilmu(Long idFilmu) {
		this.idFilmu = idFilmu;
	}
	public String getTytulFilmu() {
		return tytulFilmu;
	}
	public void setTytulFilmu(String tytulFilmu) {
		this.tytulFilmu = tytulFilmu;
	}
	public Cennik getRodzajFilmu() {
		return rodzajFilmu;
	}
	public void setRodzajFilmu(Cennik rodzajFilmu) {
		this.rodzajFilmu = rodzajFilmu;
	}
	public Integer getRokProdukcji() {
		return rokProdukcji;
	}
	public void setRokProdukcji(Integer rokProdukcji) {
		this.rokProdukcji = rokProdukcji;
	}
	
	@Override
	public String toString() {
	
		return tytulFilmu;
	}
	

}
