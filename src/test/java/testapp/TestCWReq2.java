package testapp;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import app.CWReq2;
import app.Film;
import junit.framework.TestCase;

public class TestCWReq2 extends TestCase {
	
	private CWReq2 r;

	public TestCWReq2(String testName) throws FileNotFoundException {
		super(testName);
		r = new CWReq2("src/test/java/testapp/DBconfiguration1.json");
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
    
	/* -------------------------------------------------------------
	 * TODO: getExpected() to be completed as part of the coursework.
	 * --------------------------------------------------------------
	 */
	/* ---------------------------------------------------------------------
	 * The getExpected() method returns yours requirements's expected results.
	 * These results can be obtained by writing complex SQL queries 
	 * and using the ResultSet API to obtain the expected results. Unlike 
	 * the requirement code, where you talk to the database multiple times 
	 * to get tables into memory and compute over them, the expected results 
	 * can be obtained by running SQL queries which have advanced SQL 
	 * primitives in it such as SUM, COUNT, ORDER BY, GROUP BY, etc.
	 * In this instance, the return type is a String, you are free to choose
	 * other return types depending on the requirement. You are allowed to 
	 * write additional helper methods.
	 * ---------------------------------------------------------------------
	 */
    
    private ArrayList<Long> getExpected() throws SQLException {
    	ArrayList<Long> documentaryFilmIDs = new ArrayList<Long>();
    	long filmID;
    	ResultSet rs = r.getResultSet("select count(film_id) from film where description like \'%Documentary%\'");
    	while(rs.next()) {
    		filmID = rs.getLong("count(film_id)");
    		documentaryFilmIDs.add(filmID);
    		}
    	return documentaryFilmIDs;
    }
	
	/* -------------------------------------------------------------
	 * TODO: testAndOutput() to be modified, if required, as part 
	 * of the coursework.
	 * --------------------------------------------------------------
	 */
	/* ---------------------------------------------------------------------
	 * The testAndOutput() method does two things:
	 *    1. Invokes the method which prints output from the requirement 
	 *    	 code on to the console in a human-friendly format.
	 *    2. Compares the expected result with the actual result.
	 *    
	 * This method may need to be modified if you are using a type which 
	 * is other than a string for returning your expected and actual results.
	 * ---------------------------------------------------------------------
	 */
    /**
     *  Output Results and Test Sample Requirement
     * @throws FileNotFoundException 
     * @throws SQLException 
     */
    
    public void testAndOutput() throws FileNotFoundException, SQLException
    {
    	r.printOutput();
    	ArrayList<Long> actualIDs = new ArrayList<Long>();
    	ArrayList<Long> expectedIDs = getExpected();
    	actualIDs = r.getActual();
    	
    	assertEquals(expectedIDs, actualIDs);
    }
}
