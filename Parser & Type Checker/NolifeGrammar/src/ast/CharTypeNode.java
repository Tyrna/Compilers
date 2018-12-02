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
	public Object accept(Visitor v) {
		return v.visit(this);
	}


}
