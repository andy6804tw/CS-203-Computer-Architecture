import java.text.*;

public class ExpressionNode {

    ExpressionValue  value;
    ExpressionNode   left_side;
    ExpressionNode   right_side;

    public ExpressionNode(ExpressionValue n) {
        this(n,null,null);
    }
    public ExpressionNode(ExpressionValue n, ExpressionNode ls, ExpressionNode rs) {
        value      = n;
        left_side  = ls;
        right_side = rs;
    }
    public boolean isOperator() { return value.isOperator(); }
    public boolean isLeaf() {
        if ((left_side==null)&&(right_side==null))
            return true;
        else
            return false;
    }
    public boolean isAddOrSubtract() {
        if (value.isOperator() && ((value.getSign()=='+')||(value.getSign()=='-')))
            return true;
        else
            return false;
    }
    public ExpressionNode getLeftSide() { return left_side; }
    public ExpressionNode getRightSide() { return right_side; }

    public String toString() {
        if (value.isOperator()) {
            String lefty, righty;
            if (left_side.isOperator() && left_side.isAddOrSubtract()
                                       && !isAddOrSubtract())
               lefty = "("+left_side.toString()+")";
            else
               lefty = left_side.toString();
            if (right_side.isOperator() && right_side.isAddOrSubtract())
               righty = "("+right_side.toString()+")";
            else
               righty = right_side.toString();
            return (new String(lefty+value.toString()+righty));
        }
        else {
            return value.toString();
        }
    }
    public String getName() {
        return value.getName();
    }
    public char getSign() {
        return value.getSign();
    }
    public String getValueString() {
        Double temp = new Double(getValue());
        return temp.toString();
    }
    public double getValue() {
        double temp;
        if (value.isOperator()) {
            switch (value.getSign()) {
                case '+': temp = (left_side.getValue()+right_side.getValue()); break;
                case '-': temp = (left_side.getValue()-right_side.getValue()); break;
                case '*': temp = (left_side.getValue()*right_side.getValue()); break;
                case '/': temp = (left_side.getValue()/right_side.getValue()); break;
                default:  temp = 0.0;
            }
        }
        else {
            temp = value.getValue();
        }
        return temp;
    }
    public String getErrorString() {
        DecimalFormat two_decimal_places = new DecimalFormat("##.###");
        Double temp = new Double(getError());
        if (temp.isInfinite() || temp.isNaN())
            return temp.toString();
        else
            return two_decimal_places.format(temp);
    }
    public double getError() {
        double temp;
        if (value.isOperator()) {
            double left_error  = 0.01*left_side.getError();
            double right_error = 0.01*right_side.getError();
            double this_error  = 0.01*value.getError();
            double left_value  = left_side.getValue();
            double right_value = right_side.getValue();
            double term1, term2;
            switch (value.getSign()) {
                case '+':
                    term1 = (left_value*left_error)/(left_value+right_value);
                    term2 = (right_value*right_error)/(left_value+right_value);
                    temp = (1.0+this_error)*(term1 + term2) + this_error;
                    temp = temp*100;
                    break;
                case '-':
                    term1 = (left_value*left_error)/(left_value-right_value);
                    term2 = (right_value*right_error)/(left_value-right_value);
                    temp = (1.0+this_error)*(term1 - term2) + this_error;
                    temp = temp*100;
                    break;
                case '*':
                    term1 = left_error + right_error + left_error*right_error;
                    temp = (1.0+this_error)*term1 + this_error;
                    temp = temp*100;
                    break;
                case '/':
                    term1 = left_error - right_error;
                    temp = (term1 + (this_error*(1 + left_error)))/(1.0+right_error);
                    temp = temp*100;
                    break;
                default:
                    temp = 0.0;
            }
        }
        else {
            temp = value.getError();
        }
        return temp;
    }
    public String getErrorEquation() {
        String temp;
        if (value.isOperator()) {
            String left        = new String(left_side.toString());
            String left_err    = new String(left_side.getErrorEquation());
            String left_p      = new String("("+left+")");
            String left_err_p  = new String("("+left_err+")");
            if (left_side.isLeaf()) {
                left_p         = new String(left);
                left_err_p     = new String(left_err);
            }
            String right       = new String(right_side.toString());
            String right_err   = new String(right_side.getErrorEquation());
            String right_p     = new String("("+right+")");
            String right_err_p = new String("("+right_err+")");
            if (right_side.isLeaf()) {
                right_p         = new String(right);
                right_err_p     = new String(right_err);
            }
            String err       = new String(value.getErrorEquation());
            String term1, term2;
            switch (value.getSign()) {
                case '+':
                    term1 = new String(
                        left_p+"*"+left_err_p+"/("+left+"+"+right+")");
                    term2 = new String(
                        right_p+"*"+right_err_p+"/("+left+"+"+right+")");
                    temp = new String((term1+"+"+term2+"+"+err));
                    break;
                case '-':
                    term1 = new String(
                        left_p+"*"+left_err_p+"/("+left+"-"+right+")");
                    term2 = new String(
                        right_p+"*"+right_err_p+"/("+left+"-"+right+")");
                    temp = new String((term1+"-"+term2+"+"+err));
                    break;
                case '*':
                    temp = new String((left_err+"+"+right_err+"+"+err));
                    break;
                case '/':
                    temp = new String((left_err+"-"+right_err+"+"+err));
                    break;
                default:
                    temp = new String(err);
            }
        }
        else {
            temp = new String(value.getErrorEquation());
        }
        return temp;
    }
}
