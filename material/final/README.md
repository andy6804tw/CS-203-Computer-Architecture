## 1. 請說明何謂 hardware speculation
```
當還不確定 branch 指令的 outcome 時，以預測的方式猜測可能的 outcome，之後繼續執行 這個路徑上的指令。 
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

