<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>






<div>

	
	 <s:url value="/hs/search" var="search_url" />
    <s:url value="/hs/index" var="index_url"/>
     
    
  
  

  
  <input type="button" name="in"    onClick="location.href='${index_url}'"/>
   <input type="button"    onClick="location.href='${search_url}'"/>
 
</div>