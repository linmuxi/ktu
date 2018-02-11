# ktu
基于swing可视化模拟发送kafka报文数据，仅供测试使用

### 2018-2-11 记录
公司研发架构采用了kafka，每次进行消息调试都是通过代码test，很繁琐不方便；后面为了快速测试及其方便测试同事开展测试，
写了一个基于swing的可视化界面进行消息发送的小程序；
		
### 具体操作步骤
1、修改config.properties文件,配置kafka broker地址“metadata.broker.list”和发送topic“kafka.topic.list”（topic也可以在swing界面直接调整）
2、打包编译mvn clean install
3、启动程序:java -jar ktu-0.0.1-SNAPSHOT-jar-with-dependencies.jar config.properties
