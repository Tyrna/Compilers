package ast;

import visitor.Visitor;

public class StmtListNode extends ASTNode{
	
	public StmtListNode() {
		super();
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
