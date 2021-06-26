package Utils;

import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Restassured_Automation_Utils {

	public Response get_URL_Without_Params(String BaseURL) {

		return RestAssured.given().header("Content-Type", "application/json").log().all().get(BaseURL);

	}

	public Response get_URL_withOneQueryParam(String BaseURL, String URI, String value, String pathParam) {

		BaseURL = BaseURL + URI;
		return RestAssured.given().header("Content-Type", "application/json").queryParam(value, pathParam).log().all()
				.get(BaseURL);

	}

	public Response get_URL_withTwoQueryParams(String BaseURL, String URI, String value, String pathParam,
			String valueOne, String pathParamOne) {

		BaseURL = BaseURL + URI;
		return RestAssured.given().header("Content-Type", "application/json").queryParam(value, pathParam)
				.queryParam(valueOne, pathParamOne).log().all().get(BaseURL);

	}

	public Response post_WithOutBody(String BaseURL, String URI) {

		BaseURL = BaseURL + URI;

		return RestAssured.given().header("Content-Type", "application/json").log().all().post(BaseURL);
	}

	public Response post_URL_WithOne_PathParams(String BaseURL, String URI, String pathParam, Map<String, String[]> body) {

		BaseURL = BaseURL + URI;
		return RestAssured.given().header("Content-Type", "application/json")
				.pathParam("value", pathParam).body(body).log().all().post(BaseURL);

	}
	
	public Response post_URL(String BaseURL, String URI, String newlyAddedUser) {

		BaseURL = BaseURL + URI;
		return RestAssured.given().header("Content-Type", "application/json").body(newlyAddedUser).log().all().post(BaseURL);

	}


}
