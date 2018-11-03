import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * Author: zhshuo
 * Time : 2018/10/19 14:31 星期五
 **/
@Log4j2
public class StreamCase {

    List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 800, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH));


    @Test
    public void groupingByTest(){
        Map<String, List<Dish>> collect = menu.stream().collect(groupingBy(dish -> {
            if (dish.getCalories() <= 400) return "dift";
            else if (dish.getCalories() <= 700) return "normal";
            else return "fat";
        }));
        log.info(collect);

        Map<Dish.Type, Map<String, List<Dish>>> map = menu.stream().collect(groupingBy(Dish::getType, groupingBy(dish -> {
            if (dish.getCalories() <= 400) return "dift";
            else if (dish.getCalories() <= 700) return "normal";
            else return "fat";
        })));
        log.info(map);

        Map<Dish.Type, Long> collect1 = menu.stream().collect(groupingBy(Dish::getType, counting()));
        log.info(collect1);

        Map<Dish.Type, Optional<Dish>> collect2 = menu.stream().collect(groupingBy(Dish::getType, maxBy(Comparator.comparingInt(Dish::getCalories))));
        log.info(collect2);

        Map<Dish.Type, Dish> collect3 = menu.stream().collect(groupingBy(Dish::getType, collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
        log.info(collect3);

        Map<Dish.Type, Set<String>> caloricLevelsByType =
                menu.stream().collect(
                        groupingBy(Dish::getType, mapping(
                                dish -> { if (dish.getCalories() <= 400) return "dift";
                                else if (dish.getCalories() <= 700) return "normal";
                                else return "fat"; },
                                toSet() )));
        log.info(caloricLevelsByType);
    }

    @Test
    public void partitionTest(){
        Map<Boolean, Map<Dish.Type, List<Dish>>> collect = menu.stream().collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
        System.out.println(collect);
    }

    @Test
    public void baseTest() {
        List<String> collect = menu.stream().filter(d -> d.getCalories() > 300).map(Dish::getName).limit(3).collect(toList());
        System.out.println(collect);

        List<Dish> collect1 = menu.stream().filter(d -> d.getCalories() == 800).distinct().collect(toList());
        System.out.println(collect1);

        List<Integer> collect2 = menu.stream().map(d -> d.getName().length()).collect(toList());

        List<Integer> collect3 = menu.stream().map(Dish::getName).map(String::length).collect(toList());

        if(menu.stream().noneMatch(dish -> dish.getName().contains("123"))){
            System.out.println("~~~");
        }

        Optional<Dish> any = menu.stream().filter(Dish::isVegetarian).findAny();
        any.ifPresent(d-> System.out.println(d));

    }

    @Test
    public void strAryStreamTest() {
        List<String> strings = Arrays.asList("hello", "world");
       /* List<Stream<String>> collect = strings.stream().map(s -> s.split("")).map(Arrays::stream).distinct().collect(Collectors.toList());
        System.out.println(collect);*/
        /*List<String> collect = strings.stream().map(s -> s.split("")).flatMap(Arrays::stream).distinct().collect(Collectors.toList());
        System.out.println(collect);

        List<Integer> li = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> collect1 = li.stream().map(i -> i * i).collect(Collectors.toList());
        System.out.println(collect1);
*/
        List<Integer> l = Arrays.asList(1, 2, 3), l2 = Arrays.asList(3, 4);

        List<int[]> collect2 = l.stream().flatMap(integer -> l2.stream().map(k -> new int[]{integer, k})).filter(ints -> (ints[0] + ints[1]) % 3 == 0).collect(toList());

        collect2.forEach(ints -> System.out.println(ints[0] + "," + ints[1]));


        //有初始值,直接返回T对象
        Integer reduce = l.stream().reduce(2, (integer, integer2) -> integer + integer2);
        System.out.println(reduce);

        //无初始值,返回Optional对象`
        l.stream().reduce((integer, integer2) -> integer + integer2);

    }

    @Test
    public void reduceTest(){
        menu.stream().map(Dish::getCalories).reduce(Integer::max).ifPresent(integer -> System.out.println(integer));

        menu.stream().map(dish -> 1).reduce(Integer::sum).ifPresent(integer -> System.out.println(integer));

        System.out.println(menu.stream().count());

    }

    @Test
    public void collectTest(){
        Optional<Dish> collect = menu.stream().collect(maxBy(Comparator.comparing(Dish::getCalories)));
        collect.ifPresent(System.out::println);

        Integer collect1 = menu.stream().collect(summingInt(Dish::getCalories));
        System.out.println(collect1);

        IntSummaryStatistics collect2 = menu.stream().collect(summarizingInt(Dish::getCalories));
        log.info(collect2);

    }

}
