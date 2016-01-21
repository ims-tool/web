var zoom = 100;
var x, y,posx,posy;

var mermaid_config = {
    startOnLoad:false,
    init: function ()
    {
		zoom = 70;
		$( ".mermaid" ).css( "zoom" ,  zoom+'%');
    }
};

function zoomIn()
{
	zoom = zoom+10;
	$( ".mermaid" ).css( "zoom" ,  zoom+'%');
	
	$( "body" ).scrollLeft( posx );
	$( "body" ).scrollTop( posy );
	
}
function zoomOut()
{
	if(zoom > 10)
		zoom = zoom-10;
	$( ".mermaid" ).css( "zoom" ,  zoom+'%');
	
	$( "body" ).scrollLeft( posx );
	$( "body" ).scrollTop( posy );
	
}
		

function handleMouse(e) {
  // Verify that x and y already have some value
  
  if (x && y) {
    // Scroll window by difference between current and previous positions
    window.scrollBy( (e.clientX - x)*-1, (e.clientY - y)*-1 );
  }
  x = e.clientX;
  y = e.clientY;
  // Store current position
  
}

function handleMouseUp(e) {
	x = e.clientX;
	y = e.clientY;
}

var customMouse = {
	
	init: function ()
	{
		this.cursor();
		this.hideSideBar();
		this.updateMousePosition();
		this.mouseWheel();
		this.mouseDownUp();
		this.click();
	},
		
	cursor: function (){
		$('.mermaid a').parent().parent().parent().parent().parent().mouseover(function(e){
			$('body').css('cursor','pointer');      
		});
		
		$('.mermaid a').parent().parent().parent().parent().parent().mouseout(function(e){
			$('body').css('cursor','move');      
		});
	},
	
	hideSideBar: function ()
	{
	},
	
	updateMousePosition: function ()
	{
		$(document).bind('mousemove',function(e){ 
		    posx = e.clientX;
			posy = e.clientY;     
		});
	},
	
	mouseWheel: function ()
	{
		$('.mermaid').mousewheel(function(event, delta) {
			if(delta == 1)
				zoomIn();
			else
				zoomOut();
				
			return false;
		});
	},
	
	mouseDownUp: function ()
	{
		$('.mermaid').mousedown(function (){
			document.onmousemove = handleMouse;
		});
		
		$('.mermaid').mouseup(function (){
			document.onmousemove = handleMouseUp;
			
		});
	},
	
	defaultnextformClick: function ()
	{
		parent.window.APP.defaultnextformClick();
	},
	
	click: function () {
		$('.mermaid a').unbind().click(function (){
			return false;
		});
		
		$('.mermaid a').parent().parent().parent().parent().parent().mousedown(function (e){
			e.preventDefault();
			if(e.button == 0){
				
				var id = $(this).find('a').attr('id');
				var type = $(this).find('a').attr('elementType');
				var href = $(this).find('a').attr('href').split("=");
				var formId = href[1];
				
				parent.window.APP.elementId = id;
				parent.window.APP.elementType = type;
				
				$(parent.document).find("#wrapper").find('.sidebar-brand cite').html(type);
				
				if($(parent.document).find("#wrapper").attr("class") != "active")
					$(parent.document).find("#wrapper").addClass("active");
					
				$('g').css('stroke','#555');
				$('g').css('stroke-width','1.5px');
				$(this).css('stroke','#33CC33');
				$(this).css('stroke-width','5px');
				e.preventDefault();
				
				var result = "";
				$.get("Main?action=loadElement&id="+id+"&type="+type+"&formid="+formId,function (obj){
					result = "";
					$.each(obj, function(i, val) {
					  result += i + ": " + val + "<br />";
					});
					
					$(parent.document).find('.yed-download').attr('formid',obj.id);
					$(parent.document).find('.yed-download').attr('form',obj.form);
					$(parent.document).find('.element-text').html(result);
					$(parent.document).find('.btn-action-container a').attr('elementId',id);
					$(parent.document).find('.btn-action-container a').attr('elementType',type);
					customMouse.defaultnextformClick();
					parent.window.APP.editor.nextformEvent();
					if(!parent.EdicaoVisivel)
						$('.editar-nextform').css('display','none');
				},"json");
				
			} else{
				if( $(this).find('a').attr('href').slice(-2) != "=0" ){
					var level = $(parent.document).find('#level').val();
					
					if($(this).find('a').attr('target') != undefined){
						window.open($(this).find('a').attr('href'));
						return false;
					}else{
						parent.APP.showLoading();
						window.location.href = $(this).find('a').attr('href') + '&level='+level;
					}
				}
			}
			return false;
		});
	}
};

var ApplicationFeatures = {
		
		init: function ()
		{
			this.beforeCurrentForm();
			this.showCondition();
		},
		
		beforeCurrentForm: function ()
		{
			$('.beforeCurrentForm').unbind().change(function (){
				if($(this).val() != '')
					parent.APP.frameLoad($(this).val());
			});
		},
		
		showCondition: function (){
			$('.teste').hide();
			
			$('[data-toggle="tooltip"]').parent().parent().parent().parent().parent().mouseover(function (e){
				
				$('.teste').css('margin-left',e.clientX);
				$('.teste').css('margin-top',e.clientY);
				
				$('.teste').html($(this).find('a').attr('title'));
				$('.teste').append('<a target="_blank" href="http://telefonia/recall2/MDIN_CONDITION2.ASPX?FORM='+$(this).find('a').attr('elementName')+'">Mais Detalhes</a>');
				$('.teste').append('<a class="visualizar-condition" style="float: right" href="javascript:void(0);" id="'+$(this).find('a').attr('id')+'">Visualizar</a>');
				
				$('.teste').show(function (){
					setTimeout(function(){ $('.teste').hide('slow'); }, 5000);
				});
				
				$('.visualizar-condition').click(function (){
					parent.APP.showFrameCondition($(this).attr('id'));
				});
			});
		}
};

function stopEvent(event){
	 if(event.preventDefault != undefined)
	  event.preventDefault();
	 if(event.stopPropagation != undefined)
	  event.stopPropagation();
}

$(document).ready(function (){
	try{

		document.oncontextmenu = function(e){
			 var evt = new Object({keyCode:93});
			 stopEvent(e);
			 return false;
		}

		
		try{
			parent.APP.hideLoading();
		} catch(e){}
		
		var customMermaidInit = mermaid.init;
		mermaid.changeColor = function (){
			$('#mermaid').css('color','#000');
			$('#mermaid a').css('color','#000');
		};
		
		mermaid.init = function (){
			customMermaidInit();
			mermaid.changeColor();
			
			$.each($('.relInfo'),function (index,obj){
				$(obj).text($(obj).text()+"%");
				$(obj).parent().parent().parent().width($(obj).parent().parent().parent().width()+60);
			});
		};
		
		mermaid.init();
	} catch(e){
		
		if(e.message.indexOf('Lexical') != -1)
		{
			alert("Elemento cadastrado com caracteres inválidos no banco de dados");
		} else 
			console.log(e);
	}

	mermaid_config.init();
	customMouse.init();
	
	ApplicationFeatures.init();

});