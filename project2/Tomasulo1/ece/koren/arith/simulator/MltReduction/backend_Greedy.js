/**
 *  Author: Prasad Shabadi
 */

/**
 * GreedyReduction(form) function is called when the 'compute' button is clicked on
 * the GUI. Sanity check of the form data is performed here. The basic table
 * data for the tablet page is also constructed.
 * 
 * @param form
 */
function GreedyReduction(form) {

	readForm(form);

	var TheTop = "<HTML><HEAD><TITLE>Tablet</TITLE></HEAD>\n";
	TheTop += "<BODY bgcolor=white>\n";
	var Middle;
	var Bottom;
	Bottom = "</BODY> </HTML>";

	if (isNaN(Nbits) || Nbits <= 0) {
		Middle = PrintError("Total bits must be a positive number")
	} else if (Nbits < 3) {
		Middle = PrintError("Partial Production Tree Not Required for Number of bits less than 3");
	} else if (isNaN(h_a2s) || h_a2s <= 0) {
		Middle = PrintError("Gate delay must be a positive number");
	} else if (isNaN(h_a2c) || h_a2c <= 0) {
		Middle = PrintError("Gate delay must be a positive number");
	} else if (isNaN(f_a2c) || f_a2c <= 0) {
		Middle = PrintError("Gate delay must be a positive number");
	} else if (isNaN(f_c2c) || f_c2c <= 0) {
		Middle = PrintError("Gate delay must be a positive number");
	} else if (isNaN(f_c2s) || f_c2s <= 0) {
		Middle = PrintError("Gate delay must be a positive number");
	} else if (isNaN(f_a2s) || f_a2s <= 0) {
		Middle = PrintError("Gate delay must be a positive number");
	}

	else if (h_a2s < h_a2c) {
		Middle = PrintError("3-Greedy Algorithm implemented in this simulator assumes \n SUM delay to be equal to or greater than CARRY delay. \n <br> <p> \n Please enter SUM delay greater than or equal to CARRY delay </p> </br>")
	} else if ((f_a2s < f_a2c) || (f_c2s < f_a2c)) {
		Middle = PrintError("3-Greedy Algorithm implemented in this simulator assumes \n SUM delay to be equal to or greater than CARRY delay. \n <br> <p> \n Please enter SUM delay greater than or equal to CARRY delay </p> </br>")
	} else if ((f_a2s < f_c2c) || (f_c2s < f_c2c)) {
		Middle = PrintError("3-Greedy Algorithm implemented in this simulator assumes \n SUM delay to be equal to or greater than CARRY delay. \n <br> <p> \n Please enter SUM delay greater than or equal to CARRY delay </p> </br>")
	} else {
		Middle = CreateTable(form);
		Middle += "<p> \n The maximum delay in generting all inputs to CPA = "
				+ MaximumDelay + " </p> \n";
		Middle += "<p> \n Total Number of Half Adders = " + TotalHalfAdders
				+ "</p> \n";
		Middle += "<p> \n Total Number of Full Adders = " + TotalFullAdders
				+ "</p> \n";
		Middle += "<p> \n Total Number of Components = " + TotalPPComponents
				+ "</p> \n";
	}

	parent.frames[1].document.open()
	parent.frames[1].document.write(TheTop);
	parent.frames[1].document.write(Middle);
	parent.frames[1].document.write(Bottom);
	parent.frames[1].document.close()

}

/**
 * CreateTable(form) function creates the basic html table setup.
 * 
 * @param form
 * @returns {String}
 */
function CreateTable(form) {
	var TableStr = "<TABLE border=1>\n";
	TableStr += GenerateColummHeaders();
	TableStr += GenerateTableEntries();
	TableStr += "</TABLE>";
	return TableStr;
}

/**
 * readForm(form) function reads the input form and collects all the user
 * entered data.
 * 
 * @param form
 */
function readForm(form) {
	Nbits = parseInt(form.Nbits.value);
	h_a2s = parseFloat(form.HA_AToSum.value);
	h_a2c = parseFloat(form.HA_AToCout.value);
	f_a2c = parseFloat(form.FA_AToCout.value);
	f_c2c = parseFloat(form.FA_CinToCout.value);
	f_c2s = parseFloat(form.FA_CinToSum.value);
	f_a2s = parseFloat(form.FA_AToSum.value);

}

/**
 * GenerateColummHeaders() function creates the actual table headers.
 */
function GenerateColummHeaders() {
	var internalHead;
	internalHead = "<Th bgcolor=\"#EAEAAF\" text=\"black\">VCS Number</Th>\n ";
	internalHead += "<Th bgcolor=\"#EAEAAF\" text=\"black\">#Initial of PPs</Th>\n";
	internalHead += "<Th bgcolor=\"#EAEAAF\" text=\"black\">#Carries</Th>\n";
	internalHead += "<Th bgcolor=\"#EAEAAF\" text=\"black\">[CDV]</Th>\n";
	internalHead += "<Th bgcolor=\"#EAEAAF\" text=\"black\">[C,S]</Th>\n";
	internalHead += "<Th bgcolor=\"#EAEAAF\" text=\"black\">#HA</Th>\n";
	internalHead += "<Th bgcolor=\"#EAEAAF\" text=\"black\">#FA</Th>\n";
	internalHead += "<Th bgcolor=\"#EAEAAF\" text=\"black\">#Totol</Th>\n";
	return internalHead;
}

/**
 * GenerateTableEntries() function parses the form data and implements the
 * primary portion of the greedy algorithm. Details of the algorithm can
 * be found in reference [1] on the help page.
 * 
 * @returns {String}
 */
function GenerateTableEntries() {
	createPartialProductTree();

	var AllTableEntries = " ";
	var ColStart = "<TD>";
	var ColEnd = "</TD>\n";
	for ( var CurrColumnIndex = 2; CurrColumnIndex <= ColumnCount - 2; CurrColumnIndex++) {
		var CurrColumnLength = 0;
		for ( var index in ColumnList[CurrColumnIndex].items) {
			CurrColumnLength++;
		}

		if (CurrColumnLength % 2 == 0) {
			halfAdderOperator(CurrColumnIndex);
		}

		CurrColumnLength = 0;
		for ( var index in ColumnList[CurrColumnIndex].items) {
			CurrColumnLength++;
		}
		var NewCurrColumnLength = 0;
		NewCurrColumnLength = CurrColumnLength;
		while (NewCurrColumnLength > 3) {
			fullAdderOperator(CurrColumnIndex);
			NewCurrColumnLength = 0;
			for ( var index in ColumnList[CurrColumnIndex].items) {
				NewCurrColumnLength++;
			}
		}

	}
	GenerateFinalInputsToCPA();

	CalculateSummaryInfo();

	for ( var CurrIndex = 2; CurrIndex <= ColumnCount - 2; CurrIndex++) {
		var CurrCarryVector = "[";
		if (VCSList[CurrIndex].carryVector.length > 0) {
			for ( var carryIndex = 0; carryIndex < VCSList[CurrIndex].carryVector.length - 1; carryIndex++) {
				CurrCarryVector += VCSList[CurrIndex].carryVector[carryIndex];
				CurrCarryVector += ",";
			}
			CurrCarryVector += VCSList[CurrIndex].carryVector[VCSList[CurrIndex].carryVector.length - 1];
		} else {
			CurrCarryVector += " ";
		}
		CurrCarryVector += "]";

		AllTableEntries += "<TR>\n";
		AllTableEntries += ColStart + VCSList[CurrIndex].VCSNumber + ColEnd;
		AllTableEntries += ColStart + VCSList[CurrIndex].InitialPPcount
				+ ColEnd;
		AllTableEntries += ColStart + VCSList[CurrIndex].carryVector.length
				+ ColEnd;
		AllTableEntries += ColStart + CurrCarryVector + ColEnd;
		AllTableEntries += ColStart + "[" + VCSList[CurrIndex].FinalCarryDelay
				+ "," + VCSList[CurrIndex].FinalSumDelay + "]" + ColEnd;
		AllTableEntries += ColStart + VCSList[CurrIndex].HAcount + ColEnd;
		AllTableEntries += ColStart + VCSList[CurrIndex].FAcount + ColEnd;
		AllTableEntries += ColStart + VCSList[CurrIndex].totalComponents
				+ ColEnd;
		AllTableEntries += "</TR>";
	}
	return AllTableEntries;
}
/**
 * CalculateSummaryInfo()function calculates the summary information to be
 * displayed at the bottom of the table.
 */
function CalculateSummaryInfo() {
	MaximumDelay = 0;
	TotalHalfAdders = 0;
	TotalFullAdders = 0;
	TotalPPComponents = 0;
	var delayList = new Array();
	for ( var CurrIndex = 2; CurrIndex <= ColumnCount - 2; CurrIndex++) {
		TotalHalfAdders = TotalHalfAdders * 1
				+ parseInt(VCSList[CurrIndex].HAcount);
		TotalFullAdders = TotalFullAdders * 1
				+ parseInt(VCSList[CurrIndex].FAcount);
		TotalPPComponents = TotalPPComponents * 1
				+ parseInt(VCSList[CurrIndex].totalComponents);
		delayList.push(VCSList[CurrIndex].FinalSumDelay);
		delayList.push(VCSList[CurrIndex].FinalCarryDelay);
	}
	delayList.sort(sortNumber);
	if (delayList.length > 0) {
		MaximumDelay = delayList[delayList.length - 1]
	}

}

/**
 * sortNumber(a, b) function is provided as input function for the Array.sort()
 * method for sorting the elements of the array in ascending order.
 * 
 * @param a
 * @param b
 * @returns {Number}
 */
function sortNumber(a, b) {
	return a - b;
}

/**
 * GenerateFinalInputsToCPA() function implements the final step of the
 * algorithm where the last 3 partial products go through a final step of full
 * adder reduction to produce final SUM and CARRY outputs. These signals are
 * provided as inputs to the CPA block for calculation of the final result.
 * 
 */
function GenerateFinalInputsToCPA() {

	for ( var CurrIndex = 2; CurrIndex <= ColumnCount - 2; CurrIndex++) {
		ColumnCountDummy = 0;

		for ( var key in ColumnList[CurrIndex].items) {
			ColumnCountDummy++;
		}
		if (ColumnCountDummy == 3) {
			var inputNodeNames = new Array();
			var inputNodeDelays = new Array();
			var dummyCount = 0;
			for ( var key in ColumnList[CurrIndex].items) {
				if (dummyCount <= 2) {
					dummyCount++;
					inputNodeNames.push(key);
					inputNodeDelays.push(ColumnList[CurrIndex].getItem(key));
					ColumnList[CurrIndex].removeItem(key);
				} else {
					break;
				}
			}
			var Ds = Math.max(inputNodeDelays[0] + f_a2s, inputNodeDelays[1]
					+ f_a2s, inputNodeDelays[2] + f_c2s);
			var Dc = Math.max(inputNodeDelays[0] + f_a2c, inputNodeDelays[1]
					+ f_a2c, inputNodeDelays[2] + f_c2c);
			var sumPP = "Sum" + CurrIndex;
			var CoutPP = "Cout" + CurrIndex;
			var xFA = new fullAdder(inputNodeNames[0], inputNodeNames[1],
					inputNodeNames[1], CoutPP, sumPP);
			VCSList[CurrIndex].componentsList.push(xFA);
			VCSList[CurrIndex].FAcount++;
			VCSList[CurrIndex].totalComponents++;
			VCSList[CurrIndex].finalSumPP = sumPP;
			VCSList[CurrIndex].finalCarryPP = CoutPP;
			VCSList[CurrIndex].FinalCarryDelay = Dc;
			VCSList[CurrIndex].FinalSumDelay = Ds;
		} 
	}

}

/**
 * createPartialProductTree(form) generates the initial partial products based
 * on the number of bits specified in the input form.
 * 
 * @param form
 */
function createPartialProductTree(form) {
	ColumnCount = ((2 * Nbits) - 1);
	ColumnList = new Array(ColumnCount);
	VCSList = new Array(ColumnCount);
	for ( var i = 0; i < ((2 * Nbits) - 1); i++) {
		VCSList[i] = new VCS(i);
	}

	InitializeColumns();
}

/**
 * InitializeColumns() function initializes all the columns with required number
 * of partial products by setting unique partial product names and corresponding
 * delays. Here the assumption is that all the input partial products are
 * available simultaneously.
 */
function InitializeColumns() {

	for ( var i = 0; i <= Nbits - 1; i++) {
		ColumnList[i] = new Hash();

		for ( var j = 0; j < i + 1; j++) {
			var nodetemp = "P" + i + j;
			ColumnList[i].setItem(nodetemp, 0);

		}
		var CurrColumnLength = 0;
		for ( var index in ColumnList[i].items) {
			CurrColumnLength++;
		}

		VCSList[i].InitialPPcount = CurrColumnLength;
	}

	for ( var i = Nbits; i <= 2 * Nbits - 2; i++) {
		ColumnList[i] = new Hash();

		for ( var j = 0; j < 2 * Nbits - 1 - i; j++) {
			var nodetemp = "P" + i + j;
			ColumnList[i].setItem(nodetemp, 0);
		}
		var CurrColumnLength = 0;
		for ( var index in ColumnList[i].items) {
			CurrColumnLength++;
		}

		VCSList[i].InitialPPcount = CurrColumnLength;

	}
}

/**
 * Hash() function is used to perform all the generic functions on the
 * associative arrays of partial products used algorithm. This can be used for
 * removing elements, adding elements, getting specific values, checking for
 * elements, sorting elements in ascending order. *
 * 
 * @returns {Hash}
 */
function Hash() {
	this.length = 0;
	this.items = new Array();
	for ( var i = 0; i < arguments.length; i += 2) {
		if (typeof (arguments[i + 1]) != 'undefined') {
			this.items[arguments[i]] = arguments[i + 1];
			this.length++;
		}
	}

	this.removeItem = function(in_key) {
		var tmp_previous;
		if (typeof (this.items[in_key]) != 'undefined') {
			this.length--;
			var tmp_previous = this.items[in_key];
			delete this.items[in_key];
		}

		return tmp_previous;
	}

	this.getItem = function(in_key) {
		return this.items[in_key];
	}

	this.setItem = function(in_key, in_value) {
		var tmp_previous;
		if (typeof (in_value) != 'undefined') {
			if (typeof (this.items[in_key]) == 'undefined') {
				this.length++;
			} else {
				tmp_previous = this.items[in_key];
			}

			this.items[in_key] = in_value;
		}

		return tmp_previous;
	}

	this.hasItem = function(in_key) {
		return typeof (this.items[in_key]) != 'undefined';
	}

	this.valueSortedItems = function()

	{
		var tempArr = new Array();
		sortedKeys = sortByValue(this.items);
		for ( var i = 0; i < sortedKeys.length; i++) {
			tempArr[sortedKeys[i]] = this.items[sortedKeys[i]];
		}
		this.items = tempArr;

	}

	function sortByValue(AssoItems) {

		var keyArray = new Array();

		for ( var keyindex in AssoItems) {
			keyArray.push(keyindex);
		}

		return keyArray.sort(function(a, b) {
			return AssoItems[a] - AssoItems[b];
		});
	}

	this.clear = function() {
		for ( var i in this.items) {
			delete this.items[i];
		}

		this.length = 0;
	}
}

/**
 * halfAdderOperator(ColumnNumber) function implements one of the key steps of
 * the greedy algorithm. Here top 2 partial products are reduced using the
 * half adder operation and corresponding column information is updated.
 * 
 * @param ColumnNumber
 */
function halfAdderOperator(ColumnNumber) {
	var inputNodeNames = new Array();
	var inputNodeDelays = new Array();

	var dummyCount = 0;
	for ( var key in ColumnList[ColumnNumber].items) {
		if (dummyCount <= 1) {
			dummyCount++;
			inputNodeNames.push(key);
			inputNodeDelays.push(ColumnList[ColumnNumber].getItem(key));
			ColumnList[ColumnNumber].removeItem(key);
		} else {
			break;
		}
	}

	var Ds = Math.max(inputNodeDelays[0] + h_a2s, inputNodeDelays[1] + h_a2s);
	var Dc = Math.max(inputNodeDelays[0] + h_a2c, inputNodeDelays[1] + h_a2c);
	var sumPP = "N" + ColumnNumber + VCSList[ColumnNumber].newPPCount;
	var nextColumn = ColumnNumber + 1;
	var CoutPP = "N" + nextColumn + VCSList[ColumnNumber + 1].newPPCount;

	var xHA = new halfAdder(inputNodeNames[0], inputNodeNames[1], CoutPP, sumPP);
	VCSList[ColumnNumber].componentsList.push(xHA);
	VCSList[ColumnNumber].HAcount++;
	VCSList[ColumnNumber].totalComponents++;
	VCSList[ColumnNumber].carryVector.push(Dc);
	ColumnList[ColumnNumber].setItem(sumPP, Ds);
	VCSList[ColumnNumber].newPPCount++;
	ColumnList[ColumnNumber + 1].setItem(CoutPP, Dc);
	VCSList[ColumnNumber + 1].newPPCount++;
	ColumnList[ColumnNumber].valueSortedItems();
	ColumnList[ColumnNumber + 1].valueSortedItems();
	//
	VCSList[ColumnNumber].FinalCarryDelay = Dc;
	VCSList[ColumnNumber].FinalSumDelay = Ds;

}

/**
 * fullAdderOperator(ColumnNumber) function implements one of the key steps of
 * the greedy algorithm. Here top 3 partial products are reduced using the
 * full adder operation and corresponding column information is updated.
 * 
 * @param ColumnNumber
 */

function fullAdderOperator(ColumnNumber) {
	var inputNodeNames = new Array();
	var inputNodeDelays = new Array();
	var dummyCount = 0;
	for ( var key in ColumnList[ColumnNumber].items) {
		if (dummyCount <= 2) {
			dummyCount++;
			inputNodeNames.push(key);
			inputNodeDelays.push(ColumnList[ColumnNumber].getItem(key));
			ColumnList[ColumnNumber].removeItem(key);
		} else {
			break;
		}
	}
	var Ds = Math.max(inputNodeDelays[0] + f_a2s, inputNodeDelays[1] + f_a2s,
			inputNodeDelays[2] + f_c2s);
	var Dc = Math.max(inputNodeDelays[0] + f_a2c, inputNodeDelays[1] + f_a2c,
			inputNodeDelays[2] + f_c2c);
	var sumPP = "N" + ColumnNumber + VCSList[ColumnNumber].newPPCount;
	var nextColumn = ColumnNumber + 1;
	var CoutPP = "N" + nextColumn + VCSList[ColumnNumber + 1].newPPCount;

	var xFA = new fullAdder(inputNodeNames[0], inputNodeNames[1],
			inputNodeNames[1], CoutPP, sumPP);
	VCSList[ColumnNumber].componentsList.push(xFA);
	VCSList[ColumnNumber].FAcount++;
	VCSList[ColumnNumber].totalComponents++;
	VCSList[ColumnNumber].carryVector.push(Dc);

	dummy = 0;
	for ( var index in ColumnList[ColumnNumber].items) {
		dummy++;
	}
	ColumnList[ColumnNumber].setItem(sumPP, Ds);
	VCSList[ColumnNumber].newPPCount++;
	ColumnList[ColumnNumber + 1].setItem(CoutPP, Dc);
	VCSList[ColumnNumber + 1].newPPCount++;

	dummy = 0;
	for ( var index in ColumnList[ColumnNumber].items) {
		dummy++;
	}
	ColumnList[ColumnNumber].valueSortedItems();
	ColumnList[ColumnNumber + 1].valueSortedItems();
	//
	VCSList[ColumnNumber].FinalCarryDelay = Dc;
	VCSList[ColumnNumber].FinalSumDelay = Ds;

}

/**
 * halfAdder(in1Net, in2Net, CoutNet, SumNet) function is used to store the
 * connectivity information for individual half adder instances in the reduction
 * tree.
 * 
 * @param in1Net
 * @param in2Net
 * @param CoutNet
 * @param SumNet
 * @returns {halfAdder}
 */
function halfAdder(in1Net, in2Net, CoutNet, SumNet) {
	this.Componenttype = "HA";
	this.inputA = in1Net;
	this.inputB = in2Net;
	this.CarryOut = CoutNet;
	this.Sum = SumNet;
}

/**
 * fullAdder(in1Net, in2Net, in3Net, CoutNet, SumNet) function is used to store
 * the connectivity information for individual full adder instances in the
 * reduction tree.
 * 
 * @param in1Net
 * @param in2Net
 * @param in3Net
 * @param CoutNet
 * @param SumNet
 * @returns {fullAdder}
 */
function fullAdder(in1Net, in2Net, in3Net, CoutNet, SumNet) {
	this.Componenttype = "FA";
	this.inputA = in1Net;
	this.inputB = in2Net;
	this.inputCin = in3Net;
	this.CarryOut = CoutNet;
	this.Sum = SumNet;
}

/**
 * VCS(number)function is used to store all the summary information of every
 * column. This information is upodated at all stages of the algorithm flow.
 * Final table entries are displayed by parsing these elements.
 * 
 * @param number
 * @returns {VCS}
 */
function VCS(number) {
	this.VCSNumber = number;
	this.InitialPPcount = 0;
	this.componentsList = new Array();
	this.HAcount = 0;
	this.FAcount = 0;
	this.totalComponents = 0;
	this.FinalCarryDelay = 0;
	this.FinalSumDelay = 0;
	this.carryVector = new Array();
	this.newPPCount = 0;
	this.finalSumPP = 0;
	this.finalCarryPP = 0;
}

/**
 * PrintError(Message) is a generic function used to display error messages when
 * the form data is invalid.
 * 
 * @param Message
 * @returns
 */
function PrintError(Message) {
	Error = "<CENTER><H1> ERROR </H1></CENTER> <P>\n";
	Error += "<CENTER> <EM>" + Message + "</EM> </CENTER>\n";
	return Error;
}
