package flipkart;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import common.Common;

public class Login extends Common{

	@BeforeClass
	public void setData() {
		testName="Login";
		description="Login to Flipkart";
	}
//	@Parameters("url")
	@Test
	public void login() {
		launch();
	}
}
