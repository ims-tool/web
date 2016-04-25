
google.load("visualization", "1", {packages : [ "corechart" ]});
google.load('visualization', '1', {packages:['table']});


google.setOnLoadCallback(drawMapVolumeLigacaoEstado);
google.setOnLoadCallback(drawChartLineVolumeLigacaoUra);

setInterval(drawChartWebServices, 600000);
setInterval(drawChartLineRetencao, 600000);
setInterval(drawChartLineVolumeLigacaoMinuto, 600000);
setInterval(drawChartLineVolumeLigacaoUra, 600000);


//Volume de ligação por minuto
function drawMapVolumeLigacaoEstado() {
 	var tabelaLine;
 	
 	var div = document.getElementById('map_volume_ligacao_estado');
	if(div == null){
		return;
	}
	var chartLine = new google.visualization.GeoChart(div);
	
	/*function selectHandler() {
		
		 var selectedItem = chartLine.getSelection()[0];
         
         if (selectedItem) {
        	 
        	 var dataHoraI = tabelaLine.getValue(selectedItem.row, 0)+":00";
        	 var dataHoraF = tabelaLine.getValue(selectedItem.row, 0)+":59";
        	 var url = "/ims-tool-dashboard/Dashboard?funct=getVolumeLigacaoEstado&tempo=15";
        	 
        	 window.open(url);
        	 
         }
		
   }
	
	
   google.visualization.events.addListener(chartLine, 'select', selectHandler); */
	
	$.get("/ims-tool-dashboard/Dashboard?action=getVolumeLigacaoEstado&minutes=15", function(data){
 		tabelaLine = google.visualization.arrayToDataTable(JSON.parse(data));
 		var optionsLine = {region: 'BR',
 		        displayMode: 'markers',
 		        colorAxis: {colors: ['green', 'blue']}};
 		
 		
 		chartLine.draw(tabelaLine, optionsLine);
 	} );
 
}

function AddZero(num) {
    return (num >= 0 && num < 10) ? "0" + num : num + "";
}


//Volume de ligação por minuto
function drawChartLineVolumeLigacaoUra() {
 
	var tabela;
	var div = document.getElementById('chart_bar_volume_ligacao_ura');
	if(div == null){
		return;
	}
	var chart = new google.visualization.ColumnChart(div);
	
	function selectHandler() {
		
		var selectedItem = chart.getSelection()[0];
         
         if (selectedItem) {
           
           var flowName = tabela.getValue(selectedItem.row, 0);
           
           
           var today = new Date();
           var dataHoraF = AddZero(today.getDate())+"/"+AddZero(today.getMonth())+"/"+AddZero(today.getFullYear())
                           +" "+AddZero(today.getHours())+":"+AddZero(today.getMinutes())+":59";
           today.setHours(today.getHours()-1);
           var dataHoraI = AddZero(today.getDate())+"/"+AddZero(today.getMonth())+"/"+AddZero(today.getFullYear())
				           +" "+AddZero(today.getHours())+":"+AddZero(today.getMinutes())+":59";
		   
           var url = "/ims-tool-dashboard/Report?menu=report&submenu=logura&action=consultar&datahoraI="+dataHoraI+
                     "&datahoraF="+dataHoraF+"&flowName="+flowName;
           window.open(url);
         }
		
   }
	
	
   google.visualization.events.addListener(chart, 'select', selectHandler);
	
	$.get("/ims-tool-dashboard/Dashboard?action=buscaVolumeLigacaoURA", function(data){
		tabela = google.visualization.arrayToDataTable(JSON.parse(data));
		var options = {
			height: 260,
	        width: 480,
			bar: {groupWidth: "95%"},
			legend: { position: 'bottom' }
					
		};
		chart.draw(tabela, options);
	} );

}


