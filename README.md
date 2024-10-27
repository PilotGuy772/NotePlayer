# Note Player
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
