public class ExpressionValue {

    private String   name;
    private char     sign;
    private double   value;
    private double   relative_error;
    private boolean  is_operator;

    public ExpressionValue(char s) {
        this(s,0.0);
    }
    public ExpressionValue(char s, double r) {
        if ((s=='+')||(s=='-')||(s=='*')||(s=='/')) {
            sign = s;
            relative_error = r;
        }
        else {
            sign = ' ';
            relative_error = 0.0;
        }
        is_operator = true;
        name = null;
        value = 0.0;
    }
    public ExpressionValue(String n) {
        this(n,0.0,0.0);
    }
    public ExpressionValue(String n, double r) {
        this(n,r,0.0);
    }
    public ExpressionValue(String n, double r, double v) {
        is_operator = false;
        name = n;
        relative_error = r;
        value = v;
    }

    public boolean isOperator() { return is_operator; }

    public String toString() {
        if (isOperator()) {
            StringBuffer temp = new StringBuffer();
            temp.append(sign);
            return temp.toString();
        }
        else
            return name;
    }
    public String getName() {
        return name;
    }
    public char getSign() {
        return sign;
    }
    public String getValueString() {
        Double temp = new Double(getValue());
        return temp.toString();
    }
    public double getValue() {
        return value;
    }
    public String getErrorEquation() {
        StringBuffer temp;
        if (is_operator) {
            switch (sign) {
                case '+': temp = new StringBuffer("ADD"); break;
                case '-': temp = new StringBuffer("SUB"); break;
                case '*': temp = new StringBuffer("MUL"); break;
                case '/': temp = new StringBuffer("DIV"); break;
                default:  temp = new StringBuffer("???");
            }
        }
        else {
            temp = new StringBuffer(name);
        }
        temp.insert(0,'e');
        return temp.toString();
    }
    public String getErrorString() {
        Double temp = new Double(getError());
        return temp.toString();
    }
    public double getError() {
        return relative_error;
    }
    public void setValue(double d) {
        value = d;
    }
    public void setError(double d) {
        relative_error = d;
    }
}
