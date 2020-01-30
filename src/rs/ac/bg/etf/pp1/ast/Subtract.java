// generated with ast extension for cup
// version 0.8
// 13/0/2020 2:3:32


package rs.ac.bg.etf.pp1.ast;

public class Subtract extends AddOp {

    public Subtract () {
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
        buffer.append("Subtract(\n");

        buffer.append(tab);
        buffer.append(") [Subtract]");
        return buffer.toString();
    }
}
