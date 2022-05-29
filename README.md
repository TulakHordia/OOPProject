# Object-Oriented Programming - Project.
Creators Name: Benjamin Rain
ID: 319520425

* Part 1: Hierarchy, Polymorphism, Object and Exceptions. [Done]
* Part 2: Interfaces, files, generics [Done]
* Part 3: GUI & MVC [Done]

* <https://github.com/TulakHordia>

## Table of contents

> * [Object-Oriented Programming - Project / github.com/TulakHordia/OOPProject]
>   * [Table of contents]
>   * [Usage]
>   * [Content]
>   * [For known issues please refer to the known_issues file]

## About / Synopsis
Whole project is complete.
A program used to create, edit and manipulate questions.
Create exams automatically or even manually and save them into a .txt file.
Import/Export binary files, save to .txt files.

## Usage
Launch the program, everything else should be self-explanatory on how it works.

## Content
Contains a way to add a new question + answer. (American or an Open one)
Double clicking the questions table will enter "edit mode" and allow you to edit the question (by pressing Enter when done).
Can see the Answers for an american question in a separate window.
Can import & export files in and out of the program.
Can opt into creating an automatic exam with set amount of questions or a manual one by selecting them in the table.
Functions to manually create an exam with the available questions or rather let the program create one for you.

* Contains a 'Program' main class to request user input for the various options and implemented methods from 'ProgramMethods'.
* A 'Manager' class to manage all the requests coming from the 'Program' main class.
* 'Question' abstract class to contain all the data & info regarding the questions.
* 'AmericanQ' along with 'OpenQ' classes to store all sub-data of 'Question' class.
* 'AmericanAnswers' has all the available answers to the 'AmericanQ' questions.
* 'QuestionComparator' sorts the requested array by the total answer length.
* 'ProgramMethods' is an interface used to hand-out methods for the 'Program' class.

## Work to be done
'What work needs to be done in order to add multiple-subjects into the code?'
Basically I only need to add a new 'Class' for each subject so I can add the question-objects into the relevant subject class type.



### Thank you for using the program.
### Best regards,
### Benjamin Rain. :)

