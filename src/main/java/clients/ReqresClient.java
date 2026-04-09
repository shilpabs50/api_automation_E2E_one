package clients;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;



public class ReqresClient {
	
	private RequestSpecification requestSpec;
	
	
	public ReqresClient(RequestSpecification requestSpec) {		
		this.requestSpec = requestSpec;
	}
	
	
	//to get single user
	public Response getUser(int userId) {
		
		return given()
				.log().all()
				.spec(requestSpec)
			.when()
				.get("/users/"+userId );
		
	}
	
	//to get users list with query parameter
	public Response getUsers(int page) {
		
		return given()
				.log().all()
				.spec(requestSpec)
				.queryParam("page", page)
			.when()
			 	.get("/users");
				
	}
	
	

}
