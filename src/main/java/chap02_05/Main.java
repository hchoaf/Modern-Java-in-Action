package chap02_05;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
        // 1 Find all transactions in the year 2011 and sort them by value (small to high).
        System.out.println("-----------1-----------");
        transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .forEach(System.out::println);

        // 2 What are all the unique cities where the traders work?
        System.out.println("-----------2-----------");
        List<String> cities = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct().toList();
        cities.forEach(System.out::println);

        // 3 Find all traders from Cambridge and sort them by name.
        System.out.println("-----------3-----------");
        List<Trader> traders = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> Objects.equals(trader.getCity(), "Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .toList();
        traders.forEach(System.out::println);

        // 4 Return a string of all traders’ names sorted alphabetically.
        String traderNames = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (partialString, traderName) -> partialString + traderName);

        // 5 Are any traders based in Milan?
        Boolean isBasedInMilan = transactions.stream()
                .anyMatch(transaction -> Objects.equals(transaction.getTrader().getCity(), "Milan"));

        // 6 Print the values of all transactions from the traders living in Cambridge.
        transactions.stream()
                .filter(transaction -> Objects.equals(transaction.getTrader().getCity(), "Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);

        // 7 What’s the highest value of all the transactions?
        Optional<Integer> highestValue = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);
        // 8 Find the transaction with the smallest value.
        Optional<Transaction> smallestTransaction = transactions.stream()
                .reduce((t1, t2) -> {
                    return t1.getValue() < t2.getValue() ? t1 : t2;
                });

    }
}