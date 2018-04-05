package main.java.GameObjects;

import java.util.ArrayList;
import java.util.Collections;

public class BlackjackShoe {

    private ArrayList<BlackjackCard> shoe;
    private ArrayList<BlackjackCard> revealedCards;
    private static final int FIRST_CARD = 0;

    public BlackjackShoe (int numOfDecks) {
        shoe = new ArrayList<>();
        revealedCards = new ArrayList<>();
        initializeDecks(numOfDecks);
    }

    private void initializeDecks(int numOfDecks) {
        for(int i = 0; i < numOfDecks; i++) {
            for (Suit suit : Suit.values()) {
                int rankIndex = 1;
                for (Rank rank : Rank.values()) {
                    shoe.add(new BlackjackCard(rank, suit, rankIndex));
                    rankIndex++;
                }
            }
        }
        Collections.shuffle(shoe);
    }

    public BlackjackCard drawCard() {
        BlackjackCard card = shoe.get(FIRST_CARD);
        shoe.remove(FIRST_CARD);
        revealedCards.add(card);
        if(shoe.size() == 0) {
            shoe = new ArrayList<>(revealedCards);
            revealedCards.clear();
            Collections.shuffle(shoe);
        }
        return card;
    }

    public ArrayList<BlackjackCard> getRevealedCards() {
        return revealedCards;
    }
}
