// generated with ast extension for cup
// version 0.8
// 13/0/2020 2:3:31


package rs.ac.bg.etf.pp1.ast;

public class BoolValue extends Value {

    private String B1;

    public BoolValue (String B1) {
        this.B1=B1;
    }

    public String getB1() {
        return B1;
    }

    public void setB1(String B1) {
        this.B1=B1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("BoolValue(\n");

        buffer.append(" "+tab+B1);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [BoolValue]");
        return buffer.toString();
    }
}
