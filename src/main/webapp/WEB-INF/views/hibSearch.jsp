<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>



<script type="text/javascript">
var prefix="mainP";
var postToServer = function() {
	var tytulFilmu = document.getElementById('tytulFilmu').value;
	var rokProdukcji = document.getElementById('rokProdukcji').value;
    $.ajax({
    type: 'GET',
    url: "searchParam/" + tytulFilmu + "/" + rokProdukcji,
    
    async: true,
    success: function(content) {
    	
    	$( "#result" ).empty();
    	for (var i = 0; i < content.length; i++) {
    		$( "#result" ).append( content[i] );
    		$( "#result" ).append( "<br>" );
    	}
    
    },
    error: function(){
    	$( "#result" ).empty().append( "Nie znalazlem filmu" );
    }
   
});
}

	
</script>



<div>

	
	 <s:url value="/hs/search" var="search_url" />
    <s:url value="/hs/index" var="index_url"/>
     <s:url value="/hs/searchMultiple" var="searchMultiple_url"/>
     <s:url value="/hs/searchStemmed" var="searchStemmed_url"/>

<s:url value="/hs/addFilmToDb" var="addFilmToDb_url"/>
   
  
  

  
  <input type="button"   onClick="location.href='${index_url}'"/>
   <input type="button"    onClick="location.href='${search_url}'"/>
      <input type="button" value="multiple"   onClick="location.href='${searchMultiple_url}'"/>
         <input type="button" value="stemmed"   onClick="location.href='${searchStemmed_url}'"/>
     <input type="button" value="addFilm"   onClick="location.href='${addFilmToDb_url}'"/>
     
    
   
    
     
     <form method="post" action="<s:url value="searchParam/${tytul}"/>" method="post">
     
      <label for="tytul">tytul</label>
	<input id="tytulFilmu" type="text" name="tytul" />
	
	<label for="rokProdukcji">gatunekFilmu</label>
	<input id="rokProdukcji" type="text" name="rokProdukcji"/>
     
		 
		 <input id="submit" type="submit" value="search" 
		           name="search" />
		</form>
		 <s:url value="/hs/searchParam/{tytulFilmu}/{rokProdukcji}" var="searchUrl" >
     <s:param name="tytulFilmu" 
                      value="${tytul}" />
                      <s:param name="rokProdukcji" 
                      value="${rokProdukcji}" />
                      </s:url>
	<input type="button" value="search"   onClick="postToServer()"/>
		
	<div id="result"></div>	
		
		
     
     
 
</div>