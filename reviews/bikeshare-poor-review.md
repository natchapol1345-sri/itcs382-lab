1. Files read: bikeshare-poor/src/main/java/bikeshare/Main.java

2.

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
| --- | --- | --- | --- | --- | --- |
| 1 | Check 1 | Main.java:44, 48, 58 | `stations[station]` throws `ArrayIndexOutOfBoundsException` for a station number outside 0-2. In `finish` this happens after the receipt is printed (line 55) but before the bike is returned (lines 62-63), so a failed return still prints a receipt. | Should fix | Check the station number before any output or state change. |
| 2 | Check 1 | Main.java:43, 54 | `rent` accepts any member type number, and `finish` charges the staff fee for every number except 1, so an invalid type such as 5 is charged as staff. | Should fix | Reject numbers other than 1 and 2 when renting. |
| 3 | Check 1 | Main.java:24-28, 54 | `minutes` is not checked, so a negative number produces a negative fee. | Should fix | Reject minutes below 1. |
| 4 | Check 1 | Main.java:32 | The program catches `ArrayIndexOutOfBoundsException` to detect a missing word in a command, which hides real indexing bugs such as finding 1. | Note | Check the number of words before using them. |
| 5 | Check 1 | Main.java:51-69 | `finish` nests a loop, two if statements and a try block four levels deep and does several jobs, so it is hard to read. | Should fix | Split lookup, receipt and saving into separate methods. |
| 6 | Check 1 | Main.java:54 | The fee rates 2 and 3 are magic numbers inside an expression. | Note | Name them as constants or keep them with the member type. |
| 7 | Check 2 | Main.java:51-69 | `finish` finds the bike, calculates the fee, prints the receipt, writes the file and updates the bike; the boolean `dryRun` decides which of these happen. Cohesion is logical. | Must fix | Remove the flag and make separate methods for the preview and the return. |
| 8 | Check 2 | Main.java:39-49 | `rent` searches for a bike, changes its state and prints a message; cohesion is procedural. | Note | Return the bike and let the caller print. |
| 9 | Check 2 | Main.java:16-37 | `main` prints the help, reads input, parses numbers and dispatches commands; cohesion is procedural. | Note | Move each command into its own method. |
| 10 | Check 2 | Main.java:7-70 | `Main` holds the menu, the fee rules, the bike records and the file saving, which is weak class cohesion. | Should fix | Move the bike data and the fee rule into their own classes. |
| 11 | Check 2 | Main.java:10-14 | The public static mutable fields `stations`, `bikeIds`, `bikeStation`, `bikeRenter` and `bikeRenterType` are shared by all methods; this is common coupling and the fields are not private. | Should fix | Make the fields private and keep them in a `Bike` class. |
| 12 | Check 2 | Main.java:11-14 | The four arrays describe the same bikes and must keep the same length and index order (the literal 6 is repeated on lines 13 and 14); this is connascence of position. | Should fix | Replace the arrays with one list of `Bike` objects. |
| 13 | Check 2 | Main.java:8-9, 17, 54 | The member type is an int code (1, 2). The meaning is repeated in the help text and in `STUDENT ? 2 : 3`, and every other number means staff; this is connascence of convention. | Should fix | Use an enum with the rate inside it. |
| 14 | Check 2 | Main.java:10, 12, 17-18 | The station is an int code (0, 1, 2) that is both an array index and the number a user types; this is connascence of convention. | Should fix | Use an enum for the stations. |
| 15 | Check 2 | Main.java:26, 28, 51 | The callers pass `false` or `true` for `dryRun`, and the meaning is clear only from the position of the argument; this is connascence of position. | Should fix | Use two methods instead of a flag. |

3. Findings per check: Check 1 has 6 findings (Should fix 4, Note 2). Check 2 has 9 findings (Must fix 1, Should fix 6, Note 2). Coupling between classes (content, control or stamp): not found, because the file has only one class. Methods that return an internal collection: not found. Unclosed resources, empty blocks, unused code and `==` on objects: not found.
