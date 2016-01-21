package br.com.gvt.telefonia.ura.diagram.model;


public class PromptCollect extends Entity<PromptCollect> {

	private long id;
	private String name;	
	private String description;
	private long grammar;
	private long flushprompt;
	private long prompt;
	private long noinput;
	private long nomatch;
	private String fetchtimeout;
	private String interdigittimeout;
	private String terminatingtimeout;
	private String terminatingcharacter;
	private long nextform;
	private String tag;
	private String versionid;
	
	public long getGrammar() {
		return grammar;
	}
	public void setGrammar(long grammar) {
		this.grammar = grammar;
	}
	public long getFlushprompt() {
		return flushprompt;
	}
	public void setFlushprompt(long flushprompt) {
		this.flushprompt = flushprompt;
	}
	public String getFetchtimeout() {
		return fetchtimeout;
	}
	public void setFetchtimeout(String fetchtimeout) {
		this.fetchtimeout = fetchtimeout;
	}
	public String getInterdigittimeout() {
		return interdigittimeout;
	}
	public void setInterdigittimeout(String interdigittimeout) {
		this.interdigittimeout = interdigittimeout;
	}
	public String getTerminatingtimeout() {
		return terminatingtimeout;
	}
	public void setTerminatingtimeout(String terminatingtimeout) {
		this.terminatingtimeout = terminatingtimeout;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getVersionid() {
		return versionid;
	}
	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}
	public long getNextform() {
		return nextform;
	}
	public void setNextform(long nextform) {
		this.nextform = nextform;
	}
	public long getNomatch() {
		return nomatch;
	}
	public void setNomatch(long nomatch) {
		this.nomatch = nomatch;
	}
	public long getNoinput() {
		return noinput;
	}
	public void setNoinput(long noinput) {
		this.noinput = noinput;
	}
	
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
	public long getPrompt() {
		return prompt;
	}
	public void setPrompt(long prompt) {
		this.prompt = prompt;
	}
	
	public String getTerminatingcharacter() {
		return terminatingcharacter;
	}
	public void setTerminatingcharacter(String terminatingcharacter) {
		this.terminatingcharacter = terminatingcharacter;
	}
}
