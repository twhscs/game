**UPDATE:** Important formatting options are now stored in the repository. You do not have to configure any settings aside from what follows below. Please do not modify anything in the `.settings` folder

**NOTE:** Mac OS X is currently unsupported due to limitations with the JSFML library.

IMPORTANT SETUP
===============

1. Download [Eclipse Mars](https://www.eclipse.org/downloads/packages/release/Mars/M1)
2. Window > Show View > Other > Git > Git Repositories
3. Click the "Clone a Git Repository and add the clone to this view" button (blue arrow)
4. Help > Eclipse Marketplace > Find: 'checkstyle' > Install "Checkstyle Plug-in 5.7.0 (version might be slightly different)
5. Window > Preferences > Checkstyle > New > Project Relative Configuration
6. Name: Google Java Style
7. Location > Browse > `google_java_style.xml` > Ok
8. Check "Protect Checkstyle configuration file" > Ok
9. Google Java Style > Set as Default
10. Help > Check for Updates > Install any available updates
11. Restart Eclipse
12. File > Import > General > Existing Projects into Workspace > Browse > Find where the git repository was cloned to earlier and open it
13. Open src > game > "Game.java" and press Ctrl + F11
14. Make sure everything is working properly

PRO-TIPS
========
+ Adhere to the checkstyle warnings
+ Comment every line
+ Add Javadoc documentation wherever possible
+ Always branch
+ Never commit to the master branch
+ Commit often
+ Make commit messages meaningful
+ Always favor quality over quantity
+ Re-factor code whenever possible
+ Use the final modified whenever possible
+ Actively use the issue tracker and add to it
+ Never work on something not in the issue tracker
+ Favor essential features to stylistic ones
+ Review pull requests and leave your feedback as a comment
+ Consult the [JSFML documentation](http://jsfml.org/javadoc/)
+ Give back to JSFML, if you find a bug in the library add it to the [JSFML GitHub page](https://github.com/pdinklag/JSFML)
