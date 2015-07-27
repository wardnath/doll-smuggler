# doll-smuggler by Nathan Ward

## Problem specification:

This project is a solution to the [Atomic Object Clojure coding challenge](https://github.com/micahalles/doll-smuggler). The problem specified for this challenge is not a new one, it is a variant of the [knapsack problem](https://en.wikipedia.org/wiki/Knapsack_problem) which is a standard example in computer science literature used to demonstrate principles in dynamic programming and computational complexity. 

The major unique feature of this project is not the  knapsack calculation algorithm itself, rather it is the use of [JSON](https://en.wikipedia.org/wiki/JSON) for providing information about the dolls and knapsack and for the output of the data from the calculation. 

JSON is often used for transmitting and receiving information from RESTful web services. This project could feasibly be hosted on a webserver and could be used to provide optimal doll packing advice to clients who access this service. Clients would send in information to our webserver using JSON to encode the capacity of the backpack and all of the possible dolls that can go on a trip. Our product would then send back the recommendations of what dolls they should take via JSON coding again.

## Requirements

* The Linux OS (preferably Ubuntu 64 bit edition)
* leiningen
* org.clojure/clojure
* [cheshire](https://github.com/dakrone/cheshire)
* org.clojure/tools/cli

The last three requirements are automatically retrieved by leiningen. 

## Installation and Usage

1. Download the project from https://github.com/wardnath/doll-smuggler using git or download as a zip and extract
2. [Install Leiningen](https://github.com/technomancy/leiningen#installation)
3. cd to project directory
4. Run with Leinengen
  * To run the code type in `lein run -m doll-smuggler.core`
  * To run the test suite type in `lein test`

**Output Format**

With `lein run -m doll-smuggler.core` the code will output JSON formatted data of the solution to dolls_input_0.json located in the input_files directory. With command line options, other input data can also be provided. The output includes the dolls that should be put in the backpack, the total weight of these dolls:
```
$ lein run
{
  "total_weight" : 396,
  "dolls" : [ {
    "name" : "luke",
    "weight" : 150,
    "value" : 9
  }, {
    "name" : "anthony",
    "weight" : 35,
    "value" : 13
  }, {
    "name" : "candice",
    "weight" : 200,
    "value" : 153
  }, {
    "name" : "dorothy",
    "weight" : 160,
    "value" : 50
  }, {
    "name" : "puppy",
    "weight" : 60,
    "value" : 15
  }, {
    "name" : "randal",
    "weight" : 60,
    "value" : 27
  }, {
    "name" : "marc",
    "weight" : 70,
    "value" : 11
  }, {
    "name" : "grumpkin",
    "weight" : 70,
    "value" : 42
  }, {
    "name" : "dusty",
    "weight" : 75,
    "value" : 43
  }, {
    "name" : "grumpy",
    "weight" : 80,
    "value" : 22
  }, {
    "name" : "eddie",
    "weight" : 20,
    "value" : 7
  }, {
    "name" : "sally",
    "weight" : 50,
    "value" : 4
  } ]
}
```

**Input Format**

JSON data provided to the application should include the maximum capacity of the backpack and a list of all of the dolls, where each doll has a name, weight, and a value. An example of this is shown below:

```
{
  "max_weight": 400,
  "dolls" : [
    { "name": "luke",
      "weight": 9,
      "value": 150
      },
    { "name": "anthony",
      "weight": 13,
      "value": 35
      }
    ]
  };
```


## Options

There are two parameters that can be used at the command line for doll-smuggler

```
Options:
  -f, --file FILEPATH  Default: ./input_files/dolls_input_0.json This provides the JSON file to the application
  -h, --help
```

## Examples

 $ lein run -m doll-smuggler.core

Run code with default data, dolls_input_0.json

 $ lein run -m doll-smuggler.core -f "./input_files/dolls_input_1.json"

Runs the application with dolls_input_1.json from the input_files directory

 $ lein run -m doll-smuggler.core -h 

Generates helpful documentation

$ lein test

Runs test suite



