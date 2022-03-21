package testapp;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.LabReq2;
import junit.framework.TestCase;

/**
 * 
 * Tests that the LabReq2 class is returning the correct list of actor names
 * 
 * @author Mariam Cirovic
 * @author Template code Santanu Dash
 * 
 */
public class TestLabReq2 extends TestCase {
	
	private LabReq2 r;
	
	public TestLabReq2(String testName) throws FileNotFoundException {
		super(testName);
		r = new LabReq2("src/test/java/testapp/DBconfiguration1.json");
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
	 * Use an SQL query to determine the list of actor names that acted in Karate Moon
	 * 
	 * @return The list of Actor last names
	 * @throws SQLException 
	 */      
    private List<String> getExpected() throws SQLException{
    	
    	List<String> actorNames = new ArrayList<String>();
    	
    	String actorName;
    	
    	ResultSet rs = r.getResultSet("Select concat (first_name, \" \", last_name) as actor_name \n" + 
    			"from actor, film, film_actor \n" + 
    			"where film.title = 'KARATE MOON' \n" + 
    			"and film.film_id = film_actor.film_id \n" + 
    			"and film_actor.actor_id = actor.actor_id;");

    	while(rs.next()) {
    		actorName = rs.getString("actor_name");
    		actorNames.add(actorName);
    	}
    	
    	return actorNames;
    }

    /**
     *Output Results and Test Requirement 2
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
