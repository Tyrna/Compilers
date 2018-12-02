/**
 * 
 */
package ast;

import visitor.Visitor;

/**
 * @author carr
 *
 */
public class FloatTypeNode extends ASTNode {

	/**
	 * 
	 */
	public FloatTypeNode() {
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
