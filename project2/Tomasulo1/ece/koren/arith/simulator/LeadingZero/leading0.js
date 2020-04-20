// ***************************************************************
// ***************************************************************

function LeadingZero(Struct1, Struct2, Struct3, DetailsOn) {

   if(DetailsOn)
       parent.frames[1].document.writeln("<PRE>");


   // Is A = 0 ?
   if (Struct1.ZeroF || Struct1.DenF){
      Struct1.f = Struct1.f;
   }else{
      Struct1.f = Struct1.f + 1;
   }

   // Is B = 0 ?
   if (Struct2.ZeroF || Struct2.DenF){
      Struct2.f = Struct2.f;
   }else{
      Struct2.f = Struct2.f + 1;
   }

//    if(DetailsOn)
    if(0)
    {
        parent.frames[1].document.writeln("<PRE>");
        parent.frames[1].document.writeln("<CENTER><H4>H. Suzuki,H. Morinaka,H. Makino, Y. Nakase, K. Mashiko, T. Sumi,</H4></CENTER>");
        parent.frames[1].document.writeln("<CENTER><H4>\"Leading-zero anticipatory logic for high-speed floating point addition\"</H4></CENTER>");
        parent.frames[1].document.writeln("<CENTER><H5> IEEE Journal of Solid-State Circuits, Volume: 31 , Issue: 8 , Aug. 1996</H5></CENTER>");

        PrintHR();
        parent.frames[1].document.writeln("</PRE>");

    }
    else if(DetailsOn)
    {
        parent.frames[1].document.writeln("<PRE>");
        parent.frames[1].document.writeln("<CENTER><H4>Algorithm 1</H4></CENTER>");

        PrintHR();
        parent.frames[1].document.writeln("</PRE>");

    }

    if(DetailsOn)
    {
        if (Struct1.DenF){
            PrintIEEENum("A    ",Struct1.IEEE + " (Denorm.)", Struct1.Sign, Struct1.f, Struct1.e, bits);
        } else {
            PrintIEEENum("A    ",Struct1.IEEE, Struct1.Sign, Struct1.f, Struct1.e, bits);
        }

        if (Struct2.DenF){
            PrintIEEENum("B    ",Struct2.IEEE + " (Denorm.)", Struct2.Sign, Struct2.f, Struct2.e, bits);
        } else {
            PrintIEEENum("B    ",Struct2.IEEE, Struct2.Sign, Struct2.f, Struct2.e, bits);
        }

        PrintHR();
    }

    e1 = Struct1.e; f1 = Struct1.f;
    e2 = Struct2.e; f2 = Struct2.f;
    // Alignment Step
    if (e1 > e2) {
        bitsDiff = (e1 - e2);
        f2 = f2 / Math.pow(2,bitsDiff);
        e2 = e1;
    } else {
        if (e1 < e2) {
            bitsDiff = (e2 - e1);
            f1 = f1 / Math.pow(2,bitsDiff);
            e1 = e2;
        } else {
            bitsDiff=0;
        }
    }

    Fullf1 = f1;
    Fullf2 = f2;

    f1 = TruncateToBits(f1,bits);
    f2 = TruncateToBits(f2,bits);
    Extra1 = (Fullf1 - f1)*Math.pow(2,bits);
    Extra2 = (Fullf2 - f2)*Math.pow(2,bits);

    //*************************************************
    // Find G, R, S for A and B
    //*************************************************

    Struct1.G = 0;
    if (Extra1 >=0.5 ){
        Struct1.G = 1;
        Extra1 = Extra1 - 0.5;
    }

    Struct2.G = 0;
    if (Extra2 >=0.5 ){
        Struct2.G = 1;
        Extra2 = Extra2 - 0.5;
    }

    Struct1.R = 0;
    if (Extra1 >= 0.25 ){
        Struct1.R = 1;
        Extra1 = Extra1 - 0.25;
    }

    Struct2.R = 0;
    if (Extra2 >= 0.25){
        Struct2.R = 1;
        Extra2 = Extra2 - 0.25;
    }

    Struct1.S = 0;
    if (Extra1 > 0 ){
        Struct1.S = 1;
    }

    Struct2.S = 0;
    if (Extra2 > 0 ){
        Struct2.S = 1;
    }
    //*************************************************
    //*************************************************

    Struct1.e = e1;Struct2.e = e2;
    Struct1.f = f1;Struct2.f = f2;

    if((Struct1.realSign == Struct2.realSign) || (Struct1.e != Struct2.e))
    {
        if(!DetailsOn)
        {
        if (Struct1.DenF){
            PrintIEEENum("A    ",Struct1.IEEE + " (Denorm.)", Struct1.Sign, Struct1.f, Struct1.e, bits);
        } else {
            PrintIEEENum("A    ",Struct1.IEEE, Struct1.Sign, Struct1.f, Struct1.e, bits);
        }

        if (Struct2.DenF){
            PrintIEEENum("B    ",Struct2.IEEE + " (Denorm.)", Struct2.Sign, Struct2.f, Struct2.e, bits);
        } else {
            PrintIEEENum("B    ",Struct2.IEEE, Struct2.Sign, Struct2.f, Struct2.e, bits);
        }
        }

        PrintHR();

        parent.frames[1].document.writeln("<PRE>");

        if((Struct1.e != Struct2.e))
            parent.frames[1].document.writeln("ERROR: Incorrect operands! Different exponent");
        else
            parent.frames[1].document.writeln("ERROR: Incorrect operands! Same sign");

        parent.frames[1].document.writeln("</PRE>");
        parent.frames[1].document.writeln("</PRE>");
        return -1;
    }

    if(DetailsOn)
    {
        parent.frames[1].document.writeln("<PRE>");

        parent.frames[1].document.writeln("E<sub>i</sub> = (not (a<sub>i</sub> xor b<sub>i</sub>)) and (a<sub>i-1</sub> or b<sub>i-1</sub>)");
        parent.frames[1].document.writeln("</PRE>");

        PrintHR();
    }

    if(DetailsOn)
    {
        //	Add with G R and S
        parent.frames[1].document.writeln("Alignment Step:")
        PrintGRS("A    ", Alpha.G, Alpha.R, Alpha.S, Alpha.Sign, f1, e1, bits)
        PrintGRS("B    ", Beta.G, Beta.R, Beta.S, Beta.Sign, f2, e2, bits)
        if(DetailsOn)
            PrintHR();
    }


    E = "";

    if(Struct1.f > Struct2.f)
    {
      S1 = PrintBinary(Struct1.f, bits) + Struct1.G;
    }
    else
    {
        S1 = PrintBinary(Struct2.f, bits) + Struct2.G;
    }

    if(DetailsOn)
    {

        parent.frames[1].document.writeln("a    : "+ S1);
    }

    if(Struct1.f > Struct2.f)
    {
        // assume effective subtract, so S2 is positive
      S2 = BitWiseNegate(PrintBinary(Struct2.f, bits) + Struct2.G);
    }
    else
    {
        S2 = BitWiseNegate(PrintBinary(Struct1.f, bits) + Struct1.G);
    }
    if(DetailsOn)
    {
        parent.frames[1].document.writeln("b    : "+ S2);
        PrintHR();
    }

    for (i=0; i<S1.length-1; i++) {
         nextPos = i+1;
         if (S1.charAt(nextPos) == '.')
            nextPos = i+2;
         if (S1.charAt(i) == '.')
           E = E + ".";
         else if ( ( S1.charAt(i) == S2.charAt(i) ) &&
                    ( (S1.charAt(nextPos) == '1')  || (S2.charAt(i+1) == '1') ) ) {
               E = E + "1";
         }
         else {
            E = E + "0";
         }
      }
      numLeadingZeroes = 0
      done = 0;
      for (i=0; i<E.length && done == 0; i++) {
        if (E.charAt(i) == '.') { } // ignore
        else if (E.charAt(i) == '0')
           numLeadingZeroes++;
       else
           done = 1;
      }

    if(DetailsOn)
    {

        parent.frames[1].document.writeln("e    : " + E );
        parent.frames[1].document.writeln("<PRE>");

        parent.frames[1].document.writeln("Number of Leading 0s: "+numLeadingZeroes);
        parent.frames[1].document.writeln("</PRE>");

        PrintHR();
    }

      if(DetailsOn)
    {
	    parent.frames[1].document.writeln("</PRE>")
    }
	return numLeadingZeroes;
}



// ***************************************************************


function GetBits(TheNum, NumDecs) {

    var bitsNum=new Array();

    // deal with negative numbers
    if (TheNum < 0) {
        TheNum *= -1
    }

    var i =0;

    StartPlace = OrderOfMagnitude(TheNum)
    if (StartPlace < 0) {StartPlace = 0}

    for (CurPlace = StartPlace; CurPlace >= -NumDecs; CurPlace--) {
        if (CurPlace == -1) {}//S += "."}
        CurPower = Math.pow(2, CurPlace)
        if (TheNum >= CurPower) {
            //S += "1"
            bitsNum[i] = 1;
            i++;
            TheNum = TheNum - CurPower;

        } else  {
            bitsNum[i] = 0;
            i++;
        }
    }
    return bitsNum;
}

// ***************************************************************

function LeadingZeroNew(Struct1, Struct2, Struct3, DetailsOn) {
    var bitsA;
    var bitsB;


    if(DetailsOn)
        parent.frames[1].document.writeln("<PRE>");

    // Is A = 0 ?
    if (Struct1.ZeroF || Struct1.DenF){
        Struct1.f = Struct1.f;
    }else{
        Struct1.f = Struct1.f + 1;
    }

    // Is B = 0 ?
    if (Struct2.ZeroF || Struct2.DenF){
        Struct2.f = Struct2.f;
    }else{
        Struct2.f = Struct2.f + 1;
    }



//    if(DetailsOn)
    if(0)
    {
        parent.frames[1].document.writeln("<PRE>");
        parent.frames[1].document.writeln("<CENTER><H4>J. Bruguera and T. Lang. \"Leading-one prediction with concurrent position correction\"</H4></CENTER>");

        parent.frames[1].document.writeln("<CENTER><H5> International Conference on Computers, pages 298-305, ct. 1999</H5></CENTER>");
        parent.frames[1].document.writeln("</PRE>");

    }
    else if(DetailsOn)
    {
        parent.frames[1].document.writeln("<PRE>");
        parent.frames[1].document.writeln("<CENTER><H4>Algorithm 2</H4></CENTER>");

        PrintHR();
        parent.frames[1].document.writeln("</PRE>");

    }


    if(DetailsOn)
    {
        if (Struct1.DenF){
            PrintIEEENum("A    ",Struct1.IEEE + " (Denorm.)", Struct1.Sign, Struct1.f, Struct1.e, bits);
        } else {
            PrintIEEENum("A    ",Struct1.IEEE, Struct1.Sign, Struct1.f, Struct1.e, bits);
        }

        if (Struct2.DenF){
            PrintIEEENum("B    ",Struct2.IEEE + " (Denorm.)", Struct2.Sign, Struct2.f, Struct2.e, bits);
        } else {
            PrintIEEENum("B    ",Struct2.IEEE, Struct2.Sign, Struct2.f, Struct2.e, bits);
        }

        PrintHR();
    }

    e1 = Struct1.e; f1 = Struct1.f;
    e2 = Struct2.e; f2 = Struct2.f;
    // Alignment Step
    if (e1 > e2) {
        bitsDiff = (e1 - e2);
        f2 = f2 / Math.pow(2,bitsDiff);
        e2 = e1;
    } else {
        if (e1 < e2) {
            bitsDiff = (e2 - e1);
            f1 = f1 / Math.pow(2,bitsDiff);
            e1 = e2;
        } else {
            bitsDiff=0;
        }
    }

    Fullf1 = f1;
    Fullf2 = f2;

    f1 = TruncateToBits(f1,bits);
    f2 = TruncateToBits(f2,bits);
    Extra1 = (Fullf1 - f1)*Math.pow(2,bits);
    Extra2 = (Fullf2 - f2)*Math.pow(2,bits);

    //*************************************************
    // Find G, R, S for A and B
    //*************************************************

    Struct1.G = 0;
    if (Extra1 >=0.5 ){
        Struct1.G = 1;
        Extra1 = Extra1 - 0.5;
    }

    Struct2.G = 0;
    if (Extra2 >=0.5 ){
        Struct2.G = 1;
        Extra2 = Extra2 - 0.5;
    }

    Struct1.R = 0;
    if (Extra1 >= 0.25 ){
        Struct1.R = 1;
        Extra1 = Extra1 - 0.25;
    }

    Struct2.R = 0;
    if (Extra2 >= 0.25){
        Struct2.R = 1;
        Extra2 = Extra2 - 0.25;
    }

    Struct1.S = 0;
    if (Extra1 > 0 ){
        Struct1.S = 1;
    }

    Struct2.S = 0;
    if (Extra2 > 0 ){
        Struct2.S = 1;
    }
    //*************************************************
    //*************************************************

    Struct1.e = e1;Struct2.e = e2;
    Struct1.f = f1;Struct2.f = f2;

    if((Struct1.realSign == Struct2.realSign) || (Struct1.e != Struct2.e))
    {
        if(!DetailsOn)
        {
            if (Struct1.DenF){
                PrintIEEENum("A    ",Struct1.IEEE + " (Denorm.)", Struct1.Sign, Struct1.f, Struct1.e, bits);
            } else {
                PrintIEEENum("A    ",Struct1.IEEE, Struct1.Sign, Struct1.f, Struct1.e, bits);
            }

            if (Struct2.DenF){
                PrintIEEENum("B    ",Struct2.IEEE + " (Denorm.)", Struct2.Sign, Struct2.f, Struct2.e, bits);
            } else {
                PrintIEEENum("B    ",Struct2.IEEE, Struct2.Sign, Struct2.f, Struct2.e, bits);
            }

            PrintHR();
        }
        parent.frames[1].document.writeln("<PRE>");

        if((Struct1.e != Struct2.e))
            parent.frames[1].document.writeln("ERROR: Incorrect operands! Different exponent");
        else
            parent.frames[1].document.writeln("ERROR: Incorrect operands! Same sign");

        parent.frames[1].document.writeln("</PRE>");
        parent.frames[1].document.writeln("</PRE>");
        return -1;
    }

    if(DetailsOn)
    {
        parent.frames[1].document.writeln("<PRE>");

        parent.frames[1].document.writeln("W<sub>i</sub> =  (a<sub>i</sub> - b<sub>i</sub>) where a > b");
        parent.frames[1].document.writeln("Correction steps needed when: W=0<sup>k</sup>p0<sup>j</sup>n(x) and W=0<sup>k</sup>pn<sup>j</sup>0<sup>t</sup>neg1(x) where p=1, n ={-1}");

        parent.frames[1].document.writeln("</PRE>");

        PrintHR();
    }

    if(DetailsOn)
    {
        //	Add with G R and S
        parent.frames[1].document.writeln("Alignment Step:")
        PrintGRS("A    ", Alpha.G, Alpha.R, Alpha.S, Alpha.Sign, f1, e1, bits)
        PrintGRS("B    ", Beta.G, Beta.R, Beta.S, Beta.Sign, f2, e2, bits)
        if(DetailsOn)
            PrintHR();
    }



    if(Struct1.f > Struct2.f)
        S1 = PrintBinary(Struct1.f, bits) + Struct1.G;
    else
        S1 = PrintBinary(Struct2.f, bits) + Struct2.G;

    //bitsA = GetBits(Struct1.f, bits);
    bitsA = S1;//PrintBinary(Struct1.f, bits) + Struct1.G;


    // assume effective subtract, so S2 is positive
    if(Struct1.f > Struct2.f)
        S2 = PrintBinary(Struct2.f, bits) + Struct2.G;
    else
        S2 = PrintBinary(Struct1.f, bits) + Struct1.G;

    //bitsB = GetBits(Struct2.f, bits);
    bitsB = S2;//PrintBinary(Struct2.f, bits) + Struct2.G;

    if(DetailsOn)
    {
        parent.frames[1].document.writeln("a    : "+ S1);
        parent.frames[1].document.writeln("b    : "+ S2);
        PrintHR();
    }


    E3 = "";

    for (i=0; i<bitsA.length; i++) {
        if((bitsA.charAt(i)=='0') && (bitsB.charAt(i)=='0'))
            E3 = E3 + "0";
        else if ((bitsA.charAt(i)=='1') && (bitsB.charAt(i)=='1')) {
            E3 = E3 + "0";
        }
        else if ((bitsA.charAt(i)=='1') && (bitsB.charAt(i)=='0')) {
            E3 = E3 + "p";
        }
        else if ((bitsA.charAt(i)=='0') && (bitsB.charAt(i)=='1')) {
            E3 = E3 + "n";
        }
        else if(((bitsA.charAt(i)=='.')&& (bitsB.charAt(i)=='.'))) {
            E3 = E3 + ".";
        }
    }

    if(DetailsOn)
    {
        parent.frames[1].document.writeln("<PRE>");
        parent.frames[1].document.writeln("W    : " + E3 );
//        parent.frames[1].document.writeln("Number of Leading 0s: "+numLeadingZeroes);
        parent.frames[1].document.writeln("</PRE>");

        PrintHR();
    }

    E = "";

    for (i=0; i<E3.length; i++) {
        if (E3.charAt(i) == '.')
        { } // ignore
        else
        {
            E = E + E3.charAt(i);
        }
    }

    //E = E2;

    numLeadingZeroes = 0;
    done = 0;
    for (i=0; i<E.length && done == 0; i++) {
        if (E.charAt(i) == '.') { } // ignore
        else if (E.charAt(i) == '0')
            numLeadingZeroes++;
        else
        {
            done = 1;
            leadingOneAt = i;
        }
    }


    addZeros = 0;


    if((E.charAt(leadingOneAt) == 'p') && ((E.charAt(leadingOneAt+1) == '0')||(E.charAt(leadingOneAt+1) == 'n')))
    {

        while ((E.charAt(leadingOneAt+1) != '0') && (E.charAt(leadingOneAt+1) != 'p'))
        {
            addZeros++;
            leadingOneAt++;
        }
        if(E.charAt(leadingOneAt+1) == '0')
        {
            done = 0;
            for (i=leadingOneAt+1; i<E.length && done == 0; i++) {
                if (E.charAt(i) == '.') { } // ignore
                else if (E.charAt(i) == 'p') {
                    numLeadingZeroes = numLeadingZeroes + addZeros;
                    done = 1;
                }
                else if (E.charAt(i) == 'n')
                {
                    numLeadingZeroes++;
                    numLeadingZeroes = numLeadingZeroes + addZeros;
                    done = 1;

                    if(DetailsOn)
                    {
                        parent.frames[1].document.writeln("<PRE>");
                        parent.frames[1].document.writeln("Concurrent correction step needed due to sequence W = 0...0p0000...n... ");
                        PrintHR();
                        parent.frames[1].document.writeln("Corrected number of Leading 0s: "+numLeadingZeroes);
                        parent.frames[1].document.writeln("</PRE>");

                        PrintHR();
                    }
                }
            }


        }
        else
        {
            numLeadingZeroes = numLeadingZeroes + addZeros;
            if(DetailsOn)
            {
                parent.frames[1].document.writeln("<PRE>");
                parent.frames[1].document.writeln("Concurrent correction step needed due to sequence W = 0...0pn...p... ");
                PrintHR();
                parent.frames[1].document.writeln("Corrected number of Leading 0s: "+numLeadingZeroes);
                parent.frames[1].document.writeln("</PRE>");

                PrintHR();
            }
        }
    }
    else if(E.charAt(leadingOneAt) == 'n')
    {
        if(E.charAt(leadingOneAt+1) == '0')
        {
            done = 0;
            for (i=leadingOneAt+1; i<E.length && done == 0; i++) {
                if (E.charAt(i) == '.') { } // ignore
                else if (E.charAt(i) == 'n') {done = 1;}
                else if (E.charAt(i) == 'p')
                {
                    numLeadingZeroes++;
                    done = 1;
                }
            }

            if(DetailsOn)
            {
                parent.frames[1].document.writeln("<PRE>");
                parent.frames[1].document.writeln("Concurrent correction step needed due to sequence W = 0...0n0000...p... ");
                PrintHR();
                parent.frames[1].document.writeln("</PRE>");
            }
        }
    }

    if(DetailsOn)
    {
        parent.frames[1].document.writeln("<PRE>");
        parent.frames[1].document.writeln("Number of Leading 0s: "+numLeadingZeroes);
        parent.frames[1].document.writeln("</PRE>");

        PrintHR();
    }

    if(DetailsOn)
    {
        parent.frames[1].document.writeln("</PRE>")
    }
    return numLeadingZeroes;
}

