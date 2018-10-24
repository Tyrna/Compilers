package ast;

import visitor.Visitor;

public class WriteNode extends ASTNode {
	
	public WriteNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
