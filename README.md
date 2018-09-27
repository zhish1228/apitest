# 1.使用方式
1.安装maven  
2.使用maven命令mvn clean test执行测试脚本  
3.当前默认使用testng.xml驱动测试  

# 2. module
* demo : 旧代码,可忽略
* manager : 基本框架 

# 3.entity
测试数据对应的表entity,主要包含http请求的参数和断言信息

# 4.cases
具体运行的case，包含初始化数据和测试脚本  
同一test多个case依赖db数据驱动,测试方法methodName需要与table中casename字段相同   

# 5.conf
全局配置
## 4.1 conf/test
测试配置:  
失败重试,提高脚本运行稳定,重试记录skip  
运行监听器，目前只有log,可做异常处理(如输出信息，screenshot等)  

# 6.utils
数据库、http工具、dataprovider

# 7.run
在本地或者jenkins上执行,可配合TestNG Results插件查看结果   
mvn clean test -pl <module-name>  -Denvironment=dev   
如:clean test -pl  manager   -Denvironment=dev
# 8.todo
...
