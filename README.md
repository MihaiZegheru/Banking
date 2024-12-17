# Banking
Banking concept in Java that provides a broad overview of the system structure.
The project is built around the idea of mocking multiple commands that get
executed in the local banking system. Some notable particularities are the
generous number of design patterns used for development and the ForexGenie
class that can handle Forex. Even though the project could have taken a more
scalable and detached form, I considered that some things are not worth the
hussle as more can be added later and provided tests may raise difficulties
due to perspective differences.

The following is a minimalist, simplified Google like Green Design Document
style presentation.

## Overview
Banking is a project that proposes a strong OOP like take at a real life banking
system. We are going to assume all of the commands will be mocked through a
`Command` pattern. Users should be able to do various payments and money
transfers with their cards. They should also have access to a savings account.
Moreover, they should be able to delete their accounts and cards. Foreign
exchanges may also be needed. We would also like to be able to generate some
reports about each specific user or account.

## Design
The project supports `Users` with multiple `Accounts` and associated
`Cards`, each of them being able to implement different strategies for
handling savings or payments. These `Users` can then perform all sorts of
commands through the `BankingQuerent` which executes `BankingCommands` in the
banking system. Data may be queried from `BankingScotty`, a simple database
simulation, and then manipulated. Forex is provided by the `ForexGenie`.

The following are the project's packages.

### Banking
This package includes the `BankingManager` that handles everything that is
related to communication with the database. It also handles the current time,
which is provided through static discrete timestamps. 

All the other major banking packages are included here.

### Currency
This package inludes the `ForexGenie` which offers support for foreign
exchanges. Computing a foreign exchange makes use of Bellman-Ford algorithm.

### Data
This package is home to `BankingScotty`, this project's "database". It stores
runtime information about the `Users`. Each existing user is saved here and it
can be queried based on a feature, like `email`, account's `iban` or card's
`cardNumber`. In case the bank wants to make some processing of the data before
feeding it to the querier, `BankingScotty` should only be called through
classes that implement `BankingScottyFriend` interface, like `bankingManager`.

### Transaction
This package offers an implementation for a `TransactionTable` object. It
mainly represent a middle-person for facilitating payments by collecting the
desirated amounts from `PaymentCollectees` and sharing them to
`PaymentReceivers`. This way it supports split payments as well.

### User
This package contains the `User` class and multiple other realted packages. The
`User` is mainly a data object for storing references to its cards and accounts,
as well as possible aliases and other personal information.

### Account
This package countains two `Account` types. A classic one for doing day
to day payments, `ClassicAccount`, which implements a payment strategy,
implemented from `PaymentCollectee` and a receiving strategy, implemented from
`PaymentReceiver`. There is also an account for savings, `SavingsCollector` that
implements all of the above and an extra `SavingsCollector` strategy.

### Card
This package contains two `Card` types. A classic one, `ClassicCard` that
implements `PaymentCollectee` and a disposable one, `DisposableCard`, that
also implements `PaymentCollectee` but having a self-destructing strategy. After
destruction, a new `DisposableCard` is instantiated.

### Tracking
This package contains multiple classes related to gathering data and emiting
reports about users and accounts. `FlowTracker` generates different kind
of reports by manipulating `TrackingNodes` from its `history`. `TrackingNodes`
are added to the `FlowTracker` in specific tracing points from the
`BankingCommands` by building them using a `Builder` pattern.

### Mock
This package is outside of `banking` and it provides the `Mocker` which takes
all input and feeds it to the `BankingSystem` through `BankingCommands`.

### Command
This package offers a wide range of commands that manipulate the whole banking
system. They are created from a `Factory` pattern, `BankingCommandFactory`, and
then they are executed by `BankingQuerent`. The used `Command` pattern allows
for easy extendability of already current logic.

## Technical Details
The only thing worth explaining in this section belongs to the `ForexGenie`. It
handles currency exchanges which get computed everytime, with no caching. The
complexity of the algorithm used, Bellman-Ford, is O(n^2), which practically
performs well since the number of edges should generally be considered small as
well as the number of nodes. Since all the costs are always positive, Dijkstra
could have been a great optimization, but since n is small, the current approach
flies. This is done in good faith that the supported currencies have a low count
and that in practice they might change quite often. We assume there is no
negative cycle, so the length of the walk is not minded about.

## Open Questions
It might be a good idea to separate the output logic from the actual commands.
These commands can be used by the whole banking system and they may mean more
than just mocking.

## Summary
Banking project is a Java-based system designed to simulate a banking
environment, emphasizing OOP principles and design patterns. It allows users to
manage multiple accounts and cards, perform payments, transfers, and savings,
as well as handle foreign exchanges through the `ForexGenie`. The system is
organized into well-defined packages: `Data` for database management, `Currency`
for foreign exchange, `Transaction` for payment processing, and `Tracking` for
generating user and account reports. `BankingCommands` ensure a scalable and
modular approach.
