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
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
