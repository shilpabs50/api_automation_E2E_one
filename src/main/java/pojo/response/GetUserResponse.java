package pojo.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;



@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetUserResponse {
	
	
	private User data;
	private Support support;
	//private Meta meta;
	
	

}
