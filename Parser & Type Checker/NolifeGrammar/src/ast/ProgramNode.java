/**
 * 
 */
package ast;

import visitor.Visitor;

/**
 * @author carr
 *
 */
public class ProgramNode extends ASTNode {

	/**
	 * 
	 */
	public ProgramNode() {
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
