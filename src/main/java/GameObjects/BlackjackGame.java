package main.java.GameObjects;

import ec.EvolutionState;
import main.java.PlayerObjects.BlackjackPlayer;
import main.java.PlayerObjects.Dealer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class BlackjackGame {

    private BlackjackShoe shoe;
    private BlackjackPlayer player;
    private Dealer dealer;
    private EvolutionState state;
    private int threadNum;
    private int jobNumber;
    private static Logger logger = LogManager.getLogger("player-log");
    private static final int STANDARD_BET = 2;
    private static final int NUMBER_OF_DECKS = 2;
    private static final int BLACKJACK_PAYOUT = 3;
    private static final int BLACKJACK = 21;
    private static final int RESP_HIT = 1;
    private static final int RESP_STAY = 2;
    private static final int RESP_DOUBLE_DOWN = 3;
    private static final int RESP_SPLIT = 4;
    private static final int RESP_BUST = 0;
    private static final int RESP_BLACKJACK = 3;
    private static final int DEALER_HAND_NUM = 0;
    private static final int FIRST_CARD = 0;
    private int distanceFrom21 = 0;
    private int playedGames = 0;
    private int doubleDownMultiplier = 1;
    private int doubleDownCount = 0;

    public BlackjackGame() {
        dealer = new Dealer();
        shoe = new BlackjackShoe(NUMBER_OF_DECKS);
    }

    public void addPlayer(BlackjackPlayer player) {
        this.player = player;
    }

    public double playGames(int numOfGames) {
        for (playedGames = 0; playedGames < numOfGames && player.getFunds() > 0; playedGames++) {
            playBlackjackGame(false);
        }
        if(player.getFunds() < 0) {
            player.modifyFunds(-player.getFunds());
        }
        logPlayerData();
        return getFitness();
    }

    public void determineWinner(int handNum, boolean playerBlackjack) {
        int playerTotal = player.getHandValue(handNum);
        int dealerTotal = dealer.getHandValue(DEALER_HAND_NUM);
        if (dealerTotal == BLACKJACK && playerTotal != BLACKJACK) {
            dealerWin();
        } else if (dealerTotal > BLACKJACK && playerTotal > BLACKJACK) {
            distanceFrom21 += playerTotal - 21;
            dealerWin();
        } else if (dealerTotal <= BLACKJACK && playerTotal > BLACKJACK) {
            distanceFrom21 += playerTotal - 21;
            dealerWin();
        } else if (dealerTotal > BLACKJACK && playerTotal <= BLACKJACK) {
            distanceFrom21 += 21 - playerTotal;
            playerWin(playerBlackjack);
        } else if (dealerTotal > playerTotal) {
            distanceFrom21 += 21 - playerTotal;
            dealerWin();
        } else if (dealerTotal < playerTotal) {
            distanceFrom21 += 21 - playerTotal;
            playerWin(playerBlackjack);
        } else {
            distanceFrom21 += 21 - playerTotal;
            tie();
        }
        doubleDownMultiplier = 1;
    }

    private void dealerWin() {
        player.modifyFunds(-(STANDARD_BET)*doubleDownMultiplier);
        player.addLoss();
    }

    private void playerWin(boolean hasBlackjack) {
        player.addWin();
        if (hasBlackjack) {
            player.modifyFunds(BLACKJACK_PAYOUT * doubleDownMultiplier);
            player.addBlackjackWin();
        } else {
            player.modifyFunds(STANDARD_BET * doubleDownMultiplier);
        }
    }

    private void tie() {
        player.addTie();
    }

    public void addEvolutionData(EvolutionState state, int threadNum) {
        this.state = state;
        this.threadNum = threadNum;
    }

    private void logPlayerData() {
        logger.info("JobNumber=" + jobNumber + "|Generation=" + state.generation + "|Funds=" + player.getFunds()
                + "|Wins=" + player.getWins() + "|BlackjackWins=" + player.getBlackjackWins() + "|Losses="
                + player.getLosses() + "|Ties=" + player.getTies() + "|DoubleDowns=" + doubleDownCount
                + "|Splits=" + player.getSplits() + "|Fitness=" + getFitness());
    }

    private void playBlackjackGame(boolean splitGame) {
        int handNum = player.getNumOfHands();
        BlackjackCard dealerFirstCard;
        if (!splitGame) {
            dealerFirstCard = shoe.drawCard();
            dealer.dealCard(dealerFirstCard, DEALER_HAND_NUM);
            dealer.dealCard(shoe.drawCard(), DEALER_HAND_NUM);
            player.dealCard(shoe.drawCard(), handNum);
            player.dealCard(shoe.drawCard(), handNum);
        } else {
            dealerFirstCard = dealer.getCard(FIRST_CARD, DEALER_HAND_NUM);
        }
        if (dealer.checkCards(DEALER_HAND_NUM) == RESP_BLACKJACK) {
                determineWinner(handNum, false);
            } else if (player.checkCards(handNum) == RESP_BLACKJACK) {
                determineWinner(handNum, true);
            } else {
                boolean didSplitGameHappen = handlePlayerDecision(handNum, dealerFirstCard, splitGame);
                if (!didSplitGameHappen) {
                    handleDealerDecision();
                }
                determineWinner(handNum, false);
            }
            if (splitGame) {
                player.removeHand();
            } else {
                player.flushHand(handNum);
                dealer.flushHand(DEALER_HAND_NUM);
            }
        }

        private boolean handlePlayerDecision(int handNum, BlackjackCard dealerFirstCard, boolean splitGame) {
            boolean continueDealing = true;
            int numOfDecisions = 0;
            while (continueDealing) {
                int status = player.checkCards(handNum);
                if (status != RESP_BLACKJACK && status != RESP_BUST) {
                    int decision = player.decide(dealerFirstCard, numOfDecisions, handNum);
                    if (decision == RESP_DOUBLE_DOWN) {
                        doubleDownMultiplier = 2;
                        player.dealCard(shoe.drawCard(), handNum);
                        doubleDownCount++;
                        return false;
                    } else if (decision == RESP_SPLIT) {
                        splitPlayerHand(handNum, dealerFirstCard);
                        return true;
                    } else if (splitGame) {
                        return false;
                    } else if (decision == RESP_STAY) {
                        return false;
                    } else if (decision == RESP_HIT) {
                        player.dealCard(shoe.drawCard(), handNum);
                        continueDealing = false;
                        numOfDecisions++;
                    }
                } else {
                    continueDealing = false;
                    numOfDecisions++;
                }
            }
            return false;
        }

        private void handleDealerDecision() {
            boolean continueDealing = true;
            while (continueDealing) {
                int status = dealer.checkCards(DEALER_HAND_NUM);
                if (status != RESP_BLACKJACK && status != RESP_BUST && status != RESP_STAY) {
                    dealer.dealCard(shoe.drawCard(), DEALER_HAND_NUM);
                } else if (status == RESP_BLACKJACK) {
                    continueDealing = false;
                } else {
                    continueDealing = false;
                }
            }
        }

        private void splitPlayerHand(int handNum, BlackjackCard dealerFirstCard) {
            int newHand = player.splitHand(handNum);
            player.dealCard(shoe.drawCard(), newHand);
            player.dealCard(shoe.drawCard(), handNum);
            playBlackjackGame(true);
            int nextDecision = player.decide(dealerFirstCard, 0, handNum);
            if (nextDecision == RESP_DOUBLE_DOWN) {
                doubleDownMultiplier = 2;
                player.dealCard(shoe.drawCard(), handNum);
                doubleDownCount++;
                return;
            } else if (nextDecision == RESP_SPLIT) {
                if (player.getNumOfHands() != 3) {
                    splitPlayerHand(handNum, dealerFirstCard);
                } else {
                    player.decideIgnoreSplit(dealerFirstCard, 0, handNum);
                }
            }
        }

        public void setJobNumber(int jobNumber) {
            this.jobNumber = jobNumber;
        }

    public double getFitness() {
        double funds = player.getFunds();
        double distanceDeduction = funds * (distanceFrom21 / ((playedGames) + (player.getSplits()))) * 0.05;
        double situationsDeduction = (funds * 0.8) * (player.getPercentageOfVisitedSituations());
        return funds - distanceDeduction
         - situationsDeduction;
    }
    }
