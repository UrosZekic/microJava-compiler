// generated with ast extension for cup
// version 0.8
// 13/0/2020 2:3:32


package rs.ac.bg.etf.pp1.ast;

public class SimpleDesignator extends DesigExprList {

    private String id;

    public SimpleDesignator (String id) {
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id=id;
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
        buffer.append("SimpleDesignator(\n");

        buffer.append(" "+tab+id);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SimpleDesignator]");
        return buffer.toString();
    }
}
