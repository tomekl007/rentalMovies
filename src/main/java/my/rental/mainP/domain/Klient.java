package my.rental.mainP.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.Date;
import java.util.List;

@Entity
public class Klient {
	
	@Id
	private long idKlienta;
	
	private String nazwiskoKlienta;
	private String imieKlienta;
	private String plec;
	private Date dataWprowadzenia ;
	private String login;
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
