function handleDrop(event, ui) {
    var droppedFormType = ui.draggable;			 
    droppedFormType.fadeOut('fast');			        
}
 			   
function handleFormUpdateRequest(xhr, status, args) {		        						       
    if(!args.validationFailed) {				        	
    	if(args.saved == true) {
        	PF('settingFormDlg').hide();
        }        	
    }			        
} 
function handleComplementSaveRequest(xhr, status, args) {		        						       
    if(!args.validationFailed) {				        	
    	if(args.saved == true) {
        	PF('settingComplementDlg').hide();
        }        	
    }			        
}

function handleAuxiliarSaveRequest(xhr, status, args) {		        						       
    if(!args.validationFailed) {				        	
    	if(args.saved == true) {
        	PF('settingAuxiliarDlg').hide();
        }        
    }			        
}
function handleUtilSaveRequest(xhr, status, args) {		        						       
    if(!args.validationFailed) {				        	
    	if(args.saved == true) {
        	PF('settingUtilDlg').hide();
        }        	
    }			        
}
function handleOtherSaveRequest(xhr, status, args) {		        						       
    if(!args.validationFailed) {				        	
    	if(args.saved == true) {
        	PF('settingOtherDlg').hide();
        }        	
    }			        
}
function handleAdminSaveRequest(xhr, status, args) {		        						       
    if(!args.validationFailed) {				        	
    	if(args.saved == true) {
        	PF('settingAdminDlg').hide();
        }        	
    }			        
}
function node_onMouseOut(nodeElement,id) {
	var parent = nodeElement.parentElement;
	diagram_onNodeMove([
		{name : 'node_id', value : parent.id},
		{name : 'node_x', value : parent.style['left']},
		{name : 'node_y',value : parent.style['top']}
       	]);
}
function node_addContextMenu(div_name,node_id,node_name,form_type) {
	console.log(form_type);
	if(form_type == 'DecisionChance' ||
			form_type == 'Choice' ||
			form_type == 'NoMatchInput') {
		context.attach('#'+div_name, [
		                          		
  	  		{header: node_name},
  	  		{text: 'View', action: function(e) {selectElement(node_id);}}
  	  	]);
	} else {
		context.attach('#'+div_name, [
			                          		
	  		{header: node_name},
	  		{text: 'View', action: function(e) {selectElement(node_id);}},
	  		{text: 'Delete', action: function(e) {deleteElement(node_id);}}
	  	]);
	}
}
function selectElement(node_id) {
	diagram_onNodeSelect([{name : 'node_id', value : node_id}]);		
}
function selectElementDbClick(node_id) {
	document.getElementById("formFlow:nodeId").value= node_id;
	document.getElementById("formFlow:buttonElementSelected").click();
}
function deleteElement(node_id) {
	
	document.getElementById("formFlow:deleteControl").value="first";
	document.getElementById("formFlow:nodeId").value= node_id;
	document.getElementById("formFlow:buttonDeleteNode").click();
	
			
}

$(document).ready(function(){
	
	context.init({preventDoubleContext: false});
						
	context.settings({compress: true});
	
	$(document).on('mouseover', '.me-codesta', function(){
		$('.finale h1:first').css({opacity:0});
		$('.finale h1:last').css({opacity:1});
	});
	
	$(document).on('mouseout', '.me-codesta', function(){
		$('.finale h1:last').css({opacity:0});
		$('.finale h1:first').css({opacity:1});
	});
	
});
window.onbeforeunload = function (e) {
    e = e || window.event;

    if(document.getElementById('formFlow:editing').value) {
	
	    // For IE and Firefox prior to version 4
	    if (e) {
	        e.returnValue = "You have work does not save.\r\nIf you continue, your changes will be discarded.";
	    }
	
	    // For Safari
	    return "You have work does not save.\r\nIf you continue, your changes will be discarded.";
	}
};
