
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by mrahman on 4/25/16.
 */
public class Client{

    WebDriver driver1 = null;
    WebDriver driver2 = null;
    WebDriver driver3 = null;
    WebDriver driver4 = null;
    String userName1 = "Boby";
    String userName2 = "Helen";
    String userName3 = "Karen";
    String messageSendByUser1 = "Good Morning";
    String messageReceivedByUser1 = "";
    String messageSendByUser2 = "Good Evening";
    String messageReceivedByUser2 = "";
    String messageObserveByUser3 = "";
    String messageSendByUser1ToUser3 = "How Are You";
    String actualTitle = "Simple Chat";
    String actualPageHeader = "Sign in to Chat";

    @BeforeMethod
    public void setUp(){
        driver1 = new FirefoxDriver();
        driver1.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver1.get("https://simple-chat-asapp.herokuapp.com/");
        driver2 = new FirefoxDriver();
        driver2.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver2.get("https://simple-chat-asapp.herokuapp.com/");
        driver3 = new FirefoxDriver();
        driver3.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver3.get("https://simple-chat-asapp.herokuapp.com/");
        driver4 = new FirefoxDriver();
        driver4.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver4.get("https://simple-chat-asapp.herokuapp.com/");

    }


    @Test
    public void testConversation()throws InterruptedException{
        //Verify Page Title and PageHeader
        String expectedTitle = driver1.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle);
        String expectedPageHeader = driver1.findElement(By.cssSelector(".app>h1")).getText();
        Assert.assertEquals(actualPageHeader, expectedPageHeader);
        //LogIn User1
        driver1.findElement(By.xpath(".//*[@id='content']/div/form/div[1]/input")).sendKeys(userName1);
        driver1.findElement(By.xpath(".//*[@id='content']/div/form/div[2]/input")).sendKeys(userName2);
        driver1.findElement(By.cssSelector(".input>button")).click();
        //LogIn User2
        driver2.findElement(By.xpath(".//*[@id='content']/div/form/div[1]/input")).sendKeys(userName2);
        driver2.findElement(By.xpath(".//*[@id='content']/div/form/div[2]/input")).sendKeys(userName1);
        driver2.findElement(By.cssSelector(".input>button")).click();
        //Chat initiated and message entered by User1
        driver1.findElement(By.cssSelector(".chatForm>input")).sendKeys(messageSendByUser1);
        driver1.findElement(By.cssSelector(".chatForm>button")).click();
        //Message read by User2
        messageReceivedByUser2 = driver2.findElement(By.cssSelector("#chatBox .message span.text")).getText();
        Assert.assertEquals(messageSendByUser1, messageReceivedByUser2);
        //Message entered by User2
        driver2.findElement(By.cssSelector(".chatForm>input")).sendKeys(messageSendByUser2);
        driver2.findElement(By.cssSelector(".chatForm>button")).click();
        Thread.sleep(2000);
        //Message read by User1
        messageReceivedByUser1 = driver1.findElement(By.cssSelector("#chatBox .message:nth-child(2) span.text")).getText();
        //Message verified User1 to User2
        Assert.assertEquals(messageSendByUser2, messageReceivedByUser1);

        //LogIn User3
        driver3.findElement(By.xpath(".//*[@id='content']/div/form/div[1]/input")).sendKeys(userName3);
        driver3.findElement(By.xpath(".//*[@id='content']/div/form/div[2]/input")).sendKeys(userName1);
        driver3.findElement(By.cssSelector(".input>button")).click();
        //LogIn User1 to User3
        driver4.findElement(By.xpath(".//*[@id='content']/div/form/div[1]/input")).sendKeys(userName1);
        driver4.findElement(By.xpath(".//*[@id='content']/div/form/div[2]/input")).sendKeys(userName3);
        driver4.findElement(By.cssSelector(".input>button")).click();
        //Message send by User1
        driver4.findElement(By.cssSelector(".chatForm>input")).sendKeys(messageSendByUser1ToUser3);
        driver4.findElement(By.cssSelector(".chatForm>button")).click();
        //Trying Message reading of User1 and User2
        messageObserveByUser3 = driver3.findElement(By.cssSelector("#chatBox .message span.text")).getText();
        if(messageObserveByUser3.equalsIgnoreCase(messageReceivedByUser2)) {
            Assert.fail("User3 can access User1 and User2 conversation");
        }else {
            System.out.println("Conversation between User1 and User2 is not accessible by User3");
        }

    }

    @AfterMethod
    public void clean(){
        //closing app
        driver1.close();
        driver2.close();
        driver3.close();
        driver4.close();
    }

}
