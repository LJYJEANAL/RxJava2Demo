package reactnative.ng.smc.rxjava2demo.network;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * 另外一些情况下，在类、接口中没有使用泛型时，定义方法时想定义类型形参，就会使用泛型方法。如下方式：
 * *所谓泛型方法，就是在声明方法时定义一个或多个类型形参。**泛型方法的用法格式如下：
 * <p>
 * 修饰符<T, S> 返回值类型 方法名（形参列表）｛
 * 方法体
 * ｝
 * class Demo{
 * public <T> T fun(T t){   // 可以接收任意类型的数据
 * return t ;     // 直接把参数返回
 * }
 * };
 * 类型通配符是一个问号（？)，将一个问号作为类型实参传给List集合，写作：List<?>（意思是元素类型未知的List）。这个问号（？）被成为通配符，它的元素类型可以匹配任何类型。
 * 带限通配符
 * <p>
 * 简单来讲，使用通配符的目的是来限制泛型的类型参数的类型，使其满足某种条件，固定为某些类。
 * <p>
 * 主要分为两类即：上限通配符和下限通配符。
 * 1.上限通配符
 * <p>
 * 如果想限制使用泛型类别时，只能用某个特定类型或者是其子类型才能实例化该类型时，可以在定义类型时，使用extends关键字指定这个类型必须是继承某个类，或者实现某个接口，也可以是这个类或接口本身。
 * <p>
 * 它表示集合中的所有元素都是Shape类型或者其子类
 * List<? extends Shape>
 * 这就是所谓的上限通配符，使用关键字extends来实现，实例化时，指定类型实参只能是extends后类型的子类或其本身。
 * 例如：
 * <p>
 * //Circle是其子类
 * List<? extends Shape> list = new ArrayList<Circle>();
 * 这样就确定集合中元素的类型，虽然不确定具体的类型，但最起码知道其父类。然后进行其他操作。
 * <p>
 * 2.下限通配符
 * <p>
 * 如果想限制使用泛型类别时，只能用某个特定类型或者是其父类型才能实例化该类型时，可以在定义类型时，使用super关键字指定这个类型必须是是某个类的父类，或者是某个接口的父接口，也可以是这个类或接口本身。
 * <p>
 * 它表示集合中的所有元素都是Circle类型或者其父类
 * List <? super Circle>
 * 这就是所谓的下限通配符，使用关键字super来实现，实例化时，指定类型实参只能是extends后类型的子类或其本身。
 * 例如：
 * <p>
 * //Shape是其父类
 * List<? super Circle> list = new ArrayList<Shape>();
 */

public class FromJsonType<T> {
    private static Gson gson;
    private List<T> tList;

    public FromJsonType() {
    }

    public List<T> gettList(String result) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.fromJson(result, new TypeToken<List<T>>() {
        }.getType());
    }
}
