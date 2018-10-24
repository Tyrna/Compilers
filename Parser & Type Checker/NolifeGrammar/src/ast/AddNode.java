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
	public void accept(Visitor v) {
		v.visit(this);
	}

}
