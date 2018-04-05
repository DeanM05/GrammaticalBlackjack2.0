package main.java.PlayerObjects;

import main.java.GameObjects.BlackjackCard;

public class Dealer implements BasePlayer {

    private Hand hand;

    private static final int BUST = 0;
    private static final int CONTINUE = 1;
    private static final int STAY = 2;
    private static final int BLACKJACK = 21;
    private static final int SEVENTEEN = 17;
    private static final int RESP_BLACKJACK = 3;

    public Dealer () {
        hand = new Hand();
    }

    public void dealCard(BlackjackCard card, int handNum) {
        hand.addCard(card);
    }

    public int checkCards(int handNum) {
        int value = hand.valueOfHand();
        if (value > BLACKJACK) {
            return BUST;
        } else if (value == BLACKJACK) {
            return RESP_BLACKJACK;
        } else if (value >= SEVENTEEN) {
            return STAY;
        }
        return CONTINUE;
    }

    public void flushHand(int handNum) {
        hand.flush();
    }

    public int getHandValue(int handNum) {
        return hand.valueOfHand();
    }

    public BlackjackCard getCard(int index, int handNum) {
        return hand.getCard(index);
    }

    public void removeCard(int index, int handNum) {
        hand.removeCard(index);
    }
}
