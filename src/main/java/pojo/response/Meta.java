package pojo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Meta {
	
	
	private String powered_by;
	private String docs_url;
	private String upgrade_url;
	private String example_url;
	private String variant;
	private String message;
	private Cta cta;
	private String context;

}
