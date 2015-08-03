package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class ChoiceEntity extends AbstractEntity{
	
	private String name;
	private Integer menuId;
	private String dtmf;
	private FormEntity nextForm;
	private ConditionEntity condition;
	private TagEntity tag;
	private VersionEntity versionId;
	
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getDtmf() {
		return dtmf;
	}
	public void setDtmf(String dtmf) {
		this.dtmf = dtmf;
	}
	public FormEntity getNextForm() {
		return nextForm;
	}
	public void setNextForm(FormEntity nextForm) {
		this.nextForm = nextForm;
	}
	public ConditionEntity getCondition() {
		return condition;
	}
	public void setCondition(ConditionEntity condition) {
		this.condition = condition;
	}
	public TagEntity getTag() {
		return tag;
	}
	public void setTag(TagEntity tag) {
		this.tag = tag;
	}
	public VersionEntity getVersionId() {
		return versionId;
	}
	public void setVersionId(VersionEntity versionId) {
		this.versionId = versionId;
	}
	
				
}
