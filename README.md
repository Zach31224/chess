# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZMAcygQArthgBiNMCoBPGACUUZpKrBQ5SCDR7AHcACyQwMURUUgBaAD5yShooAC4YAG0ABQB5MgAVAF0YAHorAygAHTQAb3LKRwBbFAAaGFx1YOgONpRG4CQEAF9MYRSYBNZ2Lkp0tk5uGChPb19-QMx5mdhJsdF05a8fSgAKeqgm1vb9VS6oHpg+gYQASlHk0QnE2XklFXV0mYUGAAKoVM4VS5vH6KZRqVRfIw6dJkACiABlUXB8jAAFQwABibhyAFkYOcYAB1Fiotyo8mQ4DNGAAXhgAH5MMAEMwwZRHgAPVYaGCimTaP7wia7D4qdIoIU+JBoMx8qCYMVJEQqaWJLbcNKPKCWKAnABEasuMHkAGt0Ga3pr9fzYjLqLMYAAWAAMAGZCdAdEgOBx0BqxWMXZNougwOkfb7qjUzc11MAgWb0hbGczuctgBxXLb7SMUAgDDA1TA0BBmAAzayccOimGS9S6rVUfbVqwIBDNzufSatuEAmAgfORNUQhpMq4dW7dXr9QaOsUj-4IybGdIASQAcmi3DiD-kcgz+TOLnO2gu7g8nquB3sdcOJaPVOkJyg5CgFFYwFCK8oQHDcpW3ZEYAPI8T33M8YGAADQhgYCbwQpD8ggO00DXFt303DsXxQdJEMAzDsOfWVDFdPVpgNdIPCOSIoA8VRezAE5SNCcj0FwqYFijRJI0NGouJ4tARk1YSOxjDB0gAJm9b0kzErD0FLJt0A4UwLGsWw7GgdggRgdEIC8II7DCCIomQWNEWE9JsjyIpSgMdQAjQJNzkuW8bnvEZpMmZ1DWdGAEDM5VNjol0hKo9JwvM1Dml8zpujeIjETAscuNjQhf2nby0LvNLMCyrdEh3cgMSxHFivuQliTJClqVpelCuZNlOW5ZhdzQKhuWDcdllDDAkG5EV13w8C3S7OUYDrcaxCkqiO2C+VjWgc1ev65AODCiK0AdAdgsI5IPR9ABGStHCQ6AkAALxQbTlvdHZo1suSvW9C6kxTeF0xQTMYDNbaBr278RoIcazVLctDAANTBoanty8bQKm9saMHObfCsJaIxWt9fg-L9Jz-JCkpQPiysRSroNpWD4K4lD2quVTsOpjGtxm7t2bDF7ZuooLosNUzzNY9jKbaPmcOOkXTte9I6hzNmMLUiTKNemSPrjGBFO9GBqhgZXZ2S9CyPVjTtK0nTLBsewLBQdATLM6xmEs8JIkwWTmBmj0Mmkar8lRYoSjc1QPOqGX3i14WBJC6L9rMN2ovj+y4qTt3OLVjmY8FzKufSUNuEif9AOzi3c5piD0kDzFg4a0lzeQlq6Wb8TWQ5UquYVwX0jQXt+wyrG1pgNiP1T7Ze49HIAGk88E+AdYUpTMBt8w7f04aTNWGAAHE5wRD3rO9nX08VzI99RFySjMOco5zsNAto+O5kT5AfAP1NJ4Nc++7C1YX81AV24urdKhNviFxgECMAZcgIy2hD3GuVVMTYgvNeZkRIm7M1bvSGWndOTVx5nNVmechyQOJpueKgDD6U0QZQ8CFVIJolQTiO+zRdzSDaFZKcKs2jVB0AgUANpLRoXYSgfcc5G5knEYbNAuCYA8JQKIjqHIYA5DcDAQRwiVGGE6t3BhmNiHEWgXOdEqwyGvjjtsQEZiLEnSxg5UxzRzE+AXm9RIPsV7KVqGacRqggYZAAHQhMKFbW2el7DYCsFAbAiw4Bk33lI4+XsfZ-39rkAoodxEP0rugJM4jJHNACmdHYL8bH8RmMjX8QCf4xWxiY78NS5wgPEm0Qpc5wHamohQ2EVDoHAjga0sBBi+mMKRCiaqaDWbSOblSGkbd8H6KIbFbp6RSHD16W2T81TIhAJOOIzh3DPbKL4c4iRnTRnbNppBemx4oJwXPLIg5c4jmKJOboto2iQAiLOR05o9CxlGNWf-Q50hLFC3KfRc5nC6keM7B6GoYKSmx3ejEXW+s5FItedIcJG9Il2GcGWcKwQYAACkIDKiScyOw3ybSn3Rekw02QQQ3xyTdPJnlag+zgBAcKUB2k4pReMax0LQoACtKVoFqQ4kF3ZJXKn2TLNoPK+XQEFRw6QXT85EyBTsmBQyEFXI-DcyZrD0FWiwWSHBCy8GPyCMspBxj1kqwhQXQx+rgT7LBYC65yCWE1TxLM2RCiwUEONQRRxGdxFch5OQbAtZrTAAogLIcoqPSUBNOaXl-KEIIHzIWJN2EjpOnllGi+CYAxQCDCGfmBNUWeOXl9RMvjUyqABkDM0ZAE3MDzD+QtdZBjcA4DDNecMYAKH6oMXQ3BNY6q2STck2AtB7JaWClVOsc3qotZczU1cmHpBBFkaQCgG6yLRDiEAarYBshmaGnF4a91OrlXNAefY51pqhR6cem45Zp3LSkdIc852Ly8XrVeNt166XtnYCwwBXCIB-LAYA2A4l5QIIEEIJyGV2T9syuu18Q6lGMO4xEo9QogG4HgOFf9uyUaQycbV5DxQeq-FR00vqTXIIIw3K16CI3jMqjx+kfGuICcxgemQQcRONXOeJ7mL6TEcECGIYe6bDT0bwE9GjAGZ7z0Cmi2M3i15NiAA

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
