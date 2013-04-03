<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="menu">
<s:url value="/" var="home_url" />
<s:url value="/polecane" var="polecane_url" />    
<s:url value="/kontakt" var="kontakt_url" />
        
      

<ul>
<li><a href="${home_url} ">Strona Glowna/Nowosci</a></li>
<li><a href="${polecane_url}">Polecane</a></li>
<li><a href="${kontakt_url}">Kontakt</a></li>

</ul>
<br style="clear:left"/>
</div>
