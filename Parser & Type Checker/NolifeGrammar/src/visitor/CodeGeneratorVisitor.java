package visitor;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

import ast.*;

public class CodeGeneratorVisitor implements Visitor {
	
	private final int INTEGER = 0;
	private final int FLOAT = 1;
	private final int CHAR = 2;
	private HashMap<String, Boolean> registers = new HashMap<String, Boolean>();
	private String src = "";
	private int type = 0;
	private String toAssign = "";
	protected int Label = 0;
	protected int caseLabel = 0;

	public CodeGeneratorVisitor() {
		super();
	}
	
	@Override
	public Object visit(SymNode n) {
		return null; 
	}
	
	@Override
	public Object visit(IntNode n) {
		String reg = getFreeRegister();
		if (n.getType() == FLOAT) {
			src += "\tpush " + n.getLabel() + "\n";
			src += "\tfild dword ptr [%esp]\n";
			src += "\tfstp dword ptr [%esp]\n";
			src += "\tpop %" + reg + "\n";
		}
		else
			src += "\tmov %" + reg + ", " + n.getLabel() + "\n";
		return reg; 
	}
	
	@Override
	public Object visit(FloatNode n) {
		String reg = getFreeRegister();
		int off = n.getOffset() * -1;
		src += "\tmov %" + reg + ", [ _constant + " + off + " ]\n";
		type = 1;
		return reg; 
	}
	
	@Override
	public Object visit(CharNode n) {
		String reg = getFreeRegister();
		int intChar = n.getLabel().charAt(1);
		src += "\tmov %" + reg + ", " + intChar + "\n";
		return reg; 
	}
	
	@Override
	public Object visit(AddNode n) {
		src += "#Adding expression...\n";
		String leftReg = (String)n.getLeft().accept(this);
		String rightReg = (String)n.getRight().accept(this);
		
		if (type == 0)
			src += "\tadd %" + leftReg +", %" + rightReg + "\n";
		else {
			src += "#Float addition...\n";
			if (n.getLeft().getClass().getSimpleName().compareTo("IdRefNode") == 0)
				src += "\tfld dword ptr [%ebp-" + (n.getLeft().getOffset() * -1) + "]\n";
			else {
				src += "\tpush %" + leftReg + "\n";
				src += "\tfld dword ptr [%esp]\n";
				src += "\tadd %esp, 4\n";
			}
			if (n.getRight().getClass().getSimpleName().compareTo("IdRefNode") == 0)
				src += "\tfadd dword ptr [%ebp-" + (n.getRight().getOffset() * -1) + "]\n";
			else {
				src += "\tpush %" + rightReg + "\n";
				src += "\tfadd dword ptr [%esp]\n";
				src += "\tadd %esp, 4\n";
			}
			src += "\tsub %esp, 4\n";
			src += "\tfstp dword ptr [%esp]\n";
			src += "\tpop %" + leftReg + "\n";
		}
		freeRegister(rightReg);
		return leftReg; 
	}

	@Override
	public Object visit(SubNode n) {
		src += "#Substraction expression...\n";
		String leftReg = (String)n.getLeft().accept(this);
		String rightReg = (String)n.getRight().accept(this);
		
		if (type == 0)
			src += "\tsub %" + leftReg +", %" + rightReg + "\n";
		else {
			src += "#Float substraction...\n";
			if (n.getLeft().getClass().getSimpleName().compareTo("IdRefNode") == 0)
				src += "\tfld dword ptr [%ebp-" + (n.getLeft().getOffset() * -1) + "]\n";
			else {
				src += "\tpush %" + leftReg + "\n";
				src += "\tfld dword ptr [%esp]\n";
				src += "\tadd %esp, 4\n";
			}
			if (n.getRight().getClass().getSimpleName().compareTo("IdRefNode") == 0)
				src += "\tfsub dword ptr [%ebp-" + (n.getRight().getOffset() * -1) + "]\n";
			else {
				src += "\tpush %" + rightReg + "\n";
				src += "\tfsub dword ptr [%esp]\n";
				src += "\tadd %esp, 4\n";
			}
			src += "\tsub %esp, 4\n";
			src += "\tfstp dword ptr [%esp]\n";
			src += "\tpop %" + leftReg + "\n";
		}
		freeRegister(rightReg);
		return leftReg; 
	}

	@Override
	public Object visit(ModNode n) {
		src += "#Modulus expressions...\n";
		String leftReg = (String)n.getLeft().accept(this);
		String rightReg = (String)n.getRight().accept(this);
		
		src += "\tcdq\n";
		src += "\tidiv %" + rightReg + "\n";

		freeRegister(rightReg);
		return "edx";  
		}

	@Override
	public Object visit(MulNode n) {
		src += "#Multiplication expression...\n";
		String leftReg = (String)n.getLeft().accept(this);
		String rightReg = (String)n.getRight().accept(this);
		
		if (type == 0)
			src += "\timul %" + leftReg +", %" + rightReg + "\n";
		else {
			src += "#Float Multiplication...\n";
			if (n.getLeft().getClass().getSimpleName().compareTo("IdRefNode") == 0)
				src += "\tfld dword ptr [%ebp-" + (n.getLeft().getOffset() * -1) + "]\n";
			else {
				src += "\tpush %" + leftReg + "\n";
				src += "\tfld dword ptr [%esp]\n";
				src += "\tadd %esp, 4\n";
			}
			if (n.getRight().getClass().getSimpleName().compareTo("IdRefNode") == 0)
				src += "\tfmul dword ptr [%ebp-" + (n.getRight().getOffset() * -1) + "]\n";
			else {
				src += "\tpush %" + rightReg + "\n";
				src += "\tfmul dword ptr [%esp]\n";
				src += "\tadd %esp, 4\n";
			}
			src += "\tsub %esp, 4\n";
			src += "\tfstp dword ptr [%esp]\n";
			src += "\tpop %" + leftReg + "\n";
		}
		freeRegister(rightReg);
		return leftReg; 
	}
	
	@Override
	public Object visit(LessThanNode n) {
		src += "#Less Than Expression...\n";
		String leftReg = (String)n.getLeft().accept(this);
		String rightReg = (String)n.getRight().accept(this);
		
		//If float comparison 
		if (n.getType() == FLOAT) {
			src += "\tpush %" + rightReg + "\n";
			src += "\tfld dword ptr [ %esp ]\n";
			src += "\tpush %" + leftReg + "\n";
			src += "\tfld dword ptr [ %esp ]\n";
			src += "\tadd %esp, 8\n";
			src += "\tfcomip %st(0), %st(1)\n";
			src += "\tfstp %st(0)\n";
			src += "\tjae .L" + ++Label + "\n";
		}
		else {
			src += "\tcmp %" + leftReg + ", %" + rightReg + "\n";
			src += "\tjge .L" + ++Label + "\n";
		}
				
		int i = Label;
		src += "\tmov %" + leftReg + ", 1\n";
		src += "\tjmp .L" + ++Label + "\n";
		src += ".L" + i + ":\n";
		src += "\tmov %" + leftReg + ", 0\n";
		src += ".L" + Label + ":\n";
		
		freeRegister(leftReg);
		freeRegister(rightReg);
		type = INTEGER;
		return leftReg; 
	}

	@Override
	public Object visit(LessEqualNode n) {
		src += "#Less Equal Expression...\n";
		String leftReg = (String)n.getLeft().accept(this);
		String rightReg = (String)n.getRight().accept(this);
		
		//If float comparison 
		if (n.getType() == FLOAT) {
			src += "\tpush %" + rightReg + "\n";
			src += "\tfld dword ptr [ %esp ]\n";
			src += "\tpush %" + leftReg + "\n";
			src += "\tfld dword ptr [ %esp ]\n";
			src += "\tadd %esp, 8\n";
			src += "\tfcomip %st(0), %st(1)\n";
			src += "\tfstp %st(0)\n";
			src += "\tja .L" + ++Label + "\n";
		}
		else {
			src += "\tcmp %" + leftReg + ", %" + rightReg + "\n";
			src += "\tjg .L" + ++Label + "\n";
		}
				
		int i = Label;
		src += "\tmov %" + leftReg + ", 1\n";
		src += "\tjmp .L" + ++Label + "\n";
		src += ".L" + i + ":\n";
		src += "\tmov %" + leftReg + ", 0\n";
		src += ".L" + Label + ":\n";
		
		type = INTEGER;
		freeRegister(leftReg);
		freeRegister(rightReg);
		return leftReg; 
	}
	
	@Override
	public Object visit(GreaterThanNode n) {
		src += "#Greater Than Expression...\n";
		String leftReg = (String)n.getLeft().accept(this);
		String rightReg = (String)n.getRight().accept(this);
			
		//If float comparison 
		if (n.getType() == FLOAT) {
			src += "\tpush %" + rightReg + "\n";
			src += "\tfld dword ptr [ %esp ]\n";
			src += "\tpush %" + leftReg + "\n";
			src += "\tfld dword ptr [ %esp ]\n";
			src += "\tadd %esp, 8\n";
			src += "\tfcomip %st(0), %st(1)\n";
			src += "\tfstp %st(0)\n";
			src += "\tjbe .L" + ++Label + "\n";
		}
		else {
			src += "\tcmp %" + leftReg + ", %" + rightReg + "\n";
			src += "\tjle .L" + ++Label + "\n";
		}
				
		int i = Label;
		src += "\tmov %" + leftReg + ", 1\n";
		src += "\tjmp .L" + ++Label + "\n";
		src += ".L" + i + ":\n";
		src += "\tmov %" + leftReg + ", 0\n";
		src += ".L" + Label + ":\n";
		
		freeRegister(leftReg);
		freeRegister(rightReg);
		type = INTEGER;
		return leftReg; 
	}
	
	@Override
	public Object visit(GreaterEqualNode n) {
		src += "#Greater Equal Expression...\n";
		String leftReg = (String)n.getLeft().accept(this);
		String rightReg = (String)n.getRight().accept(this);
		
		//If float comparison 
		if (n.getType() == FLOAT) {
			src += "\tpush %" + rightReg + "\n";
			src += "\tfld dword ptr [ %esp ]\n";
			src += "\tpush %" + leftReg + "\n";
			src += "\tfld dword ptr [ %esp ]\n";
			src += "\tadd %esp, 8\n";
			src += "\tfcomip %st(0), %st(1)\n";
			src += "\tfstp %st(0)\n";
			src += "\tjb .L" + ++Label + "\n";
		}
		else {
			src += "\tcmp %" + leftReg + ", %" + rightReg + "\n";
			src += "\tjl .L" + ++Label + "\n";
		}
		
		int i = Label;
		src += "\tmov %" + leftReg + ", 1\n";
		src += "\tjmp .L" + ++Label + "\n";
		src += ".L" + i + ":\n";
		src += "\tmov %" + leftReg + ", 0\n";
		src += ".L" + Label + ":\n";
		
		freeRegister(leftReg);
		freeRegister(rightReg);
		type = INTEGER;
		return leftReg; 
	}
	
	@Override
	public Object visit(NotEqualNode n) {
		src += "#Not Equal Expression...\n";
		String leftReg = (String)n.getLeft().accept(this);
		String rightReg = (String)n.getRight().accept(this);
		
		//If float comparison 
		if (n.getType() == FLOAT) {
			src += "\tpush %" + rightReg + "\n";
			src += "\tfld dword ptr [ %esp ]\n";
			src += "\tpush %" + leftReg + "\n";
			src += "\tfld dword ptr [ %esp ]\n";
			src += "\tadd %esp, 8\n";
			src += "\tfcomip %st(0), %st(1)\n";
			src += "\tfstp %st(0)\n";
		}
		else 
			src += "\tcmp %" + leftReg + ", %" + rightReg + "\n";
		
		src += "\tje .L" + ++Label + "\n";
		int i = Label;
		src += "\tmov %" + leftReg + ", 1\n";
		src += "\tjmp .L" + ++Label + "\n";
		src += ".L" + i + ":\n";
		src += "\tmov %" + leftReg + ", 0\n";
		src += ".L" + Label + ":\n";
		
		freeRegister(leftReg);
		freeRegister(rightReg);
		type = INTEGER;
		return leftReg; 
	}
	
	@Override
	public Object visit(EqualNode n) {
		src += "#Equal Expression...\n";
		String leftReg = (String)n.getLeft().accept(this);
		String rightReg = (String)n.getRight().accept(this);
		
		//If float comparison 
		if (n.getType() == FLOAT) {
			src += "\tpush %" + rightReg + "\n";
			src += "\tfld dword ptr [ %esp ]\n";
			src += "\tpush %" + leftReg + "\n";
			src += "\tfld dword ptr [ %esp ]\n";
			src += "\tadd %esp, 8\n";
			src += "\tfcomip %st(0), %st(1)\n";
			src += "\tfstp %st(0)\n";
		}
		else 
			src += "\tcmp %" + leftReg + ", %" + rightReg + "\n";
					
		src += "\tjne .L" + ++Label + "\n";
		int i = Label;
		src += "\tmov %" + leftReg + ", 1\n";
		src += "\tjmp .L" + ++Label + "\n";
		src += ".L" + i + ":\n";
		src += "\tmov %" + leftReg + ", 0\n";
		src += ".L" + Label + ":\n";
		
		freeRegister(leftReg);
		freeRegister(rightReg);
		type = INTEGER;
		return leftReg; 
	}
	
	@Override
	public Object visit(AndNode n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
	return null; }
	
	@Override
	public Object visit(OrNode n) {
		String leftReg = (String)n.getLeft().accept(this);
		
		src += "\tcmp %" + leftReg + ", 0\n";
		
		String rightReg = (String)n.getRight().accept(this);
		
		src += "\tcmp %" + rightReg + ", 0\n";
		
		freeRegister(leftReg);
		freeRegister(rightReg);
		return null; 
	}
	
	@Override
	public Object visit(IdRefNode n) {
		String reg = getFreeRegister();
		src += "\tmov %" + reg + ", dword ptr [%ebp" + n.getOffset() + "]\n";
		type = n.getType();
		return reg; 
	}
	
	@Override
	public Object visit(ArrayRefNode arrayRefNode) {
		String reg = getFreeRegister();
		if (arrayRefNode.getChild(0).getClass().getSimpleName().compareTo("IdRefNode") == 0) {
			String toReg = (String)arrayRefNode.getChild(0).accept(this);
			int offset = arrayRefNode.getEndOffset();
			freeRegister(toReg);
			src += "\tmov %" + reg + ", %" + toReg + "\n";
			src += "\tsub %" + reg + ", " + arrayRefNode.getStartDim() + "\n";
			src += "\timul %" + reg + ", 4\n";
			src += "\tadd %" + reg + ", " + offset + "\n";
			src += "\tadd %" + reg +", %ebp\n";
		}
		else {
			src += "\tmov %" + reg + ", dword ptr [%ebp" + arrayRefNode.getOffset() + "]\n";
		}
		return reg; 
	}
	
	@Override
	public Object visit(StringNode stringNode) {
		stringNode.getLabel();
	return null; }
	
	@Override
	public Object visit(ParenNode parenNode) {
		String reg = (String)parenNode.getChild(0).accept(this);
		return reg; 
	}
	
	@Override
	public Object visit(NotNode notNode) {
		String reg = (String)notNode.getChild(0).accept(this);
		
		src += "#Not statement...\n";
		src += "\tcmp %" + reg + ", 0\n";
		src += "\tje .L" + ++Label + "\n";
		int i = Label;
		src += "\tmov %" + reg + ", 0\n";
		src += "\tjmp .L" + ++Label + "\n";
		src += ".L" + i + ":\n";
		src += "\tmov %" + reg + ", 1\n";
		src += ".L" + Label + ":\n";
		
		return reg; 
	}

	@Override
	public Object visit(DeclNode declNode) {
		src += "#Declaring variables. Amount of declarations * 4\n";
		int espSize = 0;
		int arrDif = 0;
		
		for (ASTNode child : declNode.getChildren()) {
			if (child.getChild(0).getClass().getSimpleName().compareTo("ArrayDeclNode") == 0) {
				ArrayDeclNode x = (ArrayDeclNode) child.getChild(0);
				espSize += x.getOffset() - x.getEndOffset();
			}
				
			else
				espSize += 4;
		}
		
		src += "\tsub %esp, " + espSize + "\n";
	return null; }

	@Override
	public Object visit(ProgramNode programNode) {
		initializeRegisters();
		
		programNode.getLabel();
		//Print initial code
		src += "\t.intel_syntax\n\t.section .rodata\n\n.io_format:\n";
		src += "\t.string \"%d\\12\"\n";
		src += "\t.string \"%f\\12\"\n";
		src += "\t.string \"%c\\12\"\n";
		src += "\t.string \"%s\\12\"\n";
		
		src += "\n.io_format_in:\n";
		src += "\t.string \"%d\"\n\t.string \"%f\"\n\t.string \"%c\"\n";
		
		//Print constants
		src += "\n_constant:\n";
		
		Map<String, Integer> sortedMap = sortMap(programNode.getTable());
		
		for (String key : sortedMap.keySet()) {
			try {
				float temp = Float.parseFloat(key);
				src += "\t.float " + temp + "\n";
			}
			catch(NumberFormatException e) {
				src += "\t.string " + key + "\n";
			}
		}
		
		src += "\n";
		
		if (programNode.getChildren().size() > 1 && programNode.getChild(1).getClass().getSimpleName().contains("SubProgDeclNode")) {
			programNode.getChild(1).accept(this);
			src += ".text\n\t.globl main;\n\t.type main, @function\n";
			src += "\nmain:\n";
			src += "\tpush %ebp\n";
			src += "\tmov %ebp, %esp\n";
			programNode.getChild(0).accept(this);
		} else {
			src += ".text\n\t.globl main;\n\t.type main, @function\n";
			src += "\nmain:\n";
			src += "\tpush %ebp\n";
			src += "\tmov %ebp, %esp\n";
			programNode.getChild(0).accept(this);
			if (programNode.getChildren().size() > 1)
				programNode.getChild(1).accept(this);
		}
		
		//Initialize global scope
		for (int i = 2; i < programNode.getChildren().size(); i++)
			programNode.getChild(i).accept(this);
		
		src += "\tleave\n\tret";
		
		//Testing the constTable
		//for (String key : constTable.keySet())
		//	System.out.printf("Key: %s, Value: %d\n", key, constTable.get(key));
		
		//Output
		try (PrintStream out = new PrintStream(new FileOutputStream("output.S"))) {
		    out.print(src);
		}
		catch (java.io.FileNotFoundException e) {
	        System.out.println("Error output file.");
		}
		
		return null;
	}

	@Override
	public Object visit(VarDeclsNode varDeclsNode) {
		for (ASTNode node : varDeclsNode.getChildren())
			node.accept(this);
	return null; }

	@Override
	public Object visit(IntTypeNode intTypeNode) {
		intTypeNode.getChild(0).accept(this);
	return null; }

	@Override
	public Object visit(FloatTypeNode floatTypeNode) {
		floatTypeNode.getChild(0).accept(this);
	return null; }
	
	@Override
	public Object visit(CharTypeNode charTypeNode) {
		charTypeNode.getChild(0).accept(this);
	return null; }

	@Override
	public Object visit(AssignNode assignNode) {
		src += "#---- Assignment expression...\n";
		assignNode.getChild(1).accept(this);
		src += "#Assigned variable...\n";
		assignNode.getChild(0).accept(this);
		freeRegister("edi");
		return null; 
	}
	
	@Override
	public Object visit(IfStmtNode ifStmtNode) {
		String reg = (String)ifStmtNode.getChild(0).accept(this);
		src += "#If Statement...\n";
		
		src += "\tcmp %" + reg + ", 0\n";
		src += "\tje .L" + ++Label + "\n";
		
		int l = Label;
		ifStmtNode.getChild(1).accept(this); 
		if (ifStmtNode.getChild(2) != null) {
			src += "\tjmp .L" + ++Label + "\n";
			src += ".L" + l + ":\n";
			l = Label;
			ifStmtNode.getChild(2).accept(this);
			src += ".L" + l + ":\n";
		}
		else
			src += ".L" + l + ":\n";
		
		return null; 
	}
	
	@Override
	public Object visit(WhileNode whileNode) {
		src += "#While Statement...\n";
		src += ".L" + ++Label + ":\n";
		int toGo = Label;
		String reg = (String)whileNode.getChild(0).accept(this);
		src += "\tcmp %" + reg + ", 0\n";
		src += "\tje .L" + ++Label + "\n";
		int l = Label;
		
		whileNode.getChild(1).accept(this);
		src += "\tjmp .L" + toGo + "\n";
		src += ".L" + l + ":\n";
		return null; 
	}
	
	@Override
	public Object visit(ProcCallNode procCallNode) {
		//Go through all expressions in the list of statements
		String name = procCallNode.getLabel();
		//if (procCallNode.getChild(0) != null) 
		//	procCallNode.getChild(0).accept(this);
		
		src += "\tcall " + name + "\n";
		return null; 
	}
	
	@Override
	public Object visit(WriteNode writeNode) {
		src += "# Printing...\n";
		String ref = "";
		int off = writeNode.getChild(0).getOffset() * -1;
		int offset = 8;
		
		//If float...
		switch (writeNode.getChild(0).getClass().getSimpleName()) {
		
			case "IntNode": {
				src += "\tpush " + writeNode.getChild(0).getLabel() + "\n";
				src += "\tpush [ offset flat:.io_format + 0 ]\n";
				break;
			}
			
			case "CharNode": {
				int asciiChar = (int) writeNode.getChild(0).getLabel().charAt(1);
				src += "\tpush " + asciiChar + "\n";
				src += "\tpush [ offset flat:.io_format + 8 ]\n";
				break;
			}
		
			case "FloatNode": {
				src += "\tsub %esp, 4\n";
				src += "\tmov %edi, [ _constant + " + off + " ]\n";
				src += "\tpush %edi\n";
				src += "\tfld dword ptr [%esp]\n";
				src += "\tfstp qword ptr [%esp]\n";
				src += "\tpush [ offset flat:.io_format + 4 ]\n";
				offset = 12;
				break;
			}
			
			case "StringNode": {
				src += "\tpush [ offset flat:_constant + " + off + " ]\n";
				src += "\tpush [ offset flat:.io_format + 12 ]\n";
				break;
			}
			
			case "ArrayRefNode":
				if (writeNode.getChild(0).getChild(0).getClass().getSimpleName().compareTo("IdRefNode") == 0) {
					ref = (String)writeNode.getChild(0).accept(this);
					freeRegister(ref);
				}
					
				off = writeNode.getChild(0).getOffset() * -1;
			case "IdRefNode": {
				switch (writeNode.getChild(0).getType()) {
					//Int
					case 0: {
						src += "\tpush dword ptr [%" + (ref != "" ? ref: "ebp-"+off) + "]\n";
						src += "\tpush [ offset flat:.io_format + 0 ]\n";
						break;
					}
					//Float
					case 1: {
						src += "\tsub %esp, 4\n";
						src += "\tfld dword ptr [%" + (ref != "" ? ref: "ebp-"+off) + "]\n";
						src += "\tfstp qword ptr [%esp]\n";
						src += "\tpush [ offset flat:.io_format + 4 ]\n";
						offset = 12;
						break;
					}
					//Char
					case 2: {
						src += "\tpush dword ptr [%" + (ref != "" ? ref: "ebp-"+off) + "]\n";
						src += "\tpush [ offset flat:.io_format + 8 ]\n";
						break;
					}
				}
				
				break;
			}
			
			default:
				String reg = (String)writeNode.getChild(0).accept(this);
				if (type == 1) {
					src += "\tsub %esp, 4\n";
					src += "\tpush %edi\n"; 
					src += "\tfld dword ptr [%esp]\n";
					src += "\tfstp qword ptr [%esp]\n";
					src += "\tpush [ offset flat:.io_format + 4 ]\n";
					offset = 12;
					break;
				} else {
					src += "\tpush %" + reg + "\n";
					src += "\tpush [ offset flat:.io_format + " + (type * 4) + "]\n";
					freeRegister("eax");
					break;
				}
		}
		
		//writeNode.getChild(0).accept(this);
		src += "\tcall printf\n";
		src += "\tadd %esp, " + offset + "\n";
	return null; }
	
	@Override
	public Object visit(ReadNode readNode) {
		src += "# Reading...\n";
		int off = readNode.getChild(0).getOffset() * -1;
		int offset = 4;
		
		
		src += "\tmov %eax, %ebp\n";
		src += "\tsub %eax, " + off + "\n";
		src += "\tpush %eax\n";
		
		//If float...
		switch (readNode.getType()) {
			case INTEGER: 
				src += "\tpush [ offset flat:.io_format_in + 0 ]\n";
				break;
				
			case FLOAT: 
				src += "\tpush [ offset flat:.io_format_in + 3 ]\n";
				break;
			
			case CHAR: 
				src += "\tpush [ offset flat:.io_format_in + 6 ]\n";
				break;
		}
		
		src += "\tcall scanf\n"; 
		src += "\tadd %esp, 8\n";
		return null;
	}
	
	@Override
	public Object visit(ReturnNode returnNode) {
		String reg = (String)returnNode.getChild(0).accept(this);
		src += "\tmov %eax, %" + reg + "\n";
		freeRegister(reg);
		return null; 
	}
	
	@Override
	public Object visit(CaseStmtNode caseStmtNode) {
		String reg = (String)caseStmtNode.getChild(0).accept(this);
		int fakeLabel = Label;
		
		src += "#Case Statement...\n";
		for (ASTNode casesNode : caseStmtNode.getChild(1).getChildren()) {
			fakeLabel += 1;
			for (ASTNode childNode : casesNode.getChild(0).getChildren()) {
				src += "\tcmp %" + reg + ", " + childNode.getLabel() + "\n";
				src += "\tje .L" + fakeLabel + "\n";
			}
		}
		src += "\tjmp .L" + ++fakeLabel + "\n";
		caseLabel = fakeLabel;
				
		if (caseStmtNode.getChild(1) != null) 
			caseStmtNode.getChild(1).accept(this);
		
		src += ".L" + caseLabel + ":\n";
		return null; 
	}
	
	@Override
	public Object visit(CaseListNode caseListNode) {
		for (ASTNode childNode : caseListNode.getChildren())
			childNode.accept(this);
		return null; 
	}
	
	@Override
	public Object visit(CaseNode caseNode) {
		caseNode.getChild(0).accept(this);
		caseNode.getChild(1).accept(this);
		src += "\tjmp .L" + caseLabel + "\n";
		return null; 
	}
	
	@Override
	public Object visit(CaseLabelsNode caseLabelsNode) {
		src += ".L" + ++Label + ":\n";
		caseLabelsNode.getChild(0).accept(this);
		for (int i = 1; i < caseLabelsNode.getChildren().size(); i++)
			caseLabelsNode.getChild(i).accept(this);
		return null; 
	}

	@Override
	public Object visit(StmtListNode stmtListNode) {
		//Go through all expressions in the list of statements
		for (ASTNode childNode : stmtListNode.getChildren()) 
			childNode.accept(this);
	return null; }
	
	@Override
	public Object visit(ExprListNode exprListNode) {
		//Go through all expressions in the list of statements
		exprListNode.getChild(0).accept(this);
		for (int i = 1; i < exprListNode.getChildren().size(); i++) 
			exprListNode.getChild(i).accept(this);
	return null; }
	
	@Override
	public Object visit(SubProgDeclNode subProgDeclNode) {
		for (ASTNode childNode : subProgDeclNode.getChildren())
			childNode.accept(this);
	return null; }
	
	@Override
	public Object visit(ProcNode procNode) {
		src += "\t.globl " + procNode.getLabel() + ";\n";
		src += "\t.type " + procNode.getLabel() + ", @function\n";
		src += procNode.getLabel() + ":\n";
		src += "\tpush %ebp\n\tmov %ebp, %esp\n";
		
		if (procNode.getChild(0) != null)
			procNode.getChild(0).accept(this);
		
		if (procNode.getChild(1) != null)
			procNode.getChild(1).accept(this);

		procNode.getChild(2).accept(this);
		
		src += "\tleave\n\tret\n\n";
		return null; 
	}

	@Override
	public Object visit(FuncNode funcNode) {
		
		src += "\t.globl " + funcNode.getLabel() + ";\n";
		src += "\t.type " + funcNode.getLabel() + ", @function\n";
		src += funcNode.getLabel() + ":\n";
		src += "\tpush %ebp\n\tmov %ebp, %esp\n";
		
		if (funcNode.getChild(0) != null)
			funcNode.getChild(0).accept(this);
		
		if (funcNode.getChild(1) != null)
			funcNode.getChild(1).accept(this);

		funcNode.getChild(2).accept(this);
		
		src += "\tleave\n\tret\n\n";
		return null; 
	}
	
	@Override
	public Object visit(ParamNode paramNode) {
		//Go through all expressions in the list of statements
		//paramNode.getChild(0).accept(this);
		//for (int i = 1; i < paramNode.getChildren().size(); i++)
			//paramNode.getChild(i).accept(this);
		return null; 
	}
	
	@Override
	public Object visit(CallNode callNode) {
		//Go through all expressions in the list of statements
		String name = callNode.getLabel();
		//if (callNode.getChild(0) != null) 
		//	callNode.getChild(0).accept(this);
		
		src += "\tcall " + name + "\n";
		String reg = getFreeRegister();
		src += "\tmov %" + reg + ", %eax\n";
		return reg; 
	}

	@Override
	public Object visit(IdDeclNode idDeclNode) {
		idDeclNode.getLabel();
	return null; }
	
	@Override
	public Object visit(ArrayDeclNode arrayDeclNode) {
		arrayDeclNode.getLabel();
		arrayDeclNode.getChild(0).accept(this);
		arrayDeclNode.getChild(1).accept(this);
		return null; 
		}

	@Override
	public Object visit(IdDefNode idDefNode) {
		int offset = idDefNode.getOffset() * -1;
		
		if (idDefNode.getType() == 1) {
			src += "\tpush %edi\n";
			src += "\tfld dword ptr [%esp]\n";
			src += "\tadd %esp, 4\n";
			src += "\tfstp dword ptr [%ebp-" + offset + "]\n";
		}
		else
			src += "\tmov dword ptr [%ebp-" + offset + "], %edi\n";
		return null; 
	}
	
	@Override
	public Object visit(ArrayDefNode arrayDefNode) {
		if (arrayDefNode.getChild(0).getClass().getSimpleName().compareTo("IdRefNode") == 0) {
			int offset = arrayDefNode.getEndOffset();
			src += "\tmov %eax, %edi\n";
			src += "\tsub %eax, " + arrayDefNode.getStartDim() + "\n";
			src += "\timul %eax, 4\n";
			src += "\tadd %eax, " + offset + "\n";
			src += "\tadd %eax, %ebp\n";
			src += "\tmov dword ptr [%eax], %edi\n"; 
		}
		else {
			int offset = arrayDefNode.getOffset() * -1;
			src += "\tmov dword ptr [%ebp-" + offset + "], %edi\n";
		}
		return null; 
	}
	
	private Map<String, Integer> sortMap(HashMap<String, Integer> map) {
		
		// 1. Convert Map to List of Map
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(map.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        
        return sortedMap;
	}
	
	private void initializeRegisters() {
		registers.put("eax", false);
		registers.put("ebx", false);
		registers.put("ecx", false);
		registers.put("edx", false);
		registers.put("edi", false);
		registers.put("esi", false);
	}
	
	private String getFreeRegister() {
		
		if (registers.get("edi") == false) {
			registers.put("edi", true);
			return "edi";
		}
		else if (registers.get("esi") == false) {
			registers.put("esi", true);
			return "esi";
		}
		else if (registers.get("ebx") == false) {
			registers.put("ebx", true);
			return "ebx";
		}
		else if (registers.get("ecx") == false) {
			registers.put("ecx", true);
			return "ecx";
		}
		else if (registers.get("edx") == false) {
			registers.put("edx", true);
			return "edx";
		}
		else if (registers.get("eax") == false) {
			registers.put("eax", true);
			return "eax";
	}
		
		return "NOT ENOUGH REGS";
	}
	
	private void freeRegister(String reg) {
		registers.put(reg, false);
	}

}
