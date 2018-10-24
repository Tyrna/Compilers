/**
 * 
 */
package visitor;

import java.util.HashMap;
import java.util.Stack;

import ast.*;
/**
 * @author carr
 *
 */
public class TypeChecker implements Visitor {

	//RealType = Children
	//ConvertedType = Parent
	private final int INTEGER = 0;
	private final int FLOAT = 1;
	private final int CHAR = 2;
	private final int ANYTYPE = 3;
	private int declType;
	private String defVal;
	private int exprType;
	private String id;
	
	private Stack<HashMap<String, Integer>> symTableStack = new Stack<HashMap<String, Integer>>();
	/**
	 * 
	 */
	public TypeChecker() {
	}
	
	private void arithTypeCheck(BinaryNode n) {
		n.getLeft().accept(this);
		int leftType = exprType;
		n.getRight().accept(this);
		int rightType = exprType;
		
		if (leftType == rightType)
			exprType = leftType;
		else if (leftType == ANYTYPE || rightType == ANYTYPE)
			exprType = ANYTYPE;
		else if (leftType == FLOAT && rightType == INTEGER ||
					leftType == INTEGER && rightType == FLOAT)
			exprType = FLOAT;
		else {
			System.err.println("Type error");
			exprType = ANYTYPE;
		}		
	}
	
	private void compTypeCheck(BinaryNode n) {
		n.getLeft().accept(this);
		int leftType = exprType;
		n.getRight().accept(this);
		int rightType = exprType;
		
		if (leftType == rightType)
			exprType = leftType;
		else if (leftType == ANYTYPE || rightType == ANYTYPE)
			exprType = ANYTYPE;
		else if (leftType == FLOAT && rightType == INTEGER ||
					leftType == INTEGER && rightType == FLOAT)
			exprType = INTEGER;
		else {
			System.err.println("Type error");
			exprType = ANYTYPE;
		}		
	}
	
	@Override
	public void visit(SymNode n) {
		
	}
	
	@Override
	public void visit(IntNode n) {
		exprType = 0;
	}
	
	@Override
	public void visit(FloatNode n) {
		exprType = 1;
	}
	
	@Override
	public void visit(CharNode n) {
		exprType = 2;
	}

	/* (non-Javadoc)
	 * @see visitor.Visitor#visit(astv3.AddNode)
	 */
	@Override
	public void visit(AddNode n) {
		arithTypeCheck(n);
	}

	/* (non-Javadoc)
	 * @see visitor.Visitor#visit(astv3.SubNode)
	 */
	@Override
	public void visit(SubNode n) {
		arithTypeCheck(n);
	}

	/* (non-Javadoc)
	 * @see visitor.Visitor#visit(astv3.DivNode)
	 */
	@Override
	public void visit(ModNode n) {
		arithTypeCheck(n);
	}

	/* (non-Javadoc)
	 * @see visitor.Visitor#visit(astv3.MulNode)
	 */
	@Override
	public void visit(MulNode n) {
		arithTypeCheck(n);
	}
	
	@Override
	public void visit(LessThanNode n) {
		compTypeCheck(n);
	}
	
	@Override
	public void visit(LessEqualNode n) {
		compTypeCheck(n);
	}
	
	@Override
	public void visit(GreaterThanNode n) {
		compTypeCheck(n);
	}
	
	@Override
	public void visit(GreaterEqualNode n) {
		compTypeCheck(n);
	}
	
	@Override
	public void visit(NotEqualNode n) {
		compTypeCheck(n);
	}
	
	@Override
	public void visit(EqualNode n) {
		compTypeCheck(n);
	}
	
	@Override
	public void visit(AndNode n) {
		//typeCheck(n);
	}
	
	@Override
	public void visit(OrNode n) {
		//typeCheck(n);
	}

	/* (non-Javadoc)
	 * @see visitor.Visitor#visit(astv3.IdNode)
	 */
	@Override
	public void visit(IdRefNode n) {
		int sym;
		if ((sym = findSymbol(n.getLabel())) >= 0)
			exprType = sym;
		else {
			System.err.println("Undeclared variable: "+n.getLabel());
			exprType = ANYTYPE;
		}
		
	}
	
	@Override
	public void visit(ArrayRefNode arrayRefNode) {
		
	}
	
	@Override
	public void visit(StringNode stringNode) {

	}

	@Override
	public void visit(ParenNode parenNode) {

	}
	

	@Override
	public void visit(NotNode notNode) {

	}

	/* (non-Javadoc)
	 * @see visitor.Visitor#visit(astv3.DeclNode)
	 */
	@Override
	public void visit(DeclNode declNode) {
		for (ASTNode n : declNode.getChildren())
			n.accept(this);
	}

	/* (non-Javadoc)
	 * @see visitor.Visitor#visit(astv3.ProgramNode)
	 */
	@Override
	public void visit(ProgramNode programNode) {
		newFrame();
		for (ASTNode n : programNode.getChildren())
			n.accept(this);
		remFrame();
	}

	/* (non-Javadoc)
	 * @see visitor.Visitor#visit(astv3.VarDeclsNode)
	 */
	@Override
	public void visit(VarDeclsNode varDeclsNode) {
		for (ASTNode n : varDeclsNode.getChildren())
			n.accept(this);
	}

	/* (non-Javadoc)
	 * @see visitor.Visitor#visit(astv3.IntTypeNode)
	 */
	@Override
	public void visit(IntTypeNode intTypeNode) {
		declType = INTEGER;
		intTypeNode.getChild(0).accept(this);
	}

	/* (non-Javadoc)
	 * @see visitor.Visitor#visit(astv3.FloatTypeNode)
	 */
	@Override
	public void visit(FloatTypeNode floatTypeNode) {
		declType = FLOAT;
		floatTypeNode.getChild(0).accept(this);
	}
	
	@Override
	public void visit(CharTypeNode charTypeNode) {
		declType = CHAR;
		charTypeNode.getChild(0).accept(this);
	}
	
	@Override
	public void visit(ArrayDeclNode arrayTypeNode) {
		
	}

	/* (non-Javadoc)
	 * @see visitor.Visitor#visit(astv3.AssignNode)
	 */
	@Override
	public void visit(AssignNode assignNode) {
		assignNode.getLHS().accept(this);
		int lhsType = exprType;
		assignNode.getRHS().accept(this);
		int rhsType = exprType;
		
		//System.out.printf("%d, %d", lhsType, rhsType);
		if (lhsType != rhsType) {
			if (!(lhsType == FLOAT && rhsType == INTEGER ||
					lhsType == INTEGER && rhsType == FLOAT)) {
				if (rhsType != ANYTYPE) {
					System.err.println("Assignment type conflict:");
					System.err.printf("\tVariable \'%s\' expects type: %s but received: %s\n", assignNode.getLHS().getLabel(),
							typeCh(lhsType), typeCh(rhsType));
				}
			}
		}
	}
	
	@Override
	public void visit(IfStmtNode ifStmtNode) {
		
		
	}
	
	@Override
	public void visit(WhileNode whileNode) {
		
		
	}
	
	@Override
	public void visit(ProcCallNode procCallNode) {
		
		
	}
	
	@Override
	public void visit(WriteNode writeNode) {
		
		
	}
	
	@Override
	public void visit(ReadNode readNode) {
		
		
	}
	
	@Override
	public void visit(ReturnNode returnNode) {
		returnNode.getChild(0).accept(this);
	}
	
	@Override
	public void visit(CaseStmtNode caseStmtNode) {
		
		
	}
	
	@Override
	public void visit(CaseListNode caseListNode) {
		
		
	}
	
	@Override
	public void visit(CaseNode caseNode) {
		
		
	}
	
	@Override
	public void visit(CaseLabelsNode caseLabelsNode) {
		
		
	}
	
	@Override
	public void visit(StmtListNode stmtListNode) {
		for (ASTNode n : stmtListNode.getChildren())
			n.accept(this);
	}
	
	@Override
	public void visit(ExprListNode exprListNode) {
		
		
	}
	
	@Override
	public void visit(SubProgDeclNode subProgDeclNode) {
		newFrame();
		for (ASTNode n : subProgDeclNode.getChildren())
			n.accept(this);
		remFrame();
	}
	
	@Override
	public void visit(ProcNode procNode) {
		if (procNode.getChild(0) != null) 
			procNode.getChild(0).accept(this);
			
		if (procNode.getChild(1) != null)
			procNode.getChild(1).accept(this);
	
		procNode.getChild(2).accept(this);
	}
	
	@Override
	public void visit(FuncNode funcNode) {
		
		
	}
	
	@Override
	public void visit(ParamNode paramNode) {
		
		
	}
	
	@Override
	public void visit(CallNode callNode) {
		
		
	}

	@Override
	public void visit(IdDeclNode idDeclNode) {
		id = idDeclNode.getLabel();
		if (findInScope(id))
			System.err.println("Variable already declared in current scope: " + id);
		else
			addSymbol(id, declType);
	}

	@Override
	public void visit(IdDefNode n) {
		int sym;
		if ((sym = findSymbol(n.getLabel())) >= 0)
			exprType = sym;
		else {
			System.err.println("Undeclared variable: "+n.getLabel());
			exprType = ANYTYPE;
		}
	}
	
	@Override
	public void visit(ArrayDefNode arrayDefNode) {
		
		
	}
	
	private String typeCh(int n) {
		
		switch(n) {
			case 0:
				return "INTEGER";
			case 1:
				return "FLOAT";
			case 2:
				return "CHAR";
			case 3:
				return "ANYTYPE";
			default:
				return null;
		}
	}
	
	private void newFrame() {
		symTableStack.push(new HashMap<String, Integer>());
	}
	
	private void remFrame() {
		symTableStack.pop();
	}
	
	private void addSymbol(String sym, int type) {
		symTableStack.peek().put(sym, type);
	}
	
	private int findSymbol(String sym) {
		if (symTableStack.peek().containsKey(sym))
			return symTableStack.peek().get(sym);
		else if (symTableStack.get(0).containsKey(sym))
			return symTableStack.get(0).get(sym);
		else 
			return -1;
	}
	
	private boolean findInScope(String sym) {
		if (symTableStack.peek().containsKey(sym))
			return true;
		return false;
	}
	
}
