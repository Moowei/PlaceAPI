## 通过百度地图提供的API批量解析地标，获取详细地址
输入文件类型为.txt格式，如输入文件名称为test.txt内容如下所示：<br>
```text
中国人民解放军第二〇二医院
清华大学
北京航空航天大学
西湖断桥
```
解析之后会在test.txt相同目录下生成两个文件test_output.txt和test_error.txt文件<br>
其中test_output.txt为解析成功的详细地址，字段间以\t分隔。test_error.txt为解析失败的地址以及解析失败的原因。<br>
test_output.txt示例如下
```text
中国人民解放军第二〇二医院   0f89bd150f896c6b96ca0704    辽宁省 沈阳市 和平区 光荣街5号
清华大学    d8cf2f3ebd4968cfd618b7a7    北京市 北京市 海淀区  北京市海淀区双清路30号
北京航空航天大学    a599ab02adf1c3923ce23157      北京市 北京市 海淀区   北京市海淀区学院路37号
西湖断桥    69750ad0eca032122fb96d67    浙江省 杭州市 西湖区 杭州市西湖区
```
