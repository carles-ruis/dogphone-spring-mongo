/* *************************************************************************************** */
/* ************************** Variables globals ********************* */
/* *************************************************************************************** */
botigues="";
terminals ="";
indexTerminalsPage = 1;

/* *************************************************************************************** */
/* ************************** Document ready ********************* */
/* *************************************************************************************** */
$(function() {
	get_botigues();
	get_terminals();
	bind_history_statechange();
	bind_navbar_crides_ajax();
	bind_navbar_seleccio_idioma();
	bind_header_botons();
	bind_modals();
	bind_elements_propis_de_la_vista();
});

var bind_elements_propis_de_la_vista = function() {
	bind_gmap();
	bind_terminals_pagination();
	bind_accessoris_sidebar();
	bind_sim_movistar_popups();
}

function get_botigues() {
	$.ajax( { url: 'resources/botigues', dataType: 'json', async: false, success: function(response) {
		botigues = response;
	}});
};

function get_terminals() {
//	$.getJSON( 'terminals', function(response) {
	$.ajax( { url: 'resources/terminals', dataType: 'json', async: false, success: function(response) {
		terminals = response;
	}});
};

var goToIndex = function() { window.location.assign('index'); }

jQuery.fn.exists = function() { return this.length>0; }

/* *************************************************************************************** */
/* * Crides AJAX per recarregar el contingut principal. jquery.history.js ********************* */
/* *************************************************************************************** */
function bind_history_statechange() {
	var History = window.History;
	if ( !History.enabled ) { return false; }
	
	History.Adapter.bind(window,'statechange',function(){
		var State = History.getState();
		History.log(State.data, State.title, State.url);
		$('#content').load(State.url + " #content", null, bind_elements_propis_de_la_vista);
	});
}

function bind_navbar_crides_ajax() {
	var History = window.History;
	if ( !History.enabled ) { return false; }

	$('a.ajax').click(function(e){
		e.preventDefault();
		var old_state = History.getState().hash;
		var new_state = $(this).attr('href');
		if (old_state==new_state) return;
		History.pushState(null, null, new_state);	
		$('#url').html(new_state);
		$('nav li').removeClass('active');
		$(this).parents().filter('li').addClass('active');
	});
}		

/* *************************************************************************************** */
/* *************************************************************************************** */
/* ************** Dropdown de seleccio d'idioma de la navbar ************** */
function bind_navbar_seleccio_idioma() {
	$('.a-idioma').click(function(e) {
		e.preventDefault();
		var codi_idioma = $(this).attr('id');
		/* la seguent linia es per evitar que dongui un error si canvia d'idioma dues vegades seguides*/
		var old_location = window.location.href.split("?")[0];
		window.location = old_location + "?lang="+codi_idioma;
	});
}

/* *************************************************************************************** */
/* *************************************************************************************** */
/* ************** Botons del header: home, login, registre i logout ************** */
function bind_header_botons() {
	$('header a.ajax').click(function(){
		$('nav #li-inici').addClass('active');
	});
	$('header .btn-login').click(function(e){
		e.preventDefault();
		$('#the-modal-login').modal('show');
	});
	$('header .btn-registre').click(function(e){
		e.preventDefault();
		$('#the-modal-registre').modal('show');
	});
	$('header .btn-logout').click(function(){
		$.ajax({ url:"usuari/logout", async:false, success:goToIndex });
	});
}

/* *************************************************************************************** */
/* *************************************************************************************** */
/* ************** Dialegs modals per login i registre ************** */
function bind_modals() {
	
	$('#the-modal-registre .btn-registre').click(function(){
		var nom = $("#registre-usuari").val();
		var password = $("#registre-password").val();
		var confirmacio = $("#registre-confirma").val();
		var usuari = {nom:nom,password:password,confirmacio:confirmacio};
		$.ajax({
			url:"usuari/registre", async:false,
			type:"POST",
			contentType: "application/json", /* content-type de la request */
			dataType: "text", /* content-type de la response */
			data: JSON.stringify(usuari), /* statusCode: { 403: function() {$("#error-login").show(); } }, */
			success:function(response){
				$('#the-modal-registre').modal('hide');
				$('#the-modal-response #contingut').html(response);
				$('#the-modal-response').modal('show');
			}
		});
	});

	$('#the-modal-login .btn-login').click(function(){
		var nom = $("#login-usuari").val();
		var password = $("#login-password").val();
		var usuari = {nom:nom,password:password};
		$.ajax({ url:"usuari/login", async:false, type:"POST", contentType: "application/json", dataType: "text", data: JSON.stringify(usuari), success:function(response){
			if (response==null || response=="") {
				goToIndex();
			} else {
				$('#the-modal-login').modal('hide');
				$('#the-modal-response #contingut').html(response);
				$('#the-modal-response').modal('show');
			}
		}});
	});
	
	$('#the-modal-response')[0].onclick = goToIndex;
}

/* ************************** *********** ********************************* */
/* ************************** Google maps ********************************* */
/* http://code.google.com/p/jquery-ui-map/ */
/* ************************** *********** ********************************* */

/* ALTERNATIVA 1 - LOAD MARKERS WITH DATA-MAPPING ATTRIBUTE */
/* http://jquery-ui-map.googlecode.com/svn/trunk/demos/jquery-google-maps-data-attribute.html */
/*function bind_gmap(event, map) {
		$("[data-gmapping]").each( function(i,element) { 	*/

/* ALTERNATIVA 2 - CREAR MARKERS AMB LA POSICIO INDICADA EN ELS ID DELS <a> */
/* http://jquery-ui-map.googlecode.com/svn/trunk/demos/jquery-google-maps-basic-example.html */
/*function bind_gmap(event, map) {
	$("a.gmapping").each( function(i,element) { */

/* ALTERNATIVA 3 - CREAR MARKERS A PARTIR D'OBJECTES JSON ENVIATS DES DEL SERVIDOR */
/* http://jquery-ui-map.googlecode.com/svn/trunk/demos/jquery-google-maps-json.html */
function bind_gmap(event, map) {
	if ( $("#map_canvas").exists()==false ) return;
	
	$.each( botigues, function(i, botiga) {
		var element = $("a.gmapping").eq(i);
		$('#map_canvas').gmap( /* per cada botiga afegim un marker al mapa */
			'addMarker', { 'position': new google.maps.LatLng(botiga.latitut, botiga.longitut), 'bounds': true },
			function(map,botiga) { 
				$(element).click(function() { /* al clicar el link d'una botiga mostrem l'adreça completa */
					$("address").removeClass("show");
					$("address").addClass("hide");
					$("address").eq(i).removeClass("hide");
					$("address").eq(i).addClass("show");
					$(botiga).triggerEvent('click');
				});
			}
		).click(function() { /* al clicar en el mapa apareix l'adreça completa */
			var popup_content = botiga.nom+"<br />"+botiga.adressa+"<br />"+botiga.telefon;
			$('#map_canvas').gmap(
				'openInfoWindow', { 'content': popup_content },
				this
			);
		});
	});
};

/* *************************************************************************************** */
/* *************************************************************************************** */
/* ************** Terminals . Paginacio ************** */

function bind_terminals_pagination() {
	if ( $("#terminals-pagination").exists()==false ) { return; }

	$('.terminals-page-prev').click(function(e){
		e.preventDefault();
		if (indexTerminalsPage>1) indexTerminalsPage -=1 ;
		show_terminals_page();
	});
	$('.terminals-page-next').click(function(e){
		e.preventDefault();
		if (indexTerminalsPage<$('.terminals-page').length) indexTerminalsPage +=1 ;
		show_terminals_page();
	});
	$('.terminals-page').click(function(e){
		e.preventDefault();
		indexTerminalsPage = parseInt($(this).text());
		show_terminals_page();
	});
	
	show_terminals_page();
}

function show_terminals_page() {
	if ( $("#terminals-pagination").exists()==false ) { return; }

	var terminal, src;
	const TERMINALS_PER_PAGE = $("#terminals-pagination .li-thumbnail").length;
	const INDEX_INICIAL = (indexTerminalsPage-1)*TERMINALS_PER_PAGE;
	$("#terminals-pagination .li-thumbnail").addClass('hide'); /* per si hi ha mes <li> que terminals a mostrar */
	for (i=0; i<TERMINALS_PER_PAGE; i++) {
		terminal = terminals[INDEX_INICIAL+i];
		if (terminal) {
			src = $("#terminals-pagination img").eq(i).attr( 'src' );
			src = src.substring( 0,src.lastIndexOf("/")+1 );
			src = src + terminal.imatge;
			$("#terminals-pagination img").eq(i).attr( 'src', src);
			$("#terminals-pagination h3").eq(i).text( terminal.nom );
			$("#terminals-pagination p").eq(i).text( terminal.descripcio );
			$("#terminals-pagination .li-thumbnail").eq(i).removeClass('hide');
		}
	}
	
	$('.terminals-page-prev').parent().removeClass('disabled');
	$('.terminals-page-next').parent().removeClass('disabled');
	$('.terminals-page').parent().removeClass('active');
	if(indexTerminalsPage==1) $('.terminals-page-prev').parent().addClass('disabled');
	if(indexTerminalsPage==$('.terminals-page').length) $('.terminals-page-next').parent().addClass('disabled');
	$('.terminals-page').eq(indexTerminalsPage-1).parent().addClass('active');
}

/* *************************************************************************************** */
/* *************************************************************************************** */
/* ************** Accessoris . Sidebar ************** */
function bind_accessoris_sidebar() {
	if ( $("a.ajax-accessoris").exists()==false ) { return; }
	$('a.ajax-accessoris').click(function(e){
		e.preventDefault();
		$('nav#the-sidebar li').removeClass('active');
		$(this).parents().filter('li').addClass('active');
		var href = $(this).attr('href')+' #accessoris-content';
		$('#accessoris-content').load(href);
	});
};

/* *************************************************************************************** */
/* *************************************************************************************** */
/* ************** Popups per la pantalla de les tarifes SIM movistar ************** */
function bind_sim_movistar_popups() {
	$(".a-popover").popover();
}