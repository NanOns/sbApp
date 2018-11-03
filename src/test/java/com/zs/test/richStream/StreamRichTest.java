package com.zs.test.richStream;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

/**
 * Author: zhshuo
 * Time : 2018/10/19 23:11 星期五
 * (1) 找出2011年发生的所有交易，并按交易额排序（从低到高）。
 * (2) 交易员都在哪些不同的城市工作过？
 * (3) 查找所有来自于剑桥的交易员，并按姓名排序。
 * (4) 返回所有交易员的姓名字符串，按字母顺序排序。
 * (5) 有没有交易员是在米兰工作的？
 * (6) 打印生活在剑桥的交易员的所有交易额。
 * (7) 所有交易中，最高的交易额是多少？
 * (8) 找到交易额最小的交易。
 **/
@Log4j2
public class StreamRichTest {

    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");

    List<Transaction> tt = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );

    @Test
    public void oneTest() {
        List<Transaction> collect = tt.stream().filter(t -> t.getYear() == 2011).
                sorted(comparing(Transaction::getValue)).
                collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void twoTest() {
        //去重也可以使用toSet
        List<String> collect = tt.stream().map(t -> t.getTrader().getCity()).distinct().collect(Collectors.toList());
        log.info(collect);
    }

    @Test
    public void threeTest() {
        List<Trader> cambridge = tt.stream().map(Transaction::getTrader).filter(s -> s.getCity().equals("Cambridge")).distinct()
                .sorted(comparing(Trader::getName)).collect(Collectors.toList());
        log.info(cambridge);
    }

    @Test
    public void fourTest() {
        String collect = tt.stream().map(t -> t.getTrader().getName()).distinct().sorted().collect(Collectors.joining());
        log.info(collect);
    }

    @Test
    public void fiveTest() {
        boolean milan = tt.stream().anyMatch(transaction -> transaction.getTrader().getCity().equals("milan"));
        log.info(milan);
    }

    @Test
    public void sixTest() {
        tt.stream().filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(t -> t.getValue()).forEach(log::info);


    }

    @Test
    public void sevenTest() {
        tt.stream().map(t -> t.getValue()).reduce(Integer::max).ifPresent(log::info);
    }

    @Test
    public void eightTest() {
        tt.stream().reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);

        tt.stream().min(comparing(Transaction::getValue));

        Integer collect = tt.stream().collect(Collectors.reducing(0, Transaction::getValue, Integer::sum));
    }

    /**
     * 使用lambda来生成斐波纳契数元组
     */
    @Test
    public void fbnqsTest() {
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]}).limit(20).forEach(ints -> log.info(ints[0] + "," + ints[1]));
    }

}
