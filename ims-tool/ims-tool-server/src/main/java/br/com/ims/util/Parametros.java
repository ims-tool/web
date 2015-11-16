package br.com.ims.util;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.ims.tool.entity.Parameters;
@XmlRootElement
public class Parametros {

	@XmlElement(required = true)
	private  List<Parameters> list;
	
	public Parametros(){
		list = new ArrayList<Parameters>();
	}

	public List<Parameters> getList() {
		return list;
	}

	public void setList(List<Parameters> list) {
		this.list = list;
	}
}
