package visitor;

import ast.*;

public class ASTBuildVisitor implements Visitor {

	private String src;
	private String prefix;

	public ASTBuildVisitor() {
		super();
		src = "";
		prefix = "";
	}
	
	@Override
	public void visit(SymNode n) {
		src += "\n" + prefix + "SYM " + n.getLabel();
	}
	
	@Override
	public void visit(IntNode n) {
		src += "\n" + prefix + "CONST INT " + n.getLabel();
	}
	
	@Override
	public void visit(FloatNode n) {
		src += "\n" + prefix + "CONST FLOAT " + n.getLabel();
	}
	
	@Override
	public void visit(CharNode n) {
		src += "\n" + prefix + "CONST CHAR " + n.getLabel();
	}
	
	@Override
	public void visit(AddNode n) {
		src += "\n" + prefix + "ADD";
		addPrefix();
		n.getLeft().accept(this);
		n.getRight().accept(this);
		delPrefix();
	}

	@Override
	public void visit(SubNode n) {
		src += "\n" + prefix + "SUB";
		addPrefix();
		n.getLeft().accept(this);
		n.getRight().accept(this);
		delPrefix();
	}

	@Override
	public void visit(ModNode n) {
		src += "\n" + prefix + "MOD";
		addPrefix();
		n.getLeft().accept(this);
		n.getRight().accept(this);
		delPrefix();
	}

	@Override
	public void visit(MulNode n) {
		src += "\n" + prefix + "MUL";
		addPrefix();
		n.getLeft().accept(this);
		n.getRight().accept(this);
		delPrefix();
	}
	
	@Override
	public void visit(LessThanNode n) {
		src += "\n" + prefix + "LESSTHAN";
		addPrefix();
		n.getLeft().accept(this);
		n.getRight().accept(this);
		delPrefix();
	}

	@Override
	public void visit(LessEqualNode n) {
		src += "\n" + prefix + "LESSEQUAL";
		addPrefix();
		n.getLeft().accept(this);
		n.getRight().accept(this);
		delPrefix();
	}
	
	@Override
	public void visit(GreaterThanNode n) {
		src += "\n" + prefix + "GREATERTHAN";
		addPrefix();
		n.getLeft().accept(this);
		n.getRight().accept(this);
		delPrefix();
	}
	
	@Override
	public void visit(GreaterEqualNode n) {
		src += "\n" + prefix + "GREATEREQUAL";
		addPrefix();
		n.getLeft().accept(this);
		n.getRight().accept(this);
		delPrefix();
	}
	
	@Override
	public void visit(NotEqualNode n) {
		src += "\n" + prefix + "NOTEQUAL";
		addPrefix();
		n.getLeft().accept(this);
		n.getRight().accept(this);
		delPrefix();
	}
	
	@Override
	public void visit(EqualNode n) {
		src += "\n" + prefix + "EQUAL";
		addPrefix();
		n.getLeft().accept(this);
		n.getRight().accept(this);
		delPrefix();
	}
	
	@Override
	public void visit(AndNode n) {
		src += "\n" + prefix + "AND";
		addPrefix();
		n.getLeft().accept(this);
		n.getRight().accept(this);
		delPrefix();
	}
	
	@Override
	public void visit(OrNode n) {
		src += "\n" + prefix + "OR";
		addPrefix();
		n.getLeft().accept(this);
		n.getRight().accept(this);
		delPrefix();
	}
	
	@Override
	public void visit(IdRefNode n) {
		src += "\n" + prefix + "IDREF " + n.getLabel();
	}
	
	@Override
	public void visit(ArrayRefNode arrayRefNode) {
		src += "\n" + prefix + "ARRAYREF " + arrayRefNode.getLabel();
		addPrefix();
		arrayRefNode.getChild(0).accept(this);
		delPrefix();
	}
	
	@Override
	public void visit(StringNode stringNode) {
		src += "\n" + prefix + "STRING " + stringNode.getLabel();
	}
	
	@Override
	public void visit(ParenNode parenNode) {
		parenNode.getChild(0).accept(this);
	}
	
	@Override
	public void visit(NotNode notNode) {
		src += "\n" + prefix + "NOT";
		addPrefix();
		notNode.getChild(0).accept(this);
		delPrefix();
	}

	public String getSrc() {
		return src;
	}

	@Override
	public void visit(DeclNode declNode) {
		src += "\n" + prefix + "DECLARATION";
		addPrefix();
		declNode.getChild(0).accept(this);
		delPrefix();
		for (int i = 1; i < declNode.getChildren().size(); i++) {
			addPrefix();
			declNode.getChild(i).accept(this);
			delPrefix();
		}
		//declNode.getChild(declNode.getChildren().size()-1).accept(this);
	}

	@Override
	public void visit(ProgramNode programNode) {
		src += "PROG ";
		src +=  programNode.getLabel(); src +="\n";
		for (ASTNode node : programNode.getChildren()) {
			addPrefix();
			node.accept(this);
			delPrefix();
		}
	}

	@Override
	public void visit(VarDeclsNode varDeclsNode) {
		src += "\n" + prefix + "VarDecl\n";
		for (ASTNode node : varDeclsNode.getChildren()) {
			addPrefix();
			node.accept(this);
			delPrefix();
		}
	}

	@Override
	public void visit(IntTypeNode intTypeNode) {
		src += "\n" + prefix + "INT ";
		addPrefix();
		intTypeNode.getChild(0).accept(this);
		delPrefix();
	}

	@Override
	public void visit(FloatTypeNode floatTypeNode) {
		src += "\n" + prefix + "FLOAT ";
		addPrefix();
		floatTypeNode.getChild(0).accept(this);
		delPrefix();
	}
	
	@Override
	public void visit(CharTypeNode charTypeNode) {
		src += "\n" + prefix + "CHAR ";
		addPrefix();
		charTypeNode.getChild(0).accept(this);
		delPrefix();
	}

	@Override
	public void visit(AssignNode assignNode) {
		src += "\n" + prefix + "ASSIGN";
		addPrefix();
		assignNode.getChild(0).accept(this);
		assignNode.getChild(1).accept(this);
		delPrefix();
	}
	
	@Override
	public void visit(IfStmtNode ifStmtNode) {
		src += "\n" + prefix + "IFTHENELSE"; 
		addPrefix();
		ifStmtNode.getChild(0).accept(this); 
		delPrefix();
		//src += "\n" + prefix + "THEN"; 
		addPrefix();
		ifStmtNode.getChild(1).accept(this); 
		delPrefix();
		if (ifStmtNode.getChild(2) != null) {
			//src += "\n" + prefix + "ELSE";
			addPrefix();
			ifStmtNode.getChild(2).accept(this);
			delPrefix();
		}
	}
	
	@Override
	public void visit(WhileNode whileNode) {
		src += "\n" + prefix + "WHILEDO";
		addPrefix();
		whileNode.getChild(0).accept(this);
		whileNode.getChild(1).accept(this);
		delPrefix();
	}
	
	@Override
	public void visit(ProcCallNode procCallNode) {
		src += "\n" + prefix + "PROCCALL " + procCallNode.getLabel();
		if (procCallNode.getChild(0) != null) {
			addPrefix();
			procCallNode.getChild(0).accept(this);
			delPrefix();
		}
	}
	
	@Override
	public void visit(WriteNode writeNode) {
		src += "\n" + prefix + "WRITE ";
		addPrefix();
		writeNode.getChild(0).accept(this);
		delPrefix();
	}
	
	@Override
	public void visit(ReadNode readNode) {
		src += "\n" + prefix + "READ ";
		addPrefix();
		readNode.getChild(0).accept(this);
		delPrefix();
	}
	
	@Override
	public void visit(ReturnNode returnNode) {
		src += "\n" + prefix + "RETURN ";
		addPrefix();
		returnNode.getChild(0).accept(this);
		delPrefix();
	}
	
	@Override
	public void visit(CaseStmtNode caseStmtNode) {
		src += "\n" + prefix + "CASENAME";
		addPrefix();
		caseStmtNode.getChild(0).accept(this);
		delPrefix();
		src += "\n" + prefix + "OF";
		if (caseStmtNode.getChild(1) != null) {
			addPrefix();
			caseStmtNode.getChild(1).accept(this);
			delPrefix();
		}
	}
	
	@Override
	public void visit(CaseListNode caseListNode) {
		src += "\n" + prefix + "CASELIST";
		addPrefix();
		for (ASTNode childNode : caseListNode.getChildren())
			childNode.accept(this);
		
		delPrefix();
	}
	
	@Override
	public void visit(CaseNode caseNode) {
		src += "\n" + prefix + "CASE";
		addPrefix();
		caseNode.getChild(0).accept(this);
		caseNode.getChild(1).accept(this);
		delPrefix();
	}
	
	@Override
	public void visit(CaseLabelsNode caseLabelsNode) {
		src += "\n" + prefix + "CASELABELS";
		addPrefix();
		caseLabelsNode.getChild(0).accept(this);
		delPrefix();
		for (int i = 1; i < caseLabelsNode.getChildren().size(); i++) {
			addPrefix();
			caseLabelsNode.getChild(i).accept(this);
			delPrefix();	
		}
	}

	@Override
	public void visit(StmtListNode stmtListNode) {
		//Go through all expressions in the list of statements
		src += "\n" + prefix + "COMPSTMT";
		for (ASTNode childNode : stmtListNode.getChildren()) {
			addPrefix();
			childNode.accept(this);
			delPrefix();
		}
	}
	
	@Override
	public void visit(ExprListNode exprListNode) {
		//Go through all expressions in the list of statements
		src += "\n" + prefix + "EXPRLIST";
		addPrefix();
		exprListNode.getChild(0).accept(this);
		for (int i = 1; i < exprListNode.getChildren().size(); i++) 
			exprListNode.getChild(i).accept(this);
		
		delPrefix();
	}
	
	@Override
	public void visit(SubProgDeclNode subProgDeclNode) {
		src += "\n" + prefix + "SUBPROGDECL";
		addPrefix();
		for (ASTNode childNode : subProgDeclNode.getChildren())
			childNode.accept(this);
			
		delPrefix();
	}
	
	@Override
	public void visit(ProcNode procNode) {
		src += "\n" + prefix + "PROCEDURE " + procNode.getLabel();
		addPrefix();
		if (procNode.getChild(0) != null) 
			procNode.getChild(0).accept(this);
		
		if (procNode.getChild(1) != null)
			procNode.getChild(1).accept(this);
	
		procNode.getChild(2).accept(this);
		delPrefix();
	}

	@Override
	public void visit(FuncNode funcNode) {
		src += "\n" + prefix + "FUNCTION " + funcNode.getLabel();
		addPrefix();
		if (funcNode.getChild(0) != null)
			funcNode.getChild(0).accept(this);
		
		if (funcNode.getChild(1) != null)
			funcNode.getChild(1).accept(this);

		funcNode.getChild(2).accept(this);
		delPrefix();
	}
	
	@Override
	public void visit(ParamNode paramNode) {
		//Go through all expressions in the list of statements
		src += "\n" + prefix + "PARAMETERS";
		addPrefix();
		paramNode.getChild(0).accept(this);
		delPrefix();
		for (int i = 1; i < paramNode.getChildren().size(); i++) {
			addPrefix();
			paramNode.getChild(i).accept(this);
			delPrefix();
		}
	}
	
	@Override
	public void visit(CallNode callNode) {
		//Go through all expressions in the list of statements
		src += "\n" + prefix + "CALL " + callNode.getLabel();
		if (callNode.getChild(0) != null) {
			addPrefix();
			callNode.getChild(0).accept(this);
			delPrefix();
		}
	}

	@Override
	public void visit(IdDeclNode idDeclNode) {
		
		src += "\n" + prefix + "IDDECL " + idDeclNode.getLabel();
	}
	
	@Override
	public void visit(ArrayDeclNode arrayDeclNode) {
		src += "\n" + prefix + "ARRAY " + arrayDeclNode.getLabel();
		addPrefix();
		arrayDeclNode.getChild(0).accept(this);
		arrayDeclNode.getChild(1).accept(this);
		delPrefix();
	}

	@Override
	public void visit(IdDefNode idDefNode) {
		src += "\n" + prefix + "IDDEF " + idDefNode.getLabel();
	}
	
	@Override
	public void visit(ArrayDefNode arrayDefNode) {
		src += "\n" + prefix + "ARRAYDEF " + arrayDefNode.getLabel();
		addPrefix();
		arrayDefNode.getChild(0).accept(this);
		delPrefix();
	}
	
	private void addPrefix() {
		prefix = prefix + "\t";
	}
	
	private void delPrefix() {
		prefix = prefix.substring(0, prefix.length()-1);
	}
	
}
