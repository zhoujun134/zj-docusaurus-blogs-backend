package com.zj.email;

/**
 * @ClassName EmailTest
 * @Author zj
 * @Description
 * @Date 2024/6/19 08:48
 * @Version v1.0
 **/
//@SpringBootTest
public class EmailTest {

//    @Resource
//    private QQSendEmailService qqSendEmailService;


    public static void main(String[] args) {
        test();

    }

   static void test() {
        String text = """
                <table style="width: 99.8%;">
          <tbody>
          <tr>
            <td id="QQMAILSTATIONERY"
 style="background:url(https://rescdn.qqmail.com/zh_CN/htmledition/images/xinzhi/bg/a_10.jpg) repeat-x #bfdfec; min-height:550px; padding: 100px 55px 200px; ">
              <div><b style="font-size: x-large;">《文章 title》有新的评论通知！请审核！</b></div>
              <div><b><br/></b></div>
              <div><b>文章 ID:&nbsp; ${eArticleId} </b></div>
              <div><b><br/></b></div>
              <div><b>文章地址:&nbsp;&nbsp;<a href="https://zbus.top/${eArticleId}">点击访问原文</a></b></div>
              <div><b><br/></b></div>
              <div><b>评论内容:&nbsp; ${eContent}</b></div>
              <div><b><br/></b></div>
              <div><b>评论用户昵称: &nbsp; ${eAuthor}</b></div>
              <div><b><br/></b></div>
              <div><b style="">评论用户邮箱:&nbsp; ${eEmail}</b></div>
              <div><b style=""><br/></b></div>
              <div><span style="font-weight: 700;">一键通过链接:&nbsp;</span><b style="font-size: 14px;">&nbsp;<a
                  href="https://zbus.top">一键通过链接</a><a href="https://zbus.top/"></a></b></div>
              <div><br/></div>
              <div>邮件来源:&nbsp;<a href="https://zbus.top">Z 不殊</a>&nbsp;的小站</div>
            </td>
          </tr>
          </tbody>
        </table>
                """;
        String articleId = "111111";
        String author = "test";
        String email = "test@qq.com";
        String content = "测试的评论内容";
        text = text.replace("${eArticleId}", articleId);
        text = text.replace("${eContent}", content);
        text = text.replace("${eAuthor}", author);
        text = text.replace("${eEmail}", email);

       System.out.println(text);

    }
}
