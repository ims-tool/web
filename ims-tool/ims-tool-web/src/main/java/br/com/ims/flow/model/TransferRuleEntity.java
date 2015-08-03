package br.com.ims.flow.model;

import java.util.List;

@SuppressWarnings("serial")
public class TransferRuleEntity extends AbstractEntity {
	
	private Integer transferId;
	private Integer regraGrupo;
	private Integer regra;
	private Integer filho;
	private TagEntity tag;
	private TransferFieldEntity campo;
	private String operacao;
	private String valor1;
	private String valor2;
	private String valor3;
	private String valor4;
	private String valor5;
	private String valor6;
	private String valor7;
	private String valor8;
	private String valor9;
	private String valor10;
	private List<TransferOutEntity> saida;	
	private VersionEntity versionId;
	
	
	public Integer getTransferId() {
		return transferId;
	}
	public void setTransferId(Integer transferId) {
		this.transferId = transferId;
	}
	public Integer getRegraGrupo() {
		return regraGrupo;
	}
	public void setRegraGrupo(Integer regraGrupo) {
		this.regraGrupo = regraGrupo;
	}
	public Integer getRegra() {
		return regra;
	}
	public void setRegra(Integer regra) {
		this.regra = regra;
	}
	public Integer getFilho() {
		return filho;
	}
	public void setFilho(Integer filho) {
		this.filho = filho;
	}
	public TagEntity getTag() {
		return tag;
	}
	public void setTag(TagEntity tag) {
		this.tag = tag;
	}
	public TransferFieldEntity getCampo() {
		return campo;
	}
	public void setCampo(TransferFieldEntity campo) {
		this.campo = campo;
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
	public List<TransferOutEntity> getSaida() {
		return saida;
	}
	public void setSaida(List<TransferOutEntity> saida) {
		this.saida = saida;
	}
	public VersionEntity getVersionId() {
		return versionId;
	}
	public void setVersionId(VersionEntity versionId) {
		this.versionId = versionId;
	}
	
	
	
		
}
