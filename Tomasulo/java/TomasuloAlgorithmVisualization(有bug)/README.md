Tomasulo algorithm is a very important hardware technique for dynamic scheduling of instructions. This project is about the visualization of Tomasolu algorithm so that we can see how this algorithm proceeds and make this algorithm easier to understand.

1. Key concepts of Tomasolu algorithm  
  - Tracking dependences to allow instructions execute as soon as operands are available, avoiding RAW hazards.  
  - Register renaming to avoid WAR and WAW hazards.  
2. Three Stages of Tomasulo algorithm  
  - Issue-get instruction from head of instruction queue.  
    If reservation station free (i.e. no structual hazard), control issues instruction & sends operands (renames registers). If no data dependence, send operand value to the RS, otherwise, keep track of the function unit that produces the result.
  - Execute-operate on operands (EX)
    When both operands ready then execute; if not ready, watch Common Data Bus for result.
  - Write result-finish exection (WB)
    Write on Common Data Bus to all awaiting units; mark reservation station available.  
3. Notice
  - In this project, there are only six types of instruction (LD, SD, ADD, DIV, MUL, DIV).
  - Execution time of six types of instruction.  
  
    | Instruction | Execution time|
    |---|---|
    |LD|2 clocks|
    |SD|2 clocks|
    |ADD/SUB|2 clocks|
    |MUL|10 clocks|
    |DIV|40 clocks|

    You can change the execution time of these six instructions in ```Util.java```.
  - By default, the number of each type of resource is set as follows:
  
    | Resource Type             | Available Number | Where you can change |
    | ---                       | ---              | ---                  |
    | Addition & Subtraction    | 3      | ```addNumber``` in ```ReservationStations.java``` |
    | Multiplication & Division | 2      | ```mulNumber``` in ```ReservationStations.java``` |
    | Load                      | 3      | ```loadNumber``` in ```LoadBuffers.java``` |
    | Store                     | 3      | ```storeNumber``` in ```StoreBuffers.java``` |

4. Procedure  
![](https://raw.githubusercontent.com/ylf951/CourseProjects/master/Tomasulo-Algorithm-Visualization/screenshots/Procedure.png)
5. Demo  
![](https://raw.githubusercontent.com/ylf951/CourseProjects/master/Tomasulo-Algorithm-Visualization/screenshots/Processing.gif)
