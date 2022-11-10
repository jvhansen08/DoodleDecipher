# General overview
This app will be a sort of Telestraions meets Jackbox Games: One player will create a game, and will give a code to the other people they want to join their game. This player then  starts the game. Each player will enter a prompt which, in an order chosen by the app, will be sent to the next player. They will then attempt to draw the prompt given them, and that drawing will be sent to the next player, who then has to guess what was drawn. This cycle continues for a pre-determined number of rounds, and the results will be available for all to enjoy.

This app will be useful in bringing people together and helping them create memories that will last a lifetime.

# Feature List
### Must-have Features
As a user, I should be able to:
- Create an account and log in
- Create a new game
- Join an existing game lobby
- Start a game (once players have joined the lobby, start a game between them)
- Receive a text prompt from the previous player
- Draw an image
- Send an image to the next player
- Receive a drawing from the previous player
- Guess what the received drawing is
- Send response (guess) to next player to draw
- View the results at the end of the game
- Enjoy this app from an intuitive & standardized UI

### Nice-to-have Features
As a user, I would like to be able to:
- Have a variety of options in colors, sizes, background colors, etc. while drawing
- Have a bucket/fill tool in addition to the pencil/drawing tool
- Store favorite drawings/sequences
- Opt out of ads with a _small_ subscription fee

# Technical Challenges

### Difficulties
- Implement a system of authentication to join a game
- Implement a system of communication to send images/text from one device to another
- To improve gameplay, we may need to set and enforce time limits on drawing and guessing

### Knowledge to Acquire
- How to utilize the Canvas and Painter classes in Android Studio in order to allow the user to draw on the screen
- How to save user drawings and store them in Firebase
- How to design a document/documents in Firebase in such a way that the correct prompts/images are sent to the proper players
- How to manage game state, lobby creation, and joining a game


# Requirements
| Requirement | Resolution |
|-------|------|
| Use Firebase | Accounts and game info will be stored in Firebase |
| Have Monetization Strategy | Either banner ads or a pop-up ad after completion of every game |
| Utilize Animation / Sensor | Intuitive animation when the user submits a drawing or text, leading to next screen |
| Target 60 Hours of work | It will be a miracle if this only takes us 60 hours, between implementing navigations, animations, drawing, communication, etc. |
| Be Useful | This is something that we would legitimately use. It will provide entertainment and bring people together |
| Have Consistent, Intentional Design | We will choose a color palette and intuitive UI (navigation, animation, placement, etc) for optimal user experience |

# Group Members

| Name | A-Number |
|------|----------|
| Jared Hansen | A02275891 |
| Logan Ballard    | A02253793 |
| NAME_HERE    | A_NUM_HERE |