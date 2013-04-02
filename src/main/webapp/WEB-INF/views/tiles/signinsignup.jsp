<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<div>        
  <sec:authorize access="!isAuthenticated()">
			
		<div class="msg">
		  <h3>Prosze zaloguj sie!</h3>
		</div>
   
		<form class="signin" action="<s:url value="/static/j_spring_security_check"/>" method="post">
		  <fieldset>
		    <div>
		      <label for="j_username">email/login</label>
		      <input id="email" type="text" name="j_username"/>
		    </div>
		    <div>
		      <label for="j_password">haslo</label>
		      <input id="pass" type="password" name="j_password"/>
		    </div>
		    <input id="remember_me" type="checkbox" value="1" 
		           name="_spring_security_remember_me"/>
		    <label for="remember_me">Zapamietaj mnie</label>
		    <small>
		      <a href="/account/resend_password">Zapomniales?</a>
		    </small>
		    <input id="submit" type="submit" value="Sign In!" 
		           name="commit"/>
		  </fieldset>
		</form>
		<div class="notify">
		  Chcesz zalozyc konto?   
		  <br/>
		  <a class="join" href="<s:url value="/klienci?new"/>">Dolacz za darmo</a>
		  <br/>
		  To latwe i szybkie!
		</div>
	</sec:authorize>
	
  <sec:authorize access="isAuthenticated()">
    <s:url value="/resources/images" var="images_url" />
	  <img src="${images_url}/spitter_me.jpg" align="middle"/>
	  <span><sec:authentication 
	        property="principal.username" /></span>
	  <br/>
    <s:url value="/static/j_spring_security_logout" 
                var="logout_url" /> 
	  <a href="${logout_url}">Wyloguj</a>
	  <sec:authorize url="/admin">
      <s:url value="/admin/listaAllWyp" var="konto_url" />
	    <br/><a href="${konto_url}">Admin</a>
	  </sec:authorize>
	</sec:authorize>
</div>