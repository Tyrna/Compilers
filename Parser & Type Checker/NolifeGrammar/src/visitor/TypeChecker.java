/**
 * 
 */
package visitor;

import ast.*;
import visitor.TypeUtils;

public class TypeChecker implements Visitor {

	//RealType = Children
	//ConvertedType = Parent
	private final int INTEGER = 0;
	private final int FLOAT = 1;
	private final int CHAR = 2;
	private final int ANYTYPE = 3;
	private int declType;
	private int exprType;
	private String id;
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
		else if (leftType == ANYTYPE && rightType != ANYTYPE)
			exprType = rightType;
		else if (rightType == ANYTYPE && leftType != ANYTYPE)
			exprType = leftType;
		else if (leftType == ANYTYPE || rightType == ANYTYPE)
			exprType = ANYTYPE;
		else if (leftType == FLOAT && rightType == INTEGER ||
					leftType == INTEGER && rightType == FLOAT)
			exprType = FLOAT;
		else {
			System.err.printf("Type error\n\tCannot do arithmetic on type %s to type %s\n",
					TypeUtils.typeCh(leftType), TypeUtils.typeCh(rightType));
			exprType = ANYTYPE;
		}		
	}
	
	private void compTypeCheck(BinaryNode n) {
		n.getChild(0).accept(this);
		int leftType = exprType;
		n.getChild(1).accept(this);
		int rightType = exprType;
		
		if (leftType == rightType)
			exprType = leftType;
		else if (leftType == ANYTYPE && rightType != ANYTYPE)	
			exprType = rightType;
		else if (rightType == ANYTYPE && leftType != ANYTYPE)
			exprType = leftType;
		else if (leftType == ANYTYPE || rightType == ANYTYPE)
			exprType = ANYTYPE;
		else if (leftType == FLOAT && rightType == INTEGER ||
					leftType == INTEGER && rightType == FLOAT)
			exprType = INTEGER;
		else {
			System.err.printf("Type error\n\tCannot compare type %s to type %s\n",
					TypeUtils.typeCh(leftType), TypeUtils.typeCh(rightType));
			exprType = ANYTYPE;
		}		
	}
	
	private void logicTypeCheck(BinaryNode n) {
		n.getLeft().accept(this);
		int leftType = exprType;
		n.getRight().accept(this);
		int rightType = exprType;
		
		if (leftType == rightType)
			exprType = leftType;
		else if (leftType == ANYTYPE && rightType != ANYTYPE)
			exprType = rightType;
		else if (rightType == ANYTYPE && leftType != ANYTYPE)
			exprType = leftType;
		else if (leftType == ANYTYPE || rightType == ANYTYPE)
			exprType = ANYTYPE;
		else if (leftType == FLOAT && rightType == INTEGER)
			exprType = FLOAT;
		else if (leftType == INTEGER && rightType == FLOAT)
			exprType = INTEGER;
		else {
			System.err.printf("Type error\n\tCannot do logical operation on type %s to type %s\n",
					TypeUtils.typeCh(leftType), TypeUtils.typeCh(rightType));
			exprType = ANYTYPE;
		}		
	}
	
	@Override
	public void visit(SymNode n) {
		
	}
	
	@Override
	public void visit(IntNode n) {
		exprType = INTEGER;
	}
	
	@Override
	public void visit(FloatNode n) {
		exprType = FLOAT;
	}
	
	@Override
	public void visit(CharNode n) {
		exprType = CHAR;
	}

	@Override
	public void visit(AddNode n) {
		arithTypeCheck(n);
	}

	@Override
	public void visit(SubNode n) {
		arithTypeCheck(n);
	}

	@Override
	public void visit(ModNode n) {
		arithTypeCheck(n);
	}

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
		logicTypeCheck(n);
	}
	
	@Override
	public void visit(OrNode n) {
		logicTypeCheck(n);
	}

	@Override
	public void visit(IdRefNode n) {
		int sym;
		
		if ((sym = TypeUtils.findSymbolType(n.getLabel())) >= 0) {
			exprType = sym;
			TypeUtils.referencedSym(n.getLabel());
		}
		else {
			System.err.println("Undeclared variable: "+n.getLabel());
			exprType = ANYTYPE;
		}
		
	}
	
	@Override
	public void visit(ArrayRefNode arrayRefNode) {
		int sym;
		if ((sym = TypeUtils.findSymbolType(arrayRefNode.getLabel())) >= 0) {
			exprType = sym;
			TypeUtils.referencedSym(arrayRefNode.getLabel());
		}
		else {
			System.err.println("Undeclared variable: "+arrayRefNode.getLabel());
			exprType = ANYTYPE;
		}
	}
	
	@Override
	public void visit(StringNode stringNode) {

	}

	@Override
	public void visit(ParenNode parenNode) {
		parenNode.getChild(0).accept(this);
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
		TypeUtils.newFrame();
		for (ASTNode n : programNode.getChildren())
			n.accept(this);
		TypeUtils.checkRefSym();
		TypeUtils.remFrame();
	}

	@Override
	public void visit(VarDeclsNode varDeclsNode) {
		for (ASTNode n : varDeclsNode.getChildren())
			n.accept(this);
	}

	@Override
	public void visit(IntTypeNode intTypeNode) {
		declType = INTEGER;
		intTypeNode.getChild(0).accept(this);
	}

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
		id = arrayTypeNode.getLabel();
		if (TypeUtils.findInScope(id))
			System.err.println("Variable already declared in current scope: " + id);
		else {
			arrayTypeNode.getChild(0).accept(this);
			//TypeUtils.addArraySymbol(id, declType, arrayTypeNode.getChild(0).getLabel(), arrayTypeNode.getChild(1).getLabel(), exprType);
		}
		
	}

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
				if (!(rhsType == ANYTYPE || lhsType == ANYTYPE)) {
					System.err.println("Assignment type conflict:");
					System.err.printf("\tVariable \'%s\' expects type: %s but received: %s\n", assignNode.getLHS().getLabel(),
							TypeUtils.typeCh(lhsType), TypeUtils.typeCh(rhsType));
				}
			}
		}
	}
	
	@Override
	public void visit(IfStmtNode ifStmtNode) {
		ifStmtNode.getChild(0).accept(this); 
		ifStmtNode.getChild(1).accept(this); 
		if (ifStmtNode.getChild(2) != null) 
			ifStmtNode.getChild(2).accept(this);
	}
	
	@Override
	public void visit(WhileNode whileNode) {
		
		
	}
	
	@Override
	public void visit(ProcCallNode procCallNode) {
		
		
	}
	
	@Override
	public void visit(WriteNode writeNode) {
		writeNode.getChild(0).accept(this);
	}
	
	@Override
	public void visit(ReadNode readNode) {
		readNode.getChild(0).accept(this);
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
		TypeUtils.newFrame();
		for (ASTNode n : subProgDeclNode.getChildren())
			n.accept(this);
		
		TypeUtils.checkRefSym();
		TypeUtils.remFrame();
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
		if (TypeUtils.findInScope(id))
			System.err.println("Variable already declared in current scope: " + id);
		else
			TypeUtils.addSymbol(id, declType);
	}

	@Override
	public void visit(IdDefNode n) {
		int sym;
		if ((sym = TypeUtils.findSymbolType(n.getLabel())) >= 0)
			exprType = sym;
		else {
			System.err.println("Undeclared variable: "+n.getLabel());
			exprType = ANYTYPE;
		}
	}
	
	@Override
	public void visit(ArrayDefNode arrayDefNode) {
		int sym;
		if ((sym = TypeUtils.findSymbolType(arrayDefNode.getLabel())) >= 0) {
			exprType = sym;
			arrayDefNode.getChild(0).accept(this);
			
			if (sym != exprType) {
				System.err.printf("Array Index Type mismatch\n\t Array %s cannot index by %s, as it expects %s\n",
						arrayDefNode.getLabel(), TypeUtils.typeCh(exprType), TypeUtils.typeCh(sym));
			}
			
			int[] range = TypeUtils.getArrayRange(arrayDefNode.getLabel());
			int index = Integer.parseInt(arrayDefNode.getChild(0).getLabel());
			if (range[0] > index || index > range[1]) {
				System.err.printf("Array out of bounds\n\t Cannot access index %d on array %s where bounds are [%d ... %d]\n",
						index, arrayDefNode.getLabel(), range[0], range[1]);
			}
		}
		else {
			System.err.println("Undeclared variable: "+arrayDefNode.getLabel());
			exprType = ANYTYPE;
		}
		
	}
	
}
