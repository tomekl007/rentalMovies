<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<div>
     
     <table class="listaGatunki">
     <tr>
     	<th>
     	  Kategorie filmów:
     	</th>
     </tr>
    <c:forEach var="gatunek" items="${gatunki}"> <!--<co id="cp_foreach_spittles"/>-->
   

      <tr>
      	<td >
         
         
         <s:url value="/gatunki/{gatunek}" 
                  var="gatunek_url" >    <!--<co id="cp_spitter_url"/>-->
        <s:param name="gatunek" 
                      value="${gatunek.gatunekFilmu}" />
      </s:url>
      
      
 		  <a href="${gatunek_url}"> 
          <c:out value="${gatunek.gatunekFilmu}" />
          </a>
        
      
      </td>
    </tr>
    	
      	
    
    </c:forEach>
 <!--    <tr>
    	 <td>
    	  <input type="button" value="ajaxButton" name="ajaxButton" onclick="onClickFindFilmy()" />
    	 </td>
    	</tr>-->
  </table>
	  
</div>
