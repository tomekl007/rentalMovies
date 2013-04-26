package my.rental.mainP.domain;

import java.io.Serializable;
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


import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.NGramFilterFactory;
import org.apache.solr.analysis.PhoneticFilterFactory;
import org.apache.solr.analysis.SnowballPorterFilterFactory;
import org.apache.solr.analysis.StandardFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.apache.solr.analysis.StopFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.AnalyzerDefs;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TermVector;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.Parameter;


@Entity
@Indexed

@AnalyzerDefs( {
@AnalyzerDef(name="applicationanalyzer",   //analyzer definition name 
tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class ),  //tokenizer factory
filters = { @TokenFilterDef(factory=LowerCaseFilterFactory.class),  //filter factory 
            @TokenFilterDef(factory = StopFilterFactory.class,
                    params = {  @Parameter(name="words",   //parameters passed to the filter factory 
                    		               value="my/rental/mainP/stopwords.txt"),
                                @Parameter(name="ignoreCase", value="true") } ) 
} ),

@AnalyzerDef(name="phonetic",
tokenizer =
@TokenizerDef(factory = StandardTokenizerFactory.class ),
filters = {
@TokenFilterDef(factory = StandardFilterFactory.class),
@TokenFilterDef(factory = StopFilterFactory.class,
params = @Parameter(name="words", value="my/rental/mainP/stopwords.txt") ),
@TokenFilterDef(factory = PhoneticFilterFactory.class,
params = {
@Parameter(name="encoder", value="DoubleMetaphone"),
@Parameter(name="inject", value="false")
} )
}),

@AnalyzerDef(name="englishSnowball",
tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class ),
filters = { @TokenFilterDef(factory=StandardFilterFactory.class),
			@TokenFilterDef(factory=LowerCaseFilterFactory.class),
            @TokenFilterDef(factory = StopFilterFactory.class,    //stop word factory
                    params = {  @Parameter(name="words",  
                    		               value="my/rental/mainP/stopwords.txt"),  //file containing stop words
                                @Parameter(name="ignoreCase", value="false") } ), 
            @TokenFilterDef(factory = SnowballPorterFilterFactory.class,  //use Snowball filter
                    params = @Parameter(name="language", value="English") )  //define the language
} ),
@AnalyzerDef(
		   name="ngramanalyzer",
		   tokenizer=@TokenizerDef(factory=StandardTokenizerFactory.class),
		   filters={
		       @TokenFilterDef(factory=NGramFilterFactory.class,
		              params={
		         @Parameter(name="minGramSize",value="3"),
		         @Parameter(name="maxGramSize",value="3")           
		       }),
		       
		       @TokenFilterDef(factory=LowerCaseFilterFactory.class),
		       @TokenFilterDef(factory = StopFilterFactory.class,
               params = {  @Parameter(name="words",   //parameters passed to the filter factory 
               		               value="my/rental/mainP/stopwords.txt"),
                           @Parameter(name="ignoreCase", value="true") })
		       })

})

@Analyzer(definition="applicationanalyzer")  //Use a pre defined analyzer
public class Film implements Serializable {
	@Id
	@DocumentId
	private Long idFilmu;
//	@Field//default is tokenization
	//if i don not want tokenization :
	//@Field(index=Index.UN_TOKENIZED)
	//to store property in lucene index : 
	//@Field(store=Store.YES)
	//value is stored compressed:
	//@Field(store=Store.COMPRESS)
	
	@Fields( { 
		@Field(index=Index.TOKENIZED, store=Store.YES),
		@Field(name="tytulFilmu_phonetic",
				analyzer=@Analyzer(definition="phonetic")),
		@Field(name="tytulFilmu_stemmer", 
				analyzer=@Analyzer(definition="englishSnowball"))  //title_stemmer uses Snowball fitler
	})
	
	private String tytulFilmu;
	private String adresOkladka;
	
	
	@Field(name="linkDoFilmweb_ngram",
			index=Index.TOKENIZED,
			analyzer=@Analyzer(definition="ngramanalyzer"))
	@Boost(0.2f)
	private String linkDoFilmweb;
	
	public String getLinkDoFilmweb() {
		return linkDoFilmweb;
	}
	public void setLinkDoFilmweb(String linkDoFilmweb) {
		this.linkDoFilmweb = linkDoFilmweb;
	}
	public String getAdresOkladka() {
		return adresOkladka;
	}
	public void setAdresOkladka(String adresOkladka) {
		this.adresOkladka = adresOkladka;
	}



	@ManyToOne
	@JoinColumn(name = "rodzajFilmu")
	@IndexedEmbedded
	private Cennik rodzajFilmu;
	@Field(termVector=TermVector.YES)//store occurence statistic 
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
	@IndexedEmbedded
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
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Film))
			return false;
		Film otherFilm = (Film)obj;
		
		return this.tytulFilmu.equals(otherFilm.tytulFilmu)
				&& this.rokProdukcji.equals(otherFilm.rokProdukcji);
	}
	
	@Override
	public int hashCode() {
		return rokProdukcji * tytulFilmu.length();
	}
	

}
