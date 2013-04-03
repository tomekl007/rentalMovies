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
  <h2>Kontakt do nas:</h2>
  <h3>nie wachaj sie zadzwonic</h3>

   
    <c:out value="${kontakt}"/>
  
</div>
