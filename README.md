# Note Player

*Attention Mr. George!*
I am going to start breaking the rules in this project!!! Revision 4705e21 is the latest one that I can guarantee will follow all the class rules; that one should be my final submission on BuildCI as well.

This is my NotePlayer project for my AP Computer Science A course. It's a simple Java program that uses `javax.sound.midi` to play notes using MIDI by interpreting a goofy syntax in a console interface.

## Syntax

In accord with the NotePlayer specification, notes may be described using the following syntax: `[note](accidental)(octave)_[duration]`.

`note` represents a letter note name (i.e., from A-G). `accidental` represents an optional accidental to put on the note ("b" for flat, "#" for sharp). `octave` represents an optional increase or decrease in the octave to play the note at. If omitted, it plays at octave 0, which is how MIDI refers to the octave that middle C is on. Finally, `duration` refers to the amount of time to play the note in milliseconds.
So, a complete note would be, for example, `F#3_500` to play F# in the third octave up from the default for 500 milliseconds. 

These notes may be chained together in one command with multiple notes separated by spaces. They are interpreted at once and played in series. More than one note may be specified per note symbol to specify
a chord. For example, `CEG_500` plays a C Major chord in the default octave. You may specify up to four unique notes per symbol. You can also specify accidentals and octave numbers for each note. For example, 
`A3C#4E4_1000` to play an A Major chord starting at A in the third octave.

Another syntax exists for applying adjustments to all notes specified in a command. You may optionally prepend `[transposition]_[tempo multiplier],` to your command. `transposition` is an integer representing the
total number of half steps to adjust each note up or down. `tempo multiplier` is a float representing a multiplier to apply to the duration of each note. If adjustments are specified, the entire form must
be present. An example with adjustments: `1_0.5,Cb_1000 Eb_1000 Gb_1000` to play a C Major arpeggio with 500ms per note.

You may also change the MIDI instrument used to play the note! Use `list instruments` to print a list of supported MIDI instruments and `set instrument [instrument]` to select the instrument to use where `instrument`
is the integer index of the instrument to set.

## Special Features

These features are NOT implemented yet!! I will tick them off as I go. These are extensions of the specification described above that I am doing for a wee challenge.

### ⛔ Read from file

Add a feature to tell NotePlayer to interpret batch commands from a file on disk. Use the command `file read [path]` with a relative or fully-qualified path to a text file to source it. NotePlayer will then
immediately start interpreting lines of the file as commands.

The point of this is to have an easy way to play saved songs without pasting long orders into the console.

### ⛔ Record to file

Add a feature to have NotePlayer keep track of your commands as you use it. Start recording with `record start`, stop with `record stop`. Scratch the most recent command from the record with `record undo`.
Save the record to a file with `file write [path]`. The record will concatenate consecutive note commands together into one command when they are written to the file. For example, if I send `C_500 E_500` in
one command, then send `G_500` in the next, the line written to the file will be `C_500 E_500 G_500`. Adjustments will be calculated during recording and the result of the calculations will be written to
the file instead of the original and the adjustments.

Again, the point of this is to have an easier way to workshop a song and save it somewhere more convenient when you're done with it.

### ⛔ Meter

Add a mode for playing notes in meter as opposed to specifying duration in milliseconds. Use the command `meter on` to switch to meter mode. Now, the syntax for adjustments has changed. Adjustments will follow the syntax of `[transposition]_[tempo bpm]_[time]/[signature]`. `transposition` remains unchanged. `tempo bpm` now represents the tempo in beats per minute (duh). The last token, `time/signature` represents the time signature for the piece. The left half of the slash represents the number of beats per measure, and the right half represents the value of a single beat. `4/4` is four beats per measure with each beat having one quarter note. `6/8` is six beats per measure with each beat having an eighth note. You get the idea. When adjustments are omitted, the default tempo is 60 and the default meter is 4/4.

When in meter mode, the `duration` portion of a note symbol no longer represents its duration in milliseconds; instead, it represents the note's value. It gets more complicated when we introduce ties. We need to have support for more complex rhythms, which may be introduced by tying notes. If we want a single note to have a value of three quarter notes, we would need to tie a half note and a quarter note. In order to do this, we extend the syntax further with a dash. In meter mode, NotePlayer will recognize multiple note values tied together with a dash and sum the values together in order to play one note for the duration of the tied note. For example, `C_2-4` plays a C for the total value of three quarter notes. Likewise, `C_1-2-4-8` plays a C for the value of a measure and a half plus a dotted quarter note. There are no restrictions on this-- the code just dumbly adds the durations for each of the values.

Here's some examples:
* Note value `4` in meter `4/4` at 60bpm. This is simple. A quarter note represents one beat. At 60bpm, this means 1000ms.
* Note value `2` in meter `4/4` at 60bpm. A half note is 4 / 2 = 2 beats. This note would be 2 * 1000 = 2000ms.
* Note value `4-8` in meter `6/8` at 240bpm (6/8 is usually beat in two, so this is actually 80bpm for the dotted quarter). 60,000 / 240 = 250ms per beat. A quarter note is 8 / 4 = 2 beats. This note is 250 * 2 = 500ms. An eighth note is 8 / 8 = 1 beat. This note is 250 * 1 = 250ms. 500ms + 250ms = 750ms total.
* Note value `8-16` in meter `4/4` at 60bmp. This is a dotted eighth. The eighth note is 4 / 8 = 0.5 beats. This note is 1000 * 0.5 = 500ms. The sixteenth note is 4 / 16 = 0.25 beats. This note is 1000 * 0.25 = 250ms. Total value is 500ms + 250ms = 750ms.  


Here is a full example of a valid command in meter mode.

`0_120_4/4,C_4-8 D_8 E_8 E_8 F_8 F_8 G_2-4 A_4 B_4 C1_1`

This plays a goofy C major scale with some dotted rhythms.
