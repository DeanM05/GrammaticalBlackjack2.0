package main.java.PlayerObjects;


import main.java.EvolutionObjects.BlackjackIndividual;
import main.java.GameObjects.BlackjackCard;

import java.util.ArrayList;

public class BlackjackPlayer implements BasePlayer {

    private ArrayList<Hand> hands;
    private double funds;
    private BlackjackIndividual ind;
    private int wins = 0;
    private int blackjackWins = 0;
    private int losses = 0;
    private int ties = 0;
    private int numOfHands = 0;
    private int splits = 0;
    private int[] visitedSituations = new int[320];

    private static final int BUST = 0;
    private static final int CONTINUE = 1;
    private static final int HIT = 1;
    private static final int BLACKJACK = 21;
    private static final int RESP_BLACKJACK = 3;
    private static final int RESP_DOUBLE_DOWN = 4;
    private static final int SECOND_CARD = 1;
    private static final int FIRST_DECISION = 0;
    private static final int NUM_OF_REGULAR_CHOICES = 140;
    private static final int NUM_OF_SOFT_CHOICES = 80;

    public BlackjackPlayer(double funds, BlackjackIndividual ind) {
        this.funds = funds;
        this.ind = ind;
        this.hands = new ArrayList<>();
        Hand initialHand = new Hand();
        hands.add(initialHand);

    }

    public void addWin() {
        wins++;
    }

    public BlackjackIndividual getIndividual() {
        return ind;
    }

    public void addBlackjackWin() {
        blackjackWins++;
    }

    public void addLoss() {
        losses++;
    }

    public void addTie() {
        ties++;
    }

    public int getWins() {
        return wins;
    }

    public int getBlackjackWins() {
        return blackjackWins;
    }

    public int getLosses() {
        return losses;
    }

    public int getTies() {
        return ties;
    }

    public int getSplits() {
        return splits;
    }

    public void dealCard(BlackjackCard card, int hand) {
        hands.get(hand).addCard(card);
    }

    public void flushHand(int hand) {
        hands.get(hand).flush();
    }

    public int checkCards(int hand) {
        int value = hands.get(hand).valueOfHand();
        if (value > BLACKJACK) {
            return BUST;
        } else if (value == BLACKJACK) {
            return RESP_BLACKJACK;
        }
        return CONTINUE;
    }

    public void modifyFunds(double winLoss) {
        funds += winLoss;
    }

    public double getFunds() {
        return funds;
    }

    public int getHandValue(int hand) {
        return hands.get(hand).valueOfHand();
    }

    public int decide(BlackjackCard dealerCard, int numOfDecisions, int handNum) {

        //10 possible dealer cards, 14 possible non-soft hands, 8 possible soft hands, 10 possible splittable hands
        //Required array: 10*32 = 320 values
        //Since array must be one-dimensional, index 0-9 will be for one player hand, 10-19 for another etc.
        Hand hand = hands.get(handNum);
        int playerValue = hand.valueOfHand();
        int dealerValue = dealerCard.getValue();
        int[] genome = (int[])ind.getGenome();
        int index = getIndex(hand, numOfDecisions, playerValue, dealerValue);
        int decision = genome[index];
        visitedSituations[index]++;
        if (decision == RESP_DOUBLE_DOWN && numOfDecisions != FIRST_DECISION) {
            return HIT;
        }
        return decision;
    }

    public int decideIgnoreSplit(BlackjackCard dealerCard, int numOfDecisions, int handNum) {
        Hand hand = hands.get(handNum);
        int playerValue = hand.valueOfHand();
        int dealerValue = dealerCard.getValue();
        int[] genome = (int[])ind.getGenome();
        int index = dealerValue - 1;
        if (hand.isHandSoft()) {
            index += NUM_OF_REGULAR_CHOICES + (10 * (playerValue - 12));
        } else {
            if (playerValue > 18) {
                playerValue = 18;
            } else if (playerValue < 5) {
                playerValue = 5;
            }
            index += 10 * (playerValue - 5);
        }
        int decision = genome[index];
        visitedSituations[index]++;
        if (decision == RESP_DOUBLE_DOWN && numOfDecisions != FIRST_DECISION) {
            return HIT;
        }
        return decision;
    }

    private int getIndex(Hand hand, int numOfDecisions, int playerValue, int dealerValue) {
        int index = (dealerValue - 1);
        if (hand.isHandSplittable() && numOfDecisions == FIRST_DECISION) {
            return NUM_OF_REGULAR_CHOICES + NUM_OF_SOFT_CHOICES + (10 * (hand.splittableCardValues() - 1)) + index;
        } else if (hand.isHandSoft()) {
            return NUM_OF_REGULAR_CHOICES + (10 * (playerValue - 12)) + index;
        } else {
            if (playerValue > 18) {
                playerValue = 18;
            }
            return 10 * (playerValue - 5) + (dealerValue - 1);
        }
    }

    public int initializeNewHand() {
        Hand hand = new Hand();
        hands.add(hand);
        numOfHands++;
        return numOfHands;
    }

    public void removeHand() {
        hands.remove(numOfHands);
        numOfHands--;
    }

    public int getNumOfHands() {
        return numOfHands;
    }

    public BlackjackCard getCard(int index, int hand) {
        return hands.get(hand).getCard(index);
    }

    public void removeCard(int index, int hand) {
        hands.get(hand).removeCard(index);
    }

    public int splitHand(int hand) {
        int newHand = initializeNewHand();
        hands.get(newHand).addCard(getCard(SECOND_CARD, hand));
        removeCard(SECOND_CARD, hand);
        splits++;
        return newHand;
    }

    public double getPercentageOfVisitedSituations() {
        double numOfVisitedSituations = 0;
        for (int i = 0; i < visitedSituations.length; i++) {
            if (visitedSituations[i] >= 1) {
                numOfVisitedSituations++;
            }
        }
        ind.setVisitedSituations(visitedSituations);
        return numOfVisitedSituations / 320;
    }

}
