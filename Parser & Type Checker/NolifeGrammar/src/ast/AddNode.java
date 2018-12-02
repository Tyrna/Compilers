/**
 * 
 */
package ast;

import visitor.Visitor;

public class AddNode extends BinaryNode {
	
	public AddNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
