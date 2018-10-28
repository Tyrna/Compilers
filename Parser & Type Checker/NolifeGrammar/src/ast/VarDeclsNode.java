/**
 * 
 */
package ast;

import visitor.Visitor;

public class VarDeclsNode extends ASTNode {

	public VarDeclsNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
