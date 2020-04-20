// Function to close all UI windows.
function closeUIwindows(){
    if (parent.Left_Matrix.UI_window != null && !parent.Left_Matrix.UI_window.closed){
        parent.Left_Matrix.UI_window.close();
    }
    if (parent.Right_Matrix.UI_window != null && !parent.Right_Matrix.UI_window.closed){
        parent.Right_Matrix.UI_window.close();
    }
}

// Function to set the size of the matrices.
function setMatrixSize(size){

    if (Simulation_State == "BEGIN"){
        resize_matrix = true;
    }  
    else {
        resize_matrix = confirm("Are you sure you want to resize the matricies?");
    }

    if (resize_matrix == true){
        // We will be creating a brand new matrix below.  
        // So if a child window exists it must be closed.
	closeUIwindows();

        // Create a new left matrix and display it. 
	parent.Left_Matrix = new Matrix(size, parent.top_left_frame);
        parent.top_left_frame.location.reload();

        // Create a new right matrix and display it. 
	parent.Right_Matrix = new Matrix(size, parent.top_right_frame);
        parent.top_right_frame.location.reload();
        
        // Reset the Simulation State      
        change_simulation_state("BEGIN");	

    }
    else {
        document.main_form.matrix_size_combo.value=parent.Left_Matrix.size;
    }
}

// Function used to perform the matrix operation.
function performMatrixOperation(){
    // Close any open UI before performing the operation.
    closeUIwindows();

    var operation;
    if (document.main_form.operation[0].checked == true){
        operation = document.main_form.operation[0].value;
    }
    else {
        operation = document.main_form.operation[1].value;
    }

    if (operation == "Addition"){
        parent.Computed_Matrix = matrix_add(parent.Left_Matrix, 
                                            parent.Right_Matrix, 
                                            parent.bottom_right_frame);
    }
    else {
        parent.Computed_Matrix = matrix_mult(parent.Left_Matrix, 
                                             parent.Right_Matrix,
                                             parent.bottom_right_frame);
    }

    change_simulation_state("MATRIX_OPERATION_COMPLETE");
}

// Function to compute the checksums for the two matrices.
function computeChecksumMatrices(){
    // Close any open UI before computing the checksum matrices.
    closeUIwindows();

    parent.Left_Matrix.compute_checksums();
    parent.Right_Matrix.compute_checksums();
    change_simulation_state("CHECKSUM_COMPUTED");
}

// Function to change the state of the simulation.
function change_simulation_state(new_state){
    if (new_state == "BEGIN"){
        document.main_form.matrixOperationButton.disabled = true;
        document.main_form.computeChecksumButton.disabled = false;
        Simulation_State = new_state; 
    }
    else if (Simulation_State == "BEGIN" && new_state == "MODIFIED"){
        Simulation_State = new_state;
    }
    else if (new_state == "CHECKSUM_COMPUTED"){
        document.main_form.matrixOperationButton.disabled = false;
        document.main_form.computeChecksumButton.disabled = true;
        Simulation_State = new_state;
    }
    else if (new_state == "MATRIX_OPERATION_COMPLETE"){
        Simulation_State = new_state;
    }
    else if (Simulation_State == "MATRIX_OPERATION_COMPLETE" && new_state == "MODIFIED"){
        Simulation_State = "CHECKSUM_COMPUTED";
    }

    parent.bottom_right_frame.location.href = "bottom_right.html";
}

// Function to set the matrices to random values.
function randomize_matrices(){
    if (Simulation_State == "BEGIN"){
        confirm_randomize = true;
    }  
    else {
        confirm_randomize = confirm("Are you sure you want to randomize the matrices?");
    }

    if (confirm_randomize == true){
	    closeUIwindows();
 
        input_ptr = document.main_form.random_decimal_places;
        num_decimal_places = parseFloat(input_ptr.options[input_ptr.selectedIndex].value);
        max_random_num = parseInt(document.main_form.random_number_max.value);
        
        parent.Left_Matrix.randomize(max_random_num, num_decimal_places);
        parent.Left_Matrix.display_size = parent.Left_Matrix.size;
        parent.top_left_frame.location.reload();
 
        parent.Right_Matrix.randomize(max_random_num, num_decimal_places);
        parent.Right_Matrix.display_size = parent.Right_Matrix.size;
        parent.top_right_frame.location.reload();
        
        // Reset the Simulation State      
        change_simulation_state("BEGIN");	
    }
}

// Function that is called when the page load is complete.
function loadComplete(){
    document.main_form.matrix_size_combo.value=default_matrix_size;
    document.main_form.random_number_max.value="10";
    change_simulation_state("BEGIN");
}

// Function to open the help window.
function OpenHelp(){

    if ((parent.help_window == null) || (parent.help_window.closed)){
        parent.help_window = window.open('help.html','help_window','width=500,height=500,toolbar=0,scrollbars=1');
    }
    else {
        parent.help_window.focus();
    }
}

// Function to closes the help window.
function CloseHelp(){
    if ((parent.help_window != null) && (!parent.help_window.closed)){
        parent.help_window.close();
    }
    parent.help_window = null; 
}

// Global variables.

default_matrix_size = 3;
var Simulation_State;

parent.Left_Matrix = new Matrix(default_matrix_size, parent.top_left_frame);
parent.Right_Matrix = new Matrix(default_matrix_size, parent.top_right_frame);
parent.top_left_frame.location="top_left.html";
parent.top_right_frame.location="top_right.html";

