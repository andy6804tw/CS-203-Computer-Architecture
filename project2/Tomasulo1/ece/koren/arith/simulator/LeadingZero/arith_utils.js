// *************************************************************************************
// Javascript helper functions obtained from www.ecs.umass.edu/ece/koren/arith/simulator
// *************************************************************************************
 function StringtoBin(Str){
	Res="";
	for(var i=Str.length();i<0;i--){
	    last=parseFloat(Str[i]);
	    do{
		if((last % 2)==1){
		    Res="1"+Res;
		}else{
		    Res="0"+Res;
		}
		last=last/2;
	    }while(last<1);
	}
	return Res;
 }


function BitWiseNegate(BitStr) {
  res = "";
  for (i=0;i<BitStr.length;i++) {
    if (BitStr.charAt(i) == '.') {
       res = res + '.';
    }
    else if (BitStr.charAt(i) == '0') {
       res = res + '1';
    }
    else if (BitStr.charAt(i) == '1') {
       res = res + '0';
    }
  }
  return res;
}
function unsignedToBinStr(TheNum, bits) {
	// initialize the string
	S = "";

	for (CurPlace = bits-1; CurPlace >= 0; CurPlace--) {
		CurPower = Math.pow(2, CurPlace)
		if (TheNum >= CurPower) {
			S += "1"
			TheNum = TheNum - CurPower
		} else  {
			S += "0"
		}
	}
	return S
}
function signedToBinStr(TheNum, bits) {
	mark = 0;
	if (TheNum < 0) {
		mark = 1;
		TheNum = -1 * TheNum;
	}
	S = unsignedToBinStr(TheNum, bits);
	if (mark == 1) {
		// convert to 2's complements
		for (i = bits-1; i>=0; i--) {
			if (state == 0) { // before first 1
				Res = S.charAt(i) + Res;
				if (S.charAt(i) == '1')
					state = 1;
			}
			else {
				Res = (S.charAt(i)=='1'?'0':'1') + Res;
			}
		}
	}
}


function PrintHR(NumColumns) {
	parent.frames[1].document.writeln("<HR noshade size=1>");
}

// ***************************************************************
// ***************************************************************

function BinToDec (BinStr) {

	Num=0;
	len = BinStr.length
	for (var i=0; i<len ; i++) {
//		Num += eval(BinStr[BinStr.length-1-i])*Math.pow(2,i);
		if (BinStr.charAt(len - 1 - i) == '1')
			Num += Math.pow(2,i);
	}
	return Num;
}

// ***************************************************************
// ***************************************************************

function OrderOfMagnitude (TheNum) {
	// returns the highest place which has a "1" in TheNum's
	// binary representation - special non-2's complement version

	LogBaseTwo = Math.log(TheNum)/Math.LN2;
	return Math.floor(LogBaseTwo)
	//return Math.round(LogBaseTwo)
}

// ***************************************************************
// ***************************************************************

function PrintError(Message) {
	Error = "<CENTER><H1> ERROR </H1></CENTER> <P>\n"
	Error += "<CENTER> <EM>" + Message + "</EM> </CENTER>\n <HR noshade=1>"
	return Error
}

// ***************************************************************
// ***************************************************************

function ParseBinary (BinNum, NumBits) {
	// Turns a binary number into a decimal, sets a flag to true if
	// BinNum is not actually binary

	IsNeg = false
	if (BinNum < 0) {
		IsNeg = true
		BinNum *= -1
	}
	RealNum = 0
	LogBaseTen = Math.log(BinNum)/Math.LN10
	HighestPlace = Math.ceil(LogBaseTen)
	for (CurPlace = HighestPlace; CurPlace >= -NumBits; CurPlace--) {
		CurEntry = Math.round(BinNum / Math.pow(10, CurPlace))
		if (CurEntry != 0 && CurEntry != 1) {
			NotBinary = true
			CurEntry = 1		// feed it something normal, to avoid
								// wierd Javascript errors
		}
		if (CurEntry == 1) {
			RealNum += Math.pow(2, CurPlace)
			BinNum -= Math.pow(10, CurPlace)
		}
	}

	if (IsNeg) {RealNum *= -1} // reverse it back at the end
	// now check if the number was negative according to two's complement
	// representation - if it was, set the decimal value to this negative
	// number
	if (HighestPlace == NumBits) {
		RealNum -= Math.pow(2, NumBits)
	}

	return RealNum
}
// ***************************************************************
// ***************************************************************

function PrintBinary2(TheNum, NumDecs) {

	// initialize the string
	S = "";
	// deal with negative numbers
              mark = 0;
	if (TheNum < 0) {
		TheNum = -1* TheNum;
		mark = 1;
	}
	StartPlace = OrderOfMagnitude(TheNum)
	if (StartPlace < 0) {StartPlace = 0}

	for (CurPlace = StartPlace; CurPlace >= -NumDecs; CurPlace--) {
		if (CurPlace == -1) {S += "."}
		CurPower = Math.pow(2, CurPlace)
		if (TheNum >= CurPower) {
			S += "1"
			TheNum = TheNum - CurPower
		} else  {
			S += "0"
		}
	}
	state = 0;
	Res = "";
	if (mark == 1) {
		// convert to 2's complements
		for (i = bits-1; i>=0; i--) {
			if (state == 0) { // before first 1
				Res = S.charAt(i) + Res;
				if (S.charAt(i) == '1')
					state = 1;
			}
			else {
				if (S.charAt(i) == '.') Res = '.' + Res;
				else                       Res = (S.charAt(i)=='1'?'0':'1') + Res;
			}
		}
	}
	else
		Res = S;

	return Res;
}

function PrintBinary(TheNum, NumDecs) {

	// initialize the string
	S = "";
	// deal with negative numbers
	if (TheNum < 0) {
		S += "-"
		TheNum *= -1
	}
	StartPlace = OrderOfMagnitude(TheNum)
	if (StartPlace < 0) {StartPlace = 0}

	for (CurPlace = StartPlace; CurPlace >= -NumDecs; CurPlace--) {
		if (CurPlace == -1) {S += "."}
		CurPower = Math.pow(2, CurPlace)
		if (TheNum >= CurPower) {
			S += "1"
			TheNum = TheNum - CurPower
		} else  {
			S += "0"
		}
	}
	return S
}

// ***************************************************************
// ***************************************************************

function PrintToBin(Name, TheNum, NumDecs) {

	S = PrintBinary(TheNum, NumDecs)
	parent.frames[1].document.writeln(Name+" = "+TheNum+"<BR>"+"Bin ="+S+"<BR>")
}

// ***************************************************************
// ***************************************************************

function TruncateToBits(Number, bits){
	if (Number >= 0) {
		return (Math.floor(Number * Math.pow(2,bits)) / Math.pow(2,bits))
	} else {
		return (-Math.floor(-Number * Math.pow(2,bits)) / Math.pow(2,bits))
	}
}

// ***************************************************************
// ***************************************************************

function Structure(IEEE, error, maxerror, Sign, f, f_str, e, e_str, ZeroF, InfF, DenF, NanF, OveF,
			 UndF, G, R, S) {
	this.IEEE=IEEE
	this.error=error
	this.maxerror=maxerror
	this.Sign=Sign
	this.f=f
	this.f_str=f_str
	this.e=e
	this.e_str=e_str
	this.ZeroF=ZeroF
	this.InfF=InfF
	this.DenF=DenF
	this.NanF=NanF
	this.OveF=OveF
	this.UndF=UndF
	this.G = G;
	this.R = R;
	this.S = S;

}

// ***************************************************************
// ***************************************************************

function PrintIEEENum(Name, Number, sign, f, eWithBias, bits){

	Text = Name + (sign ? "- " : "+ ")
	Text += PrintBinary(f,bits) + " *2<sup>" + (eWithBias - bias) + "</sup> = " + Number
	parent.frames[1].document.writeln(Text)

}

// ***************************************************************
// ***************************************************************

function PrintGRS(Name, G, R, S, sign, f, eWithBias, bits){

	Text = Name + (sign ? "- " : "+ ")
	Text += PrintBinary(f,bits) + "|" + G + R + S + " *2<sup>" + (eWithBias - bias) + "</sup>"
	parent.frames[1].document.writeln(Text)

}


// ***************************************************************
// ***************************************************************

function IEEERepresentation(Structure, TheNum, bits, bias) {

//	if (TheNum==0) {
		Structure.IEEE=0;
		Structure.error=0;
		Structure.maxerror=0;
		Structure.Sign=0;
		Structure.realSign=0;
		Structure.f=0;
		Structure.e=0;
		Structure.ZeroF=1;
		Structure.InfF=0;
		Structure.DenF=0;
		Structure.NanF= 0;
		Structure.OveF= 0;
		Structure.UndF=0;
		Structure.G=0;
		Structure.R=0;
		Structure.S=0;
//	} else {
	if (TheNum!=0) {
		Structure.Sign = (TheNum < 0)
		Structure.realSign = (TheNum < 0) ? -1 : 1
		TheNum=Math.abs(TheNum)

		Y=Math.log(TheNum)/Math.log(2);
		N=Math.floor(Y);
		Z=Y-N;

		ffull=(TheNum*Math.pow(2,-N));

		if (ffull >= 2){
			ffull = ffull/2;
			N = N + 1;
		}
		ffull = ffull - 1;
		fdecimal=Math.floor(ffull*Math.pow(2,bits));

		Structure.f=fdecimal*Math.pow(2,-bits);
		Structure.e=bias+N
		Structure.IEEE= Structure.realSign * (1+Structure.f)*Math.pow(2,Structure.e-bias);
		Structure.UndF = 0;
		Structure.DenF = 0;


		if (TheNum < Math.pow(2,-bias+1)){
			N = -bias+1;
			ffull=TheNum/Math.pow(2,N)
			fdecimal=Math.floor(ffull*Math.pow(2,bits));
			Structure.f=fdecimal/Math.pow(2,bits);
			Structure.e= 1;
			Structure.DenF = 1;
			Structure.IEEE= Structure.realSign*Structure.f*Math.pow(2,N);
		}


		if (TheNum < Math.pow(2,-bias+1-bits)){
			Structure.DenF = 0;
			Structure.UndF = 1;
		}

		Structure.error=TheNum-Math.abs(Structure.IEEE);
		Structure.maxerror=Math.pow(2,Structure.e-bias-bits);


		Structure.ZeroF=0;
		Structure.InfF = 0;
		Structure.NanF = 0;
		if (Structure.e==2*bias+1){
			if (Structure.f==0){
				Structure.InfF = 1;
			} else {
				Structure.NanF = 1;
			}
		}
		Structure.OveF = (Structure.e > 2*bias+1)
	}
	Structure.e_str = unsignedToBinStr(Structure.e, Total-bits-1 );
	Structure.f_str = unsignedToBinStr(Structure.bits);
	return Structure;
}

// ***************************************************************
// ***************************************************************

function PrintResult( Structure , Name){

	if (Structure.ZeroF || Structure.DenF){
		fprint = Structure.f;
	}else{
		fprint = Structure.f + 1;
	}

	SignPrint = " + ";
	if (Structure.Sign){ SignPrint = " - ";}

	flag = 1;
	if (Structure.NanF){
		flag = 0;

		// Set result to infinity
		PrintIEEENum(Name +"    ", SignPrint +"Inf", Structure.Sign, 1, Structure.e, bits)
 		//document.writeln("<HR>")
	}

	if (Structure.InfF){
		flag = 0;
		PrintIEEENum(Name +"    ",SignPrint +"Inf" , Structure.Sign, fprint, Structure.e, bits)
 		//document.writeln("<HR>")
	}

	if (Structure.UndF){
		flag = 0;
		PrintIEEENum(Name +"    ", "NaN (Underflow)", Structure.Sign, fprint, Structure.e, bits)
 		//document.writeln("<HR>")
	}

	if (flag) {
	    if (Structure.DenF)
		{
		    PrintIEEENum(Name +"    " , Structure.IEEE + " (Denorm.)", Structure.Sign,fprint, Structure.e, bits);
		} else {
		    PrintIEEENum(Name +"    ", Structure.IEEE, Structure.Sign, fprint, Structure.e, bits);
		}
	}	
}

// ***************************************************************
// ***************************************************************

function IsValidHex (theString) {
	// check to make sure every character is valid
	legalChars = "0123456789abcdefABCDEF"
	for (var i=0; i < theString.length; i++) {
		illegalChar = 1;
		for (var j=0; j < 22; j++){
			if (theString.charAt(i) == legalChars.charAt(j))
				illegalChar = 0;
		}	
		if (illegalChar)
			return 0;
	}
	return 1; // if we got to here, everything's valid
}

function IsValidDecimal (theString) {
	// check to make sure every character is valid
	legalChars = "-0123456789."
	for (var i=0; i < theString.length; i++) {
		illegalChar = 1;
		for (var j=0; j < 12; j++){
			if (theString.charAt(i) == legalChars.charAt(j))
				illegalChar = 0;
		}	
		if (illegalChar) 
			return 0;
	}
	return 1; // if we got to here, everything's valid
}


