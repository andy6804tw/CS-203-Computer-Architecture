# Memory Hierarchy Design
1. block: cache中資訊表示的最小單位。
2. word是處理器指令集存取memory的單位，一個word是4個bytes。
3. 四種儲存技術：SRAM, DRAM, Flash, Magnetic disk。
4. 三種associativity method: directed-mapped, set-associative, full-associative。
5. cache中的地址由三部分組成：index, tag 和 byte offset。

## 記憶體設計的原則
當初記憶體為什麼要像現在設計成階層式的架構？很簡單，因為我們發現我們寫的程式在存取記憶體的過程中，有兩大現象：

- 剛剛用過的記憶體很容易再被使用（例如，for迴圈）
- 如果一個記憶體剛剛使用過，他附近的記憶體位址也很可能被使用到（例如，陣列存取）

![](https://i.imgur.com/6wqhGGI.png)

我們把資料一次從記憶體下層轉移到記體上層的單位定作block。如果處理器要求讀取某個block的資料，剛好在上層的記憶體內，那就稱為hit。如果不在上層，那就稱為miss。hit rate就是你成功在上層記憶體就找到你要的資料的次數比例。相反的就是miss rate。hit rate + miss rate = 1。

另一個對電腦效能來說影響重大的因素就是hit time和miss penalty

- hit time
  - 判斷記憶體是否hit + 把上層資料搬到處理器的時間
- miss penalty
  - 把下層記憶體的資料搬到上層 + 上層記憶體資料搬到處理器的時間

## cache設計的概念
“cache” 是為了讓資料存取的速度適應 CPU 的處理速度，允許 CPU 直接到 cache memory 查看所需資料是否存在。若是，則直接存取不必再到 main memory —— 減少到 main memory 存取的次數，解決 main memory 存取不夠快的問題。因此才有SRAM的出現。

處理器怎麼知道data是否在cache中，並且正確的從cache抓出想要的資料？

### 三種cache的配置：
快取記憶體的動態位址轉換，採用硬體實作的技術來完成，每個 CPU 包含 tag RAM，用來紀錄一個 memory block 要對映到那一個 cache block
#### 1. direct mapping
每個 cache block 僅可以對應到唯一的一個 main memory block
- 優點：搜尋時間短
- 缺點：hit rate 低
#### 2. n-way associative
把 cache 分成多個 set，CPU 必須檢查指定 set 內的每個 block 是否有可用的 cache
- 優點：搜尋時間短且 hit rate 高
- worst case: Fully Associative 的情況，也就是 CPU 要檢查整個 cache

#### 3. fully associative
任意 cache block 可以對應到任意的 main memory block
- 優點：hit rate 高
- 缺點：搜尋時間長，CPU 必須掃過整個 cache 才能決定是否該繼續往 main memory 撈資料

### direct map 
direct-map (也被稱為One-way set associative)direct-map顧名思義，就是直接根據記憶體位置，把所有區塊平均分配給cache。看圖應該就能理解配置的方法，cache內有000~111 8個block，memory內有00000~11111 32個block，memory內的block index結尾只要等於cache index，就代表該block可以被放到該cache的該位置。也就是灰色的部份（00001, 01001, 10001, 11001）都可以被放到cache 001 block內。

![](https://i.imgur.com/NOSOuvy.png)


下圖為4KB的cache(1024個block，一個block內有4byte的資料)。這是一個32bit的address，direct map到1024個block的cache。word是處理器指令集存取memory的單位。在此架構中一個word是4個bytes，因此我們需要兩個bit來決定到底是該word的哪一個bytes。該圖cache內1個block的大小是1個word，總共有1024個block，所以我們用10 bits來表示該cache index，剩下20個bits就作為tag。因此存取該cache意思就是取出2~11bit找到cache index，並比較tag(12~31 bit)決定是否hit，如果hit到，就讀出資料。
![](https://i.imgur.com/8or05u6.png)

> 一個32bit的記憶體位址大概會分成 [tag][cache index][word index][byte index]