var Util = {
	
		tableSelect: function (table)
		{
			$(table+' tr').unbind().click(function (){
				$(table+' tr').removeClass('line-selected');
				$(table+' tr').addClass('white');
				$(this).addClass('line-selected');
			});
			
			$(table).sortable().disableSelection();
		},
		
		tableRemove: function (table)
		{
			$(table + ' .line-selected').remove();
		},
		
		search: function (type)
		{
			return {
				minimumInputLength: 2,
			    ajax: {
			      url: "Crud?action=search&type="+type,
			      dataType: 'json',
			      data: function (term, page) {
			        return {
			          q: term
			        };
			      },
			      results: function (data, page) {
			        return { results: data };
			      }
			    }
			}
		},
		
		dateFormat: function (info)
		{
			aux = info.split('.');
			aux = aux[0];
			aux = aux.split(" ");
			aux2 = aux[0].split('-');
			aux2 = aux2[2] + '/' + aux2[1] + '/' + aux2[0];
			
			return aux2 + " " + aux[1];
		},
		
		onlyNumbers: function(e){
			-1!==$.inArray(
					e.keyCode,[46,8,9,27,13,110,190])||/65|67|86|88/.test(e.keyCode)
					&&(!0===e.ctrlKey||!0===e.metaKey)||35<=e.keyCode
					&&40>=e.keyCode||(e.shiftKey||48>e.keyCode||57<e.keyCode)
					&&(96>e.keyCode||105<e.keyCode
			)
			&&e.preventDefault();
		},
		
		isArray: function (data)
		{
			return (Object.prototype.toString.call( data ) == "[object Array]");
		},
		
		isObject: function (obj)
		{
			return (Object.prototype.toString.call( data ) == "[object Object]");
		},
		
		customSerializeArray: function (array)
		{
			var result = '';
			var aux = '';
			for(key in array)
			{
				if(Util.isArray(array[key])){
					aux = '';
					for(info in array[key])
					{
						aux += array[key][info]+':';
					}
					aux = aux.substr(0,aux.length-1);
					array[key] = aux;
				}
				result += array[key] + "|";
			}
			
			return result.substr(0,result.length-1);
		},
		
		customDeserializeToArray: function (info)
		{
			var result = [];
			var aux = [];
			info = info.split('|');
			for(key in info)
			{
				if(info[key].indexOf(':') > -1){
					info[key] = info[key].split(':');
				}
				result.push(info[key]);
			}
			
			return result;
		},
		
		urlToArray: function (url)
		{
			var request = [];
			  var pairs = url.split('&');
			  for (var i = 0; i < pairs.length; i++) {
			    var pair = pairs[i].split('=');
			    if(request[decodeURIComponent(pair[0]).split('.').join('')] != undefined){
			    	if(!Util.isArray(request[decodeURIComponent(pair[0]).split('.').join('')])){
			    		request[decodeURIComponent(pair[0]).split('.').join('')] = [request[decodeURIComponent(pair[0]).split('.').join('')]];
			    	}
			    	request[decodeURIComponent(pair[0]).split('.').join('')].push(decodeURIComponent(pair[1]));
			    	
			    } else
			    	request[decodeURIComponent(pair[0]).split('.').join('')] = decodeURIComponent(pair[1]);
			  }
			  return request;
		},
		
		arrayToURL: function (array) {
			  var pairs = [];
			  for (var key in array)
			    if (array.hasOwnProperty(key))
			      pairs.push(encodeURIComponent(key) + '=' + encodeURIComponent(array[key]));
			  return pairs.join('&');
		},
		
		translateOperation: function (operation)
		{
			result = '';
			if(operation == "=")
				result = "Igual"; 
			else if(operation == ">=")
				result = "MaiorIgual";
			else if(operation == "<=")
				result = "MenorIgual";
			else if(operation == "IN")
				result = "IN";
			else if(operation == "<>")
				result = "Diferente";
			
			return result;
		},
		
		cleanArray: function (array)
		{
			var pairs = [];
			for( key in array)
			{
				if(array[key] != '' && array[key] != 'null')
					pairs.push(array[key]);
			}
			
			return pairs;
		},
		
		getValues: function (values)
		{
			var url = [];
			url.push(values.value1);
			url.push(values.value2);
			url.push(values.value3);
			url.push(values.value4);
			url.push(values.value5);
			url.push(values.value6);
			url.push(values.value7);
			url.push(values.value8);
			url.push(values.value9);
			url.push(values.value10);
			
			return url.join(',');
		}
};