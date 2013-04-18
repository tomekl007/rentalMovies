<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<script type="text/javascript">
$("#searchForm").submit(function(event) {
	/* stop form from submitting normally */
	event.preventDefault();
	/* get some values from elements on the page: */
	var phrase = $('#phrase').val();
	//var json = { "title" : title, "category" : { "id" : category}};
	
	$( "#result" ).empty().append( phrase);
	
	var $form = $( this ),
	term = $form.find( 'input[name="s"]' ).val(),
	url = $form.attr( 'action' );
	alert(term);
	/* Send the data using post */
	var posting = $.post( url, { s: phrase } );
	/* Put the results in a div */
	posting.done(function( data ) {
	var content = $( data ).find( '#content' );
	$( "#result" ).empty().append( content );
	});
	});
	
	
var prefix = "/mainP";

var RestGet = function() {
	var gatunek = document.getElementById('phrase').value;
    $.ajax({
    type: 'GET',
    url:  prefix + "/restfull/search/" + gatunek,
    dataType: 'json',
    async: true,
    success: function(content) {
    	$( "#result" ).empty();
    	for (var i = 0; i < content.length; i++) {
    		$( "#result" ).append( content[i] );
    		$( "#result" ).append( "<br>" );
    	}
    
    	//$( "#result" ).empty().append( content[0] );
    	//}
    },
    error: function(){
    	$( "#result" ).empty().append( "Nie znalazlem filmu" );
    }
   
});
}
	
</script>


		<form class="signin" action="<s:url value="/restfull/search"/>" method="post">
		  <fieldset>
		  
			<input id="phrase" type="text" name="s" placeholder="Search..." />
			<input type="submit" value="Search" />

		  </fieldset>
		</form>
		
		<button type="button" onclick="RestGet()">GET</button>
		
		 <div id="result"></div>

<div>

	
 
</div>


