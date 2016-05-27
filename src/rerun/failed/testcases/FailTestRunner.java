package rerun.failed.testcases;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;
import org.testng.annotations.Test;

public class FailTestRunner {
	@Test
	public static void FailTestRunner(){
		TestNG runner = new TestNG();
		
		List<String> list = new ArrayList<String>();
		list.add(System.getProperty("user.dir")+"//test-output//testng-failed.xml");
		
		runner.setTestSuites(list);
		runner.run();
		
	}

}
