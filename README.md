# 1、在开始运行本项目的时候，大家首先要把自己的mongodb数据库先搭建起来了先，搭建mongodb数据库的地址是：<br>  
https://blog.csdn.net/linzhefeng89/article/details/79923537 <br>  
# 2、接着大家在项目的db文件夹里的文件导入到相应的mongodb数据库中，导入数据库的脚本如下： <br>  
./mongoimport -h IP:端口 -u 账号 -p 密码 -d 数据库 -c 表 数据路径 <br>  
./mongoimport -h 127.0.0.1:27017 -u order -p hyll-2.0 -d test -c dict /home/db/dict.dat <br>  
./mongoimport -h 127.0.0.1:27017 -u order -p hyll-2.0 -d test -c user /home/db/user.dat <br>  
./mongoimport -h 127.0.0.1:27017 -u order -p hyll-2.0 -d test -c userRole /home/db/userRole.dat <br>  
./mongoimport -h 127.0.0.1:27017 -u order -p hyll-2.0 -d test -c dict /home/db/dict.dat <br>  
./mongoimport -h 127.0.0.1:27017 -u order -p hyll-2.0 -d test -c orgGroup /home/db/orgGroup.dat <br>  
# 3、大家配置好mongodb数据库以后，大家接着打开application-dev.properties配置文件在上面将数据库的连接改为大家自己的连接，然后重新编译启动自己的项目
访问：http://127.0.0.1:8080/login
