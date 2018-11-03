import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Author: zhshuo
 * Time : 2018/10/18 10:04 星期四
 **/
public class NoCase {

    List<User> users = new ArrayList<>(Arrays.asList(new User().setAge(10).setName("wangwu"),
                new User().setAge(20).setName("lisi"),
                new User().setAge(22).setName("zhangsan"),
                new User().setAge(30).setName("zhshuo"),
                new User().setAge(15).setName("lisa"),
                new User().setAge(33).setName("bob")
                ));

    List<User> filterUsers(List<User> users,Predicate<User> predicate){
        List<User> result = new ArrayList<>();
        users.forEach(user -> {
            if(predicate.test(user)){
                result.add(user);
            }
        });
        return result;
    }

    @Test
    public  void methodRefrenceTest() throws IOException {

        /*new Thread(()-> System.out.println(123));

        List<User> users = filterUsers(NoCase.users, User::ageCondition);

        List<User> zhsh = filterUsers(NoCase.users, user ->  user.getName().contains("zh"));

        List<User> userList = NoCase.users.parallelStream().filter(user -> user.getAge() > 10).collect(Collectors.toList());

        System.out.println(users.toString());
        System.out.println(zhsh.toString());
        System.out.println(userList);

        System.out.println(processFile((reader)->reader.readLine()));*/
//        System.out.println(funTest(NoCase.users,user -> user.getName()));

        /*IntPredicate predicate = value -> value % 2 == 1;
        System.out.println(predicate.test(1000));

        Predicate<Integer> integerPredicate = value -> value % 2 == 1;
        System.out.println(integerPredicate.test(1000));

        LongUnaryOperator operator = l -> l * 20 - 23 % 2;
        System.out.println(longUnOpTest(operator,23957));

        //表达式对void兼容
        Consumer stringConsumer = s ->  NoCase.users.add(new User());

        NoCase.users.sort(Comparator.comparing(User::getAge));*/

        List<String> strings = Arrays.asList("asd", "1234", "fd");

        strings.sort(String::compareToIgnoreCase);

        System.out.println(strings);

        String i = "1234";

        ToIntFunction<String> toIntFunction = Integer::parseInt;

        toIntFunction.applyAsInt("124");

        Supplier<String> s =  String::new;

    }

    @Test
    public void constructorTest(){

        ConstructorFunction<String,Integer,String,User> biFunction = User::new;

        User zhshuo = biFunction.get("zhshuo", 2,"12");

        System.out.println(zhshuo);

        users.sort(Comparator.comparing(User::getAge).reversed());

        System.out.println(users);

        Predicate<User> userPredicate = user -> user.getName().contains("zh");

        Predicate<User> negate = userPredicate.negate();

        Predicate<User> or = userPredicate.and(user -> user.getAge() == 10).or(user -> user.getMark() == null);


        Function<Integer,Integer> f = i -> i+3;
        Function<Integer,Integer> g = i -> i*6;
        Function<Integer, Integer> integerIntegerFunction = f.andThen(g);
        integerIntegerFunction.apply(2);
    }

    @Test
    public void streamTest(){
        users.stream().collect(Collectors.groupingBy(User::getAge));

    }

    public String processFile(FunctionIner iner) throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader("H://大主宰.txt"))){
            return iner.process(reader);
        }
    }

    public <T> void forEach(List<T> list, Consumer<T> c){
        for(T l:list){
            c.accept(l);
        }
    }

    public int intTest(IntSupplier intSupplier){
        return intSupplier.getAsInt();
    }

    public <T,R> List<R> funTest(List<T> list,Function<T,R> function){
        List<R> result = new ArrayList<>();
        for(T l:list){
            result.add(function.apply(l));
        }
        return result;
    }



    public long longUnOpTest(LongUnaryOperator longUnaryOperator,long va){
        return longUnaryOperator.applyAsLong(va);
    }

    public <T,R> R funTest(Function<T,R> f,T value){
        return f.apply(value);
    }

    public int intOperatorTest(IntBinaryOperator intBinaryOperator,int left,int right){
        return intBinaryOperator.applyAsInt(left,right);
    }

    public <T> void consumerTest(Consumer<T> consumer,T value){
        consumer.accept(value);
    }

    public <T> T supplierTest(Supplier<T> supplier){
        return supplier.get();
    }

    public <T,U,R> R biFunctionTest(BiFunction<T,U,R> biFunction,T vt,U vu){
        return biFunction.apply(vt,vu);
    }

}
