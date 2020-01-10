package tech.tora.tools.system;

public enum LogLevel {

	INFO("information")
	,WARN("warning")
	,ERROR("error")
	;
	
	public String level;
	
	LogLevel(String value) {
		this.level = value;
	}
	
}
