#ANR Application Not Responding  应用程序无响应

---------

## 原理分析
Android 系统中 ActivityManagerService 和  WindowManagerService，
即AMS和WMS会检测App的响应时间，
如果App在特定时间内无法响应屏幕触摸或者键盘反应就会出现ANR

> * InputDispatching Timeout(5秒)：类型键盘或触摸事件在特定时间内无响应
> * BroadcastQueue Timeout(前台10秒，后台60秒)：BroadcastReceiver在特定时间内无法处理完成
> * ServiceTimeout(前台20秒，后台200秒)：Service在特定时间内无法处理完成
> * ContentProvider Timeout(10秒)：ContentProvider的publish在10秒内没进行完

## 原因
主线程处理耗时操作
> * 高耗时的操作，如图像变换、耗时计算
> * 磁盘、数据库读写操作
> * 大量创建新对象
## 解决方案

在主线程不要处理耗时操作，放在子线程操作
>* 使用AsyncTask 
>* Handler
>* Rx
>* 协程

具体实施方案：
>* 查看log，找出问题原因
>* 从trace.txt文件查看调用stack，adb pull data/anr/traces.txt ./mytraces.txt
>* 看代码
>* 仔细查看ANR的成因(iowait?block?memoryleak?)




