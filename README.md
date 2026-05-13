# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

Alt Sequence Diagram: https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAEYAdABZM9qBACu2AMRuAMwAHABMAJwgMP7I9gAWYDoIPoYASij2SKoWckgQaJiIqKQAtAB85JQ0UABcMADaAAoA8mQAKgC6MAD0PgZQADpoAN4ARP2UaMAAtihjtWMwYwA0y7jqAO7QHAtLq8soM8BICHvLAL6YwjUwFazsXJT145NQ03PnB2MbqttQu0WyzWYyOJzOQLGVzYnG4sHuN1E9SgmWyYEoAAoMlkcpQMgBHVI5ACU12qojulVk8iUKnU9XsKDAAFUBhi3h8UKTqYplGpVJSjDpagAxJCcGCsyg8mA6SwwDmzMQ6FHAADWkoGME2SDA8QVA05MGACFVHHlKAAHmiNDzafy7gjySp6lKoDyySIVI7KjdnjAFKaUMBze11egAKKWlTYAgFT23Ur3YrmepuAAMQWG4zm6mAjIWywjUG8dQNUyVxqDIfl8jV6ChZk4mDtfPUPqqXpQ9TQPgQCATFPurbpqlqIFV6Ld7IGPO52nt7fuxlqCg4HE10u0g+9w4XbbHE+D6IUPj1GOAZ-ic5b+9HgpXa43p71HsRu-uMKeZexaLxaj7LAvzhDs-TLV5DSVJZ6n2EFLz1doIHrNBoOWS4E0oDsUwweownTdNswmSDPhgGDgWWeD4kQ5DUP2K50A4UwvF8AJoHYRkYhFOAI2kOAFBgAAZCAskKbDmCdah-WaNoul6Ax1HyNBs0VOY1l+f4OCuMCKmA-0RhUlA1P0P4dmhR4QIkqgkRgBBhPFDEhJEgkiTAUl30MPcaQPBkmWnAz5y8+9l2FMUJTdGU5XLd4lUwFVgw1ABJNAQGgFFwHgFEOHMJATVtO8HSTX1nW7GBe37HcPMs-0kpSksUHAOBMuy3KoxjONCjArDkFTGAMycQjc1UfN5hg4tS3qHxpivaAkAALxQXZ6ObEcCss6zwu3dzBRW+kjzkFAX3iC8rxvHaBWC1d1wDE7NuKjtdLLRzxQyVRAMwB7QOqPSiIrEiyO+SjqIbUi0MbTrCvgbqcJgPCCNGH7or+0G4KvIGUJBuim0YzxvD8fwvBQdAYjiRICaJxzfCwMTBTA+oGmkCMBIjdoI26Hp5NURThkBpD0AwqAdPM-1KJm2a8gKeoAB4eeQ8p3qFgXync+pbPsSmHN8NWz2ctRXIqiozp8sBDuOhDebQALeVHCoVxgUKNxFqA5vFtBZXlGW+bi9UYCSqgTSQZ9UfN29AtWoqux7PsBy2iHaZ95LUvq42g+Q1qUFjRT+a6kowDTdN+vhwbhsLMYxugCbpj1UWFsbBj9fKQ2jBQbgTyvU2qPNtzisKh76gp7WAIQICFcKuORi0r6BYqMTcPw7Mlux5i8cymJsHFDUBLRGAAHElQ0amqrLBpt+Ztn7CVbmU757Tyl741pqdsX2qlj20Dlh6KmVmy0QctEdZJeujdGTJ3PK-S2i5VA22FHbcUDsH7O3am7e+ZtkKxVVIlNAftkCBxQXzM6n0I6lSjhVAhNR6g1UTuAQ6aM04Z3jODZMUNc69XzgNfkxdRolnLgqSu8Rq6LSxoA-K9Jv45F3rmDEXcuw9wVn3P+g9h6wkoKPSeLxljn1zIWBo4wNEoAStIQsTgwhBDcCCTY8RdQoDdJyPY3xkigDVNYqCixvi6IAHJKhcRcGAnQJ6SSnuUGeMM56jHUXvLROilT6MMcY0xyxzGWKcZ8FxIJ7EgEccREaYxXFKg8XMLxPiF5MVxgEDgAB2CI6YUDphiBGIIcBuIADYMrHkMOIwwRQmE01UY0VoHQz4XymrgpSox3FKj8TUQWSiyyOwQYpF+V837ZjCXMPJWSlhmWmZ-Yq9Q9rogxI1Vp-89buQNsIscMBgEmzASHK2-IoH1Htsgvhj8XZINfmg+K8csEB2usM25EDSHWTKtHO6scekULqlQxZtD2pZwhkEvqbC8wFk4eNHhcgXlzRrkU05Ddzm7MnCgdpGIxlzCkVZFQMjpn1EOftF6b0P7bP8WosYujon1CMSYmAEzlHTyYbPOGIwVl6IMZy2JPLBE4xYv4SwzdbKbGJkgBIYA5X9ggIqgAUhAcUO9Kz+DSWqTpOdukst6cyWSPRdGX2GdmbACBgByqgHACAtkoBrHZdIXlis76zKfvMmA0tFnlGWT8B1TqXVur2AAdRYAlFmPQABCAkFBwAANI5LmBy5wErNnfmZZSkqAArHVaAMTaueigQkusKWiDOaHERVy243LOg8mBEo-VvMih8r2GCfk4I7qg-B4LCEgpISOsh8dappRAYOyM0Z05woYZURFrDC7sNRUWLhZZJqYv4bXZseKgFMhJZ60kva9VzBgGGId5ygUuiIeVGOh96jtJvfOtqmdl2QxznnLM66UUjS3eik0ZpazhhQri7u+KG0XJLeKU9UTpC1qpVM789QK1oAZUPeWWyC16W9YVIJsN55SqXgELwjqlUqso-KRAwZYDAGwPawgbzjXmFNZO+mjNmas16MYfmaG4Q9ggM8tYaBRM6AgOaNYuN0RNxbmID+SsdkgG4HgSRQjYO7PU1ATTrbyi2x5HoAwMA1MMYWvrIT-p6N4Gw4o-N+HwKEf5b+4JQqilAA

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

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
