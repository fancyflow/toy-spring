package com.chaoching.springframework.ioc;

import com.chaoching.springframework.service.TestService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

import java.util.HashMap;
import java.util.Map;

public class SimpleBeanContainerTest {

    @Test
    public void testGetBean() {
        BeanFactory beanFactory = new BeanFactory();
        String beanName = "testService";
        beanFactory.registerBean(beanName, new TestService());
        TestService testService = (TestService) beanFactory.getBean(beanName);
        Assertions.assertThat(testService).isNotNull();
        Assertions.assertThat(testService.test()).isEqualTo("test");
    }

    @Test
    public void testSynchronized() throws InterruptedException {
        Object obj1 = new Object();
        System.out.println("未开启偏向锁时新建对象的mark word布局：");
        System.out.println(ClassLayout.parseInstance(obj1).toPrintable());
        new Thread(() -> {
            synchronized (obj1) {
                System.out.println("未开启偏向锁时获取到锁后对象的mark word布局：");
                System.out.println(ClassLayout.parseInstance(obj1).toPrintable());
                System.out.println("开始执行业务逻辑");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("执行完业务逻辑后的对象的mark word布局：");
                System.out.println(ClassLayout.parseInstance(obj1).toPrintable());
            }
        }, "t").start();
        Thread.sleep(1000);
        System.out.println("Thread " + Thread.currentThread().getName() + "开始尝试获取锁...");
        System.out.println("Thread " + Thread.currentThread().getName() + "获取到锁之前对象的mark word布局：");
        System.out.println(ClassLayout.parseInstance(obj1).toPrintable());
        synchronized (obj1) {
            System.out.println("Thread " + Thread.currentThread().getName() + "获取到锁之后对象的mark word布局：");
            System.out.println(ClassLayout.parseInstance(obj1).toPrintable());
        }
        System.out.println("Thread " + Thread.currentThread().getName() + "释放锁之后对象的mark word布局：");
        System.out.println(ClassLayout.parseInstance(obj1).toPrintable());

        Object object = new Object();
        System.out.println("经历锁升级过程后同一个类新创建对象的mark word布局：");
        System.out.println(ClassLayout.parseInstance(object).toPrintable());
    }
}

/**
 * 最简单的Bean工厂
 */
class BeanFactory {
    private final Map<String, Object> beanMap;

    public BeanFactory() {
        this.beanMap = new HashMap<>(16);
    }

    public void registerBean(String beanName, Object object) {
        this.beanMap.put(beanName, object);
    }

    public Object getBean(String beanName) {
        return this.beanMap.get(beanName);
    }
}
