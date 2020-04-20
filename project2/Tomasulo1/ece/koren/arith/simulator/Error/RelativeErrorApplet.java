import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultMutableTreeNode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.Enumeration;
import java.util.Stack;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class RelativeErrorApplet extends JApplet 
                            implements ActionListener {
    JPanel                 masterPanel, expressionPanel, parametersPanel;
    JPanel                 equationsPanel, titlePanel, paraPanel;
    GridBagLayout          masterLayout;
    JScrollPane            treePane, equationsPane;
    JLabel                 titleLabel,instructionLabel;
    JLabel                 expressionLabel;
    JLabel                 baseEquationCaption,errorEquationCaption;
    JLabel                 baseEquationLabel,errorEquationLabel;
    JLabel                 valueCaption,errorValueCaption;
    JLabel                 valueLabel,errorValueLabel;
    JTextField             expressionField;
    String                 expression;
    DefaultMutableTreeNode rootNode;
    DefaultTreeModel       treeModel;
    JTree                  expressionTree;
    Hashtable              valueHolder;
    Hashtable              valueFields;
    Hashtable              errorFields;
    Hashtable              parameterLabels;
    parameterFieldsListener paraListener;

    public void init() {
        paraListener = new parameterFieldsListener();
        //The text field for the arithmetic expression
        valueHolder = new Hashtable();
        valueFields = new Hashtable();
        errorFields = new Hashtable();
        parameterLabels = new Hashtable();
        valueHolder = new Hashtable();
        expression = new String("A-B*(C+D)/E");
        expressionField = new JTextField(expression,30);
        expressionField.setFocusable(true);
        expressionField.addActionListener(this);
        //A label for the expression field
        expressionLabel = new JLabel("Enter expression:");
        expressionLabel.setForeground(Color.BLUE);
        expressionLabel.setHorizontalAlignment(JLabel.LEFT);
        expressionLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        //Captions for the equation panel
        baseEquationCaption = new JLabel("Expression:");
        baseEquationCaption.setForeground(Color.BLUE);
        baseEquationCaption.setHorizontalAlignment(JLabel.LEFT);
        baseEquationCaption.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));
        errorEquationCaption = new JLabel("Relative Error Expression:");
        errorEquationCaption.setForeground(Color.BLUE);
        errorEquationCaption.setHorizontalAlignment(JLabel.LEFT);
        errorEquationCaption.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));
        valueCaption = new JLabel("Ideal Value:");
        valueCaption.setForeground(Color.BLUE);
        valueCaption.setHorizontalAlignment(JLabel.LEFT);
        valueCaption.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));
        errorValueCaption = new JLabel("Relative Error Value (%):");
        errorValueCaption.setForeground(Color.BLUE);
        errorValueCaption.setHorizontalAlignment(JLabel.LEFT);
        errorValueCaption.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));
        //Labels for the equation panel
        baseEquationLabel = new JLabel();
        baseEquationLabel.setHorizontalAlignment(JLabel.LEFT);
        baseEquationLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        errorEquationLabel = new JLabel();
        errorEquationLabel.setHorizontalAlignment(JLabel.LEFT);
        errorEquationLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        valueLabel = new JLabel();
        valueLabel.setHorizontalAlignment(JLabel.LEFT);
        valueLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        errorValueLabel = new JLabel();
        errorValueLabel.setHorizontalAlignment(JLabel.LEFT);
        errorValueLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        //The expression tree
        //refreshExpressionTree();
        rootNode = new DefaultMutableTreeNode(" ");
        treeModel = new DefaultTreeModel(rootNode);
        expressionTree = new JTree(treeModel);
        expressionTree.setFocusable(true);
        expressionTree.putClientProperty("JTree.lineStyle","Angled");
        expressionTree.addTreeSelectionListener(
                         new equationTreeSelectionListener());
        //expressionTree.setEditable(true);

        //Lay out the GUI.
        masterLayout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        //Container contentPane = getContentPane();
        masterPanel      = new JPanel();
        expressionPanel  = new JPanel();
        titlePanel       = new JPanel();
        parametersPanel  = new JPanel();
        equationsPanel   = new JPanel();
        equationsPane    = new JScrollPane(equationsPanel);
        treePane         = new JScrollPane(expressionTree);
        setContentPane(masterPanel);
        expressionPanel.setBorder(BorderFactory.createEtchedBorder());
        parametersPanel.setBorder(BorderFactory.createEtchedBorder());
        equationsPanel.setBorder(BorderFactory.createEtchedBorder());
        treePane.setBorder(BorderFactory.createEtchedBorder());

        //DEFAULTS for c
        //c.gridx = GridBagConstraints.RELATIVE;
        //c.gridy = GridBagConstraints.RELATIVE;
        //c.gridwidth  = 1;
        //c.gridheight = 1;
        //c.fill = GridBagConstraints.NONE;
        //c.ipadx = 0;
        //c.ipady = 0;
        //c.insets = nil;
        //c.anchor = GridBagConstraints.CENTER;
        //c.weightx = 0.0;
        //c.weighty = 0.0;

        // Layout Panel 0
        c.gridx      = 0;
        c.gridy      = 0;
        c.gridwidth  = 2;
        c.anchor     = GridBagConstraints.CENTER;
        c.fill       = GridBagConstraints.BOTH;
        masterLayout.setConstraints(titlePanel, c);
        masterPanel.add(titlePanel);

        // Layout Panel 1
        masterPanel.setLayout(masterLayout);
        c.gridx      = 0;
        c.gridy      = 1;
        c.gridwidth  = 1;
        c.anchor     = GridBagConstraints.NORTHWEST;
        c.fill       = GridBagConstraints.BOTH;
        masterLayout.setConstraints(expressionPanel, c);
        masterPanel.add(expressionPanel);

        // Layout Panel 2
        c.gridx      = 1;
        c.gridy      = 1;
        c.anchor     = GridBagConstraints.EAST;
        //c.gridwidth  = GridBagConstraints.REMAINDER;
        c.gridheight = 2;
        c.fill       = GridBagConstraints.BOTH;
        masterLayout.setConstraints(treePane, c);
        masterPanel.add(treePane);

        // Layout Panel 3
        c.gridx      = 0;
        c.gridy      = 2;
        c.anchor     = GridBagConstraints.NORTHWEST;
        c.gridwidth  = 1;
        c.gridheight = 2;
        c.fill       = GridBagConstraints.BOTH;
        masterLayout.setConstraints(parametersPanel, c);
        masterPanel.add(parametersPanel);

        // Layout Panel 4
        c.gridx      = 1;
        c.gridy      = 3;
        c.anchor     = GridBagConstraints.SOUTHWEST;
        c.gridwidth  = 1;
        c.gridheight = 1;
        //c.gridwidth  = GridBagConstraints.REMAINDER;
        //c.gridheight = GridBagConstraints.REMAINDER;
        c.fill       = GridBagConstraints.BOTH;
        masterLayout.setConstraints(equationsPane, c);
        masterPanel.add(equationsPane);

        // Layout Title Panel
        GridBagLayout layout = new GridBagLayout();
        c = new GridBagConstraints();
        titlePanel.setLayout(layout);
        c.gridx      = 0;
        c.gridy      = 0;
        c.weightx    = 1.0;
        c.anchor     = GridBagConstraints.NORTHWEST;
        c.fill       = GridBagConstraints.BOTH;
        titleLabel   = new JLabel("Relative Error Calculator");
        Font font = titleLabel.getFont();
        titleLabel.setHorizontalAlignment(JLabel.LEFT);
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        titleLabel.setFont(new Font(font.getName(),font.getStyle(),
                    font.getSize()*2));
        layout.setConstraints(titleLabel, c);
        titlePanel.add(titleLabel);
        c.gridx      = 1;
        c.gridy      = 1;
        c.anchor = GridBagConstraints.SOUTHEAST;
        instructionLabel = new JLabel("Press <enter> after updating fields");
        instructionLabel.setForeground(Color.RED);
        instructionLabel.setHorizontalAlignment(JLabel.RIGHT);
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        layout.setConstraints(instructionLabel, c);
        titlePanel.add(instructionLabel);

        // Layout Expression Panel
        expressionPanel.setLayout(new BoxLayout(expressionPanel,
                                      BoxLayout.Y_AXIS));
        expressionPanel.add(expressionLabel);
        expressionPanel.add(expressionField);

        // Layout Tree Panel
        Dimension minimumSize = new Dimension(150, 200);
        treePane.setMinimumSize(minimumSize);

        // Layout Equations Panel
        equationsPanel.setLayout(new BoxLayout(equationsPanel,
                                      BoxLayout.Y_AXIS));
        equationsPanel.add(baseEquationCaption);
        equationsPanel.add(baseEquationLabel);
        equationsPanel.add(errorEquationCaption);
        equationsPanel.add(errorEquationLabel);
        equationsPanel.add(valueCaption);
        equationsPanel.add(valueLabel);
        equationsPanel.add(errorValueCaption);
        equationsPanel.add(errorValueLabel);

        // Layout Equations Pane
        minimumSize = new Dimension(250, 220);
        equationsPane.setMinimumSize(minimumSize);
    }

    //User clicked either the next or the previous button.
    public void actionPerformed(ActionEvent e) {
//If no change, need to add code to skip update
        // Check the new expression
        StringBuffer new_exp = new StringBuffer(expressionField.getText());
        int i=0;
        while(i<new_exp.length()) {
            char c = new_exp.charAt(i);
            if (Character.isWhitespace(c))
                new_exp.deleteCharAt(i);
            else if (!Character.isLetterOrDigit(c))
                if ((c=='+')||(c=='-')||(c=='*')||
                    (c=='/')||(c=='(')||(c==')'))
                    i++;
                else //INVALID CHARACTER IN EXPRESSION
                    i++;
            else
                i++;
        }
        expression = new String(new_exp.toString());
        refreshExpressionTree();
        refreshExpressionPanel();
        expressionTree.requestFocus();
        expressionTree.setSelectionRow(0);
        expressionTree.validate();
        expressionTree.repaint();
        titlePanel.validate();
        titlePanel.repaint();
        masterPanel.validate();
        masterPanel.repaint();
    }

    private void refreshExpressionTree() {
        HashSet prec1ops = new HashSet();
        prec1ops.add("+"); prec1ops.add("-");
        HashSet prec2ops = new HashSet();
        prec2ops.add("*"); prec2ops.add("/");
        HashSet prec3ops = new HashSet();
        prec3ops.add("("); prec3ops.add(")");

        StringTokenizer pars = new StringTokenizer(expression,"+-*/()",true);
        HashSet new_keys = new HashSet();
        Stack operator_stack = new Stack();
        LinkedList postfix = new LinkedList();

        while (pars.hasMoreElements()) {
            String next = pars.nextToken();
            if (!prec3ops.contains(next)) {
                new_keys.add(next);
                if (!valueHolder.containsKey(next)) {
                    ExpressionValue val;
                    if (prec1ops.contains(next)||prec2ops.contains(next))
                        val = new ExpressionValue(next.charAt(0));
                    else
                        val = new ExpressionValue(next);
                    valueHolder.put(next,val);
                }
            }
            // Perform infix to postfix translation
            if (!(prec1ops.contains(next)||prec2ops.contains(next)||
                  prec3ops.contains(next)))
                postfix.addLast(next);
            else if (prec3ops.contains(next))
                if (next.equals("("))
                    operator_stack.push(next);
                else {
                    String popper = (String)operator_stack.pop();
                    while (!popper.equals("(")) {
                        postfix.addLast(popper);
                        popper = (String)operator_stack.pop();
                    }
                }
            else if (prec1ops.contains(next)) {
                if (!operator_stack.empty()) {
                    String popper = (String)operator_stack.peek();
                    while (!operator_stack.empty()&&
                     (prec1ops.contains(popper)||prec2ops.contains(popper))) {
                        postfix.addLast((String)operator_stack.pop());
                        if (!operator_stack.empty())
                            popper = (String)operator_stack.peek();
                    }
                }
                operator_stack.push(next);
            }
            else if (prec2ops.contains(next)) {
                if (!operator_stack.empty()) {
                    String popper = (String)operator_stack.peek();
                    while (!operator_stack.empty()&&prec2ops.contains(popper)) {
                        postfix.addLast((String)operator_stack.pop());
                        if (!operator_stack.empty())
                            popper = (String)operator_stack.peek();
                    }
                }
                operator_stack.push(next);
            }
        }
        while (!operator_stack.empty())
            postfix.addLast((String)operator_stack.pop());
        // Now convert the postfix expression to a tree
        int index = 0;
        ExpressionNode treetop;
        ExpressionNode right_branch;
        ExpressionNode left_branch;
        Stack branches = new Stack();

        while (postfix.size()>1) {
            String current = (String)postfix.get(index);
            if (prec1ops.contains(current)||prec2ops.contains(current)) {
                String oper  = (String)postfix.remove(index);
                String termB = (String)postfix.remove(index-1);
                String termA = (String)postfix.remove(index-2);
                if ((termB.charAt(0)=='$')&&(termA.charAt(0)=='$')) {
                    right_branch = (ExpressionNode)branches.pop();
                    left_branch  = (ExpressionNode)branches.pop();
                    treetop      = new ExpressionNode(
                                      (ExpressionValue)valueHolder.get(oper),
                                       left_branch, right_branch);
                    branches.push(treetop);
                    postfix.add(index-2,"$");
                    index = index - 1;
                }
                else if (termB.charAt(0)=='$') {
                    right_branch = (ExpressionNode)branches.pop();
                    left_branch  = new ExpressionNode(
                                      (ExpressionValue)valueHolder.get(termA));
                    treetop      = new ExpressionNode(
                                      (ExpressionValue)valueHolder.get(oper),
                                       left_branch, right_branch);
                    branches.push(treetop);
                    postfix.add(index-2,"$");
                    index = index - 1;
                }
                else if (termA.charAt(0)=='$') {
                    right_branch = new ExpressionNode(
                                      (ExpressionValue)valueHolder.get(termB));
                    left_branch  = (ExpressionNode)branches.pop();
                    treetop      = new ExpressionNode(
                                      (ExpressionValue)valueHolder.get(oper),
                                       left_branch, right_branch);
                    branches.push(treetop);
                    postfix.add(index-2,"$");
                    index = index - 1;
                }
                else {
                    right_branch = new ExpressionNode(
                                      (ExpressionValue)valueHolder.get(termB));
                    left_branch  = new ExpressionNode(
                                      (ExpressionValue)valueHolder.get(termA));
                    treetop      = new ExpressionNode(
                                      (ExpressionValue)valueHolder.get(oper),
                                       left_branch, right_branch);
                    branches.push(treetop);
                    postfix.add(index-2,"$");
                    index = index - 1;
                }
            }
            else
                index++;
        }
        treetop = (ExpressionNode)branches.pop();

        rootNode.removeAllChildren();
        rootNode.setUserObject(treetop);
        BuildLeftBranch(rootNode, treetop);
        BuildRightBranch(rootNode, treetop);
        //treeModel = new DefaultTreeModel(rootNode);
        //treeModel.addTreeModelListener(new MyTreeModelListener());
        //expressionTree = new JTree(treeModel);
        //expressionTree.setEditable(true);
        //treePane         = new JScrollPane(expressionTree);
        treeModel.reload();

        HashSet old_keys = new HashSet((Collection)(valueHolder.keySet()));
        Iterator iter = old_keys.iterator();

        while(iter.hasNext()) {
            String old_key_member = (String)iter.next();
            if (!(new_keys.contains(old_key_member))&&
                  valueHolder.containsKey(old_key_member))
                valueHolder.remove(old_key_member);
        }
        for (int i = 0; i < expressionTree.getRowCount(); i++)
            expressionTree.expandRow(i);
        expressionPanel.validate();
        expressionPanel.repaint();
    }

    void BuildLeftBranch(DefaultMutableTreeNode dmtn, ExpressionNode en) {
        ExpressionNode left_branch = en.getLeftSide();
        DefaultMutableTreeNode left = new DefaultMutableTreeNode(left_branch);
        dmtn.add(left);
        if (left_branch.isOperator()) {
            BuildLeftBranch(left, left_branch);
            BuildRightBranch(left, left_branch);
        }
    }
    void BuildRightBranch(DefaultMutableTreeNode dmtn, ExpressionNode en) {
        ExpressionNode right_branch  = en.getRightSide();
        DefaultMutableTreeNode right = new DefaultMutableTreeNode(right_branch);
        dmtn.add(right);
        if (right_branch.isOperator()) {
            BuildLeftBranch(right, right_branch);
            BuildRightBranch(right, right_branch);
        }
    }

    void refreshExpressionPanel() {
        DecimalFormat dec_format;
        dec_format = (DecimalFormat)NumberFormat.getNumberInstance();
       
        HashSet new_keys = new HashSet((Collection)(valueHolder.keySet()));
        Iterator iter_new_keys = new_keys.iterator();

        while (iter_new_keys.hasNext()) {
            String next = (String)iter_new_keys.next();
            if (!errorFields.containsKey(next)) {
                ExpressionValue node = (ExpressionValue)valueHolder.get(next);
                DecimalField field =
                    new DecimalField(node.getError(),5, dec_format);
                field.addActionListener(paraListener);
                errorFields.put(next,field);
                if (!node.isOperator()) {
                    DecimalField val_field =
                        new DecimalField(node.getValue(),5, dec_format);
                    val_field.addActionListener(paraListener);
                    valueFields.put(next,val_field);
                }
            }
        }

        HashSet old_error_values = new HashSet(
                               (Collection)(errorFields.keySet()));
        Iterator iter_old_keys = old_error_values.iterator();
        while(iter_old_keys.hasNext()) {
            String old_key = (String)iter_old_keys.next();
            if (!valueHolder.containsKey(old_key)) {
                errorFields.remove(old_key);
                if (valueFields.containsKey(old_key))
                    valueFields.remove(old_key);
            }
        }

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        paraPanel  = new JPanel();
        paraPanel.setLayout(layout);
        LinkedList keys = new LinkedList((Collection)(errorFields.keySet()));
        // Sort alphabetically
        Collections.sort(keys, new Comparator() {
            public int compare(Object o1, Object o2) {
	             return (((String)o1).compareTo((String)o2));
            }});
        Iterator iter_keys = keys.iterator();
        // Variable Column Label
        //JLabel label = new JLabel("Term");
        JLabel label = new JLabel("");
        label.setForeground(Color.BLUE);
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridheight = 2;
        c.gridwidth  = 1;
        layout.setConstraints(label, c);
        paraPanel.add(label);
        // Error Column Label
        //label = new JLabel("Relative Error");
        label = new JLabel("Relative");
        label.setForeground(Color.BLUE);
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        layout.setConstraints(label, c);
        paraPanel.add(label);
        label = new JLabel("Error (%)");
        label.setForeground(Color.BLUE);
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        c.gridy = 1;
        layout.setConstraints(label, c);
        paraPanel.add(label);
        // Value Column Label
        label = new JLabel("Numeric");
        label.setForeground(Color.BLUE);
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        c.gridx = 2;
        c.gridy = 0;
        layout.setConstraints(label, c);
        paraPanel.add(label);
        label = new JLabel("Value");
        label.setForeground(Color.BLUE);
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        c.gridy = 1;
        layout.setConstraints(label, c);
        paraPanel.add(label);
        int i=2;
        while (iter_keys.hasNext()) {
            String next = (String)iter_keys.next();
            ExpressionValue node = (ExpressionValue)valueHolder.get(next);
            // Parameter Label
            label = new JLabel(next);
            label.setForeground(Color.RED);
            label.setHorizontalAlignment(JLabel.RIGHT);
            label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));
            c.gridx = 0;
            c.gridy = i;
            c.anchor = GridBagConstraints.EAST;
            c.gridheight = 1;
            c.gridwidth  = 1;
            layout.setConstraints(label, c);
            paraPanel.add(label);
            // Error Field
            DecimalField err_field = (DecimalField)errorFields.get(next);
            err_field.setFocusable(true);
            c.gridx = 1;
            c.anchor = GridBagConstraints.WEST;
            layout.setConstraints(err_field, c);
            paraPanel.add(err_field);
            if (!node.isOperator()) {
            // Value Field
                DecimalField val_field = (DecimalField)valueFields.get(next);
                val_field.setFocusable(true);
                c.gridx = 2;
                layout.setConstraints(val_field, c);
                paraPanel.add(val_field);
            }
            i=i+1;
        }
        parametersPanel.removeAll();
        parametersPanel.add(paraPanel, BorderLayout.WEST);
        parametersPanel.validate();
        parametersPanel.repaint();
    }
    
    class parameterFieldsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            DecimalField source = (DecimalField)e.getSource();
            String text = source.getText();

            HashSet keys = new HashSet((Collection)(errorFields.keySet()));
            Iterator iter_keys = keys.iterator();
            while (iter_keys.hasNext()) {
                String next = (String)iter_keys.next();
                DecimalField match1 = (DecimalField)errorFields.get(next);
                ExpressionValue node = (ExpressionValue)valueHolder.get(next);
                node.setError(match1.getValue());
                if (valueFields.containsKey(next)) {
                    DecimalField match2 = (DecimalField)valueFields.get(next);
                    node.setValue(match2.getValue());
                }
            }
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                          expressionTree.getLastSelectedPathComponent();
            if (node==null) return;

            ExpressionNode ex_node = (ExpressionNode)node.getUserObject();
            baseEquationLabel.setText(ex_node.toString());
            errorEquationLabel.setText(ex_node.getErrorEquation());
            valueLabel.setText((String)ex_node.getValueString());
            errorValueLabel.setText((String)ex_node.getErrorString());
        }
    }

    class equationTreeSelectionListener implements TreeSelectionListener {
        public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                          expressionTree.getLastSelectedPathComponent();
            if (node==null) return;

            ExpressionNode ex_node = (ExpressionNode)node.getUserObject();

            baseEquationLabel.setText(ex_node.toString());
            errorEquationLabel.setText(ex_node.getErrorEquation());
            valueLabel.setText((String)ex_node.getValueString());
            errorValueLabel.setText((String)ex_node.getErrorString());
        }
    }
}

