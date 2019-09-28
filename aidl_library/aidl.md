# AIDL 接口定义语言（Android Interface Definition Language）
------
在Android中一个进程通常无法访问另外一个进程的内存。
为了进行通信，进程需将其对象分解为操作系统理解的原语，并将其编组为可操作的对象，
编写执行该操作的代码较为繁琐，因此Android就使用AIDL内部帮我们处理此问题。

> * 创建.aidl文件
> * 实现接口
> * 向客户端公开接口

## 1、创建.aidl文件
> * 1.1 new->AIDL FILE  会在src->main->aidl下生成aidl文件，
> * 1.2 build/rebuild后，会自动生成java的接口文件文件目录在：/build/generated/aidl_source_output_dir/debug/compileDebugAidl/out/

注意：
aidl 接口支持的数据类型
1、java编程语言中的所有原语类型（如 int、long、char、boolean 等）
2、String
3、CharSequence
4、List
备注：List 中的所有元素必须是以上列表中支持的数据类型，或者您所声明的由 AIDL 生成的其他接口或 Parcelable 类型。您可选择将 List 用作“泛型”类（例如，List<String>）。尽管生成的方法旨在使用 List 接口，但另一方实际接收的具体类始终是 ArrayList。
5、Map
备注：Map 中的所有元素必须是以上列表中支持的数据类型，或者您所声明的由 AIDL 生成的其他接口或 Parcelable 类型。不支持泛型 Map（如 Map<String,Integer> 形式的 Map）。尽管生成的方法旨在使用 Map 接口，但另一方实际接收的具体类始终是 HashMap。


## 2、实现接口
> * 2.1 启动一个服务，服务的bind由第一步自动生成的java接口类实现
``` kotlin
/**
 * @author dashu
 * @date 2019-09-28
 * describe:
 */
public class LoginAidlService extends Service {

    private static final String TAG = "LoginAidlService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate()");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    //实现java 接口类
    private IBinder binder = new ILoginAidlInterface.Stub() {
        @Override
        public String getLoginToken() {
            return "third app token!!!";
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }
}
```
> * 2.1 在manifest中申明，并且允许外部应用调用
``` xml
<service
    android:name="com.azhansy.aidl_library.LoginAidlService"
    android:enabled="true"
    android:exported="true">
    <intent-filter>
        <action android:name="com.azhansy.aidl" />
    </intent-filter>
</service>
```

## 3、向第三方App公开接口
> * 3.1 第三方App 显性绑定该服务
``` java
Intent intent = new Intent();
intent.setAction("com.azhansy.aidl");
intent.setPackage("com.azhansy.learningnotes");
bindService(intent, connection, Context.BIND_AUTO_CREATE);
```
> * 3.2 在绑定服务连接回调回来时，即可拿到对应的接口，拿到后，即可操作相关逻辑

``` java
ServiceConnection connection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        iLoginAidlInterface = ILoginAidlInterface.Stub.asInterface(iBinder);
        Log.d("azhansy", "onServiceConnected========" + iLoginAidlInterface);

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        Log.d("azhansy", "onServiceDisconnected========");


    }
};
```
------
#### 官方文档
[Android 接口定义语言 (AIDL)](https://developer.android.com/guide/components/aidl?hl=zh-cn#kotlin)