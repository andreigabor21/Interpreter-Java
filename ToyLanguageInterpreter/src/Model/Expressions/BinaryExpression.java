package Model.Expressions;

public abstract class BinaryExpression implements Expression{
    protected final Expression lhs, rhs;
    protected final OPERATOR operator;

    public BinaryExpression(OPERATOR operator, Expression lhs, Expression rhs) {
        this.operator = operator;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public String toString() {
        return String.format("Binary Expression{%s %s %s}", lhs.toString(), operator, rhs.toString());
    }
}
