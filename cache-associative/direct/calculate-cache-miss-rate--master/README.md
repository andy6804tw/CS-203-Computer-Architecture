# calculate-cache-miss-rate-

This is a small project/homework when I was taking <i>Computer Architecture</i><br/>
Simulate directed mapped cache.<br/>

<h3>Files included </h3>
<ul>
  <li>funtion.py</li>
  <li>main.py</li>
  <li>address.txt</li>
</ul>

<h4>function.py</h4>
<code>load_address(filename)</code> : <br/>
負責將txt檔所有的位置先讀，經過出來轉換之後存在一個變數address_list中<br/>
<code>convert_string_to_int(stringToBeConverted)</code>: <br/>
將16進位的位址轉換成十進位; load_address()裡每讀一行就做一次轉換<br/>
<code>convert_cachesize_to_int(cachesize)</code>: <br/>
將cachesize字串(ex. 512k)轉成數字(512 *1024)<br/>
<code>determine_memory_block(address, blockSize)</code>: <br/>
根據給定的記憶體位置以及block size來計算出該address位於哪一個memory block<br/>
<code>determine_cache_block(memoryBlock, numberofCacheBlock)</code>: <br/>
根據determine_memory_block()所計算出來的結果以及cache block的數量來計算出所對應的cache block<br/>
<code>determine_tag(memoryBlock, numberofCacheBlock)</code>: <br/>
根據memory block以及cache block的數量來計算出TAG<br/>
<code>produce_cache_block_table(numberofCacheBlock)</code>: <br/>
建立一個原始的cache block table; valid為no、tag預設成0<br/>
<code>check_cache_block(position_of_mapped_cache_block, TAG)</code>: <br/>
根據對應到的cache block以及計算出的TAG來跟cache block裡的資料作比較，做出相關的因應<br/>
<hr>
<h4>main.py</h4>
<code>filename</code>: 要讀取的檔案<br/>
<code>cache_size</code>: 使用者給定的cache size<br/>
<code>block_size</code>: 使用者給定的 block size<br/>
<code>number_of_cache_block</code>: 計算出的cache bock的數量<br/>
<code>miss_count</code>: 紀錄miss的次數<br/>
<code>hit_count</code>: 紀錄hit的次<br/>
<code>cache_block_table</code>: 用來存資料的cache block<br/>
<code>address_list</code>: 讀檔轉換後的記憶體位置<br/>

執行:<br/>
<code>py main.py <i>filename</i> <i>cache_size</i> <i>block_size</i></code>

For example:<br/>
<code>py main.py address.txt 1024k 64</code><br/>
<h5>Result: </h5>
Miss Count: 1542<br/>
Hit Count:  3458<br/>
Miss Rate:  0.3084<br/>



https://github.com/EtienneChuang/calculate-cache-miss-rate-

## Test
#### Example1
##### Direct
```
python main.py test.txt 128 8
- Hits:2
- Misses:13
```

#### Example2
##### Direct
```
python main.py example2.txt 1024 16
- Hits:5
- Misses:7
```

#### Example3
##### Direct
```
python main.py example3.txt 1024 16
- Hits:2193
- Misses:2807
```

#### Example4
##### Direct
```
python main.py example4.txt 128 32
- Hits:2
- Misses:7
```

#### Example5
##### Direct
```
python main.py example5.txt 1024 64
- Hits:3311
- Misses:1692
```