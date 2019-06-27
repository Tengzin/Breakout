# Breakout

### Environment
Tested on Java 1.8, MacOS.

### How to Build and Run
Gradle and Java 1.8 are required.
```
gradle build
```

```
gradle run --args='(FPS) (ballspeed)'
```

### How To Play
Choose FPS value of 25-60, and ballspeed of 1-3. A speed of 1 is the slowest, and 3 is the highest.

The instructions for controls will be provided in a splash screen upon running.

### Features
- Different colored blocks yield different scores. Ones at higher levels give higher score.
- The top 3 levels increase the difficulty of the game by increasing the speed of the ball when the blocks are hit. 
Thus, hitting the top blocks is higher risk for greater reward.
