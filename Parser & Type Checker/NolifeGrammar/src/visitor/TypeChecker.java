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
	private final int NOTYPE = 4;
	private int declType;
	private int exprType;
	private int funcType;
	private String id;

	
	public TypeChecker() {
	}
	
	private void arithTypeCheck(BinaryNode n) {
		n.getLeft().accept(this);
		int leftType = exprType;
		n.getRight().accept(this);
		int rightType = exprType;
		
		if (leftType == CHAR || rightType == CHAR) {
			System.err.printf("Type error\n\tCannot do arithmetic with CHARACTERS\n");
			exprType = ANYTYPE;
		}
		else if (leftType == rightType)
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
			exprType = INTEGER;
		else if (leftType == ANYTYPE && rightType != ANYTYPE)	
			exprType = INTEGER;
		else if (rightType == ANYTYPE && leftType != ANYTYPE)
			exprType = INTEGER;
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
		
		if (exprType != INTEGER && exprType != ANYTYPE) {
			System.err.println("Cannot do MOD operations on non-integer types");
		}
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
		int arrSym;
		
		if ((arrSym = TypeUtils.findSymbolType(arrayRefNode.getLabel())) >= 0) {
			arrayRefNode.getChild(0).accept(this);
			TypeUtils.checkMyArray(arrayRefNode, exprType);
			exprType = arrSym;
			TypeUtils.referencedSym(arrayRefNode.getLabel());
		}
		else {
			System.err.println("Undeclared array: "+arrayRefNode.getLabel());
			exprType = ANYTYPE;
		}
	}
	
	@Override
	public void visit(StringNode stringNode) {
		//Nothing to do ~
	}

	@Override
	public void visit(ParenNode parenNode) {
		parenNode.getChild(0).accept(this);
	}
	

	@Override
	public void visit(NotNode notNode) {
		notNode.getChild(0).accept(this);
		
		if (exprType != 0) {
			System.err.printf("NOT Type error. NOT is only valid for INTEGERS but received %s instead\n", TypeUtils.typeCh(exprType));
			exprType = ANYTYPE;
		}
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
			TypeUtils.addArraySymbol(id, declType, arrayTypeNode.getChild(0).getLabel(), arrayTypeNode.getChild(1).getLabel(), exprType);
		}
	}

	@Override
	public void visit(AssignNode assignNode) {
		assignNode.getLHS().accept(this);
		int lhsType = exprType;
		assignNode.getRHS().accept(this);
		int rhsType = exprType;
		
		if (lhsType != rhsType) {
			if (!(lhsType == FLOAT && rhsType == INTEGER ||
					lhsType == INTEGER && rhsType == FLOAT)) {
				if (!(rhsType == ANYTYPE || lhsType == ANYTYPE)) {
					if (rhsType != NOTYPE) {
						System.err.println("Assignment type conflict");
						System.err.printf("\tVariable \'%s\' expects type: %s but received: %s\n", assignNode.getLHS().getLabel(),
								TypeUtils.typeCh(lhsType), TypeUtils.typeCh(rhsType));
					}
					else {
						System.err.printf("Assignment conflict with RHS Expression\n\tProcedure '%s' does not return a value to be assigned to variable '%s'\n",
								assignNode.getRHS().getLabel(), assignNode.getLHS().getLabel());
					}
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
		whileNode.getChild(0).accept(this);
		whileNode.getChild(1).accept(this);
	}
	
	@Override
	public void visit(ProcCallNode procCallNode) {
		TypeUtils.funcSymbol funcSymbol = (TypeUtils.funcSymbol) TypeUtils.symTableStack.get(0).get(procCallNode.getLabel());
	
		//Check if procedure was declared and if its a procedure, not a function
			if (funcSymbol == null || funcSymbol.returns) {
				System.err.printf("Procedure '%s' was never declared\n", procCallNode.getLabel());
				return;
			}
			
			//Check if there are no parameters
			if (procCallNode.getChild(0) == null) {
				if (0 != funcSymbol.parameters.values().size()) {
					System.err.printf("Incorrect amount of parameters\n\t On call '%s', we are given %d parameter(s), when we are expecting %d\n",
							funcSymbol.name, 0, funcSymbol.parameters.values().size());
				}
				
				exprType = funcSymbol.type;
				TypeUtils.referencedSym(procCallNode.getLabel());
				return;
			}
		
		//procCallNode.getChild(0).accept(this);
		
		//First check there is an correct amount of parameters?
		if (procCallNode.getChild(0).getChildren().size() != funcSymbol.parameters.values().size()) {
			System.err.printf("Incorrect amount of parameters\n\t On call '%s', we are given %d parameter(s), when we are expecting %d\n",
					funcSymbol.name, procCallNode.getChild(0).getChildren().size(), funcSymbol.parameters.values().size());
		}
		//Then check for type of parameters
		else {
			int i = 0;
			for (TypeUtils.Symbol sym : funcSymbol.parameters.values()) {
				procCallNode.getChild(0).getChild(i++).accept(this);
				
				if (exprType != sym.type) {
					//if (!(exprType == FLOAT && sym.type == INTEGER ||
							//exprType == INTEGER && sym.type == FLOAT)) {
						if (!(sym.type == ANYTYPE || exprType == ANYTYPE)) {
							System.err.printf("Procedure parameter and Call type mismatch\n\t On procedure call '%s', "
									+ "we are given type %s, when we are expecting %s for '%s' variable\n",
									funcSymbol.name, TypeUtils.typeCh(exprType), TypeUtils.typeCh(sym.type), sym.name);
						}
					//}
				}
			}
		}
		
		TypeUtils.referencedSym(procCallNode.getLabel());
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
		for (ASTNode n : exprListNode.getChildren())
			n.accept(this);
	}
	
	@Override
	public void visit(SubProgDeclNode subProgDeclNode) {
		for (ASTNode n : subProgDeclNode.getChildren()) {
			switch(n.getClass().getSimpleName()) {
				case "ProcNode" :
					TypeUtils.addFuncSymbol(n.getLabel(), 4, false);
					if (n.getChild(0) != null) 
						TypeUtils.addParamSymbolsToFunc(n);
					break;
				case "IntTypeNode" :
					TypeUtils.addFuncSymbol(n.getChild(0).getLabel(), 0, true);
					if (n.getChild(0).getChild(0) != null) 
						TypeUtils.addParamSymbolsToFunc(n.getChild(0));
					break;
				case "FloatTypeNode" :
					TypeUtils.addFuncSymbol(n.getChild(0).getLabel(), 1, true);
					if (n.getChild(0).getChild(0) != null) 
						TypeUtils.addParamSymbolsToFunc(n.getChild(0));
					break;
				case "CharTypeNode" :
					TypeUtils.addFuncSymbol(n.getChild(0).getLabel(), 2, true);
					if (n.getChild(0).getChild(0) != null) 
						TypeUtils.addParamSymbolsToFunc(n.getChild(0));
					break;
			}
		}
			
		for (ASTNode n : subProgDeclNode.getChildren()) {
			TypeUtils.newFrame();
			n.accept(this);
			TypeUtils.checkRefSym();
			TypeUtils.remFrame();
		}
	}
	
	@Override
	public void visit(ProcNode procNode) {
		//Parameters are done before this
		if (procNode.getChild(0) != null) 
			procNode.getChild(0).accept(this);
		
		//Variables
		if (procNode.getChild(1) != null)
			procNode.getChild(1).accept(this);
	
		//Compstmt
		procNode.getChild(2).accept(this);
	}
	
	@Override
	public void visit(FuncNode funcNode) {
		//Parameters are done before this
		if (funcNode.getChild(0) != null) 
			funcNode.getChild(0).accept(this);	
		
		//Variables
		if (funcNode.getChild(1) != null)
			funcNode.getChild(1).accept(this);
	
		//Compstmt
		funcNode.getChild(2).accept(this);
		
	}
	
	@Override
	public void visit(ParamNode paramNode) {
		for (ASTNode n : paramNode.getChildren()) 
			n.accept(this);
	}
	
	@Override
	public void visit(CallNode callNode) {
		TypeUtils.funcSymbol funcSymbol = (TypeUtils.funcSymbol) TypeUtils.symTableStack.get(0).get(callNode.getLabel());
		
		//Check if function was declared and if its a function, not a procedure
		if (funcSymbol == null || !(funcSymbol.returns)) {
			System.err.printf("Function '%s' was never declared\n", callNode.getLabel());
			exprType = ANYTYPE;
			return;
		}
		
		//Check if there are no parameters
		if (callNode.getChild(0) == null) {
			if (0 != funcSymbol.parameters.values().size()) {
				System.err.printf("Incorrect amount of parameters\n\t On call '%s', we are given %d parameter(s), when we are expecting %d\n",
						funcSymbol.name, 0, funcSymbol.parameters.values().size());
			}
			
			exprType = funcSymbol.type;
			TypeUtils.referencedSym(callNode.getLabel());
			return;
		}
	
		//callNode.getChild(0).accept(this);
		
		//First check there is an correct amount of parameters?
		if (callNode.getChild(0).getChildren().size() != funcSymbol.parameters.values().size()) {
			System.err.printf("Incorrect amount of parameters\n\t On call '%s', we are given %d parameter(s), when we are expecting %d\n",
					funcSymbol.name, callNode.getChild(0).getChildren().size(), funcSymbol.parameters.values().size());
		}
		//Then check for type of parameters
		else {
			int i = 0;
			for (TypeUtils.Symbol sym : funcSymbol.parameters.values()) {
				callNode.getChild(0).getChild(i++).accept(this);
				
				if (exprType != sym.type) {
					if (!(exprType == FLOAT && sym.type == INTEGER ||
							exprType == INTEGER && sym.type == FLOAT)) {
						if (!(sym.type == ANYTYPE || exprType == ANYTYPE)) {
							System.err.printf("Function parameter and Call type mismatch\n\t On function call '%s', "
									+ "we are given type %s, when we are expecting %s for '%s' variable\n",
									funcSymbol.name, TypeUtils.typeCh(exprType), TypeUtils.typeCh(sym.type), sym.name);
						}
					}
				}
			}
		}
		
		exprType = funcSymbol.type;
		TypeUtils.referencedSym(callNode.getLabel());
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
		
		int arrSym;
		
		if ((arrSym = TypeUtils.findSymbolType(arrayDefNode.getLabel())) >= 0) {
			arrayDefNode.getChild(0).accept(this);
			TypeUtils.checkMyArray(arrayDefNode, exprType);
			exprType = arrSym;
		}
		else {
			System.err.println("Undeclared variable: "+arrayDefNode.getLabel());
			exprType = ANYTYPE;
		}
		
	}
	
}
