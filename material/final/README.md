## 1. 請說明何謂 hardware speculation
```
當還不確定 branch 指令的 outcome 時，以預測的方式猜測可能的 outcome，之後繼續執行這個路徑上的指令。 
```

## 2. 為了將原本的 Tomasulo 機制擴展成可以達到 hardware-based speculation 運作，需要額外在原 Tomasulo 機制增加(a)甚麼階段與增加 (b)甚麼硬體? (c)新的階段所要做的任務是甚麼?
```
(a) Commit 
(b) Reorder buffer 
(c) 當指令進到 commit 階段時，經判定該指令是必須執行的，則將記錄在 ROB entry 中的 結果存入記憶體或是暫存器，否則需要清除此 entry 之後的所有 entry
```

## 3. Reorder Buffer 是如何確保 precise interrupt model?
```
因為 ROB 可以讓指令 In order commit，當指令執行階段發生 interrupt 或是 exception 時， 相關訊息將會先被記錄但不處理，直到指令到達 commint 階段時，才處理。 
```

## 4. 給予可以使得 CPI<1 的兩個機制。
```
superscalar processors
very long instruction word processors
```

## 5. (a)請說明 Branch Target Buffer (BTB)機制 (b)與 Branch Prediction Buffer 相較， BTB 的好處與額外增加的成本是甚麼?
```
(a)每個 entry 中紀錄了目前指令的 PC 與所預測之接下來指令位置
(b)減少計算下個指令的時間，需要多額外空間做 PC 紀錄
```

## 6. (a)What step is added to Tomasulo Algorithm in order to implement the hardware- based speculation? (b)Besides, what hardware is needed to prevent any irrevocable action? (c) What interval does the hardware supply operands in? (d) What conditions are satisfied if an instruction achieves the added step? (e) What is done in the step?
```
(a) Commit
(b) Reorder buffer
(c) between completion of instruction execution and instruction commit
(d) Result is made and the instruction is at head
(e) Updeate the register or memory with the result in ROB
```

## 7. 請說明何謂 Hardware-Based Speculation，為何需要用到 Reorder buffer? 何時指 令才可以完成 commit 步驟? 萬一猜測錯誤，應該如何處理?
```
(a) 當還不確定分支指令的結果時，以預測的方式猜測可能的結果，並將結果存放在 Reorder buffer 中，等確定該指令是應該執行之後，才將 Reorder buffer 中的值更新到相關位置。
(b) 指令結果已產生並且已在 ROB 的最前端 
(c) 將預測執行的指令從 ROB 中刪除
```

## 8. 如果 load 與 store 指令存取相同記憶體位置的資料，將產生 hazard。應該如何做，才能避免 hazard?
```
load 要被執行時，應該先判斷是否在此之前有其他要寫入同一位置的 store 指令尚未完成
store 要執行時，應該先判斷是否在此之前有其他要讀取或是寫入同一位置的 load 或是 store 指令尚未完成
```

## 9. Speculation 預測下執行並不是對於任何 branch 之後的指令都是如此，請問會考量甚麼因素?
```
為避免猜錯時過度影響效能，如果需要花較多執行時間的指令，將先不執行
```

## 10. (a)請說明 Branch Target Buffer (BTB)機制 (b)如果想再加快速度，有甚麼方式?
```
(a)每個 entry 中紀錄了目前指令的 PC 與所預測之接下來指令位置
(b)直接紀錄上次的執行指令
```

# [Multiprocessors and Thread-Level Parallelism] 
## 1. 請說明何謂 Cache Coherency problem。
```
在不同處理器的 cache 中，所看到的同一變數之值不同
```

## 2. 比較在 Snoopy protocol 中，如果使用 Write invalidate protocol 或是 Write update protocol 有甚麼優缺點?
```
由於前者只傳送 invalidation 訊息，並不會將新的資料廣播給所有處理器知道，因此較節省頻寬。而後者需要傳送新資料至所有處理器，且要確保收到，時間成本大。
```

## 3. 請解釋 directory-based protocol 中的三種 node:local、home、remote nodes。
```
A. Local node: where a request originates
B. Home node: where the memory location and the directory entry of an address reside
C. Remote node: has a copy of a cache block, whether exclusive or shared
```

## 4. What is the coherence miss? The coherence miss can be divided into two subtypes: true sharing misses and false sharing misses. Please explain false sharing misses. And give a solution to false sharing misses.
```
(a) Miss caused due to the coherence protocol
(b) when a block is invalidated because some word in the block, other than the one being read, is written into One-word size.
```

## 5. We can use the write invalidate protocol or write update protocol in implement the coherence protocol. Please explain them. And which is better? Please give the reason.
```
A. 為了確保處理器在寫入數據項之前對其具有exclusive access(獨占訪問權)
B. 必須將新的訊息廣播到所有處理器中
```

# [Data-Level Parallelism in Vector, SIMD, and GPU Architectures]
## 1. 如果驗一個 vector processor 系統中有 4 個 memory bank，當欲從記憶體載入資料到一個 64 個 element 的暫存器時，資料在 bank 中如何放置時，會有最大的讀取時間。
```
如果欲載入的資料皆是從同一個 bank 中讀取，將會有此情況
```
## 2. 如果在可以放置相同電晶體個數的晶片中，分別用來設計一般的 CPU 與 GPU，為何在 GPU 上可以同時執行上百個執行緒，而在 CPU 上能夠執行的執行數只有 個數個。
```
GPU 中的執行緒所使用的硬體資源都很單純簡單，然而 CPU 的設計則相對複雜。
```

## 3. Vector processor 與 GPU 都可以在同一個時間點執行多個指令，但是架構上完全不同，請描述其差異。
```
前者 function unit 為 deeper pipeline 設計
後者 function unit 有多個
```

## 4. 請解釋為何不同的處理器在效能呈現上會有如以下的相同趨勢?
![](https://i.imgur.com/36BDhpD.png)

```
當 Arithmetic Intensity 逐漸增加時，每秒能夠完成的浮點數指令逐漸增加，但是受限於記憶 體頻寬的限制，之後就算 Arithmetic Intensity 繼續提升，也無法讓每秒指令執行個數增加。
```
## 5. 在 Vector processor 中的vector register 一次可以存放多的資料，請問以下指令執行時，執行行為如何?
```
不需等待整個 vector 暫存器中的所有 Element 都載入完畢，如有載入完成，即可開行執行乘法動作，只要完成乘法動作的，即可繼續往下做加法，依此類推。
```
## 6. 為何會有 Vector Mask Registers 的需求? (可以以例子說明)
```
並不是 vector register 中的所有 element 都要做相同的運算，有時會依據條件決定是否要執行運算，不須運算者會在 Mask Registers 註明 disable。
for (i = 0; i < 64; i=i+1)
    if (X[i] != 0)
        X[i] = X[i] – Y[i];
```