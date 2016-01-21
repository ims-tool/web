package br.com.gvt.telefonia.ura.export;

import java.util.List;

import br.com.gvt.telefonia.ura.util.FluxElement;

public interface IExport {
	
	public String output(List<FluxElement> listElements);
}
