# My Dev Week Project for 2024
Dev weeks are a perk of working at Instaclustr - this year (even though I am doing this late cause ie in 2025) is to setup a contained development environment using development containers (https://containers.dev/)

These containers are powerful development tools, allowing large teams to have homogenous development environments, minimising the amount of "it works on my machine" issues you may have. It also allows development environments to more closely mirror production runtimes.

For this project, I've developed a small set of Spring based applications, the following diagram shows the architecture of it.

<img src="./architecture.svg">

## Goals

- See how easy and quick it is to setup a development based environment
- See how easy it is to BYO dev container
- See how easy it is to map access into the container
- Make the entire environment setup just one button press


## Non-Goals
- Transition any Instaclustr processes to use dev containers

## How to use the project

The project is basic, per the diagram, on dev container start it, it will attempt to run a flyway migration to create the required person table.

The project requires use of Intellij, to start it, just open the .devcontainer/devcontainer.json and run "Create Dev Container Mount Sources" - or clone it is you want completely isolated dev environments. Both should work.

From your local machine all you should need to do is start the front and backend apps from the intellij window that opens. Then from your host OS you can run curl localhost:3000/app/getdata to request a record from the DB through the architecture