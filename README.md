# intelli-flix

This is a plugin for IntelliJ that integrates with chatGPT and it is not intended to be used in production.

This plugin adds a new tools window that allows a user to submit prompts to ChatGPT and is similar in nature to what
a user might do when going directly to the chatGPT website. This window can be opened from the "Tools" menu.

There is also a context menu option that allows a user to select code within the editor. The context action will open a
popup dialog that allows the user to add optional instructions and will send both the selected code and the instructions
to chatGPT's code editor API, replace the contents of the editor with the response, and then close the dialog.

## Build Status
![Build](https://github.com/tkvangorder/intelli-flix/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)

## TODO
- [x] Create a new [IntelliJ Platform Plugin Template][template] project.
- [x] Get familiar with the [template documentation][template].
- [x] Converted From Kotlin to Java (Only because I am faster using Java)
- [x] Added lombok, jackson, and testing dependencies
- [x] Added a simple HTTP client to send requests to chatGPT and parse the responses.
- [x] Implement an extensible environment for the chatGPT client, with the most obvious setting being the API key. Initially, read the key from a file in the user's home directory.
- [x] Explore the IntelliJ Platform SDK and experiment with different user interface ideas.
- [x] Figure out how to auto-wrap and set minimum dimensions in the editor text field.
- [x] Implement a "tools" window to allow users to chat with chatGPT.
- [x] Add token limit to "tools" window.
- [x] Implement a popup dialog that is enabled when there is a selection in the editor. This dialog will allow the user to add optional instructions and will send both the selected code and the instructions to chatGPT's code editor API, replace the contents of the editor with the response, and then close the dialog.
- [x] Add a settings page to allow users to configure the API key.
- [ ] Provide usage feedback from chatGPT response into IDE. Maybe in status bar at the bottom of the IDE?
- [ ] Figure out how to call the chatGPT API from a background thread so we don't freeze the UI
- [ ] Fix the code suggestion dialog so that it does not close when the user uses arrow keys inside the prompt text field.
- [ ] Document the plugin

## Future Considerations

- [ ] Add button to accept the chatGPT response and close the dialog rather than blinding replacing the existing selection.
- [ ] Add a prompt/instruction history and allow the user to recall previous prompts.
- [ ] Experiment with different prompts that could augment the user's prompts/instructions. For example, we could add "Always format your responses as markdown" and then render the results in the tools window.
- [ ] Add better testing. I am sure there are some good examples of testing the UI components in the IntelliJ Platform SDK, I just ran out of time to investigate.

<!-- Plugin description -->
This is a prototype for how chatGPT might be integrated into IntelliJ. It is not intended to be used in production.

<!-- Plugin description end -->

## Installation

- Using IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "intelli-flix"</kbd> >
  <kbd>Install Plugin</kbd>
  
- Manually:

  Download the [latest release](https://github.com/tkvangorder/intelli-flix/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
