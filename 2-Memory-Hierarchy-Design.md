# Memory Hierarchy Design
1. block: cache中資訊表示的最小單位。
2. word是處理器指令集存取memory的單位，一個word是4個bytes。
3. 四種儲存技術：SRAM, DRAM, Flash, Magnetic disk。
4. 三種associativity method: directed-mapped, set-associative, full-associative。
5. cache中的地址由三部分組成：set index, tag 和 byte offset。
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
### 3. N-Way Set Associative
為了解決以上兩種極端設計模式的缺點，我們在兩者之前取得平衡。CPU 必須檢查指定 set 內的每個 block 是否有可用的 cache。也就是說把 cache 分成多個 set，每個 set n 個 cache line(block)。
- 優點：搜尋時間短且 hit rate 高
- worst case: Fully Associative 的情況，也就是 CPU 要檢查整個 cache

### Direct Mapped 
direct-map (也被稱為One-way set associative)direct-map顧名思義，就是直接根據記憶體位置，把所有區塊平均分配給cache。看圖應該就能理解配置的方法，cache內有000-111 8個block，memory內有00000-11111 32個block，memory內的block index結尾只要等於cache index，就代表該block可以被放到該cache的該位置。也就是灰色的部份（00001, 01001, 10001, 11001）都可以被放到cache 001 block內。

![](https://i.imgur.com/NOSOuvy.png)


下圖為4KB的cache(1024個block，一個block內有4byte的資料)。這是一個32bit的address，direct map到1024個block的cache。word是處理器指令集存取memory的單位。在此架構中一個word是4個bytes，因此我們需要兩個bit來決定到底是該word的哪一個bytes。該圖cache內1個block的大小是1個word，總共有1024個block，所以我們用10 bits來表示該cache index，剩下20個bits就作為tag。因此存取該cache意思就是取出2-11bit找到cache index，並比較tag(12-31 bit)決定是否hit，如果hit到，就讀出資料。
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
#### ① Small and simple caches
Small and simple first level caches。如果僅考慮Cache Hit Time，那麼結構越簡單、容量越小、組相連路數越少的緩存肯定是越快的。 所以出於速度考慮，CPU的L1緩存都是很小的。比如從Pentium MMX到Pentium 4，L1緩存的容量都沒有增長。 不過太少了肯定也是不行的。

L1 cache主要處理減少hit time；L2則處理減少miss rate.
#### ② Way prediction
直接相連的Hit Time是很快的，但conflict miss多。組相連可以減少conflict miss，但結構複雜功耗也高一些，hit time也多一點。那麼有什麼方法能兩者兼得呢？因為組相連緩存中，每一個組裡面的N個路（block）是全相連的。也就是相當於讀的時候，每次映射好一個set之後，要遍歷一遍N個block，當N越大的時候費的時間就越多。有一種黑科技方法叫做路預測（way prediction），它的思想就是在緩存的每個塊中添加預測位，來預測在下一次緩存訪問時，要訪問該組裡的哪個塊。當下一次訪問時，如果預測準了就節省了遍歷的時間（相當於直接相連的速度了）；如果不准就再遍歷唄。 。 。好在目前這個accuracy還是很高的，大概80+%了。不過有個缺點就是Hit Time不再是確定的幾個cycle了（因為沒命中的時候要花的cycle多嘛），不便於後面進行優化（參考CPU pipeline）。
### 2. Increasing cache bandwidth
#### ③ Pipelined caches
在cache訪問中也可以使用pipeline技術。 但pipeline是有可能提高overall access latency的（比如中間有流水線氣泡），而latency有時候比bandwidth更重要。所以很多high-level cache是​​不用pipeline的
#### ④ cache with Multiple Banks
對於Lower Level Cache（比如L2），它的read latency還是有點大的。假設我們有很多的cache access需要訪問不同的數據，能不能讓它們並行的access呢？可以把L2 Cache分成多個Bank（也就是多個小分區），把數據放在不同Bank上。這樣就可以並行訪問這幾個Bank了。那麼如何為數據選一個合適的Bank來存呢？一個簡單的思路就是sequential interleaving：Spread block addresses sequentially across banks. E,g, if there 4 banks, Bank 0 has all blocks whose address modulo 4 is 0; bank 1 has all blocks whose address modulo 4 is 1; .. .... 因為數據有locality嘛，把相鄰的塊存到不同bank，就可以盡量並行的訪問locality的塊了。
#### ⑤ Nonblocking caches
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
#### ⑥ Critical word first
相對一個Word來說，cache block size一般是比較大的。有時候cpu可能只需要一個block中的某一個word，那麼如果cpu還要等整個block傳輸完才能讀這個word就有點慢了。因此我們就有了兩種加速的策略：
1. Critical Word First：首先從存儲器中讀想要的word，在它到達cache後就立即發給CPU。然後在載入其他目前不急需的word的同時，CPU就可以繼續運行了
2. Early Restart：或者就按正常順序載入一整個block。當所需的word到達cache後就立即發給CPU。然後在載入其他目前不急需的word的同時，CPU就可以繼續運行了

![](https://i.imgur.com/CfkntY9.png)

#### ⑦ Merging write buffers
當某個write buffer中已存放未來將更新的資料時，如果又有新的資料要被更新，新的位置將被檢查是否可以和已存資料的合併，減少write penalty

### 4. Reducing Miss Rate
#### ⑧ Compiler optimizations
這裡的reducing miss rate又可以分為Instruction miss和data miss兩類：
- Instruction Miss：
  - 重新排序內存中的過程，以減少衝突遺漏
- Data Miss：
  - 1. Merging Arrays: improve spatial locality by single array of compound elements vs.  arrays
  - 2. Loop Interchange: change nesting of loops to access data in order stored in memory
  - 3. Loop Fusion: Combine 2 independent loops that have same looping and some variables overlap
  - 4. Blocking: Improve temporal locality by accessing “blocks” of data repeatedly vs. going down whole columns or rows

#### 1. Merging Arrays: improve spatial locality by single array of compound elements vs. 2 arrays
```C
/* Before: 2 sequential arrays */
int val[SIZE];
int key[SIZE];


/* After: 1 array of stuctures */
struct merge {
    int key;
    int val;
};
struct merge merged_array[SIZE];
```
我們可以比較一下對於這兩種定義方式，它們在內存中的組織方式：

![](https://i.imgur.com/l4JY90W.png)

我們要對index k，分別訪問key[k]和val[k]。

```C
/* Before: Miss Rate = 100% */
int k=rand(k);
int _key=key[k];
int _val=val[k];


/* After: Miss Rate = 50% */
int k=rand(k);
int _key=dat[k].key;
int _val=dat[k].val;
```
可以看出第二種方式充分利用了spatial locality。對於同一個index k，讀取key_k的同時，val_k也被讀進cache啦，這樣就節省了一次訪問內存的時間。

Reducing conflicts between val & key; improve spatial locality

#### 2. Loop Interchange: change nesting of loops to access data in order stored in memory
它們只是循環次序改變了：

```C
int x[][];   //very large
//Assume a cacheline could contain 2 integers.

/* Before */
for (j = 0; j < 100; j = j+1)
    for (i = 0; i < 5000; i = i+1)
        x[i][j] = 2 * x[i][j];


/* After */
for (i = 0; i < 5000; i = i+1)
    for (j = 0; j < 100; j = j+1)
        x[i][j] = 2 * x[i][j];
```

我們知道在C語言中，二維數組在內存中的存儲方式是Row Major Order的，也就是這樣：
![](https://i.imgur.com/cqqMW8F.png)
那麼對於第一種寫法，訪問順序是x[0][0], x[1][0], x[2][0], ......。 Miss Rate達到了100% 第二種寫法，訪問順序是x[0][0], x[0][1], x[0][2], x[0][3], ......。讀x[0][0]的時候可以把x[0][1]也讀進來，讀x[0][2]的時候可以把x[0][3]也讀進來，以此類推。這樣Miss Rate就只有50%

Sequential accesses instead of striding through
memory every 100 words; improved spatial locality 跳躍式讀取

#### 3. Loop Fusion: Combine 2 independent loops that have same looping and some variables overlap
```C
/* Before */
for (i = 0; i < N; i = i+1)
    for (j = 0; j < N; j = j+1)
        a[i][j] = 1/b[i][j] * c[i][j];
for (i = 0; i < N; i = i+1)
    for (j = 0; j < N; j = j+1)
        d[i][j] = a[i][j] + c[i][j];


/* After */
for (i = 0; i < N; i = i+1)
    for (j = 0; j < N; j = j+1){
        a[i][j] = 1/b[i][j] * c[i][j];
        d[i][j] = a[i][j] + c[i][j];
    }
```
在第二種寫法中，line 13已經把a[i][j]和c[i][j]讀進cache了，line14就可以接著用了。加起來比第一種要省很多cache miss。

2 misses per access to a & c vs. one miss per access; improve spatial locality

#### 4. Blocking: Improve temporal locality by accessing “blocks” of data repeatedly vs. going down whole columns or rows

![](https://i.imgur.com/AiIsjix.png)

從上面的例子中可以看到，當每次access的是同一column中的不同row（a[1][3], a[2][3], a[3][3], a[4] [3], ......），而不是同一row的不同colum時，miss rate是很可怕的。那麼怎麼避免這一現象呢？ 

一種思路是我們把整個大矩陣分解成若干個小矩陣（以所需的數據能被cache全部裝下為標準），然後每次都把這個小塊內要計算的任務全部完成，這樣就不用access whole column了。

```C
/* Before */
for (i = 0; i < N; i = i+1)
    for (j = 0; j < N; j = j+1){
        r = 0;
        for (k = 0; k < N; k = k+1)
            r = r + y[i][k]*z[k][j];
        x[i][j] = r;
}


/* After */
for (jj = 0; jj < N; jj = jj+B)
    for (kk = 0; kk < N; kk = kk+B)
        for (i = 0; i < N; i = i+1)
            for (j = jj; j < min(jj+B-1,N); j = j+1){
                r = 0;
                for (k = kk; k < min(kk+B-1,N); k = k+1){
                    r = r + y[i][k]*z[k][j];
                }
                x[i][j] = x[i][j] + r;
            }
```

Requires more memory accesses but improves locality of accesses

### 5. Reducing miss penalty or miss rate via parallelism
#### ⑨ Hardware prefetching
假設cache block只能裝下一個int，然後有如下指令：
```C
int a[];
load a[0];
load a[1];
load a[2];
load a[3];
load a[4];
load a[5];
```
那麼與其每次都cache miss重新載入，不如在第一次cache miss（load a[0]）時，讓cache預測到接下來會用到a[1], a[2], a[3] , ......，然後提前載入到next level cache裡備用。這就是硬件的prefetching。

#### ⑩ Compiler prefetching
Insert prefetch instructions to request data before data is needed

## Virtual Memory
State at least two reasons why we need virtual memory and elaborate your answer.
1. allow a single user program to exceed the size of primary memory.
2. allow efficient and safe sharing of memory among multiple programs.

當程式的 address space 大於 RAM 的 address space 的時候，如果沒有利用 virtual
memory 則可能造成 crash.而利用 virtual memory 則可以將部分資料存放到 disk
裡，需要的時候再經由 address mapping 找回。而且 virtual memory 可以經由
address mapping 有效的保護資料不被不同程式經由讀取相同地址汙染，不只如
此，如果要更有效率地運用儲存空間，可以將 mapping 改為指向同一個 RAM
地址，以達到不同程式共同存取相同地址的目的。

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

![](https://i.imgur.com/Ey6gSuL.png)

[練習](http://www.cs.nthu.edu.tw/~tingting/Archi_17/week14_class_sheet%20-%20ans_fix.pdf)

[參考-淺談memory cache](http://opass.logdown.com/posts/249025-discussion-on-memory-cache)
[現代處理器設計: Cache 原理和實際影響](https://hackmd.io/@sysprog/HkW3Dr1Rb)
[Hack筆記](https://hackmd.io/@drwQtdGASN2n-vt_4poKnw/H1U6NgK3Z
http://oz.nthu.edu.tw/~d947207/chap21_cache.pdf)

## 練習
1. Choose the statement that is wrong and explain why
```
(a) Spatial locality stating that if a data location is referenced, data locations with nearby addresses
will tend to be referenced soon.
(b) Temporal locality stating that if a data location is referenced then it will tend to be referenced again
soon.
(c) Memory hierarchy is a structure that uses multiple levels of memories; as the distance from the
processor increases, the size of the memories and the access time both increase.
(d) Miss penalty, the time required to fetch a block into a level of the memory hierarchy from the
lower level, including the time to access the block, transmit it from one level to the other, insert it
in the level that experienced the miss, and then pass the block to the requestor.
(e) Larger blocks exploit spatial locality to lower miss rates. Increasing the block size always
decreases the miss rate.
```
```
Ans:
(e) is wrong. Reason:不一定，如果 block size 最後占了 cache size 的一大部分,則 miss rate 會上升
因為能在 cache 裡面被利用的 blocks 數會減少，在讀 words 前某些 block 會被擠壓出去，造成 miss
rate 的效益降低
```

2. 以下關於 memory hierarchy 的敘述何者錯誤？
```
(a) Block is the basic unit of information transfer
(b) Loop is an example of spatial locality
(c) Hierarchy between cache and memory is managed by hardware
(d) DRAM is slow, cheap, and dense while SRAM is fast, expensive, and not very dense
(e) Miss penalty is the time to deliver the block to the processor
```
```
Ans:
(B) 應改為 temporal locality
(E) Miss penalty is the time to deliver the block to the processor + time to replace a block in the upper
level
```

3. 完成下面三個題組:

(a) Name the five memory components in the memory hierarchy from fastest to slowest.
(b) Name the four memory data transferring blocks from biggest sized to smallest sized.
(c) On what is the effectiveness of the cache memory based on? Explain the principle of it and name the
two types of it.
```
Ans:
(a) Register -> Cache -> Memory -> Disk -> Tape
(b) Files -> Pages -> Blocks -> Operands
(c)
Principle of Locality:
 Program access a relatively small portion of the address space at any instant of time
 90/10 rule: 10% of code executed 90% of time
Two types of locality:
 Temporal locality: if an item is referenced, it will tend to be referenced again soon, e.g., loop
 Spatial locality: if an item is referenced, items whose addresses are close by tend to be
referenced soon., e.g., instruction access, array data structure
```

4. The Average Memory Access Time equation (AMAT) has three components: hit time,
miss rate, and miss penalty. For each of the following cache optimizations, indicate
which component of the AMAT equation is improved.
- Using a second-level cache
- Using a direct-mapped cache
- Using a 4-way set-associative cache

```
1.Using a second-level cache improves miss rate
2.Using a direct-mapped cache improves hit time
3. Using a 4-way set-associative cache improves miss rate
```

5. Which of the following statements are generally true?
```
(1) Caches take advantage of temporal locality.
(2) On a read, the value returned depends on which blocks are in the cache.
(3) Most of the cost of the memory hierarchy is at the highest level.
(4) Most of the capacity of the memory hierarchy is at the lowest level.
```
```
1: True
2: False, The value returned by a read remains the same.
3: False, Most of the cost of the memory hierarchy is at the lowest level.
4: True
```

6. Describe the advantage and disadvantage of increasing the block sizes.
```
advantage : reduce miss rate due to spatial locality
disadvantage : if block sizes too big ,then the number of block will decrease
⇒ miss rate increase
```

7. 關於 write-through 和 write-back 的敘述，下列何者正確？
```
A. 當 write hit 時，write-through 會將 cache 和 memory 的資料同時更新
B. 當 write-back 的 dirty bit 為 1 時，代表 cache 裡沒有資料
C. 在 cache 和 memory，Write-back 的資料是非一致性的
D. 當 write miss 時，對於 write-through 通常都是 fetch the block
```
```
Ans：C
a)先存到 write buffer 再慢慢存回 memory
b) cache 有被寫過資料
d) write-back
```

8. Which of the following statement is true?
```
(A) Write-through : The information is written to both the block in the cache and the block in the lower level
of the memory hierarchy (main memory for a cache).
(B) Write-back : The information is written only to the lower level of the memory hierarchy. The modified
block is written to the lower level of the hierarchy only when it is replaced.
(C)In a fixed-sized cache, larger blocks will always reduce miss rate.
(D) Static Random Access Memory need to be refreshed regularly
```
```
ANS: A
(A)True.
(B) Write-back : The information is written only to the block in the cache. The modified block is written to the
lower level of the hierarchy only when it is replaced.
(C) In a fixed-sized cache, larger blocks will increased miss rate.
(D) Dynamic Random Access Memory need to be refreshed regularly
```

9. Please give out 2 reasons why we use memory hierarchy approach.
```
Ans.
DRAM’s speed can’t follow up CPU’s speed, in fact processor grows 50% faster per yaer, and the cost of
SRAM is too high.
Since 10% of code executed 90% of time, most of the time we the memory data we access is not as wide as
the whole memory, so we can transfer those data to SRAM to increase performance.
```

10. Revise these improper statement.
```
a. When we use a multilevel cache to improve the performance, the most important thing for the
level-2 cache is to minimize the hit time.
b. There are no disadvantage of using a set associativity cache to improve performance.
c. In an interleaved memory organization, since there are several memory banks, once the data is
accessed(ready), the data can be transfer concurrently.
d. When a new process is created and is waiting for the CPU scheduler dispatch, this process is in
waiting state.
```
```
Ans:
a. Minimize the miss rate
b. Data is ready after hit/miss detection and there are extra MUX delay.
c. data still transfer sequentially.
d. ready state.
```