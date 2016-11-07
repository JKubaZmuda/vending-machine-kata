package tdd.vendingMachine.money.pocket;

import tdd.vendingMachine.money.coin.Coin;
import tdd.vendingMachine.money.unit.MoneyUnit;

import java.util.*;
import java.util.stream.Collectors;

public class CoinStorage {

    private final Map<Coin, Integer> storage;

    public CoinStorage() {
        this.storage = new HashMap<>();
    }

    public CoinStorage(Map<Coin, Integer> initialStorage) {
        this();
        storage.putAll(initialStorage);
    }

    public void store(Coin coin) {
        storage.put(coin, incrementCoinQuantity(coin));
    }

    public boolean isAbleToWithdraw(MoneyUnit amount) {
        List<Map<Coin, Integer>> sortedCombinations = obtainCoinCombinations(amount).stream().sorted(compareByNumberOfCoinsAscending()).collect(Collectors.toList());
        for (Map<Coin, Integer> entry : sortedCombinations) {
            if (isStorageOwningCoins(entry)) {
                return true;
            }
        }
        return false;
    }

    public Optional<Map<Coin, Integer>> withdraw(MoneyUnit amount) {
        List<Map<Coin, Integer>> sortedCombinations = obtainCoinCombinations(amount).stream().sorted(compareByNumberOfCoinsAscending()).collect(Collectors.toList());

        for(Map<Coin, Integer> entry : sortedCombinations) {
            if(isStorageOwningCoins(entry)) {
                removeFromStorage(entry);
                return Optional.of(entry);
            }
        }
        return Optional.empty();
    }

    private Integer incrementCoinQuantity(Coin coin) {
        return storage.containsKey(coin) ? storage.get(coin) + 1 : 1;
    }


    private List<Map<Coin, Integer>> obtainCoinCombinations(MoneyUnit amount) {
        int cents = amount.getCents();
        List<Map<Coin, Integer>> result = new ArrayList<>();
        obtainCombinationsUsingGreedyAlgorithm(0, cents, 0, new ArrayList<>(), result);
        return result;
    }

    private void obtainCombinationsUsingGreedyAlgorithm(int currentSum, int totalSum, int skip, List<Coin> currentCombination, List<Map<Coin, Integer>> result) {
        if(totalSum == 0) {
            result.add(coinListToMap(new ArrayList<>()));
        }

        Coin[] coins = obtainCoinsSortedAscending();

        for (int i = skip; i < coins.length; i++) {
            Coin coin = coins[i];
            currentSum += coin.getMoneyValue().getCents();

            if (currentSum > totalSum) {
                return;
            }

            currentCombination.add(coin);

            if (currentSum == totalSum) {
                result.add(coinListToMap(currentCombination));
                currentCombination.remove(currentCombination.size() - 1);
                return;
            }

            obtainCombinationsUsingGreedyAlgorithm(currentSum, totalSum, skip, currentCombination, result);

            currentCombination.remove(currentCombination.size() - 1);
            currentSum -= coin.getMoneyValue().getCents();
            skip++;
        }
    }

    private Map<Coin, Integer> coinListToMap(List<Coin> currentCombination) {
        return currentCombination.stream().distinct().collect(Collectors.toMap(p -> p, p -> Collections.frequency(currentCombination, p)));
    }

    private void removeFromStorage(Map<Coin, Integer> coinMap) {
        for(Map.Entry<Coin, Integer> entry : coinMap.entrySet()) {
            storage.put(entry.getKey(), storage.get(entry.getKey()) - entry.getValue());
        }
    }

    private boolean isStorageOwningCoins(Map<Coin, Integer> coinMap) {
        return coinMap.entrySet().stream().allMatch((p) -> storage.containsKey(p.getKey()) && storage.get(p.getKey()) >= p.getValue());
    }

    private Comparator<Map<Coin, Integer>> compareByNumberOfCoinsAscending() {
        return (a, b) -> a.values().stream().mapToInt(i -> i).sum() - b.values().stream().mapToInt(i -> i).sum();
    }

    private Coin[] obtainCoinsSortedAscending() {
        return Arrays.stream(Coin.values()).sorted((p, d) -> p.getMoneyValue().compareTo(d.getMoneyValue())).toArray(Coin[]::new);
    }

}
