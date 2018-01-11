
function seiSicuro(idPost){
	
	var gruppo = $("#nomeGruppo").text();
	var canale = $("#nomeCanale").text();
	
	if (confirm("Sei sicuro di voler cancellare il post?") == true) {
	    
		document.location.href = "eliminaPost?post="+idPost+"&group="+gruppo+"&channel="+canale;
		
		
		
	} else {
	    
		//do nothing
	} 
}