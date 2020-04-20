## 1. Please explain Temporal Locality and Spatial Locality. And give an example for each.
Temporal Locality (時間區域性): 一個記憶體位址被存取後,不久會再度被存取
(e.g., loops, reuse)

Spatial Locality (空間區域性): 一個記憶體位址被存取後,不久其附近的記憶體位址也會被存取。 (e.g., straight-line code, array access)

## 2. There are numerous techniques for improving the performance of caches. Please explain the main idea behind the following techniques, i.e., nonblocking caches, critical word first, and early restart.
### Non-blocking cache 
allows data cache to continue to supply cache hits during a miss
### Critical Word First
先從記憶體中讀想要的word，當所需的word到達cache後就立即發給CPU。然後在載入其他目前不急需的word的同時，CPU就可以繼續運行了。
### Early restart
按正常順序載入一整個block。當所需的word到達cache後就立即發給CPU。

## 3. In the class, we describe the following compiler optimization techniques, i.e., Merging Arrays, Loop Interchange, Loop Fusion, and Blocking, which can improve the cache performance. Given the following code, please rewrite them to improve cache miss. And give which locality is improved in each case.

(a) spatial
```c
for (j = 0; j < 100; j = j+1)
			for (i = 0; i < 5000; i = i+1)
				x[i][j] = 2 * x[i][j];

for (i = 0; i < 5000; i = i+1)
		for (j = 0; j < 100; j = j+1)
			x[i][j] = 2 * x[i][j];  
```

(b) spatial
```c
int val[SIZE];
int key[SIZE];

struct merge {
	int val;
	int key;
};
struct merge merged_array[SIZE]; 
```

(c) Spatial & temporal
```c
for (i = 0; i < N; i = i+1)
		for (j = 0; j < N; j = j+1)
			a[i][j] = 1/b[i][j] * c[i][j];
for (i = 0; i < N; i = i+1)
		for (j = 0; j < N; j = j+1)
			d[i][j] = a[i][j] + c[i][j];

for (i = 0; i < N; i = i+1)
	for (j = 0; j < N; j = j+1){
		a[i][j] = 1/b[i][j] * c[i][j];
		d[i][j] = a[i][j] + c[i][j];
    } 
```

(d) Please explain the rationale behind the blocking mechanism.

提取一部分的資料並重複使用，直到被替換為止。而且不會再重新引用它。

### 4. There are numerous techniques for improving the performance of caches. Some reduce the frequency of misses, some reduce the miss penalty, some reduce hit times, and so on. Many optimizations involve trade-offs, making one performance factor worse in return for reducing another. In some cases, the so-called optimization can actually hurt performance. For the following optimizations, briefly summarize the trade-offs involved and how it helps or can hurt performance.

- Higher associativity
    - Reduce conflict misses

- Larger block size 
    - Reduce compulsory misses

- Bigger caches 
    - Reduce capacity misses
    
- Avoiding address translation in cache indexing
    - Reduce hit time
