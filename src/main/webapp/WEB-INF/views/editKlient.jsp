<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div>
<h2>Create a free Spitter account</h2>

<sf:form method="POST" modelAttribute="klient" 
       >         
   <fieldset> 
   <table cellspacing="0">
      <tr>
         <th><sf:label path="imieKlienta">imie Klienta:</sf:label></th>
         <td><sf:input path="imieKlienta" size="15" /><br/>
             <sf:errors path="imieKlienta" cssClass="error" />
         </td>
      </tr>
      <tr>
         <th><sf:label path="nazwiskoKlienta">nazwisko klienta:</sf:label></th>
         <td><sf:input path="nazwiskoKlienta" size="15" maxlength="15" />
              <small id="username_msg">Bez spacji, prosze.</small><br/>
             <sf:errors path="nazwiskoKlienta" cssClass="error" />

            </td>
      </tr>
      <tr>
         <th><sf:label path="password">Haslo:</sf:label></th>
         <td><sf:password path="password" size="30" 
                            showPassword="true" /> 
             <small>6 znakow lub wiecej</small><br/>
             <sf:errors path="password" cssClass="error" />
            </td>
      </tr>
      
      <tr>
       <th><sf:label path="plec">plec</sf:label></th>
       <td>
       
       <sf:radiobutton path="plec" value="K"/>Kobieta 
		<sf:radiobutton path="plec" value="M"/>Mezczyzna
        </td>
      
      </tr>

      <tr>
         <th><sf:label path="login">login</sf:label></th>

         <td><sf:input path="login" size="30"/> 
           
             <sf:errors path="login" cssClass="error" />
            </td>
            
      </tr>
      
        
      <tr>
         <th></th>
        </tr>
     
      
      <tr>
         <th></th>
         <td><input name="commit" type="submit" 
                    value="I accept. Create my account." /></td>
      </tr>
     
   </table>
</fieldset>
</sf:form>
</div>
