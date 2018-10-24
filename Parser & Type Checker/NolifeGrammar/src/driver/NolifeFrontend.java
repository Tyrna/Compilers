package driver;
/**
 * 
 */


import java.io.FileNotFoundException;
import java.io.FileReader;

import ast.*;
import parser.*;
import visitor.*;

/**
 * @author carr
 * 
 */
public class NolifeFrontend extends Frontend {
	
	private static ASTNodeFactory factory;

	@Override
	public void parseFile(String fileName) throws FileNotFoundException {

	    FileReader nolifeFile = new FileReader(fileName);
	    NolifeParser parser = new NolifeParser(nolifeFile);
		try {
			factory = new ASTNodeFactory();
			ASTNode node = parser.program();
			SourceVisitor v = new SourceVisitor();
		    node.accept(v);
		    System.out.println("Program is:\n\n"+v.getSrc());
		} catch (ParseException e) {
			System.err.println("Syntax Error in " + fileName + ": " + e);
			System.exit(-1);
		}
		
		System.out.println(fileName + " parsed successfully!");
	}

}
