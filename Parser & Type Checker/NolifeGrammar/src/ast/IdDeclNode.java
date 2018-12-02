/**
 * 
 */
package ast;

import visitor.Visitor;

public class IdDeclNode extends ASTNode {

	/**
	 * 
	 */
	public IdDeclNode() {
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
