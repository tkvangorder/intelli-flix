# intelli-flix

![Build](https://github.com/tkvangorder/intelli-flix/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)

## Template ToDo list
- [x] Create a new [IntelliJ Platform Plugin Template][template] project.
- [x] Get familiar with the [template documentation][template].
- [x] Verify the [pluginGroup](./gradle.properties), [plugin ID](./src/main/resources/META-INF/plugin.xml) and [sources package](./src/main/java).
- [x] Review the [Legal Agreements](https://plugins.jetbrains.com/docs/marketplace/legal-agreements.html?from=IJPluginTemplate).
- [ ] [Publish a plugin manually](https://plugins.jetbrains.com/docs/intellij/publishing-plugin.html?from=IJPluginTemplate) for the first time.
- [ ] Set the Plugin ID in the above README badges.
- [ ] Set the [Plugin Signing](https://plugins.jetbrains.com/docs/intellij/plugin-signing.html?from=IJPluginTemplate) related [secrets](https://github.com/JetBrains/intellij-platform-plugin-template#environment-variables).
- [ ] Set the [Deployment Token](https://plugins.jetbrains.com/docs/marketplace/plugin-upload.html?from=IJPluginTemplate).
- [ ] Click the <kbd>Watch</kbd> button on the top of the [IntelliJ Platform Plugin Template][template] to be notified about releases containing new features and fixes.

<!-- Plugin description -->
This is a prototype for how chatGPT might be integrated into IntelliJ. It is not intended to be used in production.

TODO:

- [x] Build a quick API layer to chatGPT. Use a simple HTTP client with Jackson to send requests to chatGPT and parse the responses.
- [x] Implement an extensible environment for the chatGPT client, with the most obvious setting being the API key. Initially, read the key from a file in the user's home directory.
- [x] Explore the IntelliJ Platform SDK and experiment with different user interface ideas.
- [x] How to auto-wrap and set minimum dimensions in the editor text field?
- [x] Implement a "tools" window to allow users to chat with chatGPT.
- [ ] Implement a popup dialog that is enabled when there is a selection in the editor. This dialog will allow the user to add optional instructions and will send both the selected code and the instructions to chatGPT's code editor API, replace the contents of the editor with the response, and then close the dialog.
- [ ] Add button to accept the chatGPT response and close the dialog.  
- [ ] Add a settings page to allow users to configure the API key.
- [ ] Figure out how to call the chatGPT API from a background thread so we don't freeze the UI
- [ ] Document the plugin
- [ ] Experiment with different prompts that could augment the user's prompts/instructions. For example, we could add "Always format your responses as markdown" and then render the results in the tools window.

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
