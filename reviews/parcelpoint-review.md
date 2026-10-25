1. Files read: parcelpoint/src/main/java/parcelpoint/Locker.java, Main.java, Parcel.java, ParcelService.java

2.

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
| --- | --- | --- | --- | --- | --- |
| 1 | Check 1 | ParcelService.java:42 | `parcel.getCode() == code` compares two Strings with `==`, so a code typed by the user never matches the stored code and `pickUp` always returns 0 without freeing the locker. | Must fix | Use `parcel.getCode().equals(code)`. |
| 2 | Check 1 | ParcelService.java:76-79 | The `FileWriter` is never closed or flushed, so `parcels.csv` stays empty and the file handle leaks on every save. | Must fix | Open the writer in a try-with-resources statement. |
| 3 | Check 1 | ParcelService.java:80-81 | The `catch (IOException e)` block is empty, so a failed save is silently ignored. | Must fix | Report the error, for example by throwing an `UncheckedIOException`. |
| 4 | Check 1 | ParcelService.java:6 | The import `java.util.Date` is never used. | Should fix | Delete the import. |
| 5 | Check 1 | ParcelService.java:12 | The private field `maxParcels` is never read or written after its declaration. | Should fix | Delete the field. |
| 6 | Check 1 | Main.java:8 | The `Scanner` on `System.in` is never closed. | Note | Open it in a try-with-resources statement. |
| 7 | Check 1 | Main.java:10-26 | `main` reads input, parses it and dispatches five commands in one if/else chain, which is hard to read and to extend. | Note | Move each command branch into a small method. |
| 8 | Check 2 | ParcelService.java:24-37 | `storeParcel` finds a locker, changes the parcel status, prints an SMS and saves the file in one method; cohesion is procedural. | Should fix | Move the saving (and later the SMS) out of this method. |
| 9 | Check 2 | ParcelService.java:39-50 | `pickUp` frees the locker, changes the status, saves the file and calculates the fee; cohesion is procedural. | Should fix | Separate the locker update from the saving. |
| 10 | Check 2 | ParcelService.java:64-72 | `printParcels(boolean forStaff)` prints one of two different outputs depending on the flag; cohesion is logical. | Should fix | Split it into two methods, one per output. |
| 11 | Check 2 | Main.java:6-27 | `main` parses text, calls the service and prints results; cohesion is procedural. | Note | Keep it as the menu, but move parsing of each command into methods. |
| 12 | Check 2 | ParcelService.java:9-83 | `ParcelService` handles locker allocation, fee rules, console printing, SMS text and CSV saving, which is weak class cohesion. | Should fix | Move the file saving into its own class first. |
| 13 | Check 2 | ParcelService.java:52-62 | `fee` does not use any attribute of `ParcelService`; it only works on the `Parcel` parameter and its size. | Note | Move the fee rule next to the size data. |
| 14 | Check 2 | ParcelService.java:28, 44, 67, 68, 78 and Parcel.java:7 | `Parcel.status` is a public field that `ParcelService` writes and reads directly; this is content coupling and a non-private field. | Should fix | Make the field private and add a getter and a setter. |
| 15 | Check 2 | ParcelService.java:20-22 and Main.java:22 | `getLockers` returns the internal list, and `Main` reaches through `service` into every `Locker` to count free ones. | Should fix | Return an unmodifiable list, or add a method in the service that returns the count. |
| 16 | Check 2 | Main.java:13-22 | `Main` depends on `ParcelService`, `Parcel` and `Locker`; it builds the parcel itself and inspects lockers. | Note | Let the service create the parcel and answer the free-locker count. |
| 17 | Check 2 | ParcelService.java:52 | `fee(Parcel, int)` receives a whole `Parcel` but only reads its size; this is stamp coupling. | Note | Pass only the size and the days. |
| 18 | Check 2 | Locker.java:5, Parcel.java:6, ParcelService.java:15-17, 26, 56-61 | The size is an int code (1, 2, 3) that four places must read in the same way, including the rule that a bigger number means a bigger size; this is connascence of convention. | Should fix | Replace the int codes with an enum. |
| 19 | Check 2 | Parcel.java:13, ParcelService.java:28, 44, 68 | The status values "NEW", "STORED" and "PICKED_UP" are string literals that must match across classes; this is connascence of convention. | Should fix | Replace the strings with an enum. |
| 20 | Check 2 | Main.java:13 | `new Parcel(words[1], words[2], Integer.parseInt(words[3]))` depends on the order of the arguments, and the two adjacent Strings (code, student ID) can be swapped without an error; this is connascence of position. | Note | Name the arguments through a small parsing method or a builder. |

3. Findings per check: Check 1 has 7 findings (Must fix 3, Should fix 2, Note 2). Check 2 has 13 findings (Should fix 8, Note 5). Strings repeated many times: not found. Methods that return a mutable collection other than `getLockers`: not found.
