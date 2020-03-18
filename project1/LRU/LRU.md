
# Least Recently Used
將存在於實體記憶體頁框中最久沒用到的分頁給取代掉，實作起來比起FIFO稍微困難，需使用計數變數(Counter)去儲存每個在頁框內的分頁使用後閒置的時間，或是如Linked List等額外的資料結構來儲存過去頁框中的分頁使用的順序，但實行起來成效顯著。流程如下表，分頁後數字的部份為最近使用的排序，數字愈小表示愈久之前使用過。

![](https://image.slidesharecdn.com/42lruoptimal-130119072029-phpapp02/95/42-lru-optimal-5-638.jpg?cb=1358580069)


### Example1
```
numberOfFrames=4
"EFABFCFDBCFCBAB"
分頁錯誤的次數為10次
```
![](https://imgur.com/ULT6cqB.png)

### Example２
```
numberOfFrames=4
"123412512345"
分頁錯誤的次數為8次
```
![](https://imgur.com/P2oechL.gif)





## Reference
[Page Replacement Algorithms in OS](https://www.slideshare.net/AdilAslam4/page-replacement-algorithms-in-os)