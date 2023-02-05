# intelli-flix

This repo was created from the IntelliJ Platform Plugin Template. It is a prototype for how chatGPT might be integrated into IntelliJ. It is not intended to be used in production. This is the first time I have build a plugin for IntelliJ, I am sure there are many things that could be improved, and I am always open to feedback.

## Running the Plugin

To run the plugin, you can use the `runIde` Gradle task. This will launch a new instance of IntelliJ with the plugin installed.  Or, ff you have imported the project into IntelliJ, you can also run the plugin using the "Run Plugin" run configuration.

### Setting the API Key

You will need to have an account with OpenAI, as this plugin will send requests to their API. You will also need to [create an API key using this link](https://platform.openai.com/account/api-keys). Once you have an API key, you can configure it in the IDE settings. 

Note: You can leave the API URL blank, as it will default to using OpenAI's public API.

[!image](./images/settings.png)

### Using the Tools Window

This plugin adds a new tools window that allows a user to submit prompts to ChatGPT and is similar in nature to what
a user might do when going directly to the chatGPT website. This window can be opened from the "Tools" menu.

[!image](./images/tools-window-example.png)

Note: Each call to the public API will consume tokens from your account and the max tokens is intended to limit how much this plugin will consume your quota. If you are receiving partial responses, you can increase the max tokens to allow for longer responses.

### Using the Code Suggest Context Menu

There is also a context menu available in the editor that allows a user to select code and send it to chatGPT for suggestions.

1. Select some code in the editor and right click, and select "OpenAI Code Suggestion".

[!image](./images/code-suggest-1.png)

2. A popup dialog will appear that allows the user to add optional instructions and will send both the selected code and the instructions to OpenAI's code editor API.

[!image](./images/code-suggest-2.png)

3. The response from OpenAI will replace the currently selected text in the editor.

[!image](./images/code-suggest-3.png)

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
- [x] Figure out how to call the chatGPT API from a background thread, so we don't freeze the UI.
- [ ] Provide usage feedback from chatGPT response into IDE. Maybe in status bar at the bottom of the IDE?
- [ ] Fix the code suggestion dialog so that it does not close when the user uses arrow keys inside the prompt text field.
- [ ] Document the plugin

## Future Considerations

- [ ] Add button to accept the chatGPT response and close the dialog rather than blinding replacing the existing selection.
- [ ] Add a prompt/instruction history and allow the user to recall previous prompts.
- [ ] Experiment with different prompts that could augment the user's prompts/instructions. For example, we could add "Always format your responses as markdown" and then render the results in the tools window.
- [ ] Add better testing. I am sure there are some good examples of testing the UI components in the IntelliJ Platform SDK, I just ran out of time to investigate.
- [ ] Move ChatGPT client to a separate library so that it can be shared across this plugin and VS Code (I think we could have a language server running a JVM).
- [ ] Figure out why the CI build is failing, not really important in the context of this sample, but I would obviously need to figure this out if I wanted to publish this plugin.
- [ ] Publish the plugin. 

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
