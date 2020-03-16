# Ch1 Fundamentals of Quantitative Design and Analysis

## What is Computer Architecture?
Computer Architecture =Instruction Set Architecture  +Organization + Hardware + ...

> The Instruction Set: a Critical Interface

指令及架構 -> 軟硬體之間的抽象介面

## Computer Technology
- Improvements in semiconductor technology
  - Feature size, clock speed
降頻減少Power，因多核心出現關係所以後期成長平緩
- Improvements in computer architectures
  - Lead to RISC architectures (改善SISC)

## Parallelism 平行度
- Classes of parallelism in applications:
  - Data-Level Parallelism (DLP)
  - Task-Level Parallelism (TLP)

- Classes of architectural parallelism:
  - Instruction-Level Parallelism (ILP)
  - Vector architectures/Graphic Processor Units (GPUs)
  - Thread-Level Parallelism
  - Request-Level Parallelism

## Flynn’s Taxonomy(費林分類法)

|            | **單一指令流**               | **多指令流**                 |
|------------|--------------------------|--------------------------|
| **單一資料流** | 單指令流單數據流（SISD） | 多指令流單數據流（MISD） |
| **多資料流**   | 單指令流多數據流（SIMD） | 多指令流多數據流（MIMD） |


下圖有四種類型, "PU" 是指程序單元（processing unit）:
![](https://i.imgur.com/sg6B4r1.png)

AMD 和 i7 可跑同一個指令及架構(ISA)，其底層架構不同，例如catch大小。

## Bandwidth and Latency
- Bandwidth: 在時間內完成的工作量(處理量Throughput)
- Latency: 每個事件的開始結束(反應時間)

1. 以較快的處理器至於計算機中 (兼得)
2. 在使用多個處理器來分別處理各工作(只提升處理量)
3. 平行度拉高因此Bandwidth提高

## Measuring Performance
Typical performance metrics:
- Response time
- Throughput

## Amdahl’s Law
阿姆達爾定律:
針對電腦系統裡面某一個特定的元件予以最佳化，對於整體系統有多少的效能改變