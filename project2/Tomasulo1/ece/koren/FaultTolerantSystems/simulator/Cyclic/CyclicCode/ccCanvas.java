package CyclicCode;

import java.awt.*;
import java.awt.Canvas;

/**
 * <p>Title: The Cyclic Code Simulator</p>
 * <p>Description: to show the intermediate steps in encoding and decoding, allow the use of different encoding polynomials and find out whether the selected one is a generator polynomial </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Dongha Lee
 * @version 1.0
 */

public class ccCanvas extends Canvas {
  ccStatus status;
  Dimension d;

  public ccCanvas() {
    status = null;
  }

  void init() {
  }

  boolean setstatus(ccStatus ccs) {
    if (ccs == null || !ccs.valid()) {
      return false;
    }
    else {
      status = ccs;
      return true;
    }
  }

  void setsize() {
    d = getSize();
  }

  void setstructure(String s) {
  }

  void setstatus(String s, String s1) {
  }

  boolean toosmall() {
    return d.width <= 100 || d.height <= 50;
  }

  void drawgrid() {
    drawgrid(getGraphics());
  }

  void drawgrid(Graphics g) {
    byte byte3 = 5;
    Dimension dimension = getSize();
    if (status == null || !status.valid() || toosmall())
      return;
    int i2 = (status.n - status.k - 1) + 3;
    int l = (4 * dimension.height) / 6;
    int k = (2 * dimension.height) / 6;
    int j1 = dimension.height / 6;
    int i1 = dimension.width / i2;
    int j = i1;
    byte byte0 = 10;
    byte byte1 = 11;
    byte byte2 = 20;
    byte byte4 = 50;
    int l1;
    int k1 = l1 = 10;
    if (i1 < 80)
      k1 = i1 / 8;
    if (j1 < 50)
      l1 = j1 / 5;
    drawharrow(g, k, j - i1 / 2, j, 0, byte1, k1);
    drawbox(g, j, k, byte1, byte1);
    if (status.bits != null && status.bits.length() > 0)
      drawBit(g, Color.black, status.bits.substring(0, 1), j, k, true);
    int i;
    for (i = 0; i < status.n - status.k - 1; i++) {
      if (status.gates.substring(i + 1, i + 2).equals("1")) {
        drawharrow(g, k, j + i * i1, j + i * i1 + i1 / 2, byte1, byte0, k1);
        drawplus(g, j + i * i1 + i1 / 2, k, byte0);
        drawvarrow(g, j + i * i1 + i1 / 2, l, k, 0, byte0, l1);
        drawharrow(g, k, j + i * i1 + i1 / 2, j + (i + 1) * i1, byte0, byte1,
                   k1);
      }
      else {
        drawharrow(g, k, j + i * i1, j + (i + 1) * i1, byte1, byte1, k1);
      }
      drawbox(g, j + (i + 1) * i1, k, byte1, byte1);
      if (status.bits != null && status.bits.length() > i + 1)
        drawBit(g, Color.black, status.bits.substring(i + 1, i + 2),
                j + (i + 1) * i1, k, true);
    }

    drawvarrow(g, j - i1 / 2, l, k, 0, 0, 0);
    drawplus(g, j + (i + 1) * i1, k, byte0);
    if(status.isEncoding()){
      drawharrow(g, l, j + (i + 1) * i1 + k1, j - i1 / 2, byte0, 0, 0);
      drawvarrow(g, j + (i + 1) * i1, l, k, 0, byte0, l1);
      drawharrow(g, l,  j + (i + 1) * i1 + i1 / 4,j + (i + 1) * i1 , byte1, 0, k1);
      g.drawString("Data in", j + (i + 1) * i1 + i1 / 4+5, l );
      g.drawString("Encoded out", j + (i + 1) * i1 + i1 / 4+5, k);
    }
    else{
      drawharrow(g, l, j + (i + 1) * i1 + k1+i1/4, j - i1 / 2, byte0, 0, 0);
      drawvarrow(g, j + (i + 1) * i1+i1/4, k, l, 0, 0, k1);
      drawvarrow(g, j + (i + 1) * i1, (l+k)/2 , k+l1, 0, 0, k1);
      drawharrow(g, (k+l)/2,  j + (i + 1) * i1 + i1 / 2,j + (i + 1) * i1 , byte1, 0, k1);
      g.drawString("Encoded in", j + (i + 1) * i1 + i1 / 4+5, (k+l)/2);
      g.drawString("Decoded out", j + (i + 1) * i1 + i1 / 4+5, k);
    }
    if (status.controlbits != null && status.controlbits.length() >= 4)
      drawBit(g, Color.red, status.controlbits.substring(0, 1),
              j + (i + 1) * i1 + 10, (l - j1 / 2) + 10);

    drawharrow(g, k, j + i * i1, j + (i + 1) * i1 , byte1, byte0, k1);
    drawharrow(g, k, j + (i + 1) * i1 , j + (i + 1) * i1 + i1 / 4, byte1, 0, k1);

    if (status.controlbits != null && status.controlbits.length() >= 4)
      drawBit(g, Color.blue, status.controlbits.substring(3, 4),
              j + (i + 1) * i1 + i1 / 2 + 10, k + 10);
  }

  private void drawBit(Graphics g, Color color, String s, int i, int j) {
    drawBit(g, color, s, i, j, false);
  }

  private void drawBit(Graphics g, Color color, String s, int i, int j,
                       boolean flag) {
    if (g == null || s == null || color == null)
      return;
    Color color1 = g.getColor();
    g.setColor(color);
    if (flag)
      g.drawString(s, i - 3, j + 5);
    else
      g.drawString(s, i, j);
    g.setColor(color1);
  }

  private void drawharrow(Graphics g, int i, int j, int k, int l, int i1,
                          int j1) {
    if (g == null)
      return;
    if (j < k) {
      j += l;
      k -= i1;
    }
    else {
      j -= l;
      k += i1;
    }
    g.drawLine(j, i, k, i);
    if (j1 > 0) {
      int k1 = j1;
      if (j < k)
        k1 *= -1;
      Polygon polygon = new Polygon();
      polygon.addPoint(k, i);
      polygon.addPoint(k + k1, i + j1 / 2);
      polygon.addPoint(k + k1, i - j1 / 2);
      g.fillPolygon(polygon);
    }
  }

  private void drawvarrow(Graphics g, int i, int j, int k, int l, int i1,
                          int j1) {
    if (g == null)
      return;
    if (j < k) {
      j += l;
      k -= i1;
    }
    else {
      j -= l;
      k += i1;
    }
    g.drawLine(i, j, i, k);
    if (j1 > 0) {
      int k1 = j1;
      if (j < k)
        k1 *= -1;
      Polygon polygon = new Polygon();
      polygon.addPoint(i, k);
      polygon.addPoint(i + j1 / 2, k + k1);
      polygon.addPoint(i - j1 / 2, k + k1);
      g.fillPolygon(polygon);
    }
  }
  private void drawbox(Graphics g, int i, int j, int k, int l)
  {
    drawbox(g, i, j, k, l, false);
  }

  private void drawbox(Graphics g, int i, int j, int k, int l, boolean flag) {
    if (g == null) {
      return;
    }
    else {
      if(!flag){
        g.drawRect(i - k, j - l, k * 2, l * 2);
      }else{
        Color color1 = g.getColor();
        g.setColor(Color.cyan);
        g.fillRect(i - k, j - l, k * 2, l * 2);
        g.setColor(color1);
      }
      return;
    }
  }

  private void drawplus(Graphics g, int i, int j, int k) {
    int l = k / 2 + 1;
    if (g == null) {
      return;
    }
    else {
      g.drawOval(i - k, j - k, 2 * k, 2 * k);
      g.drawLine(i - l, j, i + l, j);
      g.drawLine(i, j - l, i, j + l);
      return;
    }
  }

  private void drawandgate(Graphics g, int i, int j, int k) {
    if (g == null) {
      return;
    }
    else {
      g.drawArc(i - k, j - k / 2, k, k, 90, 180);
      g.drawRect( (i - k) + k / 2, j - k / 2, k * 2 - k / 2, k);
      return;
    }
  }

  public void paint(Graphics g) {
    drawgrid(g);
  }
}