# 2020_302_seesharp
An arcade shooting game built from scratch, written in Java as a part of the OOP principles course with a team of 4 people. Used an agile approach while paying attention to design patterns and scalability.

This is the final demo KUvid created by SeeSharp.
When user runs the code: 
- Build Mode Screen appears. User can change values in the Build Mode Screen and initialize the game with requested amounts of powerups and atoms.
- When user clicks the button to run the game, Run Mode Screen appears.
- Current status of atoms can be traced by looking Statistics Panel on the right side of the frame. Values in Statistic Panel changes according to Blender and shooting operations.
- In order to use Blender, press on "B". In the Blender Mode, game pauses and user can press on 2 keys one after the other. Such keys must correspond to atom types. As stated in the manual, "1" key corresponds to alpha atom, "2" key corresponds to beta atom, "3" key corresponds to gamma atom, "4" key corresponds to sigma atom.
-According to preferences of user, Blender changes atom inventory and updates statistics panel.
-Shooter can rotate, change location and shoot atom successfully.
-Shooting action also updates statistic panel (decrements shooted atom).
-In order to pause the game, press on "P" and press "R" to resume.
-Pausing game and switching to Blender Mode stops timer.
- We have 4 (one for each type) molecules falling from the top of the screen.


Implementation:
- There are View classes for each domain object. These View classes are to draw the domain objects on the console according to their location.
- The Game class has an ArrayList of all domain objects to be drawn on the console. The RunScreen class iterates over this array and calls the corresponding draw() methods.


