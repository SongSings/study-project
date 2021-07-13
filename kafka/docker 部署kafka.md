### docker 部署kafka

1.下载镜像

```shell
kafka需要依赖zookeeper使用了wurstmeister/kafka和wurstmeister/zookeeper这两个版本的镜像

　　1、docker pull wurstmeister/zookeeper  

　　2、docker pull wurstmeister/kafka
```

2.启动zookeeper

```shell
docker run -it --name zookeeper -p 2181:2181 -d wurstmeister/zookeeper
```

3.启动kafka命令

```shell
192.168.99.100:2181 为公网ip及开放端口

docker run -d   --name kafka -p 9092:9092 -e KAFKA_BROKER_ID=1 -e KAFKA_auto_create_topics_enable=true  -e KAFKA_HEAP_OPTS="-Xmx256M -Xms128M" -e KAFKA_ZOOKEEPER_CONNECT=公网ip:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://公网ip:9092 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -t wurstmeister/kafka

字段含义
    //配置分组id
    KAFKA_BROKER_ID=1

　　//开启自动创建主题(不然代码整合服务后启动报错,必须自己手动到服务上创建)
　　KAFKA_auto_create_topics_enable=true

　　//连接zookeeper
　　KAFKA_ZOOKEEPER_CONNECT=公网ip:2181

　　//默认内存1G自己服务器太小,调小一点不然启动报错内存溢出(此处也比较坑)
　　KAFKA_HEAP_OPTS="-Xmx256M -Xms128M"

　　//配置外网ip访问kafka
　　KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://公网ip:9092
```

4.启动无法访问外网问题

　　1.进入hosts文件

　　　　vi /etc/hosts

　　　　//添加公网ip

　　　　公网ip(192.168.99.100)

5.测试

　　进入kafka

　　docker exec it kafka /bin/bash

　　cd opt/kafka

(1).创建一个主题

```shell
bin/kafka-topics.sh --create --zookeeper 公网ip:2181 --replication-factor 1 --partitions 1 --topic hello
```

(2).查看主题列表：

```shell
bin/kafka-topics.sh --list --zookeeper 公网ip:2181
```

(3).运行一个消息生产者，指定topic为刚刚创建的主题:

```shell　　
bin/kafka-console-producer.sh --broker-list 公网ip:9092 --topic hello
```

(4).创建kafka消费者，指定同样的主题

```shell
bin/kafka-console-consumer.sh --bootstrap-server 公网ip:9092 --topic hello --from-beginning
```

> *注 192.168.99.100:2181 为公网ip及开放端口
>
> *参考 [docker配置kafka - 博客园 )](https://www.cnblogs.com/xuxiaobai13/p/12533056.html)*