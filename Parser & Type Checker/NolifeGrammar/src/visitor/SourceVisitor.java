package visitor;

import ast.*;

public class SourceVisitor implements Visitor {

	private String src;

	public SourceVisitor() {
		super();
		src = "";
	}
	
	@Override
	public void visit(SymNode n) {
		src += n.getLabel();
	}
	
	@Override
	public void visit(IntNode n) {
		src += n.getLabel();
	}
	
	@Override
	public void visit(FloatNode n) {
		src += n.getLabel();
	}
	
	@Override
	public void visit(CharNode n) {
		src += n.getLabel();
	}
	
	@Override
	public void visit(AddNode n) {
		n.getLeft().accept(this);
		src += " + ";
		n.getRight().accept(this);
	}

	@Override
	public void visit(SubNode n) {
		n.getLeft().accept(this);
		src += " - ";
		n.getRight().accept(this);
	}

	@Override
	public void visit(ModNode n) {
		n.getLeft().accept(this);
		src += " MOD ";
		n.getRight().accept(this);
	}

	@Override
	public void visit(MulNode n) {
		n.getLeft().accept(this);
		src += " * ";
		n.getRight().accept(this);
	}
	
	@Override
	public void visit(LessThanNode n) {
		n.getLeft().accept(this);
		src += " < ";
		n.getRight().accept(this);
	}

	@Override
	public void visit(LessEqualNode n) {
		n.getLeft().accept(this);
		src += " <= ";
		n.getRight().accept(this);
	}
	
	@Override
	public void visit(GreaterThanNode n) {
		n.getLeft().accept(this);
		src += " > ";
		n.getRight().accept(this);
	}
	
	@Override
	public void visit(GreaterEqualNode n) {
		n.getLeft().accept(this);
		src += " >= ";
		n.getRight().accept(this);
	}
	
	@Override
	public void visit(NotEqualNode n) {
		n.getLeft().accept(this);
		src += " <> ";
		n.getRight().accept(this);
	}
	
	@Override
	public void visit(EqualNode n) {
		n.getLeft().accept(this);
		src += " = ";
		n.getRight().accept(this);
	}
	
	@Override
	public void visit(AndNode n) {
		n.getLeft().accept(this);
		src += " AND ";
		n.getRight().accept(this);
	}
	
	@Override
	public void visit(OrNode n) {
		n.getLeft().accept(this);
		src += " OR ";
		n.getRight().accept(this);
	}
	
	@Override
	public void visit(IdRefNode n) {
		src += n.getLabel();
	}
	
	@Override
	public void visit(ArrayRefNode arrayRefNode) {
		src += arrayRefNode.getLabel();
		src += "[";
		arrayRefNode.getChild(0).accept(this);
		src += "]";
	}
	
	@Override
	public void visit(StringNode stringNode) {
		src += stringNode.getLabel();
	}
	
	@Override
	public void visit(ParenNode parenNode) {
		src += "(";
		parenNode.getChild(0).accept(this);
		src += ")";
	}
	
	@Override
	public void visit(NotNode notNode) {
		src += "NOT ";
		notNode.getChild(0).accept(this);
	}

	public String getSrc() {
		return src;
	}

	@Override
	public void visit(DeclNode declNode) {
		src += "VAR\t";
		declNode.getChild(0).accept(this);
		for (int i = 1; i < declNode.getChildren().size(); i++) {
			src += ",\n\t";
			declNode.getChild(i).accept(this);
		}
		//declNode.getChild(declNode.getChildren().size()-1).accept(this);
		src += ";\n\n";
	}

	@Override
	public void visit(ProgramNode programNode) {
		src += "PROGRAM ";
		src +=  programNode.getLabel(); src +="\n\n";
		for (ASTNode node : programNode.getChildren())
			node.accept(this);
	}

	@Override
	public void visit(VarDeclsNode varDeclsNode) {
		for (ASTNode node : varDeclsNode.getChildren()) {
			node.accept(this);
			src += "\n";
		}
	}

	@Override
	public void visit(IntTypeNode intTypeNode) {
		src += "INT ";
		intTypeNode.getChild(0).accept(this);
	}

	@Override
	public void visit(FloatTypeNode floatTypeNode) {
		src += "FLOAT ";
		floatTypeNode.getChild(0).accept(this);
	}
	
	@Override
	public void visit(CharTypeNode charTypeNode) {
		src += "CHAR ";
		charTypeNode.getChild(0).accept(this);
	}

	@Override
	public void visit(AssignNode assignNode) {
		assignNode.getChild(0).accept(this);
		src += " := ";
		assignNode.getChild(1).accept(this);
		src += "\n";
	}
	
	@Override
	public void visit(IfStmtNode ifStmtNode) {
		src += "IF ";
		ifStmtNode.getChild(0).accept(this);
		src += " THEN {\n";
		ifStmtNode.getChild(1).accept(this);
		src += "}\n";
		if (ifStmtNode.getChild(2) != null) {
			src += "ELSE\n";
			ifStmtNode.getChild(2).accept(this);
		}
	}
	
	@Override
	public void visit(WhileNode whileNode) {
		src += "WHILE ";
		whileNode.getChild(0).accept(this);
		src += " DO\n";
		whileNode.getChild(1).accept(this);
		src += "\n";
	}
	
	@Override
	public void visit(ProcCallNode procCallNode) {
		src += procCallNode.getLabel();
		src += "(";
		if (procCallNode.getChild(0) != null)
			procCallNode.getChild(0).accept(this);
		src += ")\n";
	}
	
	@Override
	public void visit(WriteNode writeNode) {
		src += "WRITE(";
		writeNode.getChild(0).accept(this);
		src += ")\n";
	}
	
	@Override
	public void visit(ReadNode readNode) {
		src += "READ(";
		readNode.getChild(0).accept(this);
		src += ")\n";
	}
	
	@Override
	public void visit(ReturnNode returnNode) {
		src += "RETURN ";
		returnNode.getChild(0).accept(this);
		src += "\n";
	}
	
	@Override
	public void visit(CaseStmtNode caseStmtNode) {
		src += "CASE ";
		caseStmtNode.getChild(0).accept(this);
		src += " OF\n";
		if (caseStmtNode.getChild(1) != null)
			caseStmtNode.getChild(1).accept(this);
		src += "\nEND\n";
	}
	
	@Override
	public void visit(CaseListNode caseListNode) {
		for (ASTNode childNode : caseListNode.getChildren())
			childNode.accept(this);
	}
	
	@Override
	public void visit(CaseNode caseNode) {
		caseNode.getChild(0).accept(this);
		src += "\t";
		caseNode.getChild(1).accept(this);
	}
	
	@Override
	public void visit(CaseLabelsNode caseLabelsNode) {
		caseLabelsNode.getChild(0).accept(this);
		for (int i = 1; i < caseLabelsNode.getChildren().size(); i++) {
			src += ",";
			caseLabelsNode.getChild(i).accept(this);
		}
		src += ":\n";
	}

	@Override
	public void visit(StmtListNode stmtListNode) {
		//Go through all expressions in the list of statements
		src += "_BEGIN_\n";
		for (ASTNode childNode : stmtListNode.getChildren()) {
			//src +="\t";
			childNode.accept(this);
		}
		src += "_END_\n";
	}
	
	@Override
	public void visit(ExprListNode exprListNode) {
		//Go through all expressions in the list of statements
		exprListNode.getChild(0).accept(this);
		for (int i = 1; i < exprListNode.getChildren().size(); i++) {
			src += ",";
			exprListNode.getChild(i).accept(this);
		}
	}
	
	@Override
	public void visit(SubProgDeclNode subProgDeclNode) {
		//Go through all expressions in the list of statements
		for (ASTNode childNode : subProgDeclNode.getChildren()) {
			childNode.accept(this);
		}
	}
	
	@Override
	public void visit(ProcNode procNode) {
		src += "PROCEDURE ";
		src += procNode.getLabel();
		src += "("; if (procNode.getChild(0) != null) procNode.getChild(0).accept(this); src += "){\n\t"; 
		
		if (procNode.getChild(1) != null) procNode.getChild(1).accept(this); else src += "\n";
		procNode.getChild(2).accept(this);
		src += "}\n\n";
	}

	@Override
	public void visit(FuncNode funcNode) {
		src += funcNode.getLabel();
		src += "("; if (funcNode.getChild(0) != null) funcNode.getChild(0).accept(this); src += "){\n"; 
		
		if (funcNode.getChild(1) != null) funcNode.getChild(1).accept(this); else src += "\n";
		funcNode.getChild(2).accept(this);
		src += "}\n\n";
	}
	
	@Override
	public void visit(ParamNode paramNode) {
		//Go through all expressions in the list of statements
		paramNode.getChild(0).accept(this);
		for (int i = 1; i < paramNode.getChildren().size(); i++) {
			src += ", ";
			paramNode.getChild(i).accept(this);
		}
	}
	
	@Override
	public void visit(CallNode callNode) {
		//Go through all expressions in the list of statements
		src += callNode.getLabel();
		src += "(";
		if (callNode.getChild(0) != null)
			callNode.getChild(0).accept(this);
		src += ")";
	}

	@Override
	public void visit(IdDeclNode idDeclNode) {
		src += idDeclNode.getLabel();
	}
	
	@Override
	public void visit(ArrayDeclNode arrayDeclNode) {
		src += "ARRAY ";
		arrayDeclNode.getChild(0).accept(this);
		src += "["; arrayDeclNode.getChild(1).accept(this);
		src += ".."; arrayDeclNode.getChild(2).accept(this);
		src += "]";
	}

	@Override
	public void visit(IdDefNode idDefNode) {
		src += idDefNode.getLabel();
	}
	
	@Override
	public void visit(ArrayDefNode arrayDefNode) {
		src += arrayDefNode.getLabel();
		src += "["; 
		arrayDefNode.getChild(0).accept(this);
		src += "]";
	}

}
