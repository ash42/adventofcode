# adventofcode
Several solutions to the annual Advent of Code (https://www.adventofcode.com).

In the 2023 version a little tool (DailySetup.java) was added which downloads the input for the current day. This tool does follow the [automation guidelines]([https://www.reddit.com/r/adventofcode/wiki/faqs/automation) on the [/r/adventofcode]([https://www.reddit.com/r/adventofcode) community wiki. Specifically:

- Outbound calls are throttled to every 5 minutes in downloadInput()
- Once inputs are downloaded, they are cached locally (checked in inputExists())
- If you suspect your input is corrupted, delete the existing file in the resources directory to download a fresh copy
- The User-Agent header in getInput() is set to me since I maintain this tool. The email address is read from the first line in a file config.txt in the resources directory. This file should also contain your session id on the second line.


