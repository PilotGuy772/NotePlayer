package notePlayer;

import core.MidiWrapper;
import java.util.Scanner;

public class NotePlayer
{


	private static final String InstrumentList =
			"\n0: Piano 1        1: Piano 2        2: Piano 3        3: Honky-tonk     4: E.Piano 1\n"
			+ "5: E.Piano 2      6: Harpsichord    7: Clav.          8: Celesta        9: Glockenspiel\n"
			+ "10: Music Box     11: Vibraphone    12: Marimba       13: Xylophone     14: Tubular-bell\n"
			+ "15: Santur        16: Organ 1       17: Organ 2       18: Organ 3       19: Church Org.1\n"
			+ "20: Reed Organ    21: Accordion Fr  22: Harmonica     23: Bandoneon     24: Nylon-str.Gt\n"
			+ "25: Steel-str.Gt  26: Jazz Gt.      27: Clean Gt.     28: Muted Gt.     29: Overdrive Gt\n"
			+ "30: DistortionGt  31: Gt.Harmonics  32: Acoustic Bs.  33: Fingered Bs.  34: Picked Bs.\n"
			+ "35: Fretless Bs.  36: Slap Bass 1   37: Slap Bass 2   38: Synth Bass 1  39: Synth Bass 2\n"
			+ "40: Violin        41: Viola         42: Cello         43: Contrabass    44: Tremolo Str\n"
			+ "45: PizzicatoStr  46: Harp          47: Timpani       48: Strings       49: Slow Strings\n"
			+ "50: Syn.Strings1  51: Syn.Strings2  52: Choir Aahs    53: Voice Oohs    54: SynVox\n"
			+ "55: OrchestraHit  56: Trumpet       57: Trombone      58: Tuba          59: MutedTrumpet\n"
			+ "60: French Horns  61: Brass 1       62: Synth Brass1  63: Synth Brass2  64: Soprano Sax\n"
			+ "65: Alto Sax      66: Tenor Sax     67: Baritone Sax  68: Oboe          69: English Horn\n"
			+ "70: Bassoon       71: Clarinet      72: Piccolo       73: Flute         74: Recorder\n"
			+ "75: Pan Flute     76: Bottle Blow   77: Shakuhachi    78: Whistle       79: Ocarina\n"
			+ "80: Square Wave   81: Saw Wave      82: Syn.Calliope  83: Chiffer Lead  84: Charang\n"
			+ "85: Solo Vox      86: 5th Saw Wave  87: Bass & Lead   88: Fantasia      89: Warm Pad\n"
			+ "90: Polysynth     91: Space Voice   92: Bowed Glass   93: Metal Pad     94: Halo Pad\n"
			+ "95: Sweep Pad     96: Ice Rain      97: Soundtrack    98: Crystal       99: Atmosphere\n"
			+ "100: Brightness   101: Goblin       102: Echo Drops   103: Star Theme   104: Sitar\n"
			+ "105: Banjo        106: Shamisen     107: Koto         108: Kalimba      109: Bagpipe\n"
			+ "110: Fiddle       111: Shanai       112: Tinkle Bell  113: Agogo        114: Steel Drums\n"
			+ "115: Woodblock    116: Taiko        117: Melo. Tom 1  118: Synth Drum   119: Reverse Cym.\n"
			+ "120: Gt.FretNoise 121: Breath Noise 122: Seashore     123: Bird         124: Telephone 1\n"
			+ "125: Helicopter   126: Applause     127: Gun Shot";
	
	

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

		String input = console.nextLine();
		

		
		// SPECIAL COMMANDS //
		
		// quit
		if (input.equals("quit"))
		{
			return false;
		}
		
		// `list instruments`
		if (input.equals("list instruments"))
		{
			System.out.println(InstrumentList);
			return true;
		}
		
		// `set instrument <>`
		if (input.startsWith("set instrument "))
		{
			// this is rather inelegant I admit
			// but we are supposed to have zero tolerance for
			// malformed inputs and no exception handling,
			// so I'll let it slide for now
			setInstrument(Integer.parseInt(input.substring(15)));
			return true;
		}
		
		// NOTE to Mr. George...
		/*
		 * I feel like the commands for listing and setting
		 * instruments really ought to be in the form
		 * `instrument list` and `instrument set`...
		 * in CLIs the standard form is to have a command
		 * (i.e., `git`), a sub-command (i.e., `remote`)
		 * and then maybe more sub-commands (i.e., `add`).
		 * So, in following the standard model, shouldn't we
		 * group instrument-related commands under one `instrument`
		 * sub-command with further sub-commands of `list` and `set`?
		 * */
		
		// check for playback adjustment
		int commaPos = input.indexOf(",");
		int transpose = 0;
		double tempo = 1.0;
		if (commaPos != -1)
		{
			String adjustment = input.substring(0, commaPos);
			input = input.substring(commaPos + 1);
			transpose = Integer.parseInt(adjustment.substring(0, adjustment.indexOf("_")));
			tempo = Double.parseDouble(adjustment.substring(adjustment.indexOf("_") + 1));
			
			// we need to recalculate!
			// for extra credit ;)
		
			Scanner noteScanner = new Scanner(input);
			String newInput = "";
			
			while (noteScanner.hasNext())
			{
				String noteSymbol = noteScanner.next();
				
				// we might have a chord!!
				// so let's pull the scanner trick from further down
				// to iterate through and maintain a string representing the
				// updated notes to date
				String newNotes = "";
				Scanner noteStepper = new Scanner(noteSymbol.substring(0, noteSymbol.indexOf("_")));
				noteStepper.useDelimiter("(?=[A-G])");
				while (noteStepper.hasNext())
				{
					newNotes += getNoteFromNumber(noteNumber(noteStepper.next()) + transpose);
				}
				
				int newDuration = (int) (Integer.parseInt(noteSymbol.substring(noteSymbol.indexOf("_") + 1)) * tempo);
				
				
				newInput = newInput + newNotes + "_" + newDuration + " ";
			}
			
			noteScanner.close();
			
			// why bother going to all the effort of calculating this stuff again
			// if we've just figure it all out already?
			System.out.println(newInput);
			input = newInput;
			
		}
		
		
		Scanner tokenReader = new Scanner(input);

		
		
		while(tokenReader.hasNext())
		{

			String noteSymbol = tokenReader.next();
			
			
			String noteName = noteSymbol.substring(0, noteSymbol.indexOf("_"));
			int durationms = Integer.parseInt(noteSymbol.substring(noteSymbol.indexOf("_") + 1));
			
			// noteName might have more than one note (i.e., a chord)! We will use another Scanner to step through the notes
			Scanner noteStepper = new Scanner(noteName);
			// this regex will match if the character immediately following a given index is
			// between A and G. This is called a "positive lookahead." It will "look ahead" 
			// for the given sequence and match if the given sequence exists following
			// a particular index. Importantly, this does NOT consume the characters that
			// are responsible for causing it to match. Basically, this preserves the note 
			// name that starts the sequence.
			noteStepper.useDelimiter("(?=[A-G])");
			
			// again, super inelegant, but I'm not allowed to use arrays so
			int one = 0;
			int two = 0;
			int three = 0;
			int four = 0;
			int count = 0;
			
			while (noteStepper.hasNext())
			{
				count++; // keep track of how many valid notes there are
				switch (count) // assign the variables one, two, three, and four to the appropriate notes
				{
					case 1:
						one = noteNumber(noteStepper.next());
						break;
					case 2:
						two = noteNumber(noteStepper.next());
						break;
					case 3:
						three = noteNumber(noteStepper.next());
						break;
					case 4:
						four = noteNumber(noteStepper.next());
						break;
				}
			}
			
			
			
			noteStepper.close();
			
			// and call the appropriate function based on the count
			switch(count)
			{
				case 1:
					playNote(one, durationms);
					break;
				case 2:
					MidiWrapper.playTwoNoteChord(one, two, durationms);
					break;
				case 3:
					MidiWrapper.playThreeNoteChord(one, two, three, durationms);
					break;
				case 4:
					MidiWrapper.playFourNoteChord(one, two, three, four, durationms);
					break;
			}
			
			
			
			
		}

		tokenReader.close();



		return true;
	}

	private static int noteNumber(String name)
	{
		int number;
		switch(name.substring(0,1).toUpperCase()) // take first character
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
			return name.length() >= 3                                   // if more than two characters (i.e., C (1) # (2) 5 (3))
					? number + 12 * Integer.parseInt(name.substring(2)) // that must mean there's an octave number... so we have to add 12 * the number (per the spec)
					: number;                                           // otherwise, we make no change and just return the number as it is
			
			
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
	
	
	private static String getNoteFromNumber(int num)
	{
		// we know note numbers are calculated
		// such that C0 = 60 and each half step
		// advances the count by 1. We can use
		// modulus to get the scale degree in
		// the C Major scale.
		// 60 % 12 = 0 = (C)
		// 65 % 12 = 5 = C C# D D# (E)
		// I would love to use an array for this,
		// but a switch-case will do.
		String note;
		switch (num % 12)
		{
			case 0:  note = "C"; break;
	        case 1:  note = "C#"; break;
	        case 2:  note = "D"; break;
	        case 3:  note = "D#"; break;
	        case 4:  note = "E"; break;
	        case 5:  note = "F"; break;
	        case 6:  note = "F#"; break;
	        case 7:  note = "G"; break;
	        case 8:  note = "G#"; break;
	        case 9:  note = "A"; break;
	        case 10: note = "A#"; break;
	        case 11: note = "B"; break;
	        default: note = ""; break;
		}
		
		// we also need the octave number specification
		int octave = (int)Math.floor(num / 12) - 5;
		
		return octave == 0 ? note : note + octave;
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
