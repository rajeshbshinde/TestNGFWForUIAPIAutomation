package com.framework;

import jdk.jfr.Enabled;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class MyFirstTest {
    int a =100;
    int b = 200;

    @BeforeClass
    public void setup(){
        a = 50;
        b = 10;
    }

    @Test (enabled = true, description = "First Test", priority = 1)
    void myFirstTest(){
        System.out.println("First! Sum ="+sum(1,2));
        Assert.assertEquals(sum(1,2),4);
    }
    @Test(enabled=true, description = "Second Test", dependsOnMethods = "myFirstTest", priority = 2)
    void mySecondTest(){
        System.out.println("Second! Sum ="+sum(2,2));
        Assert.assertEquals(sum(2,2),4);
    }

    @Test(enabled = true, description = "Default attributes", priority = 1)
    void myThirdTest(){
        System.out.println("Third! Sum ="+sum());
        Assert.assertEquals(sum(),60);
    }


    static int sum(int a, int b){
        return a+b;
    }

    protected int sum(){
        return a+b;
    }
}
