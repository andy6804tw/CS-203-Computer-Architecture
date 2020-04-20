// ********************** Global Funcations **********************

// Functions for Floating Point Rounding
function Stretch(Q, L, c) { var S = Q
 if (c.length>0) while (S.length<L) { S = c+S }
 return S
}
function StrU(X, M, N) { // X>=0.0
 var T, S=new String(Math.round(X*Number("1e"+N)))
 if (S.search && S.search(/\D/)!=-1) { return ''+X }
 with (new String(Stretch(S, M+N, '0')))
 return substring(0, T=(length-N)) + '.' + substring(T)
}
function Sign(X) { return X<0 ? '-' : ''; }
function StrS(X, M, N) { return Sign(X)+StrU(Math.abs(X), M, N) }
Number.prototype.toFixed= new Function('n','return StrS(this,1,n)')


// Checksum Error Object
function checksumError(row, col){
    this.row = row;
    this.col = col;
} 


// Function used to create the GUI that is used to enter matrix info
function create_UI(theMatrixName, theDocument){
    whichMatrix = theMatrixName.substring(7,11);

    if (whichMatrix != "Left"){
        whichMatrix = "Right";
    }

    theDocument.writeln("<HTML><HEAD><TITLE>" + whichMatrix + " Matrix</TITLE>");
    theDocument.writeln("<SCRIPT LANGUAGE=\"JavaScript\">");
    theDocument.writeln("<\/SCRIPT><\/HEAD>");
    
    theDocument.writeln("<BODY><center><form name='change'><table border=1>");
    theMatrix = eval(theMatrixName);

    text_width = 10;
    
    size = theMatrix.display_size;
    for (i = 0 ; i < size ; i++){
	theDocument.writeln("<tr>");
        for (j = 0 ; j < size ; j++){
           theDocument.writeln("<td><input name='column" + j + "' value='")
           if (IsFloat(theMatrix.data[i][j])){
               theDocument.write(theMatrix.data[i][j].toFixed(theMatrix.decimal_places));
           }
           else {
               theDocument.write(theMatrix.data[i][j]);
           } 

           theDocument.write("' onchange='window.opener." + theMatrixName 
                + ".change(" + i + "," + j + ",parseFloat(this.value))" 
                + "' size='" + text_width + "'><\/td>");
        }
        theDocument.writeln("<\/tr>");        
    }

    theDocument.writeln("<\/table><\/form><\/center><\/BODY>"); 
}


// Function to add two matrices together.  
function matrix_add(matrix_left, matrix_right, theFrame){
    if (matrix_left.size != matrix_right.size){
        return false;
    }

    if (matrix_left.decimal_places > matrix_right.decimal_places){
        output_decimal_places = matrix_left.decimal_places;
    }
    else {
        output_decimal_places = matrix_right.decimal_places;
    }

    size = matrix_left.size;
    output = new Matrix(size, theFrame);

    checksum_size = matrix_left.checksum_size;

    for (i = 0 ; i < checksum_size ; i++){
        for (j = 0 ; j < checksum_size ; j++){
            temp = matrix_left.data[i][j] + matrix_right.data[i][j];
            output.data[i][j] = temp;
        }
    }

    output.display_size = matrix_left.display_size;
    output.calculate_decimal_places();
    
    if (output_decimal_places != 0 && output_decimal_places < output.decimal_places){
        output.decimal_places = output_decimal_places;
    }    

    output.calculate_column_width();
    return output;
}

// Function to multiply two matrices together.
function matrix_mult(matrix_left, matrix_right, theFrame){
    if (matrix_left.size != matrix_right.size){
        return false;
    }

    output_decimal_places = matrix_left.decimal_places + matrix_right.decimal_places;

    size = matrix_left.size;
    output = new Matrix(size, theFrame);

    checksum_size = matrix_left.checksum_size;

    for (a = 0 ; a < checksum_size ; a++){
	for (b = 0 ; b < checksum_size ; b++){
            var temp = 0;
            for (c = 0 ; c < checksum_size ; c++){
                temp += matrix_left.data[a][c] * matrix_right.data[c][b];
            }
			
            output.data[a][b] = temp;
        }
    }

    output.display_size = matrix_left.display_size;
    output.calculate_decimal_places();
    
    if (output_decimal_places != 0 && output_decimal_places < output.decimal_places){
        output.decimal_places = output_decimal_places;
    }

    output.calculate_column_width();
    return output;
}

// Function to determine if a number is a float.
function IsFloat(theNumber){
    theNumberString = theNumber.toString();

    // If the number is a floating point number.
    if (theNumber != parseInt(theNumberString) && theNumber == parseFloat(theNumberString)){
        return true;
    }
    else {
        return false;
    }
}

// Function that returns the number of decimal places in a number.
function GetDecimalPlaces(theNumber){
    theNumberString = theNumber.toString();
    decimal_places = 0;

    if(IsFloat(theNumber)){
        myArray = theNumberString.split(".");
        if (myArray.length > 1){
            // Make sure we handle numbers containing scientific notation.     
            myArray1 = myArray[1].split("e");
            if (myArray1.length > 1){
                decimal_places += myArray1[0].length;
                decimal_places += Math.abs(parseInt(myArray1[1]));
            }
            else {          
                decimal_places += myArray[1].length;
            }
        }
        // Handle numbers that don't contain a decimal point but are still in 
        // scientific notation.
        else{
                
            myArray = theNumberString.split("e");
            if (myArray.length > 1){ 
                decimal_places += Math.abs(parseInt(myArray[1]));
            }
        }
    } 
    return decimal_places;
}


// Return the granularity of the input number. 
// (How many decimal places can there be before reaching the accuracy limit.)
function calculate_granularity(theNumber){
    var granularity = 1;

    if (IsFloat(theNumber)){
        theNumberString = theNumber.toString();
        theDecimalLength = GetDecimalPlaces(theNumber);

        myArray = theNumberString.split("e");
        if (myArray.length > 1){
            myArray1 = myArray[0].split(".");
            if (myArray1.length > 1){
                theNumberLength = myArray[0].length - 1; 
            }
            else {
                theNumberLength = myArray[0].length;
            }
            theNumberLength += Math.abs(parseInt(myArray[1]));
        }
        else {
            theNumberLength = theNumberString.length - 1;
        }
        
        // 15 is the accuracy limit.
        if (theNumberLength > 15){
            granularity = 15 - (theNumberLength - theDecimalLength);
        }
        else {
            granularity = theDecimalLength;
        }      
    }

    return granularity;
}

// Check the equality of two numbers
function CheckEquality(theNumber1, theNumber2, theGranularity){
    // If we have a large number in scientific notation.
    number1_e = theNumber1.toString().split("e+");
    if (number1_e.length > 1){
        number2_e = theNumber2.toString().split("e+");
        if (number2_e.length > 1){
            test = Math.abs(number1_e[0] - number2_e[0]);       
            limit = parseFloat("1e-" + theGranularity);

            if (test <= limit){
                return true;
            } 
        }
    }  
      
    else {
        // If both numbers are integers.
        if (!IsFloat(theNumber1) && !IsFloat(theNumber2)){
            if (theNumber1.toString().length > 15){
                theNumber1 = parseInt(theNumber1.toString().substring(0,15));
            }
            if (theNumber2.toString().length > 15){
                theNumber2 = parseInt(theNumber2.toString().substring(0,15));
            }
            
            limit = 1;
            test = Math.abs(theNumber1 - theNumber2)

            // If the numbers being compared are within one integer.  
            // This is  correct since we are only dealing with integers here.
            if (test < limit){
                return true;
            }  

        }        
        // Handle all other floating point numbers.  
        // (Includes small numbers in scientific notation.)
        else {
            limit = parseFloat("1e-" + theGranularity);
            test = Math.abs(theNumber1 - theNumber2)

            if (test <= limit){
                return true;
            }  
        }
    }
    return false;
}


// ********************** Member Funcations **********************

// Function to print this matrix.
function print_matrix(){
    size = this.display_size;
    checksum_size = this.checksum_size;
    theDocument = this.print_frame.document
    theDocument.writeln("<table border=1>");
    for (i = 0 ; i < size ; i++){
        theDocument.write("<tr>");
	for (j = 0 ; j < size ; j++){
            textcolor = "";
            theDocument.write("<td align='center' width='" + 10 * this.column_width + "'");
            if (i == checksum_size - 1 || j == checksum_size - 1){
                theDocument.write(" bgcolor='silver' ");
                for (x = 0 ; x < this.num_checksum_errors ; x++){
                    if (this.checksum_errors[x].row == i && this.checksum_errors[x].col == j){
                        textcolor = " color = \"red\"";
                        break;
                    }
                }
            }
            theDocument.write("><font" + textcolor + ">");
            if (IsFloat(this.data[i][j])){
                theDocument.write(this.data[i][j].toFixed(this.decimal_places));
            }
            else {
                theDocument.write(this.data[i][j]);
            } 
            theDocument.write( "</font></td>"); 
        }
        theDocument.write("</tr>");
    }
    theDocument.write("</table>");
}

// Function to randomize the values in this matrix.
function randomize_matrix(max, num_decimal_places){
    size = this.size;
    for (i = 0 ; i < size ; i++){
	for (j = 0 ; j < size ; j++){    
            temp = Math.random() * max;
            this.data[i][j] = parseFloat(temp.toFixed(num_decimal_places));
        }
    } 
    this.calculate_decimal_places();
    this.calculate_column_width();
}

// Function used to change a value in this matrix.
function change_matrix(row, col, new_value){
    this.data[row][col] = new_value;
    
    this.calculate_decimal_places();
    this.calculate_column_width();

    this.print_frame.update_in_progress = true;
    this.print_frame.location.reload();
}

// Function used to calculate the column width of this matrix.
function calculate_column_width(){
    size = this.display_size;
    var column_width = 0;
    var cur_length;
    
    for (i = 0 ; i < size ; i++){
	for (j = 0 ; j < size ; j++){
            cur_data = this.data[i][j].toFixed(this.decimal_places);  

            cur_length = cur_data.length;
            if (cur_length > column_width){
                column_width = cur_length;
            }
        }
    }

    this.column_width = column_width;
}

// Function used to calculate the decimal places of this matrix.
function calculate_decimal_places(){
    size = this.size;
    var decimal_places = 0;
  
    for (i = 0 ; i < size ; i++){
	for (j = 0 ; j < size ; j++){
            temp = calculate_granularity(this.data[i][j]);

            if (temp > decimal_places){
                decimal_places = temp;
            }
        }
    }

    this.decimal_places = decimal_places;
}


// Function used to compute the checksums for this matrix.
function compute_checksums(){
    size = this.size;
    checksum_size = this.checksum_size;
    var checksum;    

    // Compute the checksum over the rows for the matrix size.
    for (i = 0 ; i < size ; i++){
        checksum = 0;
    	for (j = 0 ; j < size ; j++){
            checksum += this.data[i][j];
        }
        this.data[i][(checksum_size - 1)] = checksum;
    }
    
    // Compute the checksum over the columns of the checksum 
    // matrix size.  This is done so that we get a valid 
    // checksum in the bottom right cell of the matrix.
    // (Even though we really don't use the bottom right cell,
    //  we don't want it to be blank.)
    for (j = 0 ; j < checksum_size ; j++){
        checksum = 0;
        for (i = 0 ; i < size ; i++){
            checksum += this.data[i][j];
        }
        this.data[(checksum_size - 1)][j] = checksum;
    }

    this.display_size = checksum_size;
    this.calculate_column_width();

    this.print_frame.update_in_progress = true;
    this.print_frame.location.reload();
}

// Function used to validate the checksums after a matrix operation.
function validate_checksums(){
    size = this.size;
    checksum_size = this.checksum_size;
    var checksum; 
    return_value = true;

    // Validate the checksum over the rows in the matrix.
    for (i = 0 ; i < size ; i++){
        checksum = 0;
    	for (j = 0 ; j < size ; j++){
            checksum += this.data[i][j];
        }

        checksum_granularity = calculate_granularity(checksum);
        data_granularity = calculate_granularity(this.data[i][(checksum_size - 1)]);

        if (checksum_granularity < data_granularity){
            checksum_validate_granularity = checksum_granularity;
        }
        else {
            checksum_validate_granularity = data_granularity;
        }        

        if ((checksum.toFixed(0).length - 1 + this.decimal_places) >= 15
         || (this.data[i][(checksum_size - 1)].toFixed(0).length - 1 + this.decimal_places) >= 15 ){
            this.checksum_rounded = true;
        }

        if (CheckEquality(this.data[i][(checksum_size - 1)], checksum, checksum_validate_granularity) == false){
            //alert(this.data[i][(checksum_size - 1)] +" - " + checksum + " : " + checksum_validate_granularity);
            this.checksum_errors[this.num_checksum_errors] = new checksumError(i, checksum_size - 1);
            this.num_checksum_errors++;
            return_value = false;
        }
    }
    
    // Validate the checksum over the columns in the matrix. 
    for (j = 0 ; j < size ; j++){
        checksum = 0;
        for (i = 0 ; i < size ; i++){
            checksum += this.data[i][j];
        }

        checksum_granularity = calculate_granularity(checksum);
        data_granularity = calculate_granularity(this.data[(checksum_size - 1)][j]);

        if (checksum_granularity < data_granularity){
            checksum_validate_granularity = checksum_granularity;
        }
        else {
            checksum_validate_granularity = data_granularity;
        }        

        if ((checksum.toFixed(0).length - 1 + this.decimal_places) >= 15
         || (this.data[(checksum_size - 1)][j].toFixed(0).length - 1 + this.decimal_places) >= 15 ){
            this.checksum_rounded = true;
        }

        if (CheckEquality(this.data[(checksum_size - 1)][j], checksum, checksum_validate_granularity) == false){
            //alert(this.data[(checksum_size - 1)][j] +" - " + checksum + " : " + checksum_validate_granularity);
            this.checksum_errors[this.num_checksum_errors] = new checksumError(checksum_size - 1, j);
            this.num_checksum_errors++;
            return_value = false;
        }
    }

    return return_value;
}



// ********************** Matrix Object **********************

function Matrix(size, theFrame){
    this.size = size;
    this.checksum_size = size + 1;
    this.display_size = size;
    this.data = new Array(size);
    this.print = print_matrix;
    this.randomize = randomize_matrix;
    this.change = change_matrix;
    this.print_frame = theFrame;
    this.compute_checksums = compute_checksums;
    this.validate_checksums = validate_checksums;
    this.UI_window = null;
    this.checksum_errors = new Array(size+size+1);
    this.num_checksum_errors = 0;
    this.column_width = 0;
    this.decimal_places = 0;
	this.checksum_rounded = false;
    this.calculate_column_width = calculate_column_width;
    this.calculate_decimal_places = calculate_decimal_places;

    checksum_size = size + 1;

    for (i = 0 ; i < checksum_size ; i++){
        this.data[i] = new Array(checksum_size);
    }

    for (i = 0; i < checksum_size ; i++){
	for (j = 0 ; j < checksum_size ; j++){
            this.data[i][j] = 10;
        }
    }
    this.calculate_column_width();
}



