![Karumi logo][karumilogo] KataLogInLogOut [![Build Status](https://travis-ci.org/Karumi/KataLogInLogOut.svg?branch=master)](https://travis-ci.org/Karumi/KataLogInLogOut)
============================

- We are here to practice refactoring and unit testing. 
- We are going to use [JUnit][junit] to perform assertions.
- We are going to practice pair programming.

---

## Getting started

This repository contains a simple Android application ready to refactor. This application contains a simple log in and log out screen.

The application implemented contains **just one Activity and there is no persistent information**. When the user opens the app, a log in form is shown asking for an email and a password. If the user presses the log in button, the application should hide the form and show the log out button **just if the email is ``attendant@karumi.com`` and the pass is ``attendantpass``**. If the user presses the log out button, the log in form should be shown **if the second of the day is even**.

If the second of the day is odd, or the user credentials are not the one expected an error should be shown using a toast. The email and pass fields in the form should not be empty. **A message should be shown to the user if there is something wrong.** 

## Tasks

Your task as an Android Developer is to **refactor and test** writing the best possible code and unit tests.

**This repository is ready to build the application, pass the checkstyle and your tests in Travis-CI environments.**

Our recommendation for this exercise is:

* Before starting

1. Fork this repository.
2. Checkout `refactor` branch.

## Considerations

* If you get stuck, `master` branch contains all the tests already solved.

* Try to test the app using just unit tests. Refactoring and learn how to write unit tests is the goal.

## Extra Tasks

If you've finished the exercise you can continue with some extra tasks: 

* Add persistence to the user session.

---

## Documentation

There are some links which can be useful to finish these tasks:

* [JUnit documentation][junit]
* [Forgetting Android by Jorge Barroso][forgetting-android]
* [The Clean Architecture by Robert C. Martin][clean-architecture]
* [Mocks aren't stubs][mocks-are-not-stubs]
* [Testing taxonomy talk by Sergio Arroyo][testing-taxonomy]

# License

Copyright 2017 Karumi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

[karumilogo]: https://cloud.githubusercontent.com/assets/858090/11626547/e5a1dc66-9ce3-11e5-908d-537e07e82090.png
[junit]: https://github.com/junit-team/junit
[forgetting-android]: https://www.slideshare.net/flipper83/forgetting-android
[clean-architecture]: https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html
[mocks-are-not-stubs]: https://www.google.es/search?q=mocks+arent+stubs&oq=mocks+arent+stubs&aqs=chrome..69i57j0.6326j0j4&sourceid=chrome&ie=UTF-8
[testing-taxonomy]: https://www.youtube.com/watch?v=L2clQEs_W7U
