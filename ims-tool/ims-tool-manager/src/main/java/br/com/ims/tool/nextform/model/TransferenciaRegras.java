package br.com.ims.tool.nextform.model;

public class TransferenciaRegras {

	String logroot;
	String regragrupo;
	String regra;
	String filho;
	String log;
	String nome;
	String campo;
	String tipo;
	String operacao;
	String valor1;
	String valor2;
	String valor3;
	String valor4;
	String valor5;
	String valor6;
	String valor7;
	String valor8;
	String valor9;
	String valor10;
	String saida;

	public TransferenciaRegras(String logroot, String regragrupo, String regra,
			String filho, String log, String nome, String campo, String tipo,
			String operacao, String valor1, String valor2, String valor3,
			String valor4, String valor5, String valor6, String valor7,
			String valor8, String valor9, String valor10, String saida) {
		this.logroot = logroot==null?"":logroot;
		this.regragrupo = regragrupo==null?"":regragrupo;
		this.regra = regra==null?"":regra;
		this.filho = filho==null?"":filho;
		this.log = log==null?"":log;
		this.nome = nome==null?"":nome;
		this.campo = campo==null?"":campo;
		this.tipo = tipo==null?"":tipo;
		this.operacao = operacao==null?"":(operacao.equals("=")?"==":operacao);
		this.valor1 = valor1==null?"":valor1;
		this.valor2 = valor2==null?"":valor2;
		this.valor3 = valor3==null?"":valor3;
		this.valor4 = valor4==null?"":valor4;
		this.valor5 = valor5==null?"":valor5;
		this.valor6 = valor6==null?"":valor6;
		this.valor7 = valor7==null?"":valor7;
		this.valor8 = valor8==null?"":valor8;
		this.valor9 = valor9==null?"":valor9;
		this.valor10 = valor10==null?"":valor10;
		this.saida = saida==null?"":saida;
	}
	
	public String getLogroot() {
		return logroot;
	}
	public void setLogroot(String logroot) {
		this.logroot = logroot;
	}
	public String getRegragrupo() {
		return regragrupo;
	}
	public void setRegragrupo(String regragrupo) {
		this.regragrupo = regragrupo;
	}
	public String getRegra() {
		return regra;
	}
	public void setRegra(String regra) {
		this.regra = regra;
	}
	public String getFilho() {
		return filho;
	}
	public void setFilho(String filho) {
		this.filho = filho;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getOperacao() {
		return operacao;
	}
	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}
	public String getValor1() {
		return valor1;
	}
	public void setValor1(String valor1) {
		this.valor1 = valor1;
	}
	public String getValor2() {
		return valor2;
	}
	public void setValor2(String valor2) {
		this.valor2 = valor2;
	}
	public String getValor3() {
		return valor3;
	}
	public void setValor3(String valor3) {
		this.valor3 = valor3;
	}
	public String getValor4() {
		return valor4;
	}
	public void setValor4(String valor4) {
		this.valor4 = valor4;
	}
	public String getValor5() {
		return valor5;
	}
	public void setValor5(String valor5) {
		this.valor5 = valor5;
	}
	public String getValor6() {
		return valor6;
	}
	public void setValor6(String valor6) {
		this.valor6 = valor6;
	}
	public String getValor7() {
		return valor7;
	}
	public void setValor7(String valor7) {
		this.valor7 = valor7;
	}
	public String getValor8() {
		return valor8;
	}
	public void setValor8(String valor8) {
		this.valor8 = valor8;
	}
	public String getValor9() {
		return valor9;
	}
	public void setValor9(String valor9) {
		this.valor9 = valor9;
	}
	public String getValor10() {
		return valor10;
	}
	public void setValor10(String valor10) {
		this.valor10 = valor10;
	}
	public String getSaida() {
		return saida;
	}
	public void setSaida(String saida) {
		this.saida = saida;
	}
}
