#!/bin/bash

BUILD_ID=dontKillMe

project_name="zjry-pms.jar"
project_path="/home/app/nginx-1.18/pms/pms-web"

echo "kill process " $project_name
ps -ef | grep java  | grep  $project_name |grep -v grep| awk '{print "kill ", $2;}' | sh


echo "进入jar工作空间 .... " $project_path/$project_name
cd $project_path

echo "准备启动 " $project_name  " 服务...."

nohup java -Xms512m -Xmx512m  -jar $project_path/$project_name  >/dev/null 2>&1 &

echo  $project_name " 服务启动成功...."

exit 0
