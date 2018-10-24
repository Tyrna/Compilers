package ast;

import visitor.Visitor;

public class CharTypeNode extends ASTNode {
	
	/**
	 * 
	 */
	public CharTypeNode() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see astv3.ASTNode#accept(visitor.Visitor)
	 */
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}


}
