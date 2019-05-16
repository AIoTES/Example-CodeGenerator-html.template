# HTML template system for Codegenerator tool
This is a template system that generates an HTML static website ready to browse the ontologies (and all their respective definitions and annotations) added to the transformation project.

## Getting stated / Use
To use just reffer the codegenerator tool to the main xml file in *src/main/resources*, wether locally (after clone) or remotely (using the raw access URL of this repository)

## How to build, install, or deploy it
We recommend the use of Eclipse IDE to edit this project. 
Eclipse has has some plugins to make it easier the Velocity Templates development ([VeloEdit](https://github.com/vaulttec/veloedit)), XML files and more!

To start to work with this project, the first step is clone this repository.
Import the project with Eclipse:
1. file->import
2. them select "existing maven project"
3. select "browse" button and navigate to root directory where you  was clone this repository
4. after that you wll see a checked file called "/pom.xml"
5. now, just click "finish" button and wait to Eclipse import all necessary files to start  

## Testing
To run the tests, use maven:
```
mvn clean test
```
This project contains the following tests:
* Test cases to view [OWL API](https://github.com/owlcs/owlapi/wiki/Documentation) workflow
* Test cases to view [Velocity](http://velocity.apache.org/) workflow
  * incliding Velocity Template files to view how velocity works
* Test cases to test individual velocity macros of the template.
* Test cases to test the template using the CodeGenerator core workflow

## Tutorial
This is a sample template system, it is meant for remix. To use it:
1. Clone it, fork it, or copy the files, into your local file system.
2. Edit your project, following the [wiki](https://gitlab.lst.tfo.upm.es/Activage-madrid-ds/code.generator/wikis/home) or the [course](https://poliformat.upv.es/portal/directtool/4136ab45-e867-4287-ac8e-d5eed63f8307/)(must be signed in).
3. Edit the README file, remember:
   1. edit the title and description
   2. edit/update _getting started_ section
   3. edit/update _build, install or deploy_ section
   4. edit/update/remove _testing_ section
   5. remove _tutorial_ section (if your project is a template then update it)
   6. Add your name and contact to credits, or update the section
   7. edit/update/remove _contributing_ section
   8. update _licence_ section

## Credits
This template is been created by:

Eduardo Buhid <ebuhid@lst.tfo.upm.es>
Alejandro Medrano <amedrano@lst.tfo.upm.es>


## Contributing
Pull requests are always appreciated.

Codegenerator does not contain a centralised template reference index, ensure your community knows about the template, watch codegnerator main project for whenever this feature is added.

## License
This software is published under the unlicense license:
```
This is free and unencumbered software released into the public domain.

Anyone is free to copy, modify, publish, use, compile, sell, or
distribute this software, either in source code form or as a compiled
binary, for any purpose, commercial or non-commercial, and by any
means.

In jurisdictions that recognize copyright laws, the author or authors
of this software dedicate any and all copyright interest in the
software to the public domain. We make this dedication for the benefit
of the public at large and to the detriment of our heirs and
successors. We intend this dedication to be an overt act of
relinquishment in perpetuity of all present and future rights to this
software under copyright law.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

For more information, please refer to <http://unlicense.org/>

```