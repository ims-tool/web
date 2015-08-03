package br.com.ims.flow.model;

@SuppressWarnings("serial")
public class ConditionEntity extends AbstractEntity{
	
	private String name;
	private String description;
	private TagEntity tag;	
	private VersionEntity versionId;
	private ConditionGroupEntity conditionGroup;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public ConditionGroupEntity getConditionGroup() {
		return conditionGroup;
	}
	public void setConditionGroup(ConditionGroupEntity conditionGroup) {
		this.conditionGroup = conditionGroup;
	}
	
	
	
	
}
