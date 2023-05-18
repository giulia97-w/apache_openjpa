package org.apache.openjpa.jdbc.identifier;

import org.apache.openjpa.jdbc.identifier.DBIdentifier.DBIdentifierType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class QualifiedIdentifierSplitPathTest {

    private boolean output;
    private DBIdentifier identifier ;

    @Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][]{
            { true  , DBIdentifier.newTable("Column.Table")},
            {false, DBIdentifier.newColumn(null)},
        });
    }

    public QualifiedIdentifierSplitPathTest(boolean output , DBIdentifier identifier ){
        this.output = output;
        this.identifier = identifier;    
    }

    @Test
    public void splitPathTest() {
    	if(identifier == DBIdentifier.newColumn(null)) {
    		DBIdentifier[] result = QualifiedDBIdentifier.splitPath(identifier);
            assertNotNull(result);
    	}else {
        DBIdentifier[] path = QualifiedDBIdentifier.splitPath(identifier);
        int expectedLength = output ? 2 : 0;
        assertEquals(expectedLength, path.length);
        
    	}
        
        
        }
    

}
        
	
    
    
    
    


