package ast;

import visitor.Visitor;

public class WriteNode extends ASTNode {
	
	public WriteNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
