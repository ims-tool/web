package br.com.gvt.telefonia.ura.report;

public class FormInfo {
	public long formid;
	public String formname;
	public String tag;
	public int totalTag;
	public double porc;
	public String dateIni;
	public String dateFim;
	
	public String getDateIni() {
		return dateIni;
	}
	public void setDateIni(String dateIni) {
		this.dateIni = dateIni;
	}
	public String getDateFim() {
		return dateFim;
	}
	public void setDateFim(String dateFim) {
		this.dateFim = dateFim;
	}
	public String getFormname() {
		return formname;
	}
	public void setFormname(String formname) {
		this.formname = formname;
	}
	public long getFormid() {
		return formid;
	}
	public void setFormid(long formid) {
		this.formid = formid;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getTotalTag() {
		return totalTag;
	}
	public void setTotalTag(int totalTag) {
		this.totalTag = totalTag;
	}
	public double getPorc() {
		return porc;
	}
	public void setPorc(double porc) {
		this.porc = porc;
	}
	
	public String toString()
	{
		return "<br /><a class='relInfo' href='http://telefonia/recall/mdin_incidencias2.aspx?amb=1&tag="+tag+"&datai="+dateIni+"&dataf="+dateFim+"&FORMNAME="+formname+"' target='_blank'>total: " + totalTag + " - " + porc + "</a>";
	}
}
