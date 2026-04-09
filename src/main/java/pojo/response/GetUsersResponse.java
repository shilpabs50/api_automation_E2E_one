package pojo.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetUsersResponse {
	
	private int page;
	private int per_page;	
	private int total;
	private int total_pages;
	private List<User> data;
	private Support support;
	//private Meta meta;
	

}
