# PreMCA
Predictive minimally cognitive agent for my MSc Artificial Life module.

[Project report](https://dl.dropboxusercontent.com/u/47395591/Uni%20Projects/Towards%20a%20predictive%20coding%20model%20in%20Beer%20FINAL%20DRAFT.pdf)

The aim of this project was to produce a simple agent controlled by an artificial neural network that can only move left or right. Circles fall from the top of the screen, and the agent must move sideways to 'catch' it. The agent has seven ray sensors that detect if the circle intersects them, and report the distance to the circle.

Parameters for the neural network were optimised with an evolutionary algorithm, which, like natural selection, pits set of network parameters (genotypes) against each other to see who produces the best agent. Less optimal parameters are then randomly changed and tested again; eventually some optimal parameters are found.

This is a complex project; but I learnt how decoupling classes and separating them into different modules helps keep complex projects maintainable. This also helped when I extracted two spin off modules for further reuse: [jgenesis](https://github.com/mbryantlibrary/jgenesis) and [jctrnn](https://github.com/mbryantlibrary/jctrnn). 

![Agent](https://dl.dropboxusercontent.com/u/47395591/Uni%20Projects/software/ALife%20project/agent.png)

![Main window](https://dl.dropboxusercontent.com/u/47395591/Uni%20Projects/software/ALife%20project/mainUI.png)
