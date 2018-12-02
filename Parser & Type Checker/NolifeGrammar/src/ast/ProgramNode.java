/**
 * 
 */
package ast;

import java.util.HashMap;

import visitor.Visitor;

public class ProgramNode extends ASTNode {
	
	private HashMap<String, Integer> symTable;

	public ProgramNode() {
		super();
	}
	
	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

	public void setTable(HashMap<String, Integer> s) {
		symTable = s;
	}
	
	public HashMap<String, Integer> getTable(){
		return symTable;
	}

}
