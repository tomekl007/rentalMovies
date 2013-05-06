<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<div>
  <h2>Wypozyczenia</h2>
  <h3>bez daty zwrotu</h3>

  <table class="filmyHome">
    <c:forEach var="w" items="${wypozyczenia}"> <!--<co id="cp_foreach_spittles"/>-->
   <tr>
   <td>
   data Wypozyczenia:
   
     <fmt:formatDate value="${w.dataWypozyczenia}" 
	                            pattern=" MMM d, yyyy" />
   
   </td>
   
   <td>
   <c:out value="${w.klient.imieKlienta} " />
   <c:out value="   ${w.klient.nazwiskoKlienta} " />

   </td>
   
   <td>
   <c:out value="${w.plyta.film}" />
  
   </td>
   <s:url value="/wypozyczenia/oddaj/{idWyp}" 
                  var="oddaj_url" >    <!--<co id="cp_spitter_url"/>-->
        <s:param name="idWyp" 
                      value="${w.idWypozyczenia}" />
      </s:url>
      
      <td>
       <input type="button" value="klient odddaje" name="oddaj"
      onClick="location.href='${oddaj_url}'"/>
      <!-- onclick="onClickWypozycz()" />-->
        
      </td>
   
   </tr>   
   
   
   
   </c:forEach>
  </table>
  
  <s:url value="/gcharts/stats" 
                  var="stats_url" />  
                  <input type="button" value="pokaz statystyki" name="pokaz statystyki"
      onClick="location.href='${stats_url}'"/>
         
       
  
</div>
