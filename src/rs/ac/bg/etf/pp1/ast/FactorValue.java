// generated with ast extension for cup
// version 0.8
// 13/0/2020 2:3:32


package rs.ac.bg.etf.pp1.ast;

public class FactorValue extends Factor {

    private FactValue FactValue;

    public FactorValue (FactValue FactValue) {
        this.FactValue=FactValue;
        if(FactValue!=null) FactValue.setParent(this);
    }

    public FactValue getFactValue() {
        return FactValue;
    }

    public void setFactValue(FactValue FactValue) {
        this.FactValue=FactValue;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FactValue!=null) FactValue.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FactValue!=null) FactValue.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FactValue!=null) FactValue.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorValue(\n");

        if(FactValue!=null)
            buffer.append(FactValue.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorValue]");
        return buffer.toString();
    }
}
