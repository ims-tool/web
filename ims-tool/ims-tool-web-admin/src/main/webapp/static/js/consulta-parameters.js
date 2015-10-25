function numeros(){  
	if (event.keyCode<48 || event.keyCode>57) { 
	return false; 
	}  
}  

function TableToExcel(tableid){
	  var sBrowser, sUsrAg = navigator.userAgent;
	  // IE
	  if (sUsrAg.indexOf("MSIE") > -1) {
		  sBrowser = "Microsoft Internet Explorer";
		  
		  var id = $('[id$="' + tableid + '"]');
		  var strCopy = $('<div></div>').html(id.clone()).html(); window.clipboardData.setData("Text", strCopy);
		  var objExcel = new ActiveXObject("Excel.Application");

		  objExcel.visible = false; 
		  var objWorkbook = objExcel.Workbooks.Add; 
		  var objWorksheet = objWorkbook.Worksheets(1); 
		  objWorksheet.Paste; 
		  objExcel.visible = true;
		  
	  // Demais browsers	  
	  } else {
		  var table= document.getElementById(tableid);
		  var html = table.outerHTML;
		  window.open('data:application/vnd.ms-excel,' + encodeURIComponent(html));
	  }
}


function focoNoCampoRegra(){
	$("#logRoot").focus();
}

function editarRegra(){
	var logroot = $('#logRoot').val();
	$('#logRootEditar').val(logroot);
	$("#editarRegraForm").submit();
}
function histRegra(id){
	var logroot
}


$(document).ready(function(){
			$('table tr:odd').addClass('outracor');
			$("#logRoot").focus();
			
			/*Teclou Enter*/
			$(document).keypress(function(e){
				if(e.wich == 13 || e.keyCode == 13){
					$("#informaLogRoot").submit();
				}
			});
			$("#btnHistRegra").click(function(){
				$('#histLogRoot').submit();				
			});
		}
);