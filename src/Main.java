import temp.Randomizer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/*Reflection дозволяє витягнути методи, змінні, і т.д. навіть якщо вони private
* також можна отримати новий екземпляр класу
* доступ до всіх змінних
* перетворювати класи одного типу в інший(cast)
* робити це все під час виконання програми(динамічно, в Runtime)*/
public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException,
            ClassNotFoundException, NoSuchMethodException, InstantiationException {

       var randomizerInterface = Class.forName("temp.Randomizer"); // підгружаємо інтерфейс
       var classLoader = randomizerInterface.getClassLoader();
       var interfacesToImplement = new Class<?>[]{randomizerInterface};
        InvocationHandler handler = ((proxy, method, args1) -> { // invocationHanler описує виклик інтерфейсу
           if(method.getName().equals("randomize")){
               var list = (List<?>) args1[0];
               var index = ThreadLocalRandom.current().nextInt(list.size());
               return list.get(index);
           }
           throw new UnsupportedOperationException();
        });

        var randomizeInstance = Proxy.newProxyInstance(classLoader, interfacesToImplement,handler);
        var randomizeMethod = randomizeInstance.getClass().getMethod("randomize" , List.class);
        var result = randomizeMethod.invoke(randomizeInstance, List.of(1,3,45,67,900));
        System.out.println(result);



        /*var randomizerClass = Class.forName("temp.Randomizer"); // пошук файлу
        var methods = randomizerClass.getDeclaredMethods(); // читаємо мета дані.
        System.out.println(Arrays.toString(methods));
        var randomizeMethod = methods[0];
        System.out.println(Arrays.toString(randomizeMethod.getParameters())); // виводимо на екран параметри

        var randomizerConstructor = randomizerClass.getConstructor(); // получаємо конструктор
        var randomizer = randomizerConstructor.newInstance(); // викликаємо метод
        var result = randomizeMethod.invoke(randomizer, List.of(1,2,3,4,5)); // викликаємо randomizer
        System.out.println(result);*/
    }
}