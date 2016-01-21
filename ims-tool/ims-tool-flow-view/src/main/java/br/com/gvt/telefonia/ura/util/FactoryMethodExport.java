package br.com.gvt.telefonia.ura.util;

import br.com.gvt.telefonia.ura.export.IExport;
import br.com.gvt.telefonia.ura.export.MermaidExport;
import br.com.gvt.telefonia.ura.export.YedExport;

public class FactoryMethodExport {
	public static IExport createExport(String className)
	{
		IExport instance = null;
		
		if(className.equals(YedExport.class.getSimpleName()))
			instance = new YedExport();
		else if(className.equals(MermaidExport.class.getSimpleName()))
			instance = new MermaidExport();
			
		return instance;
	}
}
