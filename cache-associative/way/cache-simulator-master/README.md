# cache-simulator

A cache simulator capable of calcuating miss rates on an address trace with given associativity levels, block and cache sizes, and with a least-recently-used replacement policy. Python 3.6 required.

Author: Guillermo Briceno.

---

# Usage

`./cache-sim.py <options> -trace <tracefile>`

Order does not matter. Split cache is specified with separate instruction cache and data cache sizes, block sizes, and associativity levels:

`./cache-sim.py -isize 32768 -ibsize 32 -iassoc 4 -dsize 32768 -dbsize 32 -dassoc 4 -trace <tracefile>`

Specifies an instruction cache and data cache size of 32768 bytes, both with a block size of 32 bytes and associativity level of 4.

Unified cache example:

`./cache-sim.py -usize 32768 -ubsize 32 -uassoc 4 -trace <tracefile>`

The script will output the number of hits, misses as well as their rates onto the terminal.


https://github.com/guillermofbriceno/cache-simulator


## Test
#### Example1
##### Direct
```
python cache.py -usize 128 -ubsize 8 -uassoc 1 -trace example1.txt
- Hits:2
- Misses:13
```
##### 2-way
```
python cache.py -usize 128 -ubsize 8 -uassoc 2 -trace example1.txt
- Hits:3
- Misses:12
```

#### Example2
##### Direct
```
python cache.py -usize 1024 -ubsize 16 -uassoc 1 -trace example2.txt
- Hits:5
- Misses:7
```

#### Example3
##### Direct
```
python cache.py -usize 1024 -ubsize 16 -uassoc 1 -trace example3.txt
- Hits:2193
- Misses:2807
```
##### 2-way
```
python cache.py -usize 1024 -ubsize 16 -uassoc 2 -trace example3.txt
- Hits:2197
- Misses:2803
```

#### Example4
##### Direct
```
python cache.py -usize 128 -ubsize 32 -uassoc 1 -trace example4.txt
- Hits:2
- Misses:7
```
##### 2-way
```
python cache.py -usize 128 -ubsize 32 -uassoc 2 -trace example4.txt
- Hits:4
- Misses:5
```
##### 4-way
```
python cache.py -usize 128 -ubsize 32 -uassoc 4 -trace example4.txt
- Hits:3
- Misses:6
```

#### Example5
##### Direct
```
python cache.py -usize 1024 -ubsize 64 -uassoc 1 -trace example5.txt
- Hits:3311
- Misses:1692
```
##### 2-way
```
python cache.py -usize 1024 -ubsize 64 -uassoc 2 -trace example5.txt
- Hits:3313
- Misses:1690
```
##### 4-way
```
python cache.py -usize 1024 -ubsize 64 -uassoc 4 -trace example5.txt
- Hits:3312
- Misses:1691
```