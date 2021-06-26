package POJO;

import java.util.Map;

import com.google.gson.Gson;

public class AllPoJo {
	
	public String addNewUser(Map<String,String> data) {
		
		UserPojo upDateId = new UserPojo();
    	 
		 upDateId.setName(data.get("name"));
		 upDateId.setJob(data.get("job"));
		 
		 Gson Josnbody = new Gson();
		 return Josnbody.toJson(upDateId);		
	}

}
