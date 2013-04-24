package my.rental.mainP.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Indexed
public class Klient implements Serializable {
	
	@Id
	@DocumentId
	private long idKlienta;
	
	@Size(min=3, max=20, message=
		      "Username must be between 3 and 20 characters long.") //<co id="co_enforceSize"/> 
		  @Pattern(regexp="^[a-zA-Z0-9]+$",
		        message="Username must be alphanumeric with no spaces")  //<co id="co_noSpaces"/>
	private String nazwiskoKlienta;
	@Size(min=3, max=20, message=
		      "Username must be between 3 and 20 characters long.") //<co id="co_enforceSize"/> 
		  @Pattern(regexp="^[a-zA-Z0-9]+$",
		        message="Username must be alphanumeric with no spaces")  //<co id="co_noSpaces"/>
	@Field
	private String imieKlienta;
	private String plec;
	private Date dataWprowadzenia ;
	@Field
	private String login;
	  @Size(min=6, max=20,
	          message="The password must be at least 6 characters long.") //<co id="co_enforceSize
	private String password;
	
	//zakladam ze login to email
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	@OneToMany(mappedBy = "klient", cascade=CascadeType.ALL, orphanRemoval=true,
			  fetch = FetchType.EAGER)
	  @LazyCollection(LazyCollectionOption.FALSE)
	  List<Wypozyczenie> wypozyczenia;
	
	public long getIdKlienta() {
		return idKlienta;
	}

	public void setIdKlienta(long idKlienta) {
		this.idKlienta = idKlienta;
	}

	public String getNazwiskoKlienta() {
		return nazwiskoKlienta;
	}

	public void setNazwiskoKlienta(String nazwiskoKlienta) {
		this.nazwiskoKlienta = nazwiskoKlienta;
	}

	public String getImieKlienta() {
		return imieKlienta;
	}

	public void setImieKlienta(String imieKlienta) {
		this.imieKlienta = imieKlienta;
	}

	public String getPlec() {
		return plec;
	}

	public void setPlec(String plec) {
		this.plec = plec;
	}

	public Date getDataWprowadzenia() {
		return dataWprowadzenia;
	}

	public void setDataWprowadzenia(Date dataWprowadzenia) {
		this.dataWprowadzenia = dataWprowadzenia;
	}

	public List<Wypozyczenie> getWypozyczenia() {
		return wypozyczenia;
	}

	public void setWypozyczenia(List<Wypozyczenie> wypozyczenia) {
		this.wypozyczenia = wypozyczenia;
	}

	
	
	@Override
	public String toString() {
		return nazwiskoKlienta ;
	}

}
