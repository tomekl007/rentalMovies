package my.rental.mainP.domain;

import java.io.Serializable;

public class Aktor_filmId implements Serializable{
	
	private long idAktora;
	 
	private long idFilmu;
	  
	 
	  public long getAktor_id() {
		return idAktora;
	}

	public void setAktor_id(long aktor_id) {
		this.idAktora = aktor_id;
	}

	public long getFilm_id() {
		return idFilmu;
	}

	public void setFilm_id(long film_id) {
		this.idFilmu = film_id;
	}

	public int hashCode() {
	    return (int)(idFilmu + idAktora);
	  }
	 
	  public boolean equals(Object object) {
	    if (object instanceof Aktor_filmId) {
	    	Aktor_filmId otherId = (Aktor_filmId) object;
	      return (otherId.idFilmu == this.idFilmu) && (otherId.idAktora == this.idAktora);
	    }
	    return false;
	  }
	 
	}


