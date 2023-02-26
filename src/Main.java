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

       var randomizerInterface = Class.forName("temp.Randomizer");
       var classLoader = randomizerInterface.getClassLoader();
       var interfacesToImplement = new Class<?>[]{randomizerInterface};
        InvocationHandler handler = ((proxy, method, args1) -> {
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



        /*var randomizerClass = Class.forName("temp.Randomizer");
        var methods = randomizerClass.getDeclaredMethods();
        System.out.println(Arrays.toString(methods));
        var randomizeMethod = methods[0];
        System.out.println(Arrays.toString(randomizeMethod.getParameters()));

        var randomizerConstructor = randomizerClass.getConstructor();
        var randomizer = randomizerConstructor.newInstance();
        var result = randomizeMethod.invoke(randomizer, List.of(1,2,3,4,5));
        System.out.println(result);*/
    }
}