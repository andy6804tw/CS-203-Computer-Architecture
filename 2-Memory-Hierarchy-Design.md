# Memory Hierarchy Design
1. block: cache中資訊表示的最小單位。
2. word是處理器指令集存取memory的單位，一個word是4個bytes。
3. 四種儲存技術：SRAM, DRAM, Flash, Magnetic disk。
4. 三種associativity method: directed-mapped, set-associative, full-associative。
5. cache中的地址由三部分組成：index, tag 和 byte offset。
6. 計記憶體架構的兩大原則，Temporal Locality和Spatial Locality

## 記憶體設計的原則
當初記憶體為什麼要像現在設計成階層式的架構？很簡單，因為我們發現我們寫的程式在存取記憶體的過程中，有兩大現象：

- temporal locality(時間的局部性):一個記憶體位址被存取後,不久會再度被存取，也就是說剛剛用過的記憶體很容易再被使用。
  - ex:迴圈,副程式,以及堆疊,迴圈控制變數,計算總合變數
- spatial locality(空間的局部性):一個記憶體位址被存取後,不久其附近的記憶體位址也會被存取。如果一個記憶體剛剛使用過，他附近的記憶體位址也很可能被使用到。
  - ex:循序指令,以及陣列,相關的變數

![](https://i.imgur.com/6wqhGGI.png)

我們把資料一次從記憶體下層轉移到記體上層的單位定作block。如果處理器要求讀取某個block的資料，剛好在上層的記憶體內，那就稱為hit。如果不在上層，那就稱為miss。hit rate就是你成功在上層記憶體就找到你要的資料的次數比例。相反的就是miss rate。hit rate + miss rate = 1。

另一個對電腦效能來說影響重大的因素就是hit time和miss penalty

- hit time
  - 判斷記憶體是否hit + 把上層資料搬到處理器的時間
- miss penalty
  - 把下層記憶體的資料搬到上層 + 上層記憶體資料搬到處理器的時間

## 為什麼需要 cache?
早期，main memory 的速度非常慢而且也非常貴，但當時的 CPU 速度也慢，因此還未有 cache 出現。但從 1980 年代開始，main memory 和 CPU 的差距急速拉大(如下圖)，main memory 的速讀雖有提升，卻仍不及 CPU 的進展，因此需要 cache 來補強兩者間的差距。

![](https://i.imgur.com/9O3rnCZ.png)

## cache設計的概念
“cache” 是為了讓資料存取的速度適應 CPU 的處理速度，允許 CPU 直接到 cache memory 查看所需資料是否存在。若是，則直接存取不必再到 main memory —— 減少到 main memory 存取的次數，解決 main memory 存取不夠快的問題。因此才有SRAM的出現。

處理器怎麼知道data是否在cache中，並且正確的從cache抓出想要的資料？

## 三種cache的配置(Cache Associativity)：
快取記憶體的動態位址轉換，採用硬體實作的技術來完成，每個 CPU 包含 tag RAM，用來紀錄一個 memory block 要對映到那一個 cache block。
![](https://pic3.zhimg.com/80/90bf0022f6523251334ad507324873e6_720w.jpg)

### 1. Direct Mapped 
每個 cache block 僅可以對應到唯一的一個 main memory block，相當於每個set只有1個cache line(block)。
- 優點：搜尋時間短，因為記憶體中的每一個位置只會對應到 cache 中的一個特定位置。
- 缺點：hit rate 低。當多個變數映射到同一個 cache line 時會發生嚴重的衝突，導致頻繁刷新 cache 將造成大量的延遲。

> 在這種機制下，如果沒有足夠大的 cache，處理程序幾乎無時間局部性可言。
### 2. Fully Associative
任意 cache block 可以對應到任意的 main memory block。相當於只有1個set。
- 優點：hit rate 高。可以完整利用記憶體。
- 缺點：cache 中尋找 block 時需要搜尋整個 cache

> 搜尋時間長，CPU 必須掃過整個 cache 才能決定是否該繼續往 main memory 撈資料。
### 3. N-Way Associative
為了解決以上兩種極端設計模式的缺點，我們在兩者之前取得平衡。CPU 必須檢查指定 set 內的每個 block 是否有可用的 cache。也就是說把 cache 分成多個 set，每個 set m 個 cache line。一般每個 set 有n個 cache line(block)。
- 優點：搜尋時間短且 hit rate 高
- worst case: Fully Associative 的情況，也就是 CPU 要檢查整個 cache

### Direct Mapped 
direct-map (也被稱為One-way set associative)direct-map顧名思義，就是直接根據記憶體位置，把所有區塊平均分配給cache。看圖應該就能理解配置的方法，cache內有000~111 8個block，memory內有00000~11111 32個block，memory內的block index結尾只要等於cache index，就代表該block可以被放到該cache的該位置。也就是灰色的部份（00001, 01001, 10001, 11001）都可以被放到cache 001 block內。

![](https://i.imgur.com/NOSOuvy.png)


下圖為4KB的cache(1024個block，一個block內有4byte的資料)。這是一個32bit的address，direct map到1024個block的cache。word是處理器指令集存取memory的單位。在此架構中一個word是4個bytes，因此我們需要兩個bit來決定到底是該word的哪一個bytes。該圖cache內1個block的大小是1個word，總共有1024個block，所以我們用10 bits來表示該cache index，剩下20個bits就作為tag。因此存取該cache意思就是取出2~11bit找到cache index，並比較tag(12~31 bit)決定是否hit，如果hit到，就讀出資料。
![](https://i.imgur.com/8or05u6.png)

> 一個32bit的記憶體位址大概會分成 [tag][cache index][word index][byte index]

## block size與miss rate的關係
![](https://i.imgur.com/qBHS13C.png)

在同樣的cache size下，如果提昇block size，會降低miss rate，因為你提昇了spatial locality。但是如果你無限制的提高block size，反而會導致cache內的總block數太少。另一個提高block size會造成的問題是miss penalty變大，因為一旦miss，你須要轉移更多的記憶體內容。

## two strategies writing to cache
- Write-through
  - 資料寫入cache時也會同步寫入memory
  - CPU向cache寫入數據時，同時向memory(後端存儲)也寫一份，使cache 和memory的數據保持一致。
  - 優點是簡單。
  - 缺點是每次都要訪問memory， 速度比較慢。
- write-back
  - cpu更新cache時，只是把更新的cache區標記一下，並不同步更新memory (後端存儲)。只是在cache區要被新進入的數據取代時，才更新 memory(後端存儲)。這樣做的原因是考慮到很多時候cache存入的是中間結 果，沒有必要同步更新memory(後端存儲)。
  - 優點是CPU執行的效率提高。
  - 缺點是實現起來技術比較複雜。
  - 將資料量儲存到一定的量之後,會依據同區塊的資料一次整批寫回去。
  - 所謂 dirty，他是在記憶體裡面 cache 的一個 bit 用來指示這筆資料已經被 CPU 修改過但是尚未回寫到儲存裝置中。

## 三種不同的 Cache Miss
- Compulsory Miss (Cold Cache)
  - 當程式剛開始執行時，Cache 中沒有任何的 Block。因此會發生 Cache Miss。
- Capacity Miss
  - Cache 的大小太小了，沒有辦法涵蓋到整個 Working Set。
- Conflict Miss
  - 雖然 Cache 還有很多空間，但在 Working Set 中的 任意兩個 Block 可能無法同時存在 Cache 中。發生的原因可能是因為，此 2 個 Block 的 Index 相同。(也就是所謂的 Cache Thrash)

## Six basic cache optimizations
###  Reducing Miss Rate
1. Larger block size 
- Advantage:
  - Take advantage of spatial locality. Reduces compulsory misses 
- Disadvantage:
  - Increases miss penalty

2. Larger total cache capacity to reduce miss rate
- Advantage:
  - Reduce capacity misses 
- Disadvantage:
  - Increases hit time, increases power consumption

3. Higher associativity
- Advantage:
  - Reduces conflict misses
- Disadvantage:
  - Increases hit time, increases power consumption

### Reduces overall memory access time
4. Higher number of cache levels

### Reduces miss penalty
5. Giving priority to read misses over writes
- 在寫入緩衝區中的較早寫入之前完成讀取

### Reduces hit time
6. Avoiding address translation in cache indexing
- Cache中使用虛擬地址，這樣就可以同時Access TLB和Cache / Access Cache firstly

## Ten Advanced Optimizations of Cache Performance
### 1. Reducing the hit time
#### - Small and simple caches
如果僅考慮Cache Hit Time，那麼結構越簡單、容量越小、組相連路數越少的緩存肯定是越快的。 所以出於速度考慮，CPU的L1緩存都是很小的。比如從Pentium MMX到Pentium 4，L1緩存的容量都沒有增長。 不過太少了肯定也是不行的。
#### - Way prediction
直接相連的Hit Time是很快的，但conflict miss多。組相連可以減少conflict miss，但結構複雜功耗也高一些，hit time也多一點。那麼有什麼方法能兩者兼得呢？因為組相連緩存中，每一個組裡面的N個路（block）是全相連的。也就是相當於讀的時候，每次映射好一個set之後，要遍歷一遍N個block，當N越大的時候費的時間就越多。有一種黑科技方法叫做路預測（way prediction），它的思想就是在緩存的每個塊中添加預測位，來預測在下一次緩存訪問時，要訪問該組裡的哪個塊。當下一次訪問時，如果預測準了就節省了遍歷的時間（相當於直接相連的速度了）；如果不准就再遍歷唄。 。 。好在目前這個accuracy還是很高的，大概80+%了。不過有個缺點就是Hit Time不再是確定的幾個cycle了（因為沒命中的時候要花的cycle多嘛），不便於後面進行優化（參考CPU pipeline）。
### 2. Increasing cache bandwidth
#### - Pipelined caches
在cache訪問中也可以使用pipeline技術。 但pipeline是有可能提高overall access latency的（比如中間有流水線氣泡），而latency有時候比bandwidth更重要。所以很多high-level cache是​​不用pipeline的
#### - cache with Multiple Banks
對於Lower Level Cache（比如L2），它的read latency還是有點大的。假設我們有很多的cache access需要訪問不同的數據，能不能讓它們並行的access呢？可以把L2 Cache分成多個Bank（也就是多個小分區），把數據放在不同Bank上。這樣就可以並行訪問這幾個Bank了。那麼如何為數據選一個合適的Bank來存呢？一個簡單的思路就是sequential interleaving：Spread block addresses sequentially across banks. E,g, if there 4 banks, Bank 0 has all blocks whose address modulo 4 is 0; bank 1 has all blocks whose address modulo 4 is 1; .. .... 因為數據有locality嘛，把相鄰的塊存到不同bank，就可以盡量並行的訪問locality的塊了。
#### - Nonblocking caches
假設要執行下面一段程序：
```
Reg1:=LoadMem(A);
Reg2:=LoadMem(B);
Reg3:=Reg1 + Reg2;
```
當執行第一行時，cpu發現地址A不在cache中，就需要去內存讀。但讀內存的時間是很長的，此時CPU也不會閒著，就去執行了第二行。然後發現B也不在cache中。那麼此時cache會怎麼做呢？
- (a). cache阻塞，等著先把A讀進來，然後再去讀B。這種叫做Blocking Cache
- (b). cache同時去內存讀B，最終B和A一起進入Cache。這種叫做Non-Blocking Cache

可以看出Non-Blocking Cache應該是比較高效的一種方法。在這種情況下，兩條語句的總執行時間就只有一個miss penalty了：

![](https://i.imgur.com/L08kBPV.png)

### 3. Reducing Miss Penalty
#### - Critical word first
相對一個Word來說，cache block size一般是比較大的。有時候cpu可能只需要一個block中的某一個word，那麼如果cpu還要等整個block傳輸完才能讀這個word就有點慢了。因此我們就有了兩種加速的策略：
1. Critical Word First：首先從存儲器中讀想要的word，在它到達cache後就立即發給CPU。然後在載入其他目前不急需的word的同時，CPU就可以繼續運行了
2. Early Restart：或者就按正常順序載入一整個block。當所需的word到達cache後就立即發給CPU。然後在載入其他目前不急需的word的同時，CPU就可以繼續運行了

![](https://i.imgur.com/CfkntY9.png)

#### - Merging write buffers

### 4. Reducing Miss Rate
#### - Compiler optimizations






## Summary
![](https://i.imgur.com/4FOEbsA.png)
1. 完全聯想式映射：fully associative mapping‧
  整個cache視同一個分組，An－1～A0外的位址位元為tag（需存入cache）‧
  hit rate：最高，cost：最高，speed：最快‧
2. 直接映射：direct mapping‧
  一個分組（set）只包含一個資料段落，若有2m個分組，去掉n＋m位址位元後，
  其餘位址位元為tag（需存入cache）‧
  hit rate：最低，cost：最低，speed：最慢‧

3. 分組聯想式映射：set associative mapping‧
  多個分組，每個分組內有多個資料段落，若有2m個分組，去掉n＋m位址位元後，
  其餘位址位元為tag（需存入cache）‧
  hit rate：中等，cost：中等，speed：中等‧

[練習](http://www.cs.nthu.edu.tw/~tingting/Archi_17/week14_class_sheet%20-%20ans_fix.pdf)

[參考-淺談memory cache](http://opass.logdown.com/posts/249025-discussion-on-memory-cache)
[現代處理器設計: Cache 原理和實際影響](https://hackmd.io/@sysprog/HkW3Dr1Rb)
http://hellpuppetanna.pixnet.net/blog/post/268153860-%E8%A8%88%E7%AE%97%E6%A9%9F%E7%B5%90%E6%A7%8B---09-cache%28%E4%B8%8B%29
https://hackmd.io/@drwQtdGASN2n-vt_4poKnw/H1U6NgK3Z
http://oz.nthu.edu.tw/~d947207/chap21_cache.pdf