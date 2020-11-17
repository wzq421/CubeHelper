# CubeHelper
我从小便是魔方爱好者，一直苦于找不到一款好的手机app进行辅助，身边的魔友们基本都还是用着手机计时器，
对于魔方爱好者来说肯定是非常不方便的，便有了开发一款真正为魔友打造的魔方助手的想法。于是，CubeHelper诞生了。
项目尚在开发中，个人能力有限，但是核心功能已经完成，热爱魔方的朋友可以体验一下，对MVVM架构开发有兴趣的朋
友欢迎在下方提问，我会和大家一起讨论，望不吝赐教。
本项目基于goldze的MVVMHabit框架开发github.com/goldze/MVVM…，项目主要分为主页面，计时页面和计时器页面三个部分
，主要提供了自动生成打乱公式，自定义观察时间，精确计时，成绩共享和成绩记录功能。更多功能大家可以下载项目自行体验。
接下来介绍下项目的开发细节。

1：开发过程中我尽量遵守了MVVM的开发规范，对于每个fragment和activity都有对应的viewmodel,同时利用databinding进
行view的双向绑定，viewmodel中绑定了点击事件，用来相应点击事件以改变Livedata的值，同时在Avtivity中进行观察，
对其改变做出反应来改变View，因为LiveData自带有生命周期绑定，所以可以自动完成实时更新，同时对于其他页面的信息
也会在收到消息后在当前页面Onresume的时候进行改变。（比如项目中主页面和计时器页面之间成绩的共享和传输，不用担
心数据没有显示的问题）
2：事件总线，也就是消息共享，这个应该是项目中耗时最长的部分，因为我设计了两个页面要求实现数据共同更新，
计时页面的数据回传，打乱公式的单一性等等，我一共使用了三种实现，而且因为是Mvvm架构所以所有消息传递的
逻辑都写在viewmodel中。

           RxBus，是我最喜欢用的也是用的最多的，利用了Rxjava来实现观察和订阅的功能，对             
于比如主页面讲打乱公式传到计时器页面是用了粘性事件传输，相当于是先传输后接                
 收，Rxbus比较简单，此处我直接使用了框架中封装好的Rxbus。

//Rxbus的注册和移除都在viewmodel中进行，这里是注册接收者，如果是粘性事件就是toObservableStiky和postStiky
mIsLogin=RxBus.getDefault().toObservable(Is_Login.class).subscribe(new Consumer<Is_Login>() {
@Override
public void accept(Is_Login is_login) throws Exception {
isLogin.setValue(is_login.isLogin());
}});
//在需要发送消息的地方发送消息，接受者就会收到消息并执行accept里面的代码
RxBus.getDefault().post(new Is_Login(true))22

 Messenge,一款轻量级全局的消息通信工具，主要是完成一些简单的页面通信功能，               
写起来比Rxbus简单而且相对方便易读。

//注册信使
Messenger.getDefault().register(this, MainActivityViewModel.TOKEN_MAINACTIVITYVIEWMODEL_OPENDRAWER, new BindingAction() {
@Override
public void call() {              
if(allowDrawerOpen.getValue())openDrawer.setValue(true);               
else ToastUtils.showShort("您还未登录，请登录");    }});
//在需要的地方发送消息
Messenger.getDefault().post( MainActivityViewModel.TOKEN_MAINACTIVITYVIEWMODEL_OPENDRAWER)

全局的shareViewModel,在callback中写一个shareViewModel并封装为单例，让                    
 其只有一个实例因此可以在需要通信的ViewModel都得到它的引用，在需要观察的                   
页面对其进行观察，通过改变LiveData来完成通信的需求。我其实开始是很想利用                   
Livedata进行页面通信操作，因此写出来这个shareViewModel，感觉写的并不                       
好，后来想一想应该可以借鉴github上的LiveEventBus的思想来做一个类似RxBus                   
的封装，但是感觉用起来还是没有Rxbus方便和易读，因此浅尝辄止。
3.侧滑
         从主页面和计时器页面侧滑可以实现页面的切换，使用了Blankj的SwipePanel,使用比较简单，可以自己设置出
发位置，出发效果以及监听侧滑的事件，源码只有六百多行，非常具有参考价值。
4.毫秒计时器

         因为android并没有原生的毫秒计时器，所以我基于handler实现了一个毫秒级的计时器，具体可以看
util包中的mychorometer。
