<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>



  


<div>

  <h2>Podsumowanie:</h2>

<h3>Filmy Dostepne :</h3>
  <table class="filmyHome">
    <c:forEach var="film" items="${dostepne}"> <!--<co id="cp_foreach_spittles"/>-->
    
    <!--   <s:url value="/spitters/{spitterName}" 
                  var="spitter_url" >    
        <s:param name="spitterName" 
                      value="${spittle.spitter.username}" />
      </s:url>-->

      <tr>
      	<td >
         <p class="italic">
     <!--    <a href="${spitter_url}">              -->
          <c:out value="${film.tytulFilmu}" />
          - <c:out value="${film.rokProdukcji}" /><br/>
          <c:out value="${film.gatunki} "/> 
           <input type="button" value="wiecej" name="wiecej"
           onClick="location.href='${film.linkDoFilmweb}'" />         
      </p>
      </td>
      	
      	<td>
      	 	<img src="${film.adresOkladka}" alt="filmPoster" height="84" width="84"> 
      </td>
      
      <td>
      Wystepuja :<br/> 
      
      <s:url value="/aktorzy/{aktorImie}" 
                  var="aktor_url" >    <!--<co id="cp_spitter_url"/>-->
        <s:param name="aktorImie" 
                      value="${film.aktorzy}" />
      </s:url>
      
       <a href="${aktor_url}">  
     	<c:out value="${film.aktorzy} "/>
      </a>
       
      </td>
      
      
    	</tr>
    </c:forEach>
    
     
    
  </table>
  
  <!--<c:if test="${fn:length(niedostepne)}!=0">-->
   <!--</c:if>-->
  <h3>Filmy Niedostepne :</h3>
 
 <table class="filmyHome">
    <c:forEach var="film" items="${niedostepne}"> <!--<co id="cp_foreach_spittles"/>-->
    
    <!--   <s:url value="/spitters/{spitterName}" 
                  var="spitter_url" >    
        <s:param name="spitterName" 
                      value="${spittle.spitter.username}" />
      </s:url>-->

      <tr>
      	<td >
         <p class="italic">
     <!--    <a href="${spitter_url}">              -->
          <c:out value="${film.tytulFilmu}" />
          - <c:out value="${film.rokProdukcji}" /><br/>
          <c:out value="${film.gatunki} "/> 
           <input type="button" value="wiecej" name="wiecej"
           onClick="location.href='${film.linkDoFilmweb}'" />         
      </p>
      </td>
      	
      	<td>
      	 	<img src="${film.adresOkladka}" alt="filmPoster" height="84" width="84"> 
      </td>
      
      <td>
      Wystepuja :<br/> 
      
      <s:url value="/aktorzy/{aktorImie}" 
                  var="aktor_url" >    <!--<co id="cp_spitter_url"/>-->
        <s:param name="aktorImie" 
                      value="${film.aktorzy}" />
      </s:url>
      
       <a href="${aktor_url}">  
     	<c:out value="${film.aktorzy} "/>
      </a>
       
      </td>
      
      
    	</tr>
    </c:forEach>
    
     
    
  </table>
  
  <s:url value="/klienci/zakonczenieTransakcji" 
                  var="konciec_url" />    <!--<co id="cp_spitter_url"/>-->
        
      
  
  <input type="button" value="ok" name="zakoncz"  onClick="location.href='${konciec_url}'"/>
 
  
</div>
