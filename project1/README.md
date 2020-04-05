## Test Report
#### Example1
##### Direct
```
cache_size: 128 
block_size: 8
set_degree: 1
- Hits:2
- Misses:13
```
##### 2-way
```
cache_size: 128 
block_size: 8
set_degree: 2
- Hits:3
- Misses:12
```

#### Example2
##### Direct
```
cache_size: 1024 
block_size: 16
set_degree: 1
- Hits:5
- Misses:7
```

#### Example3
##### Direct
```
cache_size: 1024 
block_size: 16
set_degree: 1
- Hits:2193
- Misses:2807
```
##### 2-way
```
cache_size: 1024 
block_size: 16
set_degree: 2
- Hits:2197
- Misses:2803
```

#### Example4
##### Direct
```
cache_size: 128 
block_size: 32
set_degree: 1
- Hits:2
- Misses:7
```
##### 2-way
```
cache_size: 128 
block_size: 32
set_degree: 2
- Hits:4
- Misses:5
```
##### 4-way
```
cache_size: 128 
block_size: 32
set_degree: 4
- Hits:3
- Misses:6
```

#### Example5
##### Direct
```
cache_size: 1024 
block_size: 64
set_degree: 1
- Hits:3311
- Misses:1692
```
##### 2-way
```
cache_size: 1024 
block_size: 64
set_degree: 2
- Hits:3313
- Misses:1690
```
##### 4-way
```
cache_size: 1024 
block_size: 64
set_degree: 4
- Hits:3312
- Misses:1691
```
#### Example6
##### Direct
```
cache_size: 512 
block_size: 16
set_degree: 1
- Hits:3
- Misses:3
```

## Deploy project
```
javac -encoding UTF-8 cacheAssociative.java
java cacheAssociative 128 16 1 example5.txt
```

執行 jar 
```
jar -cvfm cacheAssociative.jar  MANIFEST.MF ./
java -jar cacheAssociative.jar 
```

## Example
[Cache Access Example 2-way](https://www.youtube.com/watch?v=quZe1ehz-EQ)
[Hit/Miss in a 2-way set associative cache with offset](https://cs.stackexchange.com/questions/76044/hit-miss-in-a-2-way-set-associative-cache-with-offset)
[2-way example](https://www.docsity.com/en/memory-hierarchy-computer-architecture-and-engineering-solved-exams/298662/)