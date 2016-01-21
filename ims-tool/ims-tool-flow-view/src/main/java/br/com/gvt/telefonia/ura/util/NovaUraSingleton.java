package br.com.gvt.telefonia.ura.util;


public class NovaUraSingleton {

	private static NovaUraSingleton instance;
	
	private int level = 2;
	private boolean openFlow = false;
	private String tempo = "";
	private String datasource = "IVR_PROD";
	
	public boolean getOpenFlow() {
		return openFlow;
	}

	public void setOpenFlow(boolean openFlow) {
		this.openFlow = openFlow;
	}
	
	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		//this.datasource = datasource;
	}
	
	public String getTempo() {
		return tempo;
	}

	public void setTempo(String tempo) {
		this.tempo = tempo;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	private NovaUraSingleton() {
		tempo = "";
	}
	
	public static NovaUraSingleton getInstance(){
		if(instance == null){
			instance = new NovaUraSingleton();
		}
		return instance;
	}
	
	
	
	
}
