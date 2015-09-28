public class Blackjack extends ConsoleProgram
{

    private static final int HEARTS = 0;
    private static final int DIAMONDS = 1;
    private static final int SPADES = 2;
    private static final int CLUBS = 3;
    
    private static final int JACK = 11;
    private static final int QUEEN = 12;
    private static final int KING = 13;
    private static final int ACE = 14;
    
    private String getPlayerMove()
    {
    	while(true)
    	{
        	String move = readLine("Enter move (hit/stand): ").toLowerCase();
        	if(move.equals("hit") || move.equals("stand"))
        	{
        		return move;
        	}
        	System.out.println("Please try again.");
    	}
    }

    private void dealerTurn(Hand dealer, Deck deck)
    {
		while(true)
		{			
			int value = dealer.getValue();
			System.out.println("Dealer's hand");
			System.out.println(dealer);		
			System.out.println("Dealer's hand has value " + value);
			readLine("Enter to continue...");

			if(value < 17)
			{
				System.out.println("Dealer hits");
				dealer.addCard(deck.deal());
				
				if(dealer.busted())
				{
					System.out.println("Dealer busted!");
					break;
				}
			}
			else
			{
				System.out.println("Dealer stands");
				break;
			}
		}
    }
    
    // Play a turn. Return if the player busted.
    private boolean playerTurn(Hand player, Deck deck)
    {
		while(true)
		{
			String move = getPlayerMove();
			if(move.equals("hit"))
			{
				player.addCard(deck.deal());
				System.out.println("Player's hand");
				System.out.println(player);
				
				if(player.busted())
				{
					return true;
				}
			}
			else
			{
				return false;
			}
		}
    }
    
    private boolean push(Hand player, Hand dealer)
    {
    	return player.getValue() == dealer.getValue();
    }
    
    private boolean playerWins(Hand player, Hand dealer)
    {
    	if(player.busted())
    	{
    		return false;
    	}
    	if(dealer.busted())
    	{
    		return true;
    	}
    	
    	return player.getValue() > dealer.getValue();
    }
    
    private void play()
    {
    	int bankroll = 100;

    	while(true)
    	{
    		bankroll = playRound(bankroll);
    		
    		String playAgain = readLine("Would you like to play again? (Y/N)");
    		if(playAgain.equalsIgnoreCase("N"))
    		{
    			break;
    		}
    	}
    	System.out.println("Thanks for playing!");
    }
    
    private int playRound(int bankroll)
    {
		Deck deck = new Deck();
		
		Hand player = new Hand();
		Hand dealer = new Hand();
		
		deck.shuffle();
		
		System.out.println("Starting bankroll: " + bankroll);
		int bet = readInt("What is your bet? ");

		player.addCard(deck.deal());
		dealer.addCard(deck.deal());
		player.addCard(deck.deal());
		dealer.addCard(deck.deal());
		
		System.out.println("Player's hand");
		System.out.println(player);
		
		System.out.println("Dealer's hand");
		dealer.printDealerHand();
		
		boolean playerBusted = playerTurn(player, deck);
		if(playerBusted)
		{
			System.out.println("You busted.");
		}
		
		dealerTurn(dealer, deck);
		
		if(playerWins(player, dealer))
		{
			System.out.println("Player wins!");
			bankroll += bet;
		}
		else if(push(player, dealer))
		{
			System.out.println("You push.");			
		}
		else
		{
			System.out.println("Dealer wins.");
			bankroll -= bet;
		}

		System.out.println("Total bankroll: " + bankroll);
		
		return bankroll;
    }
	
	public void run()
	{
		play();
    	//Blackjack b = new Blackjack();
    	//b.play();
	}
	
}
