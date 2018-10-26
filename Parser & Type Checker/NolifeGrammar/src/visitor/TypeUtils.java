package visitor;

import java.util.HashMap;
import java.util.Stack;

public final class TypeUtils {
	
	protected static Stack<HashMap<String, Symbol>> symTableStack = new Stack<HashMap<String, Symbol>>();
	private static TypeUtils instance = new TypeUtils();
	
	protected class Symbol {
		
		int type;
		String name;
		boolean usedInScope = false;
		
		public Symbol(int t, String n) {
			type = t;
			name = n;
		}
		
	}
	
	protected class arraySymbol extends Symbol {
		int fromDim;
		int toDim;
		
		// 0 = int
		// 2 = char
		int typeOfDim;
		
		public arraySymbol(int f, int d, int t, String n, int type){
			super(t, n);
			fromDim = f;
			toDim = d;
			typeOfDim = type;
		}
	}
	
	protected static String typeCh(int n) {
		
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
	
	protected static void newFrame() {
		symTableStack.push(new HashMap<String, Symbol>());
	}
	
	protected static void remFrame() {
		symTableStack.pop();
	}
	
	protected static void addSymbol(String symName, int type) {
		symTableStack.peek().put(symName, instance.new Symbol(type, symName));
	}
	
	protected static void addArraySymbol(String symName, int type, String from, String to, int dimType) {
		System.out.println(dimType);
		if (dimType == 2) {
			from = Character.toString(from.charAt(1));
			to = Character.toString(to.charAt(1));
		}
			
		symTableStack.peek().put(symName, instance.new arraySymbol(Integer.parseInt(from), Integer.parseInt(to), type, symName, dimType));
	}
	
	protected static int[] getArrayRange(String symName) {
		Symbol sym = symTableStack.peek().get(symName);
		arraySymbol arr  = (arraySymbol) sym;
		
		
		int [] toReturn = {arr.fromDim, arr.toDim};
		return toReturn;
		
	}
	
	protected static int findSymbolType(String sym) {
		if (symTableStack.peek().containsKey(sym))
			return symTableStack.peek().get(sym).type;
		else if (symTableStack.get(0).containsKey(sym))
			return symTableStack.get(0).get(sym).type;
		else 
			return -1;
	}
	
	protected static boolean findInScope(String sym) {
		if (symTableStack.peek().containsKey(sym))
			return true;
		return false;
	}
	
	protected static void referencedSym(String sym) {
		if (symTableStack.peek().containsKey(sym))
			symTableStack.peek().get(sym).usedInScope = true;
		else if (symTableStack.get(0).containsKey(sym))
			symTableStack.get(0).get(sym).usedInScope = true;
	}
	
	protected static void checkRefSym() {
		for (Symbol n : symTableStack.peek().values())
			if (n.usedInScope == false)
				System.err.printf("Variable declared but never refereced: %s\n", n.name);
	}

}
