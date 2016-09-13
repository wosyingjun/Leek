
##Leek — 韭菜的自我救赎
>Leek是一款基于分布式的简易版智能实时选股系统，计算能力超强，代码量极少。

####基于（Flume+KafKa+JStorm+Esper+MySQL）
- 分布式架构、可自由调节计算能力。
- Flume 实时日志数据提取。
- KafKa 连接 Flume 和 JStorm，用于消息数据中转。
- JStorm + Esper 分布式实时数据处理。
- MySQL 保存数据筛选结果。

####目前实现了几种简单的策略： 
- 大单卖(stock-stategy-1): 选出股票的卖5档总手数大于买5档口总手数100倍时的股票；  
- 大单买(stock-stategy-2): 选出股票的买5档总手数大于卖5档口总手数100倍时的股票；  
- 放巨量(stock-stategy-3): 选出在10秒内成交量超过1000万时的股票；

####相关部署
- [ZooKeeper 高可用集群的安装及配置](http://wosyingjun.iteye.com/blog/2312960)
- [Kafka 集群的部署与测试](http://wosyingjun.iteye.com/blog/2316508)
- [JStorm2.1.1集群的安装和使用](http://wosyingjun.iteye.com/blog/2317034)
- Flume 集群负载均衡（待完善）



####架构图：
![](http://i.imgur.com/Cmruowc.png)
####Storm计算节点：
![](http://i.imgur.com/7cWrlwy.png)
####Esper计算模型：

![](http://i.imgur.com/pVzaWf9.jpg)

![](http://i.imgur.com/i8BwisZ.jpg)