package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.SymbolTableVisitor;

public class SymbolTableVisitorPrint extends SymbolTableVisitor {
	protected StringBuilder output = new StringBuilder();

	private int wholeTable;

	SymbolTableVisitorPrint(int print) {

		wholeTable = print;

		if (wholeTable == 1)
			output.append("(Level 0)" + "\n");
	}

	public void visitVar(Obj objToVisit) {
		switch (objToVisit.getKind()) {
		case Obj.Con:
			output.append("Con ");
			break;
		case Obj.Var:
			output.append("Var ");
			break;
		case Obj.Type:
			output.append("Type ");
			break;
		case Obj.Meth:
			output.append("Meth ");
			break;
		case Obj.Fld:
			output.append("Fld ");
			break;
		case Obj.Prog:
			output.append("Prog ");
			break;
		}
		output.append(objToVisit.getName());
		output.append(": ");
		objToVisit.getType().accept(this);
		output.append(", ");
		output.append(objToVisit.getAdr());
		output.append(", ");
		output.append(objToVisit.getLevel() + " ");
	}

	@Override
	public void visitObjNode(Obj objToVisit) {
		visitVar(objToVisit);
		if (objToVisit.getKind() == Obj.Meth) {
			for (Obj o : objToVisit.getLocalSymbols()) {
				output.append("[");
				o.accept(this);
				output.append("]");
			}
		}

		if (objToVisit.getKind() == Obj.Prog) {

			for (Obj o : objToVisit.getLocalSymbols()) {
				if (o.getName().equals("main")) {
					int c = 3;
					int k = c + 2;
				}
				output.append("\n");
				output.append("     ");
				output.append("[");
				o.accept(this);
				output.append("]");
			}

		}

	}

	@Override
	public void visitScopeNode(Scope scopteToVisit) {
		for (Obj o : scopteToVisit.values()) {
			o.accept(this);
			output.append("\n");
		}

	}

	@Override
	public void visitStructNode(Struct structToVisit) {
		switch (structToVisit.getKind()) {
		case Struct.None:
			output.append("notype");
			break;
		case Struct.Int:
			output.append("int");
			break;
		case Struct.Char:
			output.append("char");
			break;
		case Struct.Array:
			output.append("Arr of ");

			switch (structToVisit.getElemType().getKind()) {
			case Struct.None:
				output.append("notype");
				break;
			case Struct.Int:
				output.append("int");
				break;
			case Struct.Char:
				output.append("char");
				break;
			case Struct.Bool:
				output.append("bool");
				break;
			}
			break;
		case Struct.Class:
			output.append("Class");
			break;
		case Struct.Bool:
			output.append("bool");
			break;
		}

	}

	@Override
	public String getOutput() {
		return output.toString();
	}

}
