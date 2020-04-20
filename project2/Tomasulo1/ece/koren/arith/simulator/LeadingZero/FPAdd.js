// ***************************************************************
// Floating point adder - for verification of prediction
// ***************************************************************

function Add(Struct1, Struct2, Struct3, Struct4, DetailsOn, SomeOut) {

	// Is A = 0 ?
	if (Struct1.ZeroF || Struct1.DenF){
		f1 = Struct1.f;
	}else{
		f1 = Struct1.f + 1;
	}

	// Is B = 0 ?
	if (Struct2.ZeroF || Struct2.DenF){
		f2 = Struct2.f;
	}else{
		f2 = Struct2.f + 1;
	}

	e1 = Struct1.e;
	e2 = Struct2.e;

//	PrintIEEENum(Name, Number, sign, f, e, bits)
    if(SomeOut)
	    parent.frames[1].document.writeln("<PRE>");
//	S = "A.f   " + PrintBinary(f1,bits+10)
//	document.writeln(S)
//	S = "B.f   " + PrintBinary(f2,bits+10)
//	document.writeln(S)
//	document.writeln("<HR>")
    if(SomeOut)
    {
        if (Struct1.DenF){
            PrintIEEENum("A    ",Struct1.IEEE + " (Denorm.)", Struct1.Sign, f1, e1, bits);
        } else {
            PrintIEEENum("A    ",Struct1.IEEE, Struct1.Sign, f1, e1, bits);
        }

        if (Struct2.DenF){
            PrintIEEENum("B    ",Struct2.IEEE + " (Denorm.)", Struct2.Sign, f2, e2, bits);
        } else {
            PrintIEEENum("B    ",Struct2.IEEE, Struct2.Sign, f2, e2, bits);
        }
        if(DetailsOn)
            PrintHR();
    }
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

    //	f3 = Struct1.realSign*f1 +  Math.pow(-1,Struct2.Sign^Subtract)*f2;

    carryin = 0;

    mna1= Struct1.realSign*f1;

    mna2= Struct2.realSign*f2;

    if(!(Struct1.G || Struct1.R || Struct1.S) && !(Struct2.G || Struct2.R || Struct2.S))
    {
    }
    else
    {
        if(((Struct1.realSign != Struct2.realSign) && (f1 > f2) && !(Struct1.G || Struct1.R || Struct1.S)))
          {
              carryin = Math.pow(2,-23);

              if(f1 < f2)
              {
                  if(Struct2.realSign == -1)
                    mna2 = Struct2.realSign*f2 + carryin;
                  else
                    mna2 = Struct2.realSign*f2 - carryin;

              }
              else
              {
                  if(Struct1.realSign == -1)
                      mna1 = Struct1.realSign*f1 + carryin;
                  else
                      mna1 = Struct1.realSign*f1 - carryin;
              }
          }
          else if(((Struct2.realSign != Struct1.realSign)&& (f2 > f1) && !(Struct2.G || Struct2.R || Struct2.S)))
          {
              carryin = Math.pow(2,-23);
              if(f1 < f2)
              {
                  if(Struct2.realSign == -1)
                    mna2 = Struct2.realSign*f2 + carryin;
                  else
                    mna2 = Struct2.realSign*f2 - carryin;
              }
              else
              {
                  if(Struct1.realSign == -1)
                      mna1 = Struct1.realSign*f1 + carryin;
                  else
                      mna1 = Struct1.realSign*f1 - carryin;
              }

          }
        }

        if(Subtract)
            f3 = mna1 - mna2;
        else
            f3 = mna1 + mna2; //mna remove it


	    TheNum=f3*Math.pow(2,e1-bias)
	    Struct3 = IEEERepresentation(Struct3, TheNum, bits, bias);

 	    if (Subtract){OpS="A-B  ";}else{OpS="A+B  ";}

        if(SomeOut)
        {
	        //	Add with G R and S
	        parent.frames[1].document.writeln("<CENTER><H4>Alignment Step</H4></CENTER>")
	        PrintGRS("A    ", Alpha.G, Alpha.R, Alpha.S, Alpha.Sign, f1, e1, bits)
	        PrintGRS("B    ", Beta.G, Beta.R, Beta.S, Beta.Sign, f2, e2, bits)
            if(DetailsOn)
	            PrintHR();
        }


    	f3sign = (f3 < 0);
	    f3realSign = (f3 < 0) ? -1 : 1;
    	f3 = Math.abs(f3);

	    // Find G, R ans S for f3
	    Fullf3 = f3;
	    f3 = TruncateToBits(f3,bits);
	    Extra3 = (Fullf3 - f3)*Math.pow(2,bits);

	//f3orig = Struct1.realSign*f1 + Math.pow(-1,Struct2.Sign^Subtract)*f2;
        asign = 0;
        bsign = 0;
	// post calculation modification
	    if(Subtract)
            GRSf3=Struct1.realSign*(Struct1.G*4 + Struct1.R*2 + Struct1.S)- Struct2.realSign*(Struct2.G*4+Struct2.R*2+Struct2.S);
	    else
	    {
            if(!(Struct1.G || Struct1.R || Struct1.S) && !(Struct2.G || Struct2.R || Struct2.S))
            {
            }
            else
            {

                if(((Struct1.realSign != Struct2.realSign ) && (f1 > f2) && !(Struct1.G || Struct1.R || Struct1.S)))
                {
                    asign = 1;
                }
                else if(((Struct2.realSign != Struct1.realSign)&& (f2 > f1) && !(Struct2.G || Struct2.R || Struct2.S)))
                {
                    bsign = 1;
                }

            }

            GRSf3=Struct1.realSign*(asign*8 + Struct1.G*4 + Struct1.R*2 + Struct1.S)+ Struct2.realSign*(bsign*8 + Struct2.G*4+Struct2.R*2+Struct2.S);
        }


        if (GRSf3<0)
	    {
	    	GRSf3=Math.abs(GRSf3) & 7;
	    }
	    Gf3=0;
	    Rf3=0;
	    Sf3=0;
    	if (GRSf3 >= 4)
	    {
	    	Gf3=1;
	    	GRSf3-=4;
	    }
    	if (GRSf3 >=2)
	    {
	    	Rf3=1;
	    	GRSf3-=2;
	    }
    	if (GRSf3 == 1)
	    {
	    	Sf3=1;
	    }

        if(SomeOut)
        {
	        PrintGRS(OpS, Gf3, Rf3, Sf3, f3sign, f3, e2, bits);
            if(DetailsOn)
	            PrintHR();
        }

        f3 = f3 + Gf3*Math.pow(2,-bits-1) +  Rf3*Math.pow(2,-bits-2)
			+ Sf3*Math.pow(2,-bits-3);
	    result = f3realSign*f3*Math.pow(2,(e2 - bias));
	    Struct3 = IEEERepresentation(Struct3, result, bits+3, bias);

	    Fullf3 = Struct3.f;
	    e3 = Struct3.e;

	    if(Math.abs(f3)<1 && e1!=e2)
	    {
		    f3+=Gf3*Math.pow(2,-bias);
		    R1=Rf3;
		    R2=Sf3;
	    }else{
		    R1=Gf3;
		    R2=(Rf3||Sf3)? 1:0;
	    }
	    Struct3 = IEEERepresentation(Struct3, result, bits, bias);
	    f3 = Struct3.f;

    	if (Struct3.DenF || Struct3.ZeroF){
	    	f3 = f3;
	    } else {
		    f3 = f3 + 1;
	    }

        if(SomeOut)
        {
	        parent.frames[1].document.writeln("<CENTER><H4>Postnormalization Step</H4></CENTER>");
	        PrintGRS(OpS, R1, R2, "", f3sign, f3, e3, bits);
            if(DetailsOn)
	            PrintHR();
        }
	    // Find Final result using rounding techniques and f3, R1, R2
	    // Truncation
	f = f3;
	e = e3;

	result = f3realSign*f*Math.pow(2,e-bias);
	Struct3 = IEEERepresentation(Struct3, result, bits, bias);

    if(DetailsOn)
    	parent.frames[1].document.writeln("<CENTER><H4>Round to Zero</H4></CENTER> ");

    if(SomeOut)
    {
	    PrintResult(Struct3 , OpS);
	    PrintHR();
    }
	// Round to nearest Even
	f = f3 + (R1 && R2)*Math.pow(2,-bits);
	temp = ((f3*Math.pow(2,bits-1)) - Math.floor(f3*Math.pow(2,bits-1)));
	lastBit = 0;
	if (temp >= 0.5  ) { lastBit = 1;}

	feven = f;
//	fodd = f;
	if ((R1 == 1) && (R2 == 0)) { feven = feven + lastBit*Math.pow(2,-bits)}
//	if ((R1 == 1) && (R2 == 0)) { fodd = fodd + (lastBit^1)*pow(2,-bits)}
	e = e3;
	result = f3realSign*feven*Math.pow(2,e-bias);
     Struct3 = IEEERepresentation(Struct3, result, bits, bias)

     Struct4.f = Struct3.f;
     Struct4.e = Struct3.e;
     Struct4.IEEE = Struct3.IEEE;

     MyResult = Struct3.IEEE;

    if(DetailsOn)
    {
	    parent.frames[1].document.writeln("<CENTER><H4>Round to Nearest Even</H4></CENTER>")
        PrintResult( Struct3 , OpS)
	    //PrintIEEENum(OpS,result, f3sign, feven,e, bits)

	    PrintHR();
    }

	// Round to Plus Infinity
	f = f3;
	e = e3;
    if (f3sign == 0){
		f = f3 + (R1 || R2)*Math.pow(2,-bits);
	}
	result = f3realSign*f*Math.pow(2,e-bias);
	Struct3 = IEEERepresentation(Struct3, result, bits, bias)
    if(DetailsOn)
    {
	    parent.frames[1].document.writeln("<CENTER><H4>Round to Plus Infinity</H4></CENTER>")
	    PrintResult( Struct3 , OpS)
	    PrintHR();
    }

	// Round to Minus Infinity
	f = f3;
	e = e3;
    if (f3sign == 1){
		f = f3 + (R1 || R2)*Math.pow(2,-bits);
	}
	result = f3realSign*f*Math.pow(2,e-bias);
	Struct3 = IEEERepresentation(Struct3, result, bits, bias)

    if(DetailsOn)
    {
	    parent.frames[1].document.writeln("<CENTER><H4>Round to Minus Infinity</H4></CENTER>")
	    PrintResult( Struct3 , OpS)
	    PrintHR();
    }

    parent.frames[1].document.writeln("</PRE>")
	return Struct4;
}