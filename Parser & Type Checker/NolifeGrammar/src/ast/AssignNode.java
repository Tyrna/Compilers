/**
 * 
 */
package ast;

import visitor.Visitor;

/**
 * @author carr
 *
 */
public class AssignNode extends ASTNode {

	/**
	 * 
	 */
	public AssignNode() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see astv3.ASTNode#accept(visitor.Visitor)
	 */
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
	
	public ASTNode getLHS() {
		return children.get(0);
	}
	
	public ASTNode getRHS() {
		return children.get(1);
	}

}
