
##Leek — 韭菜的自我救赎

####基于（Flume+KafKa+JStorm+Esper+MySQL）
- 分布式架构、可自由调节计算能力。
- Flume 实时日志提取。
- KafKa 连接 Flume 和 JStorm，用于消息数据中转。
- JStorm + Esper 分布式实时数据处理。
- MySQL 保存数据筛选结果。

####架构图：
![](http://i.imgur.com/Cmruowc.png)
####Storm拓扑：
![](http://i.imgur.com/7cWrlwy.png)
####Esper计算模型：
![](http://i.imgur.com/pVzaWf9.jpg)
![](http://i.imgur.com/i8BwisZ.jpg)
