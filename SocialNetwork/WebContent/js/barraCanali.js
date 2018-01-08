
function getCanali(){


	var xmlhttp = new XMLHttpRequest();

	xmlhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	        var liste = JSON.parse(this.responseText);
	        var cont = 0;
	        var canale;
	        $.each(liste, function(i,lista){
	        	cont = 0;
	        	$.each(lista, function(i, stringhe){
	        		if (cont === 0){
	        			$("#listaCanali").append("<li><a href=\"canale?to="+stringhe+"\">"+stringhe+"</a></li>");
	        			canale = stringhe;
	        			
	        		}
	        		else{
	        			$("#listaCanali").append("<li><a href=\"gruppo?to="+stringhe+"&at="+canale+"\">"+stringhe+"</a></li>");
	        		}
	        		cont++;
	        	});
	        });
	    }
	};
	xmlhttp.open("GET", "barraCanali", true);
	xmlhttp.send();
	
	

	
}