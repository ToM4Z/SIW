
function getCanali(){


	var xmlhttp = new XMLHttpRequest();

	xmlhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	        var canali = JSON.parse(this.responseText);
	        $.each(canali, function(i,canale){
	        	$("#listaCanali").append(canale.nome);
	        	$.each(canali.gruppi, function(j, gruppo){
	        		$("#listaCanali").append(gruppo.nome);
	        	});
	        });
	    }
	};
	xmlhttp.open("GET", "barraCanali", true);
	xmlhttp.send();
	
	
}
