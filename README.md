# Java Vending Machine

#### By Johnnie Meredith

---

![](https://img.shields.io/github/stars/JohnnieMeredith/vendingmachine.svg) ![](https://img.shields.io/github/forks/JohnnieMeredith/vendingmachine.svg) ![](https://img.shields.io/github/tag/JohnnieMeredith/vendingmachine.svg) ![](https://img.shields.io/github/issues/JohnnieMeredith/vendingmachine.svg)

**Table of Contents**

- [Instructions](https://github.com/JohnnieMeredith/vendingmachine/blob/master/README.md#instructions)
- [Design](https://github.com/JohnnieMeredith/vendingmachine/blob/master/README.md#design)
- [Implementation](https://github.com/JohnnieMeredith/vendingmachine/blob/master/README.md#implementation)

## Instructions

### Installation and Running

#### Method 1 - Easiest

- Install Java JDK 12 or later. Follow the instructions at the [Oracle JDK Installation Guide](https://docs.oracle.com/en/java/javase/17/install/overview-jdk-installation.html)

- Download the projcect. Extract it from the archive.

Navigate to the directory containing

> vendingmachine-1.0-SNAPSHOT-jar-with-dependencies.jar

- From that directory with the command line run:
  > java -jar vendingmachine-1.0-SNAPSHOT-jar-with-dependencies.jar

#### Method 2

- Download the project. Extract it from the archive.

- Navigate using the command line to the project folder containing the pom.xml file.

- Use maven command.

> mvn clean compile

- Or use javac on each java file to compile each class

-Navigate to the folder \target\classes\com\example\ use the command

> java App

### Using the app

Upon startup you will be faced with a new window. At the top will be a large text area with entries like

> [A0] Snickers-$1.35 Qty: 10
> Below this will be a screen which relays information about the sale process.
> On the next row we have a label which shows the amount of money you have entered, a text field to enter the amount of money,
> and a button which submits the amount to be added. The next row has a text area to make your selection. This will take the form of the
> letters in the box before each entry (A0 in our example).

## Design

I used Object Oriented Design and Test Driven Development during development. I initiated my git repository. My first task in design was to come up with a model. That meant taking some time to think about the way an actual vending machine works. Deciding what parts were important and what each part did. I thought about how I could abstract the concept into object form. First, I decided to model my data objects. **Abstraction** is a core concept of OOP and doing it wrong can have lasting consequences for a project. Setting up your data objects correctly allows you to keep separation of concerns more strictly. The two concepts that fit nicely into this concept were payments and the items to be sold. While I knew that I would only take cash payment now, I decided that payment should be an interface for future adding of credit card or online payments. Next I knew I would need something to handle the Json, business logic for the machine, and planned for the case of creating a graphical user interface. Next, I went through each of my planned objects and tried to anticipate what data they would hold and what tasks they would need to accomplish. After getting a good idea of what direction to go, I planned my tools. I would use Maven to automate build tasks and tests. My choice of editor is Visual Studio Code. I have experience with Swing making that the obvious choice for the graphical user interface. I researched Json libraries and decided that Jackson would be the choice for this project.

## Implementation

With less time than I would like I would follow the age old advice of "KEEP IT SIMPLE". To ensure another OOP principle **Encapsulation**, The first classes developed were the data classes. These classes only have variables with setters and getters. I have heard debates as to whether to write tests for setters and getters and decided that for now I would not write tests for them. However for all future methods I would write the test first. Throughout the projce I made sure to make frequent commits and pushes to my git repository and Github. Next, I decided to tackle importing the Json. I included Jackson Looking at the Json text directly helped in deciding how to read it. It is setup with a config object followed by an array of item objects. Therefore I designed a class with both these things and mapped the Json to that single object. Next, I researched logging libraries. I quickly decided on log4j2. I learned about it quickly. The hardest part of using log4j2 is writing the configuration file. Making sure to write tests first, I started developing a command line version of the app first. Having tests made sure that I knew when I broke something making changing behavior much easier. The behavior of my app would be as follows.

- Load Json into an object

- Use that object to setup the product layout

- Display the layout to the User

- Ask the user for payment

- Ask the user to make a product selection

- Check wheter the transaction could take place

- Dispense the item

- Reduce the product quantity

- Update the product list to reflect changes

- Ready for another transaction

After completion I made the decision to start building a GUI version. This meant refactoring some of the methods I used in the console app to communicate with the user through the GUI. Having the tests made this process much easier. I used the Apache Netbeans IDE designer to generate the boilerplate code for the components and layout.
Next came bugfixes. While I did not have many, there were instances where my test coverage missed an edge case. This was mostly the case with taking unforseen user input.
I wanted the user to be able to enter the amount of money in any way that I could think of. Finally, I made the UI to update the json used for the config and product layout. Without specifications, I thought that two ways of changing the file would work for now. I had a text area that Json could be directly written in and a text field which takes a file path and writes that file over the location of the original Json file. With some final testing and bugfixes, the project is complete.

### Project Structure and Classes

#### com\example

##### App

The main class for this project that simulates the operation of a vending machine. It reads the items and layout from a Json file. Creates GUI and logic.

#### com\example\controller

##### VendingMachine

The logic for our vending machine.

#### com\example\model

##### CoinPayment

An implementation of payment representing cash payments.

##### Config

An object to hold the rows and columns from our Json.

##### Item

An object modelling the products for sale imported from our Json.

##### Payment

An interface modelling a payment for a vending machine. It guarantees 3 behaviors. getTotal returns the current amount of payment. addAmount adds money to the total. payAmount pays for a product.

#### com\example\utilities

##### JsonInputReaderPOJO

Java object with variables and data structures to which we can map our Json input.

##### MyJson

Class that reads the input.json and maps it to our object.

##### VendingMachineLoggerTestHelper

Class for setting up and testing our log4j2 logger.

#### com\example\view

##### ProductUpdateGUI

GUI which allows writing Json or loading a new Json file to our vending machine.

##### VendingMachineGUI

Main view for our app which simulates a users experience with a vending machine.

### Interesting Decisions

#### LinkedHashMap

I wanted to store items in a data structure and be able to look items up by the String the user must enter to purchase the item. I also wanted a data structure that would print in the same order every time. LinkedHashMap fullfils both of these goals.

#### Optional

Java 8 added Optional. This type can return a value or null. Using an Optional to store the result of looking items up from the LinkedHashMap made dealing with wrong input much easier without producing errors.

### With More Time

- [ ] Refactor further
- [ ] Improve README.md
- [ ] Improve UI 
  - [ ] Add traditional vending machine buttons 
  - [ ] Allow adding actual quarters, dollars, etc.
  - [ ] Add a credit card option
