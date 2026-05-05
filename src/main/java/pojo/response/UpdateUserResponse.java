package pojo.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class UpdateUserResponse {
	
	private String name;
	private String job;
	private Date UpdatedAt;

}
