# Java Vending Machine

#### By Johnnie Meredith

---

![](https://img.shields.io/github/stars/JohnnieMeredith/vendingmachine.svg) ![](https://img.shields.io/github/forks/JohnnieMeredith/vendingmachine.svg) ![](https://img.shields.io/github/tag/JohnnieMeredith/vendingmachine.svg) ![](https://img.shields.io/github/issues/JohnnieMeredith/vendingmachine.svg)

**(Table of Contents)**

[TOCM]

[TOC]

## Instructions to run:

- Install Java JDK 12 or later.

- Download the jar located at:

  > vendingmachine\target\vendingmachine-1.0-SNAPSHOT-jar-with-dependencies.jar

- Navigate to the directory in which you saved **vendingmachine-1.0-SNAPSHOT-jar-with-dependencies.jar**.
- From the command line in that directory run:
  > java -jar vendingmachine-1.0-SNAPSHOT-jar-with-dependencies.jar

##Design
This is my second big project in as many weeks. Having the experience of the last has given me a leg up in designing this Vending Machine in Java. I used Object Oriented Design and Test Driven Development during development. My first task in design was to come up with a model. That meant taking some time to think about the way an actual vending machine works. Deciding what parts were important and what each part did. I thought about how I could abstract the concept into object form. First, I decided to model my data objects. Setting up your data objects correctly allows you to keep separation of concerns more strictly. The two concepts that fit nicely into this concept were payments and the items to be sold. While I knew that I would only take cash payment now, I decided that payment should be an interface for future adding of credit card or online payments. Next I knew I would need something to handle the Json, business logic for the machine, and planned for the case of creating a graphical user interface. Next, I went through each of my planned objects and tried to anticipate what data they would hold and what tasks they would need to accomplish.

#Implementation

### End
