## 1. 請說明何謂 hardware speculation
```
當還不確定 branch 指令的 outcome 時，以預測的方式猜測可能的 outcome，之後繼續執行這個路徑上的指令。 
```

## 2. 為了將原本的 Tomasulo 機制擴展成可以達到 hardware-based speculation 運 作，需要額外在原 Tomasulo 機制增加(a)甚麼階段與增加(b)甚麼硬體? (c)新的階段所要 做的任務是甚麼? 
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
Superscaler processors
very long instruction work processors
```

## 5. (a)請說明 Branch Target Buffer (BTB)機制 (b)與 Branch Prediction Buffer 相較， BTB 的好處與額外增加的成本是甚麼?
```
(a)每個 entry 中紀錄了目前指令的 PC 與所預測之接下來指令位置
(b)減少計算下個指令的時間，需要多額外空間做 PC 紀錄
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

# [Data-Level Parallelism in Vector, SIMD, and GPU Architectures]
## 1. 如果驗一個 vector processor 系統中有 4 個 memory bank，當欲從記憶體載入資料到一個 64 個 element 的暫存器時，資料在 bank 中如何放置時，會有最大的讀取時間。
```
如果欲載入的資料皆是從同一個 bank 中讀取，將會有此情況
```
## 2. 如果在可以放置相同電晶體個數的晶片中，分別用來設計一般的 CPU 與 GPU，為何在 GPU 上可以同時執行上百個執行緒，而在 CPU 上能夠執行的執行數只有 個數個。
```
GPU 中的執行緒所使用的硬體資源都很單純簡單，然而 CPU 的設計則相對複雜。
```