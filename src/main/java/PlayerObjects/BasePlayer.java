package main.java.PlayerObjects;

import main.java.GameObjects.BlackjackCard;

public interface BasePlayer {

    void dealCard(BlackjackCard card, int hand);
    int checkCards(int hand);
    void flushHand(int hand);
    int getHandValue(int hand);
    BlackjackCard getCard(int index, int hand);
}
