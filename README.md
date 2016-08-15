
##Leek — 韭菜的自我救赎

####基于（Flume+KafKa+JStorm+Esper+MySQL）
- 分布式架构、可自由调节计算能力。
- Flume 实时日志数据提取。
- KafKa 连接 Flume 和 JStorm，用于行情数据中转。
- JStorm + Esper 分布式实时复杂事件处理。
- MySQL 保存数据筛选结果。

####相关部署：  
- [ZooKeeper 高可用集群的安装及配置](http://wosyingjun.iteye.com/blog/2312960)  
- [Kafka 集群的部署与测试](http://wosyingjun.iteye.com/blog/2316508)  
- [JStorm2.1.1集群的安装和使用](http://wosyingjun.iteye.com/blog/2317034)
- Flume 集群负载均衡（待完善）

####架构图：
![](http://i.imgur.com/Cmruowc.png)
####Storm拓扑：
![](http://i.imgur.com/7cWrlwy.png)
####Esper计算模型：
![](http://i.imgur.com/pVzaWf9.jpg)
![](http://i.imgur.com/i8BwisZ.jpg)
