package ast;

import visitor.Visitor;

public class StmtListNode extends ASTNode{
	
	public StmtListNode() {
		super();
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}

}
