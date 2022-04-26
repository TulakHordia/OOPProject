# Object-Oriented Programming - Project.
Creators Name: Benjamin Rain
ID: 319520425

* Part 1: Hierarchy, Polymorphism, Object and Exceptions. [Done]
* Part 2: Interfaces, files, generics [Done]
* Part 3: GUI & MVC

* <https://github.com/TulakHordia>

## Table of contents

> * [Object-Oriented Programming - Project / github.com/TulakHordia/OOPProject]
>   * [Table of contents]
>   * [Usage]
>   * [Content]
>   * [For known issues please refer to the known_issues file]

## About / Synopsis
Parts 1 & 2 are done.
A program used to create and edit exams, managing specific questions and answers.
Create and export exams, copy exams, import question & answer lists.

## Usage
Run the program, select your option from the menu and proceed along with the program requests and inputs.

## Content
Contains a way to add a new question + answer.
Update a question, or update/delete an answer.
Functions to manually create an exam with the available questions or rather let the program create one for you.
Or even create a copy of an existing exam.
Can sort the questions list by answer length.
Export exams into .txt files or .ser files.
Import .ser files.

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

