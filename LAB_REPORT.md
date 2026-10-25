# Lab Report, Code Quality and Design Quality

Repository URL: https://github.com/natchapol1345-sri/itcs382-lab

## Tools

| Item | Version or name |
| --- | --- |
| JDK | openjdk version "25.0.4.1" 2026-08-18 LTS (Eclipse Temurin) |
| Maven | Apache Maven 3.9.6 |
| IDE | Visual Studio Code 1.142.0 |
| SonarQube for IDE | SonarLint for VS Code 6.2.0 |
| AI tool and exact model | Claude (claude.ai), model Claude Sonnet 5.5 |

## Task 1. SonarQube on the starter code

Commit of the starter, before any change: b71521b

What looked wrong when I ran the starter with `store P1 6501 2`, `pickup P1 5` and `staff`: the pickup printed `Fee: 0.0`, the parcel stayed `STORED`, the program reported 2 free lockers (the locker was not freed), and `parcels.csv` was empty.

Table 1. SonarQube issues in the starter code

| No. | Rule | File:line | Message |
| --- | --- | --- | --- |
| 1 | java:S1104 | Parcel.java:7 | Make status a static final constant or non-public and provide accessors if needed. |
| 2 | java:S1128 | ParcelService.java:6 | Remove this unused import 'java.util.Date'. |
| 3 | java:S1068 | ParcelService.java:12 | Remove this unused "maxParcels" private field. |
| 4 | java:S4973 | ParcelService.java:42 | Strings and Boxed types should be compared using "equals()". |
| 5 | java:S2095 | ParcelService.java:76 | Use try-with-resources or close this "FileWriter" in a "finally" clause. |
| 6 | java:S108 | ParcelService.java:80 | Either remove or fill this block of code. |
| 7 | java:S106 | ParcelService.java:30 | Replace this use of System.out by a logger. |
| 8 | java:S106 | ParcelService.java:67 | Replace this use of System.out by a logger. |
| 9 | java:S106 | ParcelService.java:69 | Replace this use of System.out by a logger. |
| 10 | java:S106 | Main.java:9 | Replace this use of System.out by a logger. |
| 11 | java:S106 | Main.java:15 | Replace this use of System.out by a logger. |
| 12 | java:S106 | Main.java:17 | Replace this use of System.out by a logger. |
| 13 | java:S106 | Main.java:22 | Replace this use of System.out by a logger. |

## Task 2. AI review of the starter code

| Item | Value |
| --- | --- |
| Date | 25 October 2026 |
| AI tool and exact model | Claude (claude.ai), Claude Sonnet 5.5 |
| Commit reviewed | b71521b |
| How the AI read the files (attached the files, opened the folder, or pasted the files) | The four Java files were opened from the uploaded starter project inside the AI's sandbox (Prompt A as given, with `<folder>` replaced by `parcelpoint`) |
| Output file in the reviews folder | reviews/parcelpoint-review.md |

Table 2. All findings on the starter code

| No. | File:line | Finding | Found by (SonarQube, AI or both) | SonarQube rule or course term | Correct? (yes or no, and why) |
| --- | --- | --- | --- | --- | --- |
| 1 | Parcel.java:7 | Public field `status`, written and read directly by ParcelService | both | java:S1104, content coupling | yes, the field is public and ParcelService assigns it at lines 28 and 44 |
| 2 | ParcelService.java:6 | Unused import `java.util.Date` | both | java:S1128 | yes, `Date` appears nowhere in the file |
| 3 | ParcelService.java:12 | Unused private field `maxParcels` | both | java:S1068 | yes, nothing reads the field |
| 4 | ParcelService.java:42 | Strings compared with `==` in `pickUp` | both | java:S4973 | yes, running `pickup P1 5` printed `Fee: 0.0` and left the parcel `STORED` |
| 5 | ParcelService.java:76-79 | `FileWriter` never closed | both | java:S2095 | yes, `parcels.csv` was empty after the run |
| 6 | ParcelService.java:80-81 | Empty `catch (IOException e)` block | both | java:S108 | yes, the block has no statement, so a failed save is silent |
| 7 | ParcelService.java:30, 67, 69 and Main.java:9, 15, 17, 22 | `System.out` used directly in 7 places | SonarQube | java:S106 | yes, but acceptable for the output of a command-line program |
| 8 | Main.java:8 | `Scanner` on `System.in` never closed | AI | resource leak | yes, no `close()` and no try-with-resources |
| 9 | Main.java:10-26 | One if/else chain dispatches five commands in `main` | AI | long or complex method | yes, but minor, because the chain has only five short branches |
| 10 | ParcelService.java:24-37 | `storeParcel` finds a locker, sets status, prints an SMS and saves the file | AI | procedural cohesion | yes, the method does four different jobs |
| 11 | ParcelService.java:39-50 | `pickUp` frees the locker, sets status, saves and calculates the fee | AI | procedural cohesion | yes, same reason as row 10 |
| 12 | ParcelService.java:64-72 | `printParcels(boolean forStaff)` prints one of two outputs by a flag | AI | logical cohesion | yes, the two branches print different things |
| 13 | Main.java:6-27 | `main` parses, calls the service and prints | AI | procedural cohesion | no, too strong: this is what the entry point of a console program does |
| 14 | ParcelService.java:9-83 | The class handles lockers, fees, printing, SMS text and CSV saving | AI | weak class cohesion | yes, I can name five separate jobs in the class |
| 15 | ParcelService.java:52-62 | `fee` uses no attribute of `ParcelService` | AI | weak class cohesion | yes, it uses only its two parameters |
| 16 | ParcelService.java:20-22 and Main.java:22 | `getLockers` returns the internal list and `Main` reaches through it | AI | strong coupling, internal collection | yes, `Main` could add or remove lockers through the returned list |
| 17 | Main.java:13-22 | `Main` depends on `ParcelService`, `Parcel` and `Locker` | AI | strong coupling | no, too vague: three classes are not many, and the real problem is only the reach-through of row 16 |
| 18 | ParcelService.java:52 | `fee(Parcel, int)` gets the whole `Parcel` but reads only the size | AI | stamp coupling | yes, but minor |
| 19 | Locker.java:5, Parcel.java:6, ParcelService.java:15-17, 26, 56-61 | Int size codes 1, 2, 3 must be read in the same way in four places | AI | connascence of convention | yes, the comparison `>=` at line 26 relies on "bigger number means bigger size" |
| 20 | Parcel.java:13, ParcelService.java:28, 44, 68 | Status strings "NEW", "STORED", "PICKED_UP" must match across the code | AI | connascence of convention | yes, a typo in one string would silently break `printParcels` |
| 21 | Main.java:13 | `new Parcel(words[1], words[2], Integer.parseInt(words[3]))` depends on argument order | AI | connascence of position | yes, code and student ID are both Strings and can be swapped without a compile error |

2.1 Name two problems that only the AI review found.
The int size codes that four places must read in the same way (row 19, connascence of convention) and the fact that `ParcelService` does five different jobs (row 14, weak class cohesion). SonarQube has no rule for either of them.

2.2 Name one problem that only SonarQube found, or write none.
The direct use of `System.out` in seven places (row 7, java:S106). The AI review did not report it.

2.3 Name one AI finding that was wrong or too vague, and explain why.
Row 17 ("`Main` depends on three classes") is too vague, because three dependencies are not strong coupling. Only one of them is a real problem, the reach through `service.getLockers()` that row 16 already covers. Row 13 ("`main` has procedural cohesion") is also too strong, because a menu loop that reads and dispatches is the normal job of `main`. I checked each finding by opening the cited line and by running the program, which also confirmed the bugs of rows 4 and 5.

## Task 3. Fix and check

Table 3. What you did with each SonarQube issue in Table 1

| No. in Table 1 | Fixed or kept | Commit, or the reason for keeping it |
| --- | --- | --- |
| 1 | Fixed | 4f0fdc0 (field made private, getter and setter added) |
| 2 | Fixed | 23df565 |
| 3 | Fixed | 69c109d |
| 4 | Fixed | 239ff2f |
| 5 | Fixed | c5e9400 |
| 6 | Fixed | c5e9400 |
| 7 | Kept | The SMS text is the console output of a command-line program, and the project has no logging library. |
| 8 | Kept | The staff list is the console output of a command-line program. |
| 9 | Kept | The waiting list is the console output of a command-line program. |
| 10 | Kept | The command list is the console output of a command-line program. |
| 11 | Kept | The locker number is the console output of a command-line program. |
| 12 | Kept | The fee is the console output of a command-line program. |
| 13 | Kept | The free-locker count is the console output of a command-line program. |

Number of SonarQube issues that remain after your fixes: 7

The two design problems that you fixed

| Design problem (course term) | What you changed | Commit |
| --- | --- | --- |
| Connascence of convention: the int size codes 1, 2, 3 were read in the same way in four places | Added the enum `Size`, with the daily fee and `canHold`, and used it in `Locker`, `Parcel`, `ParcelService.fee` and `Main`; removed the if/else chain of `fee` | 95e9585 |
| Weak class cohesion: `ParcelService` also saved the CSV file | Moved the saving into the new class `ParcelRepository`, which `Main` passes to `ParcelService` | b80375d |

Other small commits that came from Table 2: 21c3be4 returns an unmodifiable view of the lockers (row 16), and 34d942a closes the `Scanner` in `Main` (row 8).

Output of the program after your fixes, for the commands `store P1 6501 2`, `pickup P1 5` and `staff`, and the content of parcels.csv:

```text
Commands: store <code> <studentId> <size 1-3>, pickup <code> <days>, list, staff, quit
SMS to 6501: parcel P1 is in locker 2
Locker 2
Fee: 30.0
P1 6501 PICKED_UP
Free lockers: 3

parcels.csv:
P1,PICKED_UP
```

## Task 4. AI-generated poor and good designs

| Item | bikeshare-poor | bikeshare-good |
| --- | --- | --- |
| Date | 25 October 2026 | 25 October 2026 |
| AI tool and exact model | Claude (claude.ai), Claude Sonnet 5.5 | Claude (claude.ai), Claude Sonnet 5.5 |
| Prompt (B or C as given, or your changed prompt in full) | Prompt B as given, with the BikeShare specification | Prompt C as given, with the BikeShare specification |
| Builds with mvn package (yes or no) | yes | yes |
| Number of classes, interfaces, enums and records | 1 (the class Main) | 10 (6 classes, 1 interface, 2 enums, 1 record) |
| SonarQube issues, total | 12 | 4 |
| SonarQube issues, by rule | java:S106 (7), java:S2386 (5) | java:S106 (4) |
| AI review findings in Check 1 | 6 | 2 |
| AI review findings in Check 2 | 9 | 6 |
| Review output file in the reviews folder | reviews/bikeshare-poor-review.md | reviews/bikeshare-good-review.md |

4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did.
No. SonarQube reported only rule-based issues, namely java:S106 for System.out and java:S2386 for mutable public static arrays. None of its rules or messages addresses cohesion, coupling, or connascence. Because the code was kept deliberately tidy (resources closed, exceptions caught, no dead code), SonarQube found nothing related to the poor software design.

4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for bikeshare-good.
Yes. For bikeshare-poor it reported 15 findings, including one Must fix, whereas for bikeshare-good it reported 8 findings that were all minor Notes. A finding exclusive to bikeshare-poor is `Main.finish` (Main.java:51-69), which exhibits logical cohesion because the boolean `dryRun` alters the method's behavior. No such issue exists in bikeshare-good.

4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?
bikeshare-good. Adding a new member type only requires adding a single enum constant `VISITOR(4)` to `MemberType`, while `FeeCalculator` and `Menu` remain untouched. In bikeshare-poor, adding a visitor requires introducing a new integer code, updating the ternary expression `STUDENT ? 2 : 3` in `finish` (which defaults all other codes to staff fees), and editing user prompt strings.

## Task 5. Reflection

5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.
SonarQube detects syntax-level issues, static analysis rule violations, and local code smells within individual files. For example, in the starter code, it accurately spotted the unused import and field, String equality comparison using `==`, unclosed `FileWriter`, empty catch block, public field, and `System.out` calls. However, it does not analyze architectural design quality; it failed to identify that `ParcelService` was overburdened with five responsibilities, missed the connascence of convention in parcel size codes, and could not differentiate the modular design of bikeshare-good from bikeshare-poor.

5.2 When should you not trust the AI review? Give an example from this lab.
We should not blindly trust an AI review when its findings are overly broad, generic, or lack execution context. In this lab, the AI claimed that `Main` had strong coupling simply because it referenced three classes, and criticized `main` for procedural cohesion when it was merely fulfilling standard CLI dispatching duties. Furthermore, an AI cannot execute code to verify operational behavior, so manual verification against line numbers and program testing is indispensable.

5.3 What will you do differently in Project Phase 2 after this lab?
For Project Phase 2, I will combine automated static analysis with AI reviews as complementary tools while rigorously verifying findings through hands-on testing. When structuring the codebase, I will enforce high cohesion and loose coupling early on by encapsulating responsibilities behind dedicated classes (like repository layers for file persistence), using typed enums rather than magic numeric constants, and committing incremental refactorings with clear commit messages.

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
| --- | --- | --- | --- |
| Claude (claude.ai), Claude Sonnet 5.5, 25 October 2026 | Task 2: ran Prompt A on the starter code | reviews/parcelpoint-review.md | Opened every cited line, and ran the starter program to confirm the `==` bug and the empty parcels.csv |
| Claude (claude.ai), Claude Sonnet 5.5, 25 October 2026 | Task 3: wrote the fixes and the two design changes | parcelpoint/ (commits 239ff2f to 34d942a) | Ran javac/java and the four input lines of step C2, and compared the output with the starter output |
| Claude (claude.ai), Claude Sonnet 5.5, 25 October 2026 | Task 4: wrote bikeshare-poor and bikeshare-good from Prompts B and C, in one conversation instead of two new chats | bikeshare-poor/, bikeshare-good/ | Ran javac/java and the same rent and return commands in both, and compared the receipts and rides.csv |
| Claude (claude.ai), Claude Sonnet 5.5, 25 October 2026 | Task 4: ran Prompt A on both BikeShare versions | reviews/bikeshare-poor-review.md, reviews/bikeshare-good-review.md | Checked the line numbers against the code and tried invalid commands |
| Claude (claude.ai), Claude Sonnet 5.5, 25 October 2026 | Drafted the text of this report | LAB_REPORT.md | Read every row, compared Table 1 and the Task 4 numbers with my own Problems panel, and rewrote 5.3 |
