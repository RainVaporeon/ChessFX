# ChessFX

A simple graphics interface to your chess implementation

## Building

Compiled in Java 21, requires FishUtilities-1.2.18? or higher
(see [RainVaporeon/FishUtilities](https://github.com/RainVaporeon/FishUtilities) for them)

## Development Notes

Generally speaking, most interaction with the board
is communicated via FishHook, and so if you wish to
use this, you should register your own implementation
on the hook so this code can reference from the interface
to interact with the board.
