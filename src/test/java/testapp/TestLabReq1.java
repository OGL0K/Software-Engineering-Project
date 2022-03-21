package testapp;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.Actor;
import app.LabReq1;
import junit.framework.TestCase;

/**
 * 
 * Tests that the LabReq1 class is returning the correct list of actor last names
 * 
 * @author Mariam Cirovic
 * @author Template code Santanu Dash
 * 
 */
public class TestLabReq1 extends TestCase {
	
	private LabReq1 r;

	public TestLabReq1(String testName) throws FileNotFoundException {
		super(testName);
		r = new LabReq1("src/test/java/testapp/DBconfiguration1.json");
	}
	
    @Override
    protected void setUp() throws Exception {
    	System.out.println("\n\n\n\n");
    	System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    	System.out.println("Running tests in " + this.getClass().getName() + "...");
    	System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++\n\n");
    	r.openconn();
    }
    
    @Override
    protected void tearDown() throws Exception {
    	r.closeconn();
    	System.out.println("Finished tests in " + this.getClass().getName());
    	System.out.println("-------------------------------------------------------\n\n");
    }
    
	/**
	 * Use an SQL query to determine the list of last names that are common to 3 or more actors
	 * 
	 * @return The list of Actor last names
	 * @throws SQLException 
	 */    
    private List<String> getExpected() throws SQLException {
    	List<String> actorLastNames = new ArrayList<String>();
    	String lastName;
    	
    	ResultSet rs = r.getResultSet("select last_name\n" + 
    			"from actor\n" + 
    			"group by last_name\n" + 
    			"having count(last_name) > 2;");
    	while(rs.next()) {
    		lastName = rs.getString("last_name");
    		actorLastNames.add(lastName);
    	}
    	
    	return actorLastNames;
    }   
	
    /**
     * Output Results and Tests Lab Requirement 1
     *  
     * @throws FileNotFoundException 
     * @throws SQLException 
     */   
    public void testAndOutput() throws FileNotFoundException, SQLException
    {
    	r.printOutput();
    	List<String> actual = r.getActual();
    	List<String> expected = getExpected();
    	assertEquals(expected, actual);
    }
}
