
import java.io.*;
import java.util.*;

public class test {
    public static HashMap fRegister = new HashMap();
    public static void main(String[] args) {
        fRegister.put("F1", 1);
        fRegister.put("F2", 1);
        System.out.println(fRegister.get("F2"));
        fRegister.put("F"+2, "ddd");
        System.out.println(fRegister.size());
        String r=fRegister.get("F2").toString();
        System.out.println(r);
    }
}