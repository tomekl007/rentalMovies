<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div>
  <h2>A global community of friends and strangers spitting out their 
  inner-most and personal thoughts on the web for everyone else to 
  see.</h2>
  <h3>Look at what these people are spitting right now...</h3>

  <table class="filmyHome">
    <c:forEach var="film" items="${filmy}"> <!--<co id="cp_foreach_spittles"/>-->
    
    <!--   <s:url value="/spitters/{spitterName}" 
                  var="spitter_url" >    
        <s:param name="spitterName" 
                      value="${spittle.spitter.username}" />
      </s:url>-->

      <tr>
      	<td class="opisFilmu">
      
     <!--    <a href="${spitter_url}">              -->
          <c:out value="${film.tytulFilmu}" />
          - <c:out value="${film.rokProdukcji}" /><br/>          
      </td>
      	
      	<td>
      	 	<img src="${film.adresOkladka}" alt="filmPoster" height="42" width="42"> 
      </td>
         </tr>
    </c:forEach>
  </table>
</div>
