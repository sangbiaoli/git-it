###启动

#!/bin/sh

moduleName="lqb-sprj-1.0"

pid=`ps aux | grep $moduleName.jar | grep -v grep | awk '{print $2}'`
echo $pid
[ -n "$pid" ] && kill -9 $pid

nohup java -jar $moduleName.jar -server -Xms256m -Xmx512m -Xss256k > ./run.log 2>&1 &

echo "start successed!!!"
