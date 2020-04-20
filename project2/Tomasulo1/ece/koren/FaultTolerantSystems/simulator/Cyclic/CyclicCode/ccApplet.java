package CyclicCode;

import java.applet.Applet;
import java.awt.*;
import java.io.*;

/**
 * <p>Title: The Cyclic Code Simulator</p>
 * <p>Description: to show the intermediate steps in encoding and decoding, allow the use of different encoding polynomials and find out whether the selected one is a generator polynomial </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Dongha Lee
 * @version 1.0
 */
import java.net.*;
public class ccApplet extends Applet {
  public ccApplet(){
    ccCalc t = new ccCalc();
    InputStream is = null;
    try {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        is = new URL(getDocumentBase(), "test").openStream();
    } catch(Exception e) {
    }
    try {
        if (is != null)
            is.close();
    } catch(Exception e) {
    }

    /*inAnApplet = true;
    complete = false;
    input_not_refreshed = false;
    poly_not_refreshed = false;
    welcome_string = "Welcome to the Cyclic Code Encoder/Decoder simulator.";
    step = 0;*/
  }

  public static void main(String[] args)  {
    /*ccApplet ccApp = new ccApplet();
    ccApp.inAnApplet = false;
    ccApp.init();
    ccApp.start();*/
  }

  public void init() {
    status = new ccStatus();
    status.init(9, 7, "111", "1100101", 0);
    step = 0;
    but_encode = new Button("Encode");
    but_encode.setBackground(Color.orange);
    but_reset = new Button("Reset");
    but_reset.setBackground(Color.orange);
    but_step = new Button("Step Run");
    but_step.setBackground(Color.orange);
    but_run = new Button("Run");
    but_check = new Button("Check Polynomial");
    label_poly = new Label("Polynomial (n-k+1):");
    label_k = new Label("Input bits (k):");
    label_n = new Label("Code bits (n):");
    tf_poly = new TextField("1+x^1+x^2", 10);
    tf_k = new TextField("" + status.k, 5);
    tf_n = new Label("" + status.n);
    label_input = new Label("   Input:");
    label_inputout = new Label("   Input:");
    label_output = new Label("   Output:");
    tf_input = new TextField(status.input, 20);
    can_inputout = new TextField("", 20);
    can_output = new TextField("", 20);
    can_output = new TextField("", 20);
    can_poly = new TextField("", 10);
    can_reminder = new TextField("",10);
    can_inputout.setEditable(false);
    can_output.setEditable(false);
    can_reminder.setEditable(false);
    can_poly.setEditable(false);
    message = new Label(welcome_string);
    message.setAlignment(message.CENTER);
    message.setForeground(Color.blue);
    quit = new Button("Quit");
    GridBagLayout gridbaglayout = new GridBagLayout();
    GridBagConstraints gridbagconstraints = new GridBagConstraints();
    Panel panel = new Panel();
    panel.setLayout(gridbaglayout);
    gridbagconstraints.fill = 1;
    gridbagconstraints.gridwidth = 2;
    gridbagconstraints.weighty = 0.0D;
    gridbagconstraints.weightx = 0.5D;
    gridbaglayout.setConstraints(but_encode, gridbagconstraints);
    panel.add(but_encode);
    gridbaglayout.setConstraints(but_check, gridbagconstraints);
    panel.add(but_check);
    gridbagconstraints.gridwidth = 1;
    gridbaglayout.setConstraints(but_reset, gridbagconstraints);
    panel.add(but_reset);
    gridbagconstraints.gridwidth = 1;
    gridbaglayout.setConstraints(but_run, gridbagconstraints);
    panel.add(but_run);
    gridbagconstraints.gridwidth = 0;
    gridbaglayout.setConstraints(but_step, gridbagconstraints);
    panel.add(but_step);
    gridbagconstraints.gridwidth = 2;
    gridbaglayout.setConstraints(label_poly, gridbagconstraints);
    panel.add(label_poly);
    gridbagconstraints.gridwidth = 1;
    gridbaglayout.setConstraints(tf_poly, gridbagconstraints);
    panel.add(tf_poly);
    gridbaglayout.setConstraints(can_poly, gridbagconstraints);
    panel.add(can_poly);
    gridbagconstraints.gridwidth = 2;
    gridbaglayout.setConstraints(label_input, gridbagconstraints);
    panel.add(label_input);
    gridbagconstraints.gridwidth = 0;
    gridbaglayout.setConstraints(tf_input, gridbagconstraints);
    panel.add(tf_input);
    gridbagconstraints.gridwidth = 2;
    gridbaglayout.setConstraints(label_k, gridbagconstraints);
    panel.add(label_k);
    gridbaglayout.setConstraints(tf_k, gridbagconstraints);
    panel.add(tf_k);
    gridbagconstraints.gridwidth = 2;
    gridbaglayout.setConstraints(label_inputout, gridbagconstraints);
    panel.add(label_inputout);
    gridbagconstraints.gridwidth = 0;
    gridbaglayout.setConstraints(can_inputout, gridbagconstraints);
    panel.add(can_inputout);
    gridbagconstraints.gridwidth = 2;
    gridbaglayout.setConstraints(label_n, gridbagconstraints);
    panel.add(label_n);
    gridbaglayout.setConstraints(tf_n, gridbagconstraints);
    panel.add(tf_n);
    gridbagconstraints.gridwidth = 2;
    gridbaglayout.setConstraints(label_output, gridbagconstraints);
    panel.add(label_output);
    gridbaglayout.setConstraints(can_output, gridbagconstraints);
    panel.add(can_output);
    gridbagconstraints.gridwidth = 0;
    gridbaglayout.setConstraints(can_reminder, gridbagconstraints);
    panel.add(can_reminder);
    Label label3 = new Label();
    gridbaglayout.setConstraints(label3, gridbagconstraints);
    panel.add(label3);
    gridbaglayout.setConstraints(message, gridbagconstraints);
    panel.add(message);
    canvas = new ccCanvas();
    setLayout(new BorderLayout());
    add("Center", canvas);
    add("South", panel);
    canvas.setstatus(status);
    display();
  }

  public boolean keyDown(Event event, int i) {
    if (event.target == tf_input){
      switch (i) {
        case 48: // '0'
        case 49: // '1'
          input_not_refreshed = true;
          return super.keyDown(event, i);

        case 10: // '\n'
          input_not_refreshed = false;
          return super.keyDown(event, i);
      }
      input_not_refreshed = true;
      if (i < 48 || i > 122) {
        return super.keyDown(event, i);
      }
      else {
        output("The input string may only consist of bit values (0 or 1).");
        return true;
      }
    }
    else if(event.target == tf_poly)
    {
      switch (i) {
        case 10: // '\n'
          poly_not_refreshed = false;
          return super.keyDown(event, i);
      }
      poly_not_refreshed = true;
      return super.keyDown(event, i);
    }
    else return super.keyDown(event, i);
  }

  public boolean action(Event event, Object obj) {
    if (event.target == tf_input || input_not_refreshed) {
      String s = tf_input.getText();
      for (int i1 = 0; i1 < s.length(); i1++)
        if (!s.substring(i1, i1 + 1).equals("0") &&
            !s.substring(i1, i1 + 1).equals("1")) {
          output("Illegal characters in input string.");
          tf_input.setText(status.input);
          input_not_refreshed = false;
          return true;
        }

      int k1 = status.encode != 0 ? status.n : status.k;
      if (s.length() % k1 == 0) {
        status.input = s;
        restart();
        output("Input string changed successfully.");
      }
      else {
        status.input = correct_input_string_length(s);
        tf_input.setText(status.input);
        restart();
        if (status.encode == 0)
          output("Input string modified to match number of input bits 'k'.");
        else
          output("Input string modified to match number of code bits 'n'.");
      }
      input_not_refreshed = false;
      if (event.target == tf_input)
        return true;
    }
    if (event.target == tf_poly || poly_not_refreshed) {
      poly_not_refreshed = false;
      String s1 = parse_poly(tf_poly.getText());
      if (s1 != null) {
        int j1 = status.k;
        String s3 = status.input;
        if (status.encode == 1)
          s3 = correct_input_string_length(s3, j1, s1.length());
        status.init( (s1.length() + j1) - 1, j1, s1, s3, status.encode);
        tf_input.setText(status.input);
        tf_n.setText(String.valueOf(status.n));
        restart();
        but_encode.setEnabled(true);
        but_reset.setEnabled(true);
        but_step.setEnabled(true);
        tf_k.setEnabled(true);
        tf_input.setEnabled(true);
      }
      else {
        but_encode.setEnabled(false);
        but_reset.setEnabled(false);
        but_step.setEnabled(false);
        tf_k.setEnabled(false);
        tf_input.setEnabled(false);
      }
      if (event.target == tf_poly)
        return true;
    }
    if (event.target == quit) {
      System.exit(0);
      return true;
    }
    if (event.target == tf_k) {
      int k = status.k;
      try {
        k = Integer.valueOf(tf_k.getText()).intValue();
        if (k > 64 || k < 1)
          throw new NumberFormatException();
      }
      catch (NumberFormatException numberformatexception) {
        tf_k.setText(String.valueOf(status.k));
        output("Error, value for K out of range or non-numeric.");
        return true;
      }
      String s2 = correct_input_string_length(status.input, k);
      status.init( (status.gates.length() + k) - 1, k, status.gates, s2,
                  status.encode);
      tf_n.setText("" + status.n);
      tf_k.setText("" + status.k);
      tf_input.setText(status.input);
      restart();
      output("Number of input bits set. Input string be modified accordingly.");
      return true;
    }
    if (event.target == but_encode) {
      if (status.valid()) {
        if (status.encode == 0) {
          but_encode.setLabel("Decode");
          label_input.setText("   Encoded:");
          status.encode = 1;
          status.input = correct_input_string_length(status.input);
          int l = can_output.getText().length();
          if (complete && l > 0 && l % status.n == 0)
            status.input = correct_input_string_length(can_output.getText());
          else
            status.input = correct_input_string_length(status.input);
        }
        else {
          but_encode.setLabel("Encode");
          label_input.setText("   Input:");
          status.encode = 0;
          status.input = correct_input_string_length(status.input);
        }
      }
      else {
        output("Status error.");
      }
      tf_input.setText(status.input);
      restart();
      if (status.valid())
        output("Mode changed to " + but_encode.getLabel() +
               ". Simulation restarting.");
      return true;
    }
    if (event.target == but_check) {
      String op;
      int e = status.encode, j;
      int k = status.k;
      int n = status.n;
      status.encode = 1;
      String s2 = correct_input_string_length("", k+1);
      s2 = "1" + s2.substring(0,k-1) + "1";
      status.init( (status.gates.length() + k+1) - 1, k+1, status.gates, s2,
                  status.encode);
      restart();
      do{
        j = step;
        step = status.calculate(step + 1, true);
        if(j==step){ complete = true; break; }
      }while(true);
      if(status.output.substring(0,status.n-status.k).indexOf("1")>=0){
        op = "This is NOT a Generator Polynomial!";
      }else{
        op = "This is a Generator Polynomial!";
      }
      status.k = k;
      status.n = n;
      status.encode = e;
      status.init( (status.gates.length() + k) - 1, k, status.gates, tf_input.getText(),
                  status.encode);
      restart();
      output(op);
      return true;
    }
    if (event.target == but_reset) {
      restart();
      return true;
    }
    if (event.target == but_run) {
      int j;
      restart();
      do{
        j = step;
        step = status.calculate(step + 1, true);
        if(j==step){ complete = true; break; }
      }while(true);
      display();
      output("Simulation complete.");
      complete = true;
      if(status.encode==1){
        if(status.output.substring(0,status.n-status.k).indexOf("1")>=0){
          label_output.setText("   Output:   Fault!");
          label_output.setForeground(Color.red);
        }else{
          label_output.setText("   Output:   No Fault!");
          label_output.setForeground(Color.blue);
        }
      }
      return true;
    }
    if (event.target == but_step) {
      int j = step;
      if(!complete){
        step = status.calculate(step + 1, true);
      }
      if (j == step) {
        output("Simulation complete.");
        complete = true;
        if(status.encode==1){
          if(status.output.substring(0,status.n-status.k).indexOf("1")>=0){
            label_output.setText("   Output:  Fault detected!");
            label_output.setForeground(Color.red);
          }else{
            label_output.setText("   Output:  No Fault!");
            label_output.setForeground(Color.blue);
          }
        }
      }
      else {
        complete = false;
      }
      display();
      return true;
    }
    else {
      return false;
    }
  }

  String correct_input_string_length(String s) {
    return correct_input_string_length(s, status.k, status.gates.length());
  }

  String correct_input_string_length(String s, int i) {
    return correct_input_string_length(s, i, status.gates.length());
  }

  String correct_input_string_length(String s, int i, int j) {
    int k = status.encode != 0 ? (j + i) - 1 : i;
    if (s.length() > k) {
      s = s.substring(0, k * (s.length() / k));
    }
    else {
      for (int l = s.length(); l < k; l++)
        s = s + "0";

    }
    return s;
  }

  void display() {
    if(status.encode==0 || !complete){
      can_reminder.setText("");
      can_output.setText(status.output);
    }else{
      can_output.setText(status.output.substring(status.n-status.k));
      can_reminder.setText(status.output.substring(0,status.n-status.k));
    }
    can_poly.setText(status.gates);
    can_inputout.setText(status.inputout);
    canvas.repaint();
    if (status.message.length() > 0)
      output(status.message);
  }

  void restart() {
    if (status.valid()) {
      step = 0;
      complete = false;
      status.bits = "";
      status.inputout = "";
      status.output = "";
      label_output.setText("   Output:");
      label_output.setForeground(Color.black);
      display();
      output(welcome_string);
    }
    else {
      output("Status invalid.");
    }
  }

  void output(String s) {
    if (message == null) {
      return;
    }
    else {
      message.setText(s);
      return;
    }
  }

  void output(String s, Color c) {
    if (message == null) {
      return;
    }
    else {
      Color sc = message.getForeground();
      message.setForeground(Color.red );
      message.setText(s);
      message.setForeground(sc);
      return;
    }
  }


  String parse_poly(String s) {
    String s1 = "";
    int j = 0;
    int k = 0;
    int l = 0;
    try {
      for (int i = 0; i < s.length(); i++) {
        String s3 = s.substring(i, i + 1);
        if (l == 0) {
          if (!s3.equals(" "))
            if (s3.equals("1")) {
              s1 = "1" + s1;
              l++;
            }
            else {
              throw new StringIndexOutOfBoundsException();
            }
        }
        else {
          switch (k) {
            default:
              break;

            case 0: // '\0'
            case 4: // '\004'
              if (s3.equals(" ") || s3.equals("+"))
                break;
              if (s3.equals("0") || s3.equals("1")) {
                s1 = s3 + s1;
                l++;
                k = 0;
                break;
              }
              if (s3.equals("x") || s3.equals("X"))
                k = 1;
              else
                throw new StringIndexOutOfBoundsException();
              break;

            case 2: // '\002'
              if (s3.equals("*")) {
                k = 3;
                j = 0;
              }
              else {
                throw new StringIndexOutOfBoundsException();
              }
              break;

            case 1: // '\001'
              j = 0;
              if (s3.equals(" "))
                break;
              if (s3.equals("^")) {
                k = 3;
                break;
              }
              if (s3.equals("*")) {
                k = 2;
                break;
              }
              if (s3.equals("+") && l == 1) {
                s1 = "1" + s1;
                k = 0;
                l = 2;
                break;
              }// fall through

            case 3: // '\003'
              if (s3.equals(" "))
                break;
              int i1;
              try {
                i1 = Integer.valueOf(s3).intValue();
              }
              catch (NumberFormatException numberformatexception) {
                i1 = -1;
              }
              if (i1 >= 0) {
                j = 10 * j + i1;
                k = 3;
                break;
              }
              if (s3.equals(" ") || s3.equals("+")) {
                if (s3.equals(" "))
                  k = 0;
                if (s3.equals("+"))
                  k = 4;
                if (j < l)
                  throw new StringIndexOutOfBoundsException();
                for (int k1 = l; k1 < j; k1++){
                  s1 = "0" + s1;
                }
                s1 = "1" + s1;
                l = j + 1;
                j = 0;
              }
              else {
                throw new StringIndexOutOfBoundsException();
              }
              break;
          }
        }
      }

      if (k == 1 && l == 1) {
        s1 = "1" + s1;
        k = 0;
        l = 2;
      }
      if (k == 3) {
        if (j < l)
          throw new StringIndexOutOfBoundsException();
        for (int j1 = l; j1 < j; j1++){
          s1 = "0" + s1;
        }
        s1 = "1" + s1;
        l = j + 1;
        j = 0;
      }
      else
      if (k != 0)
        throw new StringIndexOutOfBoundsException();
      if (l > 33 || l < 2)
        throw new StringIndexOutOfBoundsException();
    }
    catch (StringIndexOutOfBoundsException stringindexoutofboundsexception) {
      output("General polynomial error. Example form: \"1+x+x^3\".");
      return null;
    }
    return s1;
  }

  public void start() {
    canvas.setsize();
    output(welcome_string);
  }

  public void stop() {
    output("Goodbye for today.");
  }

  ccCanvas canvas;
  ccStatus status;
  boolean inAnApplet;
  boolean complete;
  boolean input_not_refreshed;
  boolean poly_not_refreshed;
  Label label_poly;
  Label label_k;
  Label label_n;
  TextField tf_poly;
  TextField tf_k;
  Label tf_n;
  TextField tf_input;
  TextField can_inputout;
  TextField can_output;
  TextField can_poly;
  Label label_input;
  Label label_inputout;
  Label label_output;
  TextField can_reminder;
  String welcome_string;
  Label message;
  Label copy;
  Button quit;
  Button but_encode;
  Button but_reset;
  Button but_step;
  Button but_run;
  Button but_check;
  int step;
  private static final int MAX_K = 64;
  private static final int MAX_ORDER = 33;
}