package br.com.ims.tool.nextform.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class NextFormDto implements Serializable {
	private static final long serialVersionUID = 5656420580110308160L;
	private long id;
	private String name;
	private String description;
	private long formtype;
	private long formid;
	private FormTypeDto formTypeDto;
	private AnnounceDto announce;
	private PromptCollectDto promptCollect;
	private NoMatchInputDto noMatchInput;
	private MenuDto menu;
	
	private long tag;
	
	private FlowDto flow;
	private TransferDto transfer;
	private DisconnectDto disconnect;
	private ReturnDto return_;
	
	private String jsonContext;
	
	private long nextFormDefault;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public long getFormtype() {
		return formtype;
	}

	public void setFormtype(long formtype) {
		this.formtype = formtype;
	}

	public long getFormid() {
		return formid;
	}

	public void setFormid(long formid) {
		this.formid = formid;
	}

	public FormTypeDto getFormTypeDto() {
		return formTypeDto;
	}

	public void setFormTypeDto(FormTypeDto formTypeDto) {
		this.formTypeDto = formTypeDto;
	}

	public AnnounceDto getAnnounce() {
		return announce;
	}

	public void setAnnounce(AnnounceDto announce) {
		this.announce = announce;
	}

	public PromptCollectDto getPromptCollect() {
		return promptCollect;
	}

	public void setPromptCollect(PromptCollectDto promptCollect) {
		this.promptCollect = promptCollect;
	}

	public NoMatchInputDto getNoMatchInput() {
		return noMatchInput;
	}

	public void setNoMatchInput(NoMatchInputDto noMatchInput) {
		this.noMatchInput = noMatchInput;
	}

	public MenuDto getMenu() {
		return menu;
	}

	public void setMenu(MenuDto menu) {
		this.menu = menu;
	}

	public String getJsonContexto() {
		return jsonContext;
	}

	public void setJsonContexto(String jsonContexto) {
		this.jsonContext = jsonContexto;
	}

	public FlowDto getFlow() {
		return flow;
	}

	public void setFlow(FlowDto flow) {
		this.flow = flow;
	}

	public TransferDto getTransfer() {
		return transfer;
	}

	public void setTransfer(TransferDto transfer) {
		this.transfer = transfer;
	}

	public DisconnectDto getDisconnect() {
		return disconnect;
	}

	public void setDisconnect(DisconnectDto disconnect) {
		this.disconnect = disconnect;
	}

	public long getTag() {
		return tag;
	}

	public void setTag(long tag) {
		this.tag = tag;
	}

	public long getNextFormDefault() {
		return nextFormDefault;
	}

	public void setNextFormDefault(long nextFormDefault) {
		this.nextFormDefault = nextFormDefault;
	}
 
	public ReturnDto getReturn_() {
		return return_;
	}

	public void setReturn_(ReturnDto return_) {
		this.return_ = return_;
	}

	@Override
	public String toString() {
		return "NextFormDto [id=" + id + ", name=" + name + ", description="
				+ description + ", formtype=" + formtype + ", formid=" + formid
				+ ", formTypeDto=" + formTypeDto + ", announce=" + announce
				+ ", promptCollect=" + promptCollect + ", noMatchInput="
				+ noMatchInput + ", menu=" + menu + ", tag=" + tag + ", flow="
				+ flow + ", transfer=" + transfer + ", disconnect="
				+ disconnect + ", jsonContexto=" + jsonContext
				+ ", nextFormDefault=" + nextFormDefault + "]";
	}
	
}
