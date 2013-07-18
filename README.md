# Waywia
Current build status: [![Build status][2]][1]
  [1]: https://travis-ci.org/aritzhack/Waywia
  [2]: https://travis-ci.org/aritzhack/Waywia.png?branch=master (Build status)
  
### What is Waywia?
Waywia is a open source 2D game made by aritzhack and distributed under the terms of the GNU LGPLv3

### Using the source code

1. You'll need [slick2d-maven] (https://github.com/nguillaumin/slick2d-maven "Slick2D-Maven") so that the project can be built using maven (Follow the instruction in the readme in the repo's root)
2. Clone my [AritzhUtil] (https://github.com/aritzhack/AritzhUtil "AritzhUtil") project, and add it to the workspace (eclipse), or project (Intellij IDEA). You can also remove the dependency from the pom.xml file, if you add the source code for EventBus to Waywia's source,
3. In the root folder of the source (where `pom.xml` is located) run `mvn install`
4. Set up your environment so that java runs with `-Djava.library.path=target/natives` and the project language level is set to `7.0` (or `1.7`) at least (needed because I use the diamond operator, multi-catch and try-with-resources, which need that language level)

### Contributing

Even though this is a personal experiment-project, feel free to make a [pull request](https://github.com/aritzhack/Waywia/pulls "Waywia pull requests"). However, I'd prefer that you just make an [issue](https://github.com/aritzhack/Waywia/issues "Waywia issues") describing the problem, and let me solve it by myself.
