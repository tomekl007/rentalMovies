<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<script type="text/javascript">
 function onClickWypozycz()
 {
 alert("click");
 }
</script>

<div>
  <h2>Polecane filmy</h2>
  <h3>zwane hitami!</h3>

  <table class="filmyHome">
    <c:forEach var="film" items="${filmy}"> <!--<co id="cp_foreach_spittles"/>-->
    
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
      
       <s:url value="/klienci/wypozycz/{idFilmu}" 
                  var="wypozycz_url" >    <!--<co id="cp_spitter_url"/>-->
        <s:param name="idFilmu" 
                      value="${film.idFilmu}" />
      </s:url>
      
      <sec:authorize access="isAuthenticated()">
      <td>
      <input type="button" value="wypozycz" name="wypozycz"
      onClick="location.href='${wypozycz_url}'"/>
      <!-- onclick="onClickWypozycz()" />-->
        
      </td>
      </sec:authorize>
       <sec:authorize access="!isAuthenticated()">
      <td>
      <input type="button" value="wypozycz" name="wypozycz" disabled="true"/>
      
        
      </td>
      </sec:authorize>
         </tr>
    </c:forEach>
  </table>
</div>
