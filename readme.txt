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
10.