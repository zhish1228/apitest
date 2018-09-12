# 1.使用方式
1.安装maven
2.使用maven命令mvn clean test执行测试脚本 <br/>
3.可以通过mvn clean test testng.xml或者pom中的plugin指定执行文件 <br/>

# 2.bean
http请求的参数(from db)

# 3.cases
具体运行的case，包含初始化数据和测试脚本<br/>
同一test多个case依赖db数据驱动,测试方法methodName需要与table中casename字段相同

# 4.conf
全局配置
## conf/test
测试配置:
失败重试,重试记录skip
运行监听器，目前只有log,可做异常处理(如输出信息，screenshot等)

# 5.utils
数据库、http工具、dataprovider(test data from db)

# 6.run
mvn clean test -pl <module-name>  -Denvironment=dev

# 7.todo
http协议其它方法(目前只有get)

