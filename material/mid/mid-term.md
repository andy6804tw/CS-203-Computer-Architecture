### 1. Please explain the three kinds of hazards:
```
A. Structural hazards
B. Data hazards
C. Control hazards
```

```
A. 硬體無法接受指令集的架構，需要更多的硬體資源。
B. 指令需要上一個指令的結果，但他還在pipeline中。
C. 因為抓指令所造成的延遲或是branch和jumps的選擇。

Give two common methods for eliminating structure hazards.
Duplicate resources、 Pipeline the resource、 Reorder the instructions
```

### 2. Please explain the difference between Basic Branch Prediction Buffer and Correlating Branch Predictors.

```
Basic Branch Prediction Buffer: 記錄著最近的分支是否有 taken，並且去預測未來的分支。
Correlating Branch Predictors: 除考慮本身branch的過去執行行為外， 也考慮到鄰近於此branch的執行結果。
```


### 3. Please explain Moore’s Law and Amdahl’s Law.

```
Moore’s Law: 摩爾定律在IC上可容納的電晶體數目，約每隔 18 個月便會增加一倍，性能也將提升一倍。

Amdahl’s Law: Speedup=執行時間B/執行時間A=1/((1-F)+F/S)

F: 可改善比例
S: 改善多少
```

### 4. Please explain Temporal Locality and Spatial Locality. And give an example for each.
```
Temporal Locality (時間區域性): 一個記憶體位址被存取後,不久會再度被存取
(e.g., loops, reuse)

Spatial Locality (空間區域性): 一個記憶體位址被存取後,不久其附近的記憶體位址也會被存取。 (e.g., straight-line code, array access)
```

### 5. Please compare the difference between dynamic scheduling and static scheduling.

```
dynamic scheduling: 重新排列指令順序以減少stall。
- Simpler compiler
- Handles dependencies not known at compile time
- Allows code compiled for a different machine to run efficiently.

```

### 6. Please describe the Drawbacks of Tomasulo.
```
1. 複雜度高
2. associative store(CDB)需要高速和 common data bus 的速度限制
3. Non-precise interrupts
```

### 7. There are several types of limits to the gains that can be achieved by loop unrolling. Please illustrate three types and explain them in detail.

```
A. Decrease in amount of overhead amortized with each extra unrolling Growth in code size
B. For larger loops, concern it increases the instruction cache missrate 
C. Compiler
D. Register pressure: potential shortfall in registers created by aggressive unrolling and scheduling
```

### 8. Please explain why Dynamic Branch Prediction is better than Static Branch Prediction.

```
每個分支預測都會考慮先前的運行結果，且分支在執行過程中改變自己的預測行為。
```

### 9. What is an “Instruction Set Architecture”?
```
軟硬體之間的抽象介面
registers, memory addressing, addressing modes, instruction operands
```

### 10. Why is it very difficult to deal with dependence through memory location?

```
兩個Address可能指向相同的位置，但看起來不同
100(R4) and 20(R6)
或是兩個位置看起來相同但指向不同Address
20(R4) and 20(R4)
```

### 11. Please discuss 2-bit branch predictor.
```
連續兩個taken/not taken才會改變狀態
```

### 12. How can the scheme of Tomasulo’s approach can minimize RAW hazard and eliminate the stalls for WAW, and WAR hazards?
```
1. 先讀取現有可用的operands，然後儲存，之後再利用
2. 利用其它空間儲存執行完畢欲寫到暫存器的值，之前未讀取或是未寫入的指令就不會受影響
```