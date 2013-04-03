package my.rental.mainP.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.LazyCollectionOption;

import org.hibernate.annotations.LazyCollection;




@Entity
public class Gatunek implements Serializable {
	
	@Id
	private String gatunekFilmu;
	
	@ManyToMany(mappedBy="gatunki")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Film> filmy;
	
	public String getGatunekFilmu() {
		return gatunekFilmu;
	}

	public void setGatunekFilmu(String gatunekFilmu) {
		this.gatunekFilmu = gatunekFilmu;
	}

	

	public List<Film> getFilmy() {
		return filmy;
	}

	public void setFilmy(List<Film> filmy) {
		this.filmy = filmy;
	}
	
	@Override
	public String toString() {
		return gatunekFilmu;
	
	}

}
