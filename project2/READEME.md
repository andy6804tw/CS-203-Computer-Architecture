# Project2

## Test1
```
MUL.D F0, F2, F4 
SUB.D F6, F8, F10
DIV.D F12, F14, F16
ADD.D F18, F20, F22
```

![](https://i.imgur.com/niESK0c.png)

## Test2
```
L.D F6, 8(R1)
ADD.D F0, F2, F6
DIV.D F8, F0, F4
SUB.D F10, F6, F8
MUL.D F12, F10, F30
S.D F12, 40(R1)
```

![](https://i.imgur.com/I7TNQbk.png)

## Test3
```
L.D F6, 8(R2)
L.D F2, 40(R3)
ADD.D F4, F2, F6
DIV.D F8, F0, F4
SUB.D F0, F6, F2
MUL.D F12, F6, F4
S.D F12, 40(R3)
```

![](https://i.imgur.com/FS9zzUa.png)


## Test4
```
L.D F0, 0(R1)
L.D F2, 0(R1)
ADD.D F2, F2, F2
MUL.D F12, F6, F4
DIV.D F8, F12, F4
SUB.D F8, F6, F2
S.D F8, 16(R1)
S.D F12, 8(R1)
```

![](https://i.imgur.com/u4wit1G.png)


## Test5
```
L.D F0, 0(R1)
L.D F2, 0(R0)
DIV.D F4, F0, F2
DIV.D F8, F6, F4
DIV.D F10, F8, F4
SUB.D F8, F0, F2
```

![](https://i.imgur.com/xJOXtx0.png)

## Example1
```
L.D F6, 8(R2)
L.D F2, 40(R3)
MUL.D F0, F2, F4 
SUB.D F8, F6, F2
DIV.D F10, F0, F6
ADD.D F6, F8, F2
```
![](https://i.imgur.com/vFxtw8s.png)

## Example2
```
L.D F6, 8(R2)
L.D F2, 40(R3)
ADD.D F0, F2, F6
SUB.D F8, F6, F2
MUL.D F10, F0, F8
S.D F10, 0(R3)
DIV.D F8, F0, F2
S.D F8, 8(R3)
```
![](https://i.imgur.com/drmVTKz.png)

## Example3
```
L.D F6, 8(R2)
L.D F2, 40(R3)
ADD.D F4, F2, F6
DIV.D F8, F0, F4
SUB.D F6, F6, F2
MUL.D F10, F6, F4
S.D F10, 40(R3)
```
![](https://i.imgur.com/E5o3HKe.png)

## Example4
```
L.D F6, 8(R2)
L.D F2, 40(R3)
ADD.D F4, F2, F6
DIV.D F8, F0, F4
MUL.D F6, F8, F4
SUB.D F10, F2, F4
SUB.D F14, F8, F4
SUB.D F10, F6, F4
ADD.D F12, F6, F4
```
![](https://i.imgur.com/2pKMVvg.png)

## Example5
```
L.D F6, 8(R2)
L.D F2, 40(R3)
MUL.D F4, F2, F6
DIV.D F8, F0, F4
MUL.D F6, F6, F2
DIV.D F10, F6, F4
S.D F10, 40(R3)
```
![](https://i.imgur.com/sHtZSLN.png)


## Example6
```
L.D F0, 0(R1)
L.D F2, 0(R0)
DIV.D F4, F0, F2
DIV.D F8, F6, F4
SUB.D F8, F0, F2
DIV.D F10, F8, F4
```
![](https://i.imgur.com/0IOV7MO.png)

