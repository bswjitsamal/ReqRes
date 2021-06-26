package TestSute;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import POJO.AllPoJo;
import Utils.ExcelUtils;
import Utils.Restassured_Automation_Utils;
import Utils.read_Configuration_Propertites;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ManualEntryWithAPI {

	List<String> listItemType;
	List<String> listField;
	List<String> listCreatorType;

	String URL;
	ExtentReports extent;
	ExtentTest logger;

	Restassured_Automation_Utils restUtils = new Restassured_Automation_Utils();
	Map<String, String> data = new HashMap<String, String>();

	// Primary excel file
	String originalExcelPath = System.getProperty("user.dir") + File.separator + "resources" + File.separator
			+ "TestDataSource.xls";

	// copy of the Primary excel file
	String copyExcelPath = System.getProperty("user.dir") + File.separator + "resources" + File.separator
			+ "TestDataSource.xls";

	@BeforeTest(groups = { "IntegrationTests", "EndToEnd", "IntegrationTests1" })
	public void setup() throws IOException {

		/**
		 * STEP-0: GETITNG AN ORG ID AND UPDATING THE SAME IN THE EXCEL
		 */

		read_Configuration_Propertites configDetails = new read_Configuration_Propertites();
		Properties BaseUrl = read_Configuration_Propertites.loadproperty("Configuration");
		URL = BaseUrl.getProperty("ApiBaseUrl");

	}

	@Test(priority = 1, groups = "IntegrationTests")
	public void getUserFromPage() {

		/**
		 * 
		 * STEP-1: GETITNG DATA
		 * 
		 **/

		Response itemTypeFields = restUtils.get_URL_withOneQueryParam(URL, "api/users", "page", "2");
		itemTypeFields.prettyPrint();

		// Retrieving the OrgId with index from the response
		JsonPath jsonPathEvaluator = itemTypeFields.jsonPath();
		int listField = jsonPathEvaluator.get("page");
		System.out.println("---------------" + listField);

		if (listField == 2) {
			System.out.println("We are in the correct page");
		}

		Assert.assertEquals(itemTypeFields.statusCode(), 200);
	}

	@DataProvider(name = "firstDataProvider")
	public Object[][] userEntry() throws Exception {
		Object[][] retObjArr = ExcelUtils.get_Data(originalExcelPath, "Test", 3);
		System.out.println("getData function executed!!");
		return retObjArr;
	}

	@Test(priority = 2, groups = "IntegrationTests", dataProvider = "firstDataProvider")
	public void createNewUser(String name, String job, String responseCode) {

		int statusCode = Integer.parseInt(responseCode);

		// Iterating with data
		data.put("name", name);
		data.put("job", job);

		AllPoJo postNewUser = new AllPoJo();
		String newlyAddedUser = postNewUser.addNewUser(data);
		System.out.println("--->" + newlyAddedUser);

		Response postUser = restUtils.post_URL(URL, "api/users", newlyAddedUser);
		postUser.prettyPrint();

		/**
		 * STEP-2: VERIFYING THE RESPONSE
		 */

		JsonPath jsonPathEvaluator = postUser.jsonPath();
		String nameOfTheUser = jsonPathEvaluator.get("name");
		String ProfessionOfTheUser = jsonPathEvaluator.get("job");
		System.out.println("---------------" + nameOfTheUser + ProfessionOfTheUser);

	}

}
