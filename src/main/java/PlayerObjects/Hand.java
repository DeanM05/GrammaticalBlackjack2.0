package main.java.PlayerObjects;

import main.java.GameObjects.BlackjackCard;

import java.util.ArrayList;

public class Hand {

    private ArrayList<BlackjackCard> cardsInHand;
    private boolean isSoft = false;
    private boolean isSplittable = false;
    private int splitValue = 0;
    private int handValue = 0;

    public Hand() {
        cardsInHand = new ArrayList<>();
    }

    public void addCard(BlackjackCard card) {
        cardsInHand.add(card);
        handValue = generateHandValue();
    }

    public BlackjackCard getCard(int index) {
        return cardsInHand.get(index);
    }

    public int valueOfHand() {
        return handValue;
    }

    public boolean isHandSoft() {
        return isSoft;
    }

    public boolean isHandSplittable() {
        return isSplittable;
    }

    public int splittableCardValues() {
        return splitValue;
    }

    public int generateHandValue() {
        int value = 0;
        flushGlobalVars();
        boolean hasAce = false;
        boolean softAce = false;
        if (cardsInHand.size() == 2) {
            if (cardsInHand.get(0).getValue() == cardsInHand.get(1).getValue()) {
                isSplittable = true;
                splitValue = cardsInHand.get(0).getValue();
            }
        } else {
            isSplittable = false;
        }
        for (int i = 0; i < cardsInHand.size(); i++) {
            int cardValue = cardsInHand.get(i).getValue();
            if (cardValue == 1 && !hasAce) {
                value += 10;
                softAce = true;
                hasAce = true;
            }
            value += cardValue;
            if (value > 21 && softAce) {
                value -= 10;
                softAce = false;
            }
        }
        isSoft = softAce;
        return value;
    }

    public void flush() {
        cardsInHand.clear();
        handValue = 0;
        isSoft = false;
        isSplittable = false;
        splitValue = 0;
    }

    public void flushGlobalVars() {
        isSoft = false;
        isSplittable = false;
        splitValue = 0;
    }

    public void removeCard(int index) {
        cardsInHand.remove(index);
    }

}
