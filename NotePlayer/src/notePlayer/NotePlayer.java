package notePlayer;

import core.MidiWrapper;
import java.util.Scanner;

public class NotePlayer
{



	public static void main(String[] args)
	{
		// This is where you will begin writing your code, and this is where the program will start.
		// Although you can place all of your code here in main, we strongly suggest that you
		// separate your code into multiple helper methods.  Your main method should then call those
		// helper methods at the right places.  Organizing your code like this makes your code easier
		// to read and debug, and helps avoid duplicating code.  

		Scanner console = new Scanner(System.in);

		while (messageLoop(console)) {}

		console.close();
		System.out.println("Thank you for enjoying NotePlayer! Goodbye!");
	}

	private static boolean messageLoop(Scanner console)
	{
		System.out.print("Enter command or send \"quit\" > ");

		Scanner tokenReader = new Scanner(console.nextLine());

		while(tokenReader.hasNext())
		{

			String noteSymbol = tokenReader.next();
			if (noteSymbol.equals("quit"))
			{
				tokenReader.close();
				return false;
			}
			String noteName = noteSymbol.substring(0, noteSymbol.indexOf("_")).toUpperCase();
			int durationms = Integer.parseInt(noteSymbol.substring(noteSymbol.indexOf("_") + 1));
			playNote(noteNumber(noteName), durationms);
		}

		tokenReader.close();



		return true;
	}

	private static int noteNumber(String name)
	{
		int number;
		switch(name.substring(0,1)) // take first character
		{
			case "C":
				number = 60;
				break;
			case "D":
				number = 62;
				break;
			case "E":
				number = 64;
				break;
			case "F":
				number = 65;
				break;
			case "G":
				number = 67;
				break;
			case "A":
				number = 69;
				break;
			case "B":
				number = 71;
				break;
			default:
				return -1;
		}

		if (name.length() == 1) return number; // no accidentals or octave specification

		if (name.substring(1,2).equals("#"))
		{
			number++;
			
			// if the note symbol has three or more characters,
			// parse the rest into an int and multiply the result by 12
			return name.length() >= 3 
					? number + 12 * Integer.parseInt(name.substring(2)) 
					: number;
			
			
		}
		
		if (name.substring(1,2).equals("b"))
		{
			number--;
			
			// copied from above
			// I wish there was a better way but I think this is the most efficient
			return name.length() >= 3 
					? number + 12 * Integer.parseInt(name.substring(2)) 
					: number;
		}
		
		// the last possible scenario is that the note has an octave number but no accidental
		return number + 12 * Integer.parseInt(name.substring(1));
	}


	// You may choose to add extra helper methods here to break up your code into
	// smaller, reusable chunks











	//                      .d"""" """$$$$be.
	//                    -"           ^""**$$$e.
	//                  ."                   '$$$c
	//                 /                      "4$$b
	//                d  3                      $$$$
	//                $  *                   .$$$$$$
	//               .$  ^c           $$$$$e$$$$$$$$.
	//               d$L  4.         4$$$$$$$$$$$$$$b
	//               $$$$b ^ceeeee.  4$$ECL.F*$$$$$$$
	//   e$""=.      $$$$P d$$$$F $ $$$$$$$$$- $$$$$$
	//  z$$b. ^c     3$$$F "$$$$b   $"$$$$$$$  $$$$*"      .=""$c
	// 4$$$$L        $$P"  "$$b   .$ $$$$$...e$$        .=  e$$$.
	// ^*$$$$$c  %..   *c    ..    $$ 3$$$$$$$$$$eF     zP  d$$$$$
	//   "**$$$ec   "   %ce""    $$$  $$$$$$$$$$*    .r" =$$$$P""
	//         "*$b.  "c  *$e.    *** d$$$$$"L$$    .d"  e$$***"
	//           ^*$$c ^$c $$$      4J$$$$$% $$$ .e*".eeP"
	//              "$$$$$$"'$=e....$*$$**$cz$$" "..d$*"
	//                "*$$$  *=%4.$ L L$ P3$$$F $$$P"
	//                   "$   "%*ebJLzb$e$$$$$b $P"
	//                     %..      4$$$$$$$$$$ "
	//                      $$$e   z$$$$$$$$$$%
	//                       "*$c  "$$$$$$$P"
	//                        ."""*$$$$$$$$bc
	//                     .-"    .$***$$$"""*e.
	//                  .-"    .e$"     "*$c  ^*b.
	//           .=*""""    .e$*"          "*bc  "*$e..
	//         .$"        .z*"               ^*$e.   "*****e.
	//         $$ee$c   .d"                     "*$.        3.
	//         ^*$E")$..$"                         *   .ee==d%
	//            $.d$$$*                           *  J$$$e*
	//             """""                              "$$$"

	// WWWWWWWW                           WWWWWWWW                                                      iiii                                        !!!  !!!  !!! 
	// W::::::W                           W::::::W                                                     i::::i                                      !!:!!!!:!!!!:!!
	// W::::::W                           W::::::W                                                      iiii                                       !:::!!:::!!:::!
	// W::::::W                           W::::::W                                                                                                 !:::!!:::!!:::!
	//  W:::::W           WWWWW           W:::::Waaaaaaaaaaaaa  rrrrr   rrrrrrrrr   nnnn  nnnnnnnn    iiiiiiinnnn  nnnnnnnn       ggggggggg   ggggg!:::!!:::!!:::!
	//   W:::::W         W:::::W         W:::::W a::::::::::::a r::::rrr:::::::::r  n:::nn::::::::nn  i:::::in:::nn::::::::nn    g:::::::::ggg::::g!:::!!:::!!:::!
	//    W:::::W       W:::::::W       W:::::W  aaaaaaaaa:::::ar:::::::::::::::::r n::::::::::::::nn  i::::in::::::::::::::nn  g:::::::::::::::::g!:::!!:::!!:::!
	//     W:::::W     W:::::::::W     W:::::W            a::::arr::::::rrrrr::::::rnn:::::::::::::::n i::::inn:::::::::::::::ng::::::ggggg::::::gg!:::!!:::!!:::!
	//      W:::::W   W:::::W:::::W   W:::::W      aaaaaaa:::::a r:::::r     r:::::r  n:::::nnnn:::::n i::::i  n:::::nnnn:::::ng:::::g     g:::::g !:::!!:::!!:::!
	//       W:::::W W:::::W W:::::W W:::::W     aa::::::::::::a r:::::r     rrrrrrr  n::::n    n::::n i::::i  n::::n    n::::ng:::::g     g:::::g !:::!!:::!!:::!
	//        W:::::W:::::W   W:::::W:::::W     a::::aaaa::::::a r:::::r              n::::n    n::::n i::::i  n::::n    n::::ng:::::g     g:::::g !!:!!!!:!!!!:!!
	//         W:::::::::W     W:::::::::W     a::::a    a:::::a r:::::r              n::::n    n::::n i::::i  n::::n    n::::ng::::::g    g:::::g  !!!  !!!  !!! 
	//          W:::::::W       W:::::::W      a::::a    a:::::a r:::::r              n::::n    n::::ni::::::i n::::n    n::::ng:::::::ggggg:::::g                
	//           W:::::W         W:::::W       a:::::aaaa::::::a r:::::r              n::::n    n::::ni::::::i n::::n    n::::n g::::::::::::::::g  !!!  !!!  !!! 
	//            W:::W           W:::W         a::::::::::aa:::ar:::::r              n::::n    n::::ni::::::i n::::n    n::::n  gg::::::::::::::g !!:!!!!:!!!!:!!
	//             WWW             WWW           aaaaaaaaaa  aaaarrrrrrr              nnnnnn    nnnnnniiiiiiii nnnnnn    nnnnnn    gggggggg::::::g  !!!  !!!  !!! 
	//                                                                                                                                     g:::::g                
	//                                                                                                                         gggggg      g:::::g                
	//                                                                                                                         g:::::gg   gg:::::g                
	//                                                                                                                          g::::::ggg:::::::g                
	//                                                                                                                           gg:::::::::::::g                 
	//                                                                                                                             ggg::::::ggg                   
	//                                                                                                                                gggggg                         

	// WARNING!!!!  You will CALL these methods, but you MUST NOT MODIFY any
	// of the code below


	/**
	 * WARNING!!!  DO NOT MODIFY THIS METHOD.
	 * 
	 * Once you have calculated the MIDI note number and its duration, call this
	 * method to play that note.
	 * 
	 * @param noteNumber The MIDI note number, as described in the spec.
	 * @param durationMs The number of milliseconds to play the note.  A larger number will play the note for a longer time.
	 */
	public static void playNote(int noteNumber, int durationMs)
	{
		// WARNING!!!  DO NOT MODIFY THIS METHOD.
		MidiWrapper.playNote(noteNumber, durationMs);
	}




	/**
	 * WARNING!!!  DO NOT MODIFY THIS METHOD.
	 * 
	 * Call this method to change the instrument used to play notes.
	 * 
	 * @param instrumentNumber The MIDI instrument number to begin using.  Must
	 * be in the range between 0 and 127 inclusive.
	 */
	public static void setInstrument(int instrumentNumber)
	{
		// WARNING!!!  DO NOT MODIFY THIS METHOD.
		MidiWrapper.setInstrument(instrumentNumber);
	}

}
