package main.java.GameObjects;

public class BlackjackCard {

    private Rank rank;
    private Suit suit;
    private int value;

    public BlackjackCard(Rank rank, Suit suit, int rankIndex) {
        this.rank = rank;
        this.suit = suit;
        if(rankIndex < 10) {
            this.value = rankIndex;
        } else if(rankIndex >= 10) {
            this.value = 10;
        }
    }

    public int getValue() {
        return value;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }
}
