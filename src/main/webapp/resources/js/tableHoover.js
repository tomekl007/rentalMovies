


function podswietl_wiersze(id) {

   var tab = document.getElementById(id);
    var td = tab.getElementsByTagName('td');
    for (x=0; x<td.length; x++) {
        td[x].onmouseover = function() {
		//parentNode return <tr> u ustawia <tr class="podswietlone">
		//jak bede onmouseover to wybierze table.tab.podswietlone, bo 
		//<table class="tab"> 
                
            this.parentNode.className = 'podswietlone'; //ustawiamy klase hover dla TR
        }
        td[x].onmouseout = function() {
            this.parentNode.className = '';
        }
    }
  
  
}
 if(window.onload){
podswietl_wiersze('tabelka')
}
