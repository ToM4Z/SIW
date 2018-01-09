
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
	        			canale = stringhe;
	        			$("#listaCanali").append("<ul class=\"treeview\" id=\""+canale+"\">" +
	        	                "<li><a href=\"canale?channel="+canale+"\">"+canale+"</a>"+
	                        	"<ul>");
	        		}else{
	        			$("#listaCanali").find("ul#"+canale).find("ul").append("<li>" +
	        									"<a class=\"nav-link\" href=\"gruppo?group="+stringhe+"&channel="+canale+"\">"
	        									+stringhe+"</a></li>");
	        		}
	        		cont++;
	        	});
	        	$("#listaCanali").append("</ul></li></ul>");
	        });
	    }
	};
	xmlhttp.open("GET", "barraCanali", true);
	xmlhttp.send();
	
	

	
}