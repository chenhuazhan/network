​	

**一、短网址功能介绍**



短网址（Short URL） ，顾名思义就是在形式上比较短的网址。通常用的是asp或者php转向，在Web 2.0的今天，不得不说，这是一个潮流。目前已经有许多类似服务，借助短网址您可以用简短的网址替代原来冗长的网址，让使用者可以更容易的分享链接。





**二、短网址的好处**

*** 内容需要，缩短地址**

微博限制字数为140字一条，那么如果我们需要发一些连接上去，但是这个连接非常的长，以至于将近要占用我们内容的一半篇幅，这肯定是不能被允许的，所以短网址应运而生了。



*** 用户友好，便于管理**

短网址可以在我们项目里可以很好的对开放级URL进行管理。有一部分网址可以会涵盖暴力，广告等信息，这样我们可以通过用户的举报，完全管理这个连接将不出现在我们的应用中，因为同样的URL通过加密算法之后，得到的地址是一样的。



*** 访问统计，数据分析**

我们可以对一系列的网址进行流量，点击等统计，挖掘出大多数用户的关注点，这样有利于我们对项目的后续工作更好的作出决策。



**三、PHP简易短网址功能实现**



**1、了解短网址实现的原理**



目前，百度、新浪、腾讯、淘宝等大企业都提供了短网址生成功能，这里我们以百度提供的为例（官网：http://dwz.cn/）来分析其操作：



①假设长地址为：https://www.zhihu.com/question/29270034/answer/46446911，将长地址粘贴到百度短地址转换输入框中，并点击缩短地址



![img](https://mmbiz.qpic.cn/mmbiz_png/X36HLl2EicOe41OuehFPibU8Ep3Wmjqe5LMYPgoicAeKV80ibTbPafLaJYjHibxSaT85EGuEHOlOauAmXMK8Aj8VaAg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



②得到的http://dwz.cn/2rZroi的地址即长地址转化而来的短地址，短地址访问效果等同于长地址（通过请求跟踪得到下图）



![img](https://mmbiz.qpic.cn/mmbiz_png/X36HLl2EicOe41OuehFPibU8Ep3Wmjqe5L6ia6FQQ9NOI9JGmRVoal0iaiahrgv0eXCjy4w4IE2oia7sPps5aOuLGYSA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



不难看出，在访问短网址之后产生了一个302重定向，定向到了长地址。一个短网址对应一个长地址，在DB层面可以理解成是一个映射关系。由于短地址域名是固定不变的，那么url中的2rZroi就成了识别地址的一个标识了，访问短网址后，短网址服务提供者获取“2rZroi”标识，再去DB中去查询该标识对应的长地址，最后再做重定向。

更多细节请参考：

https://www.zhihu.com/question/29270034/answer/46446911



**2、短网址标识符生成**



由于短网址的识别字符比较短，而识别字符数量从某个角度来看是有限的，采用惯用的hash生成方式在地址数量多的时候发生碰撞的可能性会很大，因此这里我们可以采用参考链接中提及的类似“发号器”的思想。通俗的讲，有点像吃饭时候在前厅取的排队号，转化第一个长地址的时候发号器给1，第二个长地址转化时给2…以此类推。



考虑到标识符尽量的要短并且防止重复，此处的标识符我们也可以把大小写字母加进来，并且要区分大小写，这样同样6个字符组成的识别符号可以被用的长地址将远远大于6个纯数字组成的字符。



0-9、a-z、A-Z共计62个字符，那么算法我们就可以采用62进制来进行运算。



为了方便操作，此处可以将进制互相转化的方法封装成一个函数库文件。

附1：10进制转化成62进制的方法



![img](https://mmbiz.qpic.cn/mmbiz_png/X36HLl2EicOe41OuehFPibU8Ep3Wmjqe5LLJNjW4bRnrwQjHOgibpLIC99YLnibxWjA4AewUzSsPx1y1jAPGqVnq5Q/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



附2：62进制转化成10进制的方法

![img](https://mmbiz.qpic.cn/mmbiz_png/X36HLl2EicOe41OuehFPibU8Ep3Wmjqe5LA9ziaiaDgnjkQyiaUDELOcUr7yHdvHRLCYAByy8icbsTrhtFJFjl3Ktm0Q/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



**3、创建短网址需要的虚拟主机**

① 此处以LAMP环境为例，因此需要配置一个Apache虚拟主机：

定义站点根目录路径为：H:webrootsubjects80228

定义需要使用的域名为：u.cn

虚拟主机配置文件中具体配置代码参考如下：



![img](https://mmbiz.qpic.cn/mmbiz_png/X36HLl2EicOe41OuehFPibU8Ep3Wmjqe5LZ1O5mzsJhkmph6ZtAYXylgMgmVw0icJNDx3hDxzEOzb9miatWiacFYo5g/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

配置完成之后重启/启动Apache。



② 给域名设置本地Hosts解析

在hosts文件中添加以下一行代码：

127.0.0.1    www.u.cn u.cn



③ 测试访问，验证虚拟主机是否配置成功



![img](https://mmbiz.qpic.cn/mmbiz_png/X36HLl2EicOe41OuehFPibU8Ep3Wmjqe5LZRTFRicFZpdia8F9onlyic5NcTlhLZwaaVic1xxKCeEkcicT3J7hlia6AN7A/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



**4、编写长地址转化需要的前端静态页面**

简单页面（make.html），需要包含一个表单输入框与一个提交按钮，为了用户体验，表单可以通过ajax进行提交，暂定提交地址为同级目录下的make.php

注意：为了方便使用Ajax，建议在表单页面中引入jQuery库来使用jQuery封装的Ajax。



![img](https://mmbiz.qpic.cn/mmbiz_png/X36HLl2EicOe41OuehFPibU8Ep3Wmjqe5LHfxHcfcY2rEsO2hK3hxMerC8kYO61DqajgwhISicp18YAHFbibZoxs5Q/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



实现的展示效果：



![img](https://mmbiz.qpic.cn/mmbiz_png/X36HLl2EicOe41OuehFPibU8Ep3Wmjqe5LyAbHVSibbVwflGwLvLJicLwcyHgBWyUGxuq1yjefp4xQKM4e5icCKbxZw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



**5、创建需要的数据库与表**

定义库名：phpurl

定义表名：urlmap

注意：由于我们使用的62进制中某个字母的大写、小写形式所表达的值是不一样的，因此数据是区分大小写的，这就要求我们要么在存储的时候就区分大小写或者在操作CURD的时候区分大小写，二选一即可，此处以后者为例。



附：数据表DLL语句

![img](https://mmbiz.qpic.cn/mmbiz_png/X36HLl2EicOe41OuehFPibU8Ep3Wmjqe5L3ibevBJGW7xguVIGlBribydbrzpwHby5ZuICSRexlpQKY6YmLkOesNPg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



**6、编写make.php代码文件**

① 该文件的作用是接收ajax请求并生成短网址，最后返回给ajax请求。

说明：简易小功能，demo中就不对数据的有效性进行过多判断，当然如果打算上线或集成到项目中去为了安全，对应的验证还是必不可少的。



![img](https://mmbiz.qpic.cn/mmbiz_png/X36HLl2EicOe41OuehFPibU8Ep3Wmjqe5LzvKeTjibDsW4RQKuP7J6WGmAwwrAMnulorOYMRrvXbbTmCnSt8pfRTw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

![img](https://mmbiz.qpic.cn/mmbiz_png/X36HLl2EicOe41OuehFPibU8Ep3Wmjqe5LDicDd3Rsrn7ZiaNZE4ibZg6fzoxfcpcUQAo35oRPMhh4bwCxNFvC0QVyQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



② 继续完善静态ajax部分，完成ajax响应的数据回显

![img](https://mmbiz.qpic.cn/mmbiz_png/X36HLl2EicOe41OuehFPibU8Ep3Wmjqe5LHSccwp9OTvKWe2uaeRY9YaW6wCIk6JPkNrdx4jg1uKZ7qj0kxxE4Ng/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



效果：

![img](https://mmbiz.qpic.cn/mmbiz_png/X36HLl2EicOe41OuehFPibU8Ep3Wmjqe5LhPQVewmHuTL5Z1Fa28LgrXhSaAAJ4q2TG76yYUyJvu6xxsLtB3QTAg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)





**7、创建分布式配置文件实现重写**



什么是分布式配置文件？

分布式配置文件是Apache配置文件的一种类型，期文件名为“.htaccess”，一般放在站点目录中，可以是根目录，也可以是里层目录。期作用是辅助主配置文件实现对站点的自定义化配置。



什么是重写？为什么短网址功能需要重写URL？

重写的含义正如其字面含义，重写即重新书写。由于目前短网址的形式一般约定俗成为“协议 + 域名 + 地址识别符”，例如刚才我们生成的短网址“http://u.cn/1”。但是这样的形式浏览器默认会认为在站点根目录下有一个名为“1”的文件夹，但是我们站点下实际是没有的，那么会产生一个404的错误，在“短”的前提下为了解决这个错误，这个时候就只能使用重写技术，将这个“http://u.cn/1”映射到一个我们自己定义的规则，如“http://u.cn/index.php?uid=1”上。



① 开启Apache的mod_rewrite.so模块（去掉前面的“#”注释）

![img](https://mmbiz.qpic.cn/mmbiz_png/X36HLl2EicOe41OuehFPibU8Ep3Wmjqe5LBl1L9ViavYibtSMvqPDU3X9AM8pAO7O0ra17Dw4OWrjn2RyIV7ibGFe0A/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



② 编写.htaccess文件

![img](https://mmbiz.qpic.cn/mmbiz_png/X36HLl2EicOe41OuehFPibU8Ep3Wmjqe5LsxMGOgZjetC0XYJGmDEMnGBwiauf5Oc1TAehYZ6Sd6IINT9qVic1FLcA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



**8、创建入口文件实现短网址的还原并实现跳转**

经过上述几个步骤，此时，长地址已经可以转化为短地址了，但是短地址此时并不能实现访问，我们还需要在用户输入短地址的时候实现其访问功能。短网址目前访问的效果如下：



![img](https://mmbiz.qpic.cn/mmbiz_png/X36HLl2EicOe41OuehFPibU8Ep3Wmjqe5LcicO27P8A1kH5f7yRVW7cTl86VTcKPYDR4NxQrse7P0UmLiaV9cvxsrA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



解决访问问题步骤：

① 创建入口文件index.php，并且编写对应的代码

注意：由于3.7章节已经设置了重写，那么此时“http://u.cn/1”已经等效于“http://u.cn/index.php?uid=1”。因此可以直接使用$_GET[xxx]的形式来获取参数。



![img](https://mmbiz.qpic.cn/mmbiz_png/X36HLl2EicOe41OuehFPibU8Ep3Wmjqe5LzJODnsejcABic4hFtB0gAwdmdjiasqwPOktLPY5DFvyWA4Lp71uXXicicw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)



② 测试访问本地短网址http://u.cn/1 ，观察访问效果

![img](https://mmbiz.qpic.cn/mmbiz_png/X36HLl2EicOe41OuehFPibU8Ep3Wmjqe5LHVgTGeRIh07Ha1pfiaOcib2hMxgaX3RnGiaVbhhGibfET86HvK5XbLsuyw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

根据上述结果，得知短网址已经成功实现预期功能。



③测试目前不存在的短网址http://u .cn/123 ，观察效果

![img](https://mmbiz.qpic.cn/mmbiz_png/X36HLl2EicOe41OuehFPibU8Ep3Wmjqe5LDgFvwH0csIp59n4yHfXOMe8Y57FAupwmHugx0hTURpD86TBDuwb82Q/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

根据上述结果，得知访问不存在的短网址会产生404错误，符合预期。



**四、总结**



至此，使用php来做的短网址功能就已经实现，虽然没有验证数据的有效性，但是“麻雀虽小五脏俱全”，用起来效果和预期一样。