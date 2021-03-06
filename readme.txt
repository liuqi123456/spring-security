1.http基本认证
2.表单认证
3.认证与授权
4.实现图形验证码
5.自动登录和注销登录
    自动登录是将用户的登录信息保存在用户浏览器的cookie中.
    spring security 提供了两种非常好的令牌：
        散列算法
        持久性存储机制
     备注：如果提供自动登录功能，就应当限制cookie登录时的部分执行权限
6.会话管理
    会话固定攻击
    会话过期
    会话并发控制
    集群会话的三种方案
        session保持
        session复制
        session共享
            session独立出来，放在单独的服务器中
7.密码加密
    spring security配置好后，自动应用密码加密
8.跨域与CORS
    协议跨域
    主机跨域
    端口跨域
    解决跨域问题：
        Nginx
        JSONP jsonp利用script标签可以实现跨域，只支持get请求
        CORS 允许服务器声明其提供的资源允许哪些站点跨域使用(使用cors来解决跨越问题，只需要后端做出支持即可)
            DefaultCorsProcessor中的handleInternal方法是处理CORS的核心
        备注：JSONP和CORS需要后端参与
9.跨域请求伪造的防护(CSRF)->CSRF漏洞工具 CSRFTester
    CSRF利用了系统对登录期用户的信任，使得用户执行了某些并非意愿的操作从而造成损失。
    任何情况下，都应避免以get方式提供涉及数据修改的api
    防御CSRF攻击的方式主要有以下两种：
        Http Referer-->是由浏览器添加的一个请求头字段，用来标识请求来源
        CsrfToken认证-->CsrfToken的防范思路是，添加一些并不存放于cookie的验证值，并在每个请求中都进行校验，便可以阻止csrf攻击
    使用spring security防御csrf攻击
        HttpSessionCsrfTokenRepository，这种方式再某些单页面应用中局限性较大，灵活性不足
        CookieCsrfTokenRepository,这种方式更加灵活可行的方案，它将csrfToken值存储再用户的cookie内
    备注：系统前端并非在浏览器中运作，就应当关闭csrf
10.单点登录与CAS
    统一了不同系统间的账户体系
    CAS三个重要术语
        TGT 登录后生成的票根
        TGC 存储在cookie中的一段数据
        ST cas server使用TGT签发的一张一次性票根
11.Http认证
    http基本认证
        RFC2616中定义的一种认证模式
            优点是使用简单、没有复杂页面交互
    http摘要认证
        http摘要认证使用对通信双方都可知的口令进行校验
12.@EnableWebSecurity与过滤器机制
    Spring security的核心实现是通过一条过滤器链来确定用户的每一个请求应该得到什么样的反馈
13.Spring social 实现OAuth对接
    理解OAuth的允许流程，则必须认识4个重要的角色
        a.Resource Owner        资源所有者，通常指用户，例如：每一个QQ用户
        b.Resource Server       资源服务器，指存放用户受保护资源的服务器，例如：存储QQ用户基本信息的服务器
        c.Client                客户端，指需要获取用户资源的第三方应用，例如：CSDN网站
        d.Authorization Server  授权服务器，用于验证资源所有者，并在验证成功之后向客户端发放相关访问令牌，例如：QQ授权登录页面
        客户端请求授权需许可
            ->用户同意许可
                ->客户端携带用户提供的授权许可向授权服务器申请访问令牌
                    ->授权服务器验证客户端及其携带的授权许可，确认有效后发放访问令牌
                        ->客户端使用访问令牌向资源服务器申请资源
                            ->资源服务器验证访问令牌，确认无误后向客户端提供资源
    OAuth定义了4中授权模式
        a.授权码模式
            授权码模式是功能最完整、流程最严密的授权模式，它将用户引导到授权服务器进行身份验证，授权服务器将发放的访问令牌传递给客户端。
        b.隐式授权模式
            隐式授权模式要求：用户登录并对第三方应用进行授权，直接返回访问token，通过token访问资源
            相比授权码模式，它少了一次授权码的颁发与客户端使用授权码换取token的过程
        c.密码授权模式
            就是客户端直接携带密码向授权服务器申请访问令牌
        d.客户端模式
            客户端提前向授权服器申请应用公钥、密钥，并通过这些关键细腻些向授权服务器申请访问令牌
14.Spring Security Oauth 实现Oauth对接
    spring security5.0集成了oauth客户端模块，该模块包含了三个子模块
        spring-security-oauth2-core
        spring-security-oauth2-jose 支持jose协议组
        spring-security-oauth2-client
    Oauth标准制定的形式
        获取code
        使用code交换access_token
        携带access_token请求被保护的用户信息和其它资源
    spring security 提供了相应的扩展接口和配置方法
        Redirection Endpoint 支持自定义重定向端点
        Oauth2AccessTokenResponseClient 实现了以code传递给access_token的具体逻辑
        UserInfo Endpoint 支持自定义用户信息端点
15.网站的安全账户
    网站应用攻击与防御
        XSS攻击
            XSS攻击类型有两种
                反射型
                持久型
            XSS攻击如何防范
                消毒->对脚本进行转义
                HttpOnly->增加HttpOnly属性
        注入攻击：sql注入攻击和os注入攻击
            攻击者获取数据数据库表结构信息的手段有如下几种
                开源
                错误回显
                盲注
            防御sql注入攻击
                消毒
                参数绑定
        csrf攻击
            攻击的核心
                利用了浏览器cookie或服务器session策略
            csrf的防御手段
                表单token
                验证码
                referer check
        其它攻击和漏洞
            error code
            html注释
            文件上传
            路径遍历
        web应用防火墙
            ModSecurity是一个开源的web应用防火墙，ModSecurity采用处理逻辑与攻击规则
            处理逻辑：负责请求和响应的拦截过滤，规则加载执行
            攻击规则：负责描述对具体攻击的规则定义、模式识别、防御策略
        网站安全漏洞扫描
    信息加密技术及密钥安全管理
        信息加密技术可分为三类：单向散列加密、对称加密、非对称加密
        Https传输中浏览器使用的数字证书实质上是经过权威机构认证的非对称加密的公钥。
        密钥安全管理，改善密钥安全性的手段有两种：
            方案一，把密钥和算法放在一个独立的服务器上
            方案二，将加解密算法放在应用系统中，密钥则放在独立服务器中
    信息过滤与反垃圾
        文本匹配主要解决敏感词过滤的问题（有时候需要对信息做降噪预处理，然后再进行匹配）
            trie算法
            构造多级Hash表进行文本匹配
        分类算法
            贝叶斯分类算法
        黑名单
            黑名单可以通过Hash表实现
            在不完全精确的场景下，可用布隆过滤器
    电子商务风险控制
        风险
            账户风险
            买家风险
            卖家风险
            交易风险
        机器自动风控的技术手段
            规则引擎
                规则引擎是一种将业务规则和规则逻辑相分离的技术
            统计模型
                经过充分训练后的统计模型，准确率不低于规则引擎