1. Files read: bikeshare-good/src/main/java/bikeshare/Bike.java, BikeService.java, CsvRideLog.java, FeeCalculator.java, Main.java, MemberType.java, Menu.java, Ride.java, RideLog.java, Station.java

2.

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
| --- | --- | --- | --- | --- | --- |
| 1 | Check 1 | Menu.java:25-26 | The handler catches `ArrayIndexOutOfBoundsException` and prints its raw message, so a short command shows "Index 2 out of bounds for length 2" to the user. | Note | Check the number of words and print a usage message. |
| 2 | Check 1 | BikeService.java:24-27 | `rent` does not check whether the member already holds a bike, so one member can rent several bikes and `giveBack` (line 31) returns only the first. | Note | Refuse a second rental for the same member. |
| 3 | Check 2 | Menu.java:32-47 | `handle` selects between the commands `rent` and `return` by their first word; cohesion is logical, which is normal for a command dispatcher. | Note | Keep it, or move each command into its own method. |
| 4 | Check 2 | BikeService.java:24 and Menu.java:37 | `rent` returns the whole mutable `Bike`, and `Menu` uses only its id; this is stamp coupling, and `Menu` could also call `bike.giveBack` and bypass the service. | Note | Return only the bike id. |
| 5 | Check 2 | BikeService.java:3-42 | `BikeService` uses `Bike`, `Ride`, `Station`, `MemberType`, `FeeCalculator` and `RideLog`, which is the highest number of dependencies in the project. | Note | Keep it, because it coordinates the other classes. |
| 6 | Check 2 | Menu.java:15-16, 34-35, 40 | The command words in the help text must equal the lower-case names of the enum constants because the code calls `valueOf(...toUpperCase())`; renaming a constant changes the commands. This is connascence of convention. | Note | Map the words to the enum constants in one place. |
| 7 | Check 2 | Menu.java:34-35, 40-41, 45 | The command grammar depends on the position of the words in the array; this is connascence of position. | Note | Parse each command into a small object. |
| 8 | Check 2 | BikeService.java:37 | `new Ride(memberId, found.getId(), station, minutes, fee)` has five positional arguments, and the two ints `minutes` and `fee` can be swapped without a compile error; this is connascence of position. | Note | Use a small builder, or wrap the fee in its own type. |

3. Findings per check: Check 1 has 2 findings (Note 2). Check 2 has 6 findings (Note 6). Fields that are not private: not found. Operations that return an internal collection: not found. Interactions tighter than data coupling other than finding 4: not found. Unclosed resources, empty blocks, unused code and `==` on objects: not found.
