package br.com.gvt.telefonia.ura.diagram.model;

public class LogPontos extends Entity<LogPontos> {

	private String id;
	private String nome;
	private String script;
	private String nome_resumido;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	public String getNome_resumido() {
		return nome_resumido;
	}
	public void setNome_resumido(String nome_resumido) {
		this.nome_resumido = nome_resumido;
	}
}
