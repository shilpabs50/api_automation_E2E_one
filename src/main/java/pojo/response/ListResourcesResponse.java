package pojo.response;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListResourcesResponse {
	
	 	private int page;
	 	private int per_page;
	 	private int total;
	 	private int total_pages;
	    private ArrayList<Datum> data;
	    private Support support;
	    private Meta _meta;

}
